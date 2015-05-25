/**
 * Created by Chris on 2/28/15.
 */

import java.io.*;
import java.util.*;

public class Raider {

    /* A list that contains each raider's name and then the number of times they have been on the LootCouncil.
    This way we can just check for the lowest number and pick the new members from those available at random. */
    private static HashMap<String, Integer> raiderList = new HashMap<String, Integer>();
    private static List<String> raiders = new ArrayList<String>();
    private static String path = "/Users/Chris/Documents/batcave/LootCouncilGen/raidList.txt";
    private static List<String> exemptionList = new ArrayList<String>();
    private static HashMap<String, String> relationMap = new HashMap<String, String>();


//    GETTERS

    //    Return raiderList
    public static HashMap<String, Integer> getRaiderList() {
        return raiderList;
    }

    //Return relationMap
    public static HashMap<String, String> getRelationMap() {
        return relationMap;
    }

    //Return candidateList with lowest participation members.
    public static List<String> getLCCandidates() {
        List<String> candidates = new ArrayList<String>();
        int lowestCount = 10000;
        int lowestCount2 = 10000;
        for (int i = 0; i < raiderList.size(); i++) {
            int raiderCount = raiderList.get(raiders.get(i));
            if (raiderCount < lowestCount) {
                lowestCount = raiderCount;
            }
        }

        //This is going to require some rethink. What if there is only one person with the lowest count?
        //We need to be smarter with this logic.

        for (int j = 0; j < raiderList.size(); j++) {
            int raiderCount = raiderList.get(raiders.get(j));
            if (raiderCount == lowestCount) {
                candidates.add(raiders.get(j));
            }
        }

//        If there are fewer than 5 people to be chosen initially, then repeat the process.
        if (candidates.size() < 5) {
//            Flag the initial candidates so that they are chosen ahead of the others.
            for (int w = 0; w < candidates.size(); w++) {
                candidates.set(w, candidates.get(w).concat("-MUSTSELECT"));
            }
            //Go through and find the second lowest count to choose appropriate candidates.
            for (int i = 0; i < raiderList.size(); i++) {
                int raiderCount = raiderList.get(raiders.get(i));
                if (raiderCount < lowestCount2) {
                    boolean isCandidate = false;
                    for (int j = 0; j < candidates.size(); j++) {
                        //Must take into account the "-MUSTSELECT" tag on names when looking for the next lowest count.
                        if (raiders.get(i).concat("-MUSTSELECT").equals(candidates.get(j))) {
                            isCandidate = true;
                            break;
                        }
                    }
                    if (!isCandidate) {
                        lowestCount2 = raiderCount;
                    }
                }
            }
        }

        //Add raiders who's count equals the second lowest count to the candidate list.
        for (int j = 0; j < raiderList.size(); j++) {
            int raiderCount = raiderList.get(raiders.get(j));
            if (raiderCount == lowestCount2) {
                candidates.add(raiders.get(j));
            }
        }

        return candidates;
    }

//    SETTERS

    //    Update a raider's participation count
    public static void updateRaider(String raider) {
        raiderList.put(raider, raiderList.get(raider) + 1);
    }

    //Update participation count for all the raiders on the council
    public static void updateRaiders(List<String> council){
        for (int i = 0; i < council.size(); i++){
            updateRaider(council.get(i));
        }
    }

    //    Add new raider
    public static void addRaider(String raider) {
        raiderList.put(raider, 0);
    }

    //    Remove raider
    public static void removeRaider(String raider) {
        raiderList.remove(raider);
    }

//    GENERATE FUNCTIONS

    //    Generate intial raiderList with given raiders from raidList.txt
    public static void generateInitialRaiderList() throws IOException {
        System.out.println("Generate raider list");
        File raidListFile = new File(path);
        if (raidListFile.isFile()) {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("*")) {
                    exemptionList.add(line);
                    continue;
                } else if (line.contains(":")) {
                    String username1 = line.substring(0, line.indexOf('|'));
                    String username2 = line.substring(line.indexOf(':')+1, line.length());
                    relationMap.put(username1, username2);
                    int count = Integer.parseInt(line.substring(line.indexOf('|') + 1, line.indexOf(':')-1));
                    raiders.add(username1);
                    raiderList.put(username1, count);
                } else {
                    String username = line.substring(0, line.indexOf('|'));
                    int count = Integer.parseInt(line.substring(line.indexOf('|') + 1));
                    raiders.add(username);
                    raiderList.put(username, count);
                }
            }
        }

        System.out.println(relationMap);
    }
//    Save Function

    //    Function that saves the raidList to the raidList.txt
    public static void saveRaidList() {
        try {
            FileWriter fw = new FileWriter(path, false);
            PrintWriter pw = new PrintWriter(fw);
            Set<String> names = raiderList.keySet();
            Iterator it = names.iterator();

            //Save names of non-exempt raiders
            for (int i = 0; i < names.size(); i++) {
                String name = it.next().toString();
                String fileLine = name + "|" + raiderList.get(name);
                pw.printf("%s" + "%n", fileLine);
            }
            //Save names of exempt raiders
            for (int i = 0; i < exemptionList.size(); i++){
                pw.printf("%s" + "%n", exemptionList.get(i));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) throws IOException {
        try {
            generateInitialRaiderList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
