package com.test.premium.calculator.risks;

import com.test.premium.calculator.exceptions.PremiumCalculatorAppException;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConditionRanges {
    private List<RangedCoefficient> condition = new ArrayList<>();

    public void add(RangedCoefficient rangedCoefficient) {
        condition.add(rangedCoefficient);
    }

    public RangedCoefficient findConditionByAmount (BigDecimal amnt) {
        return condition.stream()
                .filter(cond -> cond.getFrom().compareTo(amnt) < 0 && cond.getTo().compareTo(amnt) >= 0)
                .findFirst()
                .orElseThrow(() -> new PremiumCalculatorAppException("No condition found"));

    }

}
