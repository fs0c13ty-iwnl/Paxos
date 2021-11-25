//*********************************************************//
//            Distributed Systems  Assignment 3            //
//*********************************************************//
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// **************************************************************************************
// Class Name: SocketService
// Initiate a server for voting
// **************************************************************************************
public class SocketService {

    private static final int port = 8080;
    static ServerSocket serverSocket;
    public static void main(String[] args) throws Exception{
        serverSocket = new ServerSocket(port);

        System.out.println("[The Server Has Initiated!]");
        System.out.println("[Waiting for Votes ... ...]\n");
        // Start listening, waiting for connection
        do {
            // Server try to receive other socket requests
            Socket socket = serverSocket.accept();
            // Create a new Thread to handle the socket
            new Thread(new Task(socket)).start();
        } while (true);

    }

    // To deal with socket requests
    static class Task implements Runnable {

        private final Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                handleSocket();
            } catch (Exception e) {
                //e.printStackTrace();
            }

        }

        // Communicate with server
        private void handleSocket() throws Exception {

            synchronized (this) {
                // Read the server information
                if(socket == null) return;
                InputStream is = socket.getInputStream();
                // Convert to input stream
                ObjectInputStream ois = new ObjectInputStream(is);
                Obj obj = (Obj) ois.readObject();
                // Return the to the client
                OutputStream os = socket.getOutputStream();
                Double chance = obj.getChance();
                Proposer finalProposal  = obj.getFinalProposal();
                Proposer proposal = obj.getProposal();
                Acceptor acceptor = obj.getAcceptor();

                System.out.println("[Current Time] "+ proposal.getProposalTime());
                if (chance < 0 || chance > 1){
                    try {
                        throw new IllegalArgumentException("Illegal Chance Value! Should be within Range of 0-1!");
                    } catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        oos.writeObject(null);
                        oos.flush();
                        socket.close();
                        return;
                    }
                }

                // If the proposal is null
                if (proposal == null){
                    try {
                        throw new IllegalArgumentException("Proposal is NULL!");
                    } catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        oos.writeObject(null);
                        oos.flush();
                        socket.close();
                        return;
                    }
                }

                // If there are lost proposals
                if (Math.random() < chance) {
                    System.out.println(acceptor.getMember() + " Proposal Lost!");
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(null);
                    oos.flush();
                    socket.close();
                    return;
                }

                // If received a proposal
                if (proposal.getProposalTime() > finalProposal.getProposalTime()) {
                    System.out.println(acceptor.getMember() + " Proposal Received");
                    Learner learner = new Learner(true, finalProposal);

                    // If the chance of accepting the proposal is greater than 0.5, accept;
                    // Otherwise, reject
                    if (Math.random() - 0.5 > 0) {
                        learner.getAccept(true);
                        System.out.println(acceptor.getMember() + " Proposal Accepted");
                    } else {
                        learner.getAccept(false);
                        System.out.println(acceptor.getMember() + " Proposal Rejected");
                    }
                    finalProposal = proposal;
                    learner.setFinalProposal(finalProposal);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(learner);
                    oos.flush();
                } else {
                    System.out.println(acceptor.getMember() + " Proposal Rejected");
                    Learner learner = new Learner(false, false, null);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(learner);
                    oos.flush();
                }
                socket.close();
            }
        }
    }
}
