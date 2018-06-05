/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deductible;

/**
 *
 * @author kasper
 */
public class Deductible {

    String visit;
    boolean decuctible;

    /**
     * Method to check if its possible to be reimbursed
     *
     * @param visit - String containing visit type, eg. "Doctor"
     * @param decuctible - boolean if you are deductible
     * @return int for value you will be reimbursed: 0, 50 or 80
     */
    public int reimburse(String visit, boolean decuctible) {

        if (!decuctible) {
            return 0;
        }
        if (visit.equals("Doctor")) {
            return 50;
        }
        if (visit.equals("Hospital")) {
            return 80;
        }
        return 0;
    }

}
