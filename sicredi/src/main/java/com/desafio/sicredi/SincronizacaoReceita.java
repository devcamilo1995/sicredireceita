package com.desafio.sicredi;

import com.desafio.sicredi.model.CsvEntity;
import com.desafio.sicredi.service.ReceitaService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SincronizacaoReceita {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SincronizacaoReceita.class, args);
        ReceitaService receitaService = new ReceitaService();

        if (args.length != 0) {
            Reader reader = Files.newBufferedReader(Paths.get(args[0]));

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withSeparator(';')
                    .withType(CsvEntity.class)
                    .build();

            List<CsvEntity> contas = csvToBean.parse();
            List<String[]> resultados = new ArrayList<>();

            String[] csvResultado = new String[5];
            csvResultado[0] = "agencia";
            csvResultado[1] = "conta";
            csvResultado[2] = "saldo";
            csvResultado[3] = "status";
            csvResultado[4] = "resultado";
            resultados.add(csvResultado);

            for (CsvEntity conta : contas) {
                try {
                    boolean atualizarConta = receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), conta.getSaldo(), conta.getStatus());

                    resultados.add(conta.toString(String.valueOf(atualizarConta)));
                } catch (RuntimeException ex) {
                    resultados.add(conta.toString(ex.getMessage()));
                } catch (Exception ex) {
                    resultados.add(conta.toString(ex.getMessage()));
                }

            }
            writeAllLines(resultados);
        } else {

            System.out.println("Error");

        }
    }


    public static void writeAllLines(List<String[]> lines) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter("resultado.csv"))) {
            writer.writeAll(lines);
        }
    }
}