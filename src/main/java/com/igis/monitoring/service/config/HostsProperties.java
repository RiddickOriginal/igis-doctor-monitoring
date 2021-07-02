package com.igis.monitoring.service.config;

import com.igis.monitoring.dto.HospitalType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "host")
public class HostsProperties {
    private String adultHospitals;
    private String childHospitals;
    private String dentists;
    private String dispensary;
    private Map<HospitalType, String> map;

    @PostConstruct
    public void init(){
        this.map = Map.of(
                HospitalType.ADULT, adultHospitals,
                HospitalType.CHILD, childHospitals,
                HospitalType.DENTIST, dentists,
                HospitalType.DISPENSARY, dispensary);
    }

    public String getCategoryUrl(HospitalType type) {
        return map.get(type);
    }

    public String getBaseUrl() {
        return "https://igis.ru/online";
    }
}
