package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Category;

public class Level4 extends AbstractLevel
{

  protected Level4(GameState state)
  {
    super(state);
  }

  @Override
  protected String getLevelShortName()
  {
    return "Furious";
  }

  @Override
  protected String getLevelLongName()
  {
    return "Furious March";
  }

  @Override
  protected int getLevelNumber()
  {
    return 4;
  }

  @Override
  protected void createAliens(GameIO io)
  {
    Screen s = io.mainScreen();
    Category cat = categories.get(0);

    for (int x = GAME_START_X; x < s.getWidth(); ++x)
    {
      int y = x % 2 == 0 ? 0 : 1;

      long id = EntityFactory.makeRedAlien(es, x, y, state);
      es.putComponent(id, cat);
    }
  }

  @Override
  protected void createCategories()
  {
    categories.add(new Category("Red Aliens"));
  }

  @Override
  protected int getAvailableScore()
  {
    return categories.get(0).size() * 20;
  }

  @Override
  public Scenario quit(GameIO io)
  {
    Scenario other = super.quit(io);

    return other != null ? other : new Level5(state);
  }
}
