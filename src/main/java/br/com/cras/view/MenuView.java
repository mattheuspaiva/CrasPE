
package br.com.cras.view;

import br.com.cras.dao.ContatoDAO;
import br.com.cras.dao.CrasOriginalDAO;
import br.com.cras.dao.EnderecoDAO;
import br.com.cras.model.Contato;
import br.com.cras.model.Cras;
import br.com.cras.model.CrasOriginal;
import br.com.cras.model.Endereco;
import br.com.cras.service.CrasService;
import br.com.cras.service.ImportacaoCSVService;
import br.com.cras.service.NormalizacaoService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final Scanner scanner = new Scanner(System.in);

    private final CrasService crasService = new CrasService();

    private final ImportacaoCSVService importacaoService =
            new ImportacaoCSVService();

    private final NormalizacaoService normalizacaoService =
            new NormalizacaoService();

    private final CrasOriginalDAO originalDAO =
            new CrasOriginalDAO();

    private final EnderecoDAO enderecoDAO =
            new EnderecoDAO();

    private final ContatoDAO contatoDAO =
            new ContatoDAO();

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA CRAS PERNAMBUCO");
            System.out.println("========================================");
            System.out.println("1 - Importar CSV original");
            System.out.println("2 - Listar dados originais");
            System.out.println("3 - Normalizar dados");
            System.out.println("4 - Listar dados normalizados");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1 -> importarCSV();
                    case 2 -> listarOriginais();
                    case 3 -> normalizar();
                    case 4 -> listarNormalizados();
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println(
                        "Erro durante a operação: " + e.getMessage()
                );
                e.printStackTrace();
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
                "Novos registros importados: " + quantidade
        );
    }

    private void listarOriginais() {
        List<CrasOriginal> registros = originalDAO.listarTodos();

        if (registros.isEmpty()) {
            System.out.println("Nenhum registro original encontrado.");
            return;
        }

        System.out.println("\n========== DADOS ORIGINAIS ==========");

        for (CrasOriginal c : registros) {
            System.out.println("----------------------------------------");
            System.out.println("ID equipamento: " + c.getIdEquipamento());
            System.out.println("Nome: " + c.getNome());
            System.out.println("Município: " + c.getCidade());
            System.out.println("UF: " + c.getUf());
            System.out.println("IBGE: " + c.getIbge());
            System.out.println("Responsável: " + c.getResponsavel());
            System.out.println("Telefone: " + c.getTelefone());
            System.out.println("Endereço original: " + c.getEndereco());
            System.out.println("Número: " + c.getNumero());
            System.out.println("Complemento: " + c.getComplemento());
            System.out.println("Referência: " + c.getReferencia());
            System.out.println("Bairro: " + c.getBairro());
            System.out.println("CEP: " + c.getCep());
            System.out.println(
                    "Localização geográfica: " + c.getGeorefLocation()
            );
            System.out.println(
                    "Data de atualização: " + c.getDataAtualizacao()
            );
        }

        System.out.println("----------------------------------------");
        System.out.println("Total de registros originais: " + registros.size());
    }

    private void normalizar() {
        System.out.println(
                "Iniciando a normalização dos dados originais..."
        );

        int quantidade = normalizacaoService.normalizar();

        System.out.println(
                "Normalização concluída. Novas unidades: " + quantidade
        );
    }

    private void listarNormalizados() {
        List<Cras> crasList = crasService.listarTodos();
        List<Endereco> enderecos = enderecoDAO.listarTodos();
        List<Contato> contatos = contatoDAO.listarTodos();

        if (crasList.isEmpty()) {
            System.out.println(
                    "Nenhum dado normalizado encontrado. "
                            + "Execute primeiro a opção 3."
            );
            return;
        }

        System.out.println("\n========== DADOS NORMALIZADOS ==========");

        for (Cras cras : crasList) {
            System.out.println("\n----------------------------------------");
            System.out.println("ID do CRAS: " + cras.getId());
            System.out.println(
                    "ID equipamento original: "
                            + cras.getIdEquipamentoOrigem()
            );
            System.out.println("Nome: " + cras.getNome());
            System.out.println("Responsável: " + cras.getResponsavel());

            if (cras.getMunicipio() != null) {
                System.out.println(
                        "Município: " + cras.getMunicipio().getNome()
                );
                System.out.println(
                        "UF: " + cras.getMunicipio().getUf()
                );
                System.out.println(
                        "Código IBGE: "
                                + cras.getMunicipio().getCodigoIbge()
                );
            }

            System.out.println(
                    "Data de atualização: " + cras.getDataAtualizacao()
            );

            System.out.println("Endereços:");

            boolean encontrouEndereco = false;

            for (Endereco endereco : enderecos) {
                if (endereco.getCras() != null
                        && endereco.getCras().getId().equals(cras.getId())) {

                    encontrouEndereco = true;

                    System.out.println(
                            "  Logradouro: " + endereco.getLogradouro()
                    );
                    System.out.println(
                            "  Número: " + endereco.getNumero()
                    );
                    System.out.println(
                            "  Complemento: " + endereco.getComplemento()
                    );
                    System.out.println(
                            "  Referência: " + endereco.getReferencia()
                    );
                    System.out.println(
                            "  Bairro: " + endereco.getBairro()
                    );
                    System.out.println(
                            "  CEP: " + endereco.getCep()
                    );
                    System.out.println(
                            "  Latitude: " + endereco.getLatitude()
                    );
                    System.out.println(
                            "  Longitude: " + endereco.getLongitude()
                    );
                }
            }

            if (!encontrouEndereco) {
                System.out.println("  Nenhum endereço cadastrado.");
            }

            System.out.println("Contatos:");

            boolean encontrouContato = false;

            for (Contato contato : contatos) {
                if (contato.getCras() != null
                        && contato.getCras().getId().equals(cras.getId())) {

                    encontrouContato = true;
                    System.out.println(
                            "  Telefone: " + contato.getTelefone()
                    );
                }
            }

            if (!encontrouContato) {
                System.out.println("  Nenhum telefone cadastrado.");
            }
        }

        System.out.println("\n========== RESUMO ==========");
        System.out.println("Total de CRAS: " + crasList.size());
        System.out.println("Total de endereços: " + enderecos.size());
        System.out.println("Total de contatos: " + contatos.size());
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
