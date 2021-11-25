//*********************************************************//
//            Distributed Systems  Assignment 3            //
//    Filename     -   Leader.java                         //
//*********************************************************//
import java.io.Serializable;

// **************************************************************************************
// Leader = Learner + Acceptor + Proposer
// **************************************************************************************

// **************************************************************************************
// Class Name: Learner
// After sending a proposal,the leaner will receive a response
// **************************************************************************************
class Learner implements Serializable {
    public Proposer proposal;
    public Proposer finalProposal;

    public boolean ifReceived;
    public boolean accept;

    public Learner(boolean ifReceived, Proposer proposal) {
        this.ifReceived = ifReceived;
        this.proposal = proposal;
    }

    public Learner(boolean ifReceived, boolean accept, Proposer proposal) {
        this.ifReceived = ifReceived;
        this.accept = accept;
        this.proposal = proposal;
    }

    public Proposer getProposal() {
        return proposal;
    }

    public boolean ifAccepted() {
        return accept;
    }

    public void getAccept(boolean accept) {
        this.accept = accept;
    }

    public Proposer getFinalProposal() {
        return finalProposal;
    }

    public void setFinalProposal(Proposer finalProposal) {
        this.finalProposal = finalProposal;
    }
}

// **************************************************************************************
// Class Name: Acceptor
// Acceptor will accept the proposal
// **************************************************************************************
class Acceptor implements Serializable {

    private final String member;    // Member accepted the proposal
    private final double chance;    // Chance of the missing email

    public Acceptor(String member, double chance) {
        this.member = member;
        this.chance = chance;
    }

    public String getMember() {
        return member;
    }

    public double getChanceValue() {
        return chance;
    }
}


// **************************************************************************************
// Class Name: Acceptor
// Make proposals and record time
// **************************************************************************************
class Proposer implements Serializable {
    public String member;
    public int proposalTime;

    public Proposer() {
        // Initialise
        this.member = null;
        this.proposalTime = -1;
    }

    public Proposer(int proposalTime, String member) {
        this.proposalTime = proposalTime;
        this.member = member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getMember() {
        return member;
    }

    public int getProposalTime() {
        return proposalTime;
    }

    public void setProposalTime(int proposalTime) {
        this.proposalTime = proposalTime;
    }

    public String toString() {
        // "%s": member
        // "%d": proposal time
        String output = String.format("%s is eligible to become council president! Total Time: %d", this.member, this.proposalTime);
        return output;
    }
}

// **************************************************************************************
// Class Name: Obj
// Transfer object values
// **************************************************************************************
class Obj implements Serializable {
    private Proposer proposal;

    private Acceptor acceptor;

    private Proposer finalProposal;

    private Double chance;

    public Double getChance() {
        return chance;
    }

    public void setChance(Double chance) {
        this.chance = chance;
    }

    public Proposer getFinalProposal() {
        return finalProposal;
    }

    public void setFinalProposal(Proposer finalProposal) {
        this.finalProposal = finalProposal;
    }

    public Proposer getProposal() {
        return proposal;
    }

    public void setProposal(Proposer proposal) {
        this.proposal = proposal;
    }

    public Acceptor getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Acceptor acceptor) {
        this.acceptor = acceptor;
    }
}
