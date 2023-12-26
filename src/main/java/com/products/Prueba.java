package com.products;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Prueba {
    
    public static void main(String[] args) {
        System.out.println(redondear(new BigDecimal(Double.parseDouble("1260.9")), 100));

        // System.out.println(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC));
        // System.out.println(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        // System.out.println(Instant.now());

        System.out.println(UUID.randomUUID().toString() + "ASDDAS");
    }

    private static BigDecimal redondear(BigDecimal valorInicial, long scale){

        scale = (scale != 10 && scale != 100) ? 10 : scale;
        BigDecimal resto = valorInicial.remainder(BigDecimal.valueOf(scale));
        BigDecimal redondeo = BigDecimal.ZERO;

        boolean condicion = resto.compareTo(BigDecimal.valueOf(3L)) >= 0;
        if(scale > 10L){
            condicion = resto.compareTo(BigDecimal.valueOf(60L)) > 0;
        }

        if(condicion){
            redondeo = valorInicial.add(BigDecimal.valueOf(scale).subtract(resto));
        }else{
            redondeo = valorInicial.subtract(resto);
        }
        return redondeo;
    }
}
