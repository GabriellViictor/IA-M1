import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class FuzzyMain {
	public static void main(String[] args) {
		
		GrupoVariaveis grupoVoteAvg = new GrupoVariaveis();
		grupoVoteAvg.add(new VariavelFuzzy("MR", 0, 0, 10, 20));
		grupoVoteAvg.add(new VariavelFuzzy("R", 10, 20, 30, 60));
		grupoVoteAvg.add(new VariavelFuzzy("M", 20, 40, 50, 70));
		grupoVoteAvg.add(new VariavelFuzzy("B", 20, 40, 50, 70));
		grupoVoteAvg.add(new VariavelFuzzy("MB", 20, 40, 50, 70));

        GrupoVariaveis grupoRunTime = new GrupoVariaveis();
		grupoRunTime.add(new VariavelFuzzy("C", 0, 0, 10, 20));
		grupoRunTime.add(new VariavelFuzzy("I", 10, 20, 30, 60));
		grupoRunTime.add(new VariavelFuzzy("L", 20, 40, 50, 70));

        GrupoVariaveis grupoProfit = new GrupoVariaveis();
		grupoRunTime.add(new VariavelFuzzy("N", 0, 0, 10, 20));
		grupoRunTime.add(new VariavelFuzzy("P", 10, 20, 30, 60));
		grupoRunTime.add(new VariavelFuzzy("M", 20, 40, 50, 70));
        grupoRunTime.add(new VariavelFuzzy("G", 10, 20, 30, 60));



		

		
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(new File("restaurantes_filtrados.csv")));
			
			String header = bfr.readLine();
			String splitheder[] = header.split(";");
			for (int i = 0; i < splitheder.length;i++) {
				System.out.println(""+i+" "+splitheder[i]);
			}
			
			String line = "";
			
			while((line=bfr.readLine())!=null) {
				String spl[] = line.split(";");
				HashMap<String,Float> asVariaveis = new HashMap<String,Float>();
				
				float custodinheiro = Float.parseFloat(spl[3]);
				grupoVoteAvg.fuzzifica(custodinheiro, asVariaveis);
				
				float rating = Float.parseFloat(spl[5]);
				grupoRunTime.fuzzifica(rating, asVariaveis);
				
				rodaRegraE(asVariaveis,"Barato","B","A");
				rodaRegraE(asVariaveis,"Muito Barato","B","A");
				rodaRegraE(asVariaveis,"Muito Barato","MB","MA");
				rodaRegraE(asVariaveis,"Barato","MB","MA");
				rodaRegraE(asVariaveis,"Barato","R","NA");
				rodaRegraE(asVariaveis,"Muito Barato","R","A");
				rodaRegraE(asVariaveis,"Muito Barato","MR","NA");
				rodaRegraE(asVariaveis,"Muito Caro","MR","NA");
				rodaRegraE(asVariaveis,"Muito Caro","R","NA");
				rodaRegraE(asVariaveis,"Muito Caro","B","NA");
				rodaRegraE(asVariaveis,"Muito Caro","MB","A");
				
				float NA = asVariaveis.get("NA");
				float A = asVariaveis.get("A");
				float MA = asVariaveis.get("MA");
				
				float score = (NA*1.5f+A*7.0f+MA*9.5f)/(NA+A+MA);
				
				System.out.println(" "+custodinheiro+" "+rating +"-> "+score);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void rodaRegraE(HashMap<String, Float> asVariaveis,String var1,String var2,String varr) {
		float v = Math.min(asVariaveis.get(var1),asVariaveis.get(var2));
		if(asVariaveis.keySet().contains(varr)) {
			float vatual = asVariaveis.get(varr);
			asVariaveis.put(varr, Math.max(vatual, v));
		}else {
			asVariaveis.put(varr, v);
		}
	}
}