
package br.com.cras.dao;

import br.com.cras.model.Contato;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ContatoDAO {

    public void salvar(Contato contato) {
        executar(sessao -> sessao.persist(contato));
    }

    public Contato buscarPorId(Long id) {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.get(Contato.class, id);
        }
    }

    public List<Contato> listarTodos() {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.createQuery("FROM Contato", Contato.class).list();
        }
    }

    public void atualizar(Contato contato) {
        executar(sessao -> sessao.merge(contato));
    }

    public void excluir(Contato contato) {
        executar(sessao -> {
            Contato gerenciado = sessao.merge(contato);
            sessao.remove(gerenciado);
        });
    }

    private void executar(
            java.util.function.Consumer<Session> acao) {
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
                    "Erro ao executar operação de contato.", erro
            );
        }
    }
}
