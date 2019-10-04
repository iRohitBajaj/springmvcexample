package com.example.config;

import org.springframework.util.StringValueResolver;

public class CustomValueResolver implements StringValueResolver {

    private StringValueResolver valueResolver;

    CustomValueResolver(final StringValueResolver stringValueResolver) {
        this.valueResolver = stringValueResolver;
    }

    @Override
    public String resolveStringValue(final String strVal) {

        String value = null;
        if(strVal.equalsIgnoreCase("${jdbc.url}")){
            value = "fakeurl";
        }else {
            value =valueResolver.resolveStringValue(strVal);
        }
        return value;
    }
}