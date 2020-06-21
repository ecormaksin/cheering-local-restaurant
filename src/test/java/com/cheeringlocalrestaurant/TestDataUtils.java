package com.cheeringlocalrestaurant;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.SystemBaseURL;

/**
 * テストで共通に使用するデータ
 * ※オブジェクト指向の原則に外れているが、効率を考慮してすべてpublicアクセスにする。
 * 
 * @author vagrant
 *
 */
public final class TestDataUtils {

    public static final String SYSTEM_BASE_URL = "http://localhost:8080";
    public static final String RESTAURANT_NAME = "いろは食堂";
    public static final String MAIL_ADDRESS = "iroha@example.com";
    public static final String REMOTE_IP_ADDRESS = "127.0.0.1";

    public static final SystemBaseURL systemBaseURL = new SystemBaseURL(SYSTEM_BASE_URL);
    public static final MailAddress mailAddress = new MailAddress(MAIL_ADDRESS);
    public static final RestaurantTempRegister restaurantTempRegister = new RestaurantTempRegister(RESTAURANT_NAME, MAIL_ADDRESS);
    public static final RemoteIpAddress remoteIpAddress = new RemoteIpAddress(REMOTE_IP_ADDRESS);
}
