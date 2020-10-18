# Meeting Notes

<h3 align="center">Iteration One (9th September to 4th October)</h3>

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
- In this meeting, both of the group mates decided to first establish the back-end of the game.
- This would include created the boilerplate of classes that take care of the I/O e.g. creating, reading the game database.
- The basic data flow around the game was also discussed, which would eventually help to create the classes mentioned above.
- Regarding the task until the next meeting, we both had to create brief plans for the back-end.

*(20 days to go)*


**Saturday, 19th September**
- After creating the plans individually and discussing with each other in this meeting, it was found that we had quite a lot of overlap in what we did.
- This was a good and a bad thing; *good* because we were both on the same track, and *bad* because it would be hard to merge as it would take a long time determining which parts we want to keep etc.
- Due to this, we now decided to finally divide the work so that Shrey will work on the front-end of the game, and Jason will work on the back-end (with the complex data processing).
- The task for us until the next meeting was to decide how we want to implement saving the progress of the game, and to get the basics of the GUI to work.

*(16 days to go)*


**Tuesday, 22th September**
- This meeting was very short (~18 minutes).
- It was specifically called to decide on how the front-end would collaborate with the back-end to make sure that the game is functioning properly.
- A few refactoring decisions were made, which was moving some of the code from the `IO` class to their respective database classes.

*(13 days to go)*

**Friday, 25th September**
- This was a progress review meeting (specially for the GUI) in order to discuss everything that is currently being implemented.
- It was kept relatively short to discuss how we want to insert the functionalities for the Game Module (displaying the grid etc).
- The plan until the next meeting was to get the basics of the Game Module done, includes the client-side and back-end.

*(10 days to go)*

**Monday, 28th September**
- There was some confusion regarding how to traverse through the current list of Game questions to create the grid, hence we had this meeting.
- Some of the design decisions were changed from the earlier meetings, and change the way we were storing the clues within the internal database.
- The plan until the next meeting was to get the Game Module done using the new architecture - this meeting was important to resolve the misunderstandings.

 *(7 days to go)*

**Wednesday, 30th September**
- At this point, all of the core functionalities for Quinzical were all implemented.
- The important discussion in this meeting was based mainly on how we want to check for the answers from the users. One of the key decisions made was to ignore the "a", "the" within the answers and while providing clues to the user. This is because "a" and "the" are technically not part of the answer.
- The plan until next meeting was to refactor the existing code into smaller coherent classes, and to fix the back-end answer checking system.

*(5 days to go)*

**Friday, 2nd September**
- Most of the implementation (business-logic) was completed by this point.
- In the meeting, we had done some testing together to make sure everything was functioning as expected. This type of testing was done to get inputs from all the team members on their final thoughts.
- Some tasks to do until the final meeting are -
    - Change some of the implementation to store the progress in a hidden directory.
    - Clean up the Team Agreement document so that it can be appended to the end of our individual report.
    - Delete the configuration files of your IDE from the repository.
    - Refactor the "Speaker" class, and then place "Speaker" and "Formatting" classes to a new package called "tasks".

*(3 days to go)*

**Sunday, 4th September**
- This meeting was intended to do some final checking, before the submission for Assignment 3.
- Performing checks together enabled us to get lots of cases for checking that the user may go through, and therefore eliminate any bugs that exist within our application.
- Also decided on some points to talk about in the client presentation.
- There were no tasks/todo decided until next time, because we wanted to get some rest before starting to prepare for the overall project.

*(1 day to go)*

<hr>

<h3 align="center">Iteration Two (5th September to 19th October)</h3>

**Tuesday, 13th September**
- In this meeting, we discussed the extra functionalities that we needed to implement for the next iteration of the application.
- Some key decisions were made in this meeting regarding the design -
    - Implementing the selection of categories for game using toggle buttons.
    - Implementing the functionality for comparing perfomance using some facts such as "time practiced", "ratio of correct/incorrect" etc.
    - Implementing the functionality for motivating the user by giving them XP points whenever they get an answer correct (and store this between games).

*(6 days to go)*

**Friday, 16th September**
- Prior to this meeting, the backend changes were made regarding how the user would be able to give a list of categories that they have selected, in order to populate the game database.
- However, there was some confusion regarding how to use the backend API for the frontend.
- This meeting was just to solve that issue, and talk about some future decisions.

*(3 days to go)*