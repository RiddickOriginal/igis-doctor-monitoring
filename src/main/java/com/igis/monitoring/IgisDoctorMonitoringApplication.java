package com.igis.monitoring;

import com.igis.monitoring.service.config.HostsProperties;
import com.igis.monitoring.dto.HospitalType;
import com.igis.monitoring.service.IgisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@EnableConfigurationProperties(HostsProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class IgisDoctorMonitoringApplication implements CommandLineRunner {
    @Autowired
    IgisService igisService;

    public static void main(String[] args) {
        SpringApplication.run(IgisDoctorMonitoringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> hospitalsMap = igisService.getTipsMap(HospitalType.ADULT);
        System.out.println(hospitalsMap);
    }
}
