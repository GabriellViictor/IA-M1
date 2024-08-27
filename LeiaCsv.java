import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeiaCsv {

  public static void main(String[] args) {
    LeiaCsv obj = new LeiaCsv();
    obj.run();
  }

  public void run() {
    String arquivoCSV = "movie_dataset.csv";
    BufferedReader br = null;
    String linha = "";
    String csvDivisor = ",";
    String[] cabeçalho = null; // Vetor para armazenar o cabeçalho
    boolean primeiraLinhaDeDados = true; // Flag para identificar a primeira linha de dados

    try {
        br = new BufferedReader(new FileReader(arquivoCSV));

        // Ler e armazenar o cabeçalho
        if ((linha = br.readLine()) != null) {
            cabeçalho = linha.split(csvDivisor); // Armazenar o cabeçalho no vetor
        }

        // Ler o restante do arquivo
        while ((linha = br.readLine()) != null) {
            if (primeiraLinhaDeDados) {
                // Exibir a primeira linha de dados após o cabeçalho
                System.out.println("Primeira linha de dados após o cabeçalho:");
                System.out.println(linha);
                primeiraLinhaDeDados = false; // Definir a flag para false após exibir a linha
            }
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Exibir o cabeçalho no terminal
    if (cabeçalho != null) {
        System.out.println("Cabeçalho do CSV:");
        for (String coluna : cabeçalho) {
            System.out.print(coluna + " ");
        }
        System.out.println();
    }
  }
}
