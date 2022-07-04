package Model;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Jogo implements Serializable {
    private Equipa_Jogo casa;
    private Equipa_Jogo fora;
    private int golosCasa;
    private int golosFora;
    private int golosCasaIntervalo;
    private int golosForaIntervalo;
    private List<Integer> minutosGolosCasa;
    private List<Integer> minutosGolosFora;
    private LocalDate date;
    private Remates remates;

    public Jogo() {
        this.casa = new Equipa_Jogo();
        this.fora = new Equipa_Jogo();
        this.golosCasa = this.golosFora = 0;
        this.golosCasaIntervalo = this.golosForaIntervalo = 0;
        this.minutosGolosCasa = new LinkedList<>();
        this.minutosGolosFora = new LinkedList<>();
        this.date = LocalDate.now();
        this.remates = new Remates();
    }

    public Jogo(Equipa_Jogo Casa, Equipa_Jogo Fora, int gC, int gF, int gCIntervalo, int gFIntervalo,LocalDate data) {
        this.casa = Casa.clone();
        this.fora = Fora.clone();
        this.golosCasa = gC;
        this.golosFora = gF;
        this.golosCasaIntervalo = gCIntervalo;
        this.golosForaIntervalo = gFIntervalo;
        this.minutosGolosCasa = new LinkedList<>();
        this.minutosGolosFora = new LinkedList<>();
        this.date = data;
        this.remates = new Remates();
    }

    public Jogo(Equipa_Jogo visitada, Equipa_Jogo visitante) {
        this.casa = visitada.clone();
        this.fora = visitante.clone();
        this.golosCasa = this.golosFora = 0;
        this.golosCasaIntervalo = this.golosForaIntervalo = 0;
        this.minutosGolosCasa = new LinkedList<>();
        this.minutosGolosFora = new LinkedList<>();
        this.date = LocalDate.now();
        this.remates = new Remates();
    }

    public Jogo(Equipa visitada, Equipa visitante) {
        this.casa = new Equipa_Jogo(visitada);
        this.fora = new Equipa_Jogo(visitante);
        this.golosCasa = this.golosFora = 0;
        this.golosCasaIntervalo = this.golosForaIntervalo = 0;
        this.minutosGolosCasa = new LinkedList<>();
        this.minutosGolosFora = new LinkedList<>();
        this.date = LocalDate.now();
        this.remates = new Remates();
    }

    public Jogo(Jogo j) {
        this.casa = j.getEquipaCasa();
        this.fora = j.getEquipaFora();
        this.golosCasa = j.getGolosCasa();
        this.golosFora = j.getGolosFora();
        this.golosCasaIntervalo = j.golosCasaIntervalo;
        this.golosForaIntervalo = j.golosForaIntervalo;
        this.minutosGolosCasa = j.getMinutosGolosCasa();
        this.minutosGolosFora = j.getMinutosGolosFora();
        this.date = j.getDate();
        this.remates = j.getRemates();
    }

    public Equipa_Jogo getEquipaCasa() {
        return this.casa.clone();
    }

    public Equipa_Jogo getEquipaFora() {
        return this.fora.clone();
    }

    public int getGolosCasa() {
        return this.golosCasa;
    }

    public int getGolosFora() {
        return this.golosFora;
    }

    public int getGolosCasaIntervalo() {
        return this.golosCasaIntervalo;
    }

    public int getGolosForaIntervalo() {
        return this.golosForaIntervalo;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Remates getRemates() {
        return this.remates.clone();
    }

    public List<Integer> getMinutosGolosCasa() {
        List<Integer> res = new ArrayList<>();
        for (Integer i: this.minutosGolosCasa){
            res.add(i);
        }
        return res;
    }

    public List<Integer> getMinutosGolosFora() {
        List<Integer> res = new ArrayList<>();
        for (Integer i: this.minutosGolosFora){
            res.add(i);
        }
        return res;
    }

    public void setRemates(Remates remates) {
        this.remates = remates.clone();
    }

    //atualiza os remates da equipa_jogo
    public void adicionaRemates(Remates r){
        this.remates.setRematesEquipaCasa(this.remates.getRematesEquipaCasa() + r.getRematesEquipaCasa());
        this.remates.setRematesEquipaFora(this.remates.getRematesEquipaFora() + r.getRematesEquipaFora());
        this.remates.setRematesPerigososEquipaCasa(this.remates.getRematesPerigososEquipaCasa() + r.getRematesPerigososEquipaCasa());
        this.remates.setRematesPerigososEquipaFora(this.remates.getRematesPerigososEquipaFora() + r.getRematesPerigososEquipaFora());
    }

    public Jogo clone() {
        return new Jogo(this);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getEquipaCasa().getNome() + " X " + getEquipaFora().getNome() + ": ");
        s.append(getGolosCasa() + ":" + getGolosFora() + "  " + getDate() + "\n");

        s.append(this.casa.toString());
        s.append(this.fora.toString());

        return s.toString();
    }

    public String substituicoesToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nSubstituiçoes Casa:\n");
        sb.append(this.casa.substituicoesToString());
        sb.append("\nSubstituiçoes Fora:\n");
        sb.append(this.fora.substituicoesToString());
        return sb.toString();
    }

    //Função que adiciona à qualidade da equipa um boost;
    public void boostEquipaCasa(){
        this.casa.setQualidadeEquipa(this.casa.getQualidadeEquipa() + 7);
    }

    //Função que determina o resultado de um jogo em função da qualidade da equipa
    public int simulaResultado() {
        int winner; // 0 -> casa | 1 -> empate | 2-> fora
        int quantasSubsC, quantasSubsF,nextMinC,nextMinF, contaSubC = 0,contaSubF = 0, minutoGolo = 0;
        Random rand = new Random();

        if ((quantasSubsC = this.casa.quantasSubs()) == 0){
            nextMinC = 100;
        }
        else{
            nextMinC = this.casa.getMinutoSubs(0);
        }
        if ((quantasSubsF = this.fora.quantasSubs()) == 0){
            nextMinF = 100;
        }
        else {
            nextMinF = this.fora.getMinutoSubs(0);
        }

        int game_time = 90, time;
        for (time = 0; time < game_time; time += 3) {
            if (time % 10 == 0) {
                this.casa.fadiga();
                this.fora.fadiga();
                boostEquipaCasa();
            }

            if (time >= nextMinC){
                this.casa.subMinuto(nextMinC);
                boostEquipaCasa();
                if (contaSubC < quantasSubsC-1) {
                    contaSubC++;
                    nextMinC = this.casa.getMinutoSubs(contaSubC);
                }
                else{
                    nextMinC = 100;
                }
            }
            if (time > nextMinF){
                this.fora.subMinuto(nextMinF);
                if (contaSubF < quantasSubsF-1){
                    contaSubF++;
                    nextMinF = this.fora.getMinutoSubs(contaSubF);
                }
                else{
                    nextMinF = 100;
                }
            }
            MatchEvent event = new MatchEvent(this.casa.clone(), this.fora.clone());
            Remates r = event.getRemates();
            adicionaRemates(r);
            boolean goal = event.getGoal();
            int homeOrAway = event.getHomeOrAway();
            if (goal && homeOrAway == 0) {
                if (time<=45){
                    this.golosCasaIntervalo++;
                    this.golosCasa++;
                }
                else{
                    this.golosCasa++;
                }
                if (time == 0) minutoGolo = 0;
                else minutoGolo = time - 3 + rand.nextInt(3);
                this.minutosGolosCasa.add(minutoGolo);
            }
            else if (goal && homeOrAway == 2) {
                if (time<=45){
                    this.golosForaIntervalo++;
                    this.golosFora++;
                }
                else {
                    this.golosFora++;
                }
                if (time == 0) minutoGolo = 0;
                else minutoGolo = time - 3 + rand.nextInt(3);
                this.minutosGolosFora.add(minutoGolo);
            }
        }

        if (getGolosCasa() > getGolosFora()) {
            winner = 0;
        } else if (getGolosFora() > getGolosCasa()) {
            winner = 2;
        } else {
            winner = 1;
        }
        return winner;
    }

    public static Jogo parse(Equipa a, Equipa b, String s) {
        String[] campos = s.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(campos[4], formatter);

        List<String> aux = new ArrayList<>();
        for (int i = 5; i < 19; i++) aux.add(campos[i]);
        Equipa_Jogo a1 = Equipa_Jogo.parse(a, aux);

        List<String> aux2 = new ArrayList<>();
        for (int i = 19; i < 33; i++) aux2.add(campos[i]);
        Equipa_Jogo b1 = Equipa_Jogo.parse(b, aux2);

        return new Jogo(a1, b1, Integer.parseInt(campos[2]), Integer.parseInt(campos[3]), 0, 0,dateTime);
    }
}