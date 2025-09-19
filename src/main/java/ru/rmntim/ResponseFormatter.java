package ru.rmntim;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ResponseFormatter {
    private final String HTTP_RESPONSE_TEMPLATE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Access-Control-Allow-Origin: *
            Access-Control-Allow-Methods: POST, GET, OPTIONS
            Access-Control-Allow-Headers: Content-Type
            Content-Length: %d
            
            %s
            """;

    private final String HTTP_ERROR_TEMPLATE = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Access-Control-Allow-Origin: *
            Content-Length: %d
            
            %s
            """;

    private final String RESULT_JSON_TEMPLATE = """
            {
                "time": "%s",
                "now": "%s",
                "result": %b,
                "x": %d,
                "y": %.2f,
                "r": %.2f
            }
            """;

    private final String ERROR_JSON_TEMPLATE = """
            {
                "now": "%s",
                "reason": "%s"
            }
            """;

    public String formatSuccessResponse(String executionTime, LocalDateTime now, boolean result,
                                        int x, float y, float r) {
        var json = String.format(RESULT_JSON_TEMPLATE, executionTime, now, result, x, y, r);
        return String.format(HTTP_RESPONSE_TEMPLATE,
                json.getBytes(StandardCharsets.UTF_8).length, json);
    }

    public String formatErrorResponse(LocalDateTime now, String reason) {
        var json = String.format(ERROR_JSON_TEMPLATE, now, reason);
        return String.format(HTTP_ERROR_TEMPLATE,
                json.getBytes(StandardCharsets.UTF_8).length, json);
    }
}