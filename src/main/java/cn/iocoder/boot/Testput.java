package cn.iocoder.boot;

import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动化做题主要操作类（重构版）
 * 整合了UI操作、题解获取和代码提交功能
 */
public class Testput {

    private static final Logger logger = LoggerFactory.getLogger(Testput.class);

    // 等待时间常量（毫秒）
    public static final int 等待超时时间秒 = 3;

    /**
     * 执行完整的自动做题流程
     * @param driver EdgeDriver实例
     */
    public static void 自动做题流程(EdgeDriver driver) {
        String 题目ID = 获取当前题目ID(); // 获取当前题目ID

        try {
            logger.info("开始自动做题流程，题目ID: {}", 题目ID);

            // 1. 获取题解（包含关闭弹窗、导航、选择语言、复制代码等完整流程）
            SolutionOperations.获取题解完整流程(driver, 题目ID);

            // 2. 在代码编辑器中粘贴代码
            CodeEditorOperations.粘贴代码到编辑器(driver, 等待超时时间秒);

            // 3. 提交代码
            CodeEditorOperations.提交代码(driver, 等待超时时间秒);

            // 4. 关闭提交结果模态框
            CodeEditorOperations.关闭提交结果模态框(driver, 等待超时时间秒);

            logger.info("自动做题流程完成，题目ID: {}", 题目ID);

        } catch (Exception e) {
            // 不再直接记录错误到日志，让Main.java统一处理
            logger.error("自动做题流程失败，题目ID: {}, 错误: {}", 题目ID, e.getMessage());
            throw new RuntimeException("自动做题流程执行失败", e);
        }
    }

    /**
     * 获取当前题目ID（从properties配置读取）
     * @return 当前题目ID
     */
    private static String 获取当前题目ID() {
        try {
            return Config.获取题目ID();
        } catch (Exception e) {
            logger.warn("无法获取题目ID，使用默认值: {}", e.getMessage());
            return "unknown";
        }
    }
}
