package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Jogador {
    private static final ArrayList<String> VALID_COLORS = new ArrayList<>(List.of(new String[]{"Purple", "Green", "Brown", "Blue"}));

    private final int id;
    private final String nome;
    private final String linguagens;
    private String cor;
    private int posicao;
    private boolean derrotado;

    public Jogador( int id, String nome, String linguagensFavoritas) {
        this.id = id;
        this.nome = nome;
        this.linguagens = linguagensFavoritas;
        //this.cor = CORES [id - 1];
        this.posicao = 1; // Posição inicial
    }

    public Jogador(String[] info) {
        this.id = Integer.parseInt(info[0]);
        this.nome = info[1];
        this.linguagens = info[2];
        this.cor = info[3];
        this.posicao = 1;
        this.derrotado = false;
    }

    public int id() {
        return id;
    }

    public String nome() { return nome; }

    public String linguagens() {
        String[] linguagens = this.linguagens.split(";");
        Arrays.sort(linguagens);

        return String.join(";", linguagens);
    }

    public String cor() {
        return this.cor;
    }

    public int posicao() {
        return posicao;
    }

    public void avanca(int casas) {
        this.posicao += casas;
    }

    public static boolean valida(String[] info, HashMap<Integer, Jogador> jogadores) {
        try {
            int id = Integer.parseInt(info[0]);
            String nome = info[1];
            String color = info[3];

            // invalid id
            if (id <= 0) {
                return false;
            }

            // duplicated id
            if (jogadores.containsKey(id)) {
                return false;
            }

            // empty name
            if (nome.isEmpty()) {
                return false;
            }

            // invalid color
            if (!VALID_COLORS.contains(color)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.id + " | " + this.nome + " | " + this.posicao + " | " + this.linguagens + " | " + (this.derrotado ? "Derrotado" : "Em Jogo");
    }

    public String[] toArray() {
        return new String[] {String.valueOf(this.id), this.nome, this.linguagens, this.cor, String.valueOf(this.posicao)};
    }
}
