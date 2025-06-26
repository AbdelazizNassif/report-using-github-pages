package filesReaders;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
    String propertyFileName;
    public PropertyFileReader(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    private Properties getAllProperties() {
        Properties fileProperties = null;
        try {
            FileReader fileReader = new FileReader( System.getProperty("user.dir")+"/src/main/resources/properties/" + propertyFileName ) ;
            fileProperties=new Properties();
            fileProperties.load(fileReader);
        }
        catch (IOException ioException)
        {
            System.out.println("IO Exception");
        }
        return fileProperties;
    }
    public String getPropertyByKey(String propertyKey) {
        Properties fileProperties = getAllProperties ();
        return fileProperties.getProperty(propertyKey);
    }
    public boolean getBooleanProperty(String propertyKey) {
        Properties fileProperties = getAllProperties ();
        return Boolean.parseBoolean(fileProperties.getProperty(propertyKey));
    }
    public int getIntegerProperty(String propertyKey) {
        Properties fileProperties = getAllProperties ();
        return Integer.parseInt(fileProperties.getProperty(propertyKey));
    }
}
