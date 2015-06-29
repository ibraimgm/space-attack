package spaceattack.framework.terminal;

import java.util.ArrayList;
import java.util.List;

import spaceattack.framework.AbstractGameIO;
import spaceattack.framework.Screen;
import spaceattack.terminal.Terminal;
import spaceattack.terminal.TerminalFactory;
import spaceattack.terminal.VKey;

public final class TerminalGameIO extends AbstractGameIO
{
  private List<VKey> inputs = new ArrayList<VKey>();
  private Screen mainScreen = new TerminalScreen(0, 0, 80, 24);


  @Override
  public VKey peekKey()
  {
    if (inputs.isEmpty())
      return VKey.NONE;
    else
      return inputs.get(0);
  }

  @Override
  public VKey consumeKey()
  {
    if (inputs.isEmpty())
      return VKey.NONE;
    else
      return inputs.remove(0);
  }

  @Override
  public void fetchInputs()
  {
    Terminal t = TerminalFactory.getInstance();
    VKey k;

    do
    {
      k = t.readKey();

      if (k != VKey.NONE)
        inputs.add(k);

    } while (k != VKey.NONE);
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
