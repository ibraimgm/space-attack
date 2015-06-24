package spaceattack.main;

import spaceattack.framework.GameRunner;
import spaceattack.framework.terminal.TerminalGameIO;
import spaceattack.terminal.TerminalFactory;


public class App
{

  public static void main(String[] args)
  {
    TerminalFactory.getInstance().setup();
    new GameRunner().run(60, new TerminalGameIO(), new MainMenu());
    TerminalFactory.getInstance().tearDown();
  }
}
