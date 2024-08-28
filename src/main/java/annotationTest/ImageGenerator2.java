package annotationTest;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.*;

public class ImageGenerator2 {
    static {
        // 加载OpenCV的本地库
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        nu.pattern.OpenCV.loadShared();
         nu.pattern.OpenCV.loadLocally(); // Use in case loadShared() doesn't work

    }
    public static Mat generateImage(int width, int height, Map<Integer, List<List<Point>>> pointMap, Integer type) throws IOException {
        // 创建一个单通道黑色图像
        Mat image = Mat.zeros(new Size(width, height), CvType.CV_8UC1);

        for (Map.Entry<Integer, List<List<Point>>> entry : pointMap.entrySet()) {
            List<List<Point>> pointList = entry.getValue();
            if(type == 1){
                // 绘制矩形
                // 参数：图像、矩形左上角坐标、矩形右下角坐标、颜色、线条粗细（负数表示填充）
                pointList.forEach(e ->{
                    double area = Math.abs((e.get(1).x - e.get(0).x) * (e.get(1).y - e.get(0).y));
                    System.out.println("Rectangle area: " + area);
                    Imgproc.rectangle(image, new Point(e.get(0).x, e.get(0).y), new Point(e.get(1).x, e.get(1).y), new Scalar(entry.getKey()), -1);
                });
            } else {
                pointList.forEach(e ->{

                    // 将点数组转换为MatOfPoint（OpenCV Java的特定数据结构）
                    MatOfPoint matOfPoints = new MatOfPoint(e.toArray(new Point[0]));

                    // 计算多边形面积
                    double area = Imgproc.contourArea(matOfPoints);
                    System.out.println("Polygon area: " + area);

                    // 绘制多边形（填充）
                    // 参数：图像、多边形顶点列表、颜色、线条类型、线条粗细（负数表示填充）
                    Imgproc.fillPoly(image, Arrays.asList(matOfPoints), new Scalar(entry.getKey()));
                });
            }
        }


        return image;
    }

    public static void main(String[] args) throws Exception{
        int width = 1000;
        int height = 800;
//        String s1="35,14,82,53;123,44,197,76;";
//        String s2="69,33,139,85;108,13,165,61;";
//        String s1="307,150,306,151,307,152,307,154,306,155,306,163,304,165,301,165,300,166,294,166,293,167,289,167,288,168,251,168,250,169,245,169,244,170,238,170,237,171,238,172,243,172,244,173,246,173,249,176,249,180,250,181,250,190,249,191,249,192,250,193,250,196,249,197,249,201,248,202,248,203,246,205,241,205,240,204,234,204,232,206,232,207,231,208,231,209,230,210,228,210,227,209,226,209,225,208,224,208,223,207,219,207,218,206,215,206,214,207,214,208,213,209,213,223,212,224,212,229,213,230,213,232,214,233,214,234,215,235,215,236,216,237,216,238,217,239,218,239,220,241,221,241,222,242,223,242,224,243,225,243,226,244,227,244,228,245,229,245,230,246,231,246,232,247,233,247,234,248,236,248,237,249,238,249,239,250,240,250,241,251,242,251,243,252,244,252,245,253,247,253,248,254,249,254,250,255,253,255,254,256,256,256,257,257,261,257,262,258,265,258,266,259,272,259,273,260,278,260,279,261,284,261,285,262,291,262,292,263,299,263,300,264,307,264,308,265,314,265,315,266,325,266,326,267,339,267,340,268,348,268,349,269,353,269,355,271,355,272,356,272,361,267,362,267,363,266,365,266,366,265,367,265,368,264,369,264,370,263,371,263,373,261,374,261,379,256,379,255,382,252,382,251,383,250,383,249,384,248,384,247,389,242,389,241,392,238,392,237,394,235,394,234,396,232,397,232,400,229,400,228,401,227,401,225,402,224,402,214,401,213,401,212,399,210,393,210,392,209,389,209,388,208,387,208,386,209,383,209,382,208,378,208,377,207,374,207,373,206,371,206,370,205,368,205,367,204,363,204,362,203,358,203,357,202,353,202,352,201,351,201,350,200,350,196,349,195,349,189,348,188,348,181,347,180,347,176,345,174,343,174,342,173,336,173,335,172,330,172,329,171,319,171,318,170,317,170,316,169,315,169,311,165,311,162,310,161,310,159,309,158,309,152,308,151,308,150;";
//        String s2="54,369,53,370,46,370,45,371,42,371,41,372,40,372,39,373,39,374,38,375,38,379,40,381,41,381,42,382,54,382,55,383,68,383,69,384,81,384,82,383,85,383,86,382,88,382,90,380,90,379,89,378,89,377,88,377,87,376,86,376,85,375,84,375,83,374,77,374,76,373,73,373,72,372,71,372,70,371,68,371,67,370,61,370,60,369;318,177,316,179,316,181,317,182,317,186,318,187,318,192,319,193,319,198,320,199,342,199,343,200,347,200,348,199,348,192,347,191,347,182,346,181,346,179,344,177,341,177,340,178,328,178,327,177;";
        String s1="536,73,297,25,513,26;";
        String s2="231,97,359,80,244,189,394,188;478,123,458,221,508,187;304,254,425,257,457,259;428,78,425,163,426,163;";
        Map<Integer, List<List<Point>>> pointMap = new LinkedHashMap<>();

        String[] s1Rects = s1.split(";");
        String[] s2Rects = s2.split(";");

        List<List<Point>> poinitList = new ArrayList<>();
        for (String s :s1Rects) {
            String[] coords =s.split(",");
            List<Point> rectPoints = new ArrayList<>();
            for (int i = 0; i< coords.length; i += 2) {
                int x = Integer.parseInt(coords[i]);
                int y = Integer.parseInt(coords[i + 1]);
                Point p = new Point(x,y);
                rectPoints.add(p);
            }
            poinitList.add(rectPoints);
        }
        pointMap.put(2,poinitList);
        List<List<Point>> poinitList2 = new ArrayList<>();
        for (String s :s2Rects) {
            String[] coords =s.split(",");
            List<Point> rectPoints = new ArrayList<>();
            for (int i = 0; i< coords.length; i += 2) {
                int x = Integer.parseInt(coords[i]);
                int y = Integer.parseInt(coords[i + 1]);
                Point p = new Point(x,y);
                rectPoints.add(p);
            }
            poinitList2.add(rectPoints);
        }
        pointMap.put(1,poinitList2);


        Mat image = generateImage(width, height, pointMap, 2);
        Imgcodecs.imwrite("/Users/lisc/zgxw/output.png", image);
    }
}
