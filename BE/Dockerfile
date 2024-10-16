FROM gradle:8.3.0-jdk17

WORKDIR /opt/app  # Working directory within the container

COPY ./build/libs/BE-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar BE-0.0.1-SNAPSHOT.jar"]
