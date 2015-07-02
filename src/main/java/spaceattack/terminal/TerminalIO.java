package spaceattack.terminal;

import java.util.ArrayDeque;
import java.util.Deque;

public interface TerminalIO
{
  void setup();
  void tearDown();
  void flush();

  void clrscr();
  void gotoxy(int x, int y);
  void displayCursor(boolean visible);
  void textBackground(TerminalColor color);
  void textForeground(TerminalColor color);
  TerminalColor currentBackground();
  TerminalColor currentForeground();
  void putc(char c);
  boolean kbhit();
  VKey readKey();

  default void puts(String s)
  {
    for (char c : s.toCharArray())
      putc(c);
  }

  default void printf(String format, Object... args)
  {
    String s = String.format(format, args);
    puts(s);
  }

  default void printc(String format, Object... args)
  {
    char buffer[] = String.format(format, args).toCharArray();
    Deque<ColorPair> stack = new ArrayDeque<TerminalIO.ColorPair>();

    // parsing
    for (int i = 0; i < buffer.length; ++i)
    {
      char c0 = buffer[i];
      char c1 = (i + 1) >= buffer.length ? '\0' : buffer[i + 1];
      char c2 = (i + 2) >= buffer.length ? '\0' : buffer[i + 2];
      char c3 = (i + 3) >= buffer.length ? '\0' : buffer[i + 3];

      // do we have a '$$' or '$}' ?
      if ((c0 == '$') && ((c1 == '$') || (c1 == '}')))
      {
        putc(c1);
        ++i;
        continue;
      }

      // do we have a '}' and actual colors to pop?
      if ((c0 == '}') && (!stack.isEmpty()))
      {
        ColorPair p = stack.removeFirst();
        textForeground(p.fg);
        textBackground(p.bg);
        continue;
      }

      // do we have one color?
      TerminalColor fg = TerminalColor.charToColor(c1);

      if ((c0 == '$') && (fg != TerminalColor.NO_COLOR) && (c2 == '{'))
      {
        fg = fg == TerminalColor.COLOR_UNCHANGED ? currentForeground() : fg;

        stack.addFirst(new ColorPair(currentForeground(), currentBackground()));
        textForeground(fg);

        i+= 2;
        continue;
      }

      // do we have TWO colors?
      TerminalColor bg = TerminalColor.charToColor(c2);

      if ((c0 == '$') && (fg != TerminalColor.NO_COLOR) && (bg != TerminalColor.NO_COLOR) && (c3 == '{'))
      {
        fg = fg == TerminalColor.COLOR_UNCHANGED ? currentForeground() : fg;
        bg = bg == TerminalColor.COLOR_UNCHANGED ? currentBackground() : bg;

        stack.addFirst(new ColorPair(currentForeground(), currentBackground()));
        textForeground(fg);
        textBackground(bg);

        i+= 3;
        continue;
      }

      // since we can't determine what this is, it must be a normal char
      putc(c0);
    }

    // now, if the user forgot to close the command, we try to pop everything
    while (!stack.isEmpty())
    {
      ColorPair p = stack.removeFirst();
      textForeground(p.fg);
      textBackground(p.bg);
    }
  }

  public static class ColorPair
  {
    public TerminalColor fg;
    public TerminalColor bg;

    public ColorPair(TerminalColor fg, TerminalColor bg)
    {
      this.fg = fg;
      this.bg = bg;
    }
  }
}
