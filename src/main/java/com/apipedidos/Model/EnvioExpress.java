package com.apipedidos.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EXPRESS")
public class EnvioExpress extends Envio {
    private Boolean entrega24Horas;
    private Double recargoUrgencia;
    
    public EnvioExpress() {
        super(25.0, 1);
        this.entrega24Horas = true;
        this.recargoUrgencia = 15.0;
    }
    
    public EnvioExpress(Boolean entrega24Horas, Double recargoUrgencia) {
        super(25.0, 1);
        this.entrega24Horas = entrega24Horas;
        this.recargoUrgencia = recargoUrgencia;
    }
    
    @Override
    public Double calcularCosto() {
        return getCostoBase() + recargoUrgencia;
    }
    
    public Boolean getEntrega24Horas() { return entrega24Horas; }
    public void setEntrega24Horas(Boolean entrega24Horas) { this.entrega24Horas = entrega24Horas; }
    
    public Double getRecargoUrgencia() { return recargoUrgencia; }
    public void setRecargoUrgencia(Double recargoUrgencia) { this.recargoUrgencia = recargoUrgencia; }
}



