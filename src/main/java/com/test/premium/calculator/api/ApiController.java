package com.test.premium.calculator.api;

import com.test.premium.calculator.PremiumCalculator;
import com.test.premium.calculator.model.Policy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "premium")
public class ApiController {

    private final PremiumCalculator calculator;

    public ApiController(PremiumCalculator calculator) {
        this.calculator = calculator;
    }

    @PostMapping(path = "calculator")
    public ResponseEntity calculatePremium (@RequestBody Policy policy) {
        var premium = calculator.calculate(policy);

        return ResponseEntity.ok(premium);
    }
}
