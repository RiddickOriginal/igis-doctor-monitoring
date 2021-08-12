package com.igis.monitoring.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElementsService {

    public Elements getElementsByLink(String link, String selector){
        Document document;
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e){
            return null;
        }
        return document.select(selector);
    }
}
