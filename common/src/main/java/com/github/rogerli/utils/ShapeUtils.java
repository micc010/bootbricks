package com.github.rogerli.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.referencing.FactoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

//import org.gdal.gdal.gdal;
//import org.gdal.ogr.DataSource;
//import org.gdal.ogr.Driver;
//import org.gdal.ogr.ogr;

/**
 * @author roger.li
 * @since 2019/6/24
 */
@Component
public class ShapeUtils {

    @Autowired
    private ObjectMapper objectMapper;

//    /**
//     * geojson转换为shp文件
//     *
//     * @param jsonPath
//     * @param shpPath
//     *
//     * @return
//     */
//    public Map geojson2Shape(String jsonPath, String shpPath) {
//        Map map = new HashMap();
//        GeometryJSON gjson = new GeometryJSON();
//        try {
//            String strJson = cm.getFileContent(jsonPath);
//            JSONObject json = new JSONObject(strJson);
//            JSONArray features = (JSONArray) json.get("features");
//            JSONObject feature0 = new JSONObject(features.get(0).toString());
//            System.out.println(feature0.toString());
//            String strType = ((JSONObject) feature0.get("geometry")).getString("type").toString();
//
//            Class<?> geoType = null;
//            switch (strType) {
//                case "Point":
//                    geoType = Point.class;
//                case "MultiPoint":
//                    geoType = MultiPoint.class;
//                case "LineString":
//                    geoType = LineString.class;
//                case "MultiLineString":
//                    geoType = MultiLineString.class;
//                case "Polygon":
//                    geoType = Polygon.class;
//                case "MultiPolygon":
//                    geoType = MultiPolygon.class;
//            }
//            //创建shape文件对象
//            File file = new File(shpPath);
//            Map<String, Serializable> params = new HashMap<String, Serializable>();
//            params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
//            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
//            //定义图形信息和属性信息
//            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
//            tb.setCRS(DefaultGeographicCRS.WGS84);
//            tb.setName("shapefile");
//            tb.add("the_geom", geoType);
//            tb.add("POIID", Long.class);
//            ds.createSchema(tb.buildFeatureType());
//            //设置编码
//            Charset charset = Charset.forName("GBK");
//            ds.setCharset(charset);
//            //设置Writer
//            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);
//
//            for (int i = 0, len = features.length(); i < len; i++) {
//                String strFeature = features.get(i).toString();
//                Reader reader = new StringReader(strFeature);
//                SimpleFeature feature = writer.next();
//                feature.setAttribute("the_geom", gjson.readMultiPolygon(reader));
//                feature.setAttribute("POIID", i);
//                writer.write();
//            }
//            writer.close();
//            ds.dispose();
//            map.put("status", "success");
//            map.put("message", shpPath);
//        } catch (Exception e) {
//            map.put("status", "failure");
//            map.put("message", e.getMessage());
//            e.printStackTrace();
//        }
//        return map;
//    }

    /**
     * shp转换为Geojson
     *
     * @param shpPath
     *
     * @return
     */
    public Map shape2Geojson(String shpPath) throws IOException, FactoryException {
        FeatureJSON fjson = new FeatureJSON();
        File file = new File(shpPath);
        StringWriter writer = new StringWriter();
        ShapefileDataStore shpDataStore = new ShapefileDataStore(file.toURI().toURL());
        shpDataStore.setCharset(Charset.forName("GBK"));
        String typeName = shpDataStore.getTypeNames()[0];
        SimpleFeatureSource featureSource = shpDataStore.getFeatureSource(typeName);
        SimpleFeatureCollection result = featureSource.getFeatures();
        fjson.writeFeatureCollection(result, writer);
        return new ObjectMapper().readValue(writer.toString(), Map.class);
    }

    public static void main(String[] args) throws IOException, FactoryException {
        System.out.println(new ShapeUtils().shape2Geojson("D:\\arcgis\\测试\\行政区域划分.shp"));
    }

//    public static void main(String[] args) {
//        // 注册所有的驱动
//        ogr.RegisterAll();
//        // 为了支持中文路径，请添加下面这句代码
//        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
//        // 为了使属性表字段支持中文，请添加下面这句
//        gdal.SetConfigOption("SHAPE_ENCODING", "");
//        //shp文件所在的位置
//        String strVectorFile = "D:\\arcgis\\测试\\行政区域划分.shp";
//        //打开数据
//        DataSource ds = ogr.Open(strVectorFile, 0);
//        if (ds == null) {
//            System.out.println("打开文件失败！");
//            return;
//        }
//        System.out.println("打开文件成功！");
//        int count = ogr.GetDriverCount();
//        for (int i = 0; i < count; i++){
//            System.out.println(ogr.GetDriver(i).getName());
//        }
//        Driver dv = ogr.GetDriverByName("GEOJSON");
////        Vector createOptions = dv.GetMetadata_List();
//        if (dv == null) {
//            System.out.println("打开驱动失败！");
//            return;
//        }
//        System.out.println("打开驱动成功！");
//        //输出geojson的位置及文件名
////        dv.CreateDataSource("D:\\arcgis\\测试\\aaaa1.geojson", createOptions);
//        dv.CopyDataSource(ds, "D:\\arcgis\\测试\\aaaa1.geojson");
//        System.out.println("转换成功！");
//    }


}
