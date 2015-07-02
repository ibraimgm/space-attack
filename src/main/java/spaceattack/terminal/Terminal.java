package spaceattack.terminal;

import spaceattack.terminal.impl.BufferedTerminalIO;
import spaceattack.terminal.impl.WindowsTerminalIO;

public final class Terminal
{

  public static TerminalIO INSTANCE = get();

  private static TerminalIO get()
  {
    TerminalIO inner;

    String s = System.getProperty("os.name").toLowerCase();

    if (s.indexOf("win") >= 0)
      inner = new BufferedTerminalIO(new WindowsTerminalIO(), 80, 24, TerminalColor.VIVID_BLACK, TerminalColor.DULL_WHITE);
    else if (s.indexOf("linux") >= 0)
      inner = null;
    else
      inner = null;

    if (inner == null)
      throw new RuntimeException("Terminal not supported!");
    else
      return inner;
  }

}
