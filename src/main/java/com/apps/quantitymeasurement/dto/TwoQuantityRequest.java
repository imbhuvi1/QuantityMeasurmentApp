package com.apps.quantitymeasurement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoQuantityRequest {
    private QuantityDTO q1;
    private QuantityDTO q2;
    private QuantityDTO targetUnit;
}