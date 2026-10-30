
package br.com.cras.dao;

import br.com.cras.model.Cras;
import br.com.cras.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CrasDAO {

    public void salvar(Cras cras) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.persist(cras);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao salvar CRAS: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public Cras buscarPorID(Long id) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Cras cras = sessao.get(Cras.class, id);
        sessao.close();

        return cras;
    }

    public Cras buscarPorIdEquipamento(String idEquipamento) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Cras> resultados = sessao.createQuery(
                "FROM Cras c WHERE c.idEquipamentoOrigem = :id",
                Cras.class
        ).setParameter("id", idEquipamento).list();

        sessao.close();

        if (resultados.isEmpty()) {
            return null;
        }

        return resultados.get(0);
    }

    public List<Cras> buscarTodos() {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Cras> lista = sessao.createQuery(
                "FROM Cras", Cras.class
        ).list();

        sessao.close();

        return lista;
    }

    public void atualizar(Cras cras) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.merge(cras);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao atualizar CRAS: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public void excluir(Cras cras) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            Cras crasEncontrado = sessao.merge(cras);
            sessao.remove(crasEncontrado);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao excluir CRAS: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }
}
