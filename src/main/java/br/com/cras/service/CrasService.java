
package br.com.cras.service;

import br.com.cras.dao.CrasDAO;
import br.com.cras.model.Cras;

import java.util.List;

public class CrasService {

    private final CrasDAO crasDAO = new CrasDAO();

    public void cadastrar(Cras cras) {

        if (cras == null) {
            throw new IllegalArgumentException(
                    "A unidade CRAS não pode ser nula."
            );
        }

        if (cras.getNome() == null || cras.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da unidade é obrigatório."
            );
        }

        if (cras.getIdEquipamentoOrigem() == null
                || cras.getIdEquipamentoOrigem().isBlank()) {
            throw new IllegalArgumentException(
                    "O identificador do equipamento é obrigatório."
            );
        }

        if (cras.getMunicipio() == null) {
            throw new IllegalArgumentException(
                    "O município da unidade é obrigatório."
            );
        }

        if (crasDAO.buscarPorIdEquipamento(
                cras.getIdEquipamentoOrigem()) != null) {

            throw new IllegalArgumentException(
                    "Já existe uma unidade com esse identificador."
            );
        }

        crasDAO.salvar(cras);
    }

    public void atualizar(Cras cras) {

        if (cras == null) {
            throw new IllegalArgumentException(
                    "A unidade CRAS não pode ser nula."
            );
        }

        if (cras.getNome() == null || cras.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da unidade é obrigatório."
            );
        }

        if (cras.getMunicipio() == null) {
            throw new IllegalArgumentException(
                    "O município da unidade é obrigatório."
            );
        }

        crasDAO.atualizar(cras);
    }

    public void excluir(Cras cras) {
        crasDAO.excluir(cras);
    }

    public Cras buscarPorId(Long id) {
        return crasDAO.buscarPorID(id);
    }

    public List<Cras> listarTodos() {
        return crasDAO.buscarTodos();
    }
}
