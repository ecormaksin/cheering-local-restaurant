package com.cheeringlocalrestaurant.domain.model.restaurant.tempopenday;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TempOpenDayTest {

    @Test
    void _日またぎでない場合はそのままの時間帯で表示される() {
        TempOpenDay tempOpenDay = new TempOpenDay("2020/06/28 11:00", "2020/06/28 18:00");
        assertEquals("2020/06/28 11:00〜18:00", tempOpenDay.toString());
    }

    @Test
    void _日またぎの場合は終了時刻が24時間加算された状態で表示される() {
        TempOpenDay tempOpenDay = new TempOpenDay("2020/06/28 22:00", "2020/06/29 03:00");
        assertEquals("2020/06/28 22:00〜27:00", tempOpenDay.toString());
    }
    
    @Test
    void _1日を超える日またぎの場合は例外が発生する() {
        assertThrows(DateSpanOverADayException.class, () -> {
            new TempOpenDay("2020/06/28 22:00", "2020/06/30 03:00");
        });
    }
}
