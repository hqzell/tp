---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---
# User Guide

**RACE (Residential Assistant’s Contact Entries)** is a desktop application for managing resident information, optimized for use via a Command Line Interface (CLI) while still providing the benefits of a Graphical User Interface (GUI). It allows Residential Assistants to quickly store, update, and retrieve resident details in a secure, centralised system, replacing fragmented and inefficient workflows. Fast CLI commands enable efficient data entry and management, especially during high-intensity periods like onboarding.

**Target Users:** Residential Assistants (RAs)  

**Assumptions:** Users have basic computer literacy and are comfortable with typing commands, navigating lists, and interpreting simple system feedback. They can quickly pick up terminal-style interactions and prefer efficient, keyboard-driven workflows for repetitive tasks.

<!-- * Table of Contents -->
<!-- Will only show when printed or exported as PDF, as sidebar is provided when viewing on the web. -->
<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-T10-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all residents.

   * `add n/John Doe p/98765432 e/e1234567@u.nus.edu r/#01-01` : Adds a resident named `John Doe` to the address book.

   * `delete 3` : Deletes the 3rd resident shown in the current list.

   * `clear` : Deletes all residents.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/halal` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/halal`, `t/halal t/allergies` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Dash-based inputs are options.<br>
  Some options take a value, while others are standalone flags.

* Options that take a value must be followed by one.<br>
  e.g. `-sort PREFIX` can be used as `-sort n/`.

* Some options are standalone flags which do not take a value.<br>
  e.g. `-newtag` should be entered by itself, not as `-newtag yes`.

* If a flag is specified multiple times, it will be treated as a single flag.<br>
  e.g. `-newtag -newtag` is treated the same as `-newtag`.

* For commands that accept multiple values in one parameter, use comma-separated input.<br>
  e.g. `delete 1,3,5` deletes residents at indices 1, 3, and 5.

* Leading and trailing spaces are ignored for command arguments.<br>
  e.g. `delete  1, 3 ,5 ` is accepted as `delete 1,3,5`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* For fields that should appear at most once (e.g. `n/`, `p/`, `e/`, `r/`, `c/`, and the `-newtag` flag), providing the same prefix more than once in a single command is rejected.

* When the app reports **`Invalid command format!`**, the message often includes a **second line** showing the correct usage for that command — read both lines together.
</box>

### Viewing help : `help`

Shows a help window with a link to the application’s online user guide.

Format:
`help`

Expected Output:
After inputting `help`:

`Opened help window.`

<box type="info" seamless>

**Note:**
* The help window includes a URL to the online user guide.
* You can also open help from the **Help** menu or press **`F1`**.

</box>


<box type="tip" seamless>

**Tips:**
* Use this command whenever you need a quick reminder of the documentation link.

* If the help window is **minimised**, it may stay hidden when you run `help` again — see [Known issues](#known-issues).

</box>

---

### Adding a resident: `add`

Adds a resident to the address book.

Format: `add n/NAME [p/PHONE] [e/EMAIL] r/ROOM [t/TAG]…​ [-newtag]`

<box type="tip" seamless>

**Tip:** A resident can have any number of tags (including 0)
</box>

<box type="info" seamless>

**Note:**
* Built-in tags are `vegetarian`, `vegan`, `halal`, and `allergies`.
* All tags are case-sensitive. For example, `study-group` and `Study-Group` are treated as different tags.
* Kebab-case is recommended for consistency, e.g. `study-group`.
* **Spaces are not allowed in tags.** Use hyphens to separate words instead (e.g., `study-group` not `study group`).
* To create a new custom tag while adding a resident, include `-newtag` in the same command.
* If you include `-newtag` for a tag that already exists, RACE will still accept the command. No duplicate tag is created.
* Duplicate checks apply to `name`, `room`, `phone`, and `email`.
* `phone` and `email` are optional, but if provided, they must still be unique among residents.
* If you previously ran `list -sort ...`, adding a resident preserves that active sort order in the displayed list.

</box>

Examples:
* `add n/John Doe p/98765432 e/e1234567@u.nus.edu r/#14-203-D`
* `add n/Betsy Crowe t/vegetarian e/e4567890@u.nus.edu r/#10-10 p/1234567 t/allergies`
* `add n/Alex Tan Jia-en r/#12-101 p/91234567 t/study-group -newtag`

<box type="warning" seamless>

**Caution:**
* Text before the first prefix (e.g. `add stray n/Alice r/#1-01`) triggers `Invalid command format!`.
* Missing required fields:
  * Missing `n/` -> `Missing required parameter: n/NAME`
  * Missing `r/` -> `Missing required parameter: r/ROOM`
* Duplicate fields are rejected with targeted messages:
  * Name -> `Name already exists in the address book. E.g. try using Alex Tan (Year 2) instead of Alex Tan.`
  * Phone -> `Phone number already exists in the address book.`
  * Email -> `Email already exists in the address book.`
  * Room -> `Room number already exists in the address book.`
* `-newtag` must not be followed by a value; e.g. `-newtag oops` is rejected.

</box>

---

### Working with tags

Tags help you label residents with quick categories or notes.

<box type="info" seamless>

**Note:**
* The four built-in tags are always available: `vegetarian`, `vegan`, `halal`, `allergies`.
* Any other tag is treated as a custom tag.
* Custom tags must already exist before you can reuse them.
* **Spaces are not allowed in tags.** Use hyphens to separate words (e.g., `project-team` not `project team`).
* If you want to introduce a brand-new custom tag, use `-newtag` together with `add` or `edit`.
* If you use `-newtag` for an already existing tag, the command still succeeds normally.

</box>

Examples:
* `add n/Sam Lee r/#08-110 t/halal` uses a built-in tag.
* `add n/Sam Lee r/#08-110 t/study-group -newtag` creates and uses a new custom tag.
* `edit 2 t/study-group` reuses an existing custom tag.
* `edit 2 t/Study-Group -newtag` creates a different tag from `study-group` because tags are case-sensitive.

---

### Listing all residents : `list`

Shows all residents in the address book. You can optionally sort the displayed list by name or room.

Format:
`list`
`list -sort PREFIX`

Expected Output:
When sorting is NOT used:
`Listed all residents`

When sorting IS used:
`Listed all residents sorted by FIELD` (where `FIELD` is `name` or `room`, depending on the prefix you used)

<box type="info" seamless>

**Note:**
* Supported sort prefixes:
  * `n/` (name)
  * `r/` (room)
* If you omit `-sort`, residents are shown in the order stored in the app (typically the order they were added).

</box>

<box type="warning" seamless>

**Caution:**
* Invalid command format → `Invalid command format!`
* Invalid sort prefix (e.g., `list -sort x/`) → `Invalid sort field! Supported field prefixes: n/, r/`

</box>

<box type="tip" seamless>

**Tips:**
* Use `list -sort PREFIX` to review residents in a predictable order.
* After [`find`](#finding-residents-by-keyword-name-room-or-tag-find), any active **sort order is cleared** until you run `list -sort ...` again.

</box>

Examples:
Input → Expected Output
* `list` → `Listed all residents`
* `list -sort r/` → `Listed all residents sorted by room`
* `list -sort n/` → `Listed all residents sorted by name`


### Editing a resident : `edit`

Edits an existing resident in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROOM] [t/TAG]…​ [-newtag]`

* Edits the resident at the specified `INDEX`. The index refers to the index number shown in the displayed list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the resident will be removed i.e. adding of tags is not cumulative.
* You can remove all of the resident’s tags by typing `t/` without
    specifying any tags after it.
* If the edited tags include a brand-new custom tag, include `-newtag` in the same command.

Examples:
*  `edit 1 p/91234567 e/e1222222@u.nus.edu` Edits the phone number and email address of the 1st resident to be `91234567` and `e1222222@u.nus.edu` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd resident to be `Betsy Crower` and clears all existing tags.
*  `edit 3 t/project-team -newtag` Replaces the 3rd resident's tags with `project-team` and creates that custom tag if needed.

### Commenting on a resident: `comment`

Adds, edits, or deletes a comment for a specific resident.

Format: `comment INDEX c/[COMMENT]`

Expected output (the full line includes the resident’s details after the name):
* New comment (resident had no comment before): `Added comment to Person: NAME; ...`
* Replacing an existing comment: `Updated comment for Person: NAME; ...`
* Deleting a comment (`c/` with no text): `Removed comment from Person: NAME; ...`

<box type="info" seamless>

**Note:**
* The `INDEX` refers to the unique numbered position shown in the current list (via `list` or `find`). The `INDEX` must be a positive integer (1, 2, 3, …).
* A new comment will overwrite any existing comment.
* To delete a comment, use `c/` with no text.
* If the comment only contains whitespace, it is treated as empty and the comment is removed.

</box>

Examples:
**Input → Expected Output**
* `comment 1 c/Prefers WhatsApp messages before visits`  
  → Adds a new comment to the 1st resident  
* `comment 2 c/Lost room key on 15 Mar`  
  → Replaces existing comment  
* `comment 3 c/`  
  → Deletes the existing comment  

<box type="warning" seamless>

**Caution:**
* Invalid index → `The person index provided is invalid`
* Missing parameters → `Invalid command format!`

</box>

<box type="tip" seamless>

**Tips:**
* Use comments to log important interactions (e.g., maintenance issues, noise complaints, welfare check-ins).
* Use `comment INDEX c/` to quickly clear outdated notes.

</box>

### Finding residents by keyword (name, room, or tag): find

Finds residents whose **name**, **room**, or **tags** contain any of the given keywords.

Format:
* `find KEYWORD [MORE_KEYWORDS]...`

Expected Output:
`X persons listed!`

<box type="info" seamless>

**Note:**
* Search is case-insensitive.
* Partial matching is supported.
  * e.g., `Han` matches `Hans`
  * e.g., `#14-2` matches `#14-203-D`
  * e.g., `allerg` matches tag `allergies`
* `find` checks resident **name**, **room**, and **tags**.
* If multiple keywords are provided, OR logic is used (a resident is returned if any keyword matches).

</box>

Examples:
**Input → Expected Output**
* `find alex`  
  → Shows residents whose names contain `alex`
* `find #14-2`  
  → Shows residents in rooms containing `#14-2`
* `find allerg`  
  → Shows residents with tags containing `allerg` (e.g., `allergies`)
* `find alex #14-2 halal`  
  → Shows residents matching any of these keywords in name, room, or tags  

<box type="warning" seamless>

**Caution:**
* No input provided → `Invalid command format!`

</box>

<box type="tip" seamless>

**Tips:**
* Use multiple keywords to quickly find groups (e.g., `find Alex Bob David Hannah`).
* Combine with other commands for efficiency:
  * `find Alex` → `delete 1`
  * `find Alex` → `edit 1 r/#14-205`
* To sort the list again after filtering, run [`list -sort ...`](#listing-all-residents--list).

</box>

### Deleting a resident: `delete`

Deletes one or more residents from the address book.

Format: `delete INDEX[,INDEX]...`

Expected Output:
* One index: `Deleted Person: NAME; Phone: PHONE; Email: EMAIL; ...`
* Several indices: `Deleted Persons:` followed by one block per deleted resident (same detail format as above)

<box type="info" seamless>

**Note:**
* The `INDEX` refers to the position in the current list (`list` or `find`).
* The `INDEX` must be a positive integer (1, 2, 3, …).
* Deletion is permanent and cannot be undone.

</box>

Examples:
**Input → Expected Output**
* `delete 1`  
  → Deletes the 1st resident  
* `list` → `delete 3`  
  → Deletes the 3rd resident in the list  
* `find Alex` → `delete 1`  
  → Deletes the first matching result  
* `delete 1,3`  
  → Deletes the 1st and 3rd residents in the **current** list (see [command format notes](#features))

<box type="warning" seamless>

**Caution:**
* Invalid index → `The person index provided is invalid`
* Missing index → `Invalid command format!`

</box>

<box type="tip" seamless>

**Tips:**
* Always confirm the correct index using `list` or `find`.
* Back up `data/addressbook.json` before bulk deletions.
* After `find`, indices refer to filtered results (not the full list).

</box>

### Clearing all residents: `clear`

Clears all residents from the address book.

Format: `clear`

Expected Output:
`Address book has been cleared!`

<box type="info" seamless>

**Note:**
* This action removes all data permanently and cannot be undone.

</box>

Examples:
**Input → Expected Output**
* `clear`  
  → Removes all residents  
* `clear abc`  
  → Still removes all residents (extra input ignored)  

<box type="warning" seamless>

**Caution:**
* None (unless command is misspelled)

</box>

<box type="tip" seamless>

**Tips:**
* Use `clear` at the start of a new semester to reset the system.
* Back up `data/addressbook.json` before using this command.
* Avoid accidental execution.

</box>

### Exiting the program: `exit`

Closes the application.

Format: `exit`

Expected Output:
`Exiting Address Book as requested ...`  
(Application window closes)

<box type="info" seamless>

**Note:**
* All data is automatically saved before exiting.

</box>

Examples:
**Input → Expected Output**
* `exit`  
  → Closes the application  
* `exit 123`  
  → Still exits (extra input ignored)  

<box type="warning" seamless>

**Caution:**
* None (unless command is misspelled)

</box>

<box type="tip" seamless>

**Tips:**
* Use `exit` for quick keyboard-based closing.
* You can also close the window manually — data is auto-saved.
* Ensure all tasks are completed before exiting.

</box>

### Saving the data

AddressBook data are saved to disk automatically after every successful command. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
If you edit tags manually, keep the `customTags` list reasonably aligned with the custom tags used by residents. On load, RACE ensures that all tags used by residents are present in `customTags`, but extra unused entries in `customTags` may still remain.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

### Resident details

**Q**: What information can I store for each resident?<br>
**A**: In RACE, each resident has a name and room, plus optional phone number, email address, tags, and a comment.

**Q**: Is it required to enter the phone number and email for a resident?<br>
**A**: No. You can omit these when using the `add` command.

**Q**: Can I add a comment while creating a resident?<br>
**A**: No. Comments cannot be added as part of the `add` command. They must be added later with the `comment` command.

### Rules and limitations

**Q**: Can I add two residents with the same name?<br>
**A**: No. Name is part of duplicate checking, so adding another resident with the same name is rejected. If two residents have the same legal name, use a differentiator in the stored name, e.g., `Alex Tan (Block 14)` and `Alex Tan (Block 9)`.
**Q**: What fields are checked for duplicates when adding a resident?<br>
**A**: RACE rejects `add` if an existing resident already has the same `name`, `room`, `phone` (when provided), or `email` (when provided).
**Q**: Can I delete more than one resident at once?<br>
**A**: Yes. Use comma-separated indices, e.g. `delete 1,3,5` (see the [delete](#deleting-a-resident-delete) section).

**Q**: How do I create a new custom tag?<br>
**A**: Use `-newtag` together with `add` or `edit`. For example, `add n/Sam Lee r/#08-110 t/study-group -newtag` creates `study-group` if it does not already exist.

**Q**: Are tags case-sensitive?<br>
**A**: Yes. `study-group` and `Study-Group` are treated as different tags. For consistency, we recommend entering tags in kebab-case.

**Q**: Can tags contain spaces?<br>
**A**: No. Tags cannot contain spaces. Use hyphens to separate words instead (e.g., `study-group` instead of `study group`).

**Q**: What happens if I use `-newtag` for a tag that already exists?<br>
**A**: Nothing extra happens. The command still works normally, and RACE simply reuses the existing tag.

### Saving and data

**Q**: Does the app save automatically?<br>
**A**: Yes. Data is saved automatically after every successful command.

**Q**: Where is my data stored?<br>
**A**: Your data is stored in `data/addressbook.json`, in the same folder as the `.jar` file you use to open the app.

**Q**: How do I move my data to another computer, or pass it to another RA taking over?<br>
**A**: Close the app first. Then find `data/addressbook.json` in the same folder as the `.jar` file you use to open the app. Copy that file into the `data` folder within the same folder as the `.jar` file in the new setup. If the new setup already has its own `addressbook.json`, replace it with your copied file. When you open the app again, your resident list should appear there.

**Q**: What happens the first time I open the app?<br>
**A**: If no data file exists yet, the app starts with sample residents. These are written to `data/addressbook.json` when you make your first successful change. If the file exists but cannot be loaded properly, the app starts with an empty list, and your next successful change saves a fresh data file.

### Help

**Q**: How do I get help?<br>
**A**: Use the `help` command or press `F1` to open the help window.

---

## Known issues

1. **Window position and size may be restored poorly after display changes.** If you move the app to another screen, change to a smaller screen, or use a lower resolution, the app may reopen partially or fully off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command again (or use the `Help` menu, or the keyboard shortcut `F1`), the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **Closing the app with the window close button behaves differently from using `exit`.** The latest window size and position may not be saved, and if the Help Window is still open, the application may continue running until that window is also closed.
4. **Sample data on first launch is not written to disk immediately.** If no data file exists yet, the app starts with sample residents in memory. If you close the app immediately without running a successful command, `data/addressbook.json` may not be created yet.
5. **Unsupported prefixes may sometimes lead to misleading error messages.** For example, using `c/` inside `add` is not supported, but the app may treat it as part of the previous field and report a room or tag format error instead of a clearer usage error.

---

## Command summary

 Action      | Format, Examples                                                                                                                                     
-------------|------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add**     | `add n/NAME [p/PHONE] [e/EMAIL] r/ROOM [t/TAG]…​ [-newtag]` <br> e.g., `add n/James Ho p/22224444 e/e1234567@u.nus.edu r/#14-203-D t/study-group -newtag` 
 **Clear**   | `clear`                                                                                                                                              
 **Delete**  | `delete INDEX[,INDEX]...`<br> e.g., `delete 3`, `delete 1,3,5`                                                                                                                  
 **Edit**    | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROOM] [t/TAG]…​ [-newtag]`<br> e.g., `edit 2 n/James Lee t/project-team -newtag`                            
 **Find**    | `find KEYWORD [MORE_KEYWORDS]` or `find ROOM`<br> e.g., `find James Jake`, `find #14-203-D`                                                          
 **List**    | `list [-sort PREFIX]` <br> e.g., `list -sort r/`                                                                                                            
 **Help**    | `help`                                                                                                                                               
 **Comment** | `comment INDEX c/[COMMENT]`<br> e.g., `comment 1 c/Prefers WhatsApp messages before visits`, `comment 3 c/`                                            
