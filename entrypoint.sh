#!/bin/sh

java $JAVA_OPTS \
-javaagent:/lib/opentelemetry-javaagent.jar \
-Dotel.service.name=orders-api \
-jar app.jar