Feature: Handle notes

  Users want to save notes easily
  Notes should be saved automatically

  Scenario: Thomas must remember to buy cat food
    Given we are out of food for the cats
    When Thomas writes a note about buying cat food
    Then should his todo list contain buy cat food
