# Quarkus Project

## Buildpacks native Build

There is no packeto buildpacks for quarkus yet, see:

https://github.com/quarkusio/quarkus/issues/23559
https://github.com/quarkusio/quarkus-buildpacks/issues/16

There is also a pull request with initial code in paketo-buildpacks repository at this moment:

https://github.com/paketo-buildpacks/quarkus
https://github.com/paketo-buildpacks/quarkus/pull/1

There are some WIP but does not work with maven-compiler-plugin release 17 nor Java 17:

https://github.com/quarkusio/quarkus-buildpacks

When it is solved:

```
mvn package
```

```
docker run --rm -d -p 8080:8080 --name construccion_ejem7 $HOSTNAME/construccion_ejem7:1.0.0-SNAPSHOT
```

Now visit: http://localhost:8080/greeting
