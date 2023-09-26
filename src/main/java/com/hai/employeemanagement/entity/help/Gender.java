package com.hai.employeemanagement.entity.help;

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    ;

    public final String label;

    private Gender(String label) {
        this.label = label;
    }
}
