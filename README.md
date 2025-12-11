# Overview
Final Project for CS240 (Computer Organization)

CATS is a custom Assembly language instruction set designed for MARS simulator.
It contains 11 renamed instructions and 10 custom instructions not already in MARS. 

# How to Run CATS
  1. In order to use this custom cat-themed Assembly language, make sure to have MARS-LE installed.
  2. Place the CATS.jar and CATS.java file into the customlang folder.
  3. Should you want to make changes to CATS.java, you can recompile CATS.java by typing 'javac -jar BuildCustomLang.jar CATS' into a terminal window.
     (Be sure that the directory is set to the MARS-LE folder.)
  4. In the MARS application, switch the language to CATS Assembly by going to Tools -> Language Switcher
     
# How to Use Each Program
  Cat Mood Program
    \n 1. Change $t0, $t1, and $t2 to alter treat amount and energy level
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
