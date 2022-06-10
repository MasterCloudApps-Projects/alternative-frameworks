# Quarkus Project

To run, execute this:

```
docker run --rm -d -p 3306:3306 --name mysql -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=users mysql:latest
quarkus dev
```

To run test:

```
quarkus dev test
```
