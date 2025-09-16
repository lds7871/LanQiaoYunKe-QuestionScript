package cn.iocoder.boot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class SolutionOperations {
    private static final Logger logger = LoggerFactory.getLogger(SolutionOperations.class);

    // 按钮相关选择器
    public static final String BTN_CLOSE = "btn-close";
    public static final String CLOSE_BTN = "close-btn";
    // 题解相关选择器
    public static final String SOLUTION_TEXT_XPATH = "//*[contains(text(),'题解')]";
    public static final String CPP_LANGUAGE_XPATH = "//span[contains(text(),'C/C++')]";
    public static final String NOTE_TITLE = "note-title";
    // 内容相关选择器
    public static final String CONTENT = "content";
    // JavaScript脚本
    public static final String COPY_BUTTON_SCRIPT = "return document.querySelector('.content').querySelector('.icon-copy')";
    // 等待时间常量（毫秒）
    public static final int WAIT_SHORT = 300;
    public static final int WAIT_MEDIUM = 500;
    public static final int WAIT_LONG = 1000;
    public static final int WAIT_VERY_LONG = 2000;
    public static final int WAIT_PAGE_LOAD = 4000;
    public static final int WAIT_TIMEOUT_SECONDS = 3;

    /**
     * 关闭初始弹窗并进入题解页面
     */
    public static void 关闭初始弹窗(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("关闭初始弹窗");
        UIOperations.waitAndClickByClassName(driver, BTN_CLOSE, timeoutSeconds);
        UIOperations.safeSleep(WAIT_PAGE_LOAD);
        logger.info("初始弹窗已关闭，页面加载完成");
    }

    /**
     * 导航到题解页面
     */
    public static void 导航到题解页面(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("导航到题解页面");
        UIOperations.jsClickByXPath(driver, SOLUTION_TEXT_XPATH, timeoutSeconds);
        UIOperations.safeSleep(WAIT_SHORT);
        logger.info("已进入题解页面");
    }

    /**
     * 选择C/C++语言
     */
    public static void 选择Cpp语言(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("选择C/C++语言");
        UIOperations.jsClickByXPath(driver, CPP_LANGUAGE_XPATH, timeoutSeconds);
        UIOperations.safeSleep(WAIT_LONG);
        logger.info("已选择C/C++语言");
    }

    /**
     * 选择第一个题解
     * 如果未找到题解，直接终止本次循环，进入下一题
     */
    public static void 选择第一个题解(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("选择第一个题解");
        try {
            UIOperations.jsClickByClassName(driver, NOTE_TITLE, timeoutSeconds);
            UIOperations.safeSleep(WAIT_VERY_LONG);
            logger.info("已选择第一个题解");
        } catch (Exception e) {
            logger.error("未找到题解，流程终止");
            LogGenerate.记录题解获取(false, "未找到题解");
            // 直接关闭浏览器
            if (driver != null) {
                try { driver.quit(); } catch (Exception ignore) {}
            }
            driver = null; // 关键：让主流程 finally 不再关闭
            // 抛出自定义异常，主流程捕获后直接continue进入下一题
            throw new NoSolutionFoundException("未找到题解，流程终止");
        }
    }

    /**
     * 复制题解代码
     */
    public static void 复制题解代码(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("开始复制题解代码");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(CONTENT)));
        WebElement copyButton = (WebElement) ((JavascriptExecutor) driver).executeScript(COPY_BUTTON_SCRIPT);
        if (copyButton == null) {
            logger.error("未找到复制按钮，流程终止");
            LogGenerate.自定义记录("未找到复制按钮，流程终止");
            // 直接关闭浏览器
            if (driver != null) {
                try { driver.quit(); } catch (Exception ignore) {}
            }
            // 抛出自定义异常，让主流程知道driver已经关闭
            throw new CopyButtonNotFoundException("未找到复制按钮，已关闭浏览器");
        }
        UIOperations.scrollToElement(driver, copyButton);
        UIOperations.safeSleep(WAIT_MEDIUM);
        UIOperations.hoverAndClick(driver, copyButton);
        UIOperations.safeSleep(WAIT_SHORT);
        logger.info("题解代码复制完成");
    }

    /**
     * 关闭题解详情页面
     */
    public static void 关闭题解详情页面(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("关闭题解详情页面");
        UIOperations.jsClickByClassName(driver, CLOSE_BTN, timeoutSeconds);
        UIOperations.safeSleep(WAIT_SHORT);
        logger.info("题解详情页面已关闭");
    }

    /**
     * 完整的题解获取流程
     */
    public static void 获取题解完整流程(EdgeDriver driver, String problemId) throws Exception {
        logger.info("开始完整的题解获取流程，题目ID: {}", problemId);
        try {
            int timeoutSeconds = WAIT_TIMEOUT_SECONDS;
            关闭初始弹窗(driver, timeoutSeconds);
            导航到题解页面(driver, timeoutSeconds);
            选择Cpp语言(driver, timeoutSeconds);
            选择第一个题解(driver, timeoutSeconds);
            复制题解代码(driver, timeoutSeconds);
            关闭题解详情页面(driver, timeoutSeconds);
            logger.info("题解获取流程完成，题目ID: {}", problemId);
        } catch (Exception e) {
            logger.error("题解获取失败，题目ID: {}, 错误: {}", problemId, "");
//            throw e;
        }
    }

    // 自定义异常类，放在文件末尾
    public static class NoSolutionFoundException extends RuntimeException {
        public NoSolutionFoundException(String message) {
            super(message);
        }
    }

    // 新增：复制按钮未找到异常
    public static class CopyButtonNotFoundException extends RuntimeException {
        public CopyButtonNotFoundException(String message) {
            super(message);
        }
    }
}
