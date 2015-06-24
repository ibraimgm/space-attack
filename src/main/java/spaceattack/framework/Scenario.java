package spaceattack.framework;

public interface Scenario
{
  void init();
  void pause();
  void resume();
  void update(GameIO io, double delta);
  void render(GameIO io, double delta);
  Scenario quit();
}
