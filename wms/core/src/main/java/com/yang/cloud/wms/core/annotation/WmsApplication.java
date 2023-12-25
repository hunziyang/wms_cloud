package com.yang.cloud.wms.core.annotation;

import com.yang.cloud.wms.Wms;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication(scanBasePackageClasses = Wms.class,
        nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public @interface WmsApplication {
}
