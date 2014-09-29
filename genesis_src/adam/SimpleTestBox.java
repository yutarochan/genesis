/*  1:   */ package adam;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ 
/*  8:   */ public class SimpleTestBox
/*  9:   */   implements WiredBox
/* 10:   */ {
/* 11:   */   public String getName()
/* 12:   */   {
/* 13:15 */     return "Simple Test Box";
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void print(Object in)
/* 17:   */   {
/* 18:19 */     System.out.println("Java Simple test box got: " + in);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SimpleTestBox()
/* 22:   */   {
/* 23:23 */     Connections.getPorts(this).addSignalProcessor("print");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void transmit()
/* 27:   */   {
/* 28:27 */     Connections.getPorts(this).transmit("sent from Java");
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.SimpleTestBox
 * JD-Core Version:    0.7.0.1
 */