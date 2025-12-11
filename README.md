# Overview
Final Project for CS240 (Computer Organization)

CATS is a custom Assembly language instruction set designed for MARS simulator.
It contains 11 renamed instructions and 10 custom instructions not already in MARS. 

# Implemented Instructions:
| Instruction               | Meaning                                    |
| ------------------------- | ------------------------------------------ |
| **meow rs**               | Output low byte of register as a character |
| **purr rt,rs,imm**        | Saturating add immediate                   |
| **chase rd,rs,rt**        | Select max(rs, rt)                         |
| **bncat rs,label**        | Branch if rs is negative                   |
| **cmove rd,rs,rt**        | Conditional move if rt ≠ 0                 |
| **lick rd,rs**            | Clear lowest bit (force even)              |
| **tail rd,rs**            | Reverse all 32 bits                        |
| **pawrot rd,rs**          | Rotate left by 5 bits                      |
| **scratch rt,offset(rs)** | Store 0xFFFFFFFF at memory location        |
| **hiss imm**              | Raise a software trap (debug)              |

# How to Run CATS
  1. In order to use this custom cat-themed Assembly language, make sure to have MARS-LE installed.
  2. Place the CATS.jar and CATS.java file into the customlang folder.
  3. Should you want to make changes to CATS.java, you can recompile CATS.java by typing 'javac -jar BuildCustomLang.jar CATS' into a terminal window.
     (Be sure that the directory is set to the MARS-LE folder.)
  4. In the MARS application, switch the language to CATS Assembly by going to Tools -> Language Switcher
     
# How to Use Each Program
  Cat Mood Program
  1. Change $t0, $t1, and $t2 to alter treat amount and energy level
  2. After assembling, the program will run a calculation to determine a mood score
  3. If the mood score is positive, the cat is happy. If the mood score is negative, the cat is grumpy.
  4. It will print a face in the console depending on whether the cat is happy or grumpy.

  Treat Counter Program
  1. Change $t0, $t1, and $t2 to alter treat amount in each room
  2. If total treat amount is >= 5, cat is happy and prints '9'. If it is <5 , cat is sad and prints '0'

  Cat Duel Program
  1. Change $s0 and $s1 to set Whisker and Mitten's health
  2. After assembling, program does a random amount of damage to each cat.
  3. If one cat's health falls to 0 or below, that cat loses.
  4. Prints out the winner's initial ('W' or 'M')
