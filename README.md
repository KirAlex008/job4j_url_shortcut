# job4j_url_shortcut

Project description
The is a simple url shortcut service. A user may register a site and get links using this service. The service can count the amount of redirecting and give this info to user.

Used technologies
Java 14
Spring Boot 2
Spring Security & JWT authorization
Spring Data JPA
PostgreSQL

Using REST API
Register site
curl --location --request POST 'http://localhost:8080/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "site": "vk.com"
}'
Getting token
curl --location --request POST 'http://localhost:8080/login' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "your_login",
    "password": "your_password"
}'
Convert link
curl --location --request POST 'http://localhost:8080/convert' \
--header 'Authorization: Bearer your_token\
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "http://job4j.ru:8888/TrackStudio/staticframeset.html#253134"
}'
Getting statistic
curl --location --request GET 'http://localhost:8080/statistic' \
--header 'Authorization: Bearer your_token\
