package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class GameManager {
    static HashMap <Integer, Jogador> jogadores; // initialize map inside createInitialBoard to avoid persistent data during tests
    static Jogador vencedor;
    static Tabuleiro tabuleiro;
    static int nrTurnos = 0;

    public GameManager() {}

    public boolean createInitialBoard(String[][] playerInfo, int worldSize){
        if (playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        if (worldSize < playerInfo.length*2) {
            return false;
        }

        jogadores = new HashMap<>();
        for (String[] j : playerInfo) {
            if (!Jogador.valida(j, jogadores)) {
                return false;
            }

            Jogador jogador = new Jogador(j);
            jogadores.put(jogador.id(), jogador);
        }

        tabuleiro = new Tabuleiro(worldSize);
        vencedor = null;
        nrTurnos = 1;

        return true;
    }

    public String getImagePng(int nrSquare) {
        return tabuleiro.slotImage(nrSquare);
    }

    public String[] getProgrammerInfo(int id){
        Jogador jogador = jogadores.get(id);
        if (jogador != null) {
            return jogador.toArray();
        }
        return null;
    }

    public String getProgrammerInfoAsStr(int id){
        Jogador jogador = jogadores.get(id);
        if (jogador != null) {
            return jogador.toString();
        }
        return null;
    }

    public String[] getSlotInfo(int slot){
        if (slot < 1 || slot > tabuleiro.tamanho()) {
            return null;
        }

        String[] res = new String[]{""};

        for (Jogador jogador : jogadores.values()) {
            if (jogador.posicao() == slot) {
                res[0] += jogador.id() + ",";
            }
        }

        if (!res[0].isEmpty()) {
            res[0] = res[0].substring(0, res[0].length()-2);
        }

        return res;
    }

    public int getCurrentPlayerID(){
        ArrayList<Integer> ids = new ArrayList<>(jogadores.keySet());
        ids.sort(Integer::compareTo);

        return (nrTurnos-1) % ids.size();
    }

    public boolean moveCurrentPlayer(int spaces){
        if (spaces < 1 || spaces > 6) {
            return false;
        }

        Jogador jogador = jogadores.get(getCurrentPlayerID());

        int pos = jogador.posicao();
        spaces = (pos + spaces > tabuleiro.tamanho()) ? tabuleiro.tamanho() - pos : spaces;

        jogador.avanca(spaces);
        nrTurnos++;

        // set winner
        if (pos + spaces > tabuleiro.tamanho()) {
           if (vencedor == null) {
               vencedor = jogador;
           }
        }

        return true;
    }

    public boolean gameIsOver(){
        return !getSlotInfo(tabuleiro.tamanho())[0].isEmpty();
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> res = new ArrayList<>();

        res.add("THE GREAT PROGRAMMING JOURNEY");
        res.add("");
        res.add("NR. DE TURNOS");
        res.add(String.valueOf(nrTurnos));
        res.add("");
        res.add("VENCEDOR");
        res.add(((vencedor != null) ? vencedor.nome() : ""));
        res.add("");
        res.add("RESTANTES");
        for (Jogador jogador : jogadores.values()) {
            if (vencedor != null) {
                if (vencedor.id() == jogador.id()) {
                    continue;
                }
            }

            res.add(jogador.nome());
        }

        return res;
    }

    public JPanel getAuthorsPanel() {
        return new JPanel();
    }

    public HashMap<String, String> customizeBoard() {
        return new HashMap<>();
    }

    // ??
    public Jogador getJogador(int id) {
        return jogadores.get(id);
    }

    // ??
    public Jogador getJogadorAtual() {
        return jogadores.get(getCurrentPlayerID());
    }
}
