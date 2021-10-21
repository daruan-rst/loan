FROM openjdk:11

EXPOSE 5080

ADD build/libs/loan-0.0.1.jar loan-0.0.1.jar

ENTRYPOINT ["java","-jar","loan-0.0.1.jar"]