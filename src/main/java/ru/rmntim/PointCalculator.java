package ru.rmntim;

public class PointCalculator {

    public boolean calculate(int x, float y, float r) {
        // Первая четверть (x > 0, y > 0) - нет области
        if (x > 0 && y > 0) {
            return false;
        }

        // Вторая четверть (x <= 0, y >= 0) - четверть круга
        if (x <= 0 && y >= 0) {
            return isInCircleQuarter(x, y, r);
        }

        // Третья четверть (x <= 0, y <= 0) - треугольник
        if (x <= 0 && y <= 0) {
            return isInTriangle(x, y, r);
        }

        // Четвертая четверть (x >= 0, y <= 0) - прямоугольник
        if (x >= 0 && y <= 0) {
            return isInRectangle(x, y, r);
        }

        return false;
    }

    private boolean isInCircleQuarter(int x, float y, float r) {
        return (x * x + y * y) <= (r * r);
    }

    private boolean isInTriangle(int x, float y, float r) {
        return x >= -r && y >= -r/2 && y >= x/2 - r/2;
    }

    private boolean isInRectangle(int x, float y, float r) {
        return x <= r/2 && y >= -r;
    }
}