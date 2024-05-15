/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.exceptions;

/**
 *
 * @author root
 */
//import com.paycraftsystems.error.messages.ErrorCodes;
//import com.paycraftsystems.error.codes.ErrorCodes;
import com.paycraftsystems.error.messages.ErrorCodes;
import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
//import javax.ws.rs.WebApplicationException;


public class PaycraftException extends WebApplicationException {

    public PaycraftException(String message, Throwable cause) {
        super(Response.status(400).header("message", message).header("cause", cause.getMessage()).build());
    }
    
    public PaycraftException(String message) {
        super(Response.status(400).header("message", message).build());
    }
    
    public PaycraftException(int status, String message, Throwable cause) {
        super(Response.status(status).header("message", message).header("cause", cause.getMessage()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErroDesc(status)).build()).build());
    }
    
    public PaycraftException(int status, String cause) {
        
        
        super(Response.status(status).header("message", cause).header("cause", cause).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErroDesc(status)).build()).build());
    }

}
