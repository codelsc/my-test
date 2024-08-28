import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/5/27 11:32 上午
 */
public class Test1 {
    public static void main(String[] args) {
        String pythonScriptPath = "/Users/lisc/zgxw/workspace/java/myTest/src/main/java/test1.py";
        String[] cmd = new String[2];
        cmd[0] = "python3"; // 执行python命令
        cmd[1] = pythonScriptPath;

        try {
            // 创建一个新进程执行python脚本
            Process p = Runtime.getRuntime().exec(cmd);
            // 从进程中获取输出
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Python脚本执行完毕.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
