package com.jk.utils;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils extends PropertyPlaceholderConfigurer {

    private static final Map<String, String> map = new HashMap<String, String>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
        super.processProperties(beanFactory, props);
        for (Object key : props.keySet()) {
            String keyStr = key.toString().replace(" ", "");
            String value = props.getProperty(keyStr).replace(" ", "");
            map.put(keyStr, value);
        }
    }

    public static String getProperty(String key) {
        return map.get(key);
    }

}