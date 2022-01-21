package ece651.sp22.as1134.battleship;
/**
   * Construct Placement class with specifed corrdinate and orientation
   * @param where is the coordinate of the Placement
   * @param orientation is the direction of the Placement
   */
public class Placement {
  final Coordinate where;
  final char orientation;
  public char getOrientation(){
    return orientation;
  }
  public Coordinate getWhere(){
    return where;
  }
  @Override
  public String toString() {
    return "("+orientation+ ")";
  }
  @Override
  public int hashCode() {
    return where.hashCode()+toString().hashCode();
  }
  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Placement c=(Placement) o;
      return where.equals(c.where) && orientation==c.orientation;
    }
    return false;
  }
  public Placement(Coordinate whe,char ori){
    this.where=whe;
    this.orientation=Character.toUpperCase(ori);
  }
  public Placement(String input){
    int length=input.length();
    if(length!=3){
      throw new IllegalArgumentException("input is wrong");
    }
    String coordinate=input.substring(0,2);
    char ori=input.charAt(2);
    if(ori!='H'&&ori!='h'&&ori!='V'&&ori!='v'){
       throw new IllegalArgumentException("input is wrong");
    }
    Coordinate whe=new Coordinate(coordinate);
    this.where=whe;
    this.orientation=Character.toUpperCase(ori);
  }
}
