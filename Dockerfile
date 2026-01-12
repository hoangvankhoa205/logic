# ---- build stage ----
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . /app
    
# Tạo JAR, bỏ test cho nhanh
RUN mvn -q -DskipTests package
    
# ---- runtime stage ----
FROM eclipse-temurin:17-jre-alpine
    
WORKDIR /app
# Copy JAR ra runtime image
COPY --from=build /app/target/logic-0.0.1-SNAPSHOT.jar /app/app.jar
    
EXPOSE 8081
    
    
ENTRYPOINT ["java", "-jar", "/app/app.jar" ]