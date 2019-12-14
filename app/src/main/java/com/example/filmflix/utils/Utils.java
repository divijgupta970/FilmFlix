package com.example.filmflix.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String convertDate(String releaseDate){
        Date dateInitial= new Date();
        try {
            dateInitial = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String pattern="dd MMMM, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateConverted = "Released on "+simpleDateFormat.format(dateInitial);
        return dateConverted;
    }
}
