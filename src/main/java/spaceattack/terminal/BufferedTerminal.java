package spaceattack.terminal;

public final class BufferedTerminal implements Terminal
{
  Terminal inner;
  int width, height;
  int currX, currY, currBg, currFg;
  BufferData[] current;
  BufferData[] changed;
  
  
  public BufferedTerminal(Terminal inner, int width, int height, int initialBg, int initialFg)
  {
    this.inner = inner;
    this.width = width;
    this.height = height;
    this.currX = 0;
    this.currY = 0;
    this.currBg = initialBg;
    this.currFg = initialFg;
    
    current = new BufferData[width * height];
    changed = new BufferData[width * height];
    
    for (int i = 0; i < width * height; ++i)
    {
      current[i] = new BufferData(initialBg, initialFg);
      changed[i] = new BufferData(initialBg, initialFg);
    }
    
    // setup the inner terminal
    inner.setup();
    inner.textForeground(initialFg);
    inner.textBackground(initialBg);
    inner.clrscr();
  }

  @Override
  public void setup()
  {
    // Nothing to do here, the inner terminal is already started on the constructor
  }

  @Override
  public void tearDown()
  {
    inner.tearDown();
  }

  @Override
  public void flush()
  {
    for (int i = 0; i < width * height; ++i)
      if ((changed[i].bg != current[i].bg) ||
          (changed[i].fg != current[i].fg) ||
          (changed[i].c != current[i].c))
      {
        // compute x and y
        int x = i > (width - 1) ? i % width : i;
        int y = i >= width ? i / width : 0;
        
        inner.gotoxy(x, y);
        inner.textBackground(changed[i].bg);
        inner.textForeground(changed[i].fg);
        inner.putc(changed[i].c);
        
        current[i].bg = changed[i].bg;
        current[i].fg = changed[i].fg;
        current[i].c = changed[i].c;
      }
  }

  @Override
  public void clrscr()
  {
    for (int i = 0; i < width * height; ++i)
    {
      changed[i].fg = currFg;
      changed[i].bg = currBg;
      changed[i].c = ' ';
    }
  }

  @Override
  public void gotoxy(int x, int y)
  {
    currX = x;
    currY = y;
  }

  @Override
  public void displayCursor(boolean visible)
  {
    inner.displayCursor(visible);    
  }

  @Override
  public void textBackground(int color)
  {
    if ((color != TerminalColor.COLOR_UNCHANGED) && (color != TerminalColor.NO_COLOR))
      this.currBg = color;
  }

  @Override
  public void textForeground(int color)
  {
    if ((color != TerminalColor.COLOR_UNCHANGED) && (color != TerminalColor.NO_COLOR))
      this.currFg = color;
  }

  @Override
  public int currentBackground()
  {
    return this.currBg;
  }

  @Override
  public int currentForeground()
  {
    return this.currFg;
  }

  @Override
  public void putc(char c)
  {
    // convert the current coordinates to an array position
    int i = currY * width + currX;
    changed[i].bg = currBg;
    changed[i].fg = currFg;
    changed[i].c = c;
    ++currX;
    
    if (currX >= width)
      ++currY;    
  }

  @Override
  public boolean kbhit()
  {
    return inner.kbhit();
  }

  @Override
  public VKey readKey()
  {
    return inner.readKey();
  }
  
  private class BufferData
  {
    public int bg;
    public int fg;
    public char c;
    
    public BufferData(int bg, int fg)
    {
      this.bg = bg;
      this.fg = fg;
      this.c = ' ';
    }
  }

}
