package org.vcruz.test.springboot.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banco {
    private long id;
    private String nombre;
    private int totalTransferencia;
}
