package com.nexign.demoPrometeusApp.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class EchoController {
    private final Random random = new Random();
    private final Counter requestCounter;

    public EchoController(MeterRegistry registry) {
        this.requestCounter = Counter.builder("app.api.requests.total")
                .description("Total number of API requests")
                .register(registry);
    }
    @Timed(
            value = "app.api.duration",
            description = "Duration of API calls",
            histogram = true,
            percentiles = {0.5, 0.9, 0.99}
    )
    @GetMapping("/**")
    public ResponseEntity<String> echoPath(HttpServletRequest request) throws InterruptedException {
        requestCounter.increment();
        Thread.sleep(random.nextInt(2000));
        return ResponseEntity.ok(request.getRequestURI());
    }
}
