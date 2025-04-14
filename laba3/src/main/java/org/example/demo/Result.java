package org.example.demo;

public class Result {
    private Double x;
    private Double y;
    private Double r;
    private boolean hit;

    public boolean isHit() {
        return hit;
    }


    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getR() {
        return r;
    }


    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setR(Double r) {
        this.r = r;
    }


    @Override
    public String toString() {
        return "Result{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", isHit=" + hit +
                '}';
    }

    public Result(Double x, Double y, Double r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    public static boolean dotIsHit(double x, double y, double r) {
        double halfR = r / 2.0;

        // Проверяем попадание в треугольник (первая четверть)
        boolean inTriangle = (x >= 0 && y >= 0 && y <= -0.5 * x + halfR);


        // Проверяем попадание в прямоугольник (третья четверть)
        boolean inRectangle = (x <= 0 && y <= 0 && x >= -r / 2 && y >= -r);

        // Проверяем попадание в четверть круга (четвертая четверть)
        boolean inCircle = (x >= 0 && y <= 0 && x * x + y * y <= (r / 2) * (r / 2));

        return inTriangle || inRectangle || inCircle;
    }



}
