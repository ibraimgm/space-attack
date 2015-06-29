package spaceattack.main.components;

import spaceattack.framework.ecs.Component;

public final class Collision implements Component
{
  public enum Type
  {
    ALIEN_SHOT,
    PLAYER_SHOT,
    ALIEN,
    PLAYER,
    EARTH_ORBIT
  };

  public Type type;

  public Collision(Type type)
  {
    this.type = type;
  }
}
