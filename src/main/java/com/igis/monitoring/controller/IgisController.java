package com.igis.monitoring.controller;

import com.igis.monitoring.dto.DoctorCard;
import com.igis.monitoring.dto.HospitalType;
import com.igis.monitoring.service.IgisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IgisController {
    private final IgisService service;

    @GetMapping("/tips")
    public ResponseEntity<Map<String, String>> getHospitalMapByType(@RequestParam("type") HospitalType type) {
        Map<String, String> tipsMap = service.getTipsMap(type);
        if (tipsMap == null)
            return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok(tipsMap);
    }

    @GetMapping("/doctors")
    public ResponseEntity<Map<String, List<DoctorCard>>> getDoctorsCardsByCategory(@RequestParam("id") String id){
        Map<String, List<DoctorCard>> doctorsList = service.getDoctorsList(id);
        if (doctorsList == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(doctorsList);
    }
}
