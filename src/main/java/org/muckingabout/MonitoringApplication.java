package org.muckingabout;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.component.swagger.DefaultCamelSwaggerServlet;
import org.apache.camel.component.swagger.RestSwaggerCorsFilter;

import org.muckingabout.http.JettyServerImpl;


/**
 * @author chackey
 */
public class MonitoringApplication {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MonitoringApplication.class);
    /**
     * The server were using
     * Again in future change this to factory/discovery method then
     * update this.
     */
    private HttpServerWrapper server = new JettyServerImpl(8080);

    private MonitoringApplication application = null;

    public MonitoringApplication() {
    }


    public void configureWebServer() throws Exception{
        configureWebServer(false);
    }
    public void configureWebServer(boolean configureCamelServlet) throws Exception{
        if(configureCamelServlet) {
            this.server.addServlet("CamelServlet", "/rest/*", new CamelHttpTransportServlet(), null);
        }
        this.server.addServlet("HystrixServlet", "/hystrix.stream/*", new HystrixMetricsStreamServlet(), null);
        this.server.addServlet("apiServlet", "/api/*", new DefaultCamelSwaggerServlet(), createSwaggerServletinitConfig());
        String[] paths = {"/api/*", "/rest/*"};
        this.server.addFilter("restSwaggerCorsFilter", new RestSwaggerCorsFilter(), paths);
    }

    public void startWebServer() throws Exception{
        this.server.startServer();

    }

    public void block() throws Exception {
        server.block();
    }

    public void shutdown() throws Exception{
        this.server.stopServer();
    }

    public static void main(String[] args) throws Exception {
        new MonitoringApplication().startMonitoring();

    }

    public  void startMonitoring() throws Exception {

        try {
            this.application.configureWebServer();
            this.application.startWebServer();
       } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            application.shutdown();
        }
    }



    private static Map<String, String> createSwaggerServletinitConfig() throws Exception{

        InputStream in = MonitoringApplication.class.getResourceAsStream("/swagger_api.properties");

        Properties configParameters = new Properties();

        if(in==null){
            log.info("To customise properties, please include swagger_api.properties file in the classpath");
            log.info("We are using defaults, services should be /rest, api path is /api");
            configParameters.put("base.path", "rest");
            configParameters.put("api.path", "api");

        } else {
            // Override defaults
            configParameters.load(in);
        }

        return (Map) configParameters;
    }


}
