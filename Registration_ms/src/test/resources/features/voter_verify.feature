Feature: Voter Verification
  As a voter
  I want to verify my details using OTP
  So that I can complete the voter registration process

  Scenario: Voter provides valid OTP and hash
    Given a voter with NIC "123456789V", email "test@example.com", and contact "0771234567" has been sent an OTP "1234"
    When the voter submits the OTP "1234" and hash "validHash"
    Then the voter is registered successfully
    And the voter details include "123456789V", "test@example.com", and "0771234567"

  Scenario: Voter provides invalid OTP
    Given a voter with NIC "987654321V", email "invalid@example.com", and contact "0779876543" has been sent an OTP "5678"
    When the voter submits the OTP "9999" and hash "validHash"
    Then the response is "OTP is incorrect or expired."

  Scenario: Voter provides invalid hash
    Given a voter with NIC "123123123V", email "example@example.com", and contact "0711231231" has been sent an OTP "3456"
    When the voter submits the OTP "3456" and hash "invalidHash"
    Then the response is "OTP is incorrect or expired."
