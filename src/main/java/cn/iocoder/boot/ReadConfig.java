package cn.iocoder.boot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadConfig {
    public static void main(String[] args) {

        ArrayList<String> brr=  ConfigRead("config.txt");
        System.out.println(brr);
    }



    public static ArrayList<String> ConfigRead(String filePath)
    {
        ArrayList<String> arr=new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null) //0 1 2
            {
               arr.add(line);
               i++;
            }
        } catch (IOException e) {
            System.err.println("读取配置文件时发生错误: " + e.getMessage());
        }
        return arr;
    }

}
