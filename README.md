sample-monitoring-app

A library that contains the configure for the Swagger servlet, Hystrix servlet and optionaly the Camel
Endpoint servlet, it runs in a jetty server.

For Examples, on how to add it to an applicataion see the tests.

The swagger servlet expects routes to be described with the Camel Rest DSL.

By default the swagger api will be at
http://localhost:8080/api, you can point swagger at it.

To change the configuration add swagger_api.propertes file to the root of the classpath.


