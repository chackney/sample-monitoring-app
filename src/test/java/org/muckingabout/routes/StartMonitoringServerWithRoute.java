package org.muckingabout.routes;

import org.apache.camel.main.Main;
import org.muckingabout.MonitoringApplication;

/**
 * Created by CLHACKNE on 23/07/2015.
 */
public class StartMonitoringServerWithRoute {

    public static void main(String ... args) {
        try {
            Main camelStart = new Main();
            ExampleRoute route = new ExampleRoute();
            route.setMode("jetty");
            route.setPort(8080);
            camelStart.addRouteBuilder(route);
            camelStart.start();
            MonitoringApplication app = new MonitoringApplication();
            app.configureWebServer(false);
            app.startWebServer();
            app.block();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
