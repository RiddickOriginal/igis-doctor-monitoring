package com.igis.monitoring.service;

import com.igis.monitoring.dto.DoctorCard;
import com.igis.monitoring.dto.HospitalType;
import com.igis.monitoring.service.config.HostsProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
public class IgisService {
    private final RestTemplate restTemplate;
    private final HostsProperties hosts;
    private final String DOCTORS_LIST_PAGE = "&page=zapdoc";
    private final String REQUEST_PARAM = "?obj=";

    public Map<String, String> getTipsMap(HospitalType type) {
        Document document;
        try {
            document = Jsoup.connect(hosts.getCategoryUrl(type)).get();
        } catch (IOException e) {
            return null;
        }
        Elements elements = document.select("a[href^='?obj=']");
        Map<String, String> map = new HashMap<>();
        elements.forEach((element) -> {
            map.put(element.text(), element.attr("href").replace("?obj=", ""));
        });
        return map;
    }

    public Map<String, List<DoctorCard>> getDoctorsList(String id)  {
        String url = hosts.getBaseUrl() + REQUEST_PARAM + id + DOCTORS_LIST_PAGE;
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            return null;
        }
        Elements select = document.select(".table-border");
        Elements elements = select.select("td");
        String currentCategory = "";
        Map<String, List<DoctorCard>> doctorsByCategory = new HashMap<>();
        for (var element : elements) {
            if(!element.select(".adsbygoogle").isEmpty())
                continue;
            Elements category = element.select("h2");
            if (category.isEmpty()) {
                String link = hosts.getBaseUrl() + element.selectFirst("a").attr("href");
                String ticketsCount = element.select("a+a").text();
                String name = element.selectFirst("b").text();
                String bio = element.select("div+small").text();
                DoctorCard doctorCard = new DoctorCard(name, link, bio);
                doctorsByCategory.get(currentCategory).add(doctorCard);
                continue;
            }

            doctorsByCategory.put(category.text(), new ArrayList<>());
            currentCategory = category.text();
        }
        return doctorsByCategory;
    }
}
