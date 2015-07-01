package spaceattack.main.components;

import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Component;

public final class BoundsCheck implements Component
{
  public enum OutOfBounds
  {
    DESTROY_ENTITY,
    MAKE_INVISIBLE,
    ADJUST_POSITION
  };

  public int minX;
  public int maxX;
  public int minY;
  public int maxY;
  public OutOfBounds behavior;

  public BoundsCheck(int minX, int maxX, int minY, int maxY, OutOfBounds behavior)
  {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.behavior = behavior;
  }

  public boolean isOOB(int x, int y)
  {
    return
        (x < minX) ||
        (y < minY) ||
        (x > maxX) ||
        (y > maxY);
  }

  public static BoundsCheck fromScreen(Screen screen, OutOfBounds behavior)
  {
    return new BoundsCheck(screen.getX(), screen.getWidth() - 1, screen.getY(), screen.getHeight() - 1, behavior);
  }

  public static BoundsCheck fixedX(int x, int minY, int maxY, OutOfBounds behavior)
  {
    return new BoundsCheck(x, x, minY, maxY, behavior);
  }

  public static BoundsCheck fixedY(int y, int minX, int maxX, OutOfBounds behavior)
  {
    return new BoundsCheck(minX, maxX, y, y, behavior);
  }
}
