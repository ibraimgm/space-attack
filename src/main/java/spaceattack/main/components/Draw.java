package spaceattack.main.components;

import spaceattack.framework.ecs.Component;
import spaceattack.terminal.TerminalColor;

public final class Draw implements Component
{
  public TerminalColor fg;
  public TerminalColor bg;
  public char c;
  public boolean visible = true;

  public Draw(TerminalColor fg, TerminalColor bg, char c)
  {
    this.fg = fg;
    this.bg = bg;
    this.c = c;
  }
}
