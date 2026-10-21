package br.com.cras;
import br.com.cras.util.HibernateUtil;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        try (Session sessao = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Hibernate conectado com sucesso ao banco CrasPE!");
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o Hibernate.");
            e.printStackTrace();
        } finally {
            HibernateUtil.fechar();
        }

    }
}
