<p align="left">
  <a href="https://quarkus.io" target="blank"><img src="https://design.jboss.org/quarkus/logo/final/PNG/quarkus_logo_horizontal_rgb_1280px_default.png#gh-light-mode-only" width="540" alt="Quarkus Logo" /></a>&emsp;&emsp;&emsp;&emsp;
<a href="http://nestjs.com/" target="blank"><img src="https://s3-ap-southeast-1.amazonaws.com/homepage-media/wp-content/uploads/2020/07/28093657/nestjs.png" width="200" alt="Nest Logo" /></a>
</p>

# Alternative Frameworks

This repository contains examples of MasterCloudApps but in alternative frameworks

## Quarkus

##### Getting started

Go to https://quarkus.io/get-started/ and follow the steps:

```
sdk install quarkus 2.9.2.Final
```

You will need sdkman installed for quarkus if not installed via CLI (https://sdkman.io/install)

```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
```

It is highly recomendable to install GraalVM with Quarkus beacuse allows:

- Better Performance 📈
- Shorter Boot Time ⚡
- Smaller Packages 📦
- Native Images #️⃣

```
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

And run it with:

```
quarkus dev
```

##### Quarkus and GraalVM used versions

```
java -version
```

openjdk version "17.0.3" 2022-04-19 \
OpenJDK Runtime Environment GraalVM CE 22.1.0 (build 17.0.3+7-jvmci-22.1-b06) \
OpenJDK 64-Bit Server VM GraalVM CE 22.1.0 (build 17.0.3+7-jvmci-22.1-b06, mixed mode, sharing)

```
quarkus version
```

Quarkus: 2.9.2.Final

## NestJS

##### Getting started

First, install npm if not installed before. Nodejs includes npm.

```
sudo apt install nodejs
```

Then go to https://docs.nestjs.com/ and follow the steps:

```
npm install -g @nestjs/cli
```

Now you can start a project:

```
nest new project-name && cd project-name
```

And run it with:

```
npm run start
```

Or:

```
nest start
```


##### NodeJS and npm used versions


```
node --version
```

v9.0.1


```
npm --version
```

9.1.3
