import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class JavaCV2 {
//    static {
//        // 加载OpenCV的本地库（确保在Java项目的运行配置中包含了OpenCV的本地库路径）
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }
    public static void main(String[] args) {
//        String videoFilePath = "/Users/lisc/zgxw/workspace/java/myTest/src/main/resources/test.mp4";
        String videoFilePath = "https://minio-api.csnd.com/ai-flow-dataset-img-test/NzgzX3Rlc3RiX3Rlc3RiVjEuMC4x_dGVzdGI=_M2IxYi01LW5leHR0b2tlbl/lia/mnKw=.mp4";

        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
            grabber.start();
            Frame frame;
            // 获取视频总长度（以微秒为单位）
            long lengthInMicroseconds = grabber.getLengthInTime();
            // 转换为秒
            double lengthInSeconds = lengthInMicroseconds / 1000000.0;
            System.out.println("视频长度（秒）：" + lengthInMicroseconds);
            System.out.println("帧率：" + grabber.getVideoFrameRate());
            System.out.println("总帧数：" + grabber.getVideoFrameRate() * lengthInMicroseconds / 1000000.0);
            System.out.println("总帧数2：" + grabber.getLengthInFrames());
            System.out.println("总帧数3：" + 30 * lengthInMicroseconds / 1000000.0);

            int totalFrames = 0;
            while (true) {
                frame = grabber.grabImage();
                if (frame == null) {
                    break;
                }
                totalFrames++;
            }

            grabber.stop();
            System.out.println("Total frames using grabFrame(): " + totalFrames);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}