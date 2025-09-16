package cn.iocoder.boot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * UI操作工具类
 * 提供通用的页面元素交互方法
 */
public class UIOperations {

    private static final Logger logger = LoggerFactory.getLogger(UIOperations.class);

    // 按钮相关选择器
    public static final String BTN_CLOSE = "btn-close";
    public static final String CLOSE_BTN = "close-btn";
    public static final String BTN_MODAL_CLOSE = "btn-modal-close";
    public static final String SUBMIT_BTN_ID = "oj-submit-btn";

    // 题解相关选择器
    public static final String SOLUTION_TEXT_XPATH = "//*[contains(text(),'题解')]";
    public static final String CPP_LANGUAGE_XPATH = "//span[contains(text(),'C/C++')]";
    public static final String NOTE_TITLE = "note-title";

    // 内容相关选择器
    public static final String CONTENT = "content";
    public static final String CONTENT_WRAP = "content-wrap";
    public static final String VIEW_LINES = "view-lines";

    // JavaScript脚本
    public static final String COPY_BUTTON_SCRIPT = "return document.querySelector('.content').querySelector('.icon-copy')";
    public static final String SCROLL_TO_ELEMENT_SCRIPT = "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});";
    public static final String CLICK_ELEMENT_SCRIPT = "arguments[0].click();";

    // 等待时间常量（毫秒）
    public static final int WAIT_SHORT = 300;
    public static final int WAIT_MEDIUM = 500;
    public static final int WAIT_LONG = 1000;
    public static final int WAIT_VERY_LONG = 2000;
    public static final int WAIT_PAGE_LOAD = 4000;
    public static final int WAIT_SUBMIT = 6000;
    public static final int WAIT_TIMEOUT_SECONDS = 3;

    /**
     * 等待并点击元素（使用className定位）
     */
    public static void waitAndClickByClassName(EdgeDriver driver, String className, int timeoutSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className(className)));
        element.click();
        logger.debug("成功点击元素: {}", className);
    }

    /**
     * 等待并点击元素（使用XPath定位）
     */
    public static void waitAndClickByXPath(EdgeDriver driver, String xpath, int timeoutSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        logger.debug("成功点击元素: {}", xpath);
    }

    /**
     * 使用JavaScript点击元素（用于处理某些难以点击的元素）
     */
    public static void jsClickByClassName(EdgeDriver driver, String className, int timeoutSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript(CLICK_ELEMENT_SCRIPT, element);
        logger.debug("使用JS成功点击元素: {}", className);
    }

    /**
     * 使用JavaScript点击元素（使用XPath定位）
     */
    public static void jsClickByXPath(EdgeDriver driver, String xpath, int timeoutSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript(CLICK_ELEMENT_SCRIPT, element);
        logger.debug("使用JS成功点击元素: {}", xpath);
    }

    /**
     * 使用JavaScript点击元素（使用ID定位）
     */
    public static void jsClickById(EdgeDriver driver, String id, int timeoutSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript(CLICK_ELEMENT_SCRIPT, element);
        logger.debug("使用JS成功点击元素ID: {}", id);
    }

    /**
     * 等待元素出现
     */
    public static WebElement waitForElement(EdgeDriver driver, String className, int timeoutSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        logger.debug("成功找到元素: {}", className);
        return element;
    }

    /**
     * 滚动到元素位置
     */
    public static void scrollToElement(EdgeDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(SCROLL_TO_ELEMENT_SCRIPT, element);
        logger.debug("已滚动到元素位置");
    }

    /**
     * 使用Actions进行鼠标悬停并点击
     */
    public static void hoverAndClick(EdgeDriver driver, WebElement element) throws Exception {
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .pause(Duration.ofMillis(WAIT_MEDIUM))
                .click()
                .perform();
        logger.debug("成功执行悬停并点击操作");
    }

    /**
     * 安全等待（捕获异常的Thread.sleep）
     */
    public static void safeSleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("等待被中断: {}", e.getMessage());
        }
    }
}
