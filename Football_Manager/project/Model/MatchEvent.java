package Model;

import java.util.Arrays;
import java.util.Random;

public class MatchEvent {
    private boolean goal;
    private int homeOrAway;
    private Remates remates;

    public MatchEvent(){
        this.goal = false;
        this.homeOrAway = 1;
        this.remates = new Remates();
    }

    public MatchEvent(Equipa_Jogo home, Equipa_Jogo away){
        this.remates = new Remates();
        this.goal = isGoal(home, away);
        this.homeOrAway = getHomeOrAway();
    }

    public MatchEvent(MatchEvent me){
        this.goal = me.getGoal();
        this.homeOrAway = me.getHomeOrAway();
        this.remates = me.getRemates();
    }

    public boolean getGoal() {
        return this.goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public int getHomeOrAway() {
        return this.homeOrAway;
    }

    public void setHomeOrAway(int homeOrAway) {
        this.homeOrAway = homeOrAway;
    }

    public Remates getRemates() {
        return this.remates.clone();
    }

    public void setRemates(Remates remates) {
        this.remates = remates.clone();
    }

    //obtem a probabilidade de ser golo
    public Integer goalProbability(){
        Random rand = new Random();
        return rand.nextInt(100);
    }

    //determina o evento que ocorreu no jogo (remates ou outros eventos)
    public int teamScored(Equipa_Jogo home, Equipa_Jogo away) {
        double homequality = home.getQualidadeEquipa();
        double awayquality = away.getQualidadeEquipa();
        int pa = (int) (homequality * 20) / 100;
        int pb = (int) (awayquality * 20) / 100;

        int pe;
        Random rand = new Random();

        if (pa == pb) pe = pb;
        else if (pa > pb) {
            pe = pb;
            pb = pb*2 - pa;
            if (pb<4) pb = 4;
        }
        else {
            pe = pa;
            pa = pa*2 - pb;
            if (pa<4) pa = 4;
        }

        int total = pa + pb + pe;


        int[] arr = new int[total];
        int i;
        for (i = 0; i < total; i++) {
            if (i < pa) arr[i] = 0;
            else if (i < pe + pa) arr[i] = 1;
            else arr[i] = 2;
        }
        int posicao = rand.nextInt(total-1);
    return arr[posicao];
    }

    //determina se foi golo de alguma equipa
    public boolean isGoal(Equipa_Jogo h, Equipa_Jogo a){
        boolean ret = false;
        int whoScored = teamScored(h, a);
        int goalprob = goalProbability();
        if (goalprob >= 85 && (whoScored == 0 || whoScored == 2)) {
            ret = true;
            setHomeOrAway(whoScored);
        }
        statsRemates(goalprob, whoScored);
        return ret;
    }

    //atualiza a variável remates da equipa
    public void statsRemates(int goalProbability, int casaOuFora){
        if(goalProbability >= 60){
            this.remates.adicionaRemateEquipa(casaOuFora);
            this.remates.adicionaRematePerigosoEquipa(casaOuFora);
        }
        else{
            this.remates.adicionaRemateEquipa(casaOuFora);
        }
    }

}
