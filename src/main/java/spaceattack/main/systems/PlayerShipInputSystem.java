package spaceattack.main.systems;

import java.util.Map.Entry;

import spaceattack.framework.GameIO;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.components.PlayerShipInput;

public final class PlayerShipInputSystem implements System
{

  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    for (Entry<Long, PlayerShipInput> item : es.getAllComponents(PlayerShipInput.class))
      item.getValue().execute(item.getKey(), es, io);
  }

}
