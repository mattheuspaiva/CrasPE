package br.com.cras;

import br.com.cras.util.HibernateUtil;
import br.com.cras.view.MenuView;

public class Main {

    public static void main(String[] args) {
        try {
            MenuView menu = new MenuView();
            menu.exibir();
        } finally {
            HibernateUtil.fechar();
        }
    }
}