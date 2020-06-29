package com.cheeringlocalrestaurant.domain.model.restaurant.tempopenday;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.cheeringlocalrestaurant.domain.type.datetime.TempOpenEndDateTime;
import com.cheeringlocalrestaurant.domain.type.datetime.TempOpenStartDateTime;
import com.cheeringlocalrestaurant.domain.type.time.AbstractTime;

import lombok.Getter;

@Getter
public class TempOpenDay {

    private TempOpenStartDateTime tempOpenStartDateTime;
    private TempOpenEndDateTime tempOpenEndDateTime;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long dateSpan;
    
    public TempOpenDay(final String startDateTimeStr, final String endDateTimeStr) {
        tempOpenStartDateTime = new TempOpenStartDateTime(startDateTimeStr);
        tempOpenEndDateTime = new TempOpenEndDateTime(endDateTimeStr);
        
        start = tempOpenStartDateTime.getValue();
        end = tempOpenEndDateTime.getValue();
        dateSpan = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
        if (dateSpan > 1) throw new DateSpanOverADayException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(start.format(DateTimeFormatter.ofPattern("uuuu/MM/dd " + AbstractTime.FORMAT_STRING)));
        sb.append("〜");
        if (dateSpan == 0) {
            sb.append(end.format(DateTimeFormatter.ofPattern(AbstractTime.FORMAT_STRING)));
        } else {
            final int hour = end.getHour() + AbstractTime.BASE_HOURS_OVER_A_DAY;
            sb.append(hour + ":" + end.format(DateTimeFormatter.ofPattern("mm")));
        }
        return sb.toString();
    }
}
