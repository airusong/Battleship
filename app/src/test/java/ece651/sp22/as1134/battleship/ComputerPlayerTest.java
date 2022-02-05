package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {
  private ComputerPlayer createComputerPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new ComputerPlayer(board, input, output, shipFactory,"computer");
  }

  @Test
  public void test_playoneturn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerPlayer computer=createComputerPlayer(10, 20, " ",bytes );
    assertEquals(computer.getName(),"computer");
    computer.doPlacementPhase();
    Board<Character> playerboard=new BattleShipBoard<Character>(10, 20, 'X');
    BoardTextView playerview=new BoardTextView(playerboard);
    BoardTextView computerview=new BoardTextView(computer.getBoard());
    computer.playOneturn(playerboard, "", "");
    String expected="  0|1|2|3|4|5|6|7|8|9\n"+
                    "A s|s| |c| | | | | |  A\n"+
                    "B s|s| |c| | | | | |  B\n"+
                    "C d|d|d|c|c| | | | |  C\n"+
                    "D d|d|d|c|c| | | | |  D\n"+
                    "E d|d|d|c|c| | | | |  E\n"+
                    "F b| | |c| | | | | |  F\n"+
                    "G b|b| |c|c| | | | |  G\n"+
                    "H b| | |c|c| | | | |  H\n"+
                    "I b| | | |c| | | | |  I\n"+
                    "J b|b| | | | | | | |  J\n"+
                    "K b| | | | | | | | |  K\n"+
                    "L b| | | | | | | | |  L\n"+
                    "M b|b| | | | | | | |  M\n"+
                    "N b| | | | | | | | |  N\n"+
                    "O  | | | | | | | | |  O\n"+
                    "P  | | | | | | | | |  P\n"+
                    "Q  | | | | | | | | |  Q\n"+
                    "R  | | | | | | | | |  R\n"+
                    "S  | | | | | | | | |  S\n"+
                    "T  | | | | | | | | |  T\n"+
                    "  0|1|2|3|4|5|6|7|8|9\n";

    assertEquals(computerview.displayMyOwnBoard(),expected);
    //    assertEquals(playerview.displayEnemyBoard(), "");
  }

}
