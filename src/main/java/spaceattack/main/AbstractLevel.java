package spaceattack.main;

import java.util.ArrayList;
import java.util.List;

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
import spaceattack.main.systems.BoundsCheckSystem;
import spaceattack.main.systems.CollisionSystem;
import spaceattack.main.systems.DrawSystem;
import spaceattack.main.systems.PlayerShipInputSystem;
import spaceattack.main.systems.TimedMoveSystem;
import spaceattack.main.systems.TimedShotSystem;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public abstract class AbstractLevel implements Scenario
{
  protected static final int GAME_START_X = 26;
  protected EntitySystem es = new EntitySystem();
  protected int fps;
  protected boolean paused = false;
  protected List<Category> categories = new ArrayList<Category>();

  protected abstract void createAliens(GameIO io);
  protected abstract void createCategories();

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
    createCategories();
    createAliens(io);
  }

  @Override
  public void fps(GameIO io, int fps)
  {
    this.fps = fps;
  }

  @Override
  public void pause(GameIO io)
  {
    paused = true;
  }

  @Override
  public void resume(GameIO io)
  {
    paused = false;
  }

  @Override
  public void update(GameIO io, double delta)
  {
    VKey key = io.peekKey();

    // allow to resume/quit game
    if (paused)
    {
      switch (key)
      {
        case ENTER:
          io.consumeKey();
          io.requestResume();
          break;

        case ESC:
          io.consumeKey();
          io.requestQuit();
          break;

        default:
          io.consumeKey();
      }

      return;
    }

    if (io.peekKey() == VKey.ENTER)
    {
      io.consumeKey();
      io.requestPause();
    }
    else
      es.runLogicSystems(io, delta);
  }

  @Override
  public void render(GameIO io, double delta)
  {
    // clear everything
    io.mainScreen().clear(TerminalColor.DULL_BLACK);

    // draw the current state
    drawUI(io);
    es.runRenderSystems(io, delta);

    // draw the pause overlay
    if (paused)
      drawPauseOverlay(io);

    // update the screen
    io.mainScreen().blit();
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

    int linesLeft = 7;

    for (Category c : categories)
    {
      String name = c.getName().substring(0, Math.min(c.getName().length(), 13));
      s.drawText("$W{| %-13s %7d |}%n", name, c.size());
      --linesLeft;
    }

    s.drawText("$W{+-----------------------+}%n");
    s.drawText("$W{| Cycle: X              |}%n");
    s.drawText("$W{| Stage: X              |}%n");
    s.drawText("$W{| Score                 |}%n");
    s.drawText("$W{| XXXX                  |}%n");
    s.drawText("$W{+-----------------------+}%n");

    while (linesLeft > 0)
    {
      s.drawText("$W{|                       |}%n");
      --linesLeft;
    }
    s.drawText("$W{+-----------------------+}");
  }

  private void drawPauseOverlay(GameIO io)
  {
    Screen s = io.mainScreen();
    int y = (s.getHeight() / 2) - 2;

    s.setBackground(TerminalColor.DULL_WHITE);
    s.setForeground(TerminalColor.DULL_BLACK);

    s.drawText(0, y + 0, fillOnCenter(s, ""));
    s.drawText(0, y + 1, fillOnCenter(s, "PAUSED"));
    s.drawText(0, y + 2, fillOnCenter(s, ""));
    s.drawText(0, y + 3, fillOnCenter(s, "[ENTER] - Resume   [ESC] - Quit"));
    s.drawText(0, y + 4, fillOnCenter(s, ""));
  }

  private String fillOnCenter(Screen screen, String s)
  {
    StringBuilder sb = new StringBuilder();
    int size = screen.getWidth();

    for (int i = 0; i < (size - s.length()) / 2; i++)
    {
      sb.append(" ");
    }

    sb.append(s);

    while (sb.length() < size)
    {
      sb.append(" ");
    }

    return sb.toString();
  }
}
