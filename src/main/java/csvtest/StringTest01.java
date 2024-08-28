package csvtest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/7/9 20:09
 */
public class StringTest01 {
    public static List<String> csvSplit(String input) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?:\"(?:[^\"]|\\\")*\"|[^,]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // 移除可能匹配到的引号
            String match = matcher.group().replaceAll("^\"|\"$", "");
            result.add(match);
        }

        return result;
    }

    public static List<String> csvSplit2(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder currentSegment = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '"') {
                insideQuotes = !insideQuotes;
                currentSegment.append(currentChar);
            } else if (currentChar == ',' && !insideQuotes) {
                result.add(currentSegment.toString().trim());
                currentSegment.setLength(0);  // 清空当前段落
            } else {
                currentSegment.append(currentChar);
            }
        }

        // 将最后一个段落添加到结果中
        if (currentSegment.length() > 0) {
            result.add(currentSegment.toString().trim());
        }

        return result;
    }

    public static void main(String[] args) {
        String input = "\"6,\"\"\"\"\\b\"\"\"\"c,c.png\",pizza,663,106,403,124,297,289,281,502,403,653,677,719";
        List<String> parts = csvSplit2(input);

        for (String part : parts) {
            System.out.println(part);
        }
    }
}
