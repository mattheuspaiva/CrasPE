package br.com.cras.service;

import br.com.cras.dao.CrasOriginalDAO;
import br.com.cras.model.CrasOriginal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImportacaoCSVService {

    private static final int TOTAL_COLUNAS = 15;

    private final CrasOriginalDAO dao;

    public ImportacaoCSVService() {
        this.dao = new CrasOriginalDAO();
    }

    public int importar(String caminhoArquivo)
            throws IOException {

        Path caminho = Path.of(caminhoArquivo);

        if (!Files.isRegularFile(caminho)) {
            throw new IOException(
                    "Arquivo não encontrado: " + caminhoArquivo
            );
        }

        int importados = 0;
        int linhaAtual = 0;

        try (BufferedReader leitor = Files.newBufferedReader(
                caminho, StandardCharsets.UTF_8)) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                linhaAtual++;

                if (linhaAtual == 1
                        && linha.startsWith("\uFEFF")) {
                    linha = linha.substring(1);
                }

                if (linha.isBlank()) {
                    continue;
                }

                List<String> colunas = separarCSV(linha);

                // Ignora o cabeçalho.
                if (linhaAtual == 1
                        && colunas.get(0)
                        .trim()
                        .equalsIgnoreCase("id_equipamento")) {
                    continue;
                }

                if (colunas.size() != TOTAL_COLUNAS) {
                    throw new IOException(
                            "Linha " + linhaAtual
                                    + " possui " + colunas.size()
                                    + " colunas; eram esperadas "
                                    + TOTAL_COLUNAS + "."
                    );
                }

                CrasOriginal original = converter(colunas);

                if (original.getIdEquipamento() == null
                        || original.getIdEquipamento().isBlank()) {
                    throw new IOException(
                            "ID do equipamento vazio na linha "
                                    + linhaAtual
                    );
                }

                dao.salvar(original);
                importados++;
            }
        }

        return importados;
    }

    private CrasOriginal converter(List<String> c) {
        CrasOriginal original = new CrasOriginal();

        original.setIdEquipamento(c.get(0));
        original.setIbge(c.get(1));
        original.setUf(c.get(2));
        original.setCidade(c.get(3));
        original.setNome(c.get(4));
        original.setResponsavel(c.get(5));
        original.setTelefone(c.get(6));
        original.setEndereco(c.get(7));
        original.setNumero(c.get(8));
        original.setComplemento(c.get(9));
        original.setReferencia(c.get(10));
        original.setBairro(c.get(11));
        original.setCep(c.get(12));
        original.setGeorefLocation(c.get(13));
        original.setDataAtualizacao(c.get(14));

        return original;
    }

    private List<String> separarCSV(String linha)
            throws IOException {

        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean entreAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char caractere = linha.charAt(i);

            if (caractere == '"') {
                if (entreAspas
                        && i + 1 < linha.length()
                        && linha.charAt(i + 1) == '"') {

                    campo.append('"');
                    i++;

                } else {
                    entreAspas = !entreAspas;
                }

            } else if (caractere == ',' && !entreAspas) {
                campos.add(campo.toString().trim());
                campo.setLength(0);

            } else {
                campo.append(caractere);
            }
        }

        if (entreAspas) {
            throw new IOException(
                    "Campo CSV com aspas não fechadas."
            );
        }

        campos.add(campo.toString().trim());

        return campos;
    }
}