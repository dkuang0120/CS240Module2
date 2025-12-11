        .text
        .globl main

main:
        ################################################
        # TREATS IN THREE ROOMS
        # room1 = 2, room2 = 3, room3 = 4
        ################################################

        caddi $t0, $zero, 1      # room1 treats
        caddi $t1, $zero, 1      # room2 treats
        caddi $t2, $zero, 1      # room3 treats

        ################################################
        # total = room1 + room2 + room3
        ################################################
        cadd  $t3, $t0, $t1      # t3 = room1 + room2
        cadd  $t3, $t3, $t2      # t3 = t3 + room3  (2+3+4=9)

        ################################################
        # If total >= 5, cat is VERY HAPPY ? print '9'
        # else cat is okay ? print '0'
        ################################################

        caddi $t4, $zero, 5      # threshold = 5

        # cless rd, rs, rt  => rd = 1 if rs < rt else 0
        cless $t5, $t3, $t4      # t5 = 1 if total < 5 else 0

        caddi $t6, $zero, 1
        cbeq  $t5, $t6, low_treats   # if t5==1, branch (total < 5)

high_treats:
        # total >= 5, print '9'
        caddi $t0, $zero, '9'
        meow  $t0
        caddi $t0, $zero, 10      # newline
        meow  $t0
        cbeq  $zero, $zero, done

low_treats:
        # total < 5, print '0'
        caddi $t0, $zero, '0'
        meow  $t0
        caddi $t0, $zero, 10      # newline
        meow  $t0

done:
        cbeq $zero, $zero, done   # loop forever

        .data
