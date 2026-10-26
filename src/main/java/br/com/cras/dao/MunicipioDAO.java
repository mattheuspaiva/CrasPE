
package br.com.cras.dao;

import br.com.cras.model.Municipio;
import br.com.cras.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public class MunicipioDAO {

    public void salvar(Municipio municipio) {
        executarTransacao(sessao -> sessao.persist(municipio));
    }

    public Municipio buscarPorId(Long id) {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            return sessao.get(Municipio.class, id);
        }
    }

    public Municipio buscarPorIbge(String ibge) {

        if (ibge == null || ibge.isBlank()) {
            return null;
        }

        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Municipio> resultados = sessao.createQuery(
                    "FROM Municipio m WHERE m.codigoIbge = :ibge",
                    Municipio.class
            ).setParameter("ibge", ibge.trim()).list();

            return resultados.isEmpty() ? null : resultados.get(0);
        }
    }

    public List<Municipio> listarTodos() {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {

            return sessao.createQuery(
                    "FROM Municipio", Municipio.class
            ).list();
        }
    }

    public void atualizar(Municipio municipio) {
        executarTransacao(sessao -> sessao.merge(municipio));
    }

    public void excluir(Municipio municipio) {
        executarTransacao(sessao -> {
            Municipio gerenciado = sessao.merge(municipio);
            sessao.remove(gerenciado);
        });
    }

    private void executarTransacao(Consumer<Session> acao) {

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
                    "Erro ao executar operação de município.", erro
            );
        }
    }
}
