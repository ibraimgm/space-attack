package spaceattack.platform.win32;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary
{
  Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
  
  int GetStdHandle(int nStdHandle);
  
  boolean GetConsoleScreenBufferInfo(int hConsoleOutput, 
                                     CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
  
  boolean SetConsoleCursorPosition(int hConsoleOutput, COORD.ByValue dwCursorPosition);
  
  boolean FillConsoleOutputCharacterA(int hConsoleOutput,
                                      char cCharacter,
                                      short nLength,
                                      COORD.ByValue dwWriteCoord,
                                      IntByReference lpNumberOfCharsWritten);
  
  boolean SetConsoleTextAttribute(int hConsoleOutput, short wAttributes);
  boolean GetConsoleCursorInfo(int hConsoleOutput, CONSOLE_CURSOR_INFO lpConsoleCursorInfo);
  boolean SetConsoleCursorInfo(int hConsoleOutput, CONSOLE_CURSOR_INFO lpConsoleCursorInfo);
}
