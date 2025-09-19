package ru.rmntim;

import com.fastcgi.FCGIInterface;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WebServer {
    private final ResponseFormatter responseFormatter;
    private final PointCalculator calculator;
    private final FCGIInterface fcgi;

    public WebServer() {
        this.responseFormatter = new ResponseFormatter();
        this.calculator = new PointCalculator();
        this.fcgi = new FCGIInterface();
    }

    public void start() {
        while (fcgi.FCGIaccept() >= 0) {
            handleRequest();
        }
    }

    private void handleRequest() {
        try {
            var queryParams = System.getProperties().getProperty("QUERY_STRING");
            var params = new Params(queryParams);

            var startTime = Instant.now();
            var result = calculator.calculate(params.getX(), params.getY(), params.getR());
            var endTime = Instant.now();

            var executionTime = ChronoUnit.NANOS.between(startTime, endTime) + " ns";
            var response = responseFormatter.formatSuccessResponse(
                    executionTime,
                    LocalDateTime.now(),
                    result,
                    params.getX(),
                    params.getY(),
                    params.getR()
            );

            System.out.println(response);
        } catch (ValidationException e) {
            var response = responseFormatter.formatErrorResponse(LocalDateTime.now(), e.getMessage());
            System.out.println(response);
        }
    }
}