package csvtest;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ImageData {
    String filename;
    String clazz;
    List<String[]> coordinates; // 每个String[]包含一对[x, y]

    public ImageData(String filename, String clazz, List<String[]> coordinates) {
        this.filename = filename;
        this.clazz = clazz;
        this.coordinates = coordinates;
    }
}

public class CsvGenerator2 {

    public static void main(String[] args) {
        List<ImageData> images = new ArrayList<>();

        // 添加示例数据
        List<String[]> coords1 = new ArrayList<>();
        coords1.add(new String[]{"1", "2"});
        coords1.add(new String[]{"3", "4"});
        images.add(new ImageData("\"1\\.jpg", "car", coords1));

        List<String[]> coords2 = new ArrayList<>();
        coords2.add(new String[]{"5", "3"});
        coords2.add(new String[]{"53", "4"});
        coords2.add(new String[]{"34", "223"}); // 假设有更多坐标
        images.add(new ImageData("1,2.jpg", "person", coords2));

        // ... 添加更多图像数据

        // 写入CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter("output.csv"))) {
            // 写入表头
            List<String> headers = new ArrayList<>();
            headers.add("filename");
            headers.add("class");
            for (int i = 1; i <= getMaxCoordsCount(images) * 2; i += 2) {
                headers.add("x" + (i / 2 + 1));
                headers.add("y" + (i / 2 + 1));
            }
            writer.writeNext(headers.toArray(new String[0]));

            // 写入数据
            for (ImageData image : images) {
                List<String> row = new ArrayList<>();
                row.add(image.filename);
                row.add(image.clazz);

                // 填充坐标或空字符串
                for (int i = 0; i < getMaxCoordsCount(images) * 2; i += 2) {
                    if (i < image.coordinates.size() * 2) {
                        String[] coord = image.coordinates.get(i / 2);
                        row.add(coord[0]);
                        row.add(coord[1]);
                    } else {
                        row.add("");
                        row.add("");
                    }
                }

                writer.writeNext(row.toArray(new String[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getMaxCoordsCount(List<ImageData> images) {
        int max = 0;
        for (ImageData image : images) {
            if (image.coordinates.size() > max) {
                max = image.coordinates.size();
            }
        }
        return max;
    }
}
