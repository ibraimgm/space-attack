package spaceattack.framework;

public class GameRunner
{
  public double getCurrentTime()
  {
    return System.currentTimeMillis();
  }

  public Scenario processEvents(GameIO io, Scenario scenario)
  {
    int e = io.peekEvent();
    boolean handled = true;
    Scenario other = scenario;

    // handle every event that we can
    while (handled)
      switch (e)
      {
        case GEvents.EVT_PAUSE:
          scenario.pause(io);
          io.consumeEvent();
          break;

        case GEvents.EVT_RESUME:
          scenario.resume(io);
          io.consumeEvent();
          break;

        case GEvents.EVT_QUIT:
          other = scenario.quit(io);
          io.consumeEvent();
          handled = false;
          break;

        default: handled = false;
      }

    // if the scenariop changes, drop every pending event
    if (other != scenario)
    {
      while (io.peekEvent() != GEvents.EVT_NONE)
        io.consumeEvent();

      if (other != null)
        other.init(io);
    }

    // return the 'new' scenario
    return other;
  }

  public void run(double desiredFps, GameIO io, Scenario scenario)
  {
    double previous = getCurrentTime();
    double lag = 0.0;
    double ms = 1000.0 / desiredFps;

    scenario.init(io);

    while (scenario != null)
    {
      scenario = processEvents(io, scenario);
      double current = getCurrentTime();
      double elapsed = current - previous;
      previous = current;
      lag += elapsed;

      io.fetchInputs();

      while ((lag >= ms) && (scenario != null))
      {
        scenario.update(io, ms);
        lag -= ms;
        scenario = processEvents(io, scenario);
      }

      if (scenario != null)
        scenario.render(io, lag);
    }
  }
}
