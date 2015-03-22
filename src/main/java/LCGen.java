import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Chris on 2/28/15.
 */
public class LCGen {

    public static void main(String args[]){
        System.out.println("Generating LootCouncil list. Please stand by.");

        //Instead of initilaizing the raider and officer lists inside the code,
        //why not have it read from a list that can be updated by the code.

        //initialize raider list if needed.
        //initialize officer list if needed.
        //get raider candidates
        //get officer candidates
        //pick raider participants, and their backups. Update their raider info.
        //pick officer participants, and their backups. Update their officer info.
        //print LC list with date.

        try {
            Raider.generateInitialRaiderList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Raider.removeRaider("Lifefire");
        Raider.saveRaidList();
//        HashMap<String, Integer> theList = Raider.getRaiderList();
        System.out.println(Raider.getRaiderList());
    }
}
