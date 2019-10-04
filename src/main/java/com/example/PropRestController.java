package com.example;

import com.example.config.CustomPropertyPaceholderConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/props")
public class PropRestController {

    @Autowired
    Environment environment;

    @Autowired
    CustomPropertyPaceholderConfigurer propertySourcesPlaceholderConfigurer;



    @RequestMapping(value = "/getAllPropsFromEnv", method = RequestMethod.GET)
    public Map<String, String> getAllPropsFromEnv()
    {
        Map<String,String> propsMap = new HashMap<>();
        propsMap.put("key1",environment.getProperty("key1"));
        propsMap.put("key2",environment.getProperty("key2"));
        propsMap.put("key3",environment.getProperty("key3"));
        propsMap.put("key4",environment.getProperty("key4"));
        propsMap.put("key5",environment.getProperty("key5"));
        propsMap.put("jdbc.url",environment.getProperty("jdbc.url"));
        propsMap.put("jdbc.username",environment.getProperty("jdbc.username"));
        propsMap.put("jdbc.password",environment.getProperty("jdbc.password"));
        System.out.println(propsMap.toString());
        return propsMap;
    }

    @RequestMapping(value = "/getAllPropsFromPlaceholderConf", method = RequestMethod.GET)
    public Map<String, String> getAllPropsFromPropPlaceholderConfigurer()
    {
        Map<String,String> propsMap = new HashMap<>();
        propertySourcesPlaceholderConfigurer.getAppliedPropertySources().forEach(
                propertySource -> {
                    if(propertySource.containsProperty("key1")) {
                        propsMap.put("key1", String.valueOf(propertySource.getProperty("key1")));
                    }
                    if(propertySource.containsProperty("key2")) {
                        propsMap.put("key2", String.valueOf(propertySource.getProperty("key2")));
                    }
                    if(propertySource.containsProperty("key3")) {
                        propsMap.put("key3", String.valueOf(propertySource.getProperty("key3")));
                    }
                    if(propertySource.containsProperty("key4")) {
                        propsMap.put("key4", String.valueOf(propertySource.getProperty("key4")));
                    }
                    if(propertySource.containsProperty("key5")) {
                        propsMap.put("key5", String.valueOf(propertySource.getProperty("key5")));
                    }
                    if(propertySource.containsProperty("jdbc.url")) {
                        propsMap.put("jdbc.url", String.valueOf(propertySource.getProperty("jdbc.url")));
                    }
                    if(propertySource.containsProperty("jdbc.username")) {
                        propsMap.put("jdbc.username", String.valueOf(propertySource.getProperty("jdbc.username")));
                    }
                    if(propertySource.containsProperty("jdbc.password")) {
                        propsMap.put("jdbc.password", String.valueOf(propertySource.getProperty("jdbc.password")));
                    }
                }
        );
        System.out.println(propsMap.toString());
        return propsMap;
    }
}
