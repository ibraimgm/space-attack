package spaceattack.main;

import spaceattack.terminal.Terminal;
import spaceattack.terminal.TerminalFactory;


public class App
{

  public static void main(String[] args)
  {
    Terminal t = TerminalFactory.getInstance();
    t.setup();
    t.puts("Hello World!");
    t.putc('A');
    t.putc('B');
    t.putc('C');
    t.printf("%nI'm printing the number %d!%n", 15);
    t.printc("The numbers are $y{earth = %d}, $C{wind = %d} %nand $R{fire = %d}!%n%n", 1, 5, 10);
    
    t.puts("Please, press any key to exit");
    t.flush();
    t.readKey();
    t.tearDown();
  }
}
