//package geoTest;
//
//import org.locationtech.jts.geom.Coordinate;
//
///**
// * @author lishichao
// * @version 1.0
// * @desc TODO
// * @date 2024/7/1 16:30
// */
//public class PixelToGeoCoordinateConverter {
//
//    // 计算仿射变换参数（斜率和截距）
//
//    private double slopeX, interceptX;
//
//    private double slopeY, interceptY;
//
//
//
//    public PixelToGeoCoordinateConverter(double lon1, double lat1, int px1, int py1,
//
//                                         double lon2, double lat2, int px2, int py2) {
//
//        // 计算X轴的斜率和截距
//
//        slopeX = (lon2 - lon1) / (double)(px2 - px1);
//
//        interceptX = lon1 - slopeX * px1;
//
//
//
//        // 计算Y轴的斜率和截距（注意：Y轴在地理坐标中是纬度，通常是反的）
//
//        slopeY = (lat2 - lat1) / (double)(py2 - py1);
//
//        interceptY = lat1 - slopeY * py1; // 注意：这里假设py2 > py1，否则可能需要调整
//
//    }
//
//
//
//    // 将像素坐标转换为地理坐标
//
//    public Coordinate pixelToGeo(int px, int py) {
//
//        double lon = slopeX * px + interceptX;
//
//        double lat = slopeY * py + interceptY; // 注意：这里的py可能需要调整，因为Y轴方向可能相反
//
//        return new Coordinate(lon, lat);
//
//    }
//
//
//
//    public static void main(String[] args) {
//
//        // 示例坐标，你需要用实际的坐标替换这些值
//
//        double lon1 = 10.0, lat1 = 50.0;
//        int px1 = 100, py1 = 100;
//
//        double lon2 = 11.0, lat2 = 49.0;
//        int px2 = 200, py2 = 200;
//
//
//
//        PixelToGeoCoordinateConverter converter = new PixelToGeoCoordinateConverter(lon1, lat1, px1, py1, lon2, lat2, px2, py2);
//
//
//
//        int px = 150; // 示例像素X坐标
//
//        int py = 150; // 示例像素Y坐标
//
//        Coordinate geoCoord = converter.pixelToGeo(px, py);
//
//        System.out.println("Geographic Coordinate: (" + geoCoord.x + ", " + geoCoord.y + ")");
//
//    }
//
//}
