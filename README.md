# Service Validation with @Valid

A DTO validation with `@Valid` in service layer using Aspect.


## Build and Install

```bash
$ ./gradlew clean build
```

## Dependency

```xml
<dependency>
    <groupId>br.com.andreluisgomes</groupId>
    <artifactId>service-validator</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

```groovy
compile 'br.com.andreluisgomes:service-validator:1.0-SNAPSHOT'
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

    @ServiceValidation(nullSafe = false, javaxValidation = false)
    public void doSomething(DTO dto) {

    }
}
```

