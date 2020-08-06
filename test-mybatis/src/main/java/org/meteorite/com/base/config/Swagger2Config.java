package org.meteorite.com.base.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EX_052100260
 * @title: Swagger2Config
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-8-6 15:24
 */
@Configuration
public class Swagger2Config {

    public static final String VERSION = "1.0.0";

    public static final String BASE_PACKAGE = "org.meteorite.com";

    @Bean
    public Docket createRestApi() {
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();

        builder.name("test-mybatis").description("本地调试 mybatis").modelRef(new ModelRef("String")).parameterType("header")
                .required(false).defaultValue("huolongguo").build();
        parameters.add(builder.build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(apiInfo()).select().apis(basepackage(BASE_PACKAGE))
                .paths(PathSelectors.any()).build().globalOperationParameters(parameters);


        System.out.println("createRestApi ..... ");
        return docket;
    }

    private Predicate<RequestHandler> basepackage(String basepackage) {
        return e -> declaringClass(e).transform(handlerPackage(basepackage)).or(true);
    }

    private Function<Class<?>, Boolean> handlerPackage(String basepackage) {

        return e -> {
            for (String s : basepackage.split(",")) {
                boolean flag = e.getPackage().getName().startsWith(s);
                if (flag) {
                    return true;
                }
            }
            return false;
        };
    }

    private Optional<? extends Class<?>> declaringClass(RequestHandler handler) {
        return Optional.fromNullable(handler.declaringClass());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("test-mybatis").description("调试用的 swagger 页面").version(VERSION).build();
    }


}
