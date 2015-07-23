package org.muckingabout.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Created by CLHACKNE on 23/07/2015.
 */
public class ExampleRoute extends RouteBuilder {


    private String mode;

    private int port;

    public void configure () {
        restConfiguration().component(getMode()).bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/rest").port(getPort()).host("localhost");

            rest("/rest")
                    .get("/hello").to("direct:hello")
                    .get("/bye").consumes("application/json").to("direct:bye")
                    .post("/bye").to("mock:update");

            from("direct:hello")
                    .transform().constant("Hello World");
            from("direct:bye")
                    .transform().constant("Bye World");
        }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
