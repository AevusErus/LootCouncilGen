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
        List<String> removeList = new ArrayList<String>();
        int councilSize = 5;

        Random rand = new Random();

        System.out.println(candidateList);

        if(candidateList.get(0).contains("MUSTSELECT")) {
            for (int i = 0; i < candidateList.size(); i++) {
                if (councilSize > 0) {
                    boolean isFlagged = candidateList.get(i).contains("MUSTSELECT");
                    if (isFlagged) {
                        String name = candidateList.get(i).substring(0, candidateList.get(i).indexOf("-"));
                        council.add(name);
                        Raider.updateRaider(name);
                        removeList.add(candidateList.get(i));
                        councilSize--;
                    }
                }
            }
            for (String toRemove : removeList) {
                candidateList.remove(toRemove);
            }
        }

        for (int i = 0; i < councilSize; i++) {
            int randomIndex = rand.nextInt((candidateList.size() - 1) + 1);
            council.add(candidateList.get(randomIndex));
            Raider.updateRaider(candidateList.get(randomIndex));
            candidateList.remove(randomIndex);
        }
        return council;
    }

    public static void saveCouncilList(List<String> councilList, List<String> candidateList) {
        String path = "/Users/Chris/Documents/batcave/LootCouncilGen/councilList.txt";

        try {
            FileWriter fw = new FileWriter(path,true
            );
            PrintWriter pw = new PrintWriter(fw);

            Date today = new Date();
            pw.printf( "%s" + "%n" ,today);

            for(int i = 0; i < councilList.size(); i++) {
                String fileLine = councilList.get(i);
                pw.printf( "%s" + "%n" ,fileLine);
            }

            pw.printf( "%n" + "%s" + "%n" ,"Candidate List:");

            for(int i = 0; i < candidateList.size(); i++) {
                String fileLine2 = candidateList.get(i);
                pw.printf( "%s" + ", " ,fileLine2);
            }

            String endSegment = "--------------------";
            pw.printf( "%n" + "%s" + "%n" ,endSegment);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

        List<String> candidates, council;
        System.out.println("Generating LootCouncil list. Please stand by.");

        try {
            Raider.generateInitialRaiderList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        candidates = Raider.getLCCandidates();
        System.out.println(candidates.size());
        council = chooseCouncil(candidates);
        saveCouncilList(council, candidates);
        Raider.saveRaidList();
        System.out.println(Raider.getRaiderList());
    }
}
