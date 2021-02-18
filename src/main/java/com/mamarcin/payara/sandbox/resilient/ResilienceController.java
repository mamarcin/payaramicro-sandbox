package com.mamarcin.payara.sandbox.resilient;

import java.time.temporal.ChronoUnit;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("/resilience")
@ApplicationScoped
public class ResilienceController {

    @Fallback(fallbackMethod = "fallback") // better use FallbackHandler
    @Timeout(500)
    @Path("/timeout")
    @GET
    public String checkTimeout() {
        try {
            Thread.sleep(700L);
        } catch (InterruptedException e) {
            //
        }
        return "Never from normal processing";
    }

    public String fallback() {
        return "Fallback answer due to timeout";
    }

    @CircuitBreaker(successThreshold = 2, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 50000)
    @Retry(retryOn = {UnsupportedOperationException.class}, maxRetries = 10)
    @Path("/circuitbreaker")
    @GET
    public String checkCircuitBreaker() {
        Random random = new Random();

        if(random.nextInt() % 2 == 0) {
            throw new UnsupportedOperationException();
        }
        return "Never from normal processing";
    }
}
