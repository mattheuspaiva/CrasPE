
package br.com.cras.dao;

import br.com.cras.model.Endereco;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EnderecoDAO {

    public void salvar(Endereco endereco) {
        executar(sessao -> sessao.persist(endereco));
    }

    public Endereco buscarPorId(Long id) {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            return sessao.get(Endereco.class, id);
        }
    }

    public List<Endereco> listarTodos() {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            return sessao.createQuery(
                    "FROM Endereco", Endereco.class
            ).list();
        }
    }

    public void atualizar(Endereco endereco) {
        executar(sessao -> sessao.merge(endereco));
    }

    public void excluir(Endereco endereco) {
        executar(sessao -> {
            Endereco gerenciado = sessao.merge(endereco);
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
                    "Erro ao executar operação de endereço.", erro
            );
        }
    }
}
