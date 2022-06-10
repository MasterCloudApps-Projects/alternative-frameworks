# Quarkus Project

## Docker native Build

```
mvn package
```

```
docker run --rm -d -p 8080:8080 --name construccion_ejem4 $HOSTNAME/construccion_ejem4:1.0.0-SNAPSHOT
```

Now visit: http://localhost:8080/greeting
