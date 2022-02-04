package ece651.sp22.as1134.battleship;

import java.io.IOException;

public abstract class Player {
  abstract Board<Character> getBoard();
   
  abstract void doPlacementPhase() throws IOException;

  abstract String getName();

  abstract void playOneturn(Board<Character> enemyBoard,String myHeader, String enemyHeader) throws IOException;
}
