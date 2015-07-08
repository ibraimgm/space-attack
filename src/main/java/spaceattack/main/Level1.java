package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Category;

public final class Level1 extends AbstractLevel
{

  public Level1(GameState state)
  {
    super(state);
  }

  @Override
  protected String getLevelShortName()
  {
    return "Stoicism";
  }

  @Override
  protected String getLevelLongName()
  {
    return "Battle of Stoicism";
  }

  @Override
  protected int getLevelNumber()
  {
    return 1;
  }

  @Override
  public Scenario quit(GameIO io)
  {
    return null;
  }

  @Override
  protected void createAliens(GameIO io)
  {
    Screen s = io.mainScreen();
    Category cat = categories.get(0);

    for (int i = GAME_START_X; i < s.getWidth(); ++i)
    {
      long id = EntityFactory.makeYellowAlien(es, i, 0, state);
      es.putComponent(id, cat);
    }
  }

  @Override
  protected void createCategories()
  {
    categories.add(new Category("Yellow Aliens"));
  }

  @Override
  protected int getAvailableScore()
  {
    return categories.get(0).size() * 10;
  }
}
