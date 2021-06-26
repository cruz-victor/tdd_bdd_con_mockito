package org.vcruz.test.springboot.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    //Agreager en el contenedor de spring un objeto de swagger
    //Docket es el objeto que crea la documentacion springboot con swagger.
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("org.vcruz.test.springboot.app.controller"))
            .paths(PathSelectors.any()).build();
    }
}
