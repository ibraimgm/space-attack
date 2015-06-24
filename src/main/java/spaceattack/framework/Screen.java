package spaceattack.framework;

public interface Screen
{
  int getX();
  int getY();
  int getWidth();
  int getHeight();
  
  void setBackground(int color);
  void setForeground(int color);
  void clear(int color);
  void blit();
  
  void drawText(int x, int y, char c);  
  void drawText(int x, int y, String s);
}
