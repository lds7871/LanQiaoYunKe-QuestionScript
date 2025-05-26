package cn.iocoder.boot;

import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        while (true)
        {
            EdgeDriver driver = null;
            int 题目加一 = 0;
            try {
                ArrayList<String> arr = ReadConfig.ConfigRead("config.txt");//读取设置
                String line = "https://www.lanqiao.cn/problems/" + arr.get(3) + "/learning/";
                题目加一 = Integer.parseInt(arr.get(3)) + 1;
                driver = OpenEdge.openEdge(line, arr.get(0), arr.get(1), arr.get(2));//赋值并启动浏览器

                Testput.clickClose(driver);  //做题
            } catch (Exception e) {
                System.out.println("====操作失败，可能未找到页面====");
                System.out.println(e);
            } finally {
                EndEdge.closeDriver(driver);//关闭浏览器
                AddConfig.modifyFourthLine("config.txt", 题目加一);
            }
        }


    }
}