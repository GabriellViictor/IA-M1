import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FuzzyMain {
	public static void main(String[] args) {

		GrupoVariaveis grupoVoteAvg = new GrupoVariaveis();
		grupoVoteAvg.add(new VariavelFuzzy("Muito Ruim", 0, 0, 10, 30));
		grupoVoteAvg.add(new VariavelFuzzy("Ruim", 20, 30, 40, 50));
		grupoVoteAvg.add(new VariavelFuzzy("Mediano", 40, 50, 60, 70));
		grupoVoteAvg.add(new VariavelFuzzy("Bom", 60, 70, 80, 90));
		grupoVoteAvg.add(new VariavelFuzzy("Muito Bom", 80, 90, 100, 100));

		GrupoVariaveis grupoRunTime = new GrupoVariaveis();
		grupoRunTime.add(new VariavelFuzzy("Curto", 0, 0, 75, 90));
		grupoRunTime.add(new VariavelFuzzy("Ideal", 75, 90, 140, 150));
		grupoRunTime.add(new VariavelFuzzy("Longo", 140, 150, Float.MAX_VALUE, Float.MAX_VALUE));

		GrupoVariaveis grupoProfit = new GrupoVariaveis();
		grupoProfit.add(new VariavelFuzzy("Negativo", Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, 0, 0));
		grupoProfit.add(new VariavelFuzzy("Pequeno", 0, 600000, 600000, 800000));
		grupoProfit.add(new VariavelFuzzy("Medio", 600000, 1000000, 100000000, 150000000));
		grupoProfit.add(new VariavelFuzzy("Grande", 100000000, 250000000, Float.MAX_VALUE, Float.MAX_VALUE));

		GrupoVariaveis grupoPopularity = new GrupoVariaveis();
		grupoPopularity.add(new VariavelFuzzy("Baixa", 0, 0, 50000000, 100000000));
		grupoPopularity.add(new VariavelFuzzy("Média", 50000000, 100000000, 500000000, 1000000000));
		grupoPopularity.add(new VariavelFuzzy("Alta", 500000000, 1000000000, 5000000000L, 10000000000L));
		grupoPopularity
				.add(new VariavelFuzzy("Muito Alta", 5000000000L, 10000000000L, Float.MAX_VALUE, Float.MAX_VALUE));

		GrupoVariaveis grupoGenres = new GrupoVariaveis();
		grupoPopularity.add(new VariavelFuzzy("Ruim", 0, 0, 2, 2.5f));
		grupoPopularity.add(new VariavelFuzzy("Média", 2, 3, 4, 5));
		grupoPopularity.add(new VariavelFuzzy("Boa", 4, 5, 5, 5));

		try {
			BufferedReader bfr = new BufferedReader(new FileReader(new File("..Dados/new.csv")));
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

				System.out.println("Fuzzyfication Results for Index: " + lineIndex + ", Title: " + title);
				System.out.println("Profit Fuzzyfication: " + asVariaveis.get("Negativo") + " (Negativo), " +
						asVariaveis.get("Pequeno") + " (Pequeno), " +
						asVariaveis.get("Medio") + " (Medio), " +
						asVariaveis.get("Grande") + " (Grande)");

				System.out.println("Vote Average Fuzzyfication: " + asVariaveis.get("Muito Ruim") + " (Muito Ruim), " +
						asVariaveis.get("Ruim") + " (Ruim), " +
						asVariaveis.get("Mediano") + " (Mediano), " +
						asVariaveis.get("Bom") + " (Bom), " +
						asVariaveis.get("Muito Bom") + " (Muito Bom)");

				System.out.println("Runtime Fuzzyfication: " + asVariaveis.get("Curto") + " (Curto), " +
						asVariaveis.get("Ideal") + " (Ideal), " +
						asVariaveis.get("Longo") + " (Longo)");

				System.out.println("Popularity Fuzzyfication: " + asVariaveis.get("Baixa") + " (Baixa), " +
						asVariaveis.get("Média") + " (Média), " +
						asVariaveis.get("Alta") + " (Alta), " +
						asVariaveis.get("Muito Alta") + " (Muito Alta)");

				System.out.println("Genres Fuzzyfication: " + asVariaveis.get("Ruim") + " (Ruim), " +
						asVariaveis.get("Média") + " (Média), " +
						asVariaveis.get("Boa") + " (Boa), ");

				System.out.println("--------------------------------------------------");

				rodaRegraE(asVariaveis, "Negativo", "Curto", "NA");
				rodaRegraE(asVariaveis, "Pequeno", "Ideal", "A");
				rodaRegraE(asVariaveis, "Medio", "Longo", "MA");

				float NA = asVariaveis.getOrDefault("NA", 0.0f);
				float A = asVariaveis.getOrDefault("A", 0.0f);
				float MA = asVariaveis.getOrDefault("MA", 0.0f);

				float score = (NA * 1.5f + A * 7.0f + MA * 9.5f) / (NA + A + MA);

				System.out.printf(
						"Index: %s, Title: %s, Profit: %.1f, Vote Average: %.1f, Runtime: %.1f, Popularity: %.1f -> Score: %.2f%n",
						lineIndex, title, profit, voteAverage, runtime, popularity, score);
				System.out.println("==================================================");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void rodaRegraE(HashMap<String, Float> asVariaveis, String var1, String var2, String varr) {
		float v = Math.min(asVariaveis.get(var1), asVariaveis.get(var2));
		asVariaveis.put(varr, Math.max(asVariaveis.getOrDefault(varr, 0.0f), v));
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
		try (BufferedReader br = new BufferedReader(new FileReader(new File("generos.txt")))) {
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
