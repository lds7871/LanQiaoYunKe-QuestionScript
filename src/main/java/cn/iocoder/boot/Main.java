package cn.iocoder.boot;

import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;

/**
 * 自动做题主程序
 * 负责主流程控制和异常处理
 */
public class Main {


    public static void main(String[] args) {
        程序初始化();
        主循环();
    }

    /**
     * 程序初始化
     */
    private static void 程序初始化() {
        LogGenerate.记录程序启动();
        LogGenerate.自定义记录("程序架构优化完成，开始执行自动做题任务");
    }

    /**
     * 主循环 - 持续处理题目
     */
    private static void 主循环() {
        while (true) {
            try {
                处理单个题目();
            } catch (Exception e) {
                LogGenerate.自定义记录("主循环发生意外错误，继续执行下一题: " + e.getMessage());
            }
        }
    }

    /**
     * 处理单个题目的完整流程
     */
    private static void 处理单个题目() {
        题目信息 题目 = new 题目信息();

        try {
            // 1. 初始化题目信息
            初始化题目信息(题目);
            // 2. 启动浏览器和登录
            启动浏览器并登录(题目);
            // 3. 执行做题流程
            执行做题流程(题目);
            // 4. 标记成功
            题目.操作成功 = true;
        } catch (SolutionOperations.NoSolutionFoundException e) {
            处理未找到题解异常(题目);
        } catch (SolutionOperations.CopyButtonNotFoundException e) {
            处理未找到复制按钮异常(题目);
        } catch (Exception e) {
            处理通用异常(题目, e);
        } finally {
            清理资源并完成(题目);
        }
    }

    /**
     * 初始化题目信息
     */
    private static void 初始化题目信息(题目信息 题目) {
        ArrayList<String> 配置内容 = ReadConfig.ConfigRead("config.txt");
        题目.当前题目ID = 配置内容.get(3);
        题目.题目链接 = "https://www.lanqiao.cn/problems/" + 配置内容.get(3) + "/learning/";
        题目.下一题号 = Integer.parseInt(配置内容.get(3)) + 1;
        题目.配置内容 = 配置内容;

        LogGenerate.记录开始(题目.当前题目ID, 题目.题目链接);
    }

    /**
     * 启动浏览器并登录
     */
    private static void 启动浏览器并登录(题目信息 题目) {
        题目.浏览器驱动 = OpenEdge.openEdge(
            题目.题目链接,
            题目.配置内容.get(0),
            题目.配置内容.get(1),
            题目.配置内容.get(2)
        );

        LogGenerate.记录浏览器启动(true, 题目.配置内容.get(2));
        LogGenerate.记录登录(true, 题目.配置内容.get(0));
    }

    /**
     * 执行做题流程
     */
    private static void 执行做题流程(题目信息 题目) {
        Testput.自动做题流程(题目.浏览器驱动);
    }

    /**
     * 处理未找到题解异常
     */
    private static void 处理未找到题解异常(题目信息 题目) {
        LogGenerate.自定义记录("未找到题解，跳过本题，进入下一题 - 题目ID: " + 题目.当前题目ID);
        if (题目.浏览器驱动 != null) {
            EndEdge.closeDriver(题目.浏览器驱动);
        }
        AddConfig.modifyFourthLine("config.txt", 题目.下一题号);
    }

    /**
     * 处理未找到复制按钮异常
     */
    private static void 处理未找到复制按钮异常(题目信息 题目) {
        LogGenerate.自定义记录("未找到复制按钮，跳过本题，进入下一题 - 题目ID: " + 题目.当前题目ID);
        // driver已经在SolutionOperations中关闭，不需要再关闭
        题目.浏览器驱动 = null;
        AddConfig.modifyFourthLine("config.txt", 题目.下一题号);
    }

    /**
     * 处理通用异常
     */
    private static void 处理通用异常(题目信息 题目, Exception 异常) {

        if (是版本不兼容错误(异常)) {
            处理版本不兼容错误(异常);
        } else if (是无需记录的异常(异常)) {
            // 不记录到日志的异常类型，直接跳过
            LogGenerate.自定义记录("此ID无对应题目或未找到复制按钮，已忽略 - 题目ID: " + 题目.当前题目ID);
        } else {
            // 其他未知异常，记录基本信息但不记录详细堆栈
            LogGenerate.自定义记录("处理题目时发生异常 - 题目ID: " + 题目.当前题目ID + ", 异常类型: " + 异常.getClass().getSimpleName());
        }
    }

    /**
     * 判断是否为版本不兼容错误
     */
    private static boolean 是版本不兼容错误(Exception 异常) {
        return 异常.getMessage() != null &&
               异常.getMessage().contains("This version of Microsoft Edge WebDriver only supports");
    }

    /**
     * 判断是否为无需记录的异常
     */
    private static boolean 是无需记录的异常(Exception 异常) {
        if (异常.getMessage() == null) return false;

        return 异常.getClass().getName().contains("NoSuchSessionException") ||
               (异常 instanceof RuntimeException && 异常.getMessage().contains("自动做题流程执行失败")) ||
               (异常 instanceof RuntimeException && 异常.getMessage().contains("未找到复制按钮"));
    }

    /**
     * 处理版本不兼容错误
     */
    private static void 处理版本不兼容错误(Exception 异常) {
        String 错误信息 = 异常.getMessage();
        if (错误信息.contains("supports Microsoft Edge version") && 错误信息.contains("Current browser version is")) {
            String 支持版本 = 提取版本号(错误信息, "supports Microsoft Edge version ");
            String 当前版本 = 提取版本号(错误信息, "Current browser version is ");
            LogGenerate.记录版本不兼容(当前版本, 支持版本);
        }
    }

    /**
     * 清理资源并完成题目处理
     */
    private static void 清理资源并完成(题目信息 题目) {
        // 清理浏览器资源
        if (题目.浏览器驱动 != null) {
            try {
                EndEdge.closeDriver(题目.浏览器驱动);
            } catch (Exception ignore) {
                // 忽略关闭时的异常
            }
        }

        // 更新配置文件
        AddConfig.modifyFourthLine("config.txt", 题目.下一题号);

        // 记录完成状态
        LogGenerate.记录完成(题目.操作成功, 题目.当前题目ID, String.valueOf(题目.下一题号));
    }

    /**
     * 从错误消息中提取版本号
     */
    private static String 提取版本号(String 错误信息, String 前缀) {
        try {
            int 起始 = 错误信息.indexOf(前缀);
            if (起始 != -1) {
                起始 += 前缀.length();
                int 结束 = 错误信息.indexOf(" ", 起始);
                if (结束 == -1) {
                    结束 = 错误信息.indexOf("\n", 起始);
                }
                if (结束 == -1) {
                    结束 = 错误信息.length();
                }
                return 错误信息.substring(起始, 结束).trim();
            }
        } catch (Exception e) {
            // 忽略解析错误
        }
        return "未知";
    }

    /**
     * 题目信息数据类
     * 封装单个题目处理过程中的所有信息
     */
    private static class 题目信息 {
        EdgeDriver 浏览器驱动 = null;
        int 下一题号 = 0;
        String 当前题目ID = "";
        String 题目链接 = "";
        boolean 操作成功 = false;
        ArrayList<String> 配置内容 = null;
    }
}