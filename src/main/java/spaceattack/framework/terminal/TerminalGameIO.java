package spaceattack.framework.terminal;

import spaceattack.framework.AbstractGameIO;
import spaceattack.framework.Screen;
import spaceattack.terminal.TerminalFactory;
import spaceattack.terminal.VKey;

public final class TerminalGameIO extends AbstractGameIO
{
  private Screen mainScreen = new TerminalScreen(0, 0, 80, 24);

  @Override
  public VKey getKey()
  {
    return TerminalFactory.getInstance().readKey();
  }

  @Override
  public void fetchInputs()
  {
    // Nothing to do here. We do not have an input queue or anything like that.
  }

  @Override
  public Screen mainScreen()
  {
    return mainScreen;
  }

  @Override
  public Screen createScreen(Screen source, int x, int y, int width, int height)
  {
    return new TerminalScreen(source.getX() + x, 
                              source.getY() + y, 
                              width, 
                              height);
  }

  @Override
  public void destroyScreen(Screen target)
  {
    // Nothing to do here.
  }
}
