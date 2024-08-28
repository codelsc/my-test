package geoTest;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultEngineeringCRS;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SHPWriter {
    public static void main(String[] args) throws Exception {
        // 创建要素类型
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("MyPolygon");
        typeBuilder.setCRS(DefaultEngineeringCRS.GENERIC_2D); // 使用平面直角坐标系
        typeBuilder.add("geometry", Polygon.class);

        SimpleFeatureType featureType = typeBuilder.buildFeatureType();

        // 创建shp文件
        File file = new File("output.shp");
        Map<String, Serializable> params = new HashMap<>();
        params.put("url", file.toURI().toURL());
        params.put("create spatial index", Boolean.TRUE);
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore shpDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

        // 设置地理范围
        Envelope bounds = new Envelope(0, 1000, 0, 800); // 设置左上角和右下角的坐标
        params.put("memory mapped buffer", Boolean.TRUE);
        params.put("write spatial index", Boolean.TRUE);
        params.put("url", file.toURI().toURL());
        params.put("create spatial index", Boolean.TRUE);
        params.put("bbox", bounds);

        // 设置空间引用
        CoordinateReferenceSystem crs = DefaultEngineeringCRS.GENERIC_2D;
        params.put("force longitude first axis order", Boolean.TRUE);
        params.put("create spatial index", Boolean.TRUE);
        params.put("prj", CRS.toSRS(crs));

        // 设置要素类型
        shpDataStore.createSchema(featureType);
        shpDataStore.forceSchemaCRS(DefaultEngineeringCRS.GENERIC_2D);
        String typeName = shpDataStore.getTypeNames()[0];
        shpDataStore.createSchema(featureType);

        // 创建要素
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);

        // 创建多边形
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(536, 73),
                new Coordinate(297, 25),
                new Coordinate(513, 26),
                new Coordinate(536, 73)
        };
        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
        Polygon polygon = geometryFactory.createPolygon(linearRing, null);

        featureBuilder.add(polygon);
        SimpleFeature feature = featureBuilder.buildFeature(null);

        // 写入shp文件
        SimpleFeatureSource featureSource = shpDataStore.getFeatureSource(typeName);
        SimpleFeatureCollection collection = new DefaultFeatureCollection(null, featureType);
        ((DefaultFeatureCollection)collection).add(feature);
        SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
        featureStore.addFeatures(collection);
    }
}
