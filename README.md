# ISO2-2026-Grupo3A102-Testing-P1

Problem 1 (ET.02.02, *Parking*) of the second theoretical exercise of Software Engineering II (UCLM, 2026/27),
group 3A1.02. The program calculates the total cost of a parking stay from two date/time objects (entry and exit):
the first 30 minutes are free, then €0.05 per minute, capped at a €20 daily maximum.

| Step | Subgroup | Members | Issue |
|---|---|---|---|
| Step 1 – Program | Pair 1 | Marcos Loro Carrasco, Ignacio Romero Torres | [#156](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/156) |
| Step 2 – Test design | Pair 2 | Adam Gouram El Bouhali, Javier Villanueva Crespo | [#157](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/157) |
| Step 3 – JUnit tests | Pair 3 | Denis-Alexandru Ionescu Telibasa, Rodrigo Muñoz Martín, David Calzado Olmo | [#158](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/158) |

Tasks, dates and how we work: [Planning](https://github.com/MarcosLorCar/ISO2-2026-Grupo3A102-Testing-P1/wiki/Planning)
in the wiki. Rules for people and AI agents: [AGENTS.md](AGENTS.md). Why things are the way they are:
[DECISIONS.md](DECISIONS.md). Main repository of the team:
[2026-ISO2-3A1.02](https://github.com/igrt08-uclm/2026-ISO2-3A1.02) · [Testing Part](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/wiki/Testing-Part)

## Requirements

- Java 21 or later
- Maven 3.9 or later

## Build and test

```bash
mvn verify
```

- Test results (Surefire): `target/surefire-reports`
- Coverage report (JaCoCo): `target/site/jacoco/index.html`. The minimum is 80 % of the lines.

> Until Step 3 the coverage check only shows a warning (`haltOnFailure` is `false` in `pom.xml`), because the program
> is written before its tests. Pair 3 sets `haltOnFailure` to `true` in Step 3 (D-004).

## Structure

| Package | Content |
|---|---|
| `es.uclm.iso2.parking.domain` | Domain classes for parking stay validation and fee calculation (no console I/O, SOLID per D-012) |
| `es.uclm.iso2.parking.ui` | Command-line interface, with no business rules (D-008) |
| `src/test/java` | JUnit tests (Step 3, Pair 3) |

## The rules of the statement (ET.02.02)

Given an entry date/time and an exit date/time:

1. **Validation:** if either date/time is `null`, or if the exit is not strictly after the entry (Q3), throw an `IllegalArgumentException`.
2. **Duration:** total stay duration is rounded up to whole minutes (Q2).
3. **Free period:** if total duration is ≤ 30 minutes, the cost is `0` cents (€0.00).
4. **Rate:** beyond the first 30 minutes, each additional minute costs `5` cents (€0.05) (Q4).
5. **Daily cap:** capped at `2000` cents (€20.00) per 24-hour block (Q1).

## Decisions on the statement

The statement leaves these questions open. These are the answers the program follows, so that Pair 2 can design the
tests from them and Pair 3 can implement them without guessing. A new decision is added as Q6, Q7… and, once Step 1
is delivered, it counts as a change of the program (D-007).

| # | Question | Answer | Why |
|---|---|---|---|
| Q1 | How does the €20 daily cap work for multi-day stays (> 24 h)? | By 24-hour blocks (1,440 minutes each). Each full 24-hour block costs 2,000 cents (€20.00), and the remaining minutes of the partial block are charged at 5 cents/minute capped at 2,000 cents (€20.00). The 30 free minutes apply only once at the start of the stay. | Avoids calendar-day edge cases (e.g., entering at 23:00 and leaving at 02:00) and keeps the calculation deterministic and easy to test. |
| Q2 | How are partial minutes (extra seconds) handled? | Rounded up to the next full minute (e.g., 30 min 15 s counts as 31 minutes). | Mirrors real parking meters where any started minute is billed. |
| Q3 | Is a 0-minute stay (`entry.equals(exit)`) valid? | No. The exit must be strictly after the entry (`!exit.isAfter(entry)` throws `IllegalArgumentException`). | A zero-length stay is not a real parking stay and makes the boundary between invalid (`<= 0`) and valid (`>= 1` second) unambiguous. |
| Q4 | When total stay exceeds 30 minutes, are the first 30 minutes still free? | Yes. Only the minutes beyond 30 are billed (30 min → 0 cents; 31 min → 5 cents). | Literal reading of *"the first 30 minutes are free of charge. Thereafter, the rate is €0.05 per minute"*. |
| Q5 | Which Java types and exception are used? | `java.time.LocalDateTime` for entry and exit; `long` representing **euro cents** for the returned cost (e.g., `5` = €0.05, `2000` = €20.00); `IllegalArgumentException` with English messages for invalid inputs. | `LocalDateTime` is immutable and avoids timezone issues; `long` cents guarantee exact integer arithmetic with zero floating-point rounding errors and exact JUnit assertions (`assertEquals` without delta); `IllegalArgumentException` is standard and tested with `assertThrows`. The UI formats cents as `€X.XX`. |

## For Pair 2 – test design (Step 2)

You design the tests from this README and the code of `develop`, and deliver `ISO2-2026-Grupo3A102-Testing-P1.pdf`
(all the sections required in ET.02.00 and TW.02.01).

- **Limits:** every limit is a named constant in the domain code (free minutes `30`, rate `5` cents/min, daily cap `2000` cents, minutes per day `1440`), so boundary values come directly from them.
- **Methods to test:** *to be completed in T2 ([#3](https://github.com/MarcosLorCar/ISO2-2026-Grupo3A102-Testing-P1/issues/3)).*

## For Pair 3 – JUnit tests (Step 3)

You write the tests from the PDF of Pair 2, in `src/test/java`, on a `hotfix` branch created from `develop`.

- **Rules:** JUnit 6 (`org.junit.jupiter`), one method per case, `@DisplayName` in English, arrange, act, assert. Do not change the production code; if a test fails, say whether the error is in the test or in the code (rule 17 of AGENTS.md).
- **Coverage:** at least 80 % of the lines with JaCoCo (D-004). Set `haltOnFailure` to `true` in `pom.xml`. The console classes of the package `ui` count too.
- **Reports:** fill in the wiki page [Test reports](https://github.com/MarcosLorCar/ISO2-2026-Grupo3A102-Testing-P1/wiki/Test-reports) with the results of `mvn verify`.
- **Pull request:** to `develop`, approved by someone other than the author (D-001).
