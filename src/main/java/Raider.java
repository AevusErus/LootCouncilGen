/**
 * Created by Chris on 2/28/15.
 */

import java.util.*;

public class Raider {

    /* A list that contains each raider's name and then the number of times they have been on the LootCouncil.
    This way we can just check for the lowest number and pick the new members from those available at random. */
    private static HashMap<String, Integer> raiderList = new HashMap<String, Integer>();
    private static String[] raiders = {"Lifefire"};

    //Generate intial raiderList with given raiders.
    public static void generateInitialRaiderList(){
        System.out.println("Generate raider list");
        for(int i = 0; i < raiders.length; i++){
            raiderList.put(raiders[i],0);
        }
    }

    //Update a raider's participation count
    public static void updateRaider (String raider) {
        raiderList.put(raider, raiderList.get(raider) + 1);
    }

    //Add new raider
    public static void addRaider (String raider) {
        raiderList.put(raider, 0);
    }

    //Remove raider
    public static void removeRaider (String raider) {
        raiderList.remove(raider);
    }

    //Return raiderList
    public HashMap<String, Integer> getRaiderList () {
        return raiderList;
    }

    //Return lowest participation list
    public List<String> getLCCandidates () {
        List<String> candidates = new ArrayList<String>();
        int lowestCount = 0;
        for(int i = 0; i < raiderList.size(); i++) {
            int raiderCount = raiderList.get(raiders[i]);
            if(raiderCount < lowestCount) {
                lowestCount = raiderCount;
            }
        }

        //This is going to require some rethink. What if there is only one person with the lowest count?
        //We need to be smarter with this logic.

        for(int j = 0; j < raiderList.size(); j++) {
            int raiderCount = raiderList.get(raiders[j]);
            if(raiderCount == lowestCount){
                candidates.add(raiders[j]);
            }
        }
        return candidates;
    }

    public static void main(String args[]){
        generateInitialRaiderList();
    }
}
