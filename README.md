## Technical overview and technologies
Used technologies:
 - Java 8
 - SpringBoot 2.3.0

## Service description
Service calculates premium amount based on Policy condition and objects for insured risks. Currently supported risks are:
 - Theft
 - Fire 

## Api description 
Api description is provided in swagger file api.yaml

## Api example
Request example: 

    curl --location --request POST 'http://localhost:8080/premium/calculator' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "policyNumber": "LV20-02-100000-5",
      "policyStatus": "APPROVED",
      "objects": [
          {
              "name": "House",
              "subObjects": [
                  {
                      "name": "TV",
                      "sumInsured": 70,
                      "riskType": "FIRE"
                  },
                  {
                      "name": "TV",
                      "sumInsured": 1000,
                      "riskType": "THEFT"
                  },
                  {
                      "name": "REFREGIRATOR",
                      "sumInsured": 500,
                      "riskType": "THEFT"
                  }
              ]
          },
          {
             "name": "Appartment",
             "subObjects": [
             {
                "name": "Table",
                "sumInsured": 50,
                "riskType": "FIRE"
             }
             ]
          }
      ]
    }'

Response example:

    {
        "risks": [
            {
                "riskType": "FIRE",
                "sum": 2.88
            },
            {
                "riskType": "THEFT",
                "sum": 75.00
            }
        ],
        "premium": 77.88
    }

## New risk implementation
New risk can be implemented by implementation IRisk interface method calculate. It has parameter Policy that have all details about policy and any complicated logic can be developed.
In case new risk is based on insured object sum amount multiplied by coefficient than CoefficientCalculation class can be used and all logic are done here. If it planned more complicated logic it is better to write you own calculate method implementation. 


