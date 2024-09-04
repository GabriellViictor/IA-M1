import java.util.List;

public class ClassificadorFuzzy {

    private String classificar(double popularidade, double avaliacao) { // mais
        double altaPopularidade = pertinenciaAlta(popularidade, 80, 100);
        double baixaPopularidade = pertinenciaBaixa(popularidade, 0, 20);
        double altaAvaliacao = pertinenciaAlta(avaliacao, 7, 10);
        double baixaAvaliacao = pertinenciaBaixa(avaliacao, 0, 3);

        if (altaAvaliacao > 0.5 && altaPopularidade > 0.5) {
            return "Alta prioridade";
        } else if (altaAvaliacao > 0.5 || altaPopularidade > 0.5) {
            return "Média prioridade";
        } else {
            return "Baixa prioridade";
        }
    }

    private double pertinenciaAlta(double valor, double limiteInferior, double limiteSuperior) {
        if (valor <= limiteInferior) {
            return 0.0;
        } else if (valor >= limiteSuperior) {
            return 1.0;
        } else {
            return (valor - limiteInferior) / (limiteSuperior - limiteInferior);
        }
    }

    private double pertinenciaBaixa(double valor, double limiteInferior, double limiteSuperior) {
        if (valor <= limiteInferior) {
            return 1.0;
        } else if (valor >= limiteSuperior) {
            return 0.0;
        } else {
            return (limiteSuperior - valor) / (limiteSuperior - limiteInferior);
        }
    }
}
