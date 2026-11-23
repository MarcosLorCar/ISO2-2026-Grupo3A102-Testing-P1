# Plan – Tn – <task name> (#n)

> Copy this file as `doc/plans/Tn-short-name.md` on the branch of the task, for example `doc/plans/T2-domain.md`.
> The agent writes it in step 3 of the cycle in [AGENTS.md](../../AGENTS.md); the member approves it before any code
> is written, and it is kept up to date until the pull request is merged.

| | |
|---|---|
| **Issue** | #n · branch `feature/short-description` |
| **Step** | Step 1 – Program (Pair 1) · issue #156 of the main repository |
| **Specification** | statement ET.02.02 and table Q of the [README](../../README.md) |
| **Decisions** | the entries of [DECISIONS.md](../../DECISIONS.md) that apply (D-003, D-004, …) |
| **Plan approved by** | member, dd/mm/yyyy |

## Questions and answers
Every doubt asked in step 2, with the member's answer. A doubt about the statement becomes a new row of table Q.
- **Q:** … **A:** …

## Files
| File | Action | What |
|---|---|---|
| `src/main/java/es/uclm/iso2/parking/domain/…` | create | … |

No test is written in Step 1 (rule 17 of AGENTS.md).

## Steps
Tick each step with the hash of its commit (green `mvn -B verify`).
- [ ] 1. …
- [ ] 2. …

## Verification
- [ ] Every point of the issue is checked.
- [ ] Every public class and method has its Javadoc (rule 18).
- [ ] `mvn -B verify` is green.

## Status
- **In progress:** nothing (the step being done right now; rule 14 in AGENTS.md)
- **Next step:** … (file, class or method)
- **Open questions:** …
- **Last update:** dd/mm/yyyy, member
