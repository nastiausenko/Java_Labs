package dev.usenkonastia.model;

public record AttackPowerSummary(int data, int outliers) {
    @Override
    public String toString() {
        return "{data=" + data + ", outliers=" + outliers + "}";
    }
}
