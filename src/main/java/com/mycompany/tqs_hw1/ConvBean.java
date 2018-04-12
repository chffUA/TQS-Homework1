package com.mycompany.tqs_hw1;


import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.google.gson.*;
import java.util.Set;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ApplicationScoped
public class ConvBean  {
    
    private double amount;
    private double res;
    private String from;
    private String to;
    private List<String> currencies;
    private CurrencyLayerCaller caller;
    private JsonObject json;
    
    public ConvBean() {
        currencies = new ArrayList<>();
        caller = new CurrencyLayerCaller();
        res = 0;
        init();
    }
    
    public ConvBean(CurrencyLayerCaller caller) {
        currencies = new ArrayList<>();
        this.caller=caller; 
        res = 0;   
        init();
    }
    
    private void init() {
        //fetch json
        JsonElement ele = new JsonParser().parse(caller.fetch());
        JsonElement quotes = ele.getAsJsonObject().get("quotes");
        json = quotes.getAsJsonObject();
        //fill option list
        Set<String> s = json.keySet();      
        for (String cur: s)
            currencies.add(cur.replaceFirst("USD", ""));
        //initialise some vars
        from = currencies.get(0);
        to = currencies.get(0);
    }
    
    public void calculate() {
        calculate(this.from, this.to, this.amount);
    }
    
    public Double calculate(String f, String t, double a) {
        try {
            double factor = 0;
            if (a<=0) { //do nothing
                res = 0;
                return res;
            } else if (f.equals(t)) { //do nothing
                res = a;
                return res;
            } else if (f.equals("USD")) { //direct
                factor = json.get(f+t).getAsDouble();
            } else if (t.equals("USD")) { //inverse
                factor = 1.0 / (json.get(t+f).getAsDouble());
            } else { //indirect
                factor = 1.0 / (json.get("USD"+f).getAsDouble());
                factor *= json.get("USD"+t).getAsDouble();
            }
            res = a * factor;
            return res;
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    public Double getFactor(String f, String t) {
        try {
            if (f.equals(t)) { //do nothing
                return 1.0;
            } else if (f.equals("USD")) { //direct
                return json.get(f+t).getAsDouble();
            } else if (t.equals("USD")) { //inverse
                return 1.0 / (json.get(t+f).getAsDouble());
            } else { //indirect
                double factor = 1.0 / (json.get("USD"+f).getAsDouble());
                factor *= json.get("USD"+t).getAsDouble();
                return factor;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    public String resString() {
        return String.format("%.4f",res);
    }
    
    public void setFrom(String from) {
        this.from=from;
    }
    
    public String getFrom() {
        return this.from;
    }
    
    public void setTo(String to) {
        this.to=to;
    }
    
    public String getTo() {
        return this.to;
    }
    
    public void setAmount(double amount) {
        this.amount=amount;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setRes(double res) {
        this.res=res;
    }
    
    public double getRes() {
        return res;
    }
    
    public void setCurrencies(List<String> currencies) {
        this.currencies=currencies;
    }
    
    public List<String> getCurrencies() {
        return this.currencies;
    }
    
}
