import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/5/27 8:59 上午
 */
public class VideoFramTest {
    public static void main(String[] args) {
        try {
            String pythonScriptPath = VideoFramTest.class.getClassLoader().getResource("python/video_fram_script.py").getPath();
            String outputFolder = "frames";
            String videoPath = VideoFramTest.class.getClassLoader().getResource("test.mp4").getPath();
            String pythonScriptDirectory = new File(pythonScriptPath).getParent(); // 获取Python脚本所在目录
            int frameRate = 10; // 设置抽帧速率（每秒10帧）

            // 构建Python脚本命令
            String[] cmd = {
                    "python3",
                    pythonScriptPath,
                    outputFolder,
                    videoPath,
                    String.valueOf(frameRate)
            };

            // 创建进程
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.directory(new File(pythonScriptDirectory));
            String currentDir = System.getProperty("user.dir");
            System.out.println("Default working directory: " + currentDir);
            Process process = pb.start();

            // 读取Python脚本的输出
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line); // 打印Python脚本的输出
            }

            // 等待Python脚本执行完毕
            int returnValue = process.waitFor();
            File[] imageFiles = new File(VideoFramTest.class.getClassLoader().getResource("python/frames").getPath()).listFiles();
            if (imageFiles != null) {
                Arrays.sort(imageFiles);
                for (File file : imageFiles) {
                    System.out.println(file.getName());
                }
            }

            System.out.println("Python script returned: " + returnValue);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
