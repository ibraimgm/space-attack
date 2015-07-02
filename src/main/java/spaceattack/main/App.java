package spaceattack.main;

import spaceattack.framework.GameRunner;
import spaceattack.framework.terminal.TerminalGameIO;
import spaceattack.terminal.Terminal;


public class App
{

  public static void main(String[] args)
  {
    Terminal.INSTANCE.setup();
    new GameRunner().run(60, new TerminalGameIO(), new MainMenu());
    Terminal.INSTANCE.tearDown();
  }
}
