package spaceattack.main.systems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import spaceattack.framework.GameIO;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.framework.ecs.System;
import spaceattack.main.GameState;
import spaceattack.main.components.Collision;
import spaceattack.main.components.Position;

public final class CollisionSystem implements System
{
  private GameState state;

  public CollisionSystem(GameState state)
  {
    this.state = state;
  }

  @Override
  public void execute(EntitySystem es, GameIO io, double delta)
  {
    List<CollisionItem> list = new ArrayList<CollisionItem>();

    // mount a list of itens to check
    for (Entry<Long, Collision> item : es.getAllComponents(Collision.class))
    {
      Position p = es.getComponent(item.getKey(), Position.class);

      if (p == null)
        continue;

      // add to the list of itens to check
      CollisionItem c = new CollisionItem();
      c.id = item.getKey();
      c.collision = item.getValue();
      c.position = p;

      list.add(c);
    }

    // create an array and check for collisions

    CollisionItem[] data = new CollisionItem[list.size()];
    data = list.toArray(data);

    for (int i = 0; i < data.length; ++i)
      for (int j = i+1; j < data.length; ++j)
      {
        CollisionItem a = data[i];
        CollisionItem b = data[j];

        //if there's no colision, there's nothing to do
        if (!checkCollision(a, b))
          continue;

        // if an alien collides with a player shot, remove both
        if (checkTypes(a, b, Collision.Type.ALIEN, Collision.Type.PLAYER_SHOT))
        {
          es.requestRemove(a.id);
          es.requestRemove(b.id);
        }

        // if an alien shot collides with the orbit, just erase the shot
        if (checkTypes(a, b, Collision.Type.ALIEN_SHOT, Collision.Type.EARTH_ORBIT))
        {
          es.requestRemove(a.collision.type == Collision.Type.ALIEN_SHOT ? a.id : b.id);
        }

        // if an alien reaches earth, it's game over
        if (checkTypes(a, b, Collision.Type.ALIEN, Collision.Type.EARTH_ORBIT))
        {
          state.takeDamage(99);
        }

        // if an alien shot collides with the player, register damage
        if (checkTypes(a, b, Collision.Type.ALIEN_SHOT, Collision.Type.PLAYER))
        {
          state.takeDamage(1);
          es.requestRemove(a.collision.type == Collision.Type.ALIEN_SHOT ? a.id : b.id);
        }

        // if an alien collides with the player, it's game over
        if (checkTypes(a, b, Collision.Type.ALIEN, Collision.Type.PLAYER))
        {
          state.takeDamage(99);
        }
      }
  }

  private boolean checkCollision(CollisionItem a, CollisionItem b)
  {
    return (a.position.x == b.position.x) &&
        (a.position.y == b.position.y);
  }

  private boolean checkTypes(CollisionItem a, CollisionItem b, Collision.Type typeA, Collision.Type typeB)
  {
    return ((a.collision.type == typeA) && (b.collision.type == typeB)) ||
        ((a.collision.type == typeB) && (b.collision.type == typeA));
  }

  private class CollisionItem
  {
    public Long id;
    public Collision collision;
    public Position position;
  }

}
