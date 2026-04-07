---
layout: default.md
title: "Sayyid Mehdi's Project Portfolio Page"
---

### Project: RACE (Residential Assistant's Contact Entries)

RACE is a desktop application for Resident Assistants to manage resident information in student housing. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java and extends the AddressBook Level 3 codebase.

Given below are my contributions to the project.

* **New Feature**: Added sorting to the `list` command via a `-sort` prefix (by name, room, phone, or email).
  * What it does: Sorts the currently filtered person list without running `find` again; supports numerical ordering for room numbers so rooms sort meaningfully, not only as strings.
  * Justification: Resident Assistants often scan long lists; a stable sort order reduces mistakes and speeds up lookup by room or name.
  * Highlights:
    * Touched **model** (`Model` / `ModelManager`), **logic** (`ListCommand`, `ListCommandParser`, `CliSyntax`), **UI** (`PersonListPanel`), and **tests** (`ListCommandTest`, `ListCommandParserTest`, and related cases).
    * Required strict parsing, comparators, and tests for edge cases; follow-up test and parser fixes continued in later commits on `master`.
  * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature}*
  * Pull requests: [PR #114][pr114], [PR #83][pr83], [PR #164][pr164]
  * Key commits (team repo `master`):
    * [2638bb0][c2638] — add sort parameters to `list`
    * [c9e7f3e][c9e7] — sorted filtered list in the model
    * [dc3cd94][dc3cd] — `-sort` prefix syntax
    * [451142a][c4511] — parser fixes and extra tests

* **New Feature**: Numerical ordering in the `Room` model for room-based sorts — [866a16f][c866a].

* **Code contributed**: [RepoSense (authorship view)][rs]

* **Project management**:
  * **Project website / product page:** UI mockup [PR #51][pr51], About Us and portfolio stub [PR #64][pr64], profile image tweak [PR #67][pr67].
  * **Fork → team repo:** routine integration PR (misc. commits, including CI) [PR #40][pr40].

* **Enhancements to existing features**:
  * Tighter **`list`** validation and **`-sort`** syntax — [PR #83][pr83], [PR #164][pr164].
  * More **tests** and follow-ups as the feature matured — [PR #114][pr114], [451142a][c4511], [7e03196][c7e03].

* **Documentation**:
  * User Guide:
    * Refined `list`, `find`, `help`, and `add` — [PR #180][pr180]
  * Developer Guide:
    * Sorting write-up and model UML vs room / comment model — [PR #166][pr166]

* **Community**:
  * Forum ([@lamemario][gh]): [issue 492][f492] (fork / feature-branch PR tip), [issue 491][f491] (issue-before-PR workflow), [issue 332][f332] (iP smoke-test request), [comment on issue 330][f330c] (peer smoke testing).

* **Tools**:
  * **CI / Codecov** (`README` + [`.github/workflows/gradle.yml`][wfgradle]): badge, Codecov action upgrade to **v5**, workflow version bumps, YAML tidy-up — [f23d41d][cf23d], [a357f72][ca357], [c7c8f91][cc7c8], [413c586][c413c]. Included in [PR #40][pr40] *(no separate DevOps-only PR)*.
  * **`.gitignore`:** ignore `bin/` and build artefacts — [addcb7e3][cadd0], [00e6ac5][c00e6]. ([file on `master`][gitignore])

* _{you can add/remove categories in the list above}_

[rs]: https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=lamemario&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=lamemario&tabRepo=AY2526S2-CS2103T-T10-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false

[gh]: https://github.com/lamemario
[wfgradle]: https://github.com/AY2526S2-CS2103T-T10-2/tp/blob/master/.github/workflows/gradle.yml
[gitignore]: https://github.com/AY2526S2-CS2103T-T10-2/tp/blob/master/.gitignore

[pr40]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/40
[pr51]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/51
[pr64]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/64
[pr67]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/67
[pr83]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/83
[pr114]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/114
[pr164]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/164
[pr166]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/166
[pr180]: https://github.com/AY2526S2-CS2103T-T10-2/tp/pull/180

[f492]: https://github.com/NUS-CS2103-AY2526-S2/forum/issues/492
[f491]: https://github.com/NUS-CS2103-AY2526-S2/forum/issues/491
[f332]: https://github.com/NUS-CS2103-AY2526-S2/forum/issues/332
[f330c]: https://github.com/NUS-CS2103-AY2526-S2/forum/issues/330#issuecomment-3928381731

[c2638]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/2638bb0e2300b269e5b179ad7716fcf021a47808
[c9e7]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/c9e7f3eb59165e682a2dfd8ba321c1ffafc84c2d
[dc3cd]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/dc3cd94f96f69e13516e47473bf6fca45fda6c95
[c4511]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/451142ab17fc697ea6e4babc6049063bccd59241
[c866a]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/866a16f55ab1dcc58e30582885cbf13d4738e124
[c7e03]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/7e031967a4cdc9abeb3d6638e1951b3942231c99

[cf23d]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/f23d41d92a2ef6f6ef355178072e48a280569436
[ca357]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/a357f723c45dbcebb2e993854e89d6ae0e07362b
[cc7c8]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/c7c8f9125ac8d826d2a1007c0f605962f8af9c97
[c413c]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/413c586fa3b32be0b3412d52ca70ad3687eeb3df
[cadd0]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/addcb7e3753df5c1081a0ad3ffa72a541dc7f614
[c00e6]: https://github.com/AY2526S2-CS2103T-T10-2/tp/commit/00e6ac5f91b7cd04d83cd1431b1709756fba1b4a
