package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.main.util.DrawUtil;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public class Congratulations implements Scenario
{
  GameState state;

  public Congratulations(GameState state)
  {
    this.state = state;
  }

  @Override
  public void init(GameIO io)
  {
    // Nothing to do here
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
    VKey key = io.consumeKey();

    if (key == VKey.ENTER)
      io.requestQuit();
  }

  @Override
  public void render(GameIO io, double delta)
  {
    Screen s = io.mainScreen();
    s.setBackground(TerminalColor.DULL_YELLOW);
    s.setForeground(TerminalColor.DULL_BLACK);

    DrawUtil.drawBanner(io, "C O N G R A T U L A T I O N S ! ! !", "Now, try to beat a harder challenge!");
    s.blit();
  }

  @Override
  public Scenario quit(GameIO io)
  {
    state.nextCycle();
    state.recoverHp();
    return new Level1(state);
  }

  @Override
  public void fps(GameIO io, int fps)
  {
    // Nothing to do here
  }

}
