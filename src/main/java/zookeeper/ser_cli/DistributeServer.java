package zookeeper.ser_cli;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {
    String url = "192.168.66.128:2181,192.168.66.129:2181";
    int timeOut = 20000;
    ZooKeeper client;
    //连接zk集群
    ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(url, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
    }

    //注册节点
    void regist(String ip)throws Exception{
        client.create("/servers/server",ip.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(ip+"服务器上线了");
    }


    public static void main(String[] args) throws Exception {
        DistributeServer server = new DistributeServer();
        server.client = server.getConnection();
        server.regist("192.168.66.128");
        Thread.sleep(Long.MAX_VALUE);
    }
}
