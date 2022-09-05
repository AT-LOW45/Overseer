package com.k.utilities;

import com.k.Activity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MiscConfig {

    private final StringBuilder pollLogSheet = new StringBuilder();

    public static String generateID(Activity activityType) {
        UUID newID = UUID.randomUUID();
        return activityType.getPrefix() + newID.toString();
    }

    public static String setTime(String type) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(type.equals("hm") ? "HH:mm" : "dd-MM HH:mm" );
        LocalTime localTime = LocalTime.now();
        return dtf.format(localTime);
    }
}
