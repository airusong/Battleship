package ece651.sp22.as1134.battleship;
/*
 * constrcut a class to store on the message that are going to display on the board
 * @param mydata is the original message 
 * @param onHit is the message to display after hitted
 */
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
