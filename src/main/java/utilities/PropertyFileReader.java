package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyFileReader {
    Properties properties = null;
    private static final String FILE_LOCATION = "src/test/resources/";

    public Properties getProperties(String fileName) {
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(FILE_LOCATION + fileName + ".properties"));
            properties = new Properties();
            properties.load(is);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
