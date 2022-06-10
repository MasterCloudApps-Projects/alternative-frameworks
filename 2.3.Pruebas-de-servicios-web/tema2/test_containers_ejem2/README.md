# Quarkus Project

To run, execute this:

```
docker run --rm -d -p 3306:3306 --name mysql -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=anuncios mysql:latest
```

```
quarkus dev
```

Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/

To run test:

```
quarkus dev test
```
