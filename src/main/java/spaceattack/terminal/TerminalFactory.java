package spaceattack.terminal;

import spaceattack.terminal.platform.win32.WindowsTerminal;

public final class TerminalFactory
{
  private static Terminal terminal;
  
  private TerminalFactory()
  {
  }
  
  public static Terminal getInstance()
  {
    if (terminal != null)
      return terminal;
    
    String s = System.getProperty("os.name").toLowerCase();
    
    if (s.indexOf("win") >= 0)
      terminal = new WindowsTerminal();
    else if (s.indexOf("linux") >= 0)
      terminal = null;
    
    if (terminal == null)
      throw new RuntimeException("Terminal not supported!");
    else
      return terminal;
  }
}
