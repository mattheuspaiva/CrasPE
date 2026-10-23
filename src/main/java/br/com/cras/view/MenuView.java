package br.com.cras.view;

import br.com.cras.dao.CrasOriginalDAO;
import br.com.cras.model.Cras;
import br.com.cras.model.CrasOriginal;
import br.com.cras.service.CrasService;
import br.com.cras.service.ImportacaoCSVService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final Scanner scanner;
    private final CrasService crasService;
    private final ImportacaoCSVService importacaoService;
    private final CrasOriginalDAO originalDAO;

    public MenuView() {
        scanner = new Scanner(System.in);
        crasService = new CrasService();
        importacaoService = new ImportacaoCSVService();
        originalDAO = new CrasOriginalDAO();
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n================================");
            System.out.println("       SISTEMA CRAS PERNAMBUCO");
            System.out.println("================================");
            System.out.println("1 - Importar CSV original");
            System.out.println("2 - Listar dados originais");
            System.out.println("3 - Listar unidades CRAS");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1 -> importarCSV();
                    case 2 -> listarOriginais();
                    case 3 -> listarCras();
                    case 0 -> System.out.println(
                            "Encerrando o sistema..."
                    );
                    default -> System.out.println(
                            "Opção inválida."
                    );
                }
            } catch (Exception e) {
                System.out.println(
                        "Erro: " + e.getMessage()
                );
            }

        } while (opcao != 0);
    }

    private void importarCSV() throws IOException {
        System.out.print(
                "Caminho do CSV [dados/cras_pernambuco.csv]: "
        );

        String caminho = scanner.nextLine().trim();

        if (caminho.isBlank()) {
            caminho = "dados/cras_pernambuco.csv";
        }

        int quantidade = importacaoService.importar(caminho);

        System.out.println(
                "Importação concluída. Registros processados: "
                        + quantidade
        );
    }

    private void listarOriginais() {
        List<CrasOriginal> registros =
                originalDAO.listarTodos();

        if (registros.isEmpty()) {
            System.out.println(
                    "Nenhum registro original encontrado."
            );
            return;
        }

        for (CrasOriginal c : registros) {
            System.out.println("------------------------------");
            System.out.println(
                    "ID equipamento: " + c.getIdEquipamento()
            );
            System.out.println("Nome: " + c.getNome());
            System.out.println("Município: " + c.getCidade());
            System.out.println("UF: " + c.getUf());
            System.out.println("IBGE: " + c.getIbge());
            System.out.println(
                    "Responsável: " + c.getResponsavel()
            );
            System.out.println("Telefone: " + c.getTelefone());
            System.out.println("Endereço: " + c.getEndereco());
            System.out.println("Número: " + c.getNumero());
            System.out.println("Bairro: " + c.getBairro());
            System.out.println("CEP: " + c.getCep());
        }

        System.out.println(
                "Total de registros: " + registros.size()
        );
    }

    private void listarCras() {
        List<Cras> registros = crasService.listarTodos();

        if (registros.isEmpty()) {
            System.out.println(
                    "Nenhuma unidade cadastrada na tabela Cras."
            );
            return;
        }

        for (Cras cras : registros) {
            System.out.println("------------------------------");
            System.out.println("ID: " + cras.getId());
            System.out.println(
                    "Equipamento: " + cras.getIdEquipamento()
            );
            System.out.println("Nome: " + cras.getNome());

            if (cras.getMunicipio() != null) {
                System.out.println(
                        "Município: "
                                + cras.getMunicipio().getCidade()
                );
            }
        }

        System.out.println(
                "Total de unidades: " + registros.size()
        );
    }

    private int lerInteiro() {
        String entrada = scanner.nextLine().trim();

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}