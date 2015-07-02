package spaceattack.os.win32;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class COORD extends Structure
{
  public static class ByValue extends COORD implements Structure.ByValue {};

  public short X;
  public short Y;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder()
  {
    return Arrays.asList(new String[]
        {
          "X",
          "Y"
        });
  }

}
