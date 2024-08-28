import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import java.util.Map;

public class JavaCV3 {
    public static void main(String[] args) {
        String videoFilePath = "https://minio-api.csnd.com/ai-flow-dataset-img-test/Nzk3X2h1bmhld2Vuamlhbl9odW5oZXdlbmppYW4=_aHVuaGV3ZW5qaWFu_YmFjN2ZlODY4OGNlYjJjZDYxMTk2YzY0MDllNjhhMTE=.mp4";
//        String videoFilePath = "https://minio-api.csnd.com/ai-flow-dataset-img-test/NzgzX3Rlc3RiX3Rlc3RiVjEuMC4x_dGVzdGI=_M2IxYi01LW5leHR0b2tlbl/lia/mnKw=.mp4";

        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);

            grabber.start();
            // 获取视频宽度
            int width = grabber.getImageWidth();
            // 获取视频高度
            int height = grabber.getImageHeight();
            System.out.println("分辨率：" + width+"x"+height);
            // 获取元数据
            Map<String, String> metadata = grabber.getVideoMetadata();
            String rotate = metadata.getOrDefault("rotate", "0");
            System.out.println("rotate:" + rotate);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}