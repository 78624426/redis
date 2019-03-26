package demo1;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test {
    static Jedis j = new Jedis("192.168.66.128",6379);
    public static void test(){
        System.out.println(j.ping());//看到pong表示客户端连接成功
        j.set("address","南京");

        System.out.println(j.get("address"));
        j.incrBy("age",10);
        Set<String>keys =  j.keys("*");
        for(String s:keys){
            System.out.println(s+",类型:"+j.type(s));
        }

        Map<String,String> m = new HashMap<String,String>();
        m.put("driver","mysql");
        m.put("url","192.168");
        j.hmset("con",m);
        System.out.println(j.hgetAll("con"));
    }

    public static void main(String[] args) {
        test();
    }
}
