package br.com.cras.service;

import br.com.cras.dao.CrasDAO;
import br.com.cras.model.Cras;

import java.util.List;

public class CrasService {

    private final CrasDAO crasDAO;

    public CrasService() {
        this.crasDAO = new CrasDAO();
    }

    public void cadastrar(Cras cras) {
        if (cras == null) {
            throw new IllegalArgumentException(
                    "O CRAS não pode ser nulo."
            );
        }

        if (cras.getNome() == null
                || cras.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do CRAS é obrigatório."
            );
        }

        crasDAO.salvar(cras);
    }

    public void atualizar(Cras cras) {
        if (cras == null || cras.getId() == null) {
            throw new IllegalArgumentException(
                    "Informe um CRAS válido para atualizar."
            );
        }

        crasDAO.atualizar(cras);
    }

    public void excluir(Long id) {
        Cras cras = buscarPorId(id);

        if (cras == null) {
            throw new IllegalArgumentException(
                    "CRAS não encontrado."
            );
        }

        crasDAO.excluir(cras);
    }

    public Cras buscarPorId(Long id) {
        return crasDAO.buscarPorID(id);
    }

    public List<Cras> listarTodos() {
        return crasDAO.buscarTodos();
    }
}