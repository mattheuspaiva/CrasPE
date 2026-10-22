package br.com.cras.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "telefone")
    private String telefone;

    public Contato() {
    }

    public Contato(String responsavel, String telefone) {
        this.responsavel = responsavel;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", responsavel='" + responsavel + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}