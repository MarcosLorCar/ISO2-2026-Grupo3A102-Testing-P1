# Plan – T3 – Console interface (#4)

| | |
|---|---|
| **Issue** | #4 · branch `feature/t3-console` |
| **Step** | Step 1 – Program (Pair 1) · issue #156 of the main repository |
| **Specification** | statement ET.02.02 and table Q of the [README](../../README.md) |
| **Decisions** | D-003, D-004, D-005, D-006, D-008, D-010, D-011, D-012 |
| **Plan approved by** | Marcos Loro Carrasco (MarcosLorCar), 24/11/2026 |

## Questions and answers
- **Q:** Which date/time input format should the console accept?
  - **A:** `yyyy-MM-dd HH:mm[:ss]` (e.g., `2026-11-24 10:00` or `2026-11-24 10:00:30`), so seconds are optional and Q2's partial-minute rounding can be tested manually.
- **Q:** Should the console run once or loop?
  - **A:** Re-prompt in a loop until the user chooses to exit (typing `exit`, case-insensitive) or input reaches EOF (so automated tests with injected `InputStream` terminate cleanly).
- **Q:** What class layout gives the best testing granularity?
  - **A:** Separate `ParkingConsole` (all console interaction with injected `InputStream` and `PrintStream`, plus static helpers for parsing and formatting) from `Main` (minimal entry point calling `new ParkingConsole(System.in, System.out).run()`). This follows SRP and DIP (D-012) and lets Pair 3 unit-test the UI thoroughly with `ByteArrayInputStream` / `ByteArrayOutputStream`.
- **Q:** How should running the program be documented in `README.md`?
  - **A:** Keep `pom.xml` unchanged (no new dependencies or plugins, rule 6) and document `mvn compile` followed by `java -cp target/classes es.uclm.iso2.parking.ui.Main`.

## Classes and methods

### `es.uclm.iso2.parking.ui.ParkingConsole`
- `public static final DateTimeFormatter DATE_TIME_FORMATTER` — formatter for `yyyy-MM-dd HH:mm[:ss]`.
- `public static final String EXIT_COMMAND = "exit"` — command to quit the console loop.
- `public ParkingConsole(InputStream in, PrintStream out)` — validates non-null streams (`IllegalArgumentException` if either is `null`) and prepares console I/O (DIP, D-012).
- `public static LocalDateTime parseDateTime(String input)` — trims and parses a date/time string using `DATE_TIME_FORMATTER` (throws `IllegalArgumentException` if `null` or blank, `DateTimeParseException` if invalid format).
- `public static String formatEuros(long cents)` — formats euro cents as `€X.XX` using `Locale.ROOT` (Q5), e.g., `0` -> `€0.00`, `5` -> `€0.05`, `2000` -> `€20.00` (throws `IllegalArgumentException` if `cents < 0`).
- `public void run()` — interactive loop:
  1. Prompts for entry date/time (or `exit` to quit); exits cleanly on `exit` or EOF.
  2. Prompts for exit date/time (or `exit` to quit); exits cleanly on `exit` or EOF.
  3. Parses both date/times (on invalid format, prints a clear English error message and re-prompts).
  4. Creates `new ParkingStay(entry, exit)` and calls `stay.calculateFeeCents()` (on `IllegalArgumentException`, prints `Error: <message>` and re-prompts).
  5. Prints the formatted total fee (`Total parking fee: €X.XX`) and continues the loop until the user exits.

### `es.uclm.iso2.parking.ui.Main`
- `private Main()` — prevents instantiation.
- `public static void main(String[] args)` — starts `new ParkingConsole(System.in, System.out).run()`.

## Files
| File | Action | What |
|---|---|---|
| `doc/plans/T3-console.md` | create | This plan file |
| `src/main/java/es/uclm/iso2/parking/ui/ParkingConsole.java` | create | Interactive console UI with injected streams, parsing, formatting and error handling (no business rules) |
| `src/main/java/es/uclm/iso2/parking/ui/Main.java` | create | Minimal entry point that launches `ParkingConsole` with `System.in` and `System.out` |
| `README.md` | edit | Add "Run the program" section explaining how to launch the console interface |

No test is written in Step 1 (rule 17 of AGENTS.md). `pom.xml` and `es.uclm.iso2.parking.domain` are not changed.

## Steps
Tick each step with the hash of its commit (green `mvn -B verify`).
- [x] 1. Implement `ParkingConsole.java` and `Main.java` with full English Javadoc, verify with `mvn -B verify`, and commit (`26392ec`).
- [x] 2. Update `README.md` with instructions on how to run the program, verify with `mvn -B verify`, and commit (committed with this plan).

## Verification
- [x] Classes in `es.uclm.iso2.parking.ui` read input from the keyboard and show the formatted result.
- [x] Clear English error messages when the user types an invalid date/time format or an invalid stay (handling `DateTimeParseException` and `IllegalArgumentException`).
- [x] No business rules in `ui`: only creates `ParkingStay`, calls `calculateFeeCents()` and prints the result.
- [x] Javadoc in English on every public class, constant and method (rule 18).
- [x] `README.md` documents how to run the program.
- [x] `mvn -B verify` is green.

## Status
- **In progress:** nothing
- **Next step:** push `feature/t3-console` and open a pull request to `develop` with `Closes #4`, then ask Ignacio to approve it (D-001, rule 8 of AGENTS.md: wait for the member to request the push and pull request).
- **Open questions:** none
- **Last update:** 24/11/2026, Marcos Loro Carrasco
