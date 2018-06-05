/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangle;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author kasper
 */
public class Triangle {

    private Scanner scan;
    private int temp;
    private int lineA;
    private int lineB;
    private int lineC;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Run scenario
        System.out.println("Start of scenario:");
        new Triangle().runScenario();

    }

    /**
     * Runs the scenario
     */
    public void runScenario() {

        scan = new Scanner(System.in, "UTF-8");

        //Scan 3 line values from user
        System.out.println("Please insert value for line A");
        lineA = validNumber();

        System.out.println("Please insert value for line B");
        lineB = validNumber();

        System.out.println("Please insert value for line C");
        lineC = validNumber();

        //Check triangle type
        checkTriangle();
    }

    /**
     * Check if it is a number that has been scanned. If it is not a number,
     * program will exit.
     *
     * @return scanned integer
     */
    private int validNumber() {
        try {
            temp = scan.nextInt();
        } catch (InputMismatchException inputException) {
            System.out.println("Input was not a number!"
                    + "\nProgram will exit. Please restart");
            System.exit(0);
        }
        return temp;
    }

    /**
     * Check which kind of triangle it is
     */
    private void checkTriangle() {

        //Check valid triangle
        if (validTriangle()) {

            //Tell type of triangle
            if (lineA == lineB && lineA == lineC) {
                isEquilateral();
            } else if (lineA != lineB && lineB != lineC && lineA != lineC) {
                isScalene();
            } else {
                isIsosceles();
            }
        }
    }

    /**
     * Method to check if inputted numbers give a valid triangle.
     *
     * @return true if triangle is valid
     */
    private boolean validTriangle() {
        if (!(lineA + lineB > lineC
                && lineA + lineC > lineB
                && lineB + lineC > lineA)) {
            System.out.println("This is not a valid triangle.");
            return false;
        }
        return true;
    }

    /**
     * Prints out a string if called
     */
    public void isScalene() {
        System.out.println("I am a scalene triangle");
    }

    /**
     * Prints out a string if called
     */
    public void isIsosceles() {
        System.out.println("I am a isosceles triangle");
    }

    /**
     * Prints out a string if called
     */
    public void isEquilateral() {
        System.out.println("I am a equilateral triangle");
    }
}
