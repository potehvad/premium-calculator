package com.test.premium.calculator;

import com.test.premium.calculator.model.*;
import com.test.premium.calculator.risks.ConditionRanges;
import com.test.premium.calculator.risks.FireRisk;
import com.test.premium.calculator.risks.RangedCoefficient;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFireRisk {

    static FireRisk fireRisk;

    @BeforeAll
    static void init() {
        ConditionRanges conditions = new ConditionRanges();
        conditions.add(new RangedCoefficient(0, 100, 0.014));
        conditions.add(new RangedCoefficient(100.01, 200, 0.024));
        conditions.add(new RangedCoefficient(200.01, 300, 0.034));
        conditions.add(new RangedCoefficient(300.01, 400, 0.044));
        conditions.add(new RangedCoefficient(400.01, 100000, 0.054));
        fireRisk = new FireRisk(conditions);
    }

    @BeforeEach
    void setUp() {
    }

    @TestFactory
    @DisplayName("Calculate Premium for one object for different objects sums")
    Stream<DynamicTest> calculatePremiumForDifferentObjectsSums() {

        PolicySubObject pso = new PolicySubObject("obj", BigDecimal.valueOf(25.10), RiskType.FIRE);

        PolicyObject po = new PolicyObject();
        po.setName("House");
        po.setSubObjects(List.of(pso));

        Policy policy = new Policy();
        policy.setPolicyNumber("123456");
        policy.setPolicyStatus(PolicyStatus.APPROVED);
        policy.setObjects(List.of(po));

        List<Double> inData = List.of(25.00, 125.00, 200.00, 350.25, 399.99, 1001.25);
        List<Double> expected = List.of(0.35, 3.00, 4.8, 15.41, 17.60, 54.07);

        return inData.stream()
                .map(val -> DynamicTest.dynamicTest("Calculate premium for different sum",
                        () -> {
                            int i = inData.indexOf(val);
                            pso.setSumInsured(new BigDecimal(inData.get(i)));
                            BigDecimal premium = fireRisk.calculate(policy);
                            assertEquals(expected.get(i), premium.doubleValue());
                        }));
    }


    @Test
    @DisplayName("Calculate Premium for specific type only")
    void calculatePremiumForSpecificTypeOnly() {
        PolicySubObject pso = new PolicySubObject("obj", BigDecimal.valueOf(25.10), RiskType.THEFT);

        PolicyObject po = new PolicyObject();
        po.setName("House");
        po.setSubObjects(List.of(pso));

        Policy policy = new Policy();
        policy.setPolicyNumber("123456");
        policy.setPolicyStatus(PolicyStatus.APPROVED);
        policy.setObjects(List.of(po));

        BigDecimal premium = fireRisk.calculate(policy);

        assertEquals(0, premium.doubleValue(),
                "Premium does not match");
    }

    @Test
    @DisplayName("Calculate Premium for multiple obj/sub objects")
    void calculatePremiumForMultipleObjects() {
        PolicySubObject pso11 = new PolicySubObject("subObj11", BigDecimal.valueOf(5.10), RiskType.FIRE);
        PolicySubObject pso12 = new PolicySubObject("subObj12", BigDecimal.valueOf(10.00), RiskType.FIRE);
        PolicySubObject pso21 = new PolicySubObject("subObj21", BigDecimal.valueOf(2.35), RiskType.FIRE);
        PolicySubObject pso22 = new PolicySubObject("subObj22", BigDecimal.valueOf(8.02), RiskType.FIRE);

        PolicyObject po1 = new PolicyObject("House", List.of(pso11, pso12));
        PolicyObject po2 = new PolicyObject("House", List.of(pso21, pso22));

        Policy policy = new Policy();
        policy.setPolicyNumber("123456");
        policy.setPolicyStatus(PolicyStatus.APPROVED);
        policy.setObjects(List.of(po1, po2));

        BigDecimal premium = fireRisk.calculate(policy);

        assertEquals(0.36, premium.doubleValue(),
                "Premium does not match");
    }


}
