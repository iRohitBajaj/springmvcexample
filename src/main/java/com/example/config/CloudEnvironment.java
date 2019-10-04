package com.example.config;

import io.pivotal.spring.cloud.service.common.ConfigServerServiceInfo;
import io.pivotal.spring.cloud.service.config.ConfigClientOAuth2ResourceDetails;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.StandardServletEnvironment;

public class CloudEnvironment extends StandardServletEnvironment {

    @Override
    protected void customizePropertySources(MutablePropertySources propertySources) {
        super.customizePropertySources(propertySources);
        try {
            PropertySource<?> source = initConfigServicePropertySourceLocator(this);
            propertySources.addLast(source);

        } catch (

                Exception ex) {
            logger.warn("failed to initialize cloud config environment", ex);
        }
    }

    private PropertySource<?> initConfigServicePropertySourceLocator(Environment environment) {

        CloudFactory cloudFactory = new CloudFactory();
        Cloud cloud=null;
        RestTemplate template=null;
        ConfigClientOAuth2ResourceDetails resource;
        String name=null;
        String configServerUri=null;
        try{
            cloud = cloudFactory.getCloud();
            ConfigServerServiceInfo configServiceInfo = cloud.getSingletonServiceInfoByType(ConfigServerServiceInfo.class);
            name = StringUtils.isEmpty(String.valueOf(cloud.getApplicationInstanceInfo().getProperties().get("name")))? "testconfigclient" : String.valueOf(cloud.getApplicationInstanceInfo().getProperties().get("name"));
            configServerUri=configServiceInfo.getUri();

            String strAccessTokenUri = configServiceInfo.getAccessTokenUri();
            String strClientId = configServiceInfo.getClientId();
            String strClientSecret = configServiceInfo.getClientSecret();

            resource = new ConfigClientOAuth2ResourceDetails();
            resource.setAccessTokenUri(strAccessTokenUri);
            resource.setClientId(strClientId);
            resource.setClientSecret(strClientSecret);

            template = new OAuth2RestTemplate(resource);

        } catch(Exception e){
            logger.info("just a local problem");
        }

        configServerUri = StringUtils.isEmpty(configServerUri)?  "http://localhost:8888" :  configServerUri;

        ConfigClientProperties configClientProperties = new ConfigClientProperties(environment);

        configClientProperties.setUri(new String[]{configServerUri});

        if(StringUtils.isEmpty(System.getProperty("spring.application.name"))){
            System.out.println("SETTING APP NAME");
            System.setProperty("spring.application.name", name);
        }

        configClientProperties.setName(name);
        configClientProperties.setProfile("default");
        configClientProperties.setLabel("master");


        System.out.println("##################### will load the client configuration");
        System.out.println(configClientProperties);

        ConfigServicePropertySourceLocator configServicePropertySourceLocator =
                new ConfigServicePropertySourceLocator(configClientProperties);

        if(template!=null)
            configServicePropertySourceLocator.setRestTemplate(template);

        return configServicePropertySourceLocator.locate(environment);
    }
}
