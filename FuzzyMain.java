import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FuzzyMain {
    public static void main(String[] args) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("movie_dataset.csv")));
            
            // Armazenar o cabeçalho para uso posterior
            String header = bfr.readLine();
            String[] splitHeader = header.split(",");  // Certifique-se de que este é o separador correto
            
            // List para armazenar todas as linhas do arquivo (exceto o cabeçalho)
            ArrayList<HashMap<String, String>> data = new ArrayList<>();
            
            String line = "";
            while ((line = bfr.readLine()) != null) {
                String[] spl = line.split(",");  // Certifique-se de que este é o separador correto
                
                if (spl.length == splitHeader.length) {
                    // Criar um HashMap para armazenar a linha
                    HashMap<String, String> asVariaveis = new HashMap<>();
                    
                    for (int i = 0; i < spl.length; i++) {
                        asVariaveis.put(splitHeader[i], spl[i]);
                    }
                    
                    data.add(asVariaveis);
                } else {
                    System.out.println("Inconsistência encontrada na linha: " + line);
                }
            }
            
            bfr.close();
            
            // Exibir a primeira linha de dados
            if (!data.isEmpty()) {
                System.out.println("Cabeçalho: " + header);
                System.out.println("Primeira linha de dados: " + data.get(0));
            } else {
                System.out.println("Nenhuma linha de dados disponível.");
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
