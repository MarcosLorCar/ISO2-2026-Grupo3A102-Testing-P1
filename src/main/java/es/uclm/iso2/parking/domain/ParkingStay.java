package es.uclm.iso2.parking.domain;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * A parking stay, defined by the date/time when the vehicle entered and the
 * date/time when it left.
 *
 * <p>Invalid date/times are rejected in the constructor (see Q3 and Q5 of the
 * README), and {@link #calculateFeeCents()} applies the rules of problem
 * ET.02.02 through {@link ParkingFeeCalculator}.
 */
public class ParkingStay {

    private final LocalDateTime entry;
    private final LocalDateTime exit;

    /**
     * Creates a parking stay and validates its date/times.
     *
     * @param entry the date/time when the vehicle entered the parking
     * @param exit  the date/time when the vehicle left the parking
     * @throws IllegalArgumentException if {@code entry} or {@code exit} is
     *                                  {@code null}, or if {@code exit} is not
     *                                  strictly after {@code entry}
     */
    public ParkingStay(LocalDateTime entry, LocalDateTime exit) {
        if (entry == null || exit == null) {
            throw new IllegalArgumentException("Entry and exit date/times cannot be null.");
        }
        if (!exit.isAfter(entry)) {
            throw new IllegalArgumentException("Exit date/time must be strictly after entry date/time.");
        }
        this.entry = entry;
        this.exit = exit;
    }

    /**
     * Returns the date/time when the vehicle entered the parking.
     *
     * @return the entry date/time, never {@code null}
     */
    public LocalDateTime getEntry() {
        return entry;
    }

    /**
     * Returns the date/time when the vehicle left the parking.
     *
     * @return the exit date/time, never {@code null}
     */
    public LocalDateTime getExit() {
        return exit;
    }

    /**
     * Calculates the total cost of the stay in euro cents.
     *
     * <p>The duration is rounded up to whole minutes (Q2), the first
     * {@link ParkingFeeCalculator#FREE_MINUTES} minutes are free (Q4) and the
     * rest is charged per 24-hour block, capped at
     * {@link ParkingFeeCalculator#DAILY_CAP_CENTS} cents per block (Q1).
     *
     * @return the total cost of the stay in euro cents, for example {@code 5}
     *         for €0.05
     */
    public long calculateFeeCents() {
        return ParkingFeeCalculator.calculateFeeCents(totalMinutesRoundedUp());
    }

    /**
     * Returns the duration of the stay in whole minutes, rounding any started
     * minute up (Q2).
     *
     * <p>Any started second also counts, so a stay of 60 seconds and one
     * nanosecond is two minutes.
     *
     * @return the duration of the stay in whole minutes, always at least 1
     */
    private long totalMinutesRoundedUp() {
        Duration duration = Duration.between(entry, exit);
        long seconds = duration.getSeconds();
        if (duration.getNano() > 0) {
            seconds++;
        }
        return (seconds + 59) / 60;
    }
}
