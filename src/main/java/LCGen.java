import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Chris on 2/28/15.
 */
public class LCGen {

    public static List<String> chooseCouncil(List<String> candidateList) {
        List<String> council = new ArrayList<String>();

        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt((candidateList.size() - 1) + 1);
            council.add(candidateList.get(randomIndex));
            Raider.updateRaider(candidateList.get(randomIndex));
            candidateList.remove(randomIndex);
        }
        return council;
    }

    public static void saveCouncilList(List<String> councilList) {
        String path = "/Users/Chris/Documents/batcave/LootCouncilGen/councilList.txt";

        try {
            FileWriter fw = new FileWriter(path,true
            );
            PrintWriter pw = new PrintWriter(fw);

            Date today = new Date();
            pw.printf( "%s" + "%n" ,today);

            for(int i = 0; i < councilList.size(); i++) {
                String name = councilList.get(i);
                String fileLine = name;
                pw.printf( "%s" + "%n" ,fileLine);
            }

            String endSegment = "--------------------";
            pw.printf( "%s" + "%n" ,endSegment);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

        List<String> candidates, council;
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
        candidates = Raider.getLCCandidates();
        council = chooseCouncil(candidates);
        saveCouncilList(council);
        Raider.saveRaidList();
        System.out.println(Raider.getRaiderList());
    }
}