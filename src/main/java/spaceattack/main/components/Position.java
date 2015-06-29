package spaceattack.main.components;

import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Component;

public final class Position implements Component
{
  public enum OOB
  {
    VANISH,
    NO_DRAW,
    KEEP_BOUNDS
  };

  public int x;
  public int y;
  public OOB oob;

  public Position(int x, int y, OOB oob)
  {
    this.x = x;
    this.y = y;
    this.oob = oob;
  }

  public Position(int x, int y)
  {
    this.x = x;
    this.y = y;
    this.oob = OOB.NO_DRAW;
  }

  public boolean isOOB(Screen screen)
  {
    return
        (x < screen.getX()) ||
        (y < screen.getY()) ||
        (x >= screen.getWidth()) ||
        (y >= screen.getHeight());
  }
}
