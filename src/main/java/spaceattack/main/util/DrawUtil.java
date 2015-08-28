package spaceattack.main.util;

import spaceattack.framework.GameIO;
import spaceattack.framework.Screen;

public final class DrawUtil
{
  public static void drawBanner(GameIO io, String firstLine, String secondLine)
  {
    Screen s = io.mainScreen();
    int y = (s.getHeight() / 2) - 2;

    s.drawText(0, y + 0, fillOnCenter(s, ""));
    s.drawText(0, y + 1, fillOnCenter(s, firstLine));
    s.drawText(0, y + 2, fillOnCenter(s, ""));
    s.drawText(0, y + 3, fillOnCenter(s, secondLine));
    s.drawText(0, y + 4, fillOnCenter(s, ""));
  }

  public static String fillOnCenter(Screen screen, String s)
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
