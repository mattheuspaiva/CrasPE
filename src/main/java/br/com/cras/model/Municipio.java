package br.com.cras.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Municipio")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ibge")
    private String ibge;

    @Column(name = "uf")
    private String uf;

    @Column(name = "cidade")
    private String cidade;

    public Municipio() {
    }

    public Municipio(String ibge, String uf, String cidade) {
        this.ibge = ibge;
        this.uf = uf;
        this.cidade = cidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "id=" + id +
                ", ibge='" + ibge + '\'' +
                ", uf='" + uf + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}