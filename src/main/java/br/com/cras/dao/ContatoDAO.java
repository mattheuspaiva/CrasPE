package br.com.cras.dao;

import br.com.cras.model.Contato;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ContatoDAO {

    public void salvar(Contato contato) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.persist(contato);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }

    public Contato buscarPorId(Long id) {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.get(Contato.class, id);
        }
    }

    public List<Contato> listarTodos() {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.createQuery("FROM Contato", Contato.class).getResultList();
        }
    }

    public void atualizar(Contato contato) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.merge(contato);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }

    public void excluir(Contato contato) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            Contato entidade = sessao.merge(contato);
            sessao.remove(entidade);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }
}