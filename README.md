# Service Validation

NullSafe and DTO validation for Javax Validation in service layer using Aspect.

## Spring Boot projects

Service Validation provides a `ServiceValidationAutoConfiguration.class` that allowed Auto Configuration mechanism.

>Spring Boot auto-configuration attempts to automatically configure your Spring application based on the jar dependencies that you have added. 
><cite>https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-auto-configuration.html</cite> 

## Build and Install

```bash
$ ./gradlew clean build
```

## Dependency

```xml
<dependency>
    <groupId>io.github.andrelugomes</groupId>
    <artifactId>service-validator</artifactId>
    <version>1.0.0</version>
</dependency>
```

```groovy
compile 'io.github.andrelugomes:service-validator:1.0.0'
```

### DTO

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
### Usage

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

### Don't do that! ¬¬

```java
@Component
public class MyComponent {

    @ServiceValidation(nullSafe = false, javaxValidation = false) //Don't do that!
    public void youDontNeedThis(DTO dto) {

    }
}
```

