Feature: Receive message from ActiveMQ

  Scenario: Test if multiple messages are received
    Given an ActiveMQ broker is running
    When multiple messages are sent to the queue
    Then the message listener should receive all messages
