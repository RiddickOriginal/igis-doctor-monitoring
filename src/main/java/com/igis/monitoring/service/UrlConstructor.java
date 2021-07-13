package com.igis.monitoring.service;

import com.igis.monitoring.dto.HospitalType;
import com.igis.monitoring.service.config.HostsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlConstructor {
    private final HostsProperties hosts;
    private static final String DOCTORS_LIST_PAGE = "&page=zapdoc";
    private static final String DOCTOR_SCHEDULE_PAGE = "&page=doc";
    private static final String REQUEST_PARAM = "?obj=";
    private static final String DOCTOR_ID = "&id=";

    public String getCategoryUrl(HospitalType type) {
        return hosts.getCategoryUrl(type);
    }

    public String withHost(String link) {
        return hosts.getBaseUrl() + link;
    }

    public String getDoctorsListUrl(String hospitalId) {
        return hosts.getBaseUrl() +
                REQUEST_PARAM +
                hospitalId +
                DOCTORS_LIST_PAGE;
    }

    public String getDoctorsScheduleUrl(String hospitalId, String docId) {
        return hosts.getBaseUrl() + REQUEST_PARAM + hospitalId + DOCTOR_SCHEDULE_PAGE + DOCTOR_ID + docId;
    }
}
