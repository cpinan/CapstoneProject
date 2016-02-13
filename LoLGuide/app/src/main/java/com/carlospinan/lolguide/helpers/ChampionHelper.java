package com.carlospinan.lolguide.helpers;

import com.carlospinan.lolguide.data.enums.ChampDataEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionHelper {

    public static final String DELIMITER = "@";
    public static final String DOUBLE_DELIMITER = "#";

    // Utilities
    public static ChampDataEnum getChampDataFromString(String value) {
        try {
            return ChampDataEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    // String array utility
    public static String getStringFromList(List<String> list) {
        String response = "";
        if (list != null && !list.isEmpty()) {
            if (list.size() == 1) {
                response = list.get(0);
            } else {
                for (Object object : list) {
                    response += object + DELIMITER;
                }
                response = response.substring(0, response.length() - DELIMITER.length());
            }
        }
        return response;
    }

    public static String getStringFromDoubleList(List<Double> list) {
        List<String> newList = new ArrayList<>();
        for (Double d : list) {
            newList.add(String.valueOf(d));
        }
        return getStringFromList(newList);
    }

    public static String getStringFromIntegerList(List<Integer> list) {
        List<String> newList = new ArrayList<>();
        for (Integer i : list) {
            newList.add(String.valueOf(i));
        }
        return getStringFromList(newList);
    }

    public static String stringFromDoubleList(List<List<Double>> list) {
        String response = "";
        if (list != null && !list.isEmpty()) {
            for (List<Double> l : list) {
                if (l == null) {
                    response += "" + DOUBLE_DELIMITER;
                } else {
                    response += getStringFromDoubleList(l) + DOUBLE_DELIMITER;
                }
            }
            response = response.substring(0, response.length() - DOUBLE_DELIMITER.length());
        }
        return response;
    }

    public static List<List<Double>> toDoubleListFromString(String string) {
        List<List<Double>> list = new ArrayList<>();
        if (string != null) {
            String[] split = string.split(DOUBLE_DELIMITER);
            List<String> stringList = Arrays.asList(split);
            for (String s : stringList) {
                List<Double> singleList = new ArrayList<>();
                List<String> stringList1 = getListStringFromString(s);
                for (String s1 : stringList1) {
                    if (s1 == null || s1.isEmpty() || s1.equalsIgnoreCase("null")) {
                        singleList.add(null);
                    } else {
                        singleList.add(Double.parseDouble(s1));
                    }
                }
                list.add(singleList);
            }
        }
        return list;
    }

    public static List<String> getListStringFromString(String string) {
        String[] split = string.split(DELIMITER);
        return Arrays.asList(split);
    }

    public static List<Double> getListDoubleFromString(String string) {
        String[] split = string.split(DELIMITER);
        List<Double> list = new ArrayList<>();
        for (String s : split) {
            list.add(Double.valueOf(s));
        }
        return list;
    }
}
