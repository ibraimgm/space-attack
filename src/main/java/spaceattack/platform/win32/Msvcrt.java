package spaceattack.platform.win32;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Msvcrt extends Library
{
  Msvcrt INSTANCE = (Msvcrt) Native.loadLibrary("msvcrt", Msvcrt.class);

  int _kbhit();
  int _getch();
  int _putch(int c);
}
