package spaceattack.framework;

import spaceattack.terminal.VKey;

public interface GameIO
{
  // input
  VKey peekKey();
  VKey consumeKey();
  void fetchInputs();

  // output
  Screen mainScreen();
  Screen createScreen(Screen source, int x, int y, int width, int height);
  void destroyScreen(Screen target);
  
  // events
  int peekEvent();
  int consumeEvent();
  void requestCustom(int event);
  
  default void requestPause()
  {
    requestCustom(GEvents.EVT_PAUSE);
  }
  
  default void requestResume()
  {
    requestCustom(GEvents.EVT_RESUME);
  }
  
  default void requestQuit()
  {
    requestCustom(GEvents.EVT_QUIT);
  }
}
