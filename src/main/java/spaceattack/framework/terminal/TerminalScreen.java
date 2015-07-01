package spaceattack.framework.terminal;

import spaceattack.framework.Screen;
import spaceattack.terminal.TerminalFactory;

public final class TerminalScreen implements Screen
{
  private int x;
  private int y;
  private int width;
  private int height;

  public TerminalScreen(int x, int y, int width, int height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public int getX()
  {
    return x;
  }

  @Override
  public int getY()
  {
    return y;
  }

  @Override
  public int getWidth()
  {
    return width;
  }

  @Override
  public int getHeight()
  {
    return height;
  }

  @Override
  public void setBackground(int color)
  {
    TerminalFactory.getInstance().textBackground(color);
  }

  @Override
  public void setForeground(int color)
  {
    TerminalFactory.getInstance().textForeground(color);
  }

  @Override
  public void clear(int color)
  {
    setBackground(color);
    TerminalFactory.getInstance().clrscr();
  }

  @Override
  public void blit()
  {
    TerminalFactory.getInstance().flush();
  }

  @Override
  public void drawText(int x, int y, char c)
  {
    TerminalFactory.getInstance().gotoxy(this.x + x, this.y + y);
    TerminalFactory.getInstance().putc(c);
  }

  @Override
  public void drawText(int x, int y, String format, Object... args)
  {
    TerminalFactory.getInstance().gotoxy(this.x + x, this.y + y);
    TerminalFactory.getInstance().printc(format, args);
  }

  @Override
  public void drawText(char c)
  {
    TerminalFactory.getInstance().putc(c);
  }

  @Override
  public void drawText(String format, Object... args)
  {
    TerminalFactory.getInstance().printc(format, args);
  }

}
