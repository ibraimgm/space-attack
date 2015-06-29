package spaceattack.framework.ecs;

import spaceattack.framework.GameIO;

public interface System
{
  void execute(EntitySystem es, GameIO io, double delta);
}
