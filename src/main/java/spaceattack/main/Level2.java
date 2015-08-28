package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Category;

public final class Level2 extends AbstractLevel
{

  protected Level2(GameState state)
  {
    super(state);
  }

  @Override
  protected String getLevelShortName()
  {
    return "F'n'F";
  }

  @Override
  protected String getLevelLongName()
  {
    return "Fast and Furious";
  }

  @Override
  protected int getLevelNumber()
  {
    return 2;
  }

  @Override
  protected void createAliens(GameIO io)
  {
   Screen s = io.mainScreen();
   Category cat = categories.get(0);

   for (int i = GAME_START_X; i < s.getWidth(); i+=2)
   {
     long id = EntityFactory.makeGreenAlien(es, i, 0, state);
     es.putComponent(id, cat);
   }
  }

  @Override
  protected void createCategories()
  {
    categories.add(new Category("Green Aliens"));
  }

  @Override
  protected int getAvailableScore()
  {
    return categories.get(0).size() * 15;
  }

  @Override
  public Scenario quit(GameIO io)
  {
    Scenario other = super.quit(io);

    return other != null ? other : new Level3(state);
  }
}
