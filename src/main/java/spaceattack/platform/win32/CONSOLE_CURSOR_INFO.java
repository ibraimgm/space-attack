package spaceattack.platform.win32;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class CONSOLE_CURSOR_INFO extends Structure
{
  public int dwSize;
  public boolean bVisible;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder()
  {
    return Arrays.asList(new String[]
        {
         "dwSize",
         "bVisible"
        });
  }

}
