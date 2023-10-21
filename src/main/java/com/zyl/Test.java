package com.zyl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ServiceLoader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
 
public class Test {
	//按照行顺序读取400000行 ~~~~OK~~~~~ time=2019ms
	public static void main(String[] args) throws Exception {
		ServiceLoader.load(null);
		long b = System.currentTimeMillis();
//		Files.createFile(Path.of("/Users/zhangyl/Desktop/aaa.txt"));
	
		File file = new File("/Users/zhangyl/Desktop/aaa.txt");
		LineIterator li = FileUtils.lineIterator(file);
		int i=0;
		while (li.hasNext()) {
			li.nextLine();
			i++;
		}
		
		long e = System.currentTimeMillis();
		System.out.println("line=" + i + " ~~~~OK~~~~~ time=" + (e - b) + "ms" );
	}
	//~~~~OK~~~~~顺序写40万行 time=1284ms
    public static void main0(String[] args) throws IOException {
    	String jsonStr =
    			"{'name':'lili', 'sex':'男', "
    			+ "'custom':"
    			+ "["
    			+ "{'title':'选项A', 'key':'CF01', 'value':'A'},"
    			+ "{'title':'选项D', 'key':'CF01', 'value':'A'},"
    			+ "{'title':'选项E', 'key':'CF01', 'value':'A'},"
    			+ "{'title':'选项F', 'key':'CF03', 'value':'C'},"
    			+ "{'title':'选项G', 'key':'CF06', 'value':'D'},"
    			+ "{'title':'选项H', 'key':'CF07', 'value':'ABBB'},"
    			+ "{'title':'选项H', 'key':'CF08', 'value':'A'},"
    			+ "{'title':'选项I', 'key':'CF07', 'value':'A'},"
    			+ "{'title':'选项A', 'key':'CF01', 'value':'A'},"
    			+ "{'title':'选项D', 'key':'CF01', 'value':'A'},"
    			+ "{'title':'选项E', 'key':'CF01', 'value':'A'},"
    			+ "{'title':'选项F', 'key':'CF03', 'value':'C'},"
    			+ "{'title':'选项G', 'key':'CF06', 'value':'D'},"
    			+ "{'title':'选项H', 'key':'CF07', 'value':'ABBB'},"
    			+ "{'title':'选项H', 'key':'CF08', 'value':'A'},"
    			+ "{'title':'选项I', 'key':'CF07', 'value':'A'},"
    			+ "{'title':'选项B', 'key':'CF02', 'value':'B'}"
    			+ "]"
    			+ "}\r\n";
    	long b = System.currentTimeMillis();
		FileOutputStream fos = null;
		FileChannel channel = null;
		RandomAccessFile randomAccessFile = null;
		try {
	        randomAccessFile = new RandomAccessFile("/Users/zhangyl/Desktop/aaa.txt", "rw");
	        channel = randomAccessFile.getChannel();
		    MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 512 * 1024 * 1024);
			for(int i=0; i<400000; i++) {

				byte[] array = jsonStr.getBytes();
				mappedByteBuffer.put(array);
//				ByteBuffer buffer = ByteBuffer.wrap(array);
//				// 4. 读取到缓冲区
//				channel.write(buffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7. 关闭
			try {
				channel.close();
			} catch (IOException ignore) {
			}
			try {
				if(fos != null)
					fos.close();
				if(randomAccessFile != null) {
					randomAccessFile.close();
				}
			} catch (IOException ignore) {

			}
		}
		long e = System.currentTimeMillis();
        System.out.println("~~~~OK~~~~~ time=" + (e-b) + "ms");
    }
}

