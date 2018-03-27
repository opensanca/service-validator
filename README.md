# Service Validation

NullSafe and DTO validation for Bean Validation in service layer using Aspect.



## Build and Install

```bash
$ ./mvnw clean install
```

## How to use

```xml
<dependency>
    <groupId>io.github.opensanca</groupId>
    <artifactId>service-validator</artifactId>
    <version>1.0.0</version>
</dependency>
```

```groovy
compile 'io.github.opensanca:service-validator:1.0.0'
```
### Spring Boot projects
 
Service Validation provides a `ServiceValidationAutoConfiguration.class` that allowed Auto Configuration mechanism.
 
 >Spring Boot auto-configuration attempts to automatically configure your Spring application based on the jar dependencies that you have added. 
 ><cite>https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-auto-configuration.html</cite> 

### Non Spring Boot projects

Service Validation provides a `ServiceValidatorImport.class` that allowed easy way configure it.

```java
@ComponentScan(basePackages={"seu.pacote","io.github.opensanca"})
```
OR
```java
@Configuration
@ComponentScan
@Import(ServiceValidatorImport.class)
public class AppConfig {
    //...
}
```
 
### Extra configgurations

Because we use Bean Validation 1.1 [JSR 349](http://beanvalidation.org/1.1/), you have to add some provider for this especification 
like `Hibernate Validator` ou `Apache BVal`.

The absence of providers cause this erros in Spring context startup.

```bazaar
javax.validation.ValidationException: 
Unable to create a Configuration, because no Bean Validation provider could be found. 
Add a provider like Hibernate Validator (RI) to your classpath.
```
OR

```bazaar
***************************
APPLICATION FAILED TO START
***************************

Description:

The Bean Validation API is on the classpath but no implementation could be found

Action:

Add an implementation, such as Hibernate Validator, to the classpath
```

Additionally, some providers of Bean Validation uses Expression Language API, causing erros in Spring context startup too.

```bazaar
Caused by: javax.validation.ValidationException: HV000183: Unable to initialize 'javax.el.ExpressionFactory'. 
Check that you have the EL dependencies on the classpath, or use ParameterMessageInterpolator instead
```
We recommended to use  `Glassfish Web EL Implementation`

## Samples

```java
public class DTO {

    @NotNull
    private String text;

    @Max(10)
    private Integer number;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```
Validate Javax Validations Constraints and NullSafe.
```java
@Component
public class MyComponent {

    @ServiceValidation
    public void doSomething(final DTO dto) {

    }
}
```
Validate Javax Validations Constraints only.
```java
@Component
public class MyComponent {

    @ServiceValidation(nullSafe = false)
    public void doSomething(final DTO dto) {

    }
}
```
Validate NullSafe arguments only.
```java
@Component
public class MyComponent {

    @ServiceValidation(javaxValidation = false)
    public void doSomething(final DTO dto) {

    }
}
```

#### Don't do this! ¬¬

```java
@Component
public class MyComponent {

    @ServiceValidation(nullSafe = false, javaxValidation = false) //Don't do this!
    public void youDontNeedThis(DTO dto) {

    }
}
```

