import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class LeiaCsv {

    private List<String> dados = new ArrayList<>();
    private String cabecalho;

    public List<String> getDados() {
        return dados;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void run() {
        String arquivoCSV = "movies_dataset.csv";
        BufferedReader br = null;
        String linha = "";

        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            String header = br.readLine();
            String splitheder[] = header.split(";");
    

            if ((linha = br.readLine()) != null) {
                cabecalho = linha;
            }

            while ((linha = br.readLine()) != null) {
                dados.add(linha);
            }

            //CODIGO PROFESSOR
			for (int i = 0; i < splitheder.length;i++) {
				System.out.println(""+i+" "+splitheder[i]);
			}
			
			String line = "";
			
			while((line=br.readLine())!=null) {
				String spl[] = line.split(";");
				HashMap<String,Float> asVariaveis = new HashMap<String,Float>();
				
			}
            //=



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
    }

    public void mostrarLinha(int indice) {
        if (indice < 0 || indice >= dados.size()) {
            System.out.println("Índice fora do intervalo.");
            return;
        }

        String linha = dados.get(indice);
        System.out.println("Linha " + (indice) + ": " + linha);
    }
}
