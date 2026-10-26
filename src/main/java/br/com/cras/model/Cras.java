
package br.com.cras.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cras")
public class Cras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cras")
    private Long id;

    @Column(name = "id_equipamento_origem", nullable = false)
    private String idEquipamentoOrigem;

    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public Cras() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEquipamentoOrigem() {
        return idEquipamentoOrigem;
    }

    public void setIdEquipamentoOrigem(String idEquipamentoOrigem) {
        this.idEquipamentoOrigem = idEquipamentoOrigem;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public String toString() {
        return "Cras{" +
                "id=" + id +
                ", idEquipamentoOrigem='" + idEquipamentoOrigem + '\'' +
                ", nome='" + nome + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
