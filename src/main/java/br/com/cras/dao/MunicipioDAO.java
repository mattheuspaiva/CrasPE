
package br.com.cras.dao;

import br.com.cras.model.Municipio;
import br.com.cras.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MunicipioDAO {

    public void salvar(Municipio municipio) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.persist(municipio);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }
            System.out.println("Erro ao salvar município: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public Municipio buscarPorIbge(String ibge) {

        if (ibge == null || ibge.isBlank()) {
            return null;
        }

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Municipio> resultados = sessao.createQuery(
                "FROM Municipio m WHERE m.codigoIbge = :ibge",
                Municipio.class
        ).setParameter("ibge", ibge.trim()).list();

        sessao.close();

        if (resultados.isEmpty()) {
            return null;
        }

        return resultados.get(0);
    }

    public List<Municipio> listarTodos() {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<Municipio> lista = sessao.createQuery(
                "FROM Municipio", Municipio.class
        ).list();

        sessao.close();

        return lista;
    }

}
