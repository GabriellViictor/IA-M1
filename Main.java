import java.util.List;

public class Main {

    public static void main(String[] args) {
        LeiaCsv leitor = new LeiaCsv();
        leitor.run();

        //Imprimir o cabeçalho
        String cabecalho = leitor.getCabecalho();
        System.out.print(cabecalho);

        leitor.mostrarLinha(3);

        List<String> dados = leitor.getDados();
 /* 
        for (String linha : dados) {
            System.out.println(linha);
        }
*/
        //ClassificadorFuzzy classificador = new ClassificadorFuzzy();
        //classificador.classificarFilmes(dados);
    }
}
