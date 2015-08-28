package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Category;

public class Level3 extends AbstractLevel
{

  protected Level3(GameState state)
  {
    super(state);
  }

  @Override
  protected String getLevelShortName()
  {
    return "Swarm";
  }

  @Override
  protected String getLevelLongName()
  {
    return "Slow Swarm";
  }

  @Override
  protected int getLevelNumber()
  {
    return 3;
  }

  @Override
  protected void createAliens(GameIO io)
  {
    Screen s = io.mainScreen();
    Category cat = categories.get(0);

    for (int y = 0; y < 2; ++y)
      for (int x = GAME_START_X; x < s.getWidth(); ++x)
      {
        int n = x - GAME_START_X;
        n %= (y == 0) ? 5 : 4;

        if (n > 1)
        {
          long id = EntityFactory.makeCyanAlien(es, x, y, state);
          es.putComponent(id, cat);
        }
      }
  }

  @Override
  protected void createCategories()
  {
    categories.add(new Category("Cyan Aliens"));
  }

  @Override
  protected int getAvailableScore()
  {
    return categories.get(0).size() * 15;
  }

}
