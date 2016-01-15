package com.holz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class ViewResolverConfig extends WebMvcConfigurerAdapter {

	@Bean
	public TilesViewResolver viewResolverTiles(){
		TilesViewResolver viewResolver = new TilesViewResolver();
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("WEB-INF/views/jsp/tiles/tiles.xml");
		tilesConfigurer.setPreparerFactoryClass(org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory.class);
		return tilesConfigurer;    
	}
	
	@Bean  
    public InternalResourceViewResolver viewResolverJsp() {  
		InternalResourceViewResolver resolver = new InternalResourceViewResolver(); 
		resolver.setOrder(2);
        resolver.setPrefix("/WEB-INF/views/jsp/");  
        resolver.setSuffix(".jsp");
        return resolver;  
    }
}
