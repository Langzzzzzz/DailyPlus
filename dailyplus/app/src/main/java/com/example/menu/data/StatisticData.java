package com.example.menu.data;

public class StatisticData {
    private String category;
    private double amount;
    private float percent;
    private int color;
    private float angle;

    public StatisticData(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public float getPercent() {
        return percent;
    }

    public String getCategory() {
        return category;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        this.angle = this.percent * 360;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public float getAngle() {
        return angle;
    }
}
