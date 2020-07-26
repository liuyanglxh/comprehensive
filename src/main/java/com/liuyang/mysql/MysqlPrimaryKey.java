package com.liuyang.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MysqlPrimaryKey {

    private int dataNum = 950000;//表的数据总量
    private int selectSize = 5000;//查询的总量
    private int threadPoolSize = 1;//查询线程数

    private DruidDataSource mysqlPool = new DruidDataSource();
    private List<Pojo> pojos = new ArrayList<>(dataNum);
    private List<List<String>> udidGroup = new ArrayList<>(threadPoolSize);
    private ExecutorService pool = Executors.newFixedThreadPool(threadPoolSize);
    private List<String> allUdids = new ArrayList<>();

    {
        for (int i = 0; i < dataNum; i++) {
            pojos.add(new Pojo());
        }

        //数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        mysqlPool.setUrl("jdbc:mysql://localhost:3306/ly");
        mysqlPool.setUsername("root");
        mysqlPool.setPassword("123456");
        mysqlPool.setMaxActive(threadPoolSize);


        try {
            ResultSet resultSet = mysqlPool.getConnection().prepareStatement("select * from ly.udids").executeQuery();
            while (resultSet.next()) {
                String udid = resultSet.getString("udid");
                allUdids.add(udid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //先触发多次minorGc让对象移动到老年代
        for (int i = 0; i < 1_000_000; i++) {
            if (new byte[1000].length == 0) {
                break;
            }
        }
        System.gc();
    }


    @Test
    public void testInsert() throws Exception {
        this.changePojos();
        String insert1 = "insert into udid_primary_key (udid, name, docIds) values(?,?,?);";
        System.out.println("主键：" + this.insert(insert1));
        String insert2 = "insert into udid_unique_key (udid, name, docIds) values(?,?,?);";
        System.out.println("唯一索引：" + this.insert(insert2));

    }

    @Test
    public void testPrimaryKey() throws Exception {
        String select1 = "select * from udid_primary_key where udid = ?";
        this.udidGroup();
        System.out.println("主键：" + this.select(select1));

    }

    @Test
    public void testUnique() throws Exception {
        String select2 = "select * from udid_unique_key where udid = ?";
        this.udidGroup();
        System.out.println("唯一索引：" + this.select(select2));

    }

    private void udidGroup() {
        int from = 0;
        int size = selectSize / threadPoolSize;
        for (int i = 0; i < threadPoolSize; i++) {
            udidGroup.add(allUdids.subList(from, from + size));
            from += size;
        }
    }

    @Test
    public void readUdids() {
        try (DruidPooledConnection connection = mysqlPool.getConnection()) {

            PreparedStatement p = connection.prepareStatement("select udid from udid_primary_key");
            ResultSet resultSet = p.executeQuery();

            List<String> allUdids = new ArrayList<>();
            while (resultSet.next()) {
                allUdids.add(resultSet.getString("udid"));
            }
            Collections.shuffle(allUdids);
            if (allUdids.size() > selectSize) {
                allUdids = allUdids.subList(0, selectSize);
            }

            connection.prepareStatement("truncate table ly.udids").execute();
            PreparedStatement pre = connection.prepareStatement("insert into ly.udids (udid) values (?)");
            for (String allUdid : allUdids) {
                pre.setString(1, allUdid);
                pre.execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long select(String sql) throws Exception {
        System.gc();

        CountDownLatch count = new CountDownLatch(udidGroup.size());

        long start = System.currentTimeMillis();

        for (List<String> udidList : udidGroup) {
            pool.execute(() -> {
                try (Connection connection = mysqlPool.getConnection()) {
                    try (PreparedStatement p = connection.prepareStatement(sql)) {
                        for (String udid : udidList) {
                            p.setString(1, udid);
                            p.executeQuery();
                        }
                        count.countDown();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        count.await();

        return (System.currentTimeMillis() - start) / 1000;
    }

    private long insert(String sql) throws Exception {
        System.gc();

        long start = System.currentTimeMillis();


        try (Connection connection = mysqlPool.getConnection()) {
            try (PreparedStatement p = connection.prepareStatement(sql)) {
                for (Pojo pojo : pojos) {
                    p.setString(1, pojo.udid);
                    p.setString(2, pojo.name);
                    p.setString(3, pojo.docIds);
                    p.execute();
                }
            }
        }

        return System.currentTimeMillis() - start;
    }

    private void changePojos() {
        for (Pojo pojo : pojos) {
            pojo.udid = UUID.randomUUID().toString();
            pojo.name = pojo.udid.substring(0, 10);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(ThreadLocalRandom.current().nextInt(1000, 10000));
            }
            pojo.docIds = list.stream().map(String::valueOf).collect(Collectors.joining(","));
        }
        System.out.println("pojo 初始化完成");
    }


    class Pojo {
        String udid;
        String name;
        String docIds;
    }

    @Test
    public void testAsyncInsert() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        CountDownLatch count = new CountDownLatch(2);

        long start = System.currentTimeMillis();
        pool.execute(() -> {
            String sql = "insert into ly.udid_unique_key select * from ly.udid_primary_key where udid <= '7fe914e8-d633-476b-a3ba-7321d8cfdffe'";
            try {
                mysqlPool.getConnection().prepareStatement(sql).execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                count.countDown();
            }
        });
        pool.execute(() -> {
            String sql = "insert into ly.udid_unique_key select * from ly.udid_primary_key where udid > '7fe914e8-d633-476b-a3ba-7321d8cfdffe'";
            try {
                mysqlPool.getConnection().prepareStatement(sql).execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                count.countDown();
            }
        });

        count.await();

        System.out.println(System.currentTimeMillis() - start);

    }

}
