package ece651.sp22.as1134.battleship;

import java.util.HashSet;

public class Battleshipv2<T> extends BasicShip<T>  {
  final String name;
  public String getName(){
    return name;
  }
  public Battleshipv2(String name,Coordinate upperLeft,char orientation,ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> eneDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo,eneDisplayInfo);
    this.name=name;
  }
  public Battleshipv2(String name,Coordinate upperLeft, char orientation, T data, T onHit) {
    this(name,upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),new SimpleShipDisplayInfo<T>(null, data));
  }


  /*
   * method to store all the coordinate used by a battleship into a set
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft,char orientation){
    HashSet<Coordinate> battleset=new HashSet<Coordinate>();
    int x = upperLeft.getColumn();
    int y = upperLeft.getRow();
    if(orientation=='U'){
      battleset.add(new Coordinate(y,x+1));
      battleset.add(new Coordinate(y+1,x));
      battleset.add(new Coordinate(y+1,x+1));
      battleset.add(new Coordinate(y+1,x+2));
    }else if(orientation=='R'){
      battleset.add(new Coordinate(y,x));
      battleset.add(new Coordinate(y+1,x));
      battleset.add(new Coordinate(y+2,x));
      battleset.add(new Coordinate(y+1,x+1));
    }else if(orientation=='D'){
      battleset.add(new Coordinate(y,x));
      battleset.add(new Coordinate(y,x+1));
      battleset.add(new Coordinate(y,x+2));
      battleset.add(new Coordinate(y+1,x+1));
    }else if(orientation=='L'){
      battleset.add(new Coordinate(y,x+1));
      battleset.add(new Coordinate(y+1,x));
      battleset.add(new Coordinate(y+1,x+1));
      battleset.add(new Coordinate(y+2,x+1));
    }else{
      throw new IllegalArgumentException("the orientation is invalid for this ship");
    }
    return battleset;
  }
}
