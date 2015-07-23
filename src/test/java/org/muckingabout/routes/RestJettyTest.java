package org.muckingabout.routes;

/**
 * Set the transport type to be servlet
 */
public class RestJettyTest extends RestRouteTest {

    /**
     * Configures the test routes to use jetty.
     * Jetty is clever enough to realise there is already a jetty instance
     * which is why we can use the same port
     *
     * @return
     */
    public ExampleRoute getExampleRoute() {
        ExampleRoute route = new ExampleRoute();
        route.setPort(8080);
        route.setMode("jetty");
        return route;
    }
}
