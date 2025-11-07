PRN | NAME
124B1F150 | Atharva Deshmukh
124B1F144 | Prathmesh Naik
124B1F154 | Maahit Bhawsar



#  CodeVerse – The Java Coding Adventure Game


---

##  Overview

**CodeVerse** is a **console-based Java learning game** designed to make programming concepts fun and interactive.  
The player solves syntax and logic challenges to earn points, progress through levels, and improve Java knowledge.

All questions are dynamically loaded from an external **text file (`questions.txt`)** using **file handling**, so new challenges can be added without changing the code.

This project demonstrates **complete Object-Oriented Programming principles** and multiple **Core Java concepts**, making it ideal for academic submission.

---

##  Aim

> To design and develop a console-based interactive learning application in Core Java that demonstrates Object-Oriented Programming, File Handling, and Exception Handling while testing users’ Java knowledge in an engaging format.

---

##  Features

-  Menu-driven console interface (Start, Scoreboard, Exit)
-  Dynamic question loading from external file (`questions.txt`)
-  Two challenge types – Syntax and Logic
-  Points, Levels, and Live Feedback system
-  Player score history saved to `player_history.txt`
-  Fully modular OOP-based structure for easy expansion

---

##  Folder Structure
CodeVerse/
│
├── Main.java
├── GameEngine.java
├── Player.java
├── Challenge.java
├── SyntaxChallenge.java
├── LogicChallenge.java
├── Question.java
├── QuestionBank.java
├── ScoreBoard.java
├── Utils.java
├── questions.txt
└── player_history.txt


---

##  How It Works

1. The game starts with a **menu-driven console interface**.
2. Questions are loaded dynamically from `questions.txt` using **BufferedReader**.
3. Each question belongs to a category:
   - **SYNTAX** → basic Java syntax and keywords  
   - **LOGIC** → reasoning or output-based challenges  
4. Player scores points for each correct answer.
5. Player history is saved automatically in `player_history.txt`.

---

##  Core Java Concepts Demonstrated

| Category | Concept | Implementation |
|-----------|----------|----------------|
| **OOP Principles** | **Encapsulation** | Private fields in `Player`, `Question`, `ScoreBoard` with public getters/setters |
| | **Abstraction** | `Challenge` is an abstract class defining challenge structure |
| | **Inheritance** | `SyntaxChallenge` and `LogicChallenge` extend `Challenge` |
| | **Polymorphism** | Overridden `present()` methods perform different actions |
| | **Composition** | `GameEngine` owns instances of `Player`, `QuestionBank`, `ScoreBoard` |
| **Core Java Basics** | Classes, Objects, Constructors | Used in all `.java` files |
| | Access Modifiers | `private`, `public` used for data hiding |
| | `enum` | Used for `Question.Type` (SYNTAX, LOGIC) |
| | Static Methods | Utility functions in `Utils.java` |
| **Collections Framework** | `ArrayList`, `List`, `Collections.shuffle()` | Used in `QuestionBank` and `ScoreBoard` |
| **File Handling** | `FileReader`, `BufferedReader`, `FileWriter` | Reading from `questions.txt`, writing to `player_history.txt` |
| **Exception Handling** | `try-catch`, `IOException`, `NumberFormatException` | Safe file and user input handling |
| **Strings & Wrappers** | `trim()`, `split()`, `toLowerCase()`, `Integer.parseInt()` | Used for input normalization |
| **Control Flow** | `if-else`, `switch`, `for`, `do-while` | Used for logic, menu, and gameplay |
| **User Input & Output** | `Scanner` + `System.out.println()` | Interactive console interface |
| **Utility Classes** | `Utils.java` | Static helper methods for input validation |

 **Total Concepts Covered:** 20+ Core Java fundamentals

---

##  Scoring System

| Challenge Type | Points (Correct) | Points (Wrong) |
|----------------|------------------|----------------|
| Syntax Challenge | +10 | -5 |
| Logic Challenge | +15 | -7 |

Levels:
- **Level 1:** 0–49 pts  
- **Level 2:** 50–119 pts  
- **Level 3:** 120+ pts  

---

##  Example `questions.txt`

CodeVerse Question File
Format: TYPE|PROMPT|ANSWER

SYNTAX|Which keyword is used to inherit a class in Java?|extends
SYNTAX|Which method is the entry point in Java?|main
SYNTAX|Which keyword creates an object?|new
LOGIC|If x=5, what is x++ + ++x?|12
LOGIC|Output of System.out.println(2 + 3 + "4");|54
LOGIC|Which loop executes at least once?|do while|do-while


---

##  Sample Console Output

===== Welcome to CodeVerse - The Java Adventure! =====
Enter your name: Atharva
Hello, Atharva!

--- Question 1 ---
[SYNTAX] Which keyword is used to inherit a class in Java?
Your answer: extends
 Correct! +10 points.

--- Question 2 ---
[LOGIC] Output of System.out.println(2 + 3 + "4");
Your answer: 54
 Correct! +15 points.

Session Complete!
Your Score: 25 | Level: 1


---

##  Learning Outcome

By developing CodeVerse, the learner will:
- Apply **OOP principles** to real-world problem solving  
- Understand **File Handling (I/O)** and **Exception Management**  
- Use **Collections** and **Enums** effectively  
- Build modular, extensible console applications  
- Gain hands-on practice with **Core Java programming**

---

##  Tools Used

- **Language:** Java SE 17 (works with Java 8+)  
- **IDE:** IntelliJ IDEA / VS Code / Eclipse  
- **Type:** Console Application  
- **No external libraries or frameworks used**

---

##  Future Enhancements

- Add **Swing GUI** for a graphical version  
- Add **timer-based challenges** using threads  
- Add **category selection** (Syntax / Logic / Mixed)  
- Add **serialization** to store player profiles  
- Add **difficulty scaling** and progress analytics  

---

##  Conclusion

> **CodeVerse** successfully demonstrates complete understanding of Core Java through Object-Oriented Programming, File Handling, Exception Handling, and modular design.  
> It transforms learning Java into an engaging game-based experience while showcasing technical depth and creativity.

---

