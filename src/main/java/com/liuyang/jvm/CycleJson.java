package com.liuyang.jvm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CycleJson {

    public static void main(String[] args) throws JsonProcessingException {
        M m1 = new M();
        M m2 = new M();
        m1.m = m2;
        m2.m = m1;
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(m1));
    }


    static class M {
        private M m;
        private String name = "1";

        public M getM() {
            return m;
        }

        public M setM(M m) {
            this.m = m;
            return this;
        }

        public String getName() {
            return name;
        }

        public M setName(String name) {
            this.name = name;
            return this;
        }
    }
}
