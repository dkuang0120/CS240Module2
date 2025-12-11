        .text
        .globl main

########################################################
# Cat Duel: Whiskers vs Mittens
# - Whiskers (W) vs Mittens (M)
# - Both start with some health.
# - Each round, damage is random
# - When a cat's health goes negative, the other cat wins.
# - Prints 'W' or 'M' for the winner.
########################################################

# Register usage:
#   $s0 = Whiskers' health
#   $s1 = Mittens' health
#   $s2 = damage RNG seed
#   $t0 = current damage value
#   $t1 = mask 3 (for damage range 0..3)
#   $t2 = zero / temp
#   $t3 = loop counter or temp
#   $t4 = temp
#   $t5 = temp
#   $t6 = temp
#   $t7 = temp
#   $t8 = temp
#   $t9 = temp

main:
        ########################################
        # Initialize health & RNG seed
        ########################################
        caddi $s0, $zero, 10     # Whiskers' health = 10
        caddi $s1, $zero, 10    # Mittens' health  = 10

        caddi $s2, $zero, 5      # RNG seed = 5
        caddi $t1, $zero, 3      # mask = 3 for damage range

battle_loop:
        ########################################
        # WHISKERS attacks MITTENS
        ########################################

        # Update RNG seed: pawrot left by 5 bits
        pawrot $s2, $s2          # s2 = rotate_left(s2,5)

        # damageRaw = seed & 3
        cand   $t0, $s2, $t1     # t0 = s2 & 3  (0..3)

        # If damage == 0, make it 1 (so cat always does something)
        caddi  $t2, $zero, 0
        cbeq   $t0, $t2, fix_w_damage
        cbeq   $zero, $zero, apply_w_damage

fix_w_damage:
        caddi  $t0, $zero, 1     # damage = 1

apply_w_damage:
        # Mittens' health -= damage
        csub   $s1, $s1, $t0

        # If Mittens' health < 0 => Whiskers wins
        bncat  $s1, whiskers_wins

        ########################################
        # 3. MITTENS attacks WHISKERS
        ########################################

        # Update RNG seed again
        pawrot $s2, $s2

        # damageRaw = seed & 3
        cand   $t0, $s2, $t1

        # If damage == 0, make it 1
        caddi  $t2, $zero, 0
        cbeq   $t0, $t2, fix_m_damage
        cbeq   $zero, $zero, apply_m_damage

fix_m_damage:
        caddi  $t0, $zero, 1

apply_m_damage:
        # Whiskers' health -= damage
        csub   $s0, $s0, $t0

        # If Whiskers' health < 0 => Mittens wins
        bncat  $s0, mittens_wins

        ########################################
        # 4. If both still alive, loop again
        ########################################
        cbeq $zero, $zero, battle_loop


########################################
# WIN SCREENS
########################################

whiskers_wins:
        # Print "W\n"
        caddi $t0, $zero, 'W'
        meow  $t0
        caddi $t0, $zero, 10
        meow  $t0
        cbeq  $zero, $zero, done

mittens_wins:
        # Print "M\n"
        caddi $t0, $zero, 'M'
        meow  $t0
        caddi $t0, $zero, 10
        meow  $t0
        cbeq  $zero, $zero, done

done:
        cbeq $zero, $zero, done

        .data
