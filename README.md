# Service Validation with @Valid

A DTO validation with `@Valid` in service layer using Aspect.


## Build and Install

```bash
$ ./gradlew clean build
```

## Usage

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

```java
@Component
public class MyComponent {

    @ServiceValidation
    public void doSomething(@Valid DTO dto) {

    }
}
```
```java
@Service
public class MyServiceImpl implements MyService {

    @Override
    @ServiceValidation
    public void doSomething(@Valid DTO dto) {

    }
}
```