//*********************************************************//
//            Distributed Systems  Assignment 3            //
//    Filename     -   CouncilElection.java                //
//*********************************************************//
import java.io.*;
import java.net.Socket;
import java.util.*;

// **************************************************************************************
// Class Name: CouncilElection
// The class will initialize and start the voting process
// **************************************************************************************
public class CouncilElection {

    private static final int port = 8080;   // Socket port

    public static List<Acceptor> MembersList = new ArrayList<>();   // Members M1 - M9
    public static List<Proposer> lastPropList = new ArrayList<>();  // Last proposal
    public static List<Proposer> ProposalList = new ArrayList<>();  // Proposals by M1 - M9

    public static int win = 0;  // To win the election, a majority (half+1) is required
    public static final Random randomNum = new Random();

    // Initialize a council election
    public CouncilElection(List<String> members, List<String> prop, List<Double> chance, List<Integer> responseTime) {
        // If needed, create three new list every time
//        MembersList = new ArrayList<Acceptor>();
//        lastPropList = new ArrayList<Proposer>();
//        ProposalList = new ArrayList<Proposer>();

        // Fault Tolerance: To ensure the correct size of arguments in members
        if (members.size() != chance.size() || prop.size() != responseTime.size()){
            try {
                throw new IllegalArgumentException("Wrong Arguments!! Please use the same SIZE for each argument");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        for (int i = 0; i < members.size(); i++) {
            lastPropList.add(new Proposer());
            MembersList.add(new Acceptor(members.get(i), chance.get(i)));
            ProposalList.add(new Proposer(responseTime.get(i), prop.get(i)));
        }

        // To win a election: A majority (half+1) is required for somebody to be elected president
        win = Math.floorDiv(members.size() - 1, 2) + 1;
    }

    // Get the next voting sessions
    private static Proposer getSession(List<Proposer> maxNum, int currentTime) {
        int time = currentTime + 1;

        if (maxNum.isEmpty()){
            return ProposalList.get(randomNum.nextInt(ProposalList.size()));
        }

        maxNum.sort(Comparator.comparing(s -> s.proposalTime));
        Proposer maxSession = maxNum.get(maxNum.size() - 1);

        // Build the next session in accordance with time
        if (maxSession.getProposalTime() >= currentTime || maxSession.getMember() == null) {
            Proposer prop = ProposalList.get(randomNum.nextInt(ProposalList.size()));
            return prop;

        } else {
            Proposer prop2 = new Proposer(time, maxSession.getMember());
            return prop2;
        }

    }

    // Initiate the voting process
    public void start(Proposer proposals){
        try{
            int propTime = 0; // Current proposal time
            while (true) {
                System.out.println("[Session]       " + ++propTime);
                System.out.println("[Proposal]      " + proposals);
                List<Proposer> lastProposal = new ArrayList<>();

                int accepts = 0;    // To be compared with the winning conditions
                int i = 0;

                for (Acceptor acceptor : MembersList) {
                    Socket socket = new Socket("127.0.0.1", port);
                    // Make sure no members can vote themselves
                    if (acceptor.getMember().equals(proposals.getMember())) {
                        i++;
                        continue;
                    }

                    OutputStream os =socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    Obj obj = new Obj();
                    obj.setAcceptor(acceptor);
                    obj.setProposal(proposals);
                    obj.setFinalProposal(lastPropList.get(i));
                    obj.setChance(acceptor.getChanceValue());
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(obj);
                    oos.flush();
                    // Receive the returned message
                    // Convert to input stream
                    ObjectInputStream ois = new ObjectInputStream(is);
                    Learner learner = (Learner) ois.readObject();


                    if (learner != null && learner.ifReceived) {
                        lastPropList.get(i).setProposalTime(learner.getFinalProposal().getProposalTime());
                        lastPropList.get(i).setMember(learner.getFinalProposal().getMember());
                        lastProposal.add(learner.getProposal());
                    }


                    if (learner != null && learner.ifAccepted()) {
                        lastPropList.get(i).setProposalTime(learner.getFinalProposal().getProposalTime());
                        lastPropList.get(i).setMember(learner.getFinalProposal().getMember());
                        accepts++;
                    }
                    os.close();
                    socket.close();
                    i++;
                }
                // If not enough votes
                if (lastProposal.size() < win) {
                    System.out.println("[Session" + propTime + "]" + "\tFailed! Not Enough VOTES");
                    proposals = getSession(lastProposal, propTime);

                // If not enough accepts
                } else if (accepts < win) {
                    System.out.println("[Session" + propTime + "]" + "\tFailed! VOTES Are Less Than The Majority");
                    proposals = getSession(lastProposal, propTime);
                } else {
                    break;
                }

            }
            // After completion of a election
            System.out.println("\n[Election Completed After " + propTime + " Sessions]\n");
            System.out.println("[FINAL RESULT]  " + proposals + "\n");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
