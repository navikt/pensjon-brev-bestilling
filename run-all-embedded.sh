#!/bin/bash

PROFILE='embeddedkafka'

mvn -f embedded-kafka/ spring-boot:run & 
mvn -f brev-bestilling-boot/ spring-boot:run -Dspring-boot.run.profiles=$PROFILE & 
mvn -f prepare-brevdata-grunnlag/ spring-boot:run -Dspring-boot.run.profiles=$PROFILE 

