//*********************************************************//
//            Distributed Systems  Assignment 3            //
//    Filename     -   TestHarnessN.java                   //
//*********************************************************//
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// **************************************************************************************
// Class Name: TestHarnessN
// M1: Response - Immediate; Chance - 0
// M2: Response - Very late (but sometimes immediate); Missing Chance - 0.7
// M3: Response - Late (but sometimes quick); Missing Chance - 0.9
// M4-M9: Response - Medium; Missing Chance - 0.5
// **************************************************************************************
public class TestHarnessN {

    public static void main(String[] args){
        try{
            int n =Integer.parseInt(args[0]);
            if(n < 2) {
                System.out.println("[ALERT] The number of councilors must be greater 1!");
            } else {

                System.out.println("****************************** TEST 1 BEGIN ******************************\n");
                System.out.println("M1-M9 have immediate responses to voting queries, none of them will lose proposals\n");
                test1(n);
                System.out.println("***************************** TEST 1 COMPLETE ****************************\n");

                System.out.println("****************************** TEST 2 BEGIN ******************************\n");
                System.out.println("M1 & M2 propose at the same time, The others will participate the election;");
                System.out.println("None of them will lose proposals\n");
                test2(n);
                System.out.println("***************************** TEST 2 COMPLETE ****************************\n");

                System.out.println("****************************** TEST 3 BEGIN ******************************\n");
                System.out.println("M1 have immediate response;");
                System.out.println("M2 or M3 propose and then go offline, M3 has higher chance to lose proposals;");
                System.out.println("M4-M9 fairly busy and their response times will vary;\n");
                test3(n);
                System.out.println("***************************** TEST 3 COMPLETE ****************************\n");

                System.out.println("****************************** TEST 4 BEGIN ******************************\n");
                System.out.println("M1 have immediate response;");
                System.out.println("M2 has high latency and may lose proposals (even go offline)");
                System.out.println("M3 has higher change to lose proposals, high possibility to lose proposals");
                System.out.println("M4 has latency but lower than M2, may never make/respond to proposals;");
                System.out.println("M5-M9 fairly busy and their response times will vary;\n");
                test4(n);
                System.out.println("***************************** TEST 4 COMPLETE ****************************\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[TestHarnessN] Fail to Apply N Tests\n");
        }
    }

    // M1-M9 have immediate responses to voting queries
    // Zero chance of losing emails
    public static void test1(int n) throws Exception{
        List<String> membersList = new ArrayList<>();
        List<String> proposalList = new ArrayList<>();
        List<Double> chance = new ArrayList<>();
        List<Integer> responseTime = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            membersList.add("M"+i);
            proposalList.add("M"+i);
            chance.add(0.0);
            responseTime.add(i);
        }

        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }

    // When two councillors send voting proposals at the same time
    // The others also participate in the election
    public static void test2(int n) throws Exception{

        List<String> membersList = new ArrayList<>();
        List<String> proposalList = new ArrayList<>();
        List<Double> chance = new ArrayList<>();
        List<Integer> responseTime = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            membersList.add("M"+i);
            proposalList.add("M"+i);
            chance.add(0.0);
            if( i == 1) {
                responseTime.add(2);
            }else {
                responseTime.add(i);
            }


        }
        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }

    // M1 – M9 have responses to voting queries suggested by the profiles above:
    // M2 or M3 propose and then go offline
    // Set the chance of losing emails of M2 & M3 > 0.5
    // M4-M9 fairly busy and as such their response times  will vary (Chance 0.5)
    public static void test3(int n) throws Exception{

        List<String> membersList = new ArrayList<>();
        List<String> proposalList = new ArrayList<>();
        List<Double> chance = new ArrayList<>();
        List<Integer> responseTime = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            membersList.add("M"+i);
            proposalList.add("M"+i);
            if(i == 1) {
                chance.add(0.0);
            }else if(i == 2) {
                chance.add(0.7);

            }else if(i == 3) {
                chance.add(0.9);
                
            }else {
                chance.add(0.5);
            }

            responseTime.add(i);

        }

        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }
    
    // Four profiles of response times: immediate; medium; late; never
    // Arrays.asList(0.0, 0.7, 0.9, 1.0, 0.5, 0.5, 0.5, 0.5, 0.5)
    // Arrays.asList(0, 3, 4, 6, 5, 6, 7, 8, 9)
    // M1 Immediate
    // M2 Late
    // M3 Medium
    // M4 Never
    // Others Fair
    public static void test4(int n) throws Exception{

        List<String> membersList = new ArrayList<>();
        List<String> proposalList = new ArrayList<>();
        List<Double> chance = new ArrayList<>();
        List<Integer> responseTime = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            membersList.add("M"+i);
            proposalList.add("M"+i);
            if(i == 1) {
                chance.add(0.0);
                
            }else if(i == 2) {
                chance.add(0.7);
                
            }else if(i == 3) {
                chance.add(0.9);
                
            }else if(i == 4) {
                chance.add(1.0);

            } else {
                chance.add(0.5);
            }
            
            if(i == 1) {
                responseTime.add(0);
                
            }else if(i == 2) {
                responseTime.add(3);
                
            }else if(i == 3) {
                responseTime.add(4);
                
            }else if(i == 4) {
                responseTime.add(6);
                
            }else {
                responseTime.add(i);
            }

        }
        
        CouncilElection election = new CouncilElection(membersList, proposalList, chance, responseTime);
        election.start(new Proposer(0, "M1"));
    }
}
