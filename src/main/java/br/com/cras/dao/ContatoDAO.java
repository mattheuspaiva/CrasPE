
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

    public Contato buscarPorId(Long id) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Contato contato = sessao.get(Contato.class, id);
        sessao.close();

        return contato;
    }

    public List<Contato> listarTodos() {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Contato> contatos = sessao.createQuery("FROM Contato", Contato.class).list();
        sessao.close();

        return contatos;
    }

    public void atualizar(Contato contato) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.merge(contato);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao atualizar contato: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public void excluir(Contato contato) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            Contato contatoEncontrado = sessao.merge(contato);
            sessao.remove(contatoEncontrado);

            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao excluir contato: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }
}
