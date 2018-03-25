# Spring 5, Embedded Tomcat 8, and Gradle: a Quick Tutorial
This repository accompanies the Spring 5, Embedded Tomcat 8, and Gradle: a Quick Tutorial article on Auth0's blog. Head there to learn how to embed Tomcat 8 on a Spring 5 project managed by Gradle.

https://github.com/auth0-blog/embedded-spring-5

Useful Commands
# create uber jar
./gradlew clean shadowJar

# run uber jar
java -jar build/libs/spring-5-demo-1.0-SNAPSHOT-all.jar

# access
http://localhost:8080/test 