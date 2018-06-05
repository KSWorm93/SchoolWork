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
public class TestInvalidDaysInMonth {

    DaysInMonth dim = new DaysInMonth();
    int month, year, expected;

    public TestInvalidDaysInMonth(int month, int year, int expected) {
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
            {0, 0, 0},
            {13, 0, 0},
            {1, -1, 0},
            {12, -1, 0}

        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGetDaysInMonthTest() throws Exception {

        assertEquals(expected, dim.getDaysInMonth(month, year));

    }

}
