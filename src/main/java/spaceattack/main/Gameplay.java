package spaceattack.main;

import java.util.Random;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.framework.ecs.Category;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.main.components.BoundsCheck;
import spaceattack.main.components.BoundsCheck.OutOfBounds;
import spaceattack.main.components.Collision;
import spaceattack.main.components.Draw;
import spaceattack.main.components.PlayerShipInput;
import spaceattack.main.components.Position;
import spaceattack.main.components.TimedMove;
import spaceattack.main.components.TimedShot;
import spaceattack.main.systems.BoundsCheckSystem;
import spaceattack.main.systems.CollisionSystem;
import spaceattack.main.systems.DrawSystem;
import spaceattack.main.systems.PlayerShipInputSystem;
import spaceattack.main.systems.TimedMoveSystem;
import spaceattack.main.systems.TimedShotSystem;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public final class Gameplay implements Scenario
{
  private static final int GAME_START_X = 26;
  private EntitySystem es = new EntitySystem();
  private Category yellowAliens = new Category();

  @Override
  public void init(GameIO io)
  {
    es.addLogicSystem(new PlayerShipInputSystem());
    es.addLogicSystem(new TimedMoveSystem());
    es.addLogicSystem(new TimedShotSystem());
    es.addLogicSystem(new BoundsCheckSystem());
    es.addLogicSystem(new CollisionSystem());
    es.addRenderSystem(new DrawSystem());

    // the planet orbit, where the aliens cannot reach!
    createEarth(io);

    // our lone savior
    createPlayer(io);

    // the evil aliens
    createAliens(io);
  }

  @Override
  public void pause(GameIO io)
  {
  }

  @Override
  public void resume(GameIO io)
  {
  }

  @Override
  public void update(GameIO io, double delta)
  {
    if (io.peekKey() == VKey.ENTER)
    {
      io.consumeKey();
      io.requestQuit();
    }
    else
      es.runLogicSystems(io, delta);
  }

  @Override
  public void render(GameIO io, double delta)
  {
    io.mainScreen().clear(TerminalColor.DULL_BLACK);
    drawUI(io);
    es.runRenderSystems(io, delta);
    io.mainScreen().blit();
  }

  @Override
  public Scenario quit(GameIO io)
  {
    return null;
  }

  private void createEarth(GameIO io)
  {
    Screen s = io.mainScreen();

    for (int i = GAME_START_X; i < s.getWidth(); ++i)
    {
      long id = es.newEntity();

      es.putComponent(id, new Position(i, s.getHeight() - 1));
      es.putComponent(id, new Draw(TerminalColor.VIVID_CYAN, TerminalColor.DULL_BLACK, '~'));
      es.putComponent(id, new Collision(Collision.Type.EARTH_ORBIT));
    }
  }

  private void createPlayer(GameIO io)
  {
    long id = es.newEntity();
    Screen s = io.mainScreen();

    es.putComponent(id, new Position((s.getWidth() - GAME_START_X) / 2, s.getHeight() - 2));
    es.putComponent(id, BoundsCheck.fixedY(s.getHeight() - 2, GAME_START_X, s.getWidth() - 1, OutOfBounds.ADJUST_POSITION));
    es.putComponent(id, new Draw(TerminalColor.VIVID_WHITE, TerminalColor.DULL_BLACK, 'M'));
    es.putComponent(id, new PlayerShipInput());
    es.putComponent(id, new Collision(Collision.Type.PLAYER));
  }

  private void createAliens(GameIO io)
  {
    Screen s = io.mainScreen();
    Random r = new Random();

    // the dreaded T's
    for (int i = GAME_START_X; i < s.getWidth(); i += 2)
    {
      long id = es.newEntity();
      int fireRate = (r.nextInt(15) + 1) * 700;

      es.putComponent(id, new Position(i, 0));
      es.putComponent(id, new Draw(TerminalColor.VIVID_YELLOW, TerminalColor.DULL_BLACK, 'T'));
      es.putComponent(id, new TimedMove(0, 1, true, 4000));
      es.putComponent(id, new Collision(Collision.Type.ALIEN));
      es.putComponent(id, new TimedShot(100, '.', TerminalColor.DULL_BLACK, TerminalColor.VIVID_YELLOW, true, fireRate));
      es.putComponent(id, yellowAliens);
    }
  }

  private void drawUI(GameIO io)
  {
    Screen s = io.mainScreen();

    s.drawText(0, 0, "$W{+-----------------------+}%n");
    s.drawText("$W{| Arrows = Move         |}%n");
    s.drawText("$W{| Space  = Shoot        |}%n");
    s.drawText("$W{+-----------------------+}%n");
    s.drawText("$W{| Kill all the aliens   |}%n");
    s.drawText("$W{| before they reach the |}%n");
    s.drawText("$W{| planet's orbit!       |}%n");
    s.drawText("$W{+-----------------------+}%n");
    s.drawText("$W{| Aliens left           |}%n");
    s.drawText("$W{|                       |}%n");
    s.drawText("$W{| %21d |}%n", yellowAliens.size());
    s.drawText("$W{| %21d |}%n", -1);
    s.drawText("$W{| %21d |}%n", -1);
    s.drawText("$W{| %21d |}%n", -1);
    s.drawText("$W{+-----------------------+}%n");
    s.drawText("$W{| Stage X               |}%n");
    s.drawText("$W{| Score                 |}%n");
    s.drawText("$W{| XXXX                  |}%n");
    s.drawText("$W{+-----------------------+}");
  }
}
