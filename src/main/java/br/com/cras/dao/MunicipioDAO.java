
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

    public Municipio buscarPorId(Long id) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Municipio municipio = sessao.get(Municipio.class, id);
        sessao.close();

        return municipio;
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

    public void atualizar(Municipio municipio) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.merge(municipio);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao atualizar município: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }

    public void excluir(Municipio municipio) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            Municipio municipioEncontrado = sessao.merge(municipio);
            sessao.remove(municipioEncontrado);
            transacao.commit();

        } catch (Exception erro) {

            if (transacao != null) {
                transacao.rollback();
            }

            System.out.println("Erro ao excluir município: " + erro.getMessage());

        } finally {
            sessao.close();
        }
    }
}
