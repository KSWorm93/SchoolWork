/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dayinmonth;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author kasper
 */
@RunWith(Parameterized.class)
public class TestValidDaysInMonth {

    DaysInMonth dim = new DaysInMonth();
    int month, year, expected;

    public TestValidDaysInMonth(int month, int year, int expected) {
        this.month = month;
        this.year = year;
        this.expected = expected;
    }

    /**
     * Inputs to the tests, to check if the inputs are valid
     *
     * @Parameters(name) gives individual names for each test case to better
     * tell them apart
     * @return list of inputs
     */
    @Parameters(name = "Month: {0} - Year: {1} - Expected: {2}")
    public static Collection<Object[]> validInputs() {
        return Arrays.asList(new Object[][]{
            {1, 0, 31},
            {2, 400, 29},
            {3, 100, 31},
            {4, 1000, 30},
            {5, 1900, 31},
            {6, 1640, 31},
            {7, 1884, 31},
            {8, 1564, 31},
            {9, 1310, 30},
            {10, 2103, 31},
            {11, 2407, 30},
            {12, 3333, 31},
            {2, 3, 28},
            {2, 4, 29},
            {2, 100, 28},
            {2, 800, 29}
        });
    }

    @Test
    public void testGetDaysInMonthTest() throws Exception {

        assertEquals(expected, dim.getDaysInMonth(month, year));

    }

}
