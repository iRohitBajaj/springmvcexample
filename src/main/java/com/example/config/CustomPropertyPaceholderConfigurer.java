package com.example.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

public class CustomPropertyPaceholderConfigurer extends PropertySourcesPlaceholderConfigurer
{

    /*private ConfigurableListableBeanFactory factory;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     final ConfigurablePropertyResolver propertyResolver) throws BeansException {
        super.processProperties(beanFactoryToProcess, propertyResolver);

        if (beanFactoryToProcess.hasEmbeddedValueResolver()) {
            logger.debug("Value resolver exists.");
            factory = beanFactoryToProcess;
        }
        else {
            logger.error("No existing embedded value resolver.");
        }

        try {
            *//*Properties newProps = new Properties();
            newProps.setProperty("jdbc.url", "fakeurl");
            if(this.getProperty("jdbc.url")!=null)
            {
                this.setProperties(newProps);
            }*//*
            Properties props = this.mergeProperties();
            props.forEach((key, value) -> System.out.println("key : "+key+" and value : "+value));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public String getProperty(String name) {
        Object propertyValue = factory.resolveEmbeddedValue(this.placeholderPrefix + name + this.placeholderSuffix);
        //factory.addPropertyEditorRegistrar();
        return propertyValue.toString();
    }*/


   /* @Override
    protected void convertProperties(Properties props) {
        Enumeration propertyNames = props.propertyNames();

        while(propertyNames.hasMoreElements()) {
            String propertyName = (String)propertyNames.nextElement();
            String propertyValue = (propertyName.indexOf("jdbc.url") != -1)? "fakeurl" :props.getProperty(propertyName);
            String convertedValue = this.convertProperty(propertyName, propertyValue);
            if (!ObjectUtils.nullSafeEquals(propertyValue, convertedValue)) {
                props.setProperty(propertyName, convertedValue);
            }
        }

    }*/

    @Override
    protected void doProcessProperties(final ConfigurableListableBeanFactory beanFactoryToProcess,
                                       final StringValueResolver valueResolver) {
        super.doProcessProperties(beanFactoryToProcess, new CustomValueResolver(valueResolver));
    }

}