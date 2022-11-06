package org.plusone.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlusOneUtil {

    private static final Logger LOGGER = Logger.getLogger(PlusOneUtil.class.getName());

    public static String getPlusOneHome(){
        String path = System.getProperty("plusone.home");
        return path;
    }

    public static Properties getPlusOneProperties(){
        Properties properties = getFileAsProperties(getPlusOneHome()+"/conf/configuration.properties");
        return properties;
    }

    public static Properties getFileAsProperties(String path) {
        Properties prop = new Properties();

        try {
            File f = new File(path);
            if (!f.exists()) {
                LOGGER.log(Level.SEVERE,"File " + path + " not exist.");
                return null;
            }

            FileInputStream fis = null;

            try {
                fis = new FileInputStream(f);
                prop.load(fis);
            } finally {
                if (fis != null) {
                    fis.close();
                }

            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return prop;
    }
}
