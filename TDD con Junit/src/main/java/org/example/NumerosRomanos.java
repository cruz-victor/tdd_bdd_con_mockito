package org.example;

import java.util.Arrays;
import java.util.List;

public class NumerosRomanos {

    /*
    * La numeracion romana se basa en siete letras mayusculas.
    * Letras    I   V   X   L   C   D   M
    * Valores   1   5   10  50  100 500 1000
    * */
    private final List<String> simbolosRomanos= Arrays.asList("I","V","X","L","C","D","M");



    public String convertirAromanos(Integer numeroNatural) {

        char[] numerosChart=numeroNatural.toString().toCharArray();

        int incremento=0;
        String resultado="";

        for (int i = numerosChart.length-1; i >=0 ; i--) {

            String numeroRomanoParcial=pasarGenerico(Character.getNumericValue(numerosChart[i]),0+incremento,1+incremento,2+incremento);

            resultado=numeroRomanoParcial+resultado;
            incremento+=2;
        }

        return resultado;
    }

    private String pasarGenerico(int decena, int x, int y, int z) {
        switch (decena){
            case 4:return simbolosRomanos.get(x)+simbolosRomanos.get(y);
            case 9:return simbolosRomanos.get(x)+simbolosRomanos.get(z);
        }

        if (decena<=3){
            return sumar("",1,decena,simbolosRomanos.get(x));
        }

        if(decena<=8)
        {
            return sumar(simbolosRomanos.get(y),6,decena,simbolosRomanos.get(x));
        }

        return null;
    }

    private String sumar(String numeroRomanoInicial, int numeroNaturalInicial, int numeroNatural, String incrementoRomano){
        String numeroRomano=numeroRomanoInicial;
        for (int i = numeroNaturalInicial; i <=numeroNatural;i++) {
            numeroRomano=numeroRomano+incrementoRomano;
        }
        return numeroRomano;
    }
}
