package spaceattack.main.components;

import spaceattack.framework.ecs.Component;
import spaceattack.terminal.TerminalColor;

public final class TimedShot extends AbstractTimedComponent implements
    Component
{
  public double shotSpeed;
  public char shotChar;
  public TerminalColor shotBg;
  public TerminalColor shotFg;

  public TimedShot(double shotSpeed, char shotChar, TerminalColor shotBg, TerminalColor shotFg, boolean repeatable, double ms)
  {
    super(repeatable, ms);
    this.shotSpeed = shotSpeed;
    this.shotChar = shotChar;
    this.shotBg = shotBg;
    this.shotFg = shotFg;
  }
}
