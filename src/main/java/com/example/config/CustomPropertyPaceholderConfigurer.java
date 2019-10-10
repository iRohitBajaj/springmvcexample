package com.example.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

public class CustomPropertyPaceholderConfigurer extends PropertySourcesPlaceholderConfigurer
{
    @Override
    protected void doProcessProperties(final ConfigurableListableBeanFactory beanFactoryToProcess,
                                       final StringValueResolver valueResolver) {
        Properties props = null;
        try {
             props = this.mergeProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.doProcessProperties(beanFactoryToProcess, new CustomValueResolver(valueResolver, props));
    }

}