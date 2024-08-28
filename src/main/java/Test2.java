import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/5/27 2:51 下午
 */
public class Test2 {
    public static void main(String[] args) {
        // Python 脚本的路径（可以是相对路径或绝对路径）
        String pythonScriptPath = "/Users/lisc/zgxw/workspace/java/myTest/src/main/java/test1.py"; // 替换为你的 Python 脚本路径

        // 创建 ProcessBuilder 对象，并设置要执行的命令
        // 注意：在 macOS 上，Python 的命令可能是 "python" 或 "python3"，取决于你的安装和配置
        ProcessBuilder pb = new ProcessBuilder("python3", pythonScriptPath); // 使用 python3

        try {
            // 启动进程
            Process process = pb.start();

            // 读取 Python 脚本的标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待 Python 脚本执行完毕
            int exitCode = process.waitFor();
            System.out.println("\nPython 脚本退出码: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
