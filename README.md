# frc-2023
Robot code for the 2023 FRC season Charged Up presented by Haas.

## repo layout
This project is structured using the basic WPILib template, so it is very similar to last year!

| File / Folder | Purpose | Do you need to edit / care about it |
| ------------- | ------- | ----------------------------- |
| .vscode | Information for VSCode to give you good autocorrect, goto definition, etc. | No |
| .wpilib | Config file for our version of WPILib. | No |
| anything with gradle in the name | The build system. | No |
| WPILib-License.md | Legal shenanagins. | No |
| README.md | Information about the project. | Not unless you find a typo / talk to me first |
| vendordeps | Config files for the project's dependencies. | Not unless your role involves requires you to add a new library |
| src/main/deploy | Non-code files that we want on the robot. | Didn't need it last year! |
| src/main/java/src/robot | The robot code. | Yes |


## contributing
With over 10 people attending our last programming meeting, this repo has the potential to get very messy, very quickly. With that in mind, please keep these in mind:

### master - develop workflow
This repo has two branches (we very very briefly touched on branches during our Git lesson, they are essentially like parallel timelines of commit history), master and develop. When you are writing code, you should be pushing your commits to the develop branch, or to a new branch you made for developing your feature. Whenever the code on develop is in a reasonable state of workingness, I will merge the develop branch into master. With that in mind, make sure you are only pushing commits to GitHub if they result in code that compiles.

If all of that makes no sense, don't worry. James or I will help you through pushing your first couple commits to GitHub.

### OOP goodness
We are using Java! It's a great language (in some ways)! One of the things it's really great at is allowing you to write code in a very encapsulated style: delegating complex logic to other classes, making very readable code. Make use of these features. If a file you are working on gets too long, find a way to encapsulate some logic behind another class in another file.

### if it ain't broke, don't fix it
We will be using a fairly similar directory layout to last year: a constants folder holds the constants, a commands folder holds commands, a subsystems folder holds subsystems. If you are writing one of these three things **put that file into the corresponding folder**. If you are writing something that isn't one of these three things, either use your best judgement, or ask me! 
