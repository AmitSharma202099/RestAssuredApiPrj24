package com.qa.api.utils;

public class StringUtility {


    public static String getRandonEmailId() {
        return "ApiAutomation"+System.currentTimeMillis()+"@gmail.com";
    }

    public static String getName() {
        return "ApiAutomation";
    }
}
