package com.hulutas.fleet_management_system.enums;


public enum PackageStatus {
    CREATED(1),
    LOADED_INTO_SACK(2),
    LOADED(3),
    UNLOADED(4);

    private final int value;

    PackageStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PackageStatus fromValue(int value) {
        for (PackageStatus status : PackageStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PackStatus value: " + value);
    }
}
