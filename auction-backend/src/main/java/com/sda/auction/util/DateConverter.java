package com.sda.auction.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter {

    public Date parse(String dateAsString) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.parse(dateAsString);
    }

    public String format(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormatter.format(date);
    }
}
