
package spaceattack.main.components;

import spaceattack.framework.ecs.Component;

public class AbstractTimedComponent implements Component
{
  public boolean repeatable;
  protected double ms;
  private double timeLeft;
  private boolean triggered;

  public AbstractTimedComponent(boolean repeatable, double ms)
  {
    this.repeatable = repeatable;
    this.ms = ms;
    reset();
  }

  public void step(double delta)
  {
    if (triggered)
      return;

    timeLeft -= delta;
    triggered = timeLeft <= 0;
  }

  public void reset()
  {
    triggered = false;
    timeLeft = ms;
  }

  public boolean triggered()
  {
    return triggered;
  }

}
