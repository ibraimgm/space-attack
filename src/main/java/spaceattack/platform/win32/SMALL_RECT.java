package spaceattack.platform.win32;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class SMALL_RECT extends Structure
{
  public short Left;
  public short Top;
  public short Right;
  public short Bottom;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder()
  {
    return Arrays.asList(new String[] 
        {
         "Left",
         "Top",
         "Right",
         "Bottom"
        });
  }

}
