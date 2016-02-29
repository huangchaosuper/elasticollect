package cn.huangchaosuper.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by I311579 on 10/19/2015.
 */
public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Collection<String> getFilesByPath(String path) {
        Collection<String> collectionList = new ArrayList<String>();
        File file = new File(path);
        if (!file.isDirectory()) {
            logger.error(Utils.getElasticollectResourceString("config.scriptPathNotBeingFound"));
        } else {
            for (String item : file.list()) {
                //collectionList.add(Paths.get(path, item).toString());
                collectionList.add(item);
            }
        }

        return collectionList;
    }

    public static String getElasticollectResourceString(String key) {
        return getResourceString("i18n.elasticollect", key);
    }

    public static String getResourceString(String baseName, String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
        String value = resourceBundle.getString(key);
        logger.debug("local={},key={},value={}", resourceBundle.getLocale().toString(), key, value);
        return value;
    }

    public static boolean loadConfigFile(String configPath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configPath));
        } catch (IOException e) {
            return false;
        }
        properties.forEach((key, value) -> {
            System.getProperties().setProperty(String.valueOf(key), String.valueOf(value));
        });
        return true;
    }

    public static <T> T getProperty(String key, T defaultValue, Class<T> clazz) {
        String value = System.getProperties().getProperty(key);

        if (value == null) {
            return defaultValue;
        } else {
            Constructor<T> constructor = null;
            try {
                constructor = clazz.getConstructor(new Class<?>[]{String.class});
            } catch (NoSuchMethodException nsme) {
                logger.error(Utils.getElasticollectResourceString("config.constructorNotBeingFound"));
            }
            try {
                return constructor.newInstance(value);
            } catch (InstantiationException ie) {
                logger.error("handle InstantiationException", ie.fillInStackTrace());
            } catch (IllegalAccessException iae) {
                logger.error("handle IllegalAccessException", iae.fillInStackTrace());
            } catch (InvocationTargetException ite) {
                logger.error("handle InvocationTargetException", ite.fillInStackTrace());
            }
            return defaultValue;
        }
    }
}
