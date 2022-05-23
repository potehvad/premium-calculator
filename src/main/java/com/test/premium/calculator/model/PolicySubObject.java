package com.test.premium.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicySubObject {
    private String name;
    private BigDecimal sumInsured;
    private RiskType riskType;

}
