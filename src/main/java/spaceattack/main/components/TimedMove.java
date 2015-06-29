package spaceattack.main.components;

import spaceattack.framework.ecs.Component;

public final class TimedMove extends AbstractTimedComponent implements Component
{
  public int deltaX;
  public int deltaY;
  
  public TimedMove(int deltaX, int deltaY, boolean repeatable, double ms)
  {
    super(repeatable, ms);
    this.deltaX = deltaX;
    this.deltaY = deltaY;    
  }
}
