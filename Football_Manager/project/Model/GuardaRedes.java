package Model;

import java.util.ArrayList;
import java.io.Serializable;

public class GuardaRedes extends Jogador implements Serializable{
    private int elasticidade;

    public GuardaRedes(){
        super();
        this.elasticidade = 0;
    }

    public GuardaRedes(String nome, int n, int v, int r, int d, int i, int jc, int remate, int cpasse, int elasticidade){
        super(nome,n,v, r, d, i, jc, remate, cpasse,null);
        this.elasticidade = super.limita(elasticidade);
        calculaQualidade();
    }

    public GuardaRedes(GuardaRedes a){
        super(a);
        this.elasticidade = a.getElasticidade();
    }

    public int getElasticidade() {
        return this.elasticidade;
    }

    public void setElasticidade(int elasticidade) {
        this.elasticidade = elasticidade;
    }

    public void diminuiElasticidade(int quanto){
        this.elasticidade = limita(this.elasticidade - quanto);
    }

    public void calculaQualidade(){
        super.setQualidade (super.getCapacidade_passe()*0.1 + super.getDestreza()*0.025 + super.getImpulsao()*0.3 + super.getJogo_de_cabeca()*0.025+ super.getRemate()*0.025+ super.getResistencia()*0.025+super.getVelocidade()*0.1+this.getElasticidade()*0.4);
    }

    public void fadiga(){
        super.diminuiVelocidade(1);
        super.diminuiResistência(1);
        super.diminuiDestreza(1);
        super.diminuiImpulsao(1);
        super.diminuiJogoCabeca(1);
        super.diminuiRemate(1);
        super.diminuiCapacidadePasse(1);
        diminuiElasticidade(1);
        calculaQualidade();
    }

    public GuardaRedes clone(){
        return new GuardaRedes(this);
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        GuardaRedes m = (GuardaRedes) o;
        return super.equals(m) && this.elasticidade == m.getElasticidade();
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(this.getElasticidade()).append(",").append(super.getQualidade()).append(",").append(super.getCarreer());
        return sb.toString();
    }
    
    public static GuardaRedes parse(String s){
        String[] campos = s.split(",");
        return new GuardaRedes(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]),
                Integer.parseInt(campos[9]));   
    }
}