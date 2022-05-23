package com.test.premium.calculator.risks;

import com.test.premium.calculator.model.Policy;
import com.test.premium.calculator.model.PolicySubObject;
import com.test.premium.calculator.model.RiskType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class CoefficientCalculation {

    protected BigDecimal calculate(RiskType riskType, ConditionRanges conditions, Policy policy) {
        BigDecimal sum = sumObjects(riskType, policy);
        if (sum.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;

        RangedCoefficient condition = conditions.findConditionByAmount(sum);

        return sum.multiply(condition.getCoefficient()).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal sumObjects (RiskType riskType, Policy policy) {
        BigDecimal sumOfObjects = BigDecimal.ZERO;

        for (var policyObject: policy.getObjects()) {
            var subObjectsSum=  policyObject.getSubObjects()
                    .stream()
                    .filter(policySubObject -> policySubObject.getRiskType() == riskType)
                    .map(PolicySubObject::getSumInsured)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            sumOfObjects = sumOfObjects.add(subObjectsSum);
        }

        return sumOfObjects;
    }

}
