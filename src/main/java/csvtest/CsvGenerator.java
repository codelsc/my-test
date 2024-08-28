package csvtest;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvGenerator {

    public static void main(String[] args) {
        // 示例数据
        Map<String, List<List<String>>> data = new HashMap<>();
        List<List<String>> coords1 = new ArrayList<>();
        coords1.add(List.of("1", "2"));
        coords1.add(List.of("3", "4"));
        coords1.add(List.of("4", "5"));
        // 添加更多数据...

        List<List<String>> coords2 = new ArrayList<>();
        coords2.add(List.of("5", "3"));
        coords2.add(List.of("53", "4"));
        // 添加更多数据...

        data.put("1.jpg", List.of(List.of("car", "1,2,3,4,4,5,6,7"), List.of("person", "5,3,53,4,34,223")));
        data.put("2.jpg", List.of(List.of("car", "23232,3,434,43")));
//        data.put("\"3.jpg", List.of(List.of("car", "23232,3,434,43")));
        // 注意：这里为了简化，我直接将坐标转换为字符串了，实际中你可能需要处理它们

        // 写入CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter("output2.csv"))) {
            // 写入表头
            writer.writeNext(new String[]{"filename", "class"});
            // 添加足够多的占位符以匹配最长的坐标列表
            List<String> headers = new ArrayList<>();
            headers.add("filename");
            headers.add("class");
            for (int i = 1; i <= 100; i++) { // 假设最长不超过100个坐标
                headers.add("x" + i);
                headers.add("y" + i);
            }
            writer.writeNext(headers.toArray(new String[0])); // 如果不需要这个额外的表头行，可以去掉

            for (Map.Entry<String, List<List<String>>> entry : data.entrySet()) {
                String filename = entry.getKey();
                for (List<String> classAndCoords : entry.getValue()) {
                    String cls = classAndCoords.get(0);
                    String coordsStr = classAndCoords.get(1);
                    List<String> coords = new ArrayList<>();
                    // 分割字符串并转换为坐标列表
                    for (String pair : coordsStr.split(",")) {
                        String[] xy = pair.split("\\s+");
                        if (xy.length == 2) {
                            coords.add(xy[0]);
                            coords.add(xy[1]);
                        }
                    }

                    // 填充缺失的坐标
                    while (coords.size() % 2 != 0) {
                        coords.add("");
                    }

                    // 写入CSV行
                    List<String> row = new ArrayList<>();
                    row.add(filename);
                    row.add(cls);
                    row.addAll(coords);
                    writer.writeNext(row.toArray(new String[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}