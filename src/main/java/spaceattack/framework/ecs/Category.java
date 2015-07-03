package spaceattack.framework.ecs;

import java.util.ArrayList;
import java.util.List;

public final class Category implements Component
{
  private String name;
  List<Long> entities;

  public Category()
  {
    entities = new ArrayList<Long>();
  }

  public Category(String name)
  {
    this();
    this.name = name;
  }

  public void add(Long id)
  {
    entities.add(id);
  }

  public void remove(Long id)
  {
    entities.remove(id);
  }

  public int size()
  {
    return entities.size();
  }

  public String getName()
  {
    return name;
  }
}
