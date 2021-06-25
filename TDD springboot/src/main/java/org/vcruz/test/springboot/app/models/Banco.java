package org.vcruz.test.springboot.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor //En las entidades siempre es necesario un constructor vacio para que se pueda manejar el contexto de hibernate
@AllArgsConstructor
@Entity
@Table(name = "bancos")
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    @Column(name="total_transferencias")
    private int totalTransferencia;
}
