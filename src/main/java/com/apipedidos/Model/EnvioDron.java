package com.apipedidos.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DRON")
/**
 * Envío por dron con tarifa fija reducida.
 */
public class EnvioDron extends Envio {
    private Double pesoMaximo;
    private Boolean requiereLicencia;
    
    public EnvioDron() {
        super(15.0, 1);
        this.pesoMaximo = 5.0;
        this.requiereLicencia = true;
    }
    
    public EnvioDron(Double pesoMaximo, Boolean requiereLicencia) {
        super(15.0, 1);
        this.pesoMaximo = pesoMaximo;
        this.requiereLicencia = requiereLicencia;
    }
    
    @Override
    public Double calcularCosto() {
        return getCostoBase() + 5.0;
    }
    
    public Double getPesoMaximo() { return pesoMaximo; }
    public void setPesoMaximo(Double pesoMaximo) { this.pesoMaximo = pesoMaximo; }
    
    public Boolean getRequiereLicencia() { return requiereLicencia; }
    public void setRequiereLicencia(Boolean requiereLicencia) { this.requiereLicencia = requiereLicencia; }
}



