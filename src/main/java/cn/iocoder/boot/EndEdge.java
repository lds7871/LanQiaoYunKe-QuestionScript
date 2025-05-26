package cn.iocoder.boot;

import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndEdge {
    private static final Logger logger = LoggerFactory.getLogger(EndEdge.class);

    public static void closeDriver(EdgeDriver driver) {
        try {
            if (driver != null) {
                Thread.sleep(300); // 等待一秒确保所有操作完成
                driver.quit();      // 关闭浏览器并结束WebDriver会话
                logger.info("浏览器已成功关闭");
            }
        } catch (Exception e) {
            logger.error("关闭浏览器时发生错误: ", e);
            throw new RuntimeException("关闭浏览器失败", e);
        }
    }
}