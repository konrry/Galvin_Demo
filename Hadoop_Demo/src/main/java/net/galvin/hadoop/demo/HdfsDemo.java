package net.galvin.hadoop.demo;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HdfsDemo {

    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) {
        HdfsDemo hdfsDemo = new HdfsDemo();
        hdfsDemo.test();
    }

    public void test(){
        System.out.println(" test start ... ");
        InputStream inputStream = null;
        try {
            inputStream = new URL("hdfs://127.0.0.1:9000/opt").openStream();
            IOUtils.copyBytes(inputStream, System.out, 1024, true);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(" test end ... ");
    }

}
