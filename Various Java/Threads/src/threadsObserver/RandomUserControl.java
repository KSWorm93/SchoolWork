package threadsObserver;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import randomperson.RandomUser;
import randomperson.RandomUserGenerator;

public class RandomUserControl extends Observable implements Runnable {


 
    public void fetchRandomUser(){
    
    RandomUser user = null;
        
        try {
            user = RandomUserGenerator.getRandomUser();
        } catch (InterruptedException ex) {
            Logger.getLogger(RandomUserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        setChanged();
        notifyObservers(user);  
          
    }

    @Override
    public void run() {
        fetchRandomUser();
    }
      
}


/*

When and why we will use Threads in our programs?
    Når vi vil udnytte den fulde kraft en cpu har, med alle dens kerner istedet for kun at køre med 1 kerne.
    Threads gør at flere opgaver kan arbejde samtidig.

Explain about the Race Condition Problem and ways to solve it in Java
    Race Condition er når 2eller flere tråde bliver sat igang næsten samtidig og de begge skal ændre på samme variabel.
    Måder man kan løse problemet på: 
    -Bruge synchronized, som gør at kun én tråd kan have fat i variablen af gangen
    -Lock() som låser et stykke kode, når en tråd kommer ind, låser den koden så kun DEN kan have fat i det stykke kode
     indtil den er færdig eller Unlocker for koden.

Explain how Threads can help us in making responsive User Interfaces
    Tråde hjælper os til hvis vi fx har en GUI som laver noget, og vi ikke har flere tråde kørende, så når den får besked på at
    hente noget data, vil den fryse indtil den er færdig, med tråde kan vi gøre så GUI'en kan køre videre imens tråden henter data  

*/
