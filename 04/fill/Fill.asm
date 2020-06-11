// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
(BLACK)
@KBD
D=M //stores the value of keyboard in D
@WHITE
D;JEQ //if D=0 go to white loop
@24575 //screen's last pixel
D=M
@WHITE
D;JLT //if last pixel is -1 goes to white loop
@count
D=M
@SCREEN
D=A+D //sets D to adrress(screen)+Count
A=D //select address
M=-1 //change value
@count
M=M+1 // increment count
@BLACK
0;JMP

(WHITE)
@KBD
D=M
@BLACK
D;JGT
@count
D=M
@SCREEN
D=A+D
A=D
M=0
@count //decrements count and whitens all pixels
M=M-1
@WHITE
0;JMP


