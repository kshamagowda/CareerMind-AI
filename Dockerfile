FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8081

CMD ["sh", "-c", "java -jar target/careermind-0.0.1-SNAPSHOT.jar --server.port=${PORT:-8081}"]