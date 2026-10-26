
package br.com.cras.service;

import br.com.cras.dao.CrasOriginalDAO;
import br.com.cras.model.CrasOriginal;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ValidacaoDadosService {

    private final CrasOriginalDAO originalDAO = new CrasOriginalDAO();

    public void analisarDadosOriginais() {
        List<CrasOriginal> registros = originalDAO.listarTodos();

        int incompletos = 0;
        int telefonesSuspeitos = 0;
        int cepsSuspeitos = 0;

        Map<String, String> nomesVistos = new HashMap<>();

        for (CrasOriginal c : registros) {
            boolean incompleto = false;

            if (vazio(c.getNome())) {
                System.out.println("Nome ausente: " + c.getIdEquipamento());
                incompleto = true;
            }

            if (vazio(c.getIbge()) || vazio(c.getCidade())) {
                System.out.println(
                        "Município incompleto: " + c.getIdEquipamento()
                );
                incompleto = true;
            }

            if (vazio(c.getEndereco())) {
                System.out.println(
                        "Endereço ausente: " + c.getIdEquipamento()
                );
                incompleto = true;
            }

            if (incompleto) {
                incompletos++;
            }

            if (!vazio(c.getTelefone())) {
                String digitos = c.getTelefone().replaceAll("\\D", "");

                if (digitos.length() != 10 && digitos.length() != 11) {
                    System.out.println(
                            "Telefone suspeito: " + c.getIdEquipamento()
                    );
                    telefonesSuspeitos++;
                }
            }

            if (!vazio(c.getCep())) {
                String digitos = c.getCep().replaceAll("\\D", "");

                if (digitos.length() != 8) {
                    System.out.println(
                            "CEP suspeito: " + c.getIdEquipamento()
                    );
                    cepsSuspeitos++;
                }
            }

            String chave = normalizar(c.getNome())
                    + "|" + normalizar(c.getCidade())
                    + "|" + normalizar(c.getUf());

            if (!vazio(c.getNome()) && !vazio(c.getCidade())) {
                String anterior = nomesVistos.putIfAbsent(
                        chave, c.getIdEquipamento()
                );

                if (anterior != null) {
                    System.out.println(
                            "Possíveis registros semelhantes: "
                                    + anterior + " e " + c.getIdEquipamento()
                    );
                }
            }
        }

        System.out.println("\n=== RESUMO DA VALIDACAO ===");
        System.out.println("Registros analisados: " + registros.size());
        System.out.println("Registros incompletos: " + incompletos);
        System.out.println("Telefones suspeitos: " + telefonesSuspeitos);
        System.out.println("CEPs suspeitos: " + cepsSuspeitos);
    }

    public int contarCamposPreenchidos(CrasOriginal c) {
        int total = 0;

        if (!vazio(c.getNome())) total++;
        if (!vazio(c.getIbge())) total++;
        if (!vazio(c.getCidade())) total++;
        if (!vazio(c.getUf())) total++;
        if (!vazio(c.getResponsavel())) total++;
        if (!vazio(c.getTelefone())) total++;
        if (!vazio(c.getEndereco())) total++;
        if (!vazio(c.getNumero())) total++;
        if (!vazio(c.getComplemento())) total++;
        if (!vazio(c.getReferencia())) total++;
        if (!vazio(c.getBairro())) total++;
        if (!vazio(c.getCep())) total++;
        if (!vazio(c.getGeorefLocation())) total++;
        if (!vazio(c.getDataAtualizacao())) total++;

        return total;
    }

    public void compararCompletude(
            CrasOriginal a, CrasOriginal b) {

        System.out.println(
                a.getIdEquipamento() + ": "
                        + contarCamposPreenchidos(a) + " campos preenchidos"
        );

        System.out.println(
                b.getIdEquipamento() + ": "
                        + contarCamposPreenchidos(b) + " campos preenchidos"
        );
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);
    }
}
