package geoTest;

import org.geotools.data.*;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.*;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 可以用
 */
public class CreateCanvasShapefile {

    public static void main(String[] args) throws Exception {

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
                new Coordinate(100, 500),
                new Coordinate(600, 600),
                new Coordinate(700, 700),
                new Coordinate(500, 800),
                new Coordinate(100, 500)
        };

        // 转换画布坐标到shp文件，并生成.shp字节数组
        byte[] shapefileBytes = createShapefile(rectCoordinates, polyCoordinates);


        Files.write(new File("shapesabc.shp").toPath(), shapefileBytes);
    }

    public static byte[] createShapefile(Coordinate[] rectCoords, Coordinate[] polyCoords) throws IOException {
        // 创建几何工厂
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        // 创建shapefile临时文件
        File tempFile = File.createTempFile("shapesheihei", ".shp");

        // 设置shapefile参数
        Map<String, Object> params = new HashMap<>();
        params.put("url", tempFile.toURI().toURL());

        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

        // 创建SimpleFeatureType
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("CanvasShapes");
        builder.setCRS(null); // 如果需要可以设置CRS
        builder.add("the_geom", Polygon.class);
        SimpleFeatureType type = builder.buildFeatureType();
        newDataStore.createSchema(type);

        // 创建并写入特性
        try (Transaction transaction = new DefaultTransaction("create")) {
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
            SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = newDataStore.getFeatureWriterAppend(typeName, transaction);

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
            writer.close();
        }
        byte[] bytes = java.nio.file.Files.readAllBytes(tempFile.toPath());
        return bytes;
    }

    private static void zipShapefile(File shapefile, ZipOutputStream zos) throws IOException {
        // 存在的文件扩展名
        String[] extensions = new String[]{".shp", ".shx", ".dbf", ".prj"};

        for (String ext : extensions) {
            File file = new File(shapefile.getParent(), shapefile.getName().replace(".shp", ext));
            if (file.exists()) {
                byte[] bytes = java.nio.file.Files.readAllBytes(file.toPath());
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }
        }
    }
}
