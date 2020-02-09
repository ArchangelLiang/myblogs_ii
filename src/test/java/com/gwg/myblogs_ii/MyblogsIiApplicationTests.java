package com.gwg.myblogs_ii;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.HashMap;

@SpringBootTest
class MyblogsIiApplicationTests {

    @Test
    void contextLoads() {
        String password = DigestUtils.md5DigestAsHex("gao1742277838".getBytes());
        System.out.println(password);
    }

    @Test
    void testSQL(){
        HashMap<String, String> param = new HashMap<>();
        param.put("id","1");
        String sql = new SQL(){{
            SELECT("*");
            FROM("blog");
            if (param.get("id")!=null){
                WHERE(" id= " + param.get("id"));
            }
            if (param.get("name") !=null) {
                WHERE("name=" + param.get("name"));
            }
            LIMIT("10");
            OFFSET("5");
        }}.toString();
        System.out.println(sql);
    }

}
