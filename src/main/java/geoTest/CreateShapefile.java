package geoTest;

import org.geotools.data.*;
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
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CreateShapefile {

    public static void main(String[] args) throws Exception {
        // step 1: Create the geometry factory
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        // Define the rectangular and polygon coordinates
        Coordinate[] rectCoordinates = new Coordinate[] {
                new Coordinate(0, 10),
                new Coordinate(10, 10),
                new Coordinate(10, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 10)
        };

        Coordinate[] polyCoordinates = new Coordinate[]{
                new Coordinate(3, 4),
                new Coordinate(5, 6),
                new Coordinate(7, 8),
                new Coordinate(9, 1),
                new Coordinate(3, 4)
        };

        // Create rectangle and polygon
        LinearRing rectRing = geometryFactory.createLinearRing(rectCoordinates);
        Polygon rectangle = geometryFactory.createPolygon(rectRing, null);

        LinearRing polyRing = geometryFactory.createLinearRing(polyCoordinates);
        Polygon polygon = geometryFactory.createPolygon(polyRing, null);

        // step 2: Create Shapefile
        File newFile = new File("shapes.shp");
        Map<String, Object> params = new HashMap<>();
        params.put("url", newFile.toURI().toURL());

        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

        // Define the type of features
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(null); // Use null CRS for a simple example; otherwise, set the correct CRS
        builder.add("the_geom", Polygon.class);

        final SimpleFeatureType type = builder.buildFeatureType();
        newDataStore.createSchema(type);

        // step 3: Write features to shapefile
        Transaction transaction = new DefaultTransaction("create");
        String typeName = newDataStore.getTypeNames()[0];
        SimpleFeatureType SHAPE_TYPE = newDataStore.getSchema(typeName);

        FeatureWriter<SimpleFeatureType, SimpleFeature> writer = newDataStore.getFeatureWriterAppend(typeName, transaction);

        // Add Rectangle
        SimpleFeature feature = writer.next();
        feature.setAttribute("the_geom", rectangle);
        writer.write();

        // Add Polygon
        feature = writer.next();
        feature.setAttribute("the_geom", polygon);
        writer.write();

        // Commit and close
        writer.close();
        transaction.commit();
        transaction.close();
    }
}