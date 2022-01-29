package ece651.sp22.as1134.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  /**
   * method to construct the main body of the board,for self board and for enemy board
   */  
   public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

   public String displayEnemyBoard() {
     return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
   }
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn){
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    int value = 65;
    int row=toDisplay.getHeight();;
    int column=toDisplay.getWidth();
    for (int i = 0; i < row; i++) {
      String name = Character.toString(value);
      ans.append(name);
      ans.append(" ");
      String sep = "";
      for (int j = 0; j < column; j++) {
        Coordinate c=new Coordinate(i,j);
        ans.append(sep);
        if(getSquareFn.apply(c)==null){
            ans.append(" ");
        }else{
          ans.append(getSquareFn.apply(c));
        }
        sep= "|";
      }
      ans.append(" ");
      ans.append(name);
      value++;
      ans.append("\n");
    }
    ans.append(makeHeader());
    return ans.toString();
    // this is a placeholder for the moment
  }
  /* 
   * method to construct the header for the board
   */
  public String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
  /*
   *method to connect two ocean
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    int width=toDisplay.getWidth();
    int height=toDisplay.getHeight();
    StringBuilder ans=new StringBuilder("     ");
    String [] mylines = displayMyOwnBoard().split("\n");
    String [] enemylines = enemyView.displayEnemyBoard().split("\n");
    ans.append(myHeader);
    int length=myHeader.length();
    for(int i=0;i<2*width+17-length;i++){
      ans.append(" ");
    }
    ans.append(enemyHeader);
    ans.append("\n");
    String space="                ";
    for(int i=0;i<=height+1;i++){
      ans.append(mylines[i]);
      ans.append(space);
      if(i==0||i==height+1){
        ans.append("  ");
      }
      ans.append(enemylines[i]);
      ans.append("\n");
    }
    return ans.toString();
  }
}
