package com.test.premium.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Premium {
    private List<PremiumRisk> risks;
    private BigDecimal premium;

}
