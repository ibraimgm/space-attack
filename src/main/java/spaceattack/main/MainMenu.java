package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public final class MainMenu implements Scenario
{

  @Override
  public void init(GameIO io)
  {
    // Nothing do do here
  }

  @Override
  public void pause(GameIO io)
  {
    // Nothing to do here
  }

  @Override
  public void resume(GameIO io)
  {
    // Nothing to do here
  }

  @Override
  public void update(GameIO io, double delta)
  {
    if (io.consumeKey() == VKey.ENTER)
      io.requestQuit();
  }

  @Override
  public void render(GameIO io, double delta)
  {
    Screen main = io.mainScreen();

    main.clear(TerminalColor.DULL_BLACK);
    main.drawText(10, 10, "This is the main screen - press enter");
    main.blit();
  }

  @Override
  public Scenario quit(GameIO io)
  {
    return new Gameplay();
  }

}
