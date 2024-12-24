package dev.usenkonastia;

public record AttackPowerSummary(int data, int outliers) {
    @Override
    public String toString() {
        return "{data=" + data + ", outliers=" + outliers + "}";
    }
}
