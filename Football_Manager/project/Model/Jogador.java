package Model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public abstract class Jogador implements Serializable{
    private String nome;
    private int numeroCamisola;
    private int velocidade;
    private int resistencia;
    private int destreza;
    private int impulsao;
    private int jogo_de_cabeca;
    private int remate;
    private int capacidade_passe;
    private double qualidade;
    private List <String> carreer;

    public Jogador(){
        this.nome = "";
        this.numeroCamisola = 0;
        this.velocidade = 0;
        this.resistencia = 0;
        this.destreza = 0;
        this.impulsao = 0;
        this.jogo_de_cabeca = 0;
        this.remate = 0;
        this.capacidade_passe = 0;
        this.qualidade = 0;
        this.carreer = new ArrayList<>();
    }

    public Jogador(String nome,int n, int veloc, int resist, int destr, int impuls, int jc, int remate, int capacpasse, ArrayList<String> carrer) {
        this.nome = nome;
        this.numeroCamisola = n;
        this.velocidade = limita(veloc);
        this.resistencia = limita(resist);
        this.destreza = limita(destr);
        this.impulsao = limita(impuls);
        this.jogo_de_cabeca = limita(jc);
        this.remate = limita(remate);
        this.capacidade_passe = limita(capacpasse);
        this.carreer = new ArrayList<>();
        if (carrer != null) {
            for (String s : carrer) {
                this.carreer.add(s);
            }
        }
    }

    public Jogador(Jogador a){
        this.nome = a.getNome();
        this.numeroCamisola = a.getNumeroCamisola();
        this.velocidade = a.getVelocidade();
        this.resistencia = a.getResistencia();
        this.destreza = a.getDestreza();
        this.impulsao = a.getImpulsao();
        this.jogo_de_cabeca = a.getJogo_de_cabeca();
        this.remate = a.getRemate();
        this.capacidade_passe = a.getCapacidade_passe();
        this.qualidade = a.getQualidade();
        this.carreer = a.getCarreer();
    }
    
    public int limita(int x){
        int result;
        if (x > 100) result = 100;
        else if (x < 0) result = 0;
        else result = x;
        return result;
    }
    
    public String getNome (){
        return this.nome;
    }

    public void setNome (String nome){
        this.nome = nome;
    }

    public int getNumeroCamisola(){
        return this.numeroCamisola;
    }

    public void setNumeroCamisola(int n){
        this.numeroCamisola = n;
    }

    public int getVelocidade() {
        return this.velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = limita(velocidade);
    }

    public void diminuiVelocidade(int quanto){
        this.velocidade = limita(this.velocidade - quanto);
    }

    public int getResistencia() {
        return this.resistencia;
    }

    public void setResistência(int resistência) {
        this.resistencia = limita(resistência);
    }

    public void diminuiResistência (int quanto){
        this.resistencia = limita(this.resistencia - quanto);
    }

    public int getDestreza() {
        return this.destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = limita(destreza);
    }

    public void diminuiDestreza (int quanto){
        this.destreza = limita(this.destreza - quanto);
    }

    public int getImpulsao() {
        return this.impulsao;
    }

    public void setImpulsao(int impulsao) {
        this.impulsao = limita(impulsao);
    }

    public void diminuiImpulsao (int quanto){
        this.impulsao = limita(this.impulsao - quanto);
    }

    public int getJogo_de_cabeca() {
        return this.jogo_de_cabeca;
    }

    public void setJogo_de_cabeca(int jogo_de_cabeca) {
        this.jogo_de_cabeca = limita(jogo_de_cabeca);
    }

    public void diminuiJogoCabeca (int quanto){
        this.jogo_de_cabeca = limita(this.jogo_de_cabeca - quanto);
    }

    public int getRemate() {
        return this.remate;
    }

    public void setRemate(int remate) {
        this.remate = limita(remate);
    }

    public void diminuiRemate (int quanto){
        this.remate = limita(this.remate - quanto);
    }

    public int getCapacidade_passe() {
        return this.capacidade_passe;
    }

    public void setCapacidade_passe(int capacidade_passe) {
        this.capacidade_passe = limita(capacidade_passe);
    }

    public void diminuiCapacidadePasse (int quanto){
        this.capacidade_passe = limita(this.capacidade_passe - quanto);
    }

    public List<String> getCarreer() {
        List<String> resultado = new ArrayList<>();
        for (String s: this.carreer){
            resultado.add(s);
        }
        return this.carreer;
    }

    public void setCarreer(ArrayList<String> carreer) {
        this.carreer = new ArrayList<>();
        for (String s: carreer)
            this.carreer.add(s);
    }

    public void addCarrer(String nomeEquipa){
        this.carreer.add(nomeEquipa);
    }
    
    public double getQualidade(){
        return this.qualidade;
    }
    
    public void setQualidade(double q){
        this.qualidade = q;
    }
    
    public abstract void calculaQualidade();

    //função que retirar "qualidade" aos jogadores com a passagem do tempo
    public abstract void fadiga();

    public abstract Jogador clone();

    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append(getNome()).append(",");
        sb.append(getNumeroCamisola()).append(",");
        sb.append(getVelocidade()).append(",");
        sb.append(getResistencia()).append(",");
        sb.append(getDestreza()).append(",");
        sb.append(getImpulsao()).append(",");
        sb.append(getJogo_de_cabeca()).append(",");
        sb.append(getRemate()).append(",");
        sb.append(getCapacidade_passe()).append(",");
        return sb.toString();
    }    

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Jogador j = (Jogador) o;
        return  this.nome == j.getNome() 
                && this.numeroCamisola == j.getNumeroCamisola()
                && this.velocidade == j.getVelocidade() 
                && this.resistencia == j.getResistencia()
                && this.destreza == j.getDestreza() 
                && this.impulsao == j.getImpulsao() 
                && this.jogo_de_cabeca == j.getJogo_de_cabeca() 
                && this.remate == j.getRemate()
                && this.qualidade == j.getQualidade()
                && this.capacidade_passe == j.getCapacidade_passe() && this.carreer.equals(j.getCarreer());
    }
}