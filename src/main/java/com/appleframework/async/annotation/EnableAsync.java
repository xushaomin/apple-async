package com.appleframework.async.annotation;

import com.appleframework.async.config.BootstrapConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author woter
 * @date 2017-8-18 上午10:50:49
 */
@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({BootstrapConfiguration.class})
public @interface EnableAsync {

    boolean proxyTargetClass() default false;

}
 