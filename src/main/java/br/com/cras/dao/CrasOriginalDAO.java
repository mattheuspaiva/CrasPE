
package br.com.cras.dao;

import br.com.cras.model.CrasOriginal;
import br.com.cras.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CrasOriginalDAO {

    public void salvar(CrasOriginal cras) {

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

            System.out.println("Erro ao salvar registro original: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public CrasOriginal buscarPorId(String id) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        CrasOriginal cras = sessao.get(CrasOriginal.class, id);
        sessao.close();

        return cras;
    }

    public List<CrasOriginal> listarTodos() {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<CrasOriginal> lista = sessao.createQuery(
                "FROM CrasOriginal", CrasOriginal.class
        ).list();
        sessao.close();

        return lista;
    }

}