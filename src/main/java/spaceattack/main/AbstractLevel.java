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
import spaceattack.main.util.DrawUtil;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public abstract class AbstractLevel implements Scenario
{
  private int maxScore;

  protected static final int GAME_START_X = 27;
  protected EntitySystem es = new EntitySystem();
  protected int fps;
  protected boolean paused = false;
  protected boolean levelStarting;
  protected List<Category> categories = new ArrayList<Category>();
  protected GameState state = null;

  protected abstract String getLevelShortName();
  protected abstract String getLevelLongName();
  protected abstract int getLevelNumber();
  protected abstract void createAliens(GameIO io);
  protected abstract void createCategories();
  protected abstract int getAvailableScore();

  protected AbstractLevel(GameState state)
  {
    this.state = state;
  }

  @Override
  public void init(GameIO io)
  {
    es.addLogicSystem(new PlayerShipInputSystem());
    es.addLogicSystem(new TimedMoveSystem());
    es.addLogicSystem(new TimedShotSystem());
    es.addLogicSystem(new BoundsCheckSystem());
    es.addLogicSystem(new CollisionSystem(state));
    es.addRenderSystem(new DrawSystem());

    // the planet orbit, where the aliens cannot reach!
    createEarth(io);

    // our lone savior
    createPlayer(io);

    // the evil aliens
    createCategories();
    createAliens(io);

    // setup this level score
    maxScore = getAvailableScore();

    // show the title
    levelStarting = true;

    // reset the state
    state.startPlaying();
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
  public Scenario quit(GameIO io)
  {
    state.addScore(currentScore());
    state.recoverHp(state.getCycle() > 5 ? 5 : state.getCycle() - 1);

    // back to the menu if we lose
    return state.isGameLost() ? new MainMenu() : null;
  }

  @Override
  public void update(GameIO io, double delta)
  {
    VKey key = io.peekKey();

    // show the level title
    if (levelStarting)
    {
      if (key == VKey.ENTER)
        levelStarting = false;

      io.consumeKey();
      return;
    }

    // if the game is already over wait for input to proceed
    if (!state.isPlaying())
    {
      if (key == VKey.ENTER)
        io.requestQuit();

      io.consumeKey();
      return;
    }

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
          state.lose();
          io.requestQuit();
          break;

        default:
          io.consumeKey();
      }

      return;
    }

    // handles pausing
    if (io.peekKey() == VKey.ENTER)
    {
      io.consumeKey();
      io.requestPause();
      return;
    }

    // magic key to automatically win
    /*if (io.peekKey() == VKey.UP_ARROW)
    {
      io.consumeKey();
      state.win();
      return;
    }*/

    // if we have come this far, update the game state
    es.runLogicSystems(io, delta);

    // check winning condition
    // losing condition is already checkd when the player takes damage
    if (state.isPlaying())
    {
      boolean aliensDead = true;

      for (Category c : categories)
        aliensDead = aliensDead && (c.size() == 0);

      if (aliensDead)
        state.win();
    }
  }

  @Override
  public void render(GameIO io, double delta)
  {
    // clear everything
    io.mainScreen().clear(TerminalColor.DULL_BLACK);

    // draw the current state
    if (levelStarting)
      drawTitleOverlay(io);
    else
    {
      drawUI(io);
      es.runRenderSystems(io, delta);
    }

    // draw the pause overlay
    if (paused)
      drawPauseOverlay(io);

    // draw the win overlay
    if (state.isGameWon())
      drawWinOverlay(io);

    // draw the lose overlay
    if (state.isGameLost())
      drawLoseOverlay(io);

    // update the screen
    io.mainScreen().blit();
  }

  protected int currentScore()
  {
    return maxScore - getAvailableScore();
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

    s.drawText(0, 0, "$W{+------------------------+}%n");
    s.drawText("$W{| Arrows = Move          |}%n");
    s.drawText("$W{| Space  = Shoot         |}%n");
    s.drawText("$W{+------------------------+}%n");
    s.drawText("$W{| Kill all the aliens    |}%n");
    s.drawText("$W{| before they reach the  |}%n");
    s.drawText("$W{| planet's orbit!        |}%n");
    s.drawText("$W{+------------------------+}%n");
    s.drawText("$W{| Aliens left            |}%n");
    s.drawText("$W{|                        |}%n");

    int linesLeft = 4;

    for (Category c : categories)
    {
      String name = c.getName().substring(0, Math.min(c.getName().length(), 14));
      s.drawText("$W{| %-14s %7d |}%n", name, c.size());
      --linesLeft;
    }

    String name = getLevelShortName();
    name = name.substring(0, Math.min(14, name.length()));

    s.drawText("$W{+------------------------+}%n");
    s.drawText("$W{| Cycle: %-15s |}%n", state.getCycleDescription());
    s.drawText("$W{| Stage: %-15s |}%n", name);
    s.drawText("$W{| Level Score:           |}%n");
    s.drawText("$W{| %22d |}%n", currentScore());
    s.drawText("$W{| Total Score:           |}%n");
    s.drawText("$W{| %22d |}%n", currentScore() + state.getTotalScore());
    s.drawText("$W{+------------------------+}%n");
    s.drawText("$W{| L %s |}%n", state.hpBarStr());

    while (linesLeft > 0)
    {
      s.drawText("$W{|                        |}%n");
      --linesLeft;
    }
    s.drawText("$W{+------------------------+}");
  }

  private void drawPauseOverlay(GameIO io)
  {
    Screen s = io.mainScreen();
    s.setBackground(TerminalColor.DULL_WHITE);
    s.setForeground(TerminalColor.DULL_BLACK);

    DrawUtil.drawBanner(io, "PAUSED", "[ENTER] - Resume   [ESC] - Quit");
  }

  private void drawTitleOverlay(GameIO io)
  {
    Screen s = io.mainScreen();
    s.setBackground(TerminalColor.DULL_WHITE);
    s.setForeground(TerminalColor.DULL_BLACK);

    DrawUtil.drawBanner(io, "LEVEL " + getLevelNumber(), "~ " + getLevelLongName() + " ~");
  }

  private void drawWinOverlay(GameIO io)
  {
    Screen s = io.mainScreen();
    s.setBackground(TerminalColor.DULL_YELLOW);
    s.setForeground(TerminalColor.DULL_BLACK);

    DrawUtil.drawBanner(io, "LEVEL " + getLevelNumber() + " CLEARED!", "!!! CONGRATULATIONS !!!");
  }

  private void drawLoseOverlay(GameIO io)
  {
    Screen s = io.mainScreen();
    s.setBackground(TerminalColor.DULL_RED);
    s.setForeground(TerminalColor.DULL_BLACK);

    DrawUtil.drawBanner(io, "LEVEL " + getLevelNumber() + " FAILED!", "--- YOU DIED ---");
  }
}
