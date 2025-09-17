package ru.rmntim;

import com.fastcgi.FCGIInterface;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Access-Control-Allow-Origin: *
            Access-Control-Allow-Methods: POST, GET, OPTIONS
            Access-Control-Allow-Headers: Content-Type
            Content-Length: %d
            
            %s
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Access-Control-Allow-Origin: *
            Content-Length: %d
            
            %s
            """;
    private static final String RESULT_JSON = """
            {
                "time": "%s",
                "now": "%s",
                "result": %b,
                "x": %d,
                "y": %.2f,
                "r": %.2f
            }
            """;
    private static final String ERROR_JSON = """
            {
                "now": "%s",
                "reason": "%s"
            }
            """;

    public static void main(String[] args) {
        var fcgi = new FCGIInterface();
        while (fcgi.FCGIaccept() >= 0) {
            try {
                var queryParams = System.getProperties().getProperty("QUERY_STRING");
                var params = new Params(queryParams);

                var startTime = Instant.now();
                var result = calculate(params.getX(), params.getY(), params.getR());
                var endTime = Instant.now();

                var json = String.format(RESULT_JSON, ChronoUnit.NANOS.between(startTime, endTime) + " ns", LocalDateTime.now(), result, params.getX(), params.getY(), params.getR());
                var response = String.format(HTTP_RESPONSE, json.getBytes(StandardCharsets.UTF_8).length, json);
                System.out.println(response);
            } catch (ValidationException e) {
                var json = String.format(ERROR_JSON, LocalDateTime.now(), e.getMessage());
                var response = String.format(HTTP_ERROR, json.getBytes(StandardCharsets.UTF_8).length, json);
                System.out.println(response);
            }
        }
    }

    private static boolean calculate(int x, float y, float r) {
        // Первая четверть (x > 0, y > 0) - нет области
        if (x > 0 && y > 0) {
            return false;
        }

        // Вторая четверть (x <= 0, y >= 0) - четверть круга
        if (x <= 0 && y >= 0) {
            return (x * x + y * y) <= (r * r);
        }

        // Третья четверть (x <= 0, y <= 0) - треугольник
        if (x <= 0 && y <= 0) {
            return x >= -r && y >= -r/2 && y >= x/2 - r/2;
        }

        // Четвертая четверть (x >= 0, y <= 0) - прямоугольник
        if (x >= 0 && y <= 0) {
            return x <= r/2 && y >= -r;
        }

        return false;
    }
}