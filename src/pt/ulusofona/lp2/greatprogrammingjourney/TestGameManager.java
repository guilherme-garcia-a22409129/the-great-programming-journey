package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {
    GameManager gm;
    String[][] players;

    // initialize a new setup every time a test executes...
    @BeforeEach
    public void setUp() {
        gm = new GameManager();
        players = new String[][]{
                {"1", "Han Solo", "Java;Python", "Green"},
                {"2", "Darth Vader", "C++;JavaScript", "Purple"}
        };
    }

    // test createInitialBoard with valid inputs
    @Test
    public void testCreateInitialBoardValido() {
        boolean result = gm.createInitialBoard(players, 10);
        assertTrue(result, "Deve criar o tabuleiro com 2 jogadores e tamanho suficiente");
    }

    // test createInitialBoard with invalid color
    @Test
    public void testCreateInitialBoardCorInvalida() {
        String[][] jogadoresInvalidos = {
                {"1", "Han Solo", "Java;Python", "Verde"},  // cor inválida
                {"2", "Darth Vader", "C++;JavaScript", "Blue"}
        };
        boolean result = gm.createInitialBoard(jogadoresInvalidos, 10);
        assertFalse(result, "Não deve aceitar cores inválidas");
    }

    // test getCurrentPlayerID
    @Test
    public void testGetCurrentPlayerID() {
        gm.createInitialBoard(players, 10);
        int current = gm.getCurrentPlayerID();
        assertEquals(1, current, "O primeiro jogador deve ter ID 1");
    }

    // test moveCurrentPlayer
    @Test
    public void testMoveCurrentPlayer() {
        gm.createInitialBoard(players, 10);

        boolean result = gm.moveCurrentPlayer(3);
        assertTrue(result, "Mover 3 casas deve ser permitido");

        String[] info = gm.getProgrammerInfo(1);
        assertEquals("4", info[4], "Após mover 3 casas, o jogador 1 deve estar na casa 4");

        int current = gm.getCurrentPlayerID();
        assertEquals(2, current, "Depois do Han Solo jogar, deve ser a vez do Darth Vader");
    }

    // test gameIsOver
    @Test
    public void testGameIsOver() {
        gm.createInitialBoard(players, 4);

        gm.moveCurrentPlayer(4);

        assertTrue(gm.gameIsOver(), "O jogo deve acabar quando alguém chega ao fim");
    }
}
