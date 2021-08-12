package com.igis.monitoring.service;

import com.igis.monitoring.dto.DoctorCard;
import com.igis.monitoring.dto.HospitalType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Service
@RequiredArgsConstructor
public class IgisService {
    private final RestTemplate restTemplate;
    private final UrlConstructor constructor;
    private final ElementsService elementsService;
    private static final Pattern docIdPattern = Pattern.compile("\\d*$");

    public Map<String, String> getTipsMap(HospitalType type) {
        Elements elements = elementsService.getElementsByLink(constructor.getCategoryUrl(type), "a[href^='?obj=']");
        Map<String, String> map = new HashMap<>();
        elements.forEach((element) -> map.put(element.text(), element.attr("href").replace("?obj=", "")));
        return map;
    }

    public Map<String, List<DoctorCard>> getDoctorsList(String hospitalId) {
        Elements elements = elementsService.getElementsByLink(constructor.getDoctorsListUrl(hospitalId), ".table-border td");
        String currentCategory = "";
        Map<String, List<DoctorCard>> doctorsByCategory = new HashMap<>();
        for (var element : elements) {
            if (!element.select(".adsbygoogle").isEmpty())
                continue;
            Elements category = element.select("h2");
            if (category.isEmpty()) {
                String docId = "?";
                Matcher matcher = docIdPattern.matcher(element.selectFirst("a").attr("href"));
                if (matcher.find()) {
                    docId = matcher.group();
                }
                String link = constructor.withHost(element.selectFirst("a").attr("href"));
                String ticketsCount = element.select("a+a").text();
                String name = element.selectFirst("b").text();
                String bio = element.select("div+small").text();
                DoctorCard doctorCard = new DoctorCard(docId, name, link, bio, ticketsCount);
                doctorsByCategory.get(currentCategory).add(doctorCard);
                continue;
            }

            doctorsByCategory.put(category.text(), new ArrayList<>());
            currentCategory = category.text();
        }
        return doctorsByCategory;
    }

    public Map getDoctorsSchedule(String hospitalId, String docId) {
        Elements elements = elementsService.getElementsByLink(constructor.getDoctorsScheduleUrl(hospitalId, docId), ".table-border td");

        return null;
    }
}
