---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# RACE Developer Guide
This guide documents **RACE** (Residential Assistant’s Contact Entries). This repository extends the AB3 architecture with additional domain concepts such as room-based resident records, tag registries, comments, and list sorting behaviours that are not present in stock AB3.
When reading older AB3-oriented diagrams or descriptions, interpret them as the high-level structure of the app; refer to the current `Model`, `Storage`, and `Logic` sections for RACE-specific details.
Contributors should prefer updating diagrams and explanations alongside feature changes to avoid documentation drift.
<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* The `comment` feature implementation is adapted from the se-edu tutorial [Adding optional fields to AB3](https://se-education.org/).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.
* renders a resident's comment in `PersonCard` only when the comment is non-empty, so empty comments do not take up space in the list view.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic`
component, taking `execute("delete 1,3")` as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object). Each `Person` stores immutable values for `Name`, `Phone`, `Email`, `Room`, `Comment`, and tags.
* stores a `CustomTagRegistry` inside `AddressBook` to track known custom tags separately from each `Person`, while still treating built-in tags as always known.
* treats tag names as exact, case-sensitive values. There is no automatic tag normalization; kebab-case is a usage convention rather than a storage rule.
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPrefs` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* persists each person's comment in the JSON data file and loads missing `comment` fields as empty comments to preserve compatibility with older saved data.
* persists the custom tag registry separately as `customTags`, while also rebuilding missing custom-tag entries from loaded persons so the in-memory model stays usable even if the file is stale.
* inherits from both `AddressBookStorage` and `UserPrefsStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Future Work\] Undo/redo feature

Undo/redo is **not implemented** in the current codebase on `master`.
There are no `undo`/`redo` commands, no `VersionedAddressBook`, and no
`Model#commitAddressBook` / `Model#undoAddressBook` / `Model#redoAddressBook`
operations in the active implementation.

If the team revisits undo/redo in a future release, the design should be
documented here together with updated sequence/activity diagrams.

### Sorting Feature

#### Implementation

The sorting feature is integrated into the `list` command. It allows users to
view all residents ordered by a chosen field (`name` or `room`).

The implementation relies on JavaFX's `SortedList`, which is initialized in `ModelManager` to wrap around the `filteredPersons` list. This architectural choice ensures that whenever the filter changes (e.g., via `find`), the sort order can still be applied to the filtered subset.

1.  `ListCommandParser` identifies the `-sort` option and maps supported field
    prefixes (`n/`, `r/`) to a corresponding `Comparator<Person>`.
2.  `ListCommand` is created with the field name and its comparator.
3.  Upon execution, `ListCommand` calls `Model#updateFilteredPersonList(Predicate, Comparator)`.
4.  `ModelManager` sets the filter on its `FilteredList` and the comparator on its `SortedList`.
5.  Sorting is only applied when a command calls `Model#updateFilteredPersonList(Predicate, Comparator)`.
    Commands that use the single-argument overload reset the sort comparator to `null`; this includes plain `list`,
    `find`, and commands such as `add` and `comment` that refresh the shown list through `updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS)`.

The following sequence diagram shows the main interactions for `list -sort n/`.

<puml src="diagrams/SortingSequenceDiagram.puml" width="760" />

#### Design considerations

**Aspect: How sorting interacts with filtering**

*   **Choice (current):** Sort order is reset by `find` and plain `list`, but
    preserved by `add` after a prior `list -sort <prefix>/`.
    *   Pros: Predictable behavior; users always see the list state they explicitly requested.
    *   Cons: Users cannot "keep" a sort order while performing multiple different searches without re-specifying the sort field.

### Tag management

#### Implementation

The tag model separates three concerns: tag identity, built-in tag definitions, and custom-tag existence.

1. `Tag` remains an immutable value object that stores the displayed tag name and uses exact string equality. This makes tag behavior predictable: `study-group` and `Study-Group` are different tags.
2. `DefaultTagEnum` defines the built-in tags (`vegetarian`, `vegan`, `halal`, `allergies`). These are treated as known immediately, but they follow the same case-sensitive matching rule as custom tags.
3. `CustomTagRegistry` stores the currently known custom tags in an `AddressBook`, and its lookup logic also treats built-in tags as known immediately. This keeps "does this tag exist already?" in the model layer instead of inside `Tag`.
4. `add` and `edit` only register unknown custom tags when the user explicitly supplies `-newtag`. Without that flag, both commands reject unknown tags consistently.
5. Shared helpers, `ParserUtil.parseBooleanFlag(...)` and `TagCommandUtil.validateKnownTags(...)`, keep `-newtag` parsing and tag validation aligned between `add` and `edit`.
6. During loading and person updates, `AddressBook` also registers custom tags found on persons. This keeps the in-memory registry consistent even if the stored `customTags` list is incomplete.

The following class diagram summarizes the main classes involved in tag management.

<puml src="diagrams/TagManagementClassDiagram.puml" width="620" />

#### Design considerations

**Aspect: Where tag existence should be tracked**

* **Choice (current):** Store tag existence in `CustomTagRegistry`, while `Tag` only represents a value.
  * Pros: Keeps the value object simple and reusable; avoids spreading tag-existence logic across commands and model classes.
  * Cons: Introduces one more model concept that must stay synchronized with resident data.

**Aspect: How tag casing should work**

* **Choice (current):** Use exact, case-sensitive tag identity for both built-in and custom tags.
  * Pros: Matches a branch-like mental model; avoids hidden normalization or silent rewriting of user input.
  * Cons: Makes near-duplicate tags easier to create if users are inconsistent with capitalization.

**Aspect: How to handle incomplete `customTags` data from storage**

* **Choice (current):** Repair the registry in memory from loaded persons, and persist the repaired registry on the next successful save.
  * Pros: Keeps runtime behavior correct without adding immediate load-time writeback.
  * Cons: The JSON file can remain temporarily stale until a later successful command triggers saving.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Felix is a Year 3 Soc student and RA at Acacia College. Approachable and proactive, he enjoys connecting with residents and planning events for them regularly. He loves his terminal. However, he has poor memory and gets overwhelmed during orientation, because of the messy process of onboarding residents and collecting data.

**Value proposition**: RACE provides a single, dedicated system to manage resident details safely, keeping sensitive information private. During orientation, RAs must onboard 40+ residents quickly, and RACE helps to manage resident information efficiently, instead of current scattered and slow workflows.


### User stories

* As a new RA, I can add a resident with the required details, so that I can register residents quickly during onboarding.
* As an RA handling a busy intake, I can include optional fields in the same `add` command, so that incomplete information does not block onboarding.
* As an RA, I can assign dietary or custom tags to residents, so that I can quickly group residents by needs or participation.
* As an RA, I can create a new custom tag while adding or editing a resident, so that I can record categories that are specific to my hall or event.
* As an RA, I can list all residents, so that I can review the current roster at a glance.
* As an RA, I can sort the resident list by name or room, so that I can scan the roster in the order that best fits my task.
* As a forgetful RA, I can find residents by keywords across name, room, or tags, so that I can retrieve a record even when I only remember partial information.
* As an RA, I can edit a resident's core details, so that the address book stays accurate when contact information changes.
* As an RA, I can replace or clear a resident's tags, so that outdated group labels do not remain in the record.
* As an RA, I can add, edit or clear a private comment for a resident, so that I can keep follow-up notes without changing the resident's main details.
* As an RA, I can delete resident records that are no longer needed, so that the address book remains organised.
* As an RA managing semester turnover, I can delete multiple residents at once, so that I can clean up the list more efficiently.
* As an RA preparing for a new intake cycle, I can clear all resident records, so that I can reset the app quickly for a new semester.
* As a new RA, I can refer to help and documentation, so that I can learn the command format quickly.
* As a busy RA, I can have my data saved automatically after successful commands, so that I do not lose updates if I close the app.


Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                   | So that I can…​                                                              |
|----------|-----------------------------|------------------------------------------------|------------------------------------------------------------------------------|
| `* * *`  | new RA                      | add a resident with required details           | register residents quickly during onboarding                                 |
| `* * *`  | RA handling busy intake     | include optional fields in the same `add` command | avoid delaying onboarding when some details are unavailable               |
| `* * *`  | RA                          | list all residents                             | review the full roster at a glance                                           |
| `* * *`  | forgetful RA                | find residents by partial keywords             | retrieve a resident record without scanning the entire list                  |
| `* * *`  | RA                          | edit a resident's details                      | keep contact and room information accurate                                   |
| `* * *`  | RA                          | add or clear a private comment                 | keep follow-up notes without changing the resident's core details            |
| `* * *`  | RA                          | delete a resident                              | remove records that are no longer needed                                     |
| `* * *`  | new RA                      | refer to help and documentation                | learn or recall the correct command format quickly                           |
| `* *`    | RA                          | assign tags to residents                       | group residents by dietary needs, events, or other categories                |
| `* *`    | RA                          | create new custom tags during `add` or `edit` | record hall-specific groupings without leaving the workflow                  |
| `* *`    | RA                          | sort the resident list by name or room         | review residents in the order that best supports my task                     |
| `* *`    | RA managing semester turnover | delete multiple residents at once            | clean up the roster more efficiently                                         |
| `* *`    | RA preparing for a new semester | clear all resident records                  | reset the app quickly for the next intake cycle                              |
| `* *`    | busy RA                     | rely on automatic saving                       | avoid losing changes after each successful update                            |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list
4.  AddressBook deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

**Use case: Add or clear a resident comment**

**MSS**

1. User requests to list residents or find a resident.
2. System shows a list of residents with their indices.
3. User requests to add a comment to a specific resident in the list.
4. System updates the resident's comment.
5. System shows the updated resident list.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. System shows an error message.

      Use case resumes at step 2.

* 3b. The user provides `c/` with no text.

    * 3b1. System clears the resident's existing comment.

      Use case ends.

**Use case: Batch Onboarding**

**MSS**

1. User requests to list all residents to check current occupancy.
2. System shows a list of residents.
3. User requests to add a new resident with mandatory and optional fields.
4. System validates the data and adds the resident to the database.
5. User requests to add another new resident with mandatory and optional fields.
6. System adds the second resident.
7. User verifies the new list.

   Use case ends.

**Extensions**

* 3a. The room number is already assigned to another resident.
    * 3a1. System shows an error message.
    * Use case resumes at step 3.
* 3b. User input is invalid.
    * 3b1. System shows an error message regarding the format.
    * Use case resumes at step 3.

**Use case: Search for Resident During Emergency**

**MSS**

1. User requests to find residents using one or more keywords.
2. System shows a filtered list of matching residents with their indices.
3. User identifies the correct resident and room number from the results.
4. User requests to update the resident's phone number to the latest provided.
5. System updates the record and confirms the change.

   Use case ends.

**Extensions**

* 1a. No residents match the keyword.
    * 1a1. System shows `0 persons listed!`.
    * Use case ends.
* 1b. The search keyword is too broad (e.g., >200 matches).
    * 1b1. User refines search.
    * Use case resumes at step 1.

**Use case: Mid-Semester Room Swap**

**MSS**

1. User requests to find the two residents involved in the swap.
2. System shows the current records for both residents.
3. User requests to update Resident A to a temporary placeholder room.
4. User requests to update Resident B to Resident A’s original room.
5. User requests to update Resident A to Resident B’s original room.
6. System confirms all updates are successful.

   Use case ends.

**Extensions**

* 3a. The user attempts to move a resident into an occupied room without using a placeholder.
    * 3a1. System shows a duplicate room error.
    * Use case resumes at step 3.

**Use case: Bulk Removal of Graduating Residents**

**MSS**

1. User requests to list all residents.
2. System shows the full list.
3. User requests to delete multiple residents by providing comma-separated indices.
4. System validates all provided indices.
5. System removes all specified records in one command.

   Use case ends.

**Extensions**

* 3a. At least one provided index is invalid.
    * 3a1. System shows an error message and aborts the command.
    * Use case resumes at step 2.

**Use case: Managing Medical/Special Needs**

**MSS**

1. User requests to find residents with a specific tag (e.g., "asthma").
2. System shows a list of residents matching the tag.
3. User requests to view the full details of a specific resident.
4. User requests to add a new specific dietary tag to that resident’s record.
5. System updates the resident record with the new tag.

   Use case ends.

**Extensions**

* 1a. No residents are found with that tag.
    * 1a1. System shows an empty list or "No residents found."
    * Use case ends.
* 4a. The user provides an invalid index for the update.
    * 4a1. System shows an error message.
    * Use case resumes at step 2.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should respond to most user commands within 2 seconds for typical usage involving up to 40 resident records.
5. Should store all resident information locally on the user’s device and should not require an internet connection to perform any core features.
6. Should provide clear and informative error messages when the user enters an invalid command or incorrect parameters.
7. Should preserve all stored resident data between application restarts, unless the user explicitly deletes the data.
8. Should allow users to recover the application state from saved data files without manual editing of the data files.
9. Should keep command output readable within a standard terminal window width (e.g., ~120 characters).

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Resident**: A person living in the residential college whose information is stored and managed by the system. Each resident record may include fields such as name, room number, and other optional details.
* **Resident Assistant (RA)**: The primary user of the application who manages resident information, performs onboarding, and maintains records throughout the semester for a batch of residents living in the residential college.
* **Comment**: A free-form note stored with a resident record for short contextual information such as follow-ups or special reminders.
* **User's Preferences (UserPref)**: Settings related to the application environment (e.g., window size or file paths) that are saved locally and loaded when the application starts.
* **JSON**: JSON (JavaScript Object Notation) is the data format used by the application to store resident information and user preferences on disk.
* **Index**: The number used by commands (e.g., `delete 1`) to identify a resident from the currently displayed list.
* **GUI (Graphical User Interface)**: The visual interface of the application, built using JavaFX, which allows users to interact with the system through visual components.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Sorting residents

1. Sorting residents by different fields

   1. Prerequisites: Multiple residents with different names, rooms, and phone numbers.
   
   1. Test case: `list -sort r/`<br>
      Expected: List is updated to show all residents sorted by room number (format: #BLOCK-ROOM[-LETTER]). Status message confirms sorting.

   1. Test case: `list -sort x/`<br>
      Expected: No sorting occurs. Error message "Invalid sort field! Supported field prefixes: n/, r/" is displayed.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Editing a person's comment

1. Adding or clearing a comment while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `comment 1 c/Requires wheelchair-accessible venue`<br>
      Expected: The first person's comment is updated. The success message is shown in the result display. The person card shows the new comment.

   1. Test case: `comment 1 c/`<br>
      Expected: The first person's comment is removed. The success message is shown in the result display. The comment label is no longer shown on the person card.

   1. Other incorrect comment commands to try: `comment`, `comment 1`, `comment 0 c/test`, `comment x c/test`, `comment 1 c/first c/second`<br>
      Expected: No person's comment is changed. Error details are shown in the result display.

### Saving data

1. Autosaving after a successful command

   1. Prerequisites: App has been launched at least once in a test folder.

   1. Test case: `add n/Test Resident r/#09-101` and then close the app.<br>
      Re-launch the app.<br>
      Expected: The newly added resident is still present in the list. The file `data/addressbook.json` exists in the app folder and contains the new resident record.

   1. Test case: `add n/Another Resident r/#09-102`, then enter an invalid command such as `delete 999999`.<br>
      Expected: The invalid command shows an error message and does not modify saved data. After closing and re-launching the app, `Another Resident` is still present and no unintended changes have been made.

1. Dealing with a missing data file

   1. Prerequisites: Close the app. In the app folder, ensure `data/addressbook.json` does not exist by deleting it or moving it elsewhere.

   1. Test case: Launch the app in that folder.<br>
      Expected: The app starts successfully and shows the sample residents in the GUI.

   1. Test case: After launch, execute a successful command such as `list` or `add n/Test Resident r/#10-101`.<br>
      Expected: A new `data/addressbook.json` file is created in the app folder.

1. Dealing with a corrupted data file

   1. Prerequisites: Close the app. Open `data/addressbook.json` in a text editor and replace its contents with invalid JSON such as `{ invalid json`.

   1. Test case: Launch the app.<br>
      Expected: The app starts successfully with an empty resident list instead of crashing, because invalid stored data cannot be loaded.

   1. Test case: Execute a successful command such as `add n/Recovered Resident r/#11-111`.<br>
      Expected: The app saves normally again, and `data/addressbook.json` is replaced with a valid file containing the new resident data.

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. Restore the existing Help window when `help` is run while it is minimised: currently, running `help` again can appear to do nothing if the Help window is minimised. We plan to change this so the same Help window is restored, brought to the front, and focused instead of remaining hidden.
2. Identify the offending index in bulk deletion failures: currently, if `delete 1,3,999` fails, the message does not clearly tell the user which index caused the command to be rejected. We plan to report the specific invalid index and clarify that no residents were deleted.
3. Make unsupported-prefix errors in `add` and `edit` more accurate: currently, unsupported prefixes such as `c/` in `add n/Amy r/#01-01 c/test` may be absorbed into another field and trigger a misleading room or tag format error. We plan to detect unsupported prefixes explicitly and report them directly, e.g. `Unsupported prefix in add command: c/`.
4. Allow both AND logic and OR logic in the `find` command: currently, `find` uses OR logic across all keywords, so users cannot require multiple conditions in one search. We plan to extend `find` so users can express AND conditions explicitly, while keeping OR searches available, e.g. `find John AND Doe` or `find John AND 14-203`.
5. Shorten overly verbose startup load errors while preserving useful details: currently, the app shows the original storage/parser error in the result display on startup so users are not left without feedback. Empty files are treated like missing data files and load sample residents, but malformed JSON can still produce a very long message because the raw parser output may include file excerpts, line numbers, and column numbers. We plan to keep the useful location details while shortening the displayed message, and move the full raw error to logs or an expandable UI.
6. Accept more valid room formats to account for more residences: currently, the room validation rules are stricter than some real residence naming schemes. We plan to expand accepted room formats so legitimate inputs from other residences can be stored without workarounds, while still rejecting malformed room entries. This includes accepting room inputs that omit the leading `#` by automatically prepending it during parsing, and treating alphabetic room suffixes case-insensitively so inputs such as `#14-203-d` and `14-203-D` are accepted consistently.
7. Allow names that contain prefix-like text such as `p/` inside the actual name: currently, names like `Chloe p/o Tan` can be misread because `p/` is treated as the phone prefix. We plan to refine parsing so such names can be entered normally without the command being split at `p/`.
8. Allow duplicate legal names by automatically appending a stored qualifier: currently, residents with the same legal name cannot both be added. We plan to allow the second and subsequent duplicates by automatically appending a distinguishing qualifier such as `Alex Tan (Block 14)` to the stored display name so each resident record remains uniquely identifiable.
9. Add a confirmation pop-up before `clear` removes all residents: currently, `clear` executes immediately and permanently. We plan to show a confirmation dialog that states all resident records will be deleted and only proceed when the user explicitly confirms.
10. Make the command box behave more like a terminal after execution: currently, the previous command text remains in the input box after a command runs. We plan to clear the command box after each submission, while also adding up-arrow and down-arrow history navigation so users can cycle through previous commands when they want to reuse or edit them.
