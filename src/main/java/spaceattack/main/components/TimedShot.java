package spaceattack.main.components;

import spaceattack.framework.ecs.Component;

public final class TimedShot extends AbstractTimedComponent implements
    Component
{
  public double shotSpeed;
  public char shotChar;
  public int shotBg;
  public int shotFg;
  
  public TimedShot(double shotSpeed, char shotChar, int shotBg, int shotFg, boolean repeatable, double ms)
  {    
    super(repeatable, ms);
    this.shotSpeed = shotSpeed;
    this.shotChar = shotChar;
    this.shotBg = shotBg;
    this.shotFg = shotFg;    
  }
}
