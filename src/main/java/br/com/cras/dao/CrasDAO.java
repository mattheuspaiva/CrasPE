package br.com.cras.dao;

import br.com.cras.model.Cras;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CrasDAO {

    public void salvar(Cras cras){
        Transaction transacao = null;

        try(Session sessao = HibernateUtil.getSessionFactory().openSession()){
            transacao = sessao.beginTransaction();
            sessao.persist(cras);
            transacao.commit();

        }catch(Exception e){
            if(transacao != null && transacao.isActive()){
                transacao.rollback();
            }
            throw e;
        }
    }

    public Cras buscarPorID(Long id){
        try(Session sessao = HibernateUtil.getSessionFactory().openSession()){
            return sessao.get(Cras.class, id);
        }
    }

    public List<Cras> buscarTodos(){
        try(Session sessao = HibernateUtil.getSessionFactory().openSession()){
            return sessao.createQuery("FROM Cras").getResultList();
        }
    }
    public void atualizar(Cras cras){
        Transaction transacao = null;
        try(Session sessao = HibernateUtil.getSessionFactory().openSession()){
            transacao = sessao.beginTransaction();
            sessao.merge(cras);
            transacao.commit();

        }catch(Exception e){
            if(transacao != null && transacao.isActive()){
                transacao.rollback();
            }
            throw e;
        }
    }
    public void excluir(Cras cras){
        Transaction transacao = null;
        try(Session sessao = HibernateUtil.getSessionFactory().openSession()){
            transacao = sessao.beginTransaction();
            Cras entidade = sessao.merge(cras);
            sessao.remove(entidade);
            transacao.commit();

        }catch(Exception e){
            if(transacao != null && transacao.isActive()){
                transacao.rollback();
            }
            throw e;
        }
    }
}
