package cn.iocoder.boot;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日志更新工具类
 * 用于记录每次做题的状态和详细信息到按日期命名的日志文件
 */
public class LogGenerate {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 获取当天的日志文件路径
     * @return 当天的日志文件路径，格式为log2025-09-16.txt
     */
    private static String getLogFilePath() {
        String dateStr = LocalDateTime.now().format(FILE_DATE_FORMAT);
        return "log" + dateStr + ".txt";
    }

    /**
     * 记录开始做题的日志
     */
    public static void 记录开始(String problemId, String problemUrl) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] 开始处理题目 ID:%s - URL:%s", timestamp, problemId, problemUrl);
        appendToLogFile(logMessage);
    }

    /**
     * 记录登录状态
     */
    public static void 记录登录(boolean success, String phone) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String status = success ? "成功" : "失败";
        String logMessage = String.format("[%s] 登录%s - 手机号:%s", timestamp, status, phone);
        appendToLogFile(logMessage);
    }

    /**
     * 记录浏览器启动状态
     */
    public static void 记录浏览器启动(boolean success, String driverPath) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String status = success ? "成功" : "失败";
        String logMessage = String.format("[%s] 浏览器启动%s - 驱动路径:%s", timestamp, status, driverPath);
        appendToLogFile(logMessage);
    }

    /**
     * 记录题解获取状态
     */
    public static void 记录题解获取(boolean success, String detail) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String status = success ? "成功" : "失败";
        String logMessage = String.format("[%s] 题解获取%s %s", timestamp, status, detail != null ? detail : "");
        appendToLogFile(logMessage);
    }

    /**
     * 记录代码提交状态
     */
    public static void 记录代码提交(boolean success, String problemId) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String status = success ? "成功" : "失败";
        String logMessage = String.format("[%s] 代码提交%s %s", timestamp, status, problemId);
        appendToLogFile(logMessage);
    }

    /**
     * 记录完成状态
     */
    public static void 记录完成(boolean success, String problemId, String nextProblemId) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String status = success ? "成功完成" : "执行失败";
        String logMessage = String.format("[%s] 题目处理%s - 当前题目ID:%s, 下一题目ID:%s", timestamp, status, problemId, nextProblemId);
        appendToLogFile(logMessage);
        appendToLogFile("----------------------------------------");
    }

    /**
     * 记录错误信息
     */
    public static void 记录错误(String errorMessage, String problemId, Exception exception) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] 错误 - 题目ID:%s - %s", timestamp, problemId," ");
        appendToLogFile(logMessage);
        if (exception != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String stackTrace = sw.toString();
            String[] lines = stackTrace.split("\n");
            for (int i = 0; i < Math.min(3, lines.length); i++) {
                appendToLogFile("  " + lines[i]);
            }
        }
    }

    /**
     * 记录浏览器版本不兼容错误
     */
    public static void 记录版本不兼容(String currentVersion, String supportedVersion) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] 浏览器版本不兼容 - 当前版本:%s, 支持版本:%s", timestamp, currentVersion, supportedVersion);
        appendToLogFile(logMessage);
        appendToLogFile("建议: 请更新EdgeDriver版本或降级Edge浏览器版本");
    }

    /**
     * 记录编译/判题结果
     */
    public static void 记录判题结果(String result) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] 判题结果: %s", timestamp, result);
        appendToLogFile(logMessage);
    }

    /**
     * 清空当天的日志文件
     */
    public static void 记录清空日志() {
        String logFilePath = getLogFilePath();
        try (FileWriter writer = new FileWriter(logFilePath, false)) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("清空日志文件失败: " + e.getMessage());
        }
    }

    /**
     * 记录程序启动信息
     */
    public static void 记录程序启动() {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        appendToLogFile("========================================");
        appendToLogFile(String.format("[%s] 自动做题程序启动", timestamp));
        appendToLogFile("========================================");
    }

    /**
     * 记录程序结束信息
     */
    public static void 记录程序结束() {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        appendToLogFile("========================================");
        appendToLogFile(String.format("[%s] 自动做题程序结束", timestamp));
        appendToLogFile("========================================");
    }

    /**
     * 自定义记录
     * 可用于记录任意自定义内容到日志
     * @param message 自定义日志内容
     */
    public static void 自定义记录(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] %s", timestamp, message);
        appendToLogFile(logMessage);
    }

    /**
     * 将日志消息追加到日志文件
     * @param message 日志消息
     */
    private static void appendToLogFile(String message) {
        String logFilePath = getLogFilePath();
        try (FileWriter writer = new FileWriter(logFilePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            System.err.println("写入日志文件失败: " + e.getMessage());
        }
    }
}
