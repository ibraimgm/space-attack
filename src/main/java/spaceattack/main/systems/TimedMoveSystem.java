package spaceattack.main.systems;

import java.util.Map.Entry;

import spaceattack.framework.GameIO;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.components.Position;
import spaceattack.main.components.TimedMove;

public final class TimedMoveSystem implements System
{

  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    for (Entry<Long, TimedMove> item : es.getAllComponents(TimedMove.class))
    {
      Long id = item.getKey();
      TimedMove tm = item.getValue();
      Position p = es.getComponent(id, Position.class);

      // we have no reason to do anything in this case
      if (p == null)
        continue;

      // consume time
      tm.step(delta);

      // time's up!
      if (tm.triggered())
      {
        p.x += tm.deltaX;
        p.y += tm.deltaY;

        if (tm.repeatable)
          tm.reset();
        else
          es.requestChange(ess -> ess.removeComponent(id, TimedMove.class));
      }
    }
  }

}
