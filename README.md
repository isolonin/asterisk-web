For starting use folow docker-compose.yml:


```version: '3.5'

services:
  java:
    container_name: asterisk-web
    image: openjdk:17-jdk
    restart: always
    volumes:
      - /var/spool/asterisk/monitor/:/monitor
      - ./logs:/logs
      - ./application.properties:/application.properties
      - ./asterisk-web-0.1.jar:/asterisk-web.jar
    environment:
      - TZ=Europe/Moscow
    ports:
        - 8080:8080
    command: ["java", "-jar", "asterisk-web.jar"]
