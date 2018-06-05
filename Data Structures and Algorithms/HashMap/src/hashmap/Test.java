/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashmap;

/**
 *
 * @author Tobias
 */
public class Test {

    public static void main(String[] args) {
        Map<String, Integer> myLinkHashMap = new HashMapLink<>();
        Map<String, Integer> myHashMap = new HashMap<>();

        testMap(myHashMap);
        testMap(myLinkHashMap);
    }

    private static void testMap(Map<String, Integer> myMap) {
        for (int i = 0; i < 1000; i += 2) {
            String s = "#" + i;
            myMap.put(s, i);
        }

        System.out.println("\nTesting: " + myMap.getMapName());
        System.out.println("First test: ");
        System.out.println("get0: " + myMap.get("#0"));
        System.out.println("remove0: " + myMap.remove("#0"));
        System.out.println("get0-again: " + myMap.get("#0"));
        System.out.println("Second test");
        System.out.println("get1: " + myMap.get("#1"));
        System.out.println("remove1: " + myMap.remove("#1"));
        System.out.println("get1-again: " + myMap.get("#1"));
        System.out.println("Third test");
        System.out.println("get2: " + myMap.get("#2"));
        System.out.println("remove2: " + myMap.remove("#2"));
        System.out.println("get2-again: " + myMap.get("#2"));
    }
}
