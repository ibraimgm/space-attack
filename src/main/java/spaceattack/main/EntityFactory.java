package spaceattack.main;

import java.util.Random;

import spaceattack.framework.ecs.EntitySystem;
import spaceattack.main.components.Collision;
import spaceattack.main.components.Draw;
import spaceattack.main.components.Position;
import spaceattack.main.components.TimedMove;
import spaceattack.main.components.TimedShot;
import spaceattack.terminal.TerminalColor;

public final class EntityFactory
{
  private static final int DEFAULT_MOVE_DELAY = 4000;
  private static final Random r = new Random();

  private EntityFactory()
  {
  }

  private static int getMoveDelay(GameState state)
  {
    int delay = DEFAULT_MOVE_DELAY;

    if (state.getCycle() >= 2)
      delay -= (state.getCycle() - 1) * 285;

    if (state.getCycle() >= 1)
      delay -= 290;

    return delay;
  }

  public static long makeYellowAlien(EntitySystem es, int x, int y, GameState state)
  {
    long id = es.newEntity();
    int fireRate = (r.nextInt(15) + 1) * 700;
    es.putComponent(id, new Position(x, y));
    es.putComponent(id, new Draw(TerminalColor.VIVID_YELLOW, TerminalColor.DULL_BLACK, 'T'));
    es.putComponent(id, new TimedMove(0, 1, true, getMoveDelay(state)));
    es.putComponent(id, new Collision(Collision.Type.ALIEN));
    es.putComponent(id, new TimedShot(100, '.', TerminalColor.DULL_BLACK, TerminalColor.VIVID_YELLOW, true, fireRate));

    return id;
  }

  public static long makeGreenAlien(EntitySystem es, int x, int y, GameState state)
  {
    long id = es.newEntity();
    int fireRate = (r.nextInt(15) + 1) * 850;
    es.putComponent(id, new Position(x, y));
    es.putComponent(id, new Draw(TerminalColor.VIVID_GREEN, TerminalColor.DULL_BLACK, 'V'));
    es.putComponent(id, new TimedMove(0, 1, true, getMoveDelay(state)));
    es.putComponent(id, new Collision(Collision.Type.ALIEN));
    es.putComponent(id, new TimedShot(50, ':', TerminalColor.DULL_BLACK, TerminalColor.VIVID_GREEN, true, fireRate));

    return id;
  }

  public static long makeCyanAlien(EntitySystem es, int x, int y, GameState state)
  {
    long id = es.newEntity();
    int fireRate = (r.nextInt(15) + 1) * 900;
    es.putComponent(id, new Position(x, y));
    es.putComponent(id, new Draw(TerminalColor.VIVID_CYAN, TerminalColor.DULL_BLACK, 'H'));
    es.putComponent(id, new TimedMove(0, 1, true, getMoveDelay(state)));
    es.putComponent(id, new Collision(Collision.Type.ALIEN));
    es.putComponent(id, new TimedShot(200, 'v', TerminalColor.DULL_BLACK, TerminalColor.VIVID_CYAN, true, fireRate));

    return id;
  }

  public static long makeRedAlien(EntitySystem es, int x, int y, GameState state)
  {
    long id = es.newEntity();
    int fireRate = (r.nextInt(15) + 1) * 800;
    es.putComponent(id, new Position(x, y));
    es.putComponent(id, new Draw(TerminalColor.VIVID_RED, TerminalColor.DULL_BLACK, 'Y'));
    es.putComponent(id, new TimedMove(0, 1, true, getMoveDelay(state)));
    es.putComponent(id, new Collision(Collision.Type.ALIEN));
    es.putComponent(id, new TimedShot(100, '!', TerminalColor.DULL_BLACK, TerminalColor.VIVID_RED, true, fireRate));

    return id;
  }
}
