
package br.com.cras.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cras", nullable = false)
    private Cras cras;

    @Column(name = "telefone")
    private String telefone;

    public Contato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cras getCras() {
        return cras;
    }

    public void setCras(Cras cras) {
        this.cras = cras;
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
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
