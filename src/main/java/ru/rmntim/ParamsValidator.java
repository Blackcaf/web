package ru.rmntim;

import java.util.Map;

public class ParamsValidator {

    public void validateParams(Map<String, String> params) throws ValidationException {
        validateX(params.get("x"));
        validateY(params.get("y"));
        validateR(params.get("r"));
    }

    private void validateX(String x) throws ValidationException {
        if (x == null || x.isEmpty()) {
            throw new ValidationException("x is invalid");
        }

        try {
            var xx = Integer.parseInt(x);
            if (xx < -3 || xx > 5) {
                throw new ValidationException("x has forbidden value");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("x is not a number");
        }
    }

    private void validateY(String y) throws ValidationException {
        if (y == null || y.isEmpty()) {
            throw new ValidationException("y is invalid");
        }

        try {
            var yy = Float.parseFloat(y);
            if (yy < -3 || yy > 5) {
                throw new ValidationException("y has forbidden value");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("y is not a number");
        }
    }

    private void validateR(String r) throws ValidationException {
        if (r == null || r.isEmpty()) {
            throw new ValidationException("r is invalid");
        }

        try {
            var rr = Float.parseFloat(r);
            if (rr < 1 || rr > 3) {
                throw new ValidationException("r has forbidden value");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("r is not a number");
        }
    }
}