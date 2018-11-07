package com.csjscm.core.framework.elasticsearch.annotation;

import com.csjscm.core.framework.elasticsearch.constant.IndexFiledTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElasticsearchIndexField {
    String fieldName() default "";
    IndexFiledTypeEnum indexType() default IndexFiledTypeEnum.KEYWORD;
}
