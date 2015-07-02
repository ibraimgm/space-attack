package spaceattack.terminal;


public enum TerminalColor
{
  NO_COLOR(-2),
  COLOR_UNCHANGED(-1),
  DULL_BLACK(AnsiColors.DULL_BLACK),
  DULL_RED(AnsiColors.DULL_RED),
  DULL_GREEN(AnsiColors.DULL_GREEN),
  DULL_YELLOW(AnsiColors.DULL_YELLOW),
  DULL_BLUE(AnsiColors.DULL_BLUE),
  DULL_MAGENTA(AnsiColors.DULL_MAGENTA),
  DULL_CYAN(AnsiColors.DULL_CYAN),
  DULL_WHITE(AnsiColors.DULL_WHITE),
  VIVID_BLACK(AnsiColors.VIVID_BLACK),
  VIVID_RED(AnsiColors.VIVID_RED),
  VIVID_GREEN(AnsiColors.VIVID_GREEN),
  VIVID_YELLOW(AnsiColors.VIVID_YELLOW),
  VIVID_BLUE(AnsiColors.VIVID_BLUE),
  VIVID_MAGENTA(AnsiColors.VIVID_MAGENTA),
  VIVID_CYAN(AnsiColors.VIVID_CYAN),
  VIVID_WHITE(AnsiColors.VIVID_WHITE);

  private final int value;

  private TerminalColor(int value)
  {
    this.value = value;
  }

  public int getValue()
  {
    return value;
  }

 // aux convert methods
 public static TerminalColor charToColor(char c)
 {
   switch(c)
   {
     case 'k': return DULL_BLACK;
     case 'r': return DULL_RED;
     case 'g': return DULL_GREEN;
     case 'y': return DULL_YELLOW;
     case 'b': return DULL_BLUE;
     case 'm': return DULL_MAGENTA;
     case 'c': return DULL_CYAN;
     case 'w': return DULL_WHITE;

     case 'K': return VIVID_BLACK;
     case 'R': return VIVID_RED;
     case 'G': return VIVID_GREEN;
     case 'Y': return VIVID_YELLOW;
     case 'B': return VIVID_BLUE;
     case 'M': return VIVID_MAGENTA;
     case 'C': return VIVID_CYAN;
     case 'W': return VIVID_WHITE;

     case '_': return COLOR_UNCHANGED;

     default: return NO_COLOR;
   }
 }
}
