package com.igis.monitoring.service;

import com.igis.monitoring.dto.DoctorCard;
import com.igis.monitoring.service.config.HostsProperties;
import com.igis.monitoring.dto.HospitalType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
public class IgisService {
    private final RestTemplate restTemplate;
    private final HostsProperties hosts;
    private final String doctorsListPage = "&page=zapdoc";

    public Map<String, String> getTipsMap(HospitalType type) throws IOException {
        Document document = Jsoup.connect(hosts.getCategoryUrl(type)).get();
        Elements elements = document.select("a[href^='?obj=']");
        Map<String, String> map = new HashMap<>();
        elements.forEach((element)->{
            map.put(element.text(), element.attr("href"));
        });
        return map;
    }

    public Map<String, String> getDoctorsList(String hospitalUrl) throws IOException {
        Document document = Jsoup.connect(hosts.getBaseUrl() + hospitalUrl + doctorsListPage).get();
        Elements elements = document.getElementsByTag("td");
        String currentCategory = "";
        Map<String, List<DoctorCard>> doctorsByCategory = new HashMap<>();
        for(var element : elements){
            Elements category = element.select("h2");
            if(category.isEmpty()){
                String link = element.selectFirst("a").attr("href");
                String ticketsCount = element.select("a+a").text();
                String name = element.selectFirst("b").text();
                String bio = element.select("div+small").text();

                doctorsByCategory.get(currentCategory).add()
            }
        }
    }
}
