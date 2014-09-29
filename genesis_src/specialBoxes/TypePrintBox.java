/*  1:   */ package specialBoxes;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ 
/*  8:   */ public class TypePrintBox
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:15 */   private String name = "typePrintBox";
/* 12:   */   
/* 13:   */   public TypePrintBox(String newName)
/* 14:   */   {
/* 15:18 */     this.name = newName;
/* 16:19 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:23 */     return "typePrintBox";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void process(Object o)
/* 25:   */   {
/* 26:27 */     System.out.println("###########" + this.name + "##############");
/* 27:28 */     System.out.println(o.getClass());
/* 28:29 */     System.out.println("########### END PRINT BOX ##############\n");
/* 29:30 */     Connections.getPorts(this).transmit(o);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     specialBoxes.TypePrintBox
 * JD-Core Version:    0.7.0.1
 */