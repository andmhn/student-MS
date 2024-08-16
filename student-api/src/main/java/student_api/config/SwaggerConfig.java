package student_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {
		@Server(description = "DEV", url = "http://127.0.0.1:8080") })

@Configuration
public class SwaggerConfig {

	@Value("Student Management System API")
	private String applicationName;
	private String description = "api documentation for student-MS. This application allows students to view classes, attend to classes, view papers, view result ";
	
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes(BASIC_AUTH_SECURITY_SCHEME,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info()
						.title(applicationName)
						.description(description)
						.version("v1")
						);
	}

	public static final String BASIC_AUTH_SECURITY_SCHEME = "basicAuth";
}
