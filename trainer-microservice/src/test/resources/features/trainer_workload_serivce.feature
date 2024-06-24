Feature: Trainer Workload Service

Scenario: Add workload for a trainer
  Given the database contains a trainer with username "john_doe" and the following yearly summaries:
    | year | month   | workingMinutes |
    | 2024 | 2024-01 | 120            |
  When the workload is added with the following details:
    | username | trainingDate | durationMinutes | actionType |
    | john_doe | 2024-01-15   | 60              | ADD        |
  Then the trainer's monthly summary for JANUARY 2024 should be updated to 180 minutes

  Scenario: Delete workload for a trainer
    Given the database contains a trainer with username "john_doe" and the following yearly summaries:
      | year | month   | workingMinutes |
      | 2024 | 2024-01 | 60            |
    When the workload is deleted with the following details:
      | username | trainingDate | durationMinutes | actionType |
      | john_doe | 2024-01-15   | 60              | DELETE     |
    Then the trainer's monthly summary for JANUARY 2024 should be updated to 0 minutes
    