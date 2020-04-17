# Smart-Kanban-Board

[![CodeFactor](https://www.codefactor.io/repository/github/cloudyyoung/smart-kanban-board/badge?s=8774062d8382e00db0e730beda189124ce4d9eb3)](https://www.codefactor.io/repository/github/cloudyyoung/smart-kanban-board)
![Java-Format](https://github.com/CloudyYoung/Smart-Kanban-Board/workflows/Java-Format/badge.svg)
[![Build Status](https://travis-ci.com/CloudyYoung/Smart-Kanban-Board.svg?token=xvFzJVyxhP7YsvANEEBw&branch=master)](https://travis-ci.com/CloudyYoung/Smart-Kanban-Board)


## Contributors
- [Yunfan Yang (@CloudyYoung)](https://github.com/CloudyYoung)
- [Chenyue Li (@Jimschenchen)](https://github.com/jimschenchen)
- [Jerremy Lewis (@lewisjerremy)](https://github.com/lewisjerremy)
- [Ben Wood (@Braindamaged69)](https://github.com/Braindamaged69)
- [Caleb Wannamaker (@calebwannamaker)](https://github.com/calebwannamaker)


## Description
A kanban board is a popular organizational tool that helps the user to visualize workflow. This is done by taking a project, 
dividing it into smaller events, and then staging the events so that they can be moved through a pipeline of categories
as they are completed. There is a standard of four categories to divide work into backlog, to do, doing, and done. These
boards are typically done on a large surface like a whiteboard or a wall. 

This typically does not work well for users that work in different locations (such as students) because they are not portable
and take a lot of space. Another drawback is that when the user has lots of projects on the go, the board can become
overwhelming with events. 

Our SmartKanban application takes the fundamental principals of a physical kanban board and makes it digital. To solve the 
problem of overwhelming the user with events, we are in the process of developing an algorithm that considers the users 
availability for work, then decides the most important events for the day given the workload of events backlogged and their 
priority relative to other events. The most important events are then added to a daily kanban board for the user to complete.

## Where to Find Latest Release
It can be found under "Release" panel.

## How to Compile and Run
The Github Wiki link is here: [https://github.com/CloudyYoung/Smart-Kanban-Board/wiki/Compile-and-Run-the-Application](https://github.com/CloudyYoung/Smart-Kanban-Board/wiki/Compile-and-Run-the-Application)
**Notice that the wiki is updated, if you are running the project v2.0 for the first time, you need to follow the new configuration procedure.**

## Terminal Commands
The text-based terminal version supports limited functionalities which are sign in, display the boards, columns and events; the GUI supports full functionalities.

Welcome Page
 - You will need to enter your account username and password to sign in.

Home Page
 - `help`: list all the commands
 - `todao`: Display the Today board
 - `overview`: Display the Overview board
 - `board`: List all the boards
 - `exit`: Exit the program

Board Page
 - `back`: Go back to previous page
 - `#id`: Enter a column id to check a specific column

Column Page
 - `back`: Go back to previous page
 - `#id`: Enter an event id to check an specific event

## References

### Libraries
 - Google Gson: [https://github.com/google/gson](https://github.com/google/gson)

 - JavaFX 11: [https://openjfx.io/](https://openjfx.io/)

### Code snippet
Code snippet references are **in the specific files** and link is beside javadoc tag `@see`.