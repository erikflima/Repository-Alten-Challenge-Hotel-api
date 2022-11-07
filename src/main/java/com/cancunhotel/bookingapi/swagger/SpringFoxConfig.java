package com.cancunhotel.bookingapi.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {


	@Bean
	public Docket apiDocket() {
		
		return new Docket( DocumentationType.SWAGGER_2 ).select()
													    .apis( RequestHandlerSelectors.basePackage( "com.cancunhotel.bookingapi.controllers" ) )
													    .build()
													    .useDefaultResponseMessages(false)
													    .apiInfo( apiInfo() )
													    .tags( 
															   new Tag("Client", "Manage client information."),
															   new Tag("Room", "Manage room information."),
															   new Tag("Reservation", "Manage reservation information.")
															 );
	}


	//Info about the documentation.
	public ApiInfo apiInfo() {
		
		return new ApiInfoBuilder()
					.title("🏖️ Cancun Hotel Booking API Documentation")
					.description("📋 Full API Official Technical Documentation \n 🆘 If you need help, please contact our development team:")
					.version("1")
					.contact( new Contact("👨🏽‍💻 Erik Lima", "https://www.eriklima.com", "erik@eriklima.com") )
					.build();
	}


	//Complement path of files to create html swagger page.
	@Override
	public void addResourceHandlers( ResourceHandlerRegistry registry ){
		
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}


}