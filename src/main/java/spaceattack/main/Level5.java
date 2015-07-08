package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Category;

public final class Level5 extends AbstractLevel
{
  public Level5(GameState state)
  {
    super(state);
  }

  private Category yellowAliens = new Category("Yellow Aliens");
  private Category greenAliens = new Category("Green Aliens");
  private Category cyanAliens = new Category("Cyan Aliens");
  private Category redAliens = new Category("Red Aliens");
  int fps;

  @Override
  protected String getLevelShortName()
  {
    return "Attack!";
  }

  @Override
  protected String getLevelLongName()
  {
    return "Attack On Earth!";
  }

  @Override
  protected int getLevelNumber()
  {
    return 5;
  }

  @Override
  public Scenario quit(GameIO io)
  {
    return null;
  }

  @Override
  protected void createCategories()
  {
    categories.add(yellowAliens);
    categories.add(greenAliens);
    categories.add(cyanAliens);
    categories.add(redAliens);
  }

  @Override
  protected void createAliens(GameIO io)
  {
    Screen s = io.mainScreen();

    boolean backRow = true;
    boolean backType1 = true;
    boolean frontType1 = true;

    // the evil aliens
    for (int i = GAME_START_X; i < s.getWidth(); i+=2)
    {
      long id;
      Category category;

      // choose the position
      int y = backRow ? 0 : 1;

      // choose what alien/category must be generated
      if ((backRow) && (backType1))
      {
        id = EntityFactory.makeYellowAlien(es, i, y, state);
        category = yellowAliens;
      }
      else if (backRow)
      {
        id = EntityFactory.makeGreenAlien(es, i, y, state);
        category = greenAliens;
      }
      else if (frontType1)
      {
        id = EntityFactory.makeCyanAlien(es, i, y, state);
        category = cyanAliens;
      }
      else
      {
        id = EntityFactory.makeRedAlien(es, i, y, state);
        category = redAliens;
      };

      // add to the category
      es.putComponent(id, category);

      // switch the next alien type
      if (backRow)
        backType1 = !backType1;
      else
        frontType1 = !frontType1;

      // switch the next row
      backRow = !backRow;
    }
  }

  @Override
  protected int getAvailableScore()
  {
    throw new RuntimeException("Not implemented yet!");
  }
}
