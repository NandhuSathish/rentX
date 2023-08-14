package com.innovature.rentx.util;

import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

@Component
public class DateValidation {
    @Autowired
    private LanguageUtil languageUtil;

    public boolean dateCheck(Date startDate, Date endDate) {

        Date currentDate = new Date();
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curdate = dateFormat.format(currentDate);
        try {
            dt = dateFormat.parse(curdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int countStart = startDate.compareTo(dt);

        // int countEnd=endDate.compareTo(dt);

        int btwStartEnd = endDate.compareTo(startDate);

        if (countStart < 0) {
            throw new BadRequestException(languageUtil.getTranslatedText("start.date.validation", null, "en"));
        }

        if (btwStartEnd < 0) {
            throw new BadRequestException(languageUtil.getTranslatedText("btw.start.end.date", null, "en"));
        }

        return false;
    }

    public boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public Date stringToStartDateFormat(String StringDate) {

        try {
            Date date = new SimpleDateFormat(Json.DATE_FORMAT).parse(StringDate);
            return date;
        } catch (ParseException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("start.date.validation", null, "en"));

        }

    }

    public Date stringToEndDateFormat(String StringDate) {

        try {
            Date date = new SimpleDateFormat(Json.DATE_FORMAT).parse(StringDate);
            return date;
        } catch (ParseException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("end.date.validation", null, "en"));

        }

    }
}
