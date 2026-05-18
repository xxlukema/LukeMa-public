# SAGA Design Pattern for Microservice Transactions

[Saga Design Pattern Spring Boot Microservices Interview Q/A - by Code Decode]<https://www.youtube.com/watch?v=Fe_qPM81hJ8>

## Diagram

                      |<------------------------(T4)<------------------------|
                      |                                                      |
                      v                                                      |
    ----(T1)---> Order Service ----(T2)---> Payment Service ---(T3)---> Delivery Service
    
    Success Flow:   create order event ----> validate payment event -----> delivery event
                      |    ^                    |                                  |   |    
                      |    |<-------------------|-- order successful event <-------|   |     
                      |                         |                                      |    
                      v                         v                                      v   
    Failure Flow:  cancel order event <--- revert payment event <--- delivery failed event

## Success Flow

## Failure Flow

## Handle Failure

1. The SAGA pattern provides **transaction management** by using a sequence of local transactions of microservices.
   **Every microservice** has its own database and it is able to **manage local transaction in atomic way** with
   strict consistency.

2. SAGA pattern **groups these local transactions and sequentially** invoke one by one. Each local transaction
   updates the database and **publishes an event to trigger the next local transaction**.

3. If one of the step fails, then saga pattern triggers to **revert transactions** that are a set of
   **compensating transactions that revert the changes on previous microservices** and restore data consistency.

## Implementation

1. choreograpghy
2. orchestration

## Choroegraphy

Use **Centralized Message Broker (kafka)**

Good for simple workflows

Disadvantages:

1. Difficult to track which saga participants listen to which commands
2. Risk of cyclic dependency between saga participants
3. Integration testing is difficult because all services must be running to simulate a transaction

## Orchestration

1. **Coordinate saga where a centralized controller** tells the saga participants what local transactions to execute
2. saga **orchestrator handles all the transactions** and **tells the participants which openeration to perform based on events**
3. orchestaror: (1) execute saga requests, 
                (2) store and interprets the states of each task,
                (3) handle failure recovery with compensating transactions

Advantages:

1. Good for complex workflows involving many participants or new participants added over time
2. Orchestrator logs contain all transaction details
3. No cyclic dependencies. Orchestrator unilaterally depends on the saga participants
4. Participants do not need to know commands for other participants
5. Clear separation of concerns. Simplifies business logic

Disadvantages:

1. Complex coordination logic for orchestror
2. **Additional point of failure** becuase the orchestrator manages the complete workflow
