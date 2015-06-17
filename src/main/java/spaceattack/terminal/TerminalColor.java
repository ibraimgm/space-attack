package spaceattack.terminal;

public final class TerminalColor
{  
 //colors based on ANSI standard
 private static final int INTENSITY_DULL  = 0;
 private static final int INTENSITY_VIVID = 60;

 private static final int COLOR_BLACK   = 0;
 private static final int COLOR_RED     = 1;
 private static final int COLOR_GREEN   = 2;
 private static final int COLOR_YELLOW  = 3;
 private static final int COLOR_BLUE    = 4;
 private static final int COLOR_MAGENTA = 5;
 private static final int COLOR_CYAN    = 6;
 private static final int COLOR_WHITE   = 7;
 
 // colors usable by the user
 public static final int NO_COLOR            = -2;          
 public static final int COLOR_UNCHANGED     = -1;
 
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
 
 // aux convert methods
 public static int charToColor(char c)
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
