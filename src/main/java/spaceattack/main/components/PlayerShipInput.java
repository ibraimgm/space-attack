package spaceattack.main.components;

import spaceattack.framework.GameIO;
import spaceattack.framework.ecs.Component;
import spaceattack.framework.ecs.EntitySystem;
import spaceattack.main.components.Position.OOB;
import spaceattack.terminal.TerminalColor;

public final class PlayerShipInput implements Component
{
  public void execute(long self, EntitySystem es, GameIO io)
  {
    Position p = es.getComponent(self, Position.class);

    if (p != null)
      switch (io.peekKey())
      {
        case LEFT_ARROW:
          p.x--;
          break;

        case RIGHT_ARROW:
          p.x++;
          break;

        case SPACE:
          es.requestChange(ess -> {
            long id = ess.newEntity();
            ess.putComponent(id, new Position(p.x, p.y - 1, OOB.VANISH));
            ess.putComponent(id, new Draw(TerminalColor.VIVID_YELLOW, TerminalColor.DULL_BLACK, '|'));
            ess.putComponent(id, new TimedMove(0, -1, true, 100));
            ess.putComponent(id, new Collision(Collision.Type.PLAYER_SHOT));
          });
          break;

        default: break;
      }

    io.consumeKey();
  }
}
