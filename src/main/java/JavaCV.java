import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.opencv.core.Core;
import org.opencv.videoio.Videoio;

public class JavaCV {
//    static {
//        // 加载OpenCV的本地库（确保在Java项目的运行配置中包含了OpenCV的本地库路径）
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }
    public static void main(String[] args) {
//        String videoFilePath = "/Users/lisc/zgxw/workspace/java/myTest/src/main/resources/test.mp4";
        String videoFilePath = "https://minio-api.csnd.com/dw-biz-support/1718158863_raKENVe9_bW90aW9ucGxhY2VzLmNvbS0wMDQ1LWMwNDU=.mp4";

        String outputFolderPath = "./f1";
        int frameRate = 5;
        String currentDir = System.getProperty("user.dir");
        System.out.println("Default working directory: " + currentDir);
        try {
            // 创建输出文件夹
//            File outputFolder = new File(outputFolderPath);
//            if (!outputFolder.exists()) {
//                outputFolder.mkdir();
//                System.out.println("Output folder created successfully.");
//            }
//
//            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
//            grabber.start();
////            grabber.setFrameRate(frameRate); // 设置帧率
//
//            Java2DFrameConverter converter = new Java2DFrameConverter();
//            Frame frame;
////            int count = 1;  // 从1开始计数
////            while ((frame = grabber.grabFrame()) != null) {
////                if (grabber.getFrameNumber() % Math.round(grabber.getFrameRate() / frameRate) == 0) {
////                    // 将帧转换为 BufferedImage
////                    BufferedImage image = converter.convert(frame);
////                    if (image != null) {
////                        // 保存帧到文件
////                        File outputfile = new File(outputFolder.getAbsolutePath() + "/frame_" + count + ".jpg");
////                        ImageIO.write(image, "jpg", outputfile);
////                        count++;
////                    }
////                }
////            }
////            grabber.stop();
////            System.out.println("Frames extracted successfully.");
//            System.out.println("VideoFrameRate:" + grabber.getVideoFrameRate());
//            // 获取视频总长度（以微秒为单位）
//            long lengthInMicroseconds = grabber.getLengthInTime();
//            // 转换为秒
//            double lengthInSeconds = lengthInMicroseconds / 1000000.0;
//            System.out.println("视频长度（秒）：" + lengthInMicroseconds);
//            System.out.println("总帧数：" + grabber.getVideoFrameRate() * lengthInMicroseconds / 1000000.0);
//            int totalFrames = 0;
//            while (true) {
//                frame = grabber.grabImage();
//                if (frame == null) {
//                    break;
//                }
//                totalFrames++;
//            }
//
//            grabber.stop();
//            System.out.println("Total frames using grabFrame(): " + totalFrames);

            // 创建一个VideoCapture对象并尝试打开视频文件
            VideoCapture capture = new VideoCapture(videoFilePath);

            // 检查视频是否成功打开
            if (!capture.isOpened()) {
                System.out.println("Error opening video stream or file");
                return;
            }

            // 获取视频的总帧数
            int frames = (int) capture.get(Videoio.CAP_PROP_FRAME_COUNT);
            System.out.println("Total number of frames: " + frames);

            // 释放VideoCapture对象
            capture.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}