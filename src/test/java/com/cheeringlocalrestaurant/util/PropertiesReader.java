package com.cheeringlocalrestaurant.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

    private static Map<String, PropertiesReader> map = new HashMap<>();

    private Properties properties;

    private PropertiesReader() {}

    private PropertiesReader(Properties properties) {
        this.properties = properties;
    }

    public static PropertiesReader getInstance(String fileNameWithOutExtention) throws IOException {
        createInstance(fileNameWithOutExtention);
        return map.get(fileNameWithOutExtention);
    }

    private static void createInstance(String fileNameWithOutExtention) throws IOException {
        if (map.containsKey(fileNameWithOutExtention)) {
            return;
        }
        InputStream inputStream = new BufferedInputStream(PropertiesReader.class.getClassLoader().getResourceAsStream(fileNameWithOutExtention + ".properties"));
        Properties properties = new Properties();
        properties.load(inputStream);
        map.put(fileNameWithOutExtention, new PropertiesReader(properties));
    }

    public String getString(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new PropertiesReaderKeyNotFoundException("キー '" + key + "' は定義されていません。");
        }
        return value;
    }

    public String getString(String key, String defaultValue) {
        try {
            return getString(key);
        } catch (PropertiesReaderKeyNotFoundException e) {
            return defaultValue;
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public int getInt(String key, int defaultValue) {
        try {
            return getInt(key);
        } catch (IllegalArgumentException | PropertiesReaderKeyNotFoundException e) {
            return defaultValue;
        }
    }

    public double getDouble(String key) {
        return Double.parseDouble(getString(key));
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return getDouble(key);
        } catch (IllegalArgumentException | PropertiesReaderKeyNotFoundException e) {
            return defaultValue;
        }
    }
}
