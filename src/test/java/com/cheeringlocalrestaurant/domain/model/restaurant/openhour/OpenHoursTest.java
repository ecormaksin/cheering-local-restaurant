package com.cheeringlocalrestaurant.domain.model.restaurant.openhour;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OpenHoursTest {

    @Test
    void _連続営業の場合() {
        String start = "11:00";
        String end = "18:00";
        OpenHours openHours = new OpenHours();
        openHours.add(start, end);

        List<String> expected = new ArrayList<>();
        expected.add(start + "〜" + end);
        assertIterableEquals(expected, openHours.listForPresentation());
    }

    @Test
    void _営業時間が日をまたぐ場合() {
        OpenHours openHours = new OpenHours();
        openHours.add("22:00", "23:59");
        openHours.add("00:00", "03:00");

        List<String> expected = new ArrayList<>();
        expected.add("22:00〜27:00");
        assertIterableEquals(expected, openHours.listForPresentation());
    }

    @Test
    void _昼と夜の営業の場合() {
        OpenHours openHours = new OpenHours();
        openHours.add("11:00", "15:00");
        openHours.add("17:00", "22:00");

        List<String> expected = new ArrayList<>();
        expected.add("11:00〜15:00");
        expected.add("17:00〜22:00");
        assertIterableEquals(expected, openHours.listForPresentation());
    }

    @Test
    void _同じ時間帯を設定した場合は一つにまとめる() {
        OpenHours openHours = new OpenHours();
        openHours.add("11:00", "15:00");
        openHours.add("11:00", "15:00");

        List<String> expected = new ArrayList<>();
        expected.add("11:00〜15:00");
        assertIterableEquals(expected, openHours.listForPresentation());
    }

    @Test
    void _時間帯が重なっていた場合は例外が発生する() {
        assertThrows(OpenHourOverlappedException.class, () -> {
            OpenHours openHours = new OpenHours();
            openHours.add("11:00", "15:00");
            openHours.add("12:00", "16:00");
        });
    }

    @Test
    void _未設定の場合リストは0件() {
        OpenHours openHours = new OpenHours();
        assertTrue(openHours.listForPresentation().size() == 0);
    }

    @Test
    void _開始と終了がともにnullの場合リストは0件() {
        OpenHours openHours = new OpenHours();
        openHours.add((String) null, (String) null);
        assertTrue(openHours.listForPresentation().size() == 0);
    }

    @Test
    void _開始と終了がともに長さ0の場合リストは0件() {
        OpenHours openHours = new OpenHours();
        openHours.add("", "");
        assertTrue(openHours.listForPresentation().size() == 0);
    }
}
