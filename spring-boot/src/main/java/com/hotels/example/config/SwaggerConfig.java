package com.hotels.example.config;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)

public class SwaggerConfig{
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";
    Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

  @Bean
  public Docket swaggerSpringfoxDocket() {
      log.debug("Starting Swagger");



      Docket docket = new Docket(DocumentationType.SWAGGER_2)

              .pathMapping("/")
              .apiInfo(ApiInfo.DEFAULT)
              .forCodeGeneration(true)
              .genericModelSubstitutes(ResponseEntity.class)
              .ignoredParameterTypes(Pageable.class)
              .ignoredParameterTypes(java.sql.Date.class)
              .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
              .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
              .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
              .securityContexts(Lists.newArrayList(securityContext()))
              .securitySchemes(Lists.newArrayList(apiKey()))
              .useDefaultResponseMessages(false);


      docket = docket.select()
              .paths(regex(DEFAULT_INCLUDE_PATTERN))
              .build();
     log.debug("Started Swagger ");
      return docket;
  }


    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()

                .securityReferences(defaultAuth())

                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))

                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

    @Primary
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }




}
