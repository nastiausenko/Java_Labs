package dev.usenkonastia.model;

/**
 * Represents a summary of attack power statistics including the number of data points and outliers.
 *
 * @author          Anastasiia Usenko
 * @param data      the number of valid data points within bounds
 * @param outliers  the number of outliers outside the bounds
 */
public record AttackPowerSummary(int data, int outliers) {

    @Override
    public String toString() {
        return "{data=" + data + ", outliers=" + outliers + "}";
    }
}
