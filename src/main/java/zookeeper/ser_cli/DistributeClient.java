package zookeeper.ser_cli;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistributeClient {
    String url = "192.168.66.128:2181,192.168.66.129:2181";
    int timeout = 20000;
    ZooKeeper client;

    void getChildren()throws Exception{
        List<String>childs = client.getChildren("/servers",true);
        //获取zk上节点中的数据
        ArrayList<String> hosts = new ArrayList<>();
        for(String c:childs){
            byte[]data = client.getData("/servers/"+c,false,null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
    }

    void getConnection()throws Exception{
        client = new ZooKeeper(url, timeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChildren();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args)throws Exception {
        DistributeClient dis_cli = new DistributeClient();
        dis_cli.getConnection();
        dis_cli.getChildren();

        Thread.sleep(Long.MAX_VALUE);
    }
}
