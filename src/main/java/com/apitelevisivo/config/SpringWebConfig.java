package com.apitelevisivo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringWebConfig implements WebMvcConfigurer {
    
    @Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
													  .apis(RequestHandlerSelectors.basePackage("com.apitelevisivo.web.controller"))
													  .paths(PathSelectors.any())
													  .build()
													  .apiInfo(apiInfo())
													  .tags(new Tag("Artista", "Gerenciar artista"))
													  .tags(new Tag("Categoria", "Gerenciar categoria"))
													  .tags(new Tag("Elenco", "Gerenciar elenco"))
													  .tags(new Tag("Episodio", "Gerenciar Episódio"))
													  .tags(new Tag("Escopo", "Gerenciar escopo"))
													  .tags(new Tag("Arquivo", "Arquivo"))
													  .tags(new Tag("Login", "Login"))
													  .tags(new Tag("Permissão", "Gerenciar permissão"))
													  .tags(new Tag("Role", "Gerenciar role"))
													  .tags(new Tag("Série", "Gerenciar série"))
													  .tags(new Tag("Serviço", "Gerenciar serviço"))
													  .tags(new Tag("Temporada", "Gerenciar temporada"))
													  .tags(new Tag("Usuário", "Gerenciar usuário"));
    }
    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API - Televisivo")
				// .description("API de controle")
				.version("1")
				// .contact(new Contact("IFSP", "http://bri.ifsp.edu.br", "contato@contato.com"))
				.build();
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorParameter(false)
				  .ignoreAcceptHeader(false)
				  .useRegisteredExtensionsOnly(false)
				  .defaultContentType(MediaType.APPLICATION_JSON)
				  .mediaType("json", MediaType.APPLICATION_JSON)
				  .mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
}