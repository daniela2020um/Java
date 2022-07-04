package Model;

import java.util.Random;
import java.io.Serializable;

public class Avancados extends Jogador implements Serializable{
    private int posicionamento;
 
    public Avancados (){
        super();
        this.posicionamento = 0;
    }

    public Avancados (String nome, int n, int v, int r, int d, int i, int jc, int remate, int cpasse,int posicionamento){
        super(nome,n, v, r, d, i, jc, remate, cpasse, null);
        this.posicionamento = super.limita(posicionamento);
        calculaQualidade();
    }
    
    public Avancados (Avancados m){
        super(m);
        this.posicionamento = m.getPosicionamento();
    }
    
    public int getPosicionamento() {
        return this.posicionamento;
    }
    
    public void setPosicionamento(int posicionamento) {
        this.posicionamento = posicionamento;
    }

    public void diminuiPosicinamento(int quanto){
        this.posicionamento = limita(this.posicionamento - quanto);
    }

    public void calculaQualidade (){
        super.setQualidade (super.getCapacidade_passe()*0.025 + super.getDestreza()*0.05 + super.getImpulsao()*0.05 + super.getJogo_de_cabeca()*0.2 + super.getRemate()*0.4 + super.getResistencia()*0.025+super.getVelocidade()*0.05+this.getPosicionamento()*0.2);
    }

    public void fadiga(){
        super.diminuiVelocidade(3);
        super.diminuiResistência(3);
        super.diminuiDestreza(2);
        super.diminuiImpulsao(2);
        super.diminuiJogoCabeca(1);
        super.diminuiRemate(3);
        super.diminuiCapacidadePasse(1);
        diminuiPosicinamento(1);
        calculaQualidade();
    }
    
    public Avancados clone (){
        return new Avancados(this);
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Avancados m = (Avancados) o;
        return super.equals(m) && this.posicionamento == m.getPosicionamento();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(this.getPosicionamento()).append(",").append(super.getQualidade()).append(",").append(super.getCarreer());
        return sb.toString();
    }
    
    public static Avancados parse (String s){
        String[] campos = s.split(",");
        Random rand = new Random();
        return new Avancados(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]),
                rand.nextInt(100));
    }
}