package br.com.cras.dao;

import br.com.cras.model.Municipio;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MunicipioDAO {

    public void salvar(Municipio municipio) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.persist(municipio);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }

    public Municipio buscarPorId(Long id) {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.get(Municipio.class, id);
        }
    }

    public List<Municipio> listarTodos() {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.createQuery("FROM Municipio", Municipio.class).getResultList();
        }
    }

    public void atualizar(Municipio municipio) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.merge(municipio);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }

    public void excluir(Municipio municipio) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            Municipio entidade = sessao.merge(municipio);
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