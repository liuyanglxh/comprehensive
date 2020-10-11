package com.liuyang.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Hbase_t {

    private Map<String, String> getConfig() {
        Map<String, String> hbaseConf = new HashMap<>();
        String ip = "47.97.23.214";
        hbaseConf.put("hbase.zookeeper.property.maxclientcnxns", "300");
        hbaseConf.put("hbase.ipc.client.socket.timeout.connect", "1000");
        hbaseConf.put("zookeeper.session.timeout", "500");
        hbaseConf.put("hbase.regionserver.handler.count", "500");
        hbaseConf.put("hadoop.security.authentication", "kerberos");
        hbaseConf.put("hbase.master.kerberos.principal", "hbase/_HOST@KFPT.CTC.LOCAL");
        hbaseConf.put("hbase.regionserver.kerberos.principal", "hbase/_HOST@KFPT.CTC.LOCAL");
        hbaseConf.put("hbase.zookeeper.property.clientPort", "2181");
        hbaseConf.put("hbase.security.authentication", "kerberos");
        hbaseConf.put("hbase.zookeeper.quorum", ip);
        hbaseConf.put("hadoop.security.auth_to_local", "RULE:[1:$1]");

        return hbaseConf;
    }

    @Test
    public void test() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("zookeeper.znode.parent", "/hbase");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "47.97.23.214:2181");
//        config.addResource(new Path("/Users/liuyang/Documents/git_repository/comprehensive/src/main/resources/hbase/hbase-site.xml"));

        Connection connection = ConnectionFactory.createConnection(configuration);
        System.out.println("closed "+connection.isClosed());

        try (Admin admin = connection.getAdmin()) {
            System.out.println("get admin");
            String tableName = "test";
            TableName tb = TableName.valueOf(tableName);


            if (!admin.tableExists(tb)) {

                System.out.println("table not exists");

                HColumnDescriptor column = new HColumnDescriptor("f");
                column.setMaxVersions(200);

                HTableDescriptor table = new HTableDescriptor(tb);
                table.addFamily(column);

                admin.createTable(table);
            } else {
                System.out.println("table exists");
            }

        }

        Put put = new Put("udud".getBytes());
        put.addColumn("f1".getBytes(), "q".getBytes(), System.currentTimeMillis(), "123".getBytes());

        try (Table table = connection.getTable(TableName.valueOf("test"))) {
            table.put(put);
        }

    }

    @Test
    public void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println("t"), "t");
        Thread t2 = new Thread(() -> System.out.println("t"), "t");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }



}
