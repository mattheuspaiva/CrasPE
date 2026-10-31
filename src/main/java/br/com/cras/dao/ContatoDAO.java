
package br.com.cras.dao;

import br.com.cras.model.Contato;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ContatoDAO {

    public void salvar(Contato contato) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.persist(contato);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao salvar contato: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public List<Contato> listarTodos() {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Contato> contatos = sessao.createQuery("FROM Contato", Contato.class).list();
        sessao.close();

        return contatos;
    }


}
