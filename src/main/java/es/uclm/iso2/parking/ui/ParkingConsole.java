package es.uclm.iso2.parking.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

import es.uclm.iso2.parking.domain.ParkingStay;

/**
 * Interactive console user interface for the Parking fee calculator (ET.02.02).
 *
 * <p>This class only handles console input/output, date/time parsing, and euro
 * formatting. It contains no business rules (D-008): it creates a
 * {@link ParkingStay}, delegates fee calculation to it, and displays either the
 * formatted fee or a clear English error message. Input and output streams are
 * injected through the constructor to support unit testing (D-012).
 */
public class ParkingConsole {

    /**
     * Accepted date/time input format: {@code yyyy-MM-dd HH:mm} with optional
     * seconds ({@code :ss}).
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm[:ss]");

    /** Command that terminates the interactive console loop (case-insensitive). */
    public static final String EXIT_COMMAND = "exit";

    private final Scanner scanner;
    private final PrintStream out;

    /**
     * Creates a console interface bound to the given input and output streams.
     *
     * @param in  the stream from which user input is read
     * @param out the stream to which prompts, results, and errors are written
     * @throws IllegalArgumentException if {@code in} or {@code out} is
     *                                  {@code null}
     */
    public ParkingConsole(InputStream in, PrintStream out) {
        if (in == null || out == null) {
            throw new IllegalArgumentException("Input and output streams cannot be null.");
        }
        this.scanner = new Scanner(in, StandardCharsets.UTF_8);
        this.out = out;
    }

    /**
     * Parses a date/time string using {@link #DATE_TIME_FORMATTER}.
     *
     * @param input the raw text entered by the user
     * @return the parsed {@link LocalDateTime}
     * @throws IllegalArgumentException if {@code input} is {@code null} or blank
     * @throws DateTimeParseException   if {@code input} does not match
     *                                  {@code yyyy-MM-dd HH:mm[:ss]}
     */
    public static LocalDateTime parseDateTime(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Date/time input cannot be null or blank.");
        }
        return LocalDateTime.parse(input.trim(), DATE_TIME_FORMATTER);
    }

    /**
     * Formats an amount in euro cents as {@code €X.XX} (Q5 of the README).
     *
     * @param cents the fee amount in euro cents, must not be negative
     * @return the formatted currency string, for example {@code "€0.05"} or
     *         {@code "€20.00"}
     * @throws IllegalArgumentException if {@code cents} is negative
     */
    public static String formatEuros(long cents) {
        if (cents < 0) {
            throw new IllegalArgumentException("Fee in cents cannot be negative.");
        }
        long euros = cents / 100;
        long remainingCents = cents % 100;
        return String.format(Locale.ROOT, "€%d.%02d", euros, remainingCents);
    }

    /**
     * Runs the interactive prompt loop until the user enters
     * {@value #EXIT_COMMAND} or the input stream reaches end-of-file.
     *
     * <p>For each stay, it reads the entry and exit date/times, delegates
     * validation and fee calculation to {@link ParkingStay}, and prints either
     * the formatted fee or an English error message before prompting again.
     */
    public void run() {
        out.println("Parking Fee Calculator (type '" + EXIT_COMMAND + "' at any prompt to quit)");
        while (true) {
            LocalDateTime entry = readDateTime("entry");
            if (entry == null) {
                out.println("Goodbye.");
                return;
            }

            LocalDateTime exit = readDateTime("exit");
            if (exit == null) {
                out.println("Goodbye.");
                return;
            }

            try {
                ParkingStay stay = new ParkingStay(entry, exit);
                long feeCents = stay.calculateFeeCents();
                out.println("Total parking fee: " + formatEuros(feeCents));
            } catch (IllegalArgumentException e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Prompts the user for a date/time until a valid value is entered or the
     * user chooses to exit.
     *
     * @param label description of the date/time being requested (e.g., "entry")
     * @return the parsed {@link LocalDateTime}, or {@code null} if the user
     *         entered {@value #EXIT_COMMAND} or input reached end-of-file
     */
    private LocalDateTime readDateTime(String label) {
        while (true) {
            out.print("Enter " + label + " date/time (yyyy-MM-dd HH:mm[:ss]) or '"
                    + EXIT_COMMAND + "' to quit: ");
            if (!scanner.hasNextLine()) {
                return null;
            }
            String line = scanner.nextLine().trim();
            if (EXIT_COMMAND.equalsIgnoreCase(line)) {
                return null;
            }
            try {
                return parseDateTime(line);
            } catch (IllegalArgumentException | DateTimeParseException e) {
                out.println("Error: invalid date/time format. Expected yyyy-MM-dd HH:mm or yyyy-MM-dd HH:mm:ss.");
            }
        }
    }
}
