
package br.com.cras.dao;

import br.com.cras.model.Cras;
import br.com.cras.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public class CrasDAO {

    public void salvar(Cras cras) {
        executar(sessao -> sessao.persist(cras));
    }

    public Cras buscarPorID(Long id) {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            return sessao.get(Cras.class, id);
        }
    }

    public Cras buscarPorIdEquipamento(String idEquipamento) {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Cras> resultados = sessao.createQuery(
                    "FROM Cras c WHERE c.idEquipamentoOrigem = :id",
                    Cras.class
            ).setParameter("id", idEquipamento).list();

            return resultados.isEmpty() ? null : resultados.get(0);
        }
    }

    public List<Cras> buscarTodos() {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            return sessao.createQuery(
                    "FROM Cras", Cras.class
            ).list();
        }
    }

    public void atualizar(Cras cras) {
        executar(sessao -> sessao.merge(cras));
    }

    public void excluir(Cras cras) {
        executar(sessao -> {
            Cras gerenciado = sessao.merge(cras);
            sessao.remove(gerenciado);
        });
    }

    private void executar(Consumer<Session> acao) {
        Transaction transacao = null;

        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            transacao = sessao.beginTransaction();
            acao.accept(sessao);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }

            throw new RuntimeException(
                    "Erro ao executar operação de CRAS.", erro
            );
        }
    }
}
