import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Chris on 2/28/15.
 */
public class LCGen {


    //Function that checks to make sure non of the council members are related
    public static List<String> checkRelations(List<String> council, List<String> candidateList){
        HashMap<String, String> relationMap = Raider.getRelationMap();
        Random rand = new Random();

        for(int i = 0; i < council.size(); i++){
            String raider = council.get(i);
            if(relationMap.containsKey(raider)){
                if(council.contains(relationMap.get(raider))){
                    System.out.println("RELATION FOUND!!!");
                    council.remove(relationMap.get(raider));
                    int randomIndex = rand.nextInt((candidateList.size() - 1) + 1);
                    council.add(candidateList.get(randomIndex));
                    candidateList.remove(randomIndex);
                }
            }
        }
        return council;
    }
//    Function that selects the council members from the candidateList.
    public static List<String> chooseCouncil(List<String> candidateList) {
        List<String> council = new ArrayList<String>();
        List<String> removeList = new ArrayList<String>();
        int councilSize = 5;

        Random rand = new Random();

//      Add the names for the council list that MUST be included.
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
//            Remove selected council names from the candidate list so that they don't throw off the random selection.
            for (String toRemove : removeList) {
                candidateList.remove(toRemove);
            }
        }

//        Select random candidates to fill the remaining council seats.
        for (int i = 0; i < councilSize; i++) {
            int randomIndex = rand.nextInt((candidateList.size() - 1) + 1);
            council.add(candidateList.get(randomIndex));
            candidateList.remove(randomIndex);
        }
        //Check the council list for relations that should be removed
        council = checkRelations(council, candidateList);
        //Update the participation count for all the council members
        Raider.updateRaiders(council);

        return council;
    }

//    Function that saves the generated council list to the councilList.txt file.
    public static void saveCouncilList(List<String> councilList, List<String> candidateList) {
        String path = "/Users/Chris/Documents/batcave/LootCouncilGen/councilList.txt";
        Random rand = new Random();
        List<String> candDub = new ArrayList<String>();

        try {
            FileWriter fw = new FileWriter(path,true
            );
            PrintWriter pw = new PrintWriter(fw);

            Date today = new Date();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");

            if(today.getDay() > 2 || today.getDay() < 2) {
                c.set((today.getYear()+1900), today.getMonth(), today.getDate());
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                pw.printf("%s" + "%n", "Loot Council for the Raid Week of " + format1.format(c.getTime()));
            } else {
                pw.printf("%s" + "%n", "Loot Council for the Raid Week of " + format1.format(today));
            }

            for(int i = 0; i < councilList.size(); i++) {
                String fileLine = councilList.get(i);
                pw.printf( "%s" + "%n" ,fileLine);
            }

            pw.printf( "%n" + "%s" + "%n" ,"Candidate List:");

            int initialCandidateSize = candidateList.size();
            for(int i = 0; i < initialCandidateSize; i++) {
                int randomIndex = rand.nextInt((candidateList.size() - 1) + 1);
                candDub.add(candidateList.get(randomIndex));
                candidateList.remove(randomIndex);
            }

            for (String fileLine2 : candDub) {
                pw.printf("%s" + ", ", fileLine2);
            }

            String endSegment = "--------------------";
            pw.printf( "%n" + "%s" + "%n" ,endSegment);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    Main function that runs to generate the council list.
    public static void main(String args[]){

        List<String> candidates, council;
        System.out.println("Generating LootCouncil list. Please stand by.");

        try {
            Raider.generateInitialRaiderList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("***** Candidate List *****");
        candidates = Raider.getLCCandidates();
        System.out.println(candidates.size() + " candidates");
        System.out.println(candidates);
        System.out.println();
        System.out.println("***** Council List *****");
        council = chooseCouncil(candidates);
        saveCouncilList(council, candidates);
        System.out.println(council);
        System.out.println();
        System.out.println("***** Raider List *****");
        Raider.saveRaidList();
        System.out.println(Raider.getRaiderList());
    }
}
