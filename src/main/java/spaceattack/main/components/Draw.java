package spaceattack.main.components;

import spaceattack.framework.ecs.Component;

public final class Draw implements Component
{
  public int fg;
  public int bg;
  public char c;
  public boolean visible = true;

  public Draw(int fg, int bg, char c)
  {
    this.fg = fg;
    this.bg = bg;
    this.c = c;
  }
}
