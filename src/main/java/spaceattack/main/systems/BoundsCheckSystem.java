package spaceattack.main.systems;

import java.util.Map.Entry;

import spaceattack.framework.GameIO;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.components.BoundsCheck;
import spaceattack.main.components.Draw;
import spaceattack.main.components.Position;

public final class BoundsCheckSystem implements System
{

  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    for (Entry<Long, BoundsCheck> item : es.getAllComponents(BoundsCheck.class))
    {
      Long id = item.getKey();
      BoundsCheck b = item.getValue();
      Position p = es.getComponent(id, Position.class);

      if (p == null)
        return;

      if (b.isOOB(p.x, p.y))
        switch (b.behavior)
        {
          case DESTROY_ENTITY:
            es.requestRemove(id);
            break;

          case MAKE_INVISIBLE:
            Draw d = es.getComponent(id, Draw.class);

            if (d != null)
              d.visible = false;
            break;

          case ADJUST_POSITION:
            if (p.x < b.minX)
              p.x = b.minX;
            else if (p.x > b.maxX)
              p.x = b.maxX;

            if (p.y < b.minY)
              p.y = b.minY;
            else if (p.y > b.maxY)
              p.y = b.maxY;
            break;
        }
    }
  }

}
