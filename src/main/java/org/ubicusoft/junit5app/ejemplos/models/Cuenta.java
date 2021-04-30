package org.ubicusoft.junit5app.ejemplos.models;

import java.math.BigDecimal;
import java.util.Locale;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona.toUpperCase();
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cuenta)){
            return false;
        }
        Cuenta c=(Cuenta)obj;
        if (this.persona == null || this.saldo==null) {
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
