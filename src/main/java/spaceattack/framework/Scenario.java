package spaceattack.framework;

public interface Scenario
{
  void init(GameIO io);
  void pause(GameIO io);
  void resume(GameIO io);
  void update(GameIO io, double delta);
  void render(GameIO io, double delta);
  Scenario quit(GameIO io);
}
