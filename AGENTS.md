# AGENTS.md

Instructions for every AI agent that works on this repository (any tool). This is the only file of rules: read it
completely before doing anything. The reasons behind each rule are in [DECISIONS.md](DECISIONS.md).

## The project
Problem 1 (*Parking*, ET.02.02) of the second theoretical exercise (T2) and second part of the lab project of
Software Engineering II (UCLM, 2026/27), group 3A1.02. The program calculates the total cost of a parking stay from
two date/time objects (entry and exit): the first 30 minutes are free, then €0.05 per minute, capped at a €20 daily
maximum.

The three pairs of the group work on this problem in sequence, so nobody tests their own code:

| Step | Pair | What | Issue |
|---|---|---|---|
| 1 – Program | Pair 1 (Marcos, Ignacio) | The Maven project, easy to test | [#156](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/156) |
| 2 – Test design | Pair 2 | A PDF with the test cases | [#157](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/157) |
| 3 – JUnit tests | Pair 3 | The JUnit tests, with at least 80 % coverage | [#158](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/158) |

Step 1 has no assessment of its own: what is graded is the test design and its JUnit implementation. So the program
has to be **easy to test and documented**, and nothing more. The statement is in the main repository
([2026-ISO2-3A1.02](https://github.com/igrt08-uclm/2026-ISO2-3A1.02)).

## How you work
Every task that changes files follows this cycle. Do not skip steps, and do not write code before step 4.

1. **Read.** Run `git fetch origin` and `git pull` on your branch. Read your task issue, this file, the decisions
   table Q of the [README](README.md) and the plan of the task in `doc/plans/`, if it exists. Check that the issue is
   assigned to the member who asked for the work. If it is assigned to someone else, or another agent is working on
   it, stop and tell the member (D-011). Remind the member to move it to *In progress* on the project board.
2. **Ask.** Write every doubt, ambiguity or missing piece in one message and wait for the answers. Never fill a gap
   with an assumption: an open question about the statement becomes a new row of table Q, decided by Pair 1.
3. **Plan.** Write or update `doc/plans/<task>.md` from [doc/plans/TEMPLATE.md](doc/plans/TEMPLATE.md): the questions
   and answers, the files you will create or change, the classes and methods, and the steps in order.
4. **Wait for an explicit "yes"** from the member before changing anything. Anything outside the approved plan needs
   a new yes.
5. **Implement one step at a time.** Before each step, apply rule 14 and write the step as *In progress* in the plan.
   After the step, `mvn -B verify` must be green; then commit, tick the step in the plan with the commit hash, and set
   *In progress* back to nothing.
6. **If something does not go as planned, stop and re-plan** with the member. Do not improvise.
7. **Done needs evidence:** a green build and every point of the issue checked. Never mark something as done because
   it "looks right".
8. **Leave it ready for the next session.** Before you stop, the plan says what is done, what was in progress and the
   exact next step, so that another session or another member can continue without asking.

A question that does not change any file does not need a plan.

## Rules you must always follow
1. **You suggest, the team decides.** Do not take decisions about the statement, and do not change a decision of
   DECISIONS.md or a row of table Q on your own.
2. **Only your task, and no work without an issue.** One task is one issue, one branch and one agent per session
   (D-006). Do not start anything that has no issue. Change only what your task needs: do not
   refactor, rename or "improve" anything else; write it in the plan as an open question instead.
3. **Only ET.02.02.** Do not add tariffs, discounts, rules or inputs that the statement does not name.
4. **The specification is the statement plus table Q of the README.** The code follows them. If they disagree or leave
   a gap, stop and ask (cycle step 2).
5. **Keep it simple.** The smallest code that meets the specification: no extra features and no overengineering. The
   simpler the program, the easier it is to test.
6. **No new dependencies, frameworks or tools.** Only JUnit, Surefire and JaCoCo, as the statement says.
7. **Branches.** Work only on the branch of your task: `feature/short-description` in Step 1, and `hotfix/short-description`
   created from `develop` in Step 3. A member creates it on GitHub from the issue: never create, rename or delete a
   branch yourself, and branches are not deleted even after their pull request is merged. Never commit to `main` or
   `develop`: everything reaches them through a pull request, and `develop` reaches `main` with another one. Never merge
   a pull request: another member approves it (D-001).
8. **Ask before acting on GitHub.** Push, open pull requests, and create, edit or close issues, comments, wiki pages or
   invitations only when the member asks for that exact action.
9. **Human approval for structural changes.** Ask before you create, move, rename or delete a file or folder that is not
   in the approved plan, a Java package, and before you change `pom.xml`, the CI workflow, `AGENTS.md`, `DECISIONS.md`
   or a row of table Q.
10. **No destructive Git commands:** no `push --force`, `reset --hard`, `rebase` of a pushed branch, `clean` or
    `--no-verify`. If you think one is needed, stop and ask.
11. **Git is the truth.** If a plan or a document says one thing and `git log`, `git status` or the build say another,
    trust Git and the build, and tell the member.
12. **Cite your sources; do not invent.** Every fact about the problem comes from the statement (ET.02.02), a row of
    table Q or a decision. If you cannot cite it, say that you do not know.
13. **Authorship.** Commits are made with the identity of the member who asked for the work, with no `Co-Authored-By`
    lines or tool signatures.
14. **Only start a step you can finish.** Before each step, estimate whether the context and tokens you have left are
    enough to implement it, build it, commit it **and** update the plan, and always keep a reserve for the plan update.
    If you are not sure, split the step into smaller ones, or do step 8 of the cycle now and stop.
15. **Never share a branch or a folder with another agent.** Two agents never work on the same branch or in the same
    folder at the same time: one can overwrite the other's changes without any warning.
16. **The statement's example is only an inspiration.** When you have a doubt about how to structure a Maven project
    with JUnit, Surefire and JaCoCo, you may read https://github.com/jaredgs93/maven-testing-demo.git, explain to the
    member how it solves the problem and wait for the team to decide. Never copy its code or text, and when it differs
    from DECISIONS.md or table Q, ours win.
17. **Nobody writes tests in Step 1.** The tests are Step 3 and belong to Pair 3, who writes them from the PDF of
    Pair 2 (D-005). If you are asked for tests in Step 1, say so and stop. In Step 3, write one JUnit 6 method per case
    (`org.junit.jupiter`, never JUnit 4), with `@DisplayName` in English and arrange, act, assert, without changing the
    production code. When a test fails, explain whether the error is in the test or in the code, and never change an
    expected value to make a test pass.
18. **Javadoc as you write.** Every class and public method you create or change has its Javadoc in English: what it
    receives, what it returns and what exceptions it throws. Pair 2 and Pair 3 read it to work without asking.
19. **The program is frozen when Step 1 is delivered** (D-007). After that, change it only with a task approved by Pair 1
    and a pull request, and tell Pair 2 and Pair 3 first: their test design and tests depend on it.
20. **Spend tokens where they add quality.** One task per conversation: when a step is committed and the plan updated
    (cycle step 8), start a new conversation from this file and the plan. Read only what the task needs, and filter
    long build output without hiding failures.
21. **English everywhere** (D-010). Everything in this repository is written in English: code, identifiers, comments,
    Javadoc, messages that the console shows, test names, commits, pull requests, issues, plans, README, wiki and
    documents. Talk to the member in the language the member uses, but never write anything else than English in the
    repository.

If two sources disagree, DECISIONS.md wins, then table Q of the README, then the issues. Do not choose on your own: tell
the member.

## Conventions
| Topic | Convention | Source |
|---|---|---|
| Names | Classes in `es.uclm.iso2.parking.domain` and `es.uclm.iso2.parking.ui`; the Maven artifact is `ISO2-2026-Grupo3A102-Testing-P1` | D-003, D-008 |
| Branches | `feature/short-description`; `hotfix/short-description` from `develop` in Step 3. `main` and `develop` are protected and everything reaches them through a pull request. No branch is deleted, even after the merge | D-001, statement ET.02.00 |
| Commits | Conventional Commits: `type(scope): summary (#issue)`, with scopes `domain`, `ui`, `build`, `ci` and `docs`, e.g. `feat(domain): implement parking fee calculation rules (#3)` | D-006 |
| Pull requests | To `develop` (the default branch), approved by someone other than the author, with `Closes #n` in the description: GitHub closes the issue when it is merged. `develop` goes to `main` with one pull request when Step 3 is done | D-001, D-006 |
| Issues | No work without one: one per task, however small, linked to the issue of the step in the main repository, with its branch created from it | D-006 |
| Plans | One file per task in `doc/plans/`, from `doc/plans/TEMPLATE.md` | D-006 |
| Versions | The artifact stays at `0.1.0-SNAPSHOT` | D-003 |
| Language | Everything in English: code, comments, Javadoc, console messages, test names, commits, pull requests, issues, plans, README, wiki and documents | D-010 |
| Tests | Step 3 only: class `<Class>Test` in `src/test/java`, same package; one method per case of the PDF of Pair 2 | D-005 |
| Javadoc | Every class and public method, in English, written together with the code | D-005 |

## Where to find things
| What | Where |
|---|---|
| Why the repository is the way it is | [DECISIONS.md](DECISIONS.md) |
| Decisions on the statement (table Q) and how to build and test | [README.md](README.md) |
| Plan and progress of each task | [doc/plans/](doc/plans/) |
| The statement, the three problems and the table of pairs | Main repository: [2026-ISO2-3A1.02](https://github.com/igrt08-uclm/2026-ISO2-3A1.02), wiki page *Testing Part* |
| What has been done, with date and link | Wiki of this repository: *Traceability* |
| Plan, dates and how we work | Wiki of this repository: *Planning* |
| Test results (Surefire and JaCoCo) | Wiki of this repository: *Test reports* (Step 3) |

## Lessons
When the member corrects you, or you find a mistake that could happen again, propose a short lesson for the member to
add to the wiki. Do not edit the wiki yourself (rule 8).
