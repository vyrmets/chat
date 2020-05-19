package chat.database;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InitializationProperties {
    private static final Logger LOGGER = Logger.getLogger(UsersRepository.class);
    private static final String WAY_PROPERTIES = "server/src/main/resources/database.properties";
    private Properties properties = new Properties();

    public String getPropertiesName(String propertiesName) {

        String name = "";

        try (FileInputStream fileInputStream = new FileInputStream(WAY_PROPERTIES)) {
            properties.load(fileInputStream);
            name = properties.getProperty(propertiesName);
        } catch (IOException e) {
            LOGGER.info("Faild to take name", e);
        }

        return name;
    }

    public String getPropertiesPass(String propertiesPass) {

        String pass = "";

        try (FileInputStream fileInputStream = new FileInputStream(WAY_PROPERTIES)) {
            properties.load(fileInputStream);
            pass = properties.getProperty(propertiesPass);
        } catch (IOException e) {
            LOGGER.info("Faild to take password", e);
        }

        return pass;
    }

    public String getPropertiesUrl(String propertiesUrl) {

        String url = "";

        try (FileInputStream fileInputStream = new FileInputStream(WAY_PROPERTIES)) {
            properties.load(fileInputStream);
            url = properties.getProperty(propertiesUrl);
        } catch (IOException e) {
            LOGGER.info("Faild to take url", e);
        }

        return url;
    }
}
