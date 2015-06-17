package spaceattack.platform.win32;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class CONSOLE_SCREEN_BUFFER_INFO extends Structure
{
  public COORD dwSize;
  public COORD dwCursorPosition;
  public short wAttributes;
  public SMALL_RECT srWindow;
  public COORD dwMaximumWindowSize;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder()
  {
    return Arrays.asList(new String[]
        {
         "dwSize",
         "dwCursorPosition",
         "wAttributes",
         "srWindow",
         "dwMaximumWindowSize"
        });
  }

}
