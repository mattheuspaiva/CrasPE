package br.com.cras.service;

import br.com.cras.dao.ContatoDAO;
import br.com.cras.dao.CrasDAO;
import br.com.cras.dao.CrasOriginalDAO;
import br.com.cras.dao.EnderecoDAO;
import br.com.cras.dao.MunicipioDAO;

import br.com.cras.model.Contato;
import br.com.cras.model.Cras;
import br.com.cras.model.CrasOriginal;
import br.com.cras.model.Endereco;
import br.com.cras.model.Municipio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class NormalizacaoService {

    private final CrasOriginalDAO originalDAO = new CrasOriginalDAO();
    private final CrasDAO crasDAO = new CrasDAO();
    private final MunicipioDAO municipioDAO = new MunicipioDAO();
    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public int normalizar() {

        List<CrasOriginal> originais = originalDAO.listarTodos();
        int processados = 0;

        for (CrasOriginal original : originais) {

            String id = limpar(original.getIdEquipamento());

            if (id.isEmpty()) {
                System.out.println("Registro ignorado: ID do equipamento vazio.");
                continue;
            }

            if (crasDAO.buscarPorIdEquipamento(id) != null) {
                continue;
            }

            String nome = limpar(original.getNome());

            if (nome.isEmpty()) {
                System.out.println(
                        "CRAS ignorado por falta de nome: " + id
                );
                continue;
            }

            Municipio municipio = obterMunicipio(original);

            if (municipio == null) {
                System.out.println(
                        "CRAS ignorado por falta de município válido: " + id
                );
                continue;
            }

            // Cria o CRAS com os dados principais.
            Cras cras = new Cras();

            cras.setIdEquipamentoOrigem(id);
            cras.setNome(nome);
            cras.setResponsavel(
                    nuloSeVazio(original.getResponsavel())
            );
            cras.setDataAtualizacao(
                    converterData(original.getDataAtualizacao())
            );
            cras.setMunicipio(municipio);

            crasDAO.salvar(cras);

            // Cria o endereço associado ao CRAS.
            Endereco endereco = obterEndereco(original, cras);

            if (endereco != null) {
                enderecoDAO.salvar(endereco);
            }

            // Cria o contato associado ao CRAS.
            Contato contato = obterContato(original, cras);

            if (contato != null) {
                contatoDAO.salvar(contato);
            }

            processados++;
        }

        System.out.println(
                "Novas unidades normalizadas: " + processados
        );

        return processados;
    }

    private Municipio obterMunicipio(CrasOriginal original) {

        String ibge = limpar(original.getIbge());
        String cidade = limpar(original.getCidade());
        String uf = limpar(original.getUf());

        // Primeiro, tenta localizar pelo código IBGE.
        if (!ibge.isEmpty()) {

            Municipio municipio = municipioDAO.buscarPorIbge(ibge);

            if (municipio != null) {
                return municipio;
            }
        }

        // Se não encontrar, tenta localizar pelo nome e UF.
        for (Municipio existente : municipioDAO.listarTodos()) {

            if (igual(existente.getNome(), cidade)
                    && igual(existente.getUf(), uf)) {
                return existente;
            }
        }

        // Não cria município sem os dados obrigatórios.
        if (cidade.isEmpty() || uf.isEmpty() || ibge.isEmpty()) {
            return null;
        }

        // Cria um novo município.
        Municipio municipio = new Municipio();

        municipio.setCodigoIbge(ibge);
        municipio.setNome(cidade);
        municipio.setUf(uf);

        municipioDAO.salvar(municipio);

        return municipio;
    }



    private Endereco obterEndereco(
            CrasOriginal original, Cras cras) {

        String logradouro = limpar(original.getEndereco());
        String numero = limpar(original.getNumero());
        String complemento = limpar(original.getComplemento());
        String referencia = limpar(original.getReferencia());
        String bairro = limpar(original.getBairro());
        String cep = limpar(original.getCep());

        // Remove o número que aparece no final do logradouro.
        // Exemplo: "ANTONIO BISPÓ - 0" -> "ANTONIO BISPÓ".
        if (!logradouro.isEmpty()) {
            int posicao = logradouro.lastIndexOf(" - ");

            if (posicao >= 0) {
                String parteFinal = logradouro
                        .substring(posicao + 3)
                        .trim();

                if (parteFinal.matches("\\d+")) {
                    logradouro = logradouro
                            .substring(0, posicao)
                            .trim();
                }
            }
        }

        BigDecimal[] coordenadas =
                converterCoordenadas(original.getGeorefLocation());

        // Não cria endereço se todos os campos estiverem vazios.
        if (logradouro.isEmpty()
                && numero.isEmpty()
                && complemento.isEmpty()
                && referencia.isEmpty()
                && bairro.isEmpty()
                && cep.isEmpty()
                && coordenadas[0] == null
                && coordenadas[1] == null) {
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.setCras(cras);
        endereco.setLogradouro(nuloSeVazio(logradouro));
        endereco.setNumero(nuloSeVazio(numero));
        endereco.setComplemento(nuloSeVazio(complemento));
        endereco.setReferencia(nuloSeVazio(referencia));
        endereco.setBairro(nuloSeVazio(bairro));
        endereco.setCep(nuloSeVazio(cep));
        endereco.setLatitude(coordenadas[0]);
        endereco.setLongitude(coordenadas[1]);

        return endereco;
    }



    private Contato obterContato(
            CrasOriginal original, Cras cras) {

        String telefone = limpar(original.getTelefone());

        // Não cria contato sem telefone.
        if (telefone.isEmpty()) {
            return null;
        }

        Contato contato = new Contato();

        contato.setCras(cras);
        contato.setTelefone(telefone);

        return contato;
    }

    private BigDecimal[] converterCoordenadas(String valor) {

        BigDecimal latitude = null;
        BigDecimal longitude = null;

        if (valor != null && !valor.isBlank()) {

            // Aceita coordenadas com ou sem barra invertida.
            String coordenadasTexto = valor.trim().replace("\\", "");
            String[] partes = coordenadasTexto.split(",");

            if (partes.length == 2) {

                try {
                    latitude = new BigDecimal(partes[0].trim());
                    longitude = new BigDecimal(partes[1].trim());

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Coordenadas inválidas: " + valor
                    );
                }

            } else {
                System.out.println(
                        "Formato de coordenadas inválido: " + valor
                );
            }
        }

        return new BigDecimal[] { latitude, longitude };
    }

    private LocalDateTime converterData(String valor) {

        if (valor == null || valor.isBlank()) {
            return null;
        }

        String data = valor.trim();

        // Tenta converter uma data ISO com fuso horário.
        try {
            return OffsetDateTime.parse(data).toLocalDateTime();

        } catch (DateTimeParseException ignored) {
            // Continua para a próxima tentativa.
        }

        // Tenta converter uma data e hora sem fuso.
        try {
            return LocalDateTime.parse(data);

        } catch (DateTimeParseException ignored) {
            // Continua para a próxima tentativa.
        }

        // Tenta aproveitar somente a parte da data.
        try {
            return java.time.LocalDate.parse(
                    data.substring(0, 10)
            ).atStartOfDay();

        } catch (RuntimeException ignored) {
            System.out.println("Data inválida: " + data);
            return null;
        }
    }

    private String limpar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private String nuloSeVazio(String valor) {
        return valor == null || valor.isBlank()
                ? null : valor.trim();
    }

    private boolean igual(String a, String b) {
        return limpar(a).equalsIgnoreCase(limpar(b));
    }
}
