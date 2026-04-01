---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---
# User Guide

![Ui](images/Ui.png)

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

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/e1234567@u.nus.edu r/#01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* For commands that accept multiple values in one parameter, use comma-separated input.<br>
  e.g. `delete 1,3,5` deletes residents at indices 1, 3, and 5.

* Leading and trailing spaces are ignored for command arguments.<br>
  e.g. `delete  1, 3 ,5 ` is accepted as `delete 1,3,5`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a help window with a link to the application’s online user guide.

Format:
`help`

Expected Output:
After inputing `help`:

`Opened help window.`

<box type="info" seamless>

**Note:**
* The help window includes a URL to the online user guide.

</box>


<box type="tip" seamless>

**Tips:**
* Use this command whenever you need a quick reminder of the documentation link.

* If the help window is already open but minimized, running `help` again will focus it (you may need to restore it manually).

</box>

---

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME [p/PHONE] [e/EMAIL] r/ROOM [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/e1234567@u.nus.edu r/#14-203-D`
* `add n/Betsy Crowe t/friend e/e4567890@u.nus.edu r/#10-10 p/1234567 t/vegetarian`

---

### Listing all persons : `list`

Shows a list of all persons in the address book. Optionally sorts the list.

Format:
`list`
`list -sort PREFIX`

Expected Output:
When sorting is NOT used:
`Listed all residents`

When sorting IS used:
`Listed all residents sorted by FIELD`

<box type="info" seamless>

**Note:**
Supported sort prefixes:
* `n/` (name)
* `r/` (room)
* `p/` (phone)
* `e/` (email)

</box>

<box type="warning" seamless>

**Caution:**
* Invalid command format → `Invalid command format!`
* Invalid sort prefix (e.g., `list -sort x/`) → `Invalid sort field! Supported field prefixes: n/, r/, p/, e/`

</box>

<box type="tip" seamless>

**Tips:**
* Use `list -sort PREFIX` to review residents in a predictable order.
* If you run `find`, the displayed order may reset—run `list -sort ...` again if needed.

</box>

Examples:
Input → Expected Output
* `list` → `Listed all residents`
* `list -sort r/` → `Listed all residents sorted by room`
* `list -sort n/` → `Listed all residents sorted by name`


### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROOM] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/e1222222@u.nus.edu` Edits the phone number and email address of the 1st person to be `91234567` and `e1222222@u.nus.edu` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Commenting on a resident: `comment`

Adds, edits, or deletes a comment for a specific resident.

Format: `comment INDEX c/[COMMENT]`

Expected Output:
* When adding/updating:  
  `Added comment to Person: NAME; ...`
* When deleting (empty comment):  
  `Removed comment from Person: NAME; ...`

<box type="info" seamless>

**Note:**
* The `INDEX` refers to the unique numbered position shown in the current list (via `list` or `find`). The `INDEX` must be a positive integer (1, 2, 3, …).
* A new comment will overwrite any existing comment.
* To delete a comment, use `c/` with no text.
* Leading and trailing spaces in comments are ignored.
* If the comment only contains whitespace, it is treated as empty.

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
* Invalid index → `The person index provided is invalid.`
* Missing parameters → `Invalid command format!`

</box>

<box type="tip" seamless>

**Tips:**
* Use comments to log important interactions (e.g., maintenance issues, noise complaints, welfare check-ins).
* Use `comment INDEX c/` to quickly clear outdated notes.

</box>

### Finding residents by name or room: `find`

Finds residents by name (matches names containing any keyword) or by exact room.

Format:
* `find KEYWORD [MORE_KEYWORDS]`
* `find ROOM`

Expected Output:
`X persons listed!`

<box type="info" seamless>

**Note:**

**Name search**
* Case-insensitive (e.g., `hans` matches `Hans`)
* Matches full words only (e.g., `Han` does not match `Hans`)
* Keyword order does not matter (e.g., `find Hans Bo` matches `Bo Hans`)
* Uses OR logic (e.g., `find Hans Bo` returns `Hans Bo`, `Bo Tan`)

**Room search**
* Must match exact format `#BLOCK-ROOM-LETTER` (e.g., `#14-203-D`)
* Matches must be exact (e.g., `#05-203-D` ≠ `#5-203-D`)

</box>

Examples:
**Input → Expected Output**
* `find John`  
  → Shows residents named `john`, `John Doe`
* `find alex david`  
  → Shows residents `Alex Yeoh`, `David Li`
* `find #14-203-D`  
  → Shows residents in that room  

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

</box>

### Deleting a resident: `delete`

Deletes a resident from the address book.

Format: `delete INDEX`

Expected Output:
`Deleted Person: NAME; Phone: PHONE; Email: EMAIL; ...`

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

<box type="warning" seamless>

**Caution:**
* Invalid index → `The person index provided is invalid.`
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

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
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
**A**: No. The app treats residents with the same name as duplicates, even if their other details are different. Try adding unique qualifiers to the name, e.g., `Alex Tan (Block 14)` and `Alex Tan (Block 9)`.
**Q**: Can I delete more than one resident at once?<br>
**A**: Yes. You can delete multiple residents in one command by providing multiple indices.

### Saving and data

**Q**: Does the app save automatically?<br>
**A**: Yes. Successful changes are saved automatically.

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
 **Add**     | `add n/NAME [p/PHONE] [e/EMAIL] [r/ROOM [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/e1234567@u.nus.edu r/#14-203-D t/friend t/colleague` 
 **Clear**   | `clear`                                                                                                                                              
 **Delete**  | `delete INDEX`<br> e.g., `delete 3`                                                                                                                  
 **Edit**    | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROOM] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/e1234567@u.nus.edu`                            
 **Find**    | `find KEYWORD [MORE_KEYWORDS]` or `find ROOM`<br> e.g., `find James Jake`, `find #14-203-D`                                                          
 **List**    | `list [-sort PREFIX]` <br> e.g., `list -sort r/`                                                                                                            
 **Help**    | `help`                                                                                                                                               
 **Comment** | `comment INDEX c/[COMMENT]`<br> e.g., `comment 1 c/Prefers WhatsApp messages before visits`, `comment 3 c/`                                            
