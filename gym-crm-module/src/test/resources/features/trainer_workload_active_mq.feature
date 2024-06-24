Feature: Verify TrainerWorkloadRequest Message Handling via ActiveMQ

  Scenario: Send TrainerWorkloadRequest to ActiveMQ Queue
    Given the gym-rest-api is running
    When a TrainerWorkloadRequest is sent to the ActiveMQ queue
    Then the TrainerWorkloadRequest message should be present in the queue
