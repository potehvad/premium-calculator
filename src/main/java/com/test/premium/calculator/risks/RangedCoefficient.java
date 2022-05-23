package com.test.premium.calculator.risks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@AllArgsConstructor
@Data
public class RangedCoefficient {
    private BigDecimal from;
    private BigDecimal to;
    private BigDecimal coefficient;


    public RangedCoefficient(double from, double to, double coefficient) {
        this.from = new BigDecimal(from);
        this.to = new BigDecimal(to);
        this.coefficient = new BigDecimal(coefficient);
    }
}
