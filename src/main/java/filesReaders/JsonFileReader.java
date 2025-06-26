package filesReaders;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {
    String jsonFileRelativeLocatorFromTestDataDir;

    public JsonFileReader(String jsonFileLocation) {
        this.jsonFileRelativeLocatorFromTestDataDir = jsonFileLocation;
    }

    private JSONObject getJsonObject()
    {
        JSONParser parser = new JSONParser();
        Object obj = null ;
        try {
            obj = parser.parse(new FileReader(System.getProperty("user.dir")+"/src/test/resources/testData/" + jsonFileRelativeLocatorFromTestDataDir));
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
        } catch (IOException e) {
            System.out.println("IO exception");
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }
    public String getJsonStringValueByKey(String jsonKey) {
        JSONObject jsonObject = getJsonObject();
        String value = (String) jsonObject.get(jsonKey);
        return value;
    }
    public Boolean getJsonBooleanValueByKey(String jsonKey) {
        JSONObject jsonObject = getJsonObject();
        Boolean value = (Boolean) jsonObject.get(jsonKey);
        return value;
    }
    public int getJsonIntegerValueByKey(String jsonKey) {
        JSONObject jsonObject = getJsonObject();
        int value = Integer.valueOf( jsonObject.get(jsonKey).toString()   ) ;
        return value;
    }

    public String getJsonFileContentAsString ()
    {
        JSONObject jsonObject = getJsonObject();
        return jsonObject.toString() ;
    }
}
