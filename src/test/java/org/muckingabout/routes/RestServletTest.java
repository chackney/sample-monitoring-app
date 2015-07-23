package org.muckingabout.routes;

/**
 * Set the transport type to be servlet
 */
public class RestServletTest extends RestRouteTest {

    public ExampleRoute getExampleRoute() {
        ExampleRoute route = new ExampleRoute();
        route.setPort(8080);
        route.setMode("servlet");
        this.includeServlet = true; //this is ugly, but its just a test.
        return route;
    }
}
