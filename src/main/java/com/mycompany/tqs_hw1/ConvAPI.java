package com.mycompany.tqs_hw1;

/**
 *
 * @author Carlos
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
 
@Path("/convert")
public class ConvAPI {
    
    private final ConvBean bean = new ConvBean();
 
    @Path("/get")
    @GET
    @Produces("application/json")
    public Response convert(
        @QueryParam("from") String from,
        @QueryParam("to") String to,
        @QueryParam("amount") String amt) {
        
        String error = "{\"error\":\"invalid parameters\"}";
        
        if (from==null || to==null || amt==null) {
            return Response.status(404).entity(error).build();
        }
        
        double amount = 0;       
        try {
            amount = Double.parseDouble(amt);
        } catch (NumberFormatException e) {
            return Response.status(404).entity(error).build();
        }
        
        Double val = bean.calculate(from, to, amount);
        Double rate = bean.getFactor(from, to);
        if (amount<0 || val==null || rate==null) {
            return Response.status(404).entity(error).build();
        }
        
        String formattedAmount = String.format("%.4f",amount);
        String formattedResult = String.format("%.4f",val);
        String formattedRate = String.format("%.4f",rate);
        
        String result = "{\"from\":\""+from+"\",\"to\":\""+to+
                "\",\"amount\":"+formattedAmount+",\"result\":"+formattedResult+
                ",\"rate\":"+formattedRate+"}";
        
        return Response.status(200).entity(result).build();
    }
}
