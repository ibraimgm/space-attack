package spaceattack.terminal.impl;

import spaceattack.os.win32.CONSOLE_CURSOR_INFO;
import spaceattack.os.win32.CONSOLE_SCREEN_BUFFER_INFO;
import spaceattack.os.win32.COORD;
import spaceattack.os.win32.Kernel32;
import spaceattack.os.win32.Msvcrt;
import spaceattack.terminal.TerminalColor;
import spaceattack.terminal.TerminalIO;
import spaceattack.terminal.VKey;

import com.sun.jna.ptr.IntByReference;

public class WindowsTerminalIO implements TerminalIO
{
  // constants from Wincon.h
  private static final short FOREGROUND_BLUE      = 1;
  private static final short FOREGROUND_GREEN     = 2;
  private static final short FOREGROUND_RED       = 4;
  private static final short FOREGROUND_INTENSITY = 8;
  private static final short BACKGROUND_BLUE      = 16;
  private static final short BACKGROUND_GREEN     = 32;
  private static final short BACKGROUND_RED       = 64;
  private static final short BACKGROUND_INTENSITY = 128;

  // managed state
  private int hConsole = -1;
  private TerminalColor currentFg = TerminalColor.DULL_WHITE;
  private TerminalColor currentBg = TerminalColor.DULL_BLACK;
  private CONSOLE_CURSOR_INFO cursorInfo;
  private CONSOLE_SCREEN_BUFFER_INFO info;

  private short colorToWinColor(TerminalColor color, boolean isForeground)
  {
    short c;

    if (isForeground)
    {
      switch(color)
      {
        case DULL_BLACK:   c = 0; break;
        case DULL_RED:     c = FOREGROUND_RED; break;
        case DULL_GREEN:   c = FOREGROUND_GREEN; break;
        case DULL_YELLOW:  c = FOREGROUND_RED | FOREGROUND_GREEN; break;
        case DULL_BLUE:    c = FOREGROUND_BLUE; break;
        case DULL_MAGENTA: c = FOREGROUND_RED | FOREGROUND_BLUE; break;
        case DULL_CYAN:    c = FOREGROUND_GREEN | FOREGROUND_BLUE; break;
        case DULL_WHITE:   c = FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE; break;

        case VIVID_BLACK:   c = FOREGROUND_INTENSITY | 0; break;
        case VIVID_RED:     c = FOREGROUND_INTENSITY | FOREGROUND_RED; break;
        case VIVID_GREEN:   c = FOREGROUND_INTENSITY | FOREGROUND_GREEN; break;
        case VIVID_YELLOW:  c = FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN; break;
        case VIVID_BLUE:    c = FOREGROUND_INTENSITY | FOREGROUND_BLUE; break;
        case VIVID_MAGENTA: c = FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_BLUE; break;
        case VIVID_CYAN:    c = FOREGROUND_INTENSITY | FOREGROUND_GREEN | FOREGROUND_BLUE; break;
        case VIVID_WHITE:   c = FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE; break;

        default: c = FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE;
      }
    }
    else
    {
      switch(color)
      {
        case DULL_BLACK:   c = 0; break;
        case DULL_RED:     c = BACKGROUND_RED; break;
        case DULL_GREEN:   c = BACKGROUND_GREEN; break;
        case DULL_YELLOW:  c = BACKGROUND_RED | BACKGROUND_GREEN; break;
        case DULL_BLUE:    c = BACKGROUND_BLUE; break;
        case DULL_MAGENTA: c = BACKGROUND_RED | BACKGROUND_BLUE; break;
        case DULL_CYAN:    c = BACKGROUND_GREEN | BACKGROUND_BLUE; break;
        case DULL_WHITE:   c = BACKGROUND_RED | BACKGROUND_GREEN | BACKGROUND_BLUE; break;

        case VIVID_BLACK:   c = BACKGROUND_INTENSITY | 0; break;
        case VIVID_RED:     c = BACKGROUND_INTENSITY | BACKGROUND_RED; break;
        case VIVID_GREEN:   c = BACKGROUND_INTENSITY | BACKGROUND_GREEN; break;
        case VIVID_YELLOW:  c = BACKGROUND_INTENSITY | BACKGROUND_RED | BACKGROUND_GREEN; break;
        case VIVID_BLUE:    c = BACKGROUND_INTENSITY | BACKGROUND_BLUE; break;
        case VIVID_MAGENTA: c = BACKGROUND_INTENSITY | BACKGROUND_RED | BACKGROUND_BLUE; break;
        case VIVID_CYAN:    c = BACKGROUND_INTENSITY | BACKGROUND_GREEN | BACKGROUND_BLUE; break;
        case VIVID_WHITE:   c = BACKGROUND_INTENSITY | BACKGROUND_RED | BACKGROUND_GREEN | BACKGROUND_BLUE; break;

        default: c = 0;
      }
    }

    return c;
  }

  @Override
  public void setup()
  {
    if (hConsole >= 0)
      return;

    // STD_OUTPUT_HANDLE = -11
    hConsole = Kernel32.INSTANCE.GetStdHandle(-11);

    // backup of the current console attributes
    info = new CONSOLE_SCREEN_BUFFER_INFO();
    cursorInfo = new CONSOLE_CURSOR_INFO();
    Kernel32.INSTANCE.GetConsoleScreenBufferInfo(hConsole, info);
    Kernel32.INSTANCE.GetConsoleCursorInfo(hConsole, cursorInfo);

    // make the cursor disappear
    displayCursor(false);

    // sets a starting foreground/background
    textForeground(TerminalColor.DULL_WHITE);
    textBackground(TerminalColor.DULL_BLACK);

    // clears the screens
    clrscr();

    // starts at 0,0
    gotoxy(0, 0);
  }

  @Override
  public void tearDown()
  {
    // restore text colors
    Kernel32.INSTANCE.SetConsoleTextAttribute(hConsole, info.wAttributes);

    // restore cursor configuration
    Kernel32.INSTANCE.SetConsoleCursorInfo(hConsole, cursorInfo);
  }

  @Override
  public void flush()
  {
    // Windows does not have buffering...
  }

  @Override
  public void clrscr()
  {
    COORD.ByValue c = new COORD.ByValue();
    IntByReference lpNumberOfCharsWritten = new IntByReference();
    c.X = 0;
    c.Y = 0;
    short nLength = (short)(info.dwSize.X * info.dwSize.Y);

    Kernel32.INSTANCE.FillConsoleOutputCharacterA(hConsole, ' ', nLength, c, lpNumberOfCharsWritten);
  }

  @Override
  public void gotoxy(int x, int y)
  {
    COORD.ByValue c = new COORD.ByValue();
    c.X = (short)x;
    c.Y = (short)y;

    Kernel32.INSTANCE.SetConsoleCursorPosition(hConsole, c);
  }

  @Override
  public void displayCursor(boolean visible)
  {
    CONSOLE_CURSOR_INFO cursor = new CONSOLE_CURSOR_INFO();
    cursor.dwSize = cursorInfo.dwSize;
    cursor.bVisible = visible;
    Kernel32.INSTANCE.SetConsoleCursorInfo(hConsole, cursor);
  }

  @Override
  public void textBackground(TerminalColor color)
  {
    if ((color == TerminalColor.COLOR_UNCHANGED) || (color == TerminalColor.NO_COLOR))
      return;

    short fg = colorToWinColor(currentForeground(), true);
    short bg = colorToWinColor(color, false);

    Kernel32.INSTANCE.SetConsoleTextAttribute(hConsole, (short)(fg | bg));
    currentBg = color;
  }

  @Override
  public void textForeground(TerminalColor color)
  {
    if ((color == TerminalColor.COLOR_UNCHANGED) || (color == TerminalColor.NO_COLOR))
      return;

    short fg = colorToWinColor(color, true);
    short bg = colorToWinColor(currentBackground(), false);

    Kernel32.INSTANCE.SetConsoleTextAttribute(hConsole, (short)(fg | bg));
    currentFg = color;
  }

  @Override
  public TerminalColor currentBackground()
  {
    return currentBg;
  }

  @Override
  public TerminalColor currentForeground()
  {
    return currentFg;
  }

  @Override
  public void putc(char c)
  {
    Msvcrt.INSTANCE._putch(c);
  }

  @Override
  public boolean kbhit()
  {
    return Msvcrt.INSTANCE._kbhit() != 0;
  }

  @Override
  public VKey readKey()
  {
    int c = -1;

    // read the input
    if (Msvcrt.INSTANCE._kbhit() != 0)
      c = Msvcrt.INSTANCE._getch();

    // check if it is a special key
    if (c == 224)
    {
      c = Msvcrt.INSTANCE._getch();

      switch(c)
      {
        case 75: return VKey.LEFT_ARROW;
        case 77: return VKey.RIGHT_ARROW;
        case 72: return VKey.UP_ARROW;
        case 80: return VKey.DOWN_ARROW;
        default: c = -1;
      }
    }

    // if we're here, it should be a "normal" key
    switch(c)
    {
      case 32: return VKey.SPACE;
      case 13: return VKey.ENTER;
      case 27: return VKey.ESC;
      default: return VKey.NONE;
    }
  }
}
