package spaceattack.main.components;

import spaceattack.framework.ecs.Component;

public final class Position implements Component
{
  public int x;
  public int y;

  public Position(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
}
