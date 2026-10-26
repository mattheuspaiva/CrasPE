
package br.com.cras.service;

import br.com.cras.dao.CrasOriginalDAO;
import br.com.cras.model.CrasOriginal;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImportacaoCSVService {

    private final CrasOriginalDAO originalDAO = new CrasOriginalDAO();

    public int importar(String caminho) throws IOException {
        int importados = 0;
        int ignorados = 0;

        Path arquivo = Path.of(caminho);

        CSVFormat formato = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .get();

        try (var leitor =
                     Files.newBufferedReader(arquivo, StandardCharsets.UTF_8);
             CSVParser parser = formato.parse(leitor)) {

            for (CSVRecord registro : parser) {
                String id = limpar(registro.get("id_equipamento"));

                if (id == null) {
                    System.out.println(
                            "Registro ignorado: ID do equipamento vazio."
                    );
                    ignorados++;
                    continue;
                }

                if (originalDAO.buscarPorId(id) != null) {
                    ignorados++;
                    continue;
                }

                CrasOriginal cras = new CrasOriginal();

                cras.setIdEquipamento(id);
                cras.setIbge(limpar(registro.get("ibge")));
                cras.setUf(limpar(registro.get("uf")));
                cras.setCidade(limpar(registro.get("cidade")));
                cras.setNome(limpar(registro.get("nome")));
                cras.setResponsavel(limpar(registro.get("responsavel")));
                cras.setTelefone(limpar(registro.get("telefone")));
                cras.setEndereco(limpar(registro.get("endereco")));
                cras.setNumero(limpar(registro.get("numero")));
                cras.setComplemento(limpar(registro.get("complemento")));
                cras.setReferencia(limpar(registro.get("referencia")));
                cras.setBairro(limpar(registro.get("bairro")));
                cras.setCep(limpar(registro.get("cep")));
                cras.setGeorefLocation(
                        limpar(registro.get("georef_location"))
                );
                cras.setDataAtualizacao(
                        limpar(registro.get("data_atualizacao"))
                );

                originalDAO.salvar(cras);
                importados++;
            }
        }

        System.out.println("Novos registros importados: " + importados);
        System.out.println("Registros ignorados: " + ignorados);

        return importados;
    }

    private String limpar(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        return valor.trim();
    }
}
