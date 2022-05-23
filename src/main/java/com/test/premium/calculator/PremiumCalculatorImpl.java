package com.test.premium.calculator;

import com.test.premium.calculator.exceptions.PremiumCalculatorAppException;
import com.test.premium.calculator.model.Policy;
import com.test.premium.calculator.model.PolicyStatus;
import com.test.premium.calculator.model.Premium;
import com.test.premium.calculator.model.PremiumRisk;
import com.test.premium.calculator.risks.IRisk;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremiumCalculatorImpl implements PremiumCalculator {

    private final List<IRisk> risks;

    public PremiumCalculatorImpl(List<IRisk> risks) {
        this.risks = risks;
    }

    @Override
    public Premium calculate(Policy policy) {

        if (policy.getPolicyStatus() != PolicyStatus.APPROVED)
            throw new PremiumCalculatorAppException("Policy not active");


        var premiumRisks =
                risks.stream()
                .map(risk -> new PremiumRisk(risk.type(), risk.calculate(policy)))
                .collect(Collectors.toList());

        BigDecimal premium =
                premiumRisks.stream()
                        .map(PremiumRisk::getSum)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Premium(premiumRisks, premium);

    }


}
