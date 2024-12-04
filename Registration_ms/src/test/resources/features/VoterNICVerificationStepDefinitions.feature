Feature: Voter Registration and Contact Verification
  To ensure only unique users can register
  As a system
  I want to verify the NIC and contact information of voters

  Background:
    Given the voter registration endpoint "/voter" is available

  Scenario: Register a new voter with a unique NIC
    Given a voter with NIC "123456789V", Contact "0711234567", and Email "voter@example.com"
    When the voter submits their registration information
    Then the system should save the voter's temporary contact information
    And send an email with a verification link to "voter@example.com"
    And send an SMS with an OTP to "0711234567"

  Scenario: Attempt to register with an already registered NIC
    Given a voter with NIC "123456789V" is already registered
    When the voter submits their registration information
    Then the system should respond with "NIC already registered."

  Scenario: Re-register with an NIC that has temporary contact info
    Given a voter with NIC "987654321V", Contact "0719876543", and Email "tempvoter@example.com"
    And temporary contact information for NIC "987654321V" exists
    When the voter submits their registration information
    Then the system should delete the existing temporary contact information
    And save the new temporary contact information
    And send an email with a verification link to "tempvoter@example.com"
    And send an SMS with an OTP to "0719876543"
