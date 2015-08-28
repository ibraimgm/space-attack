package spaceattack.main;

import spaceattack.framework.GameIO;
import spaceattack.framework.Scenario;
import spaceattack.framework.Screen;
import spaceattack.main.util.DrawUtil;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.VKey;

public final class MainMenu implements Scenario
{
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
    Screen s = io.mainScreen();

    s.clear(TerminalColor.DULL_BLACK);
    s.setForeground(TerminalColor.DULL_WHITE);

    int y = 3;
    s.drawText(0, y, DrawUtil.fillOnCenter(s,"   ____                       ")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s,"   / ___| _ __   __ _  ___ ___ ")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s,"   \\___ \\| '_ \\ / _` |/ __/ _ \\")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s,"    ___) | |_) | (_| | (_|  __/")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s,"   |____/| .__/ \\__,_|\\___\\___|")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s,"         |_|                   ")); ++y;
    y+=2;
    s.drawText(0, y, DrawUtil.fillOnCenter(s, "_   _   _             _")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s, "   / \\ | |_| |_ __ _  ___| | __")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s, "  / _ \\| __| __/ _` |/ __| |/ /")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s, "/ ___ \\ |_| || (_| | (__|   <")); ++y;
    s.drawText(0, y, DrawUtil.fillOnCenter(s, "/_/   \\_\\__|\\__\\__,_|\\___|_|\\_\\")); ++y;

    renderMenuItem(io, y+5, MENU_ITEM_START, "New Game");
    renderMenuItem(io, y+5, MENU_ITEM_QUIT,  "  Quit  ");
    s.blit();
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

  private void renderMenuItem(GameIO io, int menuAxisY, int item, String text)
  {
    Screen s = io.mainScreen();
    String indicator;// = (item == option) ? "->" : "  ";
    TerminalColor color;

    if (item == option)
    {
      indicator = "->";
      color = TerminalColor.VIVID_WHITE;
    }
    else
    {
      indicator = "  ";
      color = TerminalColor.VIVID_BLACK;
    }

    s.setForeground(color);
    s.drawText(0, menuAxisY + item, DrawUtil.fillOnCenter(s, indicator + " " + text));

    /*if (item == option)
      s.drawText(MENU_INDICATOR_X, menuAxisY + item, '*');

    s.drawText(MENU_AXIS_X, menuAxisY + item, text);*/
  }
}
