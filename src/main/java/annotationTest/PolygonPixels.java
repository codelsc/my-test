package annotationTest;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class PolygonPixels {

    // 判断点是否在多边形内部（奇偶规则）
    public static boolean isPointInPolygon(Point p, List<Point> polygon) {
        int crossings = 0;
        int j = polygon.size() - 1;

        for (int i = 0; i < polygon.size(); i++) {
            if (polygon.get(i).y < p.y && polygon.get(j).y >= p.y || polygon.get(j).y < p.y && polygon.get(i).y >= p.y) {
                if (polygon.get(i).x + (p.y - polygon.get(i).y) / (polygon.get(j).y - polygon.get(i).y) * (polygon.get(j).x - polygon.get(i).x) < p.x) {
                    crossings++;
                }
            }
            j = i;
        }

        return (crossings % 2) == 1;
    }

    // 获取多边形内的所有像素点
    public static Set<Point> getAllPolygonPixels(List<Point> polygon, int minX, int minY, int maxX, int maxY) {
        Set<Point> pixels = new HashSet<>();

        // 假设多边形的第一个点是多边形内部的一个点
        // 如果不是，需要找到一个在多边形内部的点作为起始点
        Point startPoint = polygon.get(0); // 假设多边形第一个点可用作起点

        // 使用边界填充算法（这里简化为广度优先搜索）
        Queue<Point> queue = new LinkedList<>();
        queue.add(startPoint);
        pixels.add(startPoint);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            // 检查当前点的上、下、左、右四个邻居
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    System.out.println(dx);
                    if ((dx == 0 && dy == 0) || (dx != 0 && dy != 0)) {
                        continue; // 跳过点本身和对角线上的点
                    }

                    int newX = current.x + dx;
                    int newY = current.y + dy;

                    // 检查新点是否在多边形内部且在边界范围内
                    if (newX >= minX && newX <= maxX && newY >= minY && newY <= maxY &&
                            isPointInPolygon(new Point(newX, newY), polygon)) {
                        pixels.add(new Point(newX, newY));
                        queue.add(new Point(newX, newY));
                    }
                }
            }
        }

        return pixels;
    }

    public static void main(String[] args) {
        // 示例多边形（顺时针或逆时针顺序）
        List<Point> polygon = new LinkedList<>();
        polygon.add(new Point(0, 0));
        polygon.add(new Point(3, 0));
        polygon.add(new Point(3, 3));
        polygon.add(new Point(0, 3));

        // 边界范围（这里简单地设置为包含多边形的最小矩形）
        int minX = 0, minY = 0, maxX = 3, maxY = 3;

        // 获取多边形内的所有像素点
        Set<Point> allPixels = getAllPolygonPixels(polygon, minX, minY, maxX, maxY);

        // 打印所有像素点
        for (Point p : allPixels) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }
}