        .text
        .globl main

main:
        # Setup "treats" and "energy"

        caddi $t0, $zero, 15      # $t0 = treatsToday
        caddi $t1, $zero, 3      # $t1 = treatsBonus
        caddi $t2, $zero, 14      # $t2 = energy

        # Use CHASE to pick the better treat count
        # bestTreats = CHASE(treatsToday, treatsBonus)
        
        chase $t3, $t0, $t1      # $t3 = max($t0,$t1)
        
        # Mood score = bestTreats*2 - energy
        # temp = bestTreats + bestTreats
        cadd  $t4, $t3, $t3      # $t4 = 2*bestTreats

        # mood = temp - energy
        csub  $t5, $t4, $t2      # $t5 = moodScore

        # Clamp mood using PURR (saturating add)
        # moodClamped = purr(mood, +100000 or something big)
        purr  $t6, $t5, 10        # $t6 = saturating(mood + 0)

        # Check if mood < 0 using CLESS
        #    happy if mood >= 0, grumpy if mood < 0
        caddi $t7, $zero, 0
        cless $t8, $t6, $t7

        # If $t8 == 1 -> GRUMPY
        caddi $t9, $zero, 1
        cbeq  $t8, $t9, grumpy

happy:
        # HAPPY CAT FACE ":3",
        # Print ":3"
        caddi $t0, $zero, ':'
        meow  $t0
        caddi $t0, $zero, '3'
        meow  $t0

        caddi $t0, $zero, 10 # newline
        meow  $t0

        cbeq  $zero, $zero, done

grumpy:
        # GRUMPY CAT FACE "> :("
        caddi $t0, $zero, '>'
        meow  $t0

        caddi $t0, $zero, ' '
        meow  $t0

        caddi $t0, $zero, ':'
        meow  $t0

        caddi $t0, $zero, '('
        meow  $t0

        caddi $t0, $zero, 10
        meow  $t0

done:
        cbeq $zero, $zero, done

        .data
