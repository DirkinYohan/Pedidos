package com.apipedidos.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESTANDAR")
/**
 * Envío estándar sin recargos.
 */
public class EnvioEstandar extends Envio {
    private Boolean requiereFirma;
    
    public EnvioEstandar() {
        super(8.0, 5);
        this.requiereFirma = false;
    }
    
    public EnvioEstandar(Boolean requiereFirma) {
        super(8.0, 5);
        this.requiereFirma = requiereFirma;
    }
    
    @Override
    public Double calcularCosto() {
        return getCostoBase();
    }
    
    public Boolean getRequiereFirma() { return requiereFirma; }
    public void setRequiereFirma(Boolean requiereFirma) { this.requiereFirma = requiereFirma; }
}



