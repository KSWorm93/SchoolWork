/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dayinmonth;

/**
 *
 * @author kasper
 */
public class DaysInMonth {

    int month;
    int year;
    int days;

    /**
     * Method to get days in month
     *
     * @param month month going from 1-12
     * @param year year going from 0-Integer.MAX
     * @return days in the month
     * @throws Exception IllegalArgumentException, if you input month below 1 or
     * above 12 or year below 0
     */
    public int getDaysInMonth(int month, int year) throws Exception {
        days = 0;

        if (checkValidInput(month, year)) {
            days = checkMonth(month, year);
        }
        if (days < 1) {
            throw new IllegalArgumentException("Invalid inputs");
        }
        return days;
    }

    /**
     * Method to check how many days is in month
     *
     * @param month the month you wanna check
     * @param year year you wanna check - in case of leap year
     * @return int containing the days in the month
     */
    public int checkMonth(int month, int year) {
        int daysInMonth;
        switch (month) {
            case 1:
                daysInMonth = 31;
                break;
            case 2:
                daysInMonth = checkLeapYear(year);
                break;
            case 3:
                daysInMonth = 31;
                break;
            case 4:
                daysInMonth = 30;
                break;
            case 5:
                daysInMonth = 31;
                break;
            case 6:
                daysInMonth = 31;
                break;
            case 7:
                daysInMonth = 31;
                break;
            case 8:
                daysInMonth = 31;
                break;
            case 9:
                daysInMonth = 30;
                break;
            case 10:
                daysInMonth = 31;
                break;
            case 11:
                daysInMonth = 30;
                break;
            case 12:
                daysInMonth = 31;
                break;
            default:
                daysInMonth = 0;
        }
        return daysInMonth;
    }

    /**
     * Method to check for valid inputs
     *
     * @param month int for checking month
     * @param year int for checking year
     * @return true if its valid inputs
     */
    public boolean checkValidInput(int month, int year) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (year < 0) {
            return false;
        }
        return true;
    }

    /**
     * Method to check if its a leap year giving this rule year must be equally
     * divided by 4. exception to rule: if year is divided by 100, it needs to
     * be divided by 400 as well
     *
     * @param year
     * @return
     */
    public int checkLeapYear(int year) {
        int leapYearDays;
        if (year % 4 != 0) {
            leapYearDays = 28;
        } else if (year % 400 == 0) {
            leapYearDays = 29;
        } else if (year % 100 == 0) {
            leapYearDays = 28;
        } else {
            leapYearDays = 29;
        }
        return leapYearDays;
    }
}
