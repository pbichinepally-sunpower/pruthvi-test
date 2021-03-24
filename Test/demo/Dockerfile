FROM maven:3.5-jdk-8-alpine
WORKDIR /root/hello-world
COPY . /root/hello-world
RUN mvn clean package -DskipTests=true
CMD java -jar target/*.jar
