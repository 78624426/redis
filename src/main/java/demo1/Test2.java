package demo1;

import com.alibaba.druid.support.jconsole.model.GroupableTableHeader;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class Test2 {
    //static Jedis j = new Jedis("192.168.66.128",6379);
    static Jedis j ;
    public static void main(String[] args) throws InterruptedException {
        //tr();
        ms();
    }

    //主从复制
    static void ms(){
        Jedis master = new Jedis("192.168.66.128",6379);
        Jedis slave = new Jedis("192.168.66.128",6380);
        slave.slaveof("192.168.66.128",6379);

        master.set("driver","mysql");

        System.out.println(slave.get("driver"));
    }






//事务
    static boolean tr() throws InterruptedException {
        j.set("money","960");
        j.set("debt","40");
        int consume = 10;
        int money;
        //为防止在执行事务期间被其它线程修改money值，加一个监控，也叫乐观锁
        j.watch("money");
        //在此期间，money值被其它线程修改，则事务失败
        Thread.sleep(20000);
        money = Integer.parseInt(j.get("money"));
        if(money<consume){
            j.unwatch();
            System.out.println("信用卡额度不够，请升级你的卡");
            return false;
        }

        //开启事务:money-10,debt+10
        Transaction t = j.multi();
        t.incrBy("debt",consume);
        t.decrBy("money",consume);

        List<Object>list = t.exec();
        if(list==null||list.isEmpty()){
            System.out.println("事务失败,money被其它线程修改，稍后再试");
        }
        System.out.println("事务结束，余额："+j.get("money")+",欠费："+j.get("debt"));
        return false;
    }
}
