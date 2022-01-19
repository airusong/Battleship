package ece651.sp22.as1134.battleship;

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

  public String displayMyOwnBoard() {
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    int value = 65;
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      String name = Character.toString(value);
      ans.append(name);
      ans.append(" ");
      for (int j = 0; j < toDisplay.getWidth() - 1; j++) {
        ans.append(" ");
        ans.append("|");
      }
      ans.append("  ");
      ans.append(name);
      value++;
      ans.append("\n");
    }
    ans.append(makeHeader());
    return ans.toString();
    // this is a placeholder for the moment
  }

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
}
