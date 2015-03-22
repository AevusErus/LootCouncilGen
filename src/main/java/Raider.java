/**
 * Created by Chris on 2/28/15.
 */

import java.io.*;
import java.util.*;

public class Raider {

    /* A list that contains each raider's name and then the number of times they have been on the LootCouncil.
    This way we can just check for the lowest number and pick the new members from those available at random. */
    private static HashMap<String, Integer> raiderList = new HashMap<String, Integer>();
//    private static String[] raiders = {"Lifefire"};
    private static List<String> raiders = new ArrayList<String>();
    private static String path = "/Users/Chris/Documents/batcave/LootCouncilGen/raidList.txt";

    //Generate intial raiderList with given raiders.
    public static void generateInitialRaiderList() throws IOException {
        System.out.println("Generate raider list");
        File raidListFile = new File(path);
        if(raidListFile.isFile()) {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                String username = line.substring(0,line.indexOf('|'));
                int count = Integer.parseInt(line.substring(line.indexOf('|')+1));
                raiders.add(username);
                raiderList.put(username,count);
            }
        }
    }

    public static void saveRaidList() {
        try {
            FileWriter fw = new FileWriter(path,false);
            PrintWriter pw = new PrintWriter(fw);
            Set<String> names = raiderList.keySet();
            Iterator it = names.iterator();

            for(int i = 0; i < names.size(); i++) {
                String name = it.next().toString();
                String fileLine = name+"|"+raiderList.get(name);
                pw.printf( "%s" + "%n" ,fileLine);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    public static HashMap<String, Integer> getRaiderList () {
        return raiderList;
    }

    //Return lowest participation list
    public static List<String> getLCCandidates () {
        List<String> candidates = new ArrayList<String>();
        int lowestCount = 10000;
        int lowestCount2 = 10000;
        for(int i = 0; i < raiderList.size(); i++) {
            int raiderCount = raiderList.get(raiders.get(i));
            if(raiderCount < lowestCount) {
                lowestCount = raiderCount;
            }
        }

        //This is going to require some rethink. What if there is only one person with the lowest count?
        //We need to be smarter with this logic.

        for(int j = 0; j < raiderList.size(); j++) {
            int raiderCount = raiderList.get(raiders.get(j));
            if(raiderCount == lowestCount){
                candidates.add(raiders.get(j).concat("-MUSTSELECT"));
            }
        }
        
        if (candidates.size() < 5) {
            for(int i = 0; i < raiderList.size(); i++) {
                int raiderCount = raiderList.get(raiders.get(i));
                if(raiderCount < lowestCount2) {
                    boolean isCandidate = false;
                    for(int j = 0; j < candidates.size(); j++){
                        if (raiders.get(i).concat("-MUSTSELECT").equals(candidates.get(j))){
                            isCandidate = true;
                            break;
                        }
                    }
                    if(!isCandidate) {
                        lowestCount2 = raiderCount;
                    }
                }
            }
        }

        for(int j = 0; j < raiderList.size(); j++) {
            int raiderCount = raiderList.get(raiders.get(j));
            if(raiderCount == lowestCount2){
                candidates.add(raiders.get(j));
            }
        }

        return candidates;
    }

    public static void main(String args[]) throws IOException {
        try {
            generateInitialRaiderList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
