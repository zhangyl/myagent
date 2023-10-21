package com.zyl.curator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentNode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class Test {
	// 工厂创建客户端的另一种方式


	

	// 测试
	public static void main(String[] args) throws Exception {
		// 设置重置策略
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000, 3);
		// 设置连接的字符串
		String conneString = "localhost:2181";
		// 工厂创建客户端
//		CuratorFramework client = CuratorFrameworkFactory.newClient(conneString, retryPolicy);
		
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(conneString).connectionTimeoutMs(3000)
				.retryPolicy(retryPolicy).sessionTimeoutMs(20000).canBeReadOnly(false).defaultData(null).build();
		
		client.start();
		String parentPath = "/zylservice";
		String path = "/zylservice/idgen";
		if(client.checkExists().forPath(parentPath) == null) {
			String serviceNode = client.create().creatingParentsIfNeeded().forPath(parentPath, null);
			System.out.println(serviceNode);
		}
        

		{
			List<String> list = client.getChildren().forPath(parentPath);
			System.out.println("list=" + list);
		}
		{
			String forPath = client.create()
//					.withProtection()
					.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath(path, "192.168.0.1".getBytes());
			System.out.println("create node ==> " + forPath);
			client.delete().guaranteed().deletingChildrenIfNeeded().forPath(forPath);
			System.out.println("delete node ==> " + forPath);

			//			PersistentNode node = new PersistentNode(client, CreateMode.EPHEMERAL_SEQUENTIAL, false, path, "192.168.0.1".getBytes());
//			node.start();
//	        node.waitForInitialCreate(3, TimeUnit.SECONDS);
		}
//		{
//			PersistentNode node = new PersistentNode(client, CreateMode.EPHEMERAL_SEQUENTIAL, false, path, "192.168.0.2".getBytes());
//			node.start();
//	        node.waitForInitialCreate(3, TimeUnit.SECONDS);
//		}
//		{
//			PersistentNode node = new PersistentNode(client, CreateMode.EPHEMERAL_SEQUENTIAL, false, path, "192.168.0.3".getBytes());
//			node.start();
//	        node.waitForInitialCreate(3, TimeUnit.SECONDS);
//		}
        {
			List<String> list = client.getChildren().forPath(parentPath);
			System.out.println("list=" + list);
			
			for(String item : list) {
				try {
					String actualPath = parentPath + "/" + item;
					String host = new String(client.getData().forPath(actualPath));
					System.out.println("host=" + host);
				} catch(Exception ignore) {
					
				}
			}
        }
        
        
//        String actualPath = node.getActualPath();
//        System.out.println("临时节点路径==> " + actualPath + ", 值==> " + new String(client.getData().forPath(actualPath)));
//        node.close();

        {
			List<String> list = client.getChildren().forPath(parentPath);
			System.out.println("list=" + list);
        }
        System.out.println("=======");

	}
}
