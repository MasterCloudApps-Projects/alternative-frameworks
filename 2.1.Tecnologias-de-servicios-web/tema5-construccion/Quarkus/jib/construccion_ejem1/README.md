# Quarkus Project

## Jib fast-jar Build

```
mvn package
```

```
docker run --rm -d -p 8080:8080 --name construccion_ejem1 $HOSTNAME/construccion_ejem1:1.0.0-SNAPSHOT
```

Now visit: http://localhost:8080/greeting
