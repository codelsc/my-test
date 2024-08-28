package geoTest;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CreateCanvasShapefile3 {

    public static void main(String[] args) throws Exception {
        // 画布的宽和高（无实际用处，仅作为示例）
        double width = 1000;
        double height = 1000;

        // 矩形框的顶点坐标
        Coordinate[] rectCoordinates = new Coordinate[]{
                new Coordinate(100, 200),
                new Coordinate(300, 200),
                new Coordinate(300, 400),
                new Coordinate(100, 400),
                new Coordinate(100, 200)
        };

        // 多边形的顶点坐标
        Coordinate[] polyCoordinates = new Coordinate[]{
                new Coordinate(400, 500),
                new Coordinate(600, 600),
                new Coordinate(700, 700),
                new Coordinate(500, 800),
                new Coordinate(400, 500)
        };

        // 转换画布坐标到shp文件，生成shp字节数组
        byte[] shapefileBytes = createShapefile(rectCoordinates, polyCoordinates);

        // 将字节数组数据写到.shp文件
        Files.write(new File("shapes2.shp").toPath(), shapefileBytes);
    }

    public static byte[] createShapefile(Coordinate[] rectCoords, Coordinate[] polyCoords) throws IOException {
        // 创建几何工厂
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        // 创建shapefile内存存储
        ShapefileDataStore shapefileDataStore = new ShapefileDataStore(File.createTempFile("temp", ".shp").toURI().toURL());

        // 创建SimpleFeatureType
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("CanvasShapes");
        builder.add("the_geom", Polygon.class);
        SimpleFeatureType type = builder.buildFeatureType();
        shapefileDataStore.createSchema(type);

        // 创建并写入特性
        try (Transaction transaction = new DefaultTransaction("create")) {
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = shapefileDataStore.getFeatureWriterAppend("shapes", transaction);

            // 写入矩形框
            LinearRing rectRing = geometryFactory.createLinearRing(rectCoords);
            Polygon rectangle = geometryFactory.createPolygon(rectRing, null);
            SimpleFeature feature = writer.next();
            feature.setAttribute("the_geom", rectangle);
            writer.write();

            // 写入多边形框
            LinearRing polyRing = geometryFactory.createLinearRing(polyCoords);
            Polygon polygon = geometryFactory.createPolygon(polyRing, null);
            feature = writer.next();
            feature.setAttribute("the_geom", polygon);
            writer.write();

            transaction.commit();
        }
        System.out.println(shapefileDataStore);
        return null;
    }
}
