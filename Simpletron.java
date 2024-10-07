package edu.KurtP.Simpletron;

import java.util.Scanner;

public class Simpletron extends SimpletronOperationCodes {

    private int[] memory = new int[1000];
    private int accumulator;
    private int instructionCounter;
    private int instructionRegister;
    private int operationCode;
    private int operand;
    private boolean run = true;
    public static void main(String[] args) {
        Simpletron simpletron = new Simpletron();
        simpletron.run();
    }

    public void run() {
        welcomeMessage();
        execute();
    }
    private void welcomeMessage() {
        System.out.println("***            Welcome to Simpletron!           ***");
        System.out.println("*** Please enter your program, one instruction  ***");
        System.out.println("*** (or data word) at a time. I will display    ***");
        System.out.println("*** the location number and a question mark (?) ***");
        System.out.println("*** You then type the word for that location.   ***");
        System.out.println("*** Type -99999 to stop entering your program.  ***");
    }

    private void execute() {
        Scanner codeInputter = new Scanner(System.in);
        int instructionInput = 0;
        int memoryPointer = 0;

        do {
            //Output the code input prompt
            System.out.printf("%02d ? ", memoryPointer);
            //Take the user input and assign it to the input var.
            instructionInput = codeInputter.nextInt();
            //place the input into the correct memory location
            memory[memoryPointer] = instructionInput;
            //Increment the pointer by one
            memoryPointer++;
        }
        while (instructionInput != -99999);

        System.out.printf("\n%s\n%s\n\n", "***  Program loading complete ***",
                "*** Program excution begins ***");

        while (run) {
            loadCode();
            operations(operationCode, operand);
        }

        System.exit(0);
    }

    private void loadCode() {
        instructionRegister = memory[instructionCounter];

        operationCode = instructionRegister / 100;
        operand = instructionRegister % 100;
        
        instructionRegister = memory[instructionCounter];
        
        operationCode = instructionRegister / 100;
        operand = instructionRegister % 100;
    }

    private void operations(int operationCode, int operand) {
        boolean branching = false;

        switch (operationCode) { //Start switch

            //Operations for reading input from the user
            case READ:
                Scanner read = new Scanner(System.in);
                System.out.print("Enter a number: ");
                int number = read.nextInt();
                memory[operand] = number;
                break;

            //Operations for outputting to the user
            case WRITE:
                System.out.println(memory[operand]);
                break;

            //Load the value found in memory into the accumulator
            case LOAD:
                accumulator = memory[operand];
                break;

            //Put the value in the accumlator in to memroy
            case STORE:
                memory[operand] = accumulator;
                break;

            //Add the value in the accumulator and a value from memroy
            case ADD:
                accumulator += memory[operand];
                break;

            //Subtract the value in the accumulator and a value in memory
            case SUBTRACT:
                accumulator -= memory[operand];
                break;

            //Divide the value in the accumulator by a value in memory
            case DIVIDE:
                //Can't divide by zero.
                if (memory[operand] == 0) {
                    System.out.printf("\n%s\n%s\n", "*** CANNOT DIVIDE BY ZERO ***", "*** EXITING NOW ***");
                    System.exit(-1);
                }
                else {
                    accumulator /= memory[operand];
                    break;
                }

            //Mulitply the value in the accumulator by a value in memory
            case MULITPLY:
                accumulator *= memory[operand];
                break;

            //Branc to a specific memory location
            case BRANCH:
                instructionCounter = operand;
                branching = true;
                break;

            //Branch to a memory location if the accumulator is less than zero
            case BRANCHNEG:
                if (accumulator < 0) {
                    instructionCounter = operand;
                    branching = true;
                }
                break;

            //Branch to a memroy location if the accumulator is zero
            case BRANCHZERO:
                if (accumulator == 0) {
                    instructionCounter = operand;
                    branching = true;
                }
                break;

            //Finsh processing
            case HALT:
                System.out.println("Processing complete...");
                run = false;
                memoryDump();
                break;

                case RESTO:
    if (memory[operand] == 0) {
        System.out.printf("\n%s\n%s\n", "Cociente ");
        System.exit(-1);
    } else {
        accumulator /= memory[operand];
    }
    break;

    case EXPO:
            int base = accumulator;
            int exponent = memory[operand]; 
            accumulator = 1; 
            
            for (int i = 0; i < exponent; i++) {
                accumulator *= base; 
            }
            break;




            

        } //End switch

        if (!branching) {
            instructionCounter++;
        }
    } //End of operations method


    private void memoryDump() {
        int tens, ones;

//        System.out.printf("%s\t%04d\n", "Accumlator", acculator);

        System.out.printf("\t%02d\t%02d\t%02d\t%02d\t%02d\t%02d\t%02d\t%02d\t%02d\t%02d\n", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        for (tens = 0; tens < 100; tens += 10) {
            System.out.printf("%02d\t", tens);
            for (ones = 0; ones < 10; ones++) {
                System.out.printf("%04d\t", memory[tens + ones]);
            }
            System.out.println();
        }
    }
}
