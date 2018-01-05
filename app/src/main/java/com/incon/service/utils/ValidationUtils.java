package com.incon.service.utils;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Za-z])(?=\\S+$).{8,15})";
    private static final String NAME_PATTERN = "^[a-zA-Z]+$";

    public static boolean isValidEmail(String emailAddress) {
        return !TextUtils.isEmpty(emailAddress)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUserName(String userName) {
        return userName.length() >= 6;
    }

    public static boolean isNameValid(String userName) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }


    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.length() >= 10;
    }

    public static boolean isFutureDate(Calendar dobDate) {
        // current date
        Calendar currentDate = Calendar.getInstance();
        if (dobDate.after(currentDate)) {
            return true;
        }
        return false;
    }

    public static boolean isCurrentDate(Calendar curDate) {
        // current date
        Calendar currentDate = Calendar.getInstance();
        if  (curDate.before(currentDate)) {
            return true;
        }
        return false;
    }


    public static int calculateAge(Calendar selectedDate) {
        // current date
        Calendar currentDate = Calendar.getInstance();
        //finding age difference
        int years = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR);
        //checks whether date is preceded or not in the current year
        if (currentDate.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
            years--;
        }
        return years;
    }
}
