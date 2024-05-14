FROM maven:3-openjdk-17

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/Modern-kids-0.0.1-SNAPSHOT.jar"]

