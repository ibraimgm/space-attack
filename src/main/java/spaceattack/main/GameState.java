package spaceattack.main;

public final class GameState
{
  private static final int MAX_HP = 20;

  private int totalScore;
  private int cycle;
  private int hp;
  private WinState winState;

  public GameState()
  {
    totalScore = 0;
    cycle = 0;
    hp = MAX_HP;
    winState = WinState.UNDECIDED;
  }

  public int getTotalScore()
  {
    return totalScore;
  }

  public int getCycle()
  {
    return cycle;
  }

  public void takeDamage(int damage)
  {
    hp = damage > hp ? 0 : hp - damage;

    if (hp == 0)
      lose();
  }

  public String hpBarStr()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("$C{");

    for (int i = 0; i < hp; ++i)
      sb.append(":");

    sb.append("}$R{");

    for (int i = hp; i < MAX_HP; ++i)
      sb.append(":");

    sb.append("}");

    return sb.toString();
  }

  public String getCycleDescription()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("NG");

    if (cycle > 7)
    {
      sb.append(cycle - 1);
      sb.append("+");
    }
    else
      for (int i = 1; i <= cycle; ++i)
        sb.append("+");

    return sb.toString();
  }

  public void addScore(int score)
  {
    totalScore += score;
  }

  public void nextCycle()
  {
    ++cycle;
  }

  public boolean isGameWon()
  {
    return winState == WinState.GAME_WON;
  }

  public boolean isGameLost()
  {
    return winState == WinState.GAME_LOST;
  }

  public boolean isPlaying()
  {
    return winState == WinState.UNDECIDED;
  }

  public void win()
  {
    winState = WinState.GAME_WON;
  }

  public void lose()
  {
    winState = WinState.GAME_LOST;
  }

  public enum WinState
  {
    GAME_WON,
    GAME_LOST,
    UNDECIDED
  }
}
