package com.igis.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorCard {
    String id;
    String name;
    String link;
    String bio;
    String ticketsCount;
}
