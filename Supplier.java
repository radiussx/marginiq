spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/marginiq}
    username: ${SPRING_DATASOURCE_USERNAME:marginiq}
    password: ${SPRING_DATASOURCE_PASSWORD:marginiq}
  jpa:
    hibernate:
      ddl-auto: update
    open-in-view: false
  data:
    redis:
      host: ${SPRING_DATA_REDIS_HOST:localhost}
      port: 6379

springdoc:
  swagger-ui:
    path: /swagger-ui.html
server:
  port: 8080
