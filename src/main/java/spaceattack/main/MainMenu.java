package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public final class MainMenu implements Scenario
{
  private static final int MENU_AXIS_X = 10;
  private static final int MENU_AXIS_Y = 10;
  private static final int MENU_INDICATOR_X = MENU_AXIS_X - 2;
  private static final int MENU_ITEM_START = 0;
  private static final int MENU_ITEM_QUIT = 1;
  private static final int MENU_FIRST_ITEM = MENU_ITEM_START;
  private static final int MENU_LAST_ITEM = MENU_ITEM_QUIT;

  private int option;

  @Override
  public void init(GameIO io)
  {
    option = MENU_ITEM_START;
  }

  @Override
  public void pause(GameIO io)
  {
    // Nothing to do here
  }

  @Override
  public void resume(GameIO io)
  {
    // Nothing to do here
  }

  @Override
  public void update(GameIO io, double delta)
  {
    VKey key = io.peekKey();

    switch (key)
    {
      case DOWN_ARROW:
        ++option;
        io.consumeKey();
        break;

      case UP_ARROW:
        --option;
        io.consumeKey();
        break;

      case ENTER:
        io.consumeKey();
        io.requestQuit();
        break;

      default: io.consumeKey();
    }

    option = option < MENU_FIRST_ITEM ? MENU_FIRST_ITEM : option;
    option = option > MENU_LAST_ITEM ? MENU_LAST_ITEM : option;
  }

  @Override
  public void render(GameIO io, double delta)
  {
    Screen main = io.mainScreen();

    main.clear(TerminalColor.DULL_BLACK);
    renderMenuItem(io, MENU_ITEM_START, "New Game");
    renderMenuItem(io, MENU_ITEM_QUIT, "Quit");
    main.blit();
  }

  @Override
  public Scenario quit(GameIO io)
  {

    if (option == MENU_ITEM_START)
      return new Level1(new GameState());

    return null;
  }

  @Override
  public void fps(GameIO io, int fps)
  {
    // Nothing to do here
  }

  private void renderMenuItem(GameIO io, int item, String text)
  {
    Screen s = io.mainScreen();
    s.setForeground(TerminalColor.DULL_WHITE);

    if (item == option)
      s.drawText(MENU_INDICATOR_X, MENU_AXIS_Y + item, '*');

    s.drawText(MENU_AXIS_X, MENU_AXIS_Y + item, text);
  }
}
