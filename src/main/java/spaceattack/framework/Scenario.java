package spaceattack.framework;

public interface Scenario
{
  void init();
  void pause();
  void resume();
  void update(GameIO controller, float delta);
  void render(GameIO controller, float delta);
  Scenario quit();
}
