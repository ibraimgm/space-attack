package spaceattack.main.systems;

import java.util.Map.Entry;

import spaceattack.framework.GameIO;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.components.Draw;
import spaceattack.main.components.Position;

public final class BoundsCheckSystem implements System
{

  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    Screen s = io.mainScreen();

    for (Entry<Long, Position> item : es.getAllComponents(Position.class))
    {
      Long id = item.getKey();
      Position p = item.getValue();

      if (p.isOOB(s))
        switch (p.oob)
        {
          case VANISH:
            es.requestRemove(id);
            break;

          case NO_DRAW:
            Draw d = es.getComponent(id, Draw.class);

            if (d != null)
              d.visible = false;
            break;

          case KEEP_BOUNDS:
            if (p.x < s.getX())
              p.x = s.getX();
            else if (p.x >= s.getWidth())
              p.x = s.getWidth() - 1;

            if (p.y < s.getY())
              p.y = s.getY();
            else if (p.y >= s.getHeight())
              p.y = s.getHeight() - 1;
            break;
        }
    }
  }

}
