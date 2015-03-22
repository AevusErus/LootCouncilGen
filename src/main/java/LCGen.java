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
        int councilSize = 5;

        Random rand = new Random();

        System.out.println(candidateList);

        for(String candidate : candidateList){
            if(councilSize > 0) {
                boolean isFlagged = candidate.contains("-MUST SELECT");
                if (isFlagged) {
                    String name = candidate.substring(0, candidate.indexOf("|"));
                    council.add(candidateList.get(randomIndex));
                    Raider.updateRaider(candidateList.get(randomIndex));
                    candidateList.remove(randomIndex);
                    councilSize--;
                }
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

        try {
            Raider.generateInitialRaiderList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        candidates = Raider.getLCCandidates();
        System.out.println(candidates.size());
        council = chooseCouncil(candidates);
        saveCouncilList(council);
        Raider.saveRaidList();
        System.out.println(Raider.getRaiderList());
    }
}
