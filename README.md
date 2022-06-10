# Alternative Frameworks
This repository contains examples of MasterCloudApps but in alternative frameworks

## Quarkus

##### Getting started

Go to https://quarkus.io/get-started/ and follow the steps:

```
sdk install quarkus
```

You will need sdkman installed for quarkus if not installed via CLI (https://sdkman.io/install). It is highly recomendable doing this way because of its dependency with GraalVM.


```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
sdk install java 22.1.0.r17-grl
```

You can also install quarkus via CLI:

```
curl -Ls https://sh.jbang.dev | bash -s - trust add https://repo1.maven.org/maven2/io/quarkus/quarkus-cli/
curl -Ls https://sh.jbang.dev | bash -s - app install --fresh --force quarkus@quarkusio
```

Then you can start a project:

```
quarkus create && cd code-with-quarkus
```

##### Quarkus and GraalVM compatible versions

```
java -version
```

openjdk version "17.0.3" 2022-04-19 \
OpenJDK Runtime Environment GraalVM CE 22.1.0 (build 17.0.3+7-jvmci-22.1-b06) \
OpenJDK 64-Bit Server VM GraalVM CE 22.1.0 (build 17.0.3+7-jvmci-22.1-b06, mixed mode, sharing)

```
quarkus version
```

Quarkus: 2.7.6.Final

## NextJS
