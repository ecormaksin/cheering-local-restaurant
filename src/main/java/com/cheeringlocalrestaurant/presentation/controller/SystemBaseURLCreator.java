package com.cheeringlocalrestaurant.presentation.controller;

import javax.servlet.http.HttpServletRequest;

import com.cheeringlocalrestaurant.domain.type.SystemBaseURL;

public final class SystemBaseURLCreator {

    public static SystemBaseURL execute(final HttpServletRequest request) {
        
        final int serverPort = request.getServerPort();
        
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        if (serverPort != 80 && serverPort != 443) {
            sb.append(":" + serverPort);
        }
        sb.append("/");
        
        return new SystemBaseURL(sb.toString());
    }
}
