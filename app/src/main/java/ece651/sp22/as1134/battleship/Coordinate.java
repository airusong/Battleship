package ece651.sp22.as1134.battleship;

public class Coordinate {
  private final int row;
  private final int column;
  public int getRow(){
    return row;
  }
  public int getColumn(){
    return column;
  }
  public Coordinate(int r, int c){
    this.row=r;
    this.column=c;
  }
  public Coordinate(String descr){
    int length=descr.length();
    int r;
    int c;
    if(length<=1||length>2){
      throw new IllegalArgumentException("input string wrong");
    }
    char rowLetter=descr.charAt(0);
    if (rowLetter < 'A' || rowLetter > 'Z'){
      throw new IllegalArgumentException("input string wrong");
    }else{
      r=rowLetter - 'A';
    }
    char columnLetter=descr.charAt(1);
    if(columnLetter<'0'||columnLetter>'9'){
      throw new IllegalArgumentException("input string wrong");
    }else{
      c=columnLetter-'0';
    }
    this.row=r;
    this.column=c;
  }
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }
  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
