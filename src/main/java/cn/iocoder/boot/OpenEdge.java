package cn.iocoder.boot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OpenEdge {
    private static final Logger logger = LoggerFactory.getLogger(OpenEdge.class);

    public static EdgeDriver openEdge(String 链接,String 手机号,String 密码,String 本地程序) {
        System.out.println(链接);
        System.out.println(手机号);System.out.println(密码);System.out.println(本地程序);

        EdgeDriver driver = null;
        try {
            System.setProperty("webdriver.edge.driver", 本地程序);

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");

            logger.info("正在启动Edge浏览器...");
            driver = new EdgeDriver(options);
            driver.get(链接);

            // 等待3秒，确保元素加载完成
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


            //使用CSS选择器定位     请输入手机号码
            WebElement inputElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[placeholder='请输入手机号码/邮箱']")));
            inputElement.clear();
            inputElement.sendKeys(手机号);
            Thread.sleep(300);

            //使用CSS选择器定位    请输入密码
            WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[placeholder='请输入密码']")));
            passwordElement.clear();
            passwordElement.sendKeys(密码);
            Thread.sleep(300);

            // 点击登录按钮
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".ant-btn.ant-btn-primary.ant-btn-block")));
            loginButton.click();
            Thread.sleep(200);

            return driver; // 返回driver实例

        } catch (Exception e) {
            logger.error("登录操作失败: ", e);
            throw new RuntimeException("登录操作失败", e);
        }
    }
}