package es.uclm.iso2.parking.domain;

/**
 * Applies the parking fee rules of problem ET.02.02 to a stay duration.
 *
 * <p>The limits of the rules are public constants, so that the test design and
 * the JUnit tests take their boundary values from them (D-003). This class has
 * no state and no console input or output (D-008, D-012).
 */
public class ParkingFeeCalculator {

    /** Minutes at the start of the stay that are free of charge (Q4). */
    public static final long FREE_MINUTES = 30;

    /** Price in euro cents of each billable minute after the free period (Q4). */
    public static final long CENTS_PER_MINUTE = 5;

    /** Maximum price in euro cents of each 24-hour block of the stay (Q1). */
    public static final long DAILY_CAP_CENTS = 2000;

    /** Minutes in one 24-hour block of the daily cap (Q1). */
    public static final long MINUTES_PER_DAY = 1440;

    /**
     * This class only offers static methods, so it is not instantiated.
     */
    private ParkingFeeCalculator() {
    }

    /**
     * Calculates the total cost of a stay from its duration in whole minutes.
     *
     * <p>The first {@link #FREE_MINUTES} minutes are free (Q4). The remaining
     * minutes are grouped in 24-hour blocks of {@link #MINUTES_PER_DAY}
     * minutes: each full block costs {@link #DAILY_CAP_CENTS} cents, and the
     * minutes left in the last block are charged at
     * {@link #CENTS_PER_MINUTE} cents each, capped at
     * {@link #DAILY_CAP_CENTS} cents (Q1).
     *
     * @param totalMinutes the total duration of the stay in whole minutes,
     *                     already rounded up (Q2); it cannot be negative
     * @return the total cost of the stay in euro cents, for example {@code 5}
     *         for €0.05
     */
    public static long calculateFeeCents(long totalMinutes) {
        if (totalMinutes <= FREE_MINUTES) {
            return 0;
        }

        long billableMinutes = totalMinutes - FREE_MINUTES;
        long fullDays = billableMinutes / MINUTES_PER_DAY;
        long minutesInLastDay = billableMinutes % MINUTES_PER_DAY;

        long lastDayFeeCents = minutesInLastDay * CENTS_PER_MINUTE;
        if (lastDayFeeCents > DAILY_CAP_CENTS) {
            lastDayFeeCents = DAILY_CAP_CENTS;
        }

        return fullDays * DAILY_CAP_CENTS + lastDayFeeCents;
    }
}
