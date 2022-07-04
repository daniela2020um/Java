package Model;
import java.io.Serializable;

public class Medios extends Jogador implements Serializable{
    private int recuperacao_bola;

    public Medios (){
        super();
        this.recuperacao_bola = 0;
    }
    
    public Medios (String nome, int n, int v, int r, int d, int i, int jc, int remate, int cpasse,int rbola){
        super(nome,n, v, r, d, i, jc, remate, cpasse, null);
        this.recuperacao_bola = super.limita(rbola);
        calculaQualidade();
    }

    public Medios (Medios m){
        super(m);
        this.recuperacao_bola = m.getRB();
    }
    
    public int getRB(){
        return this.recuperacao_bola;
    }
    
    public void setRB(int r){
        this.recuperacao_bola = r;
    }

    public void diminuiRB(int quanto){
        this.recuperacao_bola = limita(this.recuperacao_bola - quanto);
    }
    
    public void calculaQualidade (){
        super.setQualidade (super.getCapacidade_passe()*0.4 + super.getDestreza()*0.2 + super.getImpulsao()*0.025 + super.getJogo_de_cabeca()*0.025 + super.getRemate()*0.05+ super.getResistencia()*0.1+super.getVelocidade()*0.05+this.getRB()*0.15);
    }

    public void fadiga(){
        super.diminuiVelocidade(2);
        super.diminuiResistência(2);
        super.diminuiDestreza(2);
        super.diminuiImpulsao(1);
        super.diminuiJogoCabeca(1);
        super.diminuiRemate(1);
        super.diminuiCapacidadePasse(2);
        diminuiRB(3);
        calculaQualidade();
    }
    
    public Medios clone (){
        return new Medios(this);
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Medios m = (Medios) o;
        return super.equals(m) && this.recuperacao_bola == m.getRB();
    }    
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(this.getRB()).append(",").append(super.getQualidade()).append(",").append(super.getCarreer());
        return sb.toString();
    }
    
    public static Medios parse(String s){
        String[] campos = s.split(",");
        return new Medios(campos[0], Integer.parseInt(campos[1]),
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