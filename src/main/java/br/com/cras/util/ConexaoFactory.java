package br.com.cras.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=CrasPE;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

    private static final String USUARIO = "CrasPE_App";
    private static final String SENHA = "12345";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL, USUARIO, SENHA
        );
    }
}
