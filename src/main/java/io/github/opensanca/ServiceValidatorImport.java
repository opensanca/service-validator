package io.github.opensanca;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import(ServiceValidatorAutoConfiguration.class)
public class ServiceValidatorImport {
}
