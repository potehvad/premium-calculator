package com.test.premium.calculator;

import com.test.premium.calculator.model.*;
import com.test.premium.calculator.risks.ConditionRanges;
import com.test.premium.calculator.risks.FireRisk;
import com.test.premium.calculator.risks.RangedCoefficient;
import com.test.premium.calculator.risks.TheftRisk;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestMultipleRisks {

    static PremiumCalculator calculator;

    @BeforeAll
    static void init() {

        ConditionRanges fireConditions = new ConditionRanges();
        fireConditions.add(new RangedCoefficient(0, 100, 0.014));
        fireConditions.add(new RangedCoefficient(100.01, 200, 0.024));
        fireConditions.add(new RangedCoefficient(200.01, 300, 0.034));
        fireConditions.add(new RangedCoefficient(300.01, 400, 0.044));
        fireConditions.add(new RangedCoefficient(400.01, 100000, 0.054));

        ConditionRanges theftConditions = new ConditionRanges();
        theftConditions.add(new RangedCoefficient(0, 100, 0.5));
        theftConditions.add(new RangedCoefficient(100.01, 200, 0.6));
        theftConditions.add(new RangedCoefficient(200.01, 300, 0.7));
        theftConditions.add(new RangedCoefficient(300.01, 400, 0.8));
        theftConditions.add(new RangedCoefficient(400.01, 100000, 0.9));

        FireRisk fireRisk = new FireRisk(fireConditions);
        TheftRisk theftRisk = new TheftRisk(theftConditions);

        calculator = new PremiumCalculatorImpl(List.of(fireRisk, theftRisk));
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void calculatePremiumForDifferentObjects() {

        PolicySubObject pso11 = new PolicySubObject("subObj11", BigDecimal.valueOf(5.10), RiskType.FIRE);
        PolicySubObject pso12 = new PolicySubObject("subObj12", BigDecimal.valueOf(10.00), RiskType.FIRE);
        PolicySubObject pso13 = new PolicySubObject("subObj13", BigDecimal.valueOf(50.00), RiskType.THEFT);

        PolicySubObject pso21 = new PolicySubObject("subObj21", BigDecimal.valueOf(2.35), RiskType.FIRE);
        PolicySubObject pso22 = new PolicySubObject("subObj22", BigDecimal.valueOf(80.02), RiskType.THEFT);
        PolicySubObject pso23 = new PolicySubObject("subObj23", BigDecimal.valueOf(15.44), RiskType.THEFT);

        PolicyObject po1 = new PolicyObject("House", List.of(pso11, pso12, pso13));
        PolicyObject po2 = new PolicyObject("House", List.of(pso21, pso22, pso23));

        Policy policy = new Policy();
        policy.setPolicyNumber("123456");
        policy.setPolicyStatus(PolicyStatus.APPROVED);
        policy.setObjects(List.of(po1, po2));



        assertEquals(87.52, calculator.calculate(policy).getPremium().doubleValue());
    }


}
