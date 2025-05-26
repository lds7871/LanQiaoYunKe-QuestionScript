package cn.iocoder.boot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddConfig {

    public static void modifyFourthLine(String filePath, int newContent) {
        List<String> lines = new ArrayList<>();
        int lineNumber = 0;

        // 读取文件内容
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 4) {
                    lines.add(String.valueOf(newContent)); // 修改第四行
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("读取配置文件时发生错误: " + e.getMessage());
            return;
        }

        // 写回文件
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("写入配置文件时发生错误: " + e.getMessage());
        }
    }
}
