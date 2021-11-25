# The Project is For DISPLAY ONLY
# The Project is For DISPLAY ONLY
# The Project is For DISPLAY ONLY
# The Project is For DISPLAY ONLY
# The Project is For DISPLAY ONLY
# DO NOT COPY OR USE ANY CODES FROM THIS FOR YOUR PERSONAL USE (#assignments, proejcts)



#[Project Description]

## Assignment 3 - Paxos

### Marks

For undergraduates, the marks awarded for this assignment are worth 30% of the total mark for DS. For postgraduate students, the marks awarded for this assignment are worth 20% of the total mark for DS.

**PLEASE NOTE:** If your code does not compile and run **the awarded mark is an automatic zero**. Our expectation is that you are able to code and submit a tested and working copy of your code. There will not be any partial marks and you are expected to debug and fix errors and test that your code works on the Linux image at University (CATS machines or ssh remotely to `uss.cs.adelaide.edu.au`). 

### Assignment Description

#### Objective

To gain an understanding of consensus and voting protocols in the presence of failures of one or more of the participants.

## Welcome to the Suburbs Council Election!

This year, Suburbs Council is holding elections for council president. Any member of its nine person council is eligible to become council president.

**Member M1** – M1 has wanted to be council president for a very long time. M1 is very chatty over social media and responds to emails/texts/calls almost instantly. It is as if M1 has an in-brain connection with their mobile phone!

**Member M2** – M2 has also wanted to be council president for a very long time, except their very long time is longer than everybody else's. M2 lives in a remote part of the Suburbs and thus their internet connection is really poor, almost non-existent. Responses to emails come in very late, and sometimes only to one of the emails in the email thread, so it is unclear whether M2 has read/understood them all. However, M2 sometimes likes to work at Café @ Bottom of the Hill. When that happens, their responses are instant and M2 replies to all emails.

**Member M3** – M3 has also wanted to be council president. M3 is not as responsive as M1, nor as late as M2, however sometimes emails completely do not get to M3. The other councilors suspect that it’s because sometimes M3 goes on retreats in the woods at the top of the Suburbs, completely disconnected from the world.

**Members M4-M9** have no particular ambitions about council presidency and no particular preferences or animosities, so they will try to vote fairly. Their jobs keep them fairly busy and as such their response times will vary.

How does voting happen: On the day of the vote, one of the councilors will send out an email/message to all councilors with a proposal for a president. A majority (half+1) is required for somebody to be elected president.

### YOUR TASK:

Write a program that implements a Paxos voting protocol for Suburbs Council President that is fault tolerant and resilient to various failure types, some of which are shown in the above. Communication happens strictly via sockets. You are responsible for the message design.

#### Assessment

Your assignment will be marked out of 100 points, as following:

- 10 points -  Paxos implementation works when two councillors send voting proposals at the same time
- 30 points – Paxos implementation works in the case where all M1-M9 have immediate responses to voting queries
- 30 points – Paxos implementation works when M1 – M9 have responses to voting queries suggested by the profiles above, including when M2 or M3 propose and then go offline
- 20 points – Testing harness for the above scenarios + evidence that they work (in the form of printouts)
- 10 points for the quality of your code:

**Code Quality Checklist** 

**Do!**

```
o write comments above the header of each of your methods, describing
o what the method is doing, what are its inputs and expected outputs
o describe in the comments any special cases
o create modular code, following cohesion and coupling principles
```

**Don’t!

**

```
o use magic numbers
o use comments as structural elements (see video)
o mis-spell your comments
o use incomprehensible variable names
o have long methods (not more than 80 lines)
o allow TODO blocks 
```

 

## Bonus

- 10 points – Paxos implementation works with a number ‘n’ of councilors with four profiles of response times: immediate;  medium; late; never
- 50 points – (you can use these points in this assignment, or in any other subsequent assignment) – Fast Byzantine Paxos implementation that works when councilors lie, collude, or intentionally do not participate in some voting queries but participate in others.
