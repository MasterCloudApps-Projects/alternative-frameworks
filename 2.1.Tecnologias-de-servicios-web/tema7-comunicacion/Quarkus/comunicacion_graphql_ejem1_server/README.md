# Quarkus Project

This project works as GraphQL API server. It includes a Postman Collection to be tested. To run, execute this:

```
quarkus dev
```

Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8090/q/dev/

To use the Playground visit: http://localhost:8090/q/graphql-ui/

You can set Graphql endpoint with the property: quarkus.smallrye-graphql.root-path which is "graphql" by default

To run test:

```
quarkus dev test
```
