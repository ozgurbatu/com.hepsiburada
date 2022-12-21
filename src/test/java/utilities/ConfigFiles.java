package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFiles {

    private static Properties properties;
    static {
        String path="config.properties";
        try{
            FileInputStream fs = new FileInputStream(path);
            properties = new Properties();
            properties.load(fs);
            fs.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
