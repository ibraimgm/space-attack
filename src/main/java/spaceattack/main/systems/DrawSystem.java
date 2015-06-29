package spaceattack.main.systems;

import java.util.Map.Entry;

import spaceattack.framework.GameIO;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.components.Draw;
import spaceattack.main.components.Position;

public final class DrawSystem implements System
{
  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    Screen s = io.mainScreen();

    for (Entry<Long, Draw> item : es.getAllComponents(Draw.class))
    {
      Draw d = item.getValue();
      Position p = es.getComponent(item.getKey(), Position.class);

      if ((p != null) && (d.visible))
      {
        s.setBackground(d.bg);
        s.setForeground(d.fg);
        s.drawText(p.x, p.y, d.c);
      }
    }
  }
}
