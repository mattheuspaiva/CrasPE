
package br.com.cras.dao;

import br.com.cras.model.Endereco;
import br.com.cras.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EnderecoDAO {

    public void salvar(Endereco endereco) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.persist(endereco);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao salvar endereço: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public List<Endereco> listarTodos() {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Endereco> lista = sessao.createQuery(
                "FROM Endereco", Endereco.class
        ).list();
        sessao.close();

        return lista;
    }


}
