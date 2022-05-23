package com.test.premium.calculator.risks;

import com.test.premium.calculator.model.Policy;
import com.test.premium.calculator.model.RiskType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TheftRisk extends CoefficientCalculation implements IRisk {
    private final RiskType CLASS_RISK_TYPE = RiskType.THEFT;
    private ConditionRanges conditions;

    public TheftRisk() {
        //Init condition here
        this.conditions = new ConditionRanges();
        conditions.add(new RangedCoefficient(0, 14.99, 0.11));
        conditions.add(new RangedCoefficient(15, 100000, 0.05));
    }

    public TheftRisk(ConditionRanges conditions) {
        //Init condition here
        this.conditions = conditions;
    }

    @Override
    public RiskType type() {
        return this.CLASS_RISK_TYPE;
    }

    @Override
    public BigDecimal calculate(Policy policy) {
        return super.calculate(this.CLASS_RISK_TYPE, this.conditions, policy);
    }
}
