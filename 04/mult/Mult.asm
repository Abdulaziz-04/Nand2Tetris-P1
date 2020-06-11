// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.
//Initialize R2
@R2
M=0

//Initialize values
@R0
D=M //fetches value from R0
@firstnum
M=D //firstnum=R0

@R1
D=M //fetches the value from R1
@secondnum
M=D //secondnum=R1


@i
M=1 //i=1


@ans
M=0 //ans=0

(LOOP)
//CONDITION CHECK
@i
D=M  //first we will check the condition if(i-n)>=0
@secondnum
D=D-M
@STOP
D;JGT  //checks value of Data register for JGT

//CALCULATE ANSWER
@ans   
D=M
@firstnum
D=D+M
@ans
M=D  //store the answer
//INCREMENT VALUE AND LOOP DIRECTLY
@i
M=M+1
@LOOP
0;JMP
//TIP: swap R0 and R1 to reduce calculation




//FINAL ANSWER 
(STOP)
@ans
D=M
@R2
M=D

//TERMINATING PROCEDURE
(END)
@END
0;JMP

