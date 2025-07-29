package Utilites;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {


    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";



    public static String getJsonData (String jsonFileName , String field){
        try {
            //define object file reader
            FileReader reader = new FileReader(TEST_DATA_PATH + jsonFileName + ".json");

            //parse the Json directly into a JsonElement
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }




    public static String getPropertyValue(String fileName , String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA_PATH+fileName+".properties"));
        return properties.getProperty(key);
    }
}
