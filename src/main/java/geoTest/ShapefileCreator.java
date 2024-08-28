//package geoTest;
//
//import org.geotools.data.DataStore;
//
//import org.geotools.data.DataStoreFinder;
//
//import org.geotools.data.DefaultTransaction;
//
//import org.geotools.data.FeatureWriter;
//
//import org.geotools.data.Transaction;
//
//import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
//
//import org.geotools.geometry.jts.JTSFactoryFinder;
//
//import org.geotools.referencing.crs.DefaultGeographicCRS;
//import org.locationtech.jts.geom.Coordinate;
//
//import org.locationtech.jts.geom.GeometryFactory;
//
//import org.locationtech.jts.geom.Point;
//import org.opengis.feature.simple.SimpleFeature;
//
//import org.opengis.feature.simple.SimpleFeatureType;
//
//
//
//import java.io.File;
//
//import java.io.Serializable;
//
//import java.util.HashMap;
//
//import java.util.Map;
//
//
//
//public class ShapefileCreator {
//
//
//
//    public static void main(String[] args) throws Exception {
//
//        // 1. 定义 Shapefile 的位置、名称和模式
//
//        File shapefile = new File("path/to/your/shapefile.shp");
//
//        Map<String, Serializable> params = new HashMap<>();
//
//        params.put("url", shapefile.toURI().toURL());
//
//        params.put("create spatial index", Boolean.TRUE);
//
//
//
//        // 定义要素类型（Schema）
//
//        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
//
//        builder.setName("Points"); // 要素类型名称
//
//        builder.setCRS(DefaultGeographicCRS.WGS84); // 坐标参考系统
//
//        builder.add("location", Point.class); // 添加几何属性
//
//        builder.add("name", String.class); // 可以添加其他属性（可选）
//
//        SimpleFeatureType TYPE = builder.buildFeatureType();
//
//
//
//        // 2. 创建 DataStore
//
//        DataStore dataStore = DataStoreFinder.getDataStore(params);
//
//        dataStore.createSchema(TYPE);
//
//
//
//        // 3. 创建并写入点
//
//        Transaction transaction = new DefaultTransaction("create");
//
//        String typeName = dataStore.getTypeNames()[0];
//
//        FeatureWriter<SimpleFeatureType, SimpleFeature> writer = dataStore.getFeatureWriterAppend(typeName, transaction);
//
//
//
//        try {
//            double lon1 = 10.0, lat1 = 50.0;
//            int px1 = 100, py1 = 100;
//
//            double lon2 = 11.0, lat2 = 49.0;
//            int px2 = 200, py2 = 200;
//
//
//
//            PixelToGeoCoordinateConverter converter = new PixelToGeoCoordinateConverter(lon1, lat1, px1, py1, lon2, lat2, px2, py2);
//            Coordinate coordinate = converter.pixelToGeo(20, 30);
//
//            Coordinate coord1 = new Coordinate(coordinate.x, coordinate.y); // 经度, 纬度
//
//            Coordinate coord2 = new Coordinate(103.0, 1.5);
//
//
//
//            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
//
//
//
//            // 创建点要素
//
//            SimpleFeature feature1 = writer.next();
//
//            feature1.setAttribute("location", geometryFactory.createPoint(coord1));
//
//            // 可以设置其他属性（如果定义了的话）
//
//            // feature1.setAttribute("name", "Point 1");
//
//
//
//            SimpleFeature feature2 = writer.next();
//
//            feature2.setAttribute("location", geometryFactory.createPoint(coord2));
//
//            // feature2.setAttribute("name", "Point 2");
//
//
//
//            // 将要素写入 Shapefile
//
//            writer.write();
//
////            writer.write(feature2);
//
//
//
//            // 刷新写入并关闭 writer
//
//            transaction.commit();
//
//            writer.close();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            transaction.rollback();
//
//        } finally {
//
//            dataStore.dispose();
//
//        }
//
//    }
//
//}