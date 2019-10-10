package com.example.config;

import org.springframework.util.StringValueResolver;

import java.util.Properties;

public class CustomValueResolver implements StringValueResolver {

    private StringValueResolver valueResolver;
    private Properties props;

    CustomValueResolver(final StringValueResolver stringValueResolver, Properties props) {
        this.valueResolver = stringValueResolver;
        this.props = props;
    }

    @Override
    public String resolveStringValue( String placeholder) {

        String value =  null;
        //placeholder = sanitizePlaceHolder(placeholder);
        value =valueResolver.resolveStringValue(placeholder);
        placeholder = stripPlaceHolder(placeholder);
        if(props.stringPropertyNames().contains(placeholder)) {
            value = "fake-"+value;
            return value;
        }
        return value;
    }

    private String stripPlaceHolder(String placeholder){
        placeholder = placeholder.replace("${","").replace("}", "");
        return placeholder;
    }

    private String sanitizePlaceHolder(String placeholder){
        if(placeholder.contains(":"))
        {
            placeholder = placeholder.substring(0, placeholder.indexOf(":"))+"}";
        }
        return placeholder;
    }
}