package org.ubiqity;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jboss.logging.Logger;

@Path("/rc")
public class ProxyCommandResource {
    
    private static final Logger LOG = Logger.getLogger(ProxyCommandResource.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .executor(executorService)
            .version(HttpClient.Version.HTTP_2)
            .build();

    @GET
    public String get(@QueryParam("command") String command) {
        LOG.info("received command :"+command);
        try {
            this.httpClient
                    .send(
                            HttpRequest.newBuilder()
                                    .GET()
                                    .uri(URI.create("http://192.168.100.101/?state=" + command))
                                    .build(),
                            BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{\"result\":\"ko\"}";
        }
        return "{\"result\":\"ok\"}";
    }
}
