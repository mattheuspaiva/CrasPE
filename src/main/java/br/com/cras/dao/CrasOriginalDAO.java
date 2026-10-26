
package br.com.cras.dao;

import br.com.cras.model.CrasOriginal;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CrasOriginalDAO {

    public void salvar(CrasOriginal cras) {
        Transaction transacao = null;

        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.persist(cras);
            transacao.commit();
        } catch (Exception erro) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw new RuntimeException(
                    "Erro ao salvar registro original.", erro
            );
        }
    }

    public CrasOriginal buscarPorId(String id) {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            return sessao.get(CrasOriginal.class, id);
        }
    }

    public List<CrasOriginal> listarTodos() {
        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            return sessao.createQuery(
                    "FROM CrasOriginal", CrasOriginal.class
            ).list();
        }
    }

    public void atualizar(CrasOriginal cras) {
        Transaction transacao = null;

        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.merge(cras);
            transacao.commit();
        } catch (Exception erro) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw new RuntimeException(
                    "Erro ao atualizar registro original.", erro
            );
        }
    }

    public void excluir(CrasOriginal cras) {
        Transaction transacao = null;

        try (Session sessao =
                     HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            CrasOriginal gerenciado = sessao.merge(cras);
            sessao.remove(gerenciado);
            transacao.commit();
        } catch (Exception erro) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw new RuntimeException(
                    "Erro ao excluir registro original.", erro
            );
        }
    }
}
