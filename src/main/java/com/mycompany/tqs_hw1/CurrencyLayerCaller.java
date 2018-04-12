package com.mycompany.tqs_hw1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Carlos
 */
public class CurrencyLayerCaller {
    
    private static final String APIKEY = "7e19d62d7b447320cedb372ea73a5cc0";
    
    public String fetch() {
        Document doc = null;
        try {
            doc = Jsoup.connect(
                    "http://www.apilayer.net/api/live?access_key="+APIKEY+"&format=1"
                ).ignoreContentType(true).get();
        } catch (IOException ex) {
            Logger.getLogger(CurrencyLayerCaller.class.getName()).log(Level.SEVERE, "Failed to get data.", ex);
            return "";
        }
        return doc.body().text();
    }
    
}
