package com.apipedidos.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INTERNACIONAL")
/**
 * Envío internacional con costo por aduana y tiempos aumentados.
 */
public class EnvioInternacional extends Envio {
    private String paisDestino;
    private Double impuestoAduana;
    private Boolean requiereDocumentacion;
    
    public EnvioInternacional() {
        super(50.0, 7);
        this.paisDestino = "Default";
        this.impuestoAduana = 30.0;
        this.requiereDocumentacion = true;
    }
    
    public EnvioInternacional(String paisDestino, Double impuestoAduana, Boolean requiereDocumentacion) {
        super(50.0, 7);
        this.paisDestino = paisDestino;
        this.impuestoAduana = impuestoAduana;
        this.requiereDocumentacion = requiereDocumentacion;
    }
    
    @Override
    public Double calcularCosto() {
        return getCostoBase() + impuestoAduana;
    }
    
    public String getPaisDestino() { return paisDestino; }
    public void setPaisDestino(String paisDestino) { this.paisDestino = paisDestino; }
    
    public Double getImpuestoAduana() { return impuestoAduana; }
    public void setImpuestoAduana(Double impuestoAduana) { this.impuestoAduana = impuestoAduana; }
    
    public Boolean getRequiereDocumentacion() { return requiereDocumentacion; }
    public void setRequiereDocumentacion(Boolean requiereDocumentacion) { this.requiereDocumentacion = requiereDocumentacion; }
}



