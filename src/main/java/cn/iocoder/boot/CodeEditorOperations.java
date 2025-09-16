package cn.iocoder.boot;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * 代码编辑器操作类
 * 专门处理代码编辑器相关的操作
 */
public class CodeEditorOperations {

    private static final Logger logger = LoggerFactory.getLogger(CodeEditorOperations.class);

    // 内容相关选择器
    public static final String VIEW_LINES = "view-lines";
    public static final String CONTENT_WRAP = "content-wrap";
    // 按钮相关选择器
    public static final String SUBMIT_BTN_ID = "oj-submit-btn";
    public static final String BTN_MODAL_CLOSE = "btn-modal-close";
    // 等待时间常量（毫秒）
    public static final int WAIT_SHORT = 300;
    public static final int WAIT_SUBMIT = 6000;
    public static final int 等待超时时间秒 = 3;



    /**
     * 在代码编辑器中粘贴代码
     */
    public static void 粘贴代码到编辑器(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("开始在代码编辑器中粘贴代码");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        Actions actions = new Actions(driver);
        WebElement codeEditorArea = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(VIEW_LINES)));
        actions.moveToElement(codeEditorArea).click().perform();
        UIOperations.safeSleep(WAIT_SHORT);
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        UIOperations.safeSleep(WAIT_SHORT);
        actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
        UIOperations.safeSleep(WAIT_SHORT);
        logger.info("代码粘贴完成");
    }

    /**
     * 提交代码
     */
    public static void 提交代码(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("开始提交代码");
        UIOperations.jsClickById(driver, SUBMIT_BTN_ID, timeoutSeconds);
        UIOperations.safeSleep(WAIT_SUBMIT);
        logger.info("代码提交完成");
    }

    /**
     * 关闭提交结果模态框
     */
    public static void 关闭提交结果模态框(EdgeDriver driver, int timeoutSeconds) throws Exception {
        logger.info("等待提交结果并关闭模态框");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(CONTENT_WRAP)));
        UIOperations.safeSleep(WAIT_SHORT);
        UIOperations.jsClickByClassName(driver, BTN_MODAL_CLOSE, timeoutSeconds);
        logger.info("提交结果模态框已关闭");
        // 查询编译结果
        try {
            WebElement resultContainer = driver.findElement(By.className("result-cntr"));
            String resultText = resultContainer.getText();
            if (resultText.contains("错误")) {
                logger.info("答案或编译错误");
                LogGenerate.记录判题结果("答案或编译错误");
            } else {
                logger.info("通过测试");
                LogGenerate.记录判题结果("通过测试");
            }
        } catch (Exception ex) {
            logger.warn("未找到编译结果区域(result-cntr)，可能页面结构有变");
            LogGenerate.记录判题结果("未找到编译结果区域(result-cntr)");
        }
    }
}
