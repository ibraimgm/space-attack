package spaceattack.framework.terminal;

import spaceattack.framework.Screen;
import spaceattack.terminal.Terminal;
import spaceattack.terminal.TerminalColor;

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
  public void setBackground(TerminalColor color)
  {
    Terminal.INSTANCE.textBackground(color);
  }

  @Override
  public void setForeground(TerminalColor color)
  {
    Terminal.INSTANCE.textForeground(color);
  }

  @Override
  public void clear(TerminalColor color)
  {
    setBackground(color);
    Terminal.INSTANCE.clrscr();
  }

  @Override
  public void blit()
  {
    Terminal.INSTANCE.flush();
  }

  @Override
  public void drawText(int x, int y, char c)
  {
    Terminal.INSTANCE.gotoxy(this.x + x, this.y + y);
    Terminal.INSTANCE.putc(c);
  }

  @Override
  public void drawText(int x, int y, String format, Object... args)
  {
    Terminal.INSTANCE.gotoxy(this.x + x, this.y + y);
    Terminal.INSTANCE.printc(format, args);
  }

  @Override
  public void drawText(char c)
  {
    Terminal.INSTANCE.putc(c);
  }

  @Override
  public void drawText(String format, Object... args)
  {
    Terminal.INSTANCE.printc(format, args);
  }

}
