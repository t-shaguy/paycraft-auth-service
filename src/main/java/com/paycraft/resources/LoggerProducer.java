/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraft.resources;


import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author Antonio Goncalves        http://www.antoniogoncalves.org
 * @author taysayshaguy
 */
//@ApplicationScoped
public class LoggerProducer {

    // ======================================
    // =              Producers             =
    // ======================================

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
