package spaceattack.main.systems;

import java.util.Map.Entry;

import spaceattack.framework.GameIO;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.components.Collision;
import spaceattack.main.components.Draw;
import spaceattack.main.components.Position;
import spaceattack.main.components.Position.OOB;
import spaceattack.main.components.TimedMove;
import spaceattack.main.components.TimedShot;

public final class TimedShotSystem implements System
{

  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    for (Entry<Long, TimedShot> item : es.getAllComponents(TimedShot.class))
    {
      Long id = item.getKey();
      TimedShot ts = item.getValue();
      Position p = es.getComponent(id, Position.class);

      if (p == null)
        continue;

      ts.step(delta);

      if (ts.triggered())
      {
        es.requestChange(ess -> {
          long shotId = ess.newEntity();

          ess.putComponent(shotId, new Position(p.x, p.y + 1, OOB.VANISH));
          ess.putComponent(shotId, new Draw(ts.shotFg, ts.shotBg, ts.shotChar));
          ess.putComponent(shotId, new TimedMove(0, 1, true, ts.shotSpeed));
          ess.putComponent(shotId, new Collision(Collision.Type.ALIEN_SHOT));
        });

        if (ts.repeatable)
          ts.reset();
        else
          es.requestChange(ess -> ess.removeComponent(id, TimedShot.class));
      }
    }

  }

}
