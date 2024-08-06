package com.evotie.registration_ms.voter_registration.helper;

import java.util.HashMap;
import java.util.Map;

public class NICProcessor {

    private static final int[] DAYS_IN_MONTH = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static Map<String, String> getBirthdayAndGender(String nic) {
        Map<String, String> result = new HashMap<>();

        int year, dayOfYear;
        String gender;

        if (nic.length() == 10) {
            year = 1900 + Integer.parseInt(nic.substring(0, 2));
            dayOfYear = Integer.parseInt(nic.substring(2, 5));
        } else if (nic.length() == 12) {
            year = Integer.parseInt(nic.substring(0, 4));
            dayOfYear = Integer.parseInt(nic.substring(4, 7));
        } else {
            throw new IllegalArgumentException("Invalid NIC number length");
        }

        if (dayOfYear > 500) {
            dayOfYear -= 500;
            gender = "Female";
        } else {
            gender = "Male";
        }

        int month = 0;
        int dayOfMonth = dayOfYear;

        for (int i = 0; i < DAYS_IN_MONTH.length; i++) {
            if (dayOfMonth <= DAYS_IN_MONTH[i]) {
                month = i;
                break;
            }
            dayOfMonth -= DAYS_IN_MONTH[i];
        }

        String birthday = dayOfMonth + " " + MONTHS[month] + " " + year;

        result.put("birthday", birthday);
        result.put("gender", gender);

        return result;
    }
}

