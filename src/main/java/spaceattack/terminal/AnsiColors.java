package spaceattack.terminal;

final class AnsiColors
{
  // colors based on ANSI standard
  private static final int INTENSITY_DULL = 0;
  private static final int INTENSITY_VIVID = 60;

  private static final int COLOR_BLACK = 0;
  private static final int COLOR_RED = 1;
  private static final int COLOR_GREEN = 2;
  private static final int COLOR_YELLOW = 3;
  private static final int COLOR_BLUE = 4;
  private static final int COLOR_MAGENTA = 5;
  private static final int COLOR_CYAN = 6;
  private static final int COLOR_WHITE = 7;

  // the colors themselves
  public static final int DULL_BLACK   = INTENSITY_DULL + COLOR_BLACK;
  public static final int DULL_RED     = INTENSITY_DULL + COLOR_RED;
  public static final int DULL_GREEN   = INTENSITY_DULL + COLOR_GREEN;
  public static final int DULL_YELLOW  = INTENSITY_DULL + COLOR_YELLOW;
  public static final int DULL_BLUE    = INTENSITY_DULL + COLOR_BLUE;
  public static final int DULL_MAGENTA = INTENSITY_DULL + COLOR_MAGENTA;
  public static final int DULL_CYAN    = INTENSITY_DULL + COLOR_CYAN;
  public static final int DULL_WHITE   = INTENSITY_DULL + COLOR_WHITE;

  public static final int VIVID_BLACK   = INTENSITY_VIVID + COLOR_BLACK;
  public static final int VIVID_RED     = INTENSITY_VIVID + COLOR_RED;
  public static final int VIVID_GREEN   = INTENSITY_VIVID + COLOR_GREEN;
  public static final int VIVID_YELLOW  = INTENSITY_VIVID + COLOR_YELLOW;
  public static final int VIVID_BLUE    = INTENSITY_VIVID + COLOR_BLUE;
  public static final int VIVID_MAGENTA = INTENSITY_VIVID + COLOR_MAGENTA;
  public static final int VIVID_CYAN    = INTENSITY_VIVID + COLOR_CYAN;
  public static final int VIVID_WHITE   = INTENSITY_VIVID + COLOR_WHITE;
}
