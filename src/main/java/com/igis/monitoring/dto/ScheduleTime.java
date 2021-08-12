package com.igis.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleTime {
    private String dayOfWeek;
    private String time;
}
