package com.test.premium.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PremiumRisk {
    private RiskType riskType;
    private BigDecimal sum;
}
