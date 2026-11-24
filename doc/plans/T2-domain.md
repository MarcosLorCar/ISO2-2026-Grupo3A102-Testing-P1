# Plan – T2 – Domain and parking rules (#3)

| | |
|---|---|
| **Issue** | #3 · branch `feature/t2-domain` |
| **Step** | Step 1 – Program (Pair 1) · issue #156 of the main repository |
| **Specification** | statement ET.02.02 and table Q of the [README](../../README.md) |
| **Decisions** | D-003, D-004, D-005, D-006, D-008, D-010, D-011, D-012 |
| **Plan approved by** | Ignacio Romero Torres (igrt08-uclm), 24/11/2026 |

## Questions and answers
- **Q:** Which domain classes are required?
  - **A:** Following D-012 (single responsibility), data and input validation live in `ParkingStay`, and the fee
    rules with their named constants live in `ParkingFeeCalculator`. `ParkingStay.calculateFeeCents()` delegates to
    the calculator, so the UI has one entry point and Pair 2 can test the rules from a duration too.
- **Q:** How are partial minutes handled?
  - **A:** Q2 of the README: any started minute is billed. Any started second also counts, so 60 s 1 ns is 2 minutes.
- **Q:** How does the daily cap work?
  - **A:** Q1 of the README: the first 30 minutes are free only once; the rest is charged in 24-hour blocks, each full
    block at the €20 maximum, and the last block at 5 cents/minute capped at €20.
- **Q:** Build environment.
  - **A:** The machine has Maven 3.8.7, but the README requires Maven 3.9 or later for `maven.compiler.release` to be
    honoured. The build is verified with Maven 3.9.9 and Java 21; `pom.xml` is not changed in this task.

## Files
| File | Action | What |
|---|---|---|
| `src/main/java/es/uclm/iso2/parking/domain/ParkingStay.java` | create | Entry and exit date/times, constructor validation, getters and `calculateFeeCents()` |
| `src/main/java/es/uclm/iso2/parking/domain/ParkingFeeCalculator.java` | create | Named constants of the rules and `calculateFeeCents(long totalMinutes)` |
| `README.md` | edit | "Methods to test" section for Pair 2 and Pair 3 |
| `doc/plans/T2-domain.md` | create | This plan file |

No test is written in Step 1 (rule 17 of AGENTS.md). `pom.xml` is not changed.

## Steps
Tick each step with the hash of its commit (green `mvn -B verify`).
- [x] 1. Create plan file `doc/plans/T2-domain.md` (committed with the README update)
- [x] 2. Implement `ParkingStay.java` and `ParkingFeeCalculator.java` (`a3cc3ed`)
- [x] 3. Update the "Methods to test" section of `README.md` (committed with this plan)
- [x] 4. Run `mvn -B verify`: green with Maven 3.9.9 and Java 21 (`a3cc3ed`)

## Verification
- [x] Domain classes with input validation in the constructor and `IllegalArgumentException` for invalid values.
- [x] Public business-logic method with simple `if` statements and named constants for the rule limits.
- [x] Javadoc on every public class, method and constant; no console I/O in the domain.
- [x] README updated with the "Methods to test" table for Pair 2 and Pair 3.
- [x] `mvn -B verify` is green.

## Status
- **In progress:** nothing
- **Next step:** open the pull request to `develop` with `Closes #3` and ask the other member of Pair 1 to approve it
  (D-001, rule 8 of AGENTS.md: the member must ask for the push and the pull request).
- **Open questions:** none
- **Last update:** 24/11/2026, Ignacio Romero Torres
