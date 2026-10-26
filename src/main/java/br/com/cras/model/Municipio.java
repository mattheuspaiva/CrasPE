
package br.com.cras.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Municipio")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long id;

    @Column(name = "codigo_ibge", nullable = false)
    private String codigoIbge;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "uf", nullable = false, columnDefinition = "char(2)")
    private String uf;

    public Municipio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "id=" + id +
                ", codigoIbge='" + codigoIbge + '\'' +
                ", nome='" + nome + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
