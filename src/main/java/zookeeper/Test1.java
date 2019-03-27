package zookeeper;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Test1 {
    String url = "192.168.66.128:2181,192.168.66.129:2181";
    int timeout = 20000;
    ZooKeeper client;

    //产生客户端
    @Before
    public void init() throws IOException {
        client = new ZooKeeper(url, timeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                    List<String>childs;
                    System.out.println("=============有情况，begin=============");
                    try {
                        childs = client.getChildren("/",true);
                        for(String c:childs){
                            System.out.println(c);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                System.out.println("============情况查看结束，end==========");
            }
        });
    }
    //创建节点
    @Test
    public void createNode() throws Exception {
        String path = client.create("/hzn/java","zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }


    //获取子节点并监控子节点的变化
    @Test
    public void getAndWatch() throws Exception {
        List<String>childs = client.getChildren("/",true);
        for(String c:childs){
            System.out.println(c);
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
