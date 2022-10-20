package com.endurance.entities;

public enum ComplexityFactor {
    MINIMUM, MEDIUM, MAXIMUM;

    public static ComplexityFactor parseString(String value) {
        if ("MINIMUM".equalsIgnoreCase(value)) {
            return MINIMUM;
        }
        if ("MEDIUM".equalsIgnoreCase(value)) {
            return MEDIUM;
        }
        if ("MAXIMUM".equalsIgnoreCase(value)) {
            return MAXIMUM;
        }
        throw new IllegalArgumentException(value);
    }
}
