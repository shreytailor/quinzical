<h1 align="center">Quinzical with JavaFX 🇳🇿</h1>

This repository contains all the documentation, design decisions, meeting notes and everything regarding the development of Quinzical.

### Table of Contents

1. [Meeting Notes](#meeting-notes)

<br>

## Meeting Notes

**Wednesday, 9th September**
- Since this was the first meeting, it was kept very non-technical. The priority was to get to know more about each other.
- Thereafter, a [shared document](https://docs.google.com/document/d/1tO1x4oD1I4r3kxlxod0DCqWr4hXzNxF1erzF1AMnocE/edit?usp=sharing) was created where we briefly discussed all the aspects of the team agreement brief, and decided on some team values.
- The task for both of us until the next meeting is to read over the assignment to understand it well before making a start on it.

*(26 days to go)*


**Friday, 11th September**
- After going through the brief, we were definitely more aware of the scope of the assignment by now.
- In the meeting, it was decided that maybe we should start wire-framing the different parts of the game, while also thinking about the user experience. This was the task until our next meeting.

*(24 days to go)*


**Tuesday, 15th September**
- In this meeting, both of the group mates decided to first establish the backend of the game.
- This would include created the boilerplate of classes that take care of the I/O e.g. creating, reading the game database.
- The basic data flow around the game was also discussed, which would eventually help to create the classes mentioned above.
- Regarding the task until the next meeting, we both had to create brief plans for the backend.

*(20 days to go)*


**Saturday, 19th September**
- After creating the plans individually and discussing with each other in this meeting, it was found that we had quite a lot of overlap in what we did.
- This was a good and a bad thing; *good* because we were both on the same track, and *bad* because it would be hard to merge as it would take a long time determining which parts we want to keep etc.
- Due to this, we now decided to finally divide the work so that Shrey will work on the frontend of the game, and Jason will work on the backend (with the complex data processing).
- The task for us until the next meeting was to decide how we want to implement saving the progress of the game, and to get the basics of the GUI to work.

*(16 days to go)*


**Tuesday, 22th September**
- This meeting was very short (~18 minutes).
- It was specifically called to decide on how the frontend would collaborate with the backend to make sure that the game is functioning properly.
- A few refactoring decisions were made, which was moving some of the code from the `IO` class to their respective database classes.

*(13 days to go)*

**Friday, 25th September**
- This was a progress review meeting (specially for the GUI) in order to discuss everything that is currently being implemented.
- It was kept relatively short to discuss how we want to insert the functionlities for the Game Module (displaying the grid etc).
- The plan until the next meeting was to get the basics of the Game Module done, includes the client-side and back-end.

*(10 days to go)*

**Monday, 28th September**
- There was some confusion regarding how to traverse through the current list of Game questions to create the grid, hence we had this meeting.
- Some of the design decisions were changed from the earlier meetings, and change the way we were storing the clues within the internal database.
- The plan until the next meeting was to get the Game Module done using the new architecture - this meeting was important to resolve the misunderstandings.

 *(7 days to go)*

**Wednesday, 30th September**
- At this point, all of the core functionlities for Quinzical were all implemented.
- The important discussion in this meeting was based mainly on how we want to check for the answers from the users. One of the key decisions made was to ignore the "a", "the" within the answers and while providing clues to the user. This is because "a" and "the" are technically not part of the answer.
- The plan until next meeting was to refactor the existing code into smaller coherent classes, and to fix the back-end answer checking system.

*(5 days to go)*