package spaceattack.framework;

import spaceattack.terminal.TerminalColor;

public interface Screen
{
  int getX();
  int getY();
  int getWidth();
  int getHeight();

  void setBackground(TerminalColor color);
  void setForeground(TerminalColor color);
  void clear(TerminalColor color);
  void blit();

  void drawText(int x, int y, char c);
  void drawText(int x, int y, String format, Object... args);

  void drawText(char c);
  void drawText(String format, Object... args);
}
