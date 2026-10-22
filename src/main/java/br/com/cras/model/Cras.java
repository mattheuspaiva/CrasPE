package br.com.cras.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Cras")
public class Cras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_equipamento")
    private String idEquipamento;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    public Cras() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return "Cras{" +
                "id=" + id +
                ", idEquipamento='" + idEquipamento + '\'' +
                ", nome='" + nome + '\'' +
                ", municipio=" + municipio +
                '}';
    }
}