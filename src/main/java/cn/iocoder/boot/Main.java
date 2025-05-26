package cn.iocoder.boot;

import org.openqa.selenium.edge.EdgeDriver;


public class Main {
    public static void main(String[] args) {
        EdgeDriver driver = null;

        try {
            driver = OpenEdge.openEdge
                    ("https://www.lanqiao.cn/problems/90/learning/", "18989201175", "Aa375279901", "E:\\app\\edgedriver\\msedgedriver.exe");
            Testput.clickClose(driver);
        } catch (Exception e) {
            System.out.println("====操作失败，可能未找到页面====");
        } finally {
            EndEdge.closeDriver(driver);
        }
    }
}