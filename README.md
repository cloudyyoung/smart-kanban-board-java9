# Smart-Kanban-Board

[![CodeFactor](https://www.codefactor.io/repository/github/cloudyyoung/smart-kanban-board/badge?s=8774062d8382e00db0e730beda189124ce4d9eb3)](https://www.codefactor.io/repository/github/cloudyyoung/smart-kanban-board)
![Java-Format](https://github.com/CloudyYoung/Smart-Kanban-Board/workflows/Java-Format/badge.svg)
[![Build Status](https://travis-ci.com/CloudyYoung/Smart-Kanban-Board.svg?token=xvFzJVyxhP7YsvANEEBw&branch=master)](https://travis-ci.com/CloudyYoung/Smart-Kanban-Board)


## Team Members
- [Jerremy Lewis (@lewisjerremy)](https://github.com/lewisjerremy)
- [Ben Wood (@Braindamaged69)](https://github.com/Braindamaged69)
- [Caleb Wannamaker (@calebwannamaker)](https://github.com/calebwannamaker)
- [Yunfan Yang (@CloudyYoung)](https://github.com/CloudyYoung)


## Description
A kanban board is a popular organizational tool that helps the user to visualize workflow. This is done by taking a project, 
dividing it into smaller events, and then staging the events so that they can be moved through a pipeline of categories
as they are completed. There is a standard of four categories to divide work into `backlog`, `to do`, `doing`, and `done`. These
boards are typically done on a large surface like a whiteboard or a wall. 

This typically does not work well for users that work in different locations (such as students) because they are not portable
and take a lot of space. Another drawback is that when the user has lots of projects on the go, the board can become
overwhelming with events. 

Our SmartKanban application takes the fundamental principals of a physical kanban board and makes it digital. To solve the 
problem of overwhelming the user with events, we are in the process of developing an algorithm that considers the users 
availability for work, then decides the most important events for the day given the workload of events backlogged and their 
priority relative to other events. The most important events are then added to a daily kanban board for the user to complete.

## Where to Find Demo One
It can be found under "Release" panel or here:
[https://github.com/CloudyYoung/Smart-Kanban-Board/tree/v1.0](https://github.com/CloudyYoung/Smart-Kanban-Board/tree/v1.0)

## How to Compile and Run
The Github Wiki link is here: [https://github.com/CloudyYoung/Smart-Kanban-Board/wiki/Compile-and-Run-the-Application](https://github.com/CloudyYoung/Smart-Kanban-Board/wiki/Compile-and-Run-the-Application)

## Terminal Commands
Log-in Page
- `help` : list commands
- `username` : enter your username for authentication
- `create` : create a new account
- `forgot` : recover an account
- `exit` : terminate the program

Main Page
- `help` : list commands
- `today` : prints your daily board
- `list` : prints a list of your open boards
- `new` : create a new board
- `calendar` : calendar
- `settings` : navigates to settings page
- `exit` : terminate the program

Settings Page
- `help` : list commands
- `kanban settings` : change board preferences
- `time settings` : change availability preferences
- `account settings` : change account preferences
- `back` return to main page
- `exit` : terminate the program

## References
JSON simple
- [https://github.com/fangyidong/json-simple](https://github.com/fangyidong/json-simple)

JUnit 5
- [https://github.com/junit-team/junit5](https://github.com/junit-team/junit5)

Other code snippet references are in the files javadoc `@see`.