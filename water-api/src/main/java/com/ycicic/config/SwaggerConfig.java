package com.ycicic.config;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.ycicic.common.core.enums.ApiEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Swagger的接口配置
 *
 * @author ycicic
 */
@Slf4j
@Configuration
public class SwaggerConfig implements ModelPropertyBuilderPlugin {

    /**
     * 是否开启swagger
     */
    @Value("${swagger.enabled}")
    private Boolean enabled;

    @Value("${swagger.prefix}")
    private String prefix;

    /**
     * 创建API
     */
    @Bean
    public Docket createCommonApi() {
        return new Docket(DocumentationType.OAS_30)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.ycicic.controller.common"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().groupName("通用接口").pathMapping(prefix)
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

    @Bean
    public Docket createClientApi() {
        return new Docket(DocumentationType.OAS_30)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.ycicic.controller.app"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().groupName("APP端").pathMapping(prefix)
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

    @Bean
    public Docket createSystemApi() {
        return new Docket(DocumentationType.OAS_30)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.ycicic.controller.system"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().groupName("PC端").pathMapping(prefix)
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }


    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).operationSelector(o -> o.requestMappingPattern().matches("/.*")).build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：Water 接口文档")
                // 描述
                .description("Water")
                // 作者信息
                .contact(new Contact("ycicic", "https://github.com/ycicic", "1392241352@qq.com"))
                // 版本
                .version("1.0.0").build();
    }

    @Override
    public void apply(ModelPropertyContext context) {
        descForEnumFields(context);
    }

    /**
     * Enum支持
     *
     * @param context context
     * @see ApiEnum
     */
    private void descForEnumFields(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.empty();
        // 找到 @ApiModelProperty 注解修饰的枚举类
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = Annotations.findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class);
        }

        //没有@ApiModelProperty 没有值，直接返回
        if (!annotation.isPresent()) {
            return;
        }
        Optional<BeanPropertyDefinition> optional = context.getBeanPropertyDefinition();
        if (!optional.isPresent()) {
            return;
        }
        AnnotatedField annotatedField = optional.get().getField();
        Class<?> fieldType = annotatedField.getRawType();
        Field field = annotatedField.getAnnotated();
        Type genericType = field.getGenericType();
        extracted(context, fieldType, genericType);
    }

    private void extracted(ModelPropertyContext context, Class<?> fieldType, Type genericType) {
        if (Enum.class.isAssignableFrom(fieldType)) {
            ApiEnum apiEnum = AnnotationUtils.findAnnotation(fieldType, ApiEnum.class);
            if (null != apiEnum) {
                Object[] enumConstants = fieldType.getEnumConstants();
                List<String> displayValues = getDisplayValues(apiEnum, enumConstants);
                String joinText = " [" + String.join("; ", displayValues) + "]";
                try {
                    Field descField = PropertySpecificationBuilder.class.getDeclaredField("description");
                    descField.setAccessible(true);
                    joinText = descField.get(context.getSpecificationBuilder()) + joinText;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                PropertySpecificationBuilder specificationBuilder = context.getSpecificationBuilder();
                specificationBuilder.description(joinText);
            }
        } else if (List.class.isAssignableFrom(fieldType)) {
            if (null == genericType) return;
            if (genericType instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                if (actualTypeArgument instanceof Class) {
                    Class<?> actualType = (Class<?>) actualTypeArgument;
                    extracted(context, actualType, null);
                }
            }
        }
    }

    private List<String> getDisplayValues(ApiEnum annotation, Object[] enumConstants) {
        if (annotation == null) {
            return new ArrayList<>();
        }
        String code = annotation.code();
        String value = annotation.value();
        return Arrays.stream(enumConstants).filter(Objects::nonNull).map(item -> {
            Class<?> currentClass = item.getClass();
            Field codeField = ReflectionUtils.findField(currentClass, code);
            assert codeField != null;
            ReflectionUtils.makeAccessible(codeField);
            Object codeStr = ReflectionUtils.getField(codeField, item);

            Field descField = ReflectionUtils.findField(currentClass, value);
            assert descField != null;
            ReflectionUtils.makeAccessible(descField);
            Object descStr = ReflectionUtils.getField(descField, item);

            return codeStr + "-" + descStr;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
