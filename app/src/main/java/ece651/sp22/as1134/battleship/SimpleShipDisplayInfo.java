package ece651.sp22.as1134.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private T mydata;
  private T onHit;
  public SimpleShipDisplayInfo(T mydata, T onHit){
    this.mydata=mydata;
    this.onHit=onHit;
  }
  public T getInfo(Coordinate where, boolean hit){
     if(hit==true){
       return onHit;
     }else{
       return mydata;
     }
   }
}
