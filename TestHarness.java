//*********************************************************//
//            Distributed Systems  Assignment 3            //
//    Filename     -   TestHarness.java                    //
//*********************************************************//
import java.util.Arrays;
import java.util.List;

// **************************************************************************************
// Class Name: TestHarness
// M1: Response - Immediate; Chance - 0
// M2: Response - Very late (but sometimes immediate); Missing Chance - 0.7
// M3: Response - Late (but sometimes quick); Missing Chance - 0.9
// M4-M9: Response - Medium; Missing Chance - 0.5
// **************************************************************************************
public class TestHarness {
    // Initiating tests
    public static void main(String[] args){
        try{
            System.out.println("****************************** TEST 1 BEGIN ******************************\n");
            System.out.println("M1 and M2 will propose immediately at the same time\n");
            test1();
            System.out.println("***************************** TEST 1 COMPLETE ****************************\n");

            System.out.println("****************************** TEST 2 BEGIN ******************************\n");
            System.out.println("M1-M9 have immediate responses to voting queries;");
            System.out.println("None of them will lose proposals\n");
            test2();
            System.out.println("***************************** TEST 2 COMPLETE ****************************\n");

            System.out.println("****************************** TEST 3 BEGIN ******************************\n");
            System.out.println("M1 & M2 propose at the same time, The others will participate the election;");
            System.out.println("None of them will lose proposals\n");
            test3();
            System.out.println("***************************** TEST 3 COMPLETE ****************************\n");
                
            System.out.println("****************************** TEST 4 BEGIN ******************************\n");
            System.out.println("M1 have immediate response;");
            System.out.println("M2 or M3 propose and then go offline, M3 has higher chance to lose proposals;");
            System.out.println("M4-M9 fairly busy and their response times will vary;\n");
            test4();
            System.out.println("***************************** TEST 4 COMPLETE ****************************\n");
            
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("[TestHarness] Fail to Apply Fundamental Tests\n");
        }
    }
    
    // When two councillors send voting proposals at the same time
    public static void test1() throws Exception {
        List<String> membersList = Arrays.asList("M1", "M2");
        List<String> proposalList = Arrays.asList("M1", "M2");
        
        List<Double> chance = Arrays.asList(0.0, 0.0);
        List<Integer> responseTime = Arrays.asList(2, 2);
        
        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }

    // M1-M9 have immediate responses to voting queries
    // Zero chance of losing emails
    public static void test2() throws Exception {
        List<String> membersList = Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9");
        List<String> proposalList = Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9");

        List<Double> chance = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        List<Integer> responseTime = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }

    // When two councillors send voting proposals at the same time
    // The others also participate in the election
    public static void test3() throws Exception {
        List<String> membersList = Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9");
        List<String> proposalList = Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9");

        List<Double> chance = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        List<Integer> responseTime = Arrays.asList(2, 2, 3, 4, 5, 6, 7, 8, 9);

        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }

    // M1 – M9 have responses to voting queries suggested by the profiles above:
    // M2 or M3 propose and then go offline
    // Set the chance of losing emails of M2 & M3 > 0.5
    // M4-M9 fairly busy and as such their response times  will vary (Chance 0.5)
    public static void test4() throws Exception {
        List<String> membersList = Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9");
        List<String> proposalList = Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9");

        List<Double> chance = Arrays.asList(0.0, 0.7, 0.9, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
        List<Integer> responseTime = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }
}
