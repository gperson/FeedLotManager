package com.holz.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.holz.web.*" })
@Import({ SecurityConfig.class, DataConfig.class })
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurerAdapter {

	
	@Bean
	public TilesViewResolver viewResolver(){
		TilesViewResolver viewResolver = new TilesViewResolver();
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
	public MappingJacksonHttpMessageConverter jsonConverter(){
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
		List<MediaType> types = new ArrayList<MediaType>();
		types.add(MediaType.APPLICATION_JSON);
		jsonConverter.setSupportedMediaTypes(types);
		return jsonConverter;
	}
	
	@Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
        converters.add(jsonConverter());
    }
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}