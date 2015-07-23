package org.muckingabout.routes;

import com.jayway.restassured.response.Response;
import org.apache.camel.main.Main;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.muckingabout.MonitoringApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.core.Is.is;

/**
 * @author chackney
 */
public abstract class RestRouteTest {

    private static MonitoringApplication app;
    private static Main camelStart;

    public boolean includeServlet = false;

    @Before
    public void setup() throws Exception{
        camelStart = new Main();
        ExampleRoute route = getExampleRoute();
        camelStart.addRouteBuilder(route);
        camelStart.start();
        app = new MonitoringApplication();
        app.configureWebServer(includeServlet);
        app.startWebServer();
    }

    public abstract ExampleRoute getExampleRoute();

    @Test
    public void testHelloWorld() {
        final StringBuilder getRequestUrl = new StringBuilder()
                .append("http://").append("localhost").append(":").append("8080")
                .append("/rest/hello");

        final Response response = get(getRequestUrl.toString());

        assertThat(response.getStatusCode(), is(HttpStatus.SC_OK));

        final String responseBody = response.body().asString();
        assertEquals("Response is equal: ", "\"Hello World\"", responseBody);
    }

    @After
    public void tearDown() throws Exception {
        app.shutdown();
        camelStart.shutdown();
    }


}
