package cn.iocoder.boot;
/*这里的有些操作通过模拟点击完成，所以运行到这里请不要动鼠标*/
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Testput {
    //btn-close
    private static final Logger logger = LoggerFactory.getLogger(Testput.class);

    public static void clickClose(EdgeDriver driver) {
        try {
            // 等待元素可见
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            // 定位关闭按钮并点击
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.className("btn-close")));
            closeButton.click();
                    // 等待页面加载
            Thread.sleep(4000);

            // 尝试多种定位方式找到"题解"元素
            WebElement tiJieElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'题解')]")));
                // 确保元素可见和可点击
            wait.until(ExpectedConditions.elementToBeClickable(tiJieElement));
                // 使用JavaScript执行点击
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tiJieElement);
            Thread.sleep(300);

            // 定位并点击"C/C++"
            WebElement cppElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(text(),'C/C++')]")));
            wait.until(ExpectedConditions.elementToBeClickable(cppElement));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cppElement);
            Thread.sleep(1000);

            // 点击第一个人的答案
            WebElement firstNoteTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("note-title")));
            wait.until(ExpectedConditions.elementToBeClickable(firstNoteTitle));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstNoteTitle);
            Thread.sleep(2000);

            // 鼠标操作点击复制按钮（这里没法通过模拟点击来实现复制）
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("content")));
                    // 使用JavaScript查找复制按钮
            String script = "return document.querySelector('.content').querySelector('.icon-copy')";
            WebElement copyButton = (WebElement) ((JavascriptExecutor) driver).executeScript(script);
                    // 滚动到元素位置
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", copyButton);
            Thread.sleep(500);
                    // 使用Actions类来实现鼠标悬停
            Actions actions = new Actions(driver);
            actions.moveToElement(copyButton)  // 鼠标悬停
                    .pause(500)                 // 等待500毫秒
                    .click()                    // 执行点击
                    .perform();
            Thread.sleep(300);  // 等待复制操作完成

            // 定位并点击关闭按钮
            WebElement closeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("close-btn")));
            wait.until(ExpectedConditions.elementToBeClickable(closeBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
            Thread.sleep(300);

            // 定位代码编辑区域并操作
            WebElement codeEditorArea = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("view-lines")));
            actions.moveToElement(codeEditorArea).click().perform();// 点击编辑区域
            Thread.sleep(300);
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();// 执行全选操作 (Ctrl+A)
            Thread.sleep(300);
            actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();// 执行粘贴操作 (Ctrl+V)
            Thread.sleep(100);

            // 定位并点击提交按钮
            WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("oj-submit-btn")));
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            Thread.sleep(6000);

            // 等待content-wrap加载完成
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("content-wrap")));
            Thread.sleep(300);  // 额外等待以确保内容完全加载
            // 定位并点击关闭按钮
            WebElement modalCloseBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-modal-close")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", modalCloseBtn);


        } catch (Exception e) {
            logger.error("=================题目操作失败=================: {}", e.getMessage());
        }
    }

}
