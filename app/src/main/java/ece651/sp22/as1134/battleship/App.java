/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ece651.sp22.as1134.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class App {
  //  public String getGreeting() {
  //    return "Hello World!";
  // }
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  
  public App(Board<Character> theBoard,Reader inputSource, PrintStream out) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
  }
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }
  public void doOnePlacement() throws IOException{
    String prompt="Where would you like to put your ship?";
    Placement p = readPlacement(prompt);
    RectangleShip<Character> s = new RectangleShip<Character>(p.getWhere(), 's', '*');
    theBoard.tryAddShip(s);
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());
    

  }

  public static void main(String[] args) throws IOException{
    Board<Character> b=new BattleShipBoard<Character>(10,20);
     BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
     App app=new App(b,input,System.out);
     app.doOnePlacement();

  }    

}
