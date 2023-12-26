package com.products.utils;

public class Util {
    
    public static String formatTitle(String text){
        text = text.trim();
        String newTitle = "";
        for(String t : text.split("\\s")){
            newTitle += t.substring(0,1).toUpperCase() + t.substring(1).toLowerCase() + " ";
        }
        return newTitle.trim();
    }

    public static String formatText(String text){
        String newText = text.trim();
        if(!newText.endsWith("."))
            newText += ".";
        newText = newText.substring(0, 1).toUpperCase() + newText.substring(1);
        return newText;
    }
}
