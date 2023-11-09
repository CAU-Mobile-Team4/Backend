# 베이스 이미지 선택
FROM openjdk:17-oracle

# 작업 디렉토리 설정
WORKDIR /app

# Gradle을 통해 빌드한 jar 파일을 Docker 이미지 내로 복사
COPY ./build/libs/*.jar /app/app.jar

# 컨테이너가 시작될 때 실행될 명령어 설정
CMD ["java", "-jar", "/app/app.jar"]
