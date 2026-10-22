package br.com.cras.dao;

import br.com.cras.model.CrasOriginal;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CrasOriginalDAO {

    public void salvar(CrasOriginal crasOriginal) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.persist(crasOriginal);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }

    public CrasOriginal buscarPorId(String idEquipamento) {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.get(CrasOriginal.class, idEquipamento
            );
        }
    }

    public List<CrasOriginal> listarTodos() {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            return sessao.createQuery("FROM CrasOriginal", CrasOriginal.class).getResultList();
        }
    }

    public void atualizar(CrasOriginal crasOriginal) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            sessao.merge(crasOriginal);
            transacao.commit();

        } catch (RuntimeException e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }
            throw e;
        }
    }

    public void excluir(CrasOriginal crasOriginal) {
        Transaction transacao = null;
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            transacao = sessao.beginTransaction();
            CrasOriginal entidade = sessao.merge(crasOriginal);
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