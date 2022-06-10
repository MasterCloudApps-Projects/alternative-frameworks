# Quarkus Project

## Docker native distroless Build

```
mvn package
```

```
docker run --rm -d -p 8080:8080 --name construccion_ejem5 $HOSTNAME/construccion_ejem5:1.0.0-SNAPSHOT
```

Now visit: http://localhost:8080/greeting
