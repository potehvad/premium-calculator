package com.test.premium.calculator;

import com.test.premium.calculator.model.Policy;
import com.test.premium.calculator.model.Premium;

public interface PremiumCalculator {

    Premium calculate (Policy policy);
}
