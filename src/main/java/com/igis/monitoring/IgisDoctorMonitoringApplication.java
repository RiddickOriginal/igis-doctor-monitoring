package com.igis.monitoring;

import com.igis.monitoring.dto.DoctorCard;
import com.igis.monitoring.service.config.HostsProperties;
import com.igis.monitoring.dto.HospitalType;
import com.igis.monitoring.service.IgisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;
import java.util.Map;

@EnableConfigurationProperties(HostsProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class IgisDoctorMonitoringApplication {
    @Autowired
    IgisService igisService;

    public static void main(String[] args) {
        SpringApplication.run(IgisDoctorMonitoringApplication.class, args);
    }
}
