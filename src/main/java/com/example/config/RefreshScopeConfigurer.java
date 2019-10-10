package com.example.config;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Set;

import static org.springframework.cloud.autoconfigure.RefreshAutoConfiguration.REFRESH_SCOPE_NAME;

@Configuration
public class RefreshScopeConfigurer implements BeanDefinitionRegistryPostProcessor, EnvironmentAware{

    private Environment environment;

        private boolean bound = false;

        private Set<String> refreshables;

        public Set<String> getRefreshable() {
            return this.refreshables;
        }

        public void setRefreshable(Set<String> refreshables) {
            if (this.refreshables != refreshables) {
                this.refreshables.clear();
                this.refreshables.addAll(refreshables);
            }
        }

        public void setExtraRefreshable(Set<String> refreshables) {
            this.refreshables.addAll(refreshables);
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                throws BeansException {
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
                throws BeansException {
            bindEnvironmentIfNeeded(registry);
            for (String name : registry.getBeanDefinitionNames()) {
                BeanDefinition definition = registry.getBeanDefinition(name);
                if (isApplicable(registry, name, definition)) {
                    BeanDefinitionHolder holder = new BeanDefinitionHolder(definition,
                            name);
                    BeanDefinitionHolder proxy = ScopedProxyUtils
                            .createScopedProxy(holder, registry, true);
                    definition.setScope("refresh");
                    if (registry.containsBeanDefinition(proxy.getBeanName())) {
                        registry.removeBeanDefinition(proxy.getBeanName());
                    }
                    registry.registerBeanDefinition(proxy.getBeanName(),
                            proxy.getBeanDefinition());
                }
            }
        }

        private boolean isApplicable(BeanDefinitionRegistry registry, String name,
                                     BeanDefinition definition) {
            String scope = definition.getScope();
            if (REFRESH_SCOPE_NAME.equals(scope)) {
                // Already refresh scoped
                return false;
            }
            String type = definition.getBeanClassName();
            if (!StringUtils.hasText(type) && registry instanceof BeanFactory) {
                Class<?> cls = ((BeanFactory) registry).getType(name);
                if (cls != null) {
                    type = cls.getName();
                }
            }
            if (type != null && refreshables!=null) {
                return this.refreshables.contains(type);
            }
            return false;
        }

        private void bindEnvironmentIfNeeded(BeanDefinitionRegistry registry) {
            if (!this.bound) { // only bind once
                if (this.environment == null) {
                    this.environment = new CloudEnvironment();
                }
                Binder.get(this.environment).bind("spring.cloud.refresh",
                        Bindable.ofInstance(this));
                this.bound = true;
            }
        }

        @Override
        public void setEnvironment(Environment environment) {

            this.environment = environment;
        }

}
