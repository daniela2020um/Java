package View;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class Menu
{
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    
    public Menu(){
    }

    public void inicio(){
        StringBuilder sb = new StringBuilder("\n-------------------------------------------\n\tWelcome\n-------------------------------------------\n");
        sb.append ("0) Consultar Menu novamente.\n");
        sb.append ("1) Ler Ficheiros: texto ou ObjectStream.\n");
        sb.append ("2) Gravar ObjectStream.\n");
        sb.append ("3) Menu Equipas.\n");
        sb.append ("4) Menu Jogos.\n");
        sb.append ("5) Começar jogo entre duas equipas.\n");
        sb.append ("-1) Sair.\n");
        System.out.print(sb.toString());
    }

    public void MenuLeitura(){
        StringBuilder sb = new StringBuilder("\n-------------------------------------------\n\tLeitura:\n-------------------------------------------\n");
        sb.append("Selecione o tipo de files que pretende ler.\n");
        sb.append("0) Voltar para o Menu Principal.\n");
        sb.append("1) Ficheiros de texto (ex:logs).\n");
        sb.append("2) Ficheiros ObjectStream.\n");
        System.out.print(sb.toString());
    }

    public void LeituraAux(){
        StringBuilder sb = new StringBuilder();
        sb.append("Indique se pretende que sejam lidos ficheiros default ou indicar novos ficheiros.\n");
        sb.append("1) Ficheiros default.\n");
        sb.append("2) Novos ficheiros.\n");
        System.out.print(sb.toString());
    }

    public void Gravacao(){
        StringBuilder sb = new StringBuilder();
        sb.append("Indique se pretende usar o nome default ou indicar um novo nome para o ficheiro.\n");
        sb.append("0) Voltar para o Menu Principal.\n");
        sb.append("1) Nome default.\n");
        sb.append("2) Novo nome.\n");
        System.out.print(sb.toString());
    }

    public void MenuEquipas(){
        StringBuilder sb = new StringBuilder("\n-------------------------------------------\n\tMenu Equipas\n-------------------------------------------\n");
        sb.append("-1) Voltar para o Menu Principal.\n");
        sb.append("0) Voltar a ver menu.\n");
        sb.append("1) Consultar todas as equipas do campeonato.\n");
        sb.append("2) Consultar uma equipa em particular.\n");
        sb.append("3) Criar nova equipa.\n");
        sb.append("4) Transferir jogador entre equipas.\n");
        sb.append("5) Ver jogadores para contratar e criar novos jogadores.\n");
        System.out.print(sb.toString());
    }
    
    public void waitingInstructions(){
        System.out.print("\t>");
    }
    
    public void VoltarMenuAnterior(){
        System.out.print("Returning to the previous Menu...\n");
    }

    public void OpcaoInexistente(){
        System.out.println("Opçao Inexistente!");
    }
    
    public void ImprimeString(String s){
        System.out.print(s + "\n");
    }
    
    public void errorFileNotFound(){
        System.out.print("Could not find file.\n");
    }

    public void leituraImpossivel(){
        System.out.println("Não consegui ler os dados...! Tente novamente!");
    }

    public void LeituraConcluida(){
        System.out.println("Leitura concluida!");
    }
    
    public void equipaInexistente(){
        System.out.print("The team selected does not exist\n");
    }
    
    public void jogadorInexistente(){
        System.out.print("The player selected does not exist\n");
    }
    
    public void InstrucaoNovaEquipa(){
        System.out.print("Insira o nome da equipa que deseja criar. Esta equipa não terá jogadores. Estes devem ser criados posteriormente.\n");
    }
    
    public void EquipaCriada(int e){
        if (e == -1) System.out.print("Não foi possível criar nova equipa. Já existe uma equipa com esse nome.\n");
        else System.out.print("A equipa que criou é a equipa "+e+"\n");
    }

    public void transferencia(){
        System.out.print("Indique o numero da equipa à qual pertence o jogador, para que equipa pertende que ele vá e o numero da camisola do jogador.\nSe pretende transferir para os jogadores disponiveis (Contratações - nenhuma equipa em particular), escreva -1, quando for indicar a equipa de destino.\n");
    }

    public void MenuContratacoes(){
        StringBuilder sb = new StringBuilder("\n-------------------------------------------\n\tJogadores para contratar\n-------------------------------------------\n");
        sb.append("-1) Voltar para o menu principal.\n");
        sb.append("0) Voltar a ver menu.\n");
        sb.append("1) Criar novo Jogador.\n");
        sb.append("2) Jogadores disponiveis para contratar.\n");
        sb.append("3) Associar jogador a uma equipa.\n");
        System.out.print(sb.toString());
    }
    
    public void JogadoresParaContrato(List<String> s){
        StringBuilder sb = new StringBuilder();
        int i = 1;           
        for(String aux: s){
            sb.append(i+":"+aux+"\n");
            i++;
        }
        System.out.print(sb.toString());        
    }
    
    public void tipoJogador(){
        StringBuilder sb = new StringBuilder("\n-----------Posicao do Jogadores-----------\n");
        sb.append("1)Guarda-Redes.\n");
        sb.append("2)Defesas.\n");
        sb.append("3)Medios.\n");
        sb.append("4)Laterais.\n");
        sb.append("5)Avançados.\n");
        System.out.print(sb.toString());
    }
    
    public void CaracteristicasComuns(){
        StringBuilder sb = new StringBuilder("Introduza os valores das seguintes caracteristicas (valores 0 a 100):\n");
        sb.append("Nome; Numero da Camisola; Velocidade ;Resistencia; Destreza; Impulsao; Jogo de Cabeça; Remate; Capacidade de passe; ");
        System.out.print(sb.toString());
    }
    
    public void novoGuardaRedes(){
        System.out.println("Elasticidade.");   
    }
    public void novoDefesas(){
        System.out.println("Marcação.");   
    }
    public void novoMedios(){
        System.out.println("Recuperacao de Bola");   
    }
    public void novoLaterais(){
        System.out.println("Cruzamentos.");   
    }
    public void novoAvançados(){
        System.out.println("Posicionamento");   
    }

    public void MenuJogos(){
        StringBuilder sb = new StringBuilder("\n-------------------------------------------\n\tMenu Jogos\n-------------------------------------------\n");
        sb.append("-1) Voltar para o Menu Principal.\n");
        sb.append("0) Voltar a ver o menu.\n");
        sb.append("1) Consultar todas os jogos do campeonato.\n");
        sb.append("2) Consultar uma jogo especifico.\n");
        System.out.print(sb.toString());
    }
    
    public void NovoJogo(){
        StringBuilder sb = new StringBuilder("\n-------------------------------------------\n\t\tNovo Jogo\n-------------------------------------------\n");
        sb.append("Escolha duas equipas (indicando o numero).\n");
        System.out.print(sb.toString());
    }

    public void AvisoEquipasJogo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Ok! Já definimos os jogadores que estarão em campo.\n");
        sb.append("Se pretende alterar a estratégia de cada equipa ou gerir a equipa (e as substituições), selecione:\n");

        System.out.print(sb.toString());
    }

    public void opEquipasJogo(){
        StringBuilder sb = new StringBuilder();
        sb.append("0) quando tiver concluido a gestão.\n");
        sb.append("1) para gerir a equipa da casa (primeira equipa).\n");
        sb.append("2) para gerir a equipa que joga fora (segunda equipa).\n");
        System.out.println(sb.toString());
    }

    public void gestaoEquipa(){
        StringBuilder sb = new StringBuilder();
        sb.append("0) Edição concluida. Voltar.\n");
        sb.append("1) Alterar tática da equipa (Se pretende alterar a tática, recomendamos que seja a primeira coisa a ser feita).\n");
        sb.append("2) Colocar outra jogador como titular.\n");
        sb.append("3) Definir as substituições que será feitas durante o jogo.\n");
        sb.append("4) Ver novamente a equipa.\n");
        System.out.println(sb.toString());
    }

    public void equipaPoucosJogadores(String nome){
        System.out.println("A equipa "+nome+" não tem jogadores suficientes para começar um jogo.\n");
    }

    public void EquipaEmCampo(String s){
        StringBuilder sb = new StringBuilder("\n-----------Equipa-----------\n");
        sb.append(s+"\n");
        System.out.print(sb.toString());
    }

    public void EquipaTitular(String s){
        StringBuilder sb = new StringBuilder("\n-----------Equipa Titular-----------\n");
        sb.append(s+"\n");
        System.out.print(sb.toString());
    }

    public void EstatísticasRemates(String s){
        StringBuilder sb = new StringBuilder("----------------------------------------Estatísticas Remates-----------------------------------------\n");
        sb.append(s);
        System.out.println(sb.toString());
    }

    public void EstatísticasRematesPercentagens(int percentRemPerigosoC, int percentRemPerigosoF, int percentGoloC, int percentGoloF){
        StringBuilder sb = new StringBuilder();
        sb.append("Remates Perigosos da Equipas da Casa (%)  : " + percentRemPerigosoC + "% | Golos da Equipa da Casa (%)  : " + percentGoloC + "%\n");
        sb.append("Remates Perigosos da Equipas Visitante (%): " + percentRemPerigosoF + "% | Golos da Equipa Visitante (%): " + percentGoloF + "%\n");
        System.out.println(sb.toString());
    }

    public void MinutosGolos(List<Integer> minsGolosCasa, List<Integer> minsGolosFora){
        StringBuilder sb = new StringBuilder();
        sb.append("Minutos Golos da Equipa da Casa  : ");
        for (Integer i : minsGolosCasa){
            sb.append(i+"''  " );
        }
        sb.append("\nMinutos Golos da Equipa Visitante: ");
        for (Integer i : minsGolosFora){
            sb.append(i+"''  ");
        }
        System.out.println(sb.toString());
    }
    
    public void versus (){
        System.out.print("\t**VS**\n");
    }
    
    public void resultado(String n1, double qual1, String n2, double qual2, int gcasa, int gfora, int gCasaInt, int gForaInt){
        StringBuilder sb = new StringBuilder("----------------------------------------Estatísticas Jogo--------------------------------------------\n");
        sb.append(n1 + " qualidade = " + qual1 + " | " + n2 + " quality = " + qual2 + "\n");
        sb.append(n1 + " "+ gcasa + " - " + gfora + " " + n2 + " (Resultado ao Intervalo: " + gCasaInt + " - " + gForaInt + ")");
        System.out.println(sb);
    }

    public void vencedor(int i, String nome1, String nome2){
        if (i == 0) System.out.println(nome1+" venceu");
        else if (i == 1) System.out.println("Empate");
        else System.out.println(nome2+" venceu");
    }
}
