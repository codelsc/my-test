package annotationTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageGenerator {

    /**
     * 创建一个灰度图像，并根据提供的像素点集合设置像素值。
     *
     * @param width      图像的宽度
     * @param height     图像的高度
     * @param pixelMap   像素点集合，键是像素索引（如x*width+y），值是灰度值
     * @param outputFile 输出文件的路径
     * @throws IOException 如果无法写入文件
     */
    public static void generateImage(int width, int height, Map<Integer, Integer> pixelMap, String outputFile) throws IOException {
        // 创建一个灰度图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // 获取图像的WritableRaster对象，以便操作像素数据
        WritableRaster raster = image.getRaster();
        byte[] data = ((byte[][]) raster.getDataElements(0, 0, width, height, null))[0];

        // 遍历像素点集合，设置图像中的像素值
        for (Map.Entry<Integer, Integer> entry : pixelMap.entrySet()) {
            int index = entry.getKey();
            int pixelValue = Math.min(255, entry.getValue());

            // 确保索引在图像范围内
            if (index >= 0 && index < width * height) {
                data[index] = (byte) pixelValue;
            } else {
                System.err.println("忽略无效的像素索引: " + index);
            }
        }

        // 保存图像到文件
        ImageIO.write(image, "png", new File(outputFile));
    }

    public static void main(String[] args) {
        int width = 1000;
        int height = 800;

        // 示例像素点集合
        Map<Integer, Integer> pixelMap = Map.of(
                100 * width + 50, 1, // 像素点(100, 50)的灰度值为1
                200 * width + 60, 2  // 像素点(200, 60)的灰度值为2
                // ... 可以添加更多像素点
        );

        String outputFile = "output.png";

        try {
            generateImage(width, height, pixelMap, outputFile);
            System.out.println("图像已保存到: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
