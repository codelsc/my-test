package annotationTest;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RectanglePixels {

    // 一个简单的类来表示矩形框，包含左上角和右下角的点
    static class Rectangle {
        Point topLeft;
        Point bottomRight;

        Rectangle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
            this.topLeft = new Point(topLeftX, topLeftY);
            this.bottomRight = new Point(bottomRightX, bottomRightY);
        }

        // 获取矩形框内的所有像素点
        Set<Point> getAllPixels() {
            Set<Point> pixels = new HashSet<>();
            for (int x = topLeft.x; x <= bottomRight.x; x++) {
                for (int y = topLeft.y; y <= bottomRight.y; y++) {
                    pixels.add(new Point(x, y));
                }
            }
            return pixels;
        }
    }

    // 获取所有矩形框内的所有像素点（不重复）
    public static Set<Point> getAllRectanglePixels(List<Rectangle> rectangles) {
        Set<Point> allPixels = new HashSet<>();
        for (Rectangle rect : rectangles) {
            allPixels.addAll(rect.getAllPixels());
        }
        return allPixels;
    }

    public static void main(String[] args) {
        // 示例矩形框
        List<Rectangle> rectangles = Arrays.asList(new Rectangle(0, 0, 2, 2),
                new Rectangle(1, 1, 3, 3),
                new Rectangle(4, 4, 5, 5));


        // 获取所有矩形框内的所有像素点
        Set<Point> allPixels = getAllRectanglePixels(rectangles);

        // 打印所有像素点
        for (Point p : allPixels) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }
}
