/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ece651.sp22.as1134.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
/*
 * define two player
 * @param player1
 * @param player2
 */
public class App {
  // public String getGreeting() {
  // return "Hello World!";
  // }
  final Player player1;
  final Player player2;
  //final BufferedReader inputReader;
  //final PrintStream out;

  //  final ComputerPlayer computer;
  public App(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;
    //this.inputReader =(BufferedReader)inputSource;
    //this.out = out;

  }
  /*
   * method to let player1 place all the ships on board, then player2
   */
  public void doPlacementPhase() throws IOException {
    player1.doPlacementPhase();
    player2.doPlacementPhase();
  }

  /*
   * let player1 play a turn, then see if player 2 has lost. Then let player 2
   * play a turn and see if player 1 has lost. It should repeat this until one
   * player has lost, then report the outcome.
   */
  public void doAttackingPhase() throws IOException {
    while (true) {
      player1.playOneturn(player2.getBoard(), "Your ocean", "Player " + player2.getName() + "'s ocean");
      if (player2.getBoard().checklose() == true && player1.getBoard().checklose() != true) {
        System.out.println("Player " + player1.getName() + " wins");
        break;
      }
      player2.playOneturn(player1.getBoard(), "Your ocean", "Player " + player1.getName() + "'s ocean");
      if (player1.getBoard().checklose() == true && player2.getBoard().checklose() != true) {
        System.out.println("Player " + player2.getName() + " wins");
        break;
      }
    }
  }

  public static void main(String[] args) throws IOException {

    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b3 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b4 = new BattleShipBoard<Character>(10, 20, 'X');
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    //    V1ShipFactory factory = new V1ShipFactory();
    V2ShipFactory factory=new V2ShipFactory();
    TextPlayer p1 = new TextPlayer(b1, input, System.out, factory, "A");
    TextPlayer p2 = new TextPlayer(b2, input, System.out, factory, "B");
    ComputerPlayer pp=new ComputerPlayer(b3, input, System.out, factory,"computer");
    ComputerPlayer pp2=new ComputerPlayer(b4, input, System.out, factory,"computer");
    System.out.println("Which mode do you waht to choose\n1. player and player\n2. player and computer\n"+
                       "3. computer and player\n"+"4. computer and computer");
    String i=input.readLine();
    App app=null;
    if(i.equals("1")){
      app = new App(p1, p2);
    }
    if(i.equals("2")){
      app = new App(p1, pp);
    }
    if(i.equals("3")){
      app = new App(pp, p2);
    }
    if(i.equals("4")){
      app = new App(pp,pp2);
    }
    
    app.doPlacementPhase();
    app.doAttackingPhase();
  }

}
