# NestJS Project

## Installation

```
npm install
```

## Running the app

```
docker run --rm --name mongodb -d -p 27017:27017 mongo
```

```
 docker run --rm -d -p 3306:3306 --name mysql -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=posts mysql:latest
```

Or:

```
nest start
```
