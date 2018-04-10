#!/bin/bash

commands=(
  "./mvnw clean test"
  "./demos/spring-boot-auto-configuration-demo/mvnw test -f demos/spring-boot-auto-configuration-demo"
  "./demos/spring-boot-web-auto-configuration-demo/mvnw test -f demos/spring-boot-web-auto-configuration-demo"
  "./demos/spring-5-hibernate-validator-demo/gradlew -p demos/spring-5-hibernate-validator-demo test"
)

for cmd in "${commands[@]}"
do
  echo " ========== Running $cmd =========="
  
  eval $cmd

  ERROR_CODE=$?
  if [ ${ERROR_CODE} != 0 ]; then
    echo " ========== BUILD FAILURE. Tests are broken. =========="
    exit 1
  fi
done
