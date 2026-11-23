# DECISIONS

Log of the decisions of this repository (Problem 1, *Parking*, of the second theoretical exercise, group 3A1.02):
how we work, how the program is built and how it is delivered to the other pairs. The decisions about the statement
itself (limits, rules, invalid data) are in table Q of the [README](README.md), not here.

One entry per decision, written when the decision is taken. Each entry has its date, the step, the minutes when it was
agreed in a meeting of the group, and the issue that tracks it, if any. Then come its context, decision, alternatives,
reason and consequences. A decision that is later changed is not deleted: its status says which decision replaces it.

This file is also context for the AI agent: it must follow these decisions and never change them on its own. It is
adapted from the `DECISIONS.md` of the main repository
([2026-ISO2-3A1.02](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/blob/develop/DECISIONS.md)) and Problem 2, keeping
only what applies to a small program without use cases or iterations.

---

## D-001 · Branch protection and pull request approvals
**Date:** 23/11/2026 · **Step:** 1 · **Minutes:** [Meeting 9 of the main repository](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/wiki/Meeting-9) (proposal of the same documents for the T2 repositories), 0:19:27–0:20:26

- **Context:** The statement asks for a `develop` branch and, in Step 3, a `hotfix` branch created from it. The main repository protects `main` and `develop` and requires a pull request approved by someone other than its author (D-001 there). The owner of a repository cannot approve his own pull requests in GitHub.
- **Decision:**
  - `main` and `develop` are protected with the same ruleset as the main repository and Problems 2 and 3: no deletion, no force push, no creation of the branch and a pull request with one approval to change them. The bypass list stays empty.
  - Everything reaches `main` through a pull request: the work branches go to `develop`, and `develop` goes to `main` with one pull request when Step 3 is done, as the main repository only updates `main` when it releases. Until then nobody touches `main`. Nobody commits directly to either of them.
  - `develop` is the default branch of the repository. Whoever opens it (the other pairs, the professor) sees the current README and code, new pull requests point to it, and `Closes #n` closes the issue when the pull request is merged.
  - A pull request is approved by someone other than its author. In Pair 1, Marcos and Ignacio approve each other's pull requests.
  - No branch is ever deleted, and the branches are kept after their pull request is merged (the option of GitHub that deletes the branch on merge stays off). A second ruleset, with an empty bypass list, covers `feature/**` and `hotfix/**` and blocks deleting them and force-pushing to them. It does not stop anyone from pushing, so it does not block the work. This is what D-042 of the main repository foresees.
- **Alternatives:** Letting any member of the group approve. No protection. Deleting the work branches after the merge.
- **Reason:** The people who wrote the code and the people who review it must be different, as in the main repository, and cross approval makes it clear which member answers for each change. Keeping the branches keeps the history of each step for the other pairs and for the professor.
- **Consequences:** Nobody, including the AI agent, commits directly to `main` or `develop`, or deletes a branch. A pull request needs the approval of the other member of Pair 1 before it is merged.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-002 · Roles and steps of the three pairs
**Date:** 23/11/2026 · **Step:** 1 · **Minutes:** Meeting 9 of the main repository · **Issues:** [#156](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/156), [#157](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/157), [#158](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/158)

- **Context:** The statement (ET.02.00) asks for three pairs, three problems and three steps, so that the pair that writes a program does not test it.
- **Decision:** For this problem, Pair 1 (Marcos Loro Carrasco and Ignacio Romero Torres) writes the program (Step 1), Pair 2 (Adam Gouram El Bouhali and Javier Villanueva Crespo) designs the tests in a PDF named `ISO2-2026-Grupo3A102-Testing-P1.pdf` (Step 2), and Pair 3 (Denis-Alexandru Ionescu Telibasa, Rodrigo Muñoz Martín and David Calzado Olmo) writes the JUnit tests (Step 3). It is always stated which pair did each step.
- **Alternatives:** Another assignment of pairs to problems, which the statement allows.
- **Reason:** This is the assignment recorded on the wiki page *Testing Part* of the main repository, so that each pair works on all three stages in the three problems.
- **Consequences:** Pair 1 does not write tests of this program. The program has to be ready before Pair 2 starts, so its delivery date is on the wiki page *Planning*.
- **Status:** Approved in the group (wiki page *Testing Part*).

## D-003 · The program must be easy to test
**Date:** 23/11/2026 · **Step:** 1

- **Context:** Step 1 has no assessment of its own, but the quality of the code decides the effort of Steps 2 and 3 (statement ET.02.00). The statement also suggests that data are encapsulated in the classes so that the exceptions are triggered and the code is testable.
- **Decision:**
  - The limits of the parking rules are named constants.
  - The business logic is exposed through clear public method(s), written as simple `if` statements in the order of the decision table of the README.
  - Invalid data are rejected in the constructor with an exception.
  - Every public class and method has its Javadoc: what it receives, what it returns and what it throws.
  - The domain (`es.uclm.iso2.parking.domain`) has no console input or output.
  - The Maven artifact is `ISO2-2026-Grupo3A102-Testing-P1` and its version stays at `0.1.0-SNAPSHOT`.
- **Alternatives:** Writing the program without these rules and leaving the other pairs to adapt.
- **Reason:** The pseudocode, the decision coverage and MC/DC of Step 2 come straight from methods with simple decisions, the boundary values come from the named constants, and Step 3 writes the tests without having written the code.
- **Consequences:** Rules 5 and 18 of AGENTS.md apply. The rest of the decisions about the problem are in table Q.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-004 · Coverage threshold
**Date:** 23/11/2026 · **Step:** 1

- **Context:** The statement asks for at least 80 % of coverage in Step 3, including the methods that fulfil the business logic, measured with Surefire and JaCoCo. In the main repository the build fails below 80 % of lines and 70 % of branches (D-033 there). In this repository the program is merged before its tests exist.
- **Decision:** JaCoCo checks at least 80 % of the lines at BUNDLE level in `mvn verify`. Until Step 3 it only warns (`haltOnFailure` is `false` in `pom.xml`); Pair 3 sets it to `true` in Step 3. The reports are in `target/surefire-reports` and `target/site/jacoco`, and the results go in the wiki page *Test reports*.
- **Alternatives:** A check that fails from the start, which would block merging the program. A threshold for branches too, which the statement does not ask for.
- **Reason:** It is the figure of the statement, and a failing check would prevent Pair 1 from delivering Step 1 before the tests exist.
- **Consequences:** The check does not fail the build in Step 1. In Step 3 a pull request below 80 % cannot be merged.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-005 · Rules for the agents: tests, Javadoc and references
**Date:** 23/11/2026 · **Step:** 1 · **Minutes:** Meeting 9 of the main repository

- **Context:** The main repository fixes how an AI agent tests and documents (D-034 there). Here the statement says that "developers responsible for writing the code should not perform the testing".
- **Decision:**
  - **Any AI agent may be used**, with the same rules of AGENTS.md. `CLAUDE.md` contains one line that imports AGENTS.md, so no rules are written anywhere else.
  - **Tests.** Nobody writes tests in Step 1. In Step 3, Pair 3 writes them from the PDF of Pair 2 with JUnit 6 (`org.junit.jupiter`), one method per case, with `@DisplayName` in English and arrange, act, assert, without changing the production code. When a test fails, the agent explains whether the error is in the test or in the code, and never changes an expected value to make it pass.
  - **Javadoc.** The agent writes it together with the code.
  - **Reference.** When the agent has a doubt about the structure of a Maven project with JUnit, Surefire and JaCoCo, it may read the example of the statement (https://github.com/jaredgs93/maven-testing-demo.git), explain how it solves it and wait for the team to decide. Nothing is copied, and our decisions win when they differ.
- **Alternatives:** Naming the allowed tools. Letting Pair 1 write the tests of its own program.
- **Reason:** The rules matter, not the tool; and the whole point of the exercise is that the code and its tests come from different people.
- **Consequences:** Rules 16, 17 and 18 of AGENTS.md.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-006 · Working method
**Date:** 23/11/2026 · **Step:** 1 · **Minutes:** Meeting 9 of the main repository

- **Context:** The main repository works with a cycle (read, ask, plan, wait for a yes, implement, verify) written in AGENTS.md (D-025 there), with its tasks as GitHub issues (D-010 there) and a plan per sub-issue in `doc/plans/`.
- **Decision:**
  - **No work without an issue.** Every task, however small, has a GitHub issue before any work starts: the documents and rules of the repository, T1 Maven skeleton and CI, T2 domain and T3 console. Each issue is linked to the issue of the step in the main repository, and has its own branch, created from the issue, and its own pull request that says `Closes #n`. Nothing is changed in the repository without an issue, as in the main repository (D-010 there).
  - Every task follows the cycle of AGENTS.md, and its plan is a file in `doc/plans/` from `doc/plans/TEMPLATE.md`, approved by a member before any code is written.
  - Commits follow Conventional Commits with the scopes `domain`, `ui`, `build`, `ci` and `docs`. Pull requests go to `develop` with `Closes #n`, and GitHub closes the issue when the pull request is merged, because `develop` is the default branch (D-001).
  - Everything in the repository is written in English (D-010).
  - The wiki of the repository has the pages *Home*, *Planning*, *Traceability* and *Test reports*. *Traceability* records what has been done, with date, who and link.
- **Alternatives:** Keeping the plans in the issues. Working without issues.
- **Reason:** The plan in a file is kept in Git with the code and lets another session continue; the issues show which pair did each step, as the statement asks.
- **Consequences:** The wiki pages are filled in by the members who do each step.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-007 · The program is frozen when Step 1 is delivered
**Date:** 23/11/2026 · **Step:** 1

- **Context:** Pair 2 designs the test cases from the delivered program, and Pair 3 implements them. If the program changes after that, their work can become wrong without anyone noticing. In the main repository a finished component is only changed through an approved patch (D-036 there).
- **Decision:** Once Step 1 is merged into `develop` and announced to Pair 2, the program is frozen. A later change needs a task agreed by Pair 1, a pull request approved by someone other than the author (D-001), and a notice to Pair 2 and Pair 3 before it is merged.
- **Alternatives:** Letting Pair 1 fix the program freely. Never changing it.
- **Reason:** The tests and the test design depend on the exact behaviour and on the decisions of table Q, so a change must be seen by who depends on it.
- **Consequences:** Rule 19 of AGENTS.md. A change to table Q after the delivery is a change to the program.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-008 · Console interface separated from the domain
**Date:** 23/11/2026 · **Step:** 1

- **Context:** Problem 1 asks for a command-line interface. Other pairs test the domain, and the console must not get in the way.
- **Decision:** The program has a console in the package `es.uclm.iso2.parking.ui`, in its own classes. It only reads the data, creates the domain input object(s), calls the domain and shows the result or the message of the exception. It has no business rules. It is task T3 of Step 1.
- **Alternatives:** Mixing console I/O with domain logic, which makes unit testing harder.
- **Reason:** A program that can be run by hand lets any member or the professor try a case, and keeping it apart from the domain lets JUnit test the rules directly.
- **Consequences:** The coverage of 80 % of D-004 includes the console classes, and Pair 3 has to cover them or the check will not pass.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-009 · Access to the repository
**Date:** 23/11/2026 · **Step:** 1

- **Context:** The statement asks that each repository is shared with all the members of the group and with the practical instructor, and linked from the wiki page *Testing Part* of the main repository.
- **Decision:** The repository is public and belongs to the account of Marcos Loro Carrasco (`MarcosLorCar`). The six other members and the practical instructor (`IsmaelCaballero`) have the *write* role. It is linked in *Testing Part*.
- **Alternatives:** *Read* role for the instructor. A repository owned by another member.
- **Reason:** Every pair has to push to this repository in its own step, and the main repository gives the same role to everyone.
- **Consequences:** What protects the work is the process, not the permissions: protected `main` and `develop` (D-001) and one agent per branch and folder (rule 15 of AGENTS.md).
- **Status:** Decided by Marcos on 23/11/2026; invitations sent to the team and the lab instructor.

## D-010 · Everything in the repository is written in English
**Date:** 23/11/2026 · **Step:** 1

- **Context:** The main repository writes code, comments, commits, issues and documents in English (D-016 there, and the language row of its conventions table). The members talk in Spanish, and the statement is in English. The three pairs read and write in this repository, and so do the professor and the AI agents.
- **Decision:** Everything in this repository is written in English: code and identifiers, comments, Javadoc, the messages that the console shows, test names and `@DisplayName`, commits, pull requests, issues, plans in `doc/plans/`, the README, the wiki and the documents. The conversation between members, or with an AI agent, may be in any language, but nothing that is written in the repository is.
- **Alternatives:** Spanish for the documents and English for the code. Each pair writing in its own language.
- **Reason:** It is the same rule as in the main repository, it keeps the repository readable for every pair, and it avoids mixed languages in the same file.
- **Consequences:** Rule 21 of AGENTS.md. The PDF of Step 2 is the work of Pair 2, and its language is their choice.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-011 · Issues in progress, and one agent per task at a time
**Date:** 23/11/2026 · **Step:** 1 · **Minutes:** —

- **Context:** The professor asked in the lab session of 17/11/2026 to move the issues to *In progress* when we start them, and the main repository did it in D-041. Rule 15 of AGENTS.md already forbids two agents on the same branch or folder, but nothing shows who is working on each task.
- **Decision:**
  - Before starting a task, the member assigns its issue to themself and moves it to *In progress* on the project board. It stays there until the pull request that closes it is merged, and then it goes to *Done*.
  - An issue that is *In progress* belongs to its assignee: nobody else, and no other agent, works on it.
  - In step 1 of the cycle, the agent checks that the issue is assigned to the member who asked for the work and is not being worked on by someone else. If it is not, it stops and tells the member. It also reminds the member to move the issue to *In progress*.
  - When an issue is done, a line is added to the wiki page *Traceability* with the date, who and the link.
- **Alternatives:** Telling each other in WhatsApp who is working on what, which is not recorded.
- **Reason:** The board shows at any moment who is working on each task, and an agent cannot start on work that is someone else's.
- **Consequences:** Extends rule 15 of AGENTS.md and D-006. Step 1 of the cycle in AGENTS.md is updated accordingly.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this file.

## D-012 · Follow SOLID principles to facilitate testing (Steps 2 and 3)
**Date:** 23/11/2026 · **Step:** 1 · **Issue:** [#8](https://github.com/MarcosLorCar/ISO2-2026-Grupo3A102-Testing-P1/issues/8)

- **Context:** Statement ET.02.00 (p. 2) explicitly states that Step 1 has no assessment of its own, but its code quality decides the effort of Steps 2 and 3, and encourages implementing code that is easy to test *"by following the SOLID principles we reviewed in practical session 0"* (also recorded in main repository issue [#156](https://github.com/igrt08-uclm/2026-ISO2-3A1.02/issues/156)).
- **Decision:** The Step 1 implementation follows the SOLID principles pragmatically, without overengineering:
  - **Single Responsibility Principle (SRP):** input data and constructor validation, fee calculation logic, and console I/O (`es.uclm.iso2.parking.ui`) live in separate cohesive classes, each with one clear responsibility.
  - **Open/Closed Principle (OCP):** rule parameters (free minutes, per-minute rate, daily cap, minutes per day) are named constants and calculation logic is cleanly structured so rules can be extended or adjusted without scattering magic numbers.
  - **Liskov Substitution Principle (LSP):** any subtype or interface implementation honours the exact contract (preconditions, return values and exceptions) of its supertype.
  - **Interface Segregation Principle (ISP):** public classes and methods expose small, focused signatures with no unused parameters or bloated APIs.
  - **Dependency Inversion Principle (DIP):** domain classes never depend on console I/O (`System.in`/`System.out`) or concrete infrastructure; the UI depends on the domain, and console I/O streams can be injected so UI classes are also unit-testable in Step 3.
- **Alternatives:** Ignoring SOLID and putting all logic in a single procedural class, or overengineering with unnecessary frameworks/factories that make Step 2 (pseudocode, MC/DC) and Step 3 harder.
- **Reason:** Cohesive, decoupled classes with clean constructors and injected I/O make test units obvious for Pair 2 (Step 2) and straightforward to cover above 80 % in JUnit for Pair 3 (Step 3).
- **Consequences:** Extends D-003 and D-008; rule 5 of AGENTS.md references D-012.
- **Status:** Proposed by Marcos on 23/11/2026; pending the approval of Ignacio in the review of the pull request that adds this decision.
