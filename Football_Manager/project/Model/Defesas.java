package Model;

import java.util.Random;
import java.io.Serializable;

public class Defesas extends Jogador implements Serializable{
    private int marcação;
    
    public Defesas (){
        super();
        this.marcação = 0;
    }
    
    public Defesas (String nome, int n, int v, int r, int d, int i, int jc, int remate, int cpasse,int marcaçao){
        super(nome, n,v, r, d, i, jc, remate, cpasse, null);
        this.marcação = super.limita(marcaçao);
        calculaQualidade();
    }

    public Defesas (Defesas m){
        super(m);
        this.marcação = m.getMarcação();
    }
    
    public int getMarcação(){
        return this.marcação;
    }
    
    public void setMarcação(int marcação){
        this.marcação = marcação;        
    }

    public void diminuiMarcacao(int quanto){
        this.marcação = limita(this.marcação - quanto);
    }
    
    public void calculaQualidade (){
        super.setQualidade (super.getCapacidade_passe()*0.1 + super.getDestreza()*0.025 + super.getImpulsao()*0.2 + super.getJogo_de_cabeca()*0.2+ super.getRemate()*0.025+ super.getResistencia()*0.1+super.getVelocidade()*0.1+this.getMarcação()*0.25);
    }

    public void fadiga(){
        super.diminuiVelocidade(2);
        super.diminuiResistência(1);
        super.diminuiDestreza(2);
        super.diminuiImpulsao(2);
        super.diminuiJogoCabeca(2);
        super.diminuiRemate(1);
        super.diminuiCapacidadePasse(1);
        diminuiMarcacao(3);
        calculaQualidade();
    }

    public Defesas clone (){
        return new Defesas(this);
    }
    
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Defesas m = (Defesas) o;
        return super.equals(m) && this.marcação == m.getMarcação();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(this.getMarcação()).append(",").append(super.getQualidade()).append(",").append(super.getCarreer());
        return sb.toString();
    }
    
    public static Defesas parse(String s){
        String[] campos = s.split(",");
        Random rand = new Random();
        return new Defesas(campos[0], Integer.parseInt(campos[1]),
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