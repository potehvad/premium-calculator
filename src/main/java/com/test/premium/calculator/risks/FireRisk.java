package com.test.premium.calculator.risks;

import com.test.premium.calculator.model.Policy;
import com.test.premium.calculator.model.RiskType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FireRisk extends CoefficientCalculation implements IRisk {
    private final RiskType CLASS_RISK_TYPE = RiskType.FIRE;
    private ConditionRanges conditions;


    public FireRisk() {
        //Init condition here
        this.conditions = new ConditionRanges();
        conditions.add(new RangedCoefficient(0, 100, 0.014));
        conditions.add(new RangedCoefficient(100.01, 1000000, 0.024));
    }

    public FireRisk(ConditionRanges conditions) {
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
