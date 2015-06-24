package spaceattack.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameIO implements GameIO
{
  private List<Integer> events = new ArrayList<Integer>();

  @Override
  public int peekEvent()
  {
    if (events.isEmpty())
      return GEvents.EVT_NONE;
    else
      return events.get(0);
  }

  @Override
  public int consumeEvent()
  {
    if (events.isEmpty())
      return GEvents.EVT_NONE;
    else
    {
      int e = events.get(0);
      events.remove(0);
      return e;
    }
  }

  @Override
  public void requestCustom(int event)
  {
    events.add(event);
  }

}
