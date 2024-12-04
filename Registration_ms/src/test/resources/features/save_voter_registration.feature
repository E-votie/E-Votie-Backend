Feature: Save Voter Registration
  As an applicant
  I want to submit my voter registration details
  So that my application can be processed

  Scenario: Successfully save a new voter registration application
    Given a voter registration application with Application ID "12345" exists in the system
    And the voter registration details include:
      | Field                     | Value                  |
      | Name                      | John Doe              |
      | DOB                       | 1990-01-01           |
      | CivilStatus               | Single               |
      | RelationshipToTheChiefOccupant | Brother         |
      | ChiefOccupantNIC          | 987654321V           |
      | ElectionDistrict          | Colombo              |
      | PollingDivision           | Colombo Central      |
      | GramaNiladhariDivision    | Division A           |
      | Address                   | 123 Main Street      |
      | AdminDistrict             | Colombo District     |
      | HouseNo                   | 123                  |
    When the voter submits the application with email "john.doe@example.com"
    Then the application is saved successfully
    And an email is sent to "john.doe@example.com" with the subject "Voter Application Reserved"
    And the application status is updated to "Pending"
    And the temporary contact information for NIC "123456789V" is removed
