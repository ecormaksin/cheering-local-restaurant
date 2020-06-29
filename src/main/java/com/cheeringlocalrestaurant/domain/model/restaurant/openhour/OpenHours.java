package com.cheeringlocalrestaurant.domain.model.restaurant.openhour;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.cheeringlocalrestaurant.domain.type.time.AbstractTime;
import com.cheeringlocalrestaurant.domain.type.time.EndTime;
import com.cheeringlocalrestaurant.domain.type.time.StartTime;

public class OpenHours {

    private Map<StartTime, OpenHour> map = new TreeMap<>();

    public void add(final String start, final String end) {
        if (StringUtils.defaultString(start).equals("") && StringUtils.defaultString(end).equals("")) return;

        final OpenHour openHour = new OpenHour(start, end);
        final List<OpenHour> openHourList = new ArrayList<>(map.values());
        for (OpenHour element : openHourList) {
            if (openHour.equals(element)) return;
            if (openHour.isOverlapped(element)) throw new OpenHourOverlappedException();
        }
        this.map.put(openHour.getStartTime(), openHour);
    }

    public List<String> listForPresentation() {
        List<String> list = new ArrayList<>();
        final List<OpenHour> openHourList = new ArrayList<>(map.values());
        if (openHourList.size() == 0) return Collections.unmodifiableList(list);

        final OpenHour firstOpenHour = openHourList.get(0);
        final StartTime firstStartTime = firstOpenHour.getStartTime();
        final EndTime firstEndTime = firstOpenHour.getEndTime();
        final EndTime lastEndTime = openHourList.get(openHourList.size() - 1).getEndTime();
        final boolean doConcatenate = doConcatenate(firstStartTime, lastEndTime);

        for (int i = 0; i < openHourList.size(); i++) {
            if (i == 0 && doConcatenate) continue;

            OpenHour openHour = openHourList.get(i);
            String start = openHour.getStartTime().toString();
            String end = openHour.getEndTime().toString();

            if (i == (openHourList.size() - 1) && doConcatenate) {
                LocalTime localTime = firstEndTime.getValue();
                int hour = localTime.getHour() + AbstractTime.BASE_HOURS_OVER_A_DAY;
                int minutes = localTime.getMinute();
                end = String.format("%02d:%02d", hour, minutes);
            }
            list.add(start + "〜" + end);
        }

        return Collections.unmodifiableList(list);
    }

    private boolean doConcatenate(final StartTime firstStartTime, final EndTime lastEndTime) {
        if (!firstStartTime.toString().equals(AbstractTime.min())) return false;
        if (!lastEndTime.toString().equals(AbstractTime.max())) return false;
        return true;
    }
}