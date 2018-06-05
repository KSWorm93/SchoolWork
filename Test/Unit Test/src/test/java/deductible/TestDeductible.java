/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deductible;

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
public class TestDeductible {

    Deductible ded = new Deductible();
    boolean decuctible;
    String visit;
    int expected;

    public TestDeductible(String visit, boolean decuctible, int expected) {
        this.visit = visit;
        this.decuctible = decuctible;
        this.expected = expected;
    }

    /**
     * Inputs to the tests, to check if the inputs are valid
     *
     * @Parameters(name) gives individual names for each test case to better
     * tell them apart
     * @return list of inputs
     */
    @Parameters(name = "Visit: {0} - Deductible: {1} - Expected: {2}")
    public static Collection<Object[]> validInputs() {
        return Arrays.asList(new Object[][]{
            {"Doctor", true, 50},
            {"Doctor", false, 0},
            {"Hospital", true, 80},
            {"Hospital", false, 0},
            {"Private Clinic", true, 0},
            {"Private Clinic", false, 0}
        });
    }

    @Test
    public void testValidReimburse() {
        assertEquals(expected, ded.reimburse(visit, decuctible));
    }

}
