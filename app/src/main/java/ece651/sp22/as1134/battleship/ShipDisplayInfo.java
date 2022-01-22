package ece651.sp22.as1134.battleship;

public interface ShipDisplayInfo<T> {
   public T getInfo(Coordinate where, boolean hit);
}
