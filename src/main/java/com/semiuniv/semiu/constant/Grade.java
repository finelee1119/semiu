package com.semiuniv.semiu.constant;

public enum Grade {
    A_PLUS(4.5),
    A_ZERO(4.0),
    B_PLUS(3.5),
    B_ZERO(3.0),
    C_PLUS(2.5),
    C_ZERO(2.0),
    D_PLUS(1.5),
    D_ZERO(1.0),
    F(0.0);

    private double value;

    Grade(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    //호출 예시: Grade.A_PLUS.getValue()
}
