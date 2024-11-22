package com.hulutas.fleet_management_system.enums;

public enum SackStatus {
    CREATED(1),
    LOADED(3),
    UNLOADED(4);

    private final int value;

    SackStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SackStatus fromValue(int value) {
        for (SackStatus status : SackStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid SackStatus value: " + value);
    }

}
