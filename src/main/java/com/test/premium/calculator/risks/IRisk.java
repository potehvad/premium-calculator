package com.test.premium.calculator.risks;

import com.test.premium.calculator.model.Policy;
import com.test.premium.calculator.model.RiskType;

import java.math.BigDecimal;

public interface IRisk {

    RiskType type ();

    //I want to pass full policy here,
    // because in future probably we need more complicated logic
    BigDecimal calculate (Policy policy);


}
