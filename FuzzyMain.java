import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class FuzzyMain {
	public static void main(String[] args) {

		GrupoVariaveis grupoVoteAvg = new GrupoVariaveis();
		grupoVoteAvg.add(new VariavelFuzzy("Muito RuimQ", 0, 0, 10, 30));
		grupoVoteAvg.add(new VariavelFuzzy("RuimQ", 20, 30, 40, 50));
		grupoVoteAvg.add(new VariavelFuzzy("MedianoQ", 40, 50, 60, 70));
		grupoVoteAvg.add(new VariavelFuzzy("BomQ", 60, 70, 80, 90));
		grupoVoteAvg.add(new VariavelFuzzy("Muito BomQ", 80, 90, 100, 100));

		GrupoVariaveis grupoRunTime = new GrupoVariaveis();
		grupoRunTime.add(new VariavelFuzzy("CurtoD", 0, 0, 75, 90));
		grupoRunTime.add(new VariavelFuzzy("IdealD", 75, 90, 140, 150));
		grupoRunTime.add(new VariavelFuzzy("LongoD", 140, 150, Float.MAX_VALUE, Float.MAX_VALUE));

		GrupoVariaveis grupoProfit = new GrupoVariaveis();
		grupoProfit.add(new VariavelFuzzy("NegativoL", Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, 0, 0));
		grupoProfit.add(new VariavelFuzzy("PequenoL", 0, 600000, 600000, 800000));
		grupoProfit.add(new VariavelFuzzy("MedioL", 600000, 1000000, 100000000, 150000000));
		grupoProfit.add(new VariavelFuzzy("GrandeL", 100000000, 250000000, Float.MAX_VALUE, Float.MAX_VALUE));

		GrupoVariaveis grupoPopularity = new GrupoVariaveis();
		grupoPopularity.add(new VariavelFuzzy("BaixaP", 0, 0, 50000000, 100000000));
		grupoPopularity.add(new VariavelFuzzy("MédiaP", 50000000, 100000000, 500000000, 1000000000));
		grupoPopularity.add(new VariavelFuzzy("AltaP", 500000000, 1000000000, 5000000000L, 10000000000L));
		grupoPopularity.add(new VariavelFuzzy("Muito AltaP", 5000000000L, 10000000000L, Float.MAX_VALUE, Float.MAX_VALUE));

		GrupoVariaveis grupoGenres = new GrupoVariaveis();
		grupoGenres.add(new VariavelFuzzy("RuimG", 0, 0, 2, 2.5f));
		grupoGenres.add(new VariavelFuzzy("MédiaG", 2, 3, 4, 5));
		grupoGenres.add(new VariavelFuzzy("BoaG", 4, 5, 5, 5));

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(new File("Dados/new.csv")));
			String header = bfr.readLine();
			HashMap<String, Integer> osGeneros = dicionario();
			

			String line = "";
			while ((line = bfr.readLine()) != null) {
				String[] spl = line.split(";");
				HashMap<String, Float> asVariaveis = new HashMap<String, Float>();

				float profit = parseFloatOrZero(spl[9]);
				float voteAverage = parseFloatOrZero(spl[8]);
				float runtime = parseFloatOrZero(spl[6]);
				float popularity = parseFloatOrZero(spl[5]);
				String title = spl[7];
				String lineIndex = spl[0];

				String[] listaGeneros = spl[2].split(" ");
				int total = 0;
				int quant = 0;

				for (int i = 0; i < listaGeneros.length; i++) {
					if (listaGeneros[i] == null) {
						continue;
					}
					if(listaGeneros[i] == ""){
						break;
					}
					if (osGeneros.get(listaGeneros[i]) == 0) {
						continue;
					}
					total = total + osGeneros.get(listaGeneros[i]);
					quant++;
				}

				float nota;
				if (quant == 0) {
					nota = 0;
				} else {
					nota = total / quant;
				}

				grupoProfit.fuzzifica(profit, asVariaveis);
				grupoVoteAvg.fuzzifica(voteAverage, asVariaveis);
				grupoRunTime.fuzzifica(runtime, asVariaveis);
				grupoPopularity.fuzzifica(popularity, asVariaveis);
				grupoGenres.fuzzifica(nota, asVariaveis);

				/*System.out.println("Fuzzyfication Results for Index: " + lineIndex + ", Title: " + title);
				System.out.println("Profit Fuzzyfication: " + asVariaveis.get("NegativoL") + " (Negativo), " +
						asVariaveis.get("PequenoL") + " (Pequeno), " +
						asVariaveis.get("MedioL") + " (Medio), " +
						asVariaveis.get("GrandeL") + " (Grande)");

				System.out.println("Vote Average Fuzzyfication: " + asVariaveis.get("Muito RuimQ") + " (Muito Ruim), " +
						asVariaveis.get("RuimQ") + " (Ruim), " +
						asVariaveis.get("MedianoQ") + " (Mediano), " +
						asVariaveis.get("BomQ") + " (Bom), " +
						asVariaveis.get("Muito BomQ") + " (Muito Bom)");

				System.out.println("Runtime Fuzzyfication: " + asVariaveis.get("CurtoD") + " (Curto), " +
						asVariaveis.get("IdealD") + " (Ideal), " +
						asVariaveis.get("LongoD") + " (Longo)");

				System.out.println("Popularity Fuzzyfication: " + asVariaveis.get("BaixaP") + " (Baixa), " +
						asVariaveis.get("MédiaP") + " (Média), " +
						asVariaveis.get("AltaP") + " (Alta), " +
						asVariaveis.get("Muito AltaP") + " (Muito Alta)");

				System.out.println("Genres Fuzzyfication: " + asVariaveis.get("RuimG") + " (Ruim), " +
						asVariaveis.get("MédiaG") + " (Média), " +
						asVariaveis.get("BoaG") + " (Boa), ");

				System.out.println("--------------------------------------------------");*/

				float qualidade = (asVariaveis.get("Muito RuimQ")*2 
				+ asVariaveis.get("RuimQ")*4  
				+ asVariaveis.get("MedianoQ")*6 
				+ asVariaveis.get("BomQ")*8
				+ asVariaveis.get("Muito BomQ")*10);

				float duracao = (asVariaveis.get("CurtoD")*6
				+ asVariaveis.get("IdealD")*10
				+ asVariaveis.get("LongoD")*4);

				float lucro = (asVariaveis.get("NegativoL")*0
				+ asVariaveis.get("PequenoL")*6
				+ asVariaveis.get("MedioL")*8
				+ asVariaveis.get("GrandeL")*10);

				float popularidade = (asVariaveis.get("BaixaP")*4
				+ asVariaveis.get("MédiaP")*6
				+ asVariaveis.get("AltaP")*8
				+ asVariaveis.get("Muito AltaP")*10);

				float generos = (asVariaveis.get("RuimG")*4
				+ asVariaveis.get("MédiaG")*8
				+ asVariaveis.get("BoaG")*10);

				float notafinal = (qualidade + duracao + lucro + popularidade + generos)/5;

				if(notafinal >= 0){
					System.out.printf(
							"Index: %s, Title: %s, Profit: %.1f, Vote Average: %.1f, Runtime: %.1f, Popularity: %.1f -> Score: %.2f%n",
							lineIndex, title, profit, voteAverage, runtime, popularity, notafinal);
					System.out.println("==================================================");}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static float parseFloatOrZero(String value) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return 0.0f;
		}
	}

	public static HashMap<String, Integer> dicionario() {
		HashMap<String, Integer> generosMap = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File("Dados/generos.txt")))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" = ");
				if(parts.length < 2){
					break;
				}
				String genre = parts[0].trim();
				int value = parts[1].trim().equals("N") ? 0 : Integer.parseInt(parts[1].trim());

				generosMap.put(genre, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return generosMap;
	}

}
