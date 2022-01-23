package ece651.sp22.as1134.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character>{
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name){
    Ship<Character> s;
    if(where.getOrientation()=='v'||where.getOrientation()=='V'){
    s=new RectangleShip<Character>(name,where.getWhere(),w,h,letter,'*');
    }else{
    s=new RectangleShip<Character>(name,where.getWhere(),h,w,letter,'*');
    }
    return s;
  }

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    // TODO Auto-generated method stub
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    // TODO Auto-generated method stub
    return createShip(where, 1, 4, 'b', "BattleShip");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    // TODO Auto-generated method stub
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    // TODO Auto-generated method stub
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

}
