package com.incon.service.utils;



import com.incon.service.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils implements AppConstants.DateFormatterConstants {
    private static HashMap<String, String> monthNames = new HashMap<>();

    static {

        monthNames.put("01", "January");
        monthNames.put("02", "February");
        monthNames.put("03", "March");
        monthNames.put("04", "April");
        monthNames.put("05", "May");
        monthNames.put("06", "June");
        monthNames.put("07", "July");
        monthNames.put("08", "August");
        monthNames.put("09", "Septempber");
        monthNames.put("10", "October");
        monthNames.put("11", "November");
        monthNames.put("12", "December");
    }

    /**
     * Returns Today, Yesterday, the day of the week within one week, or a
     * symptomSelectedDate if greater.
     *
     * @param milliSeconds
     * @return
     */
    public static String formatTimeDay(long milliSeconds) {
        Date currentDate = new Date();

        String timeBarDayText = "";
        long differenceInMillis = currentDate.getTime() - milliSeconds;

        long differenceInSeconds = differenceInMillis / 1000;
        if (differenceInSeconds < 60) {
            timeBarDayText = differenceInSeconds + "s ago";
            return timeBarDayText;
        }

        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
        if (differenceInMinutes < 60) {
            timeBarDayText = differenceInMinutes + "m ago";
            return timeBarDayText;
        }

        long differenceInHours = TimeUnit.MILLISECONDS.toHours(differenceInMillis);
        if (differenceInHours < 24) {
            timeBarDayText = differenceInHours + "h ago";
            return timeBarDayText;
        }

        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
        if (differenceInDays < 7) {
            timeBarDayText = differenceInDays + "d ago";
            return timeBarDayText;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(differenceInMillis);
        int differenceInMonths = c.get(Calendar.MONTH);

        long differenceInWeeks = TimeUnit.MILLISECONDS.toDays(differenceInMillis) / 7;
        if (differenceInWeeks < 4 || differenceInMonths == 0) {
            timeBarDayText = differenceInWeeks == 1 ? differenceInWeeks + "week ago"
                    : differenceInWeeks + "weeks ago";
            return timeBarDayText;
        }


        if (differenceInMonths < 12) {
            timeBarDayText = differenceInMonths == 1 ? differenceInMonths + "month ago"
                    : differenceInMonths + "months ago";
            return timeBarDayText;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        int differenceInYears = ValidationUtils.calculateAge(calendar); //milliseconds are
        if (differenceInYears > 0) {
            timeBarDayText = differenceInYears == 1 ? differenceInYears + "year ago"
                    : differenceInYears + "years ago";
            return timeBarDayText;
        }

        return timeBarDayText;
    }


    /////////////////////////////////After api changes
    public static String convertFormattedDateToDateUsingTimezone(String inputTimezone, String
            inputDate, String inputDateFormat, String outputDateFormat) {

        try {

            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone(inputTimezone)); // missing line
            Date parsedInputDate = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getTimeZone(inputTimezone)); // missing line

            return writeDate.format(parsedInputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String convertDateToOtherFormat(Date inputDate, String outputDateFormat) {
        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            String s = writeDate.format(inputDate);

            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date convertStringFormatToDate(String inputDate, String inputDateFormat) {

        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(inputDateFormat, DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            Date outputDate = writeDate.parse(inputDate);
            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long convertStringFormatToMillis(String inputDate, String inputDateFormat) {

        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(inputDateFormat, DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            Date outputDate = writeDate.parse(inputDate);
            return outputDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long convertDifferenceDateIndays(long date1, long date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(date2);
        Date d1 = c1.getTime();
        Date d2 = c2.getTime();
        long diff = d1.getTime() - d2.getTime();

        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static String convertMillisToStringFormat(long milliSeconds,
                                                     String outputDateFormat) {
        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            Date date = new Date(milliSeconds);

            return writeDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertStringFormatToOtherStringFormat(String inputDateFormat,
                                                                String inputDate,
                                                                String outputDateFormat) {
        try {
            SimpleDateFormat readDateFormatter = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            Date readDate = readDateFormatter.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);

            return writeDate.format(readDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * This method converts the symptomSelectedDate from string.
     *
     * @param milliseconds time
     */
    public static String convertMillsToDateString(String format, long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        return dateFormat.format(calendar.getTime());
    }


    public static Date convertMillsToDate(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return calendar.getTime();
    }


    public static String convertUTCMillsToDateString(String format, long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static String getDateFromDayMonthYear(String year, String nameOfMonth, int dayOfMonth) {
        return year + "-"
                + DateUtils.convertMonthNameToNumber(nameOfMonth)
                + "-" + DateUtils.convertSingleDigitToTwoDigits(
                dayOfMonth);
    }

    public static Date convertStringToDate(String inputDate, String inputDateFormat,
                                           String outputDateFormat) {
        try {
            String dtc = inputDate;
            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getDefault()); // missing line
            Date date = readDate.parse(dtc);
            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            String s = writeDate.format(date);
            Date date1 = writeDate.parse(s);

            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToAnotherFormat(String inputDate, String inputDateFormat,
                                                    String outputDateFormat) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getDefault()); // missing line
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String convertMonthNameToNumber(String monthName) {
        Date date;
        try {
            date = new SimpleDateFormat("MMMM",
                    DATE_FORMAT_LOCALE).parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return convertSingleDigitToTwoDigits(cal.get(Calendar.MONTH) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertSingleDigitToTwoDigits(int num) {
        return num <= 9 ? "0" + num : String.valueOf(num);
    }


    public static long convertDateToUTCMilliseconds(String format, String date) {
        SimpleDateFormat writeDate = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        writeDate.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date outputDate = writeDate.parse(date);
            return outputDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long convertToMilliseconds(String format, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        try {
            Date outputDate = dateFormat.parse(date);
            return outputDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String convertTimeFormat(String inputDate,
                                           String inputFormat, String outputFormat,
                                           TimeZone inputTimeZone, TimeZone outputTimezone) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(inputFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(inputTimeZone);
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(outputTimezone);

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getMonthName(String month) {

        return monthNames.get(month);
    }

}
