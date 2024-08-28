package geoTest;

import org.geotools.data.*;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.simple.SimpleFeatureBuilder;
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
import java.util.HashMap;
import java.util.Map;

public class CreateCanvasShapefile2 {

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
        ByteArrayDataStore memoryDataStore = new ByteArrayDataStore();

        // 创建SimpleFeatureType
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("CanvasShapes");
        builder.add("the_geom", Polygon.class);
        SimpleFeatureType type = builder.buildFeatureType();
        memoryDataStore.createSchema(type);

        // 创建并写入特性
        try (Transaction transaction = new DefaultTransaction("create")) {
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = memoryDataStore.getFeatureWriterAppend("shapes", transaction);

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

        return memoryDataStore.getShpBytes();
    }

    private static class ByteArrayDataStore extends ShapefileDataStore {
        private final ByteArrayOutputStream shpOutputStream = new ByteArrayOutputStream();
        private final ByteArrayOutputStream shxOutputStream = new ByteArrayOutputStream();
        private final ByteArrayOutputStream dbfOutputStream = new ByteArrayOutputStream();

        public ByteArrayDataStore() throws IOException {
            super(File.createTempFile("temp", ".shp").toURI().toURL());
            setCharset(StandardCharsets.UTF_8);
        }

//        @Override
        protected OutputStream createOutputStream(final String file) throws IOException {
            if (file.endsWith(".shp")) {
                return shpOutputStream;
            } else if (file.endsWith(".shx")) {
                return shxOutputStream;
            } else if (file.endsWith(".dbf")) {
                return dbfOutputStream;
            }
            return null;
        }

        public byte[] getShpBytes() {
            return shpOutputStream.toByteArray();
        }

        public byte[] getShxBytes() {
            return shxOutputStream.toByteArray();
        }

        public byte[] getDbfBytes() {
            return dbfOutputStream.toByteArray();
        }
    }
}
