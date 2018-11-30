Feature: Handle notes

  Users want to save notes easily
  Notes should be saved automatically

  Scenario: Malin want to save a note about an upcoming concert
    Given she must remember to book Nalen
    When she writes the note
    Then should she be able to see the note
    
