/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class Probe
/*  6:   */   extends AbstractWiredBox
/*  7:   */ {
/*  8:   */   public Probe(String name)
/*  9:   */   {
/* 10:11 */     setName(name);
/* 11:12 */     Connections.getPorts(this).addSignalProcessor("input");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void input(Object input)
/* 15:   */   {
/* 16:16 */     System.out.println("Probe " + getName() + " received " + input.toString());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void main(String[] args)
/* 20:   */   {
/* 21:23 */     Probe A = new Probe("source");
/* 22:24 */     Probe B = new Probe("destination");
/* 23:25 */     Connections.wire(A, B);
/* 24:26 */     Connections.getPorts(A).transmit("Hello world");
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.Probe
 * JD-Core Version:    0.7.0.1
 */