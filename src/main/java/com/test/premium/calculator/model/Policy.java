package com.test.premium.calculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Policy {
    private String policyNumber;
    private PolicyStatus policyStatus;
    private List<PolicyObject> objects;
}
