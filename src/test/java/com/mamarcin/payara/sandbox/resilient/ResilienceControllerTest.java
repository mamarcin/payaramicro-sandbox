package com.mamarcin.payara.sandbox.resilient;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.junit.jupiter.api.Test;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

@MicroShedTest
public class ResilienceControllerTest {

    @Container
    public static ApplicationContainer app = new ApplicationContainer().withAppContextRoot("/payara-sandbox")
            .withReadinessPath("/payara-sandbox/data/resilience/circuitbreaker").withHttpPort(8080);

    @RESTClient
    public static ResilienceController resilienceController;

    @Test
    public void testCircuitBreaker() {
        try {
            resilienceController.checkCircuitBreaker();
        } catch (CircuitBreakerOpenException ex) {
            assertTrue(true, "Expected exception");
        }

        fail();
    }

}
