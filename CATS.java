package mars.mips.instructions.customlangs;
import mars.mips.hardware.*;
import mars.*;
import mars.util.*;
import mars.mips.instructions.*;

public class CATS extends CustomAssembly {

    @Override
    public String getName() {
        return "CATS Assembly";
    }

    @Override
    public String getDescription() {
        return "Become a cat with this Assembly language.";
    }

    @Override
    protected void populate() {
        instructionList.add(
            new BasicInstruction("cadd $t0,$t1,$t2",
                "Cat Add : ($d) = ($s) + ($t)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 100000",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int v = RegisterFile.getValue(rs) + RegisterFile.getValue(rt);
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("csub $t0,$t1,$t2",
                "Cat Subtract : ($d) = ($s) - ($t)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 100010",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int v = RegisterFile.getValue(rs) - RegisterFile.getValue(rt);
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("cand $t0,$t1,$t2",
                "Cat AND : ($d) = ($s) & ($t)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 100100",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int v = RegisterFile.getValue(rs) & RegisterFile.getValue(rt);
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("cor $t0,$t1,$t2",
                "Cat OR : ($d) = ($s) | ($t)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 100101",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int v = RegisterFile.getValue(rs) | RegisterFile.getValue(rt);
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("cxor $t0,$t1,$t2",
                "Cat XOR : ($d) = ($s) ^ ($t)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 100110",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int v = RegisterFile.getValue(rs) ^ RegisterFile.getValue(rt);
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("cless $t0,$t1,$t2",
                "Cat Less-Than : ($d) = 1 if ($s) < ($t) else 0",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 101010",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int vs = RegisterFile.getValue(rs);
                        int vt = RegisterFile.getValue(rt);
                        int v = (vs < vt) ? 1 : 0;
                        RegisterFile.updateRegister(rd, v);
                    }
                }));

        instructionList.add(
            new BasicInstruction("cbeq $t0,$t1,label",
                "Cat Branch if Equal : if ($s) == ($t), jump to label",
                BasicInstructionFormat.I_BRANCH_FORMAT,
                "000100 fffff ttttt ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rs = op[0];
                        int rt = op[1];
                        if (RegisterFile.getValue(rs) == RegisterFile.getValue(rt)) {
                            Globals.instructionSet.processBranch(op[2]);
                        }
                    }
                }));
        // instructions not in MIPS
        instructionList.add(
            new BasicInstruction("purr $t1,$t2,-100",
                "PURR : Saturating add immediate onto ($t1) using ($t2) as base",
                BasicInstructionFormat.I_FORMAT,
                "110000 fffff ttttt ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rt = op[0];
                        int rs = op[1];
                        int imm = op[2] << 16 >> 16;   // sign-extend
                        long tmp = (long)RegisterFile.getValue(rs) + imm;
                        int result;
                        if (tmp > 0x7FFFFFFFL) {
                            result = 0x7FFFFFFF;
                        } else if (tmp < 0x80000000L) {
                            result = 0x80000000;
                        } else {
                            result = (int)tmp;
                        }
                        RegisterFile.updateRegister(rt, result);
                    }
                }));
        instructionList.add(
            new BasicInstruction("chase $t0,$t1,$t2",
                "CHASE : ($d) becomes the maximum of ($s) and ($t)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 110000",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int vs = RegisterFile.getValue(rs);
                        int vt = RegisterFile.getValue(rt);
                        int v = (vs >= vt) ? vs : vt;
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("bncat $0,label",
                "BNCAT : Branch if cat's mood in ($s) is negative",
                BasicInstructionFormat.I_BRANCH_FORMAT,
                "000001 fffff 00000 ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rs = op[0];
                        int v = RegisterFile.getValue(rs);
                        if (v < 0) {
                            Globals.instructionSet.processBranch(op[1]);
                        }
                    }
                }));
        instructionList.add(
            new BasicInstruction("cmove $t0,$t1,$t2",
                "CMOVE : If ($t) != 0, move ($s) into ($d)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss ttttt 00000 110011",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int rt = op[2];
                        int vt = RegisterFile.getValue(rt);
                        if (vt != 0) {
                            int vs = RegisterFile.getValue(rs);
                            RegisterFile.updateRegister(rd, vs);
                        }
                    }
                }));
        instructionList.add(
            new BasicInstruction("lick $t0,$t1",
                "LICK : Clear lowest bit of ($s) and store in ($d)",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss 00000 fffff 00000 110100",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int v = RegisterFile.getValue(rs) & 0xFFFFFFFE;
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("tail $t0,$t1",
                "TAIL : Reverse all bits of ($s) into ($d)",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss 00000 fffff 00000 110010",
                new SimulationCode() {
                    private int reverseBits(int x) {
                        int r = 0;
                        for (int i = 0; i < 32; i++) {
                            r <<= 1;
                            r |= (x & 1);
                            x >>>= 1;
                        }
                        return r;
                    }
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int v = reverseBits(RegisterFile.getValue(rs));
                        RegisterFile.updateRegister(rd, v);
                    }
                }));
        instructionList.add(
            new BasicInstruction("pawrot $t0,$t1",
                "PAWROT : Rotate ($s) left by shamt bits into ($d)",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss 00000 fffff 00101 110001",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rd = op[0];
                        int rs = op[1];
                        int shamt = op[2];  // treat as shift amount
                        int v = RegisterFile.getValue(rs);
                        int res = (v << shamt) | (v >>> (32 - shamt));
                        RegisterFile.updateRegister(rd, res);
                    }
                }));
        instructionList.add(
            new BasicInstruction("scratch $t0,0($t1)",
                "SCRATCH : Store 0xFFFFFFFF at memory[($s)+offset]",
                BasicInstructionFormat.I_FORMAT,
                "110010 fffff ttttt ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rs = op[1];
                        int rt = op[0]; // not actually used, but encoded
                        int imm = op[2] << 16 >> 16;
                        int addr = RegisterFile.getValue(rs) + imm;
                        try {
                            Globals.memory.setWord(addr, 0xFFFFFFFF);
                        } catch (AddressErrorException e) {
                            throw new ProcessingException(stmt, e);
                        }
                    }
                }));
        instructionList.add(
            new BasicInstruction("meow $t1",
                "MEOW : Output low byte of ($s) to console",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff 00000 00000 00000 110101",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rs = op[0];
                        int v = RegisterFile.getValue(rs) & 0xFF;
                        char ch = (char)v;
                        SystemIO.printString(Character.toString(ch));
                    }
                }));
        instructionList.add(
            new BasicInstruction("hiss 1",
                "HISS : Raise a software trap with immediate code",
                BasicInstructionFormat.I_FORMAT,
                "110001 00000 00000 ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int imm = op[0] << 16 >> 16;
                        throw new ProcessingException(stmt,
                            "HISS trap raised with code " + imm);
                    }
                }));
        // CLW rt, offset(rs)  -- load word
        instructionList.add(
            new BasicInstruction("clw $t0,0($t1)",
                "Cat Load Word : ($t1) = MEM[($t2)+offset]",
                BasicInstructionFormat.I_FORMAT,
                "100000 fffff ttttt ssssssssssssssss",
             new SimulationCode() {
                public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rt = op[0];
                        int rs = op[1];
                        int imm = op[2] << 16 >> 16;      // sign-extend
                        int addr = RegisterFile.getValue(rs) + imm;
                        try {
                            int val = Globals.memory.getWord(addr);
                            RegisterFile.updateRegister(rt, val);
                        } catch (AddressErrorException e) {
                            throw new ProcessingException(stmt, e);
                        }
                    }
                }));

        instructionList.add(
            new BasicInstruction("csw $t0,0($t1)",
                "Cat Store Word : MEM[($t2)+offset] = ($t1)",
                BasicInstructionFormat.I_FORMAT,
                "101000 fffff ttttt ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rt = op[0];
                        int rs = op[1];
                        int imm = op[2] << 16 >> 16;      // sign-extend
                        int addr = RegisterFile.getValue(rs) + imm;
                        int val = RegisterFile.getValue(rt);
                        try {
                            Globals.memory.setWord(addr, val);
                        } catch (AddressErrorException e) {
                            throw new ProcessingException(stmt, e);
                        }
                    }
                }));

        instructionList.add(
            new BasicInstruction("caddi $t1,$t2,-100",
                "Cat Add Immediate : ($t1) = ($t2) + imm",
                BasicInstructionFormat.I_FORMAT,
                "001000 fffff ttttt ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement stmt) throws ProcessingException {
                        int[] op = stmt.getOperands();
                        int rt = op[0];
                        int rs = op[1];
                        int imm = op[2] << 16 >> 16;      // sign-extend
                        int vs = RegisterFile.getValue(rs);
                        RegisterFile.updateRegister(rt, vs + imm);
                    }
                }));

        instructionList.add(
                new BasicInstruction("cjump label",
                        "Cat Jump : unconditionally branch to label",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        // opcode |  rs   |  rt   |     imm (label)     |
                        //        |000111 00000 00000 ssssssssssssssss  |
                        "000111 00000 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement stmt) throws ProcessingException {
                                int[] op = stmt.getOperands();
                                // op[0] is the branch offset resolved from 'label'
                                Globals.instructionSet.processBranch(op[0]);
                            }
                        }));
    }
}
