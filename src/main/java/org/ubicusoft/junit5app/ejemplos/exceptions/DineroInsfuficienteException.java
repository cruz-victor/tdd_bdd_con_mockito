package org.ubicusoft.junit5app.ejemplos.exceptions;

public class DineroInsfuficienteException extends RuntimeException{
    public DineroInsfuficienteException(String mensaje) {
        super(mensaje);
    }
}
