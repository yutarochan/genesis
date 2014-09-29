/*  1:   */ package testBoxes;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.util.Vector;
/*  7:   */ 
/*  8:   */ public class RecordBox
/*  9:   */   implements WiredBox
/* 10:   */ {
/* 11:   */   private Vector<Object> inputs;
/* 12:   */   
/* 13:   */   public RecordBox()
/* 14:   */   {
/* 15:13 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:17 */     return "Test: Record Box";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void process(Object input)
/* 24:   */   {
/* 25:21 */     this.inputs.add(input);
/* 26:22 */     Connections.getPorts(this).transmit(input);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Vector<Object> getRecord()
/* 30:   */   {
/* 31:25 */     return this.inputs;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     testBoxes.RecordBox
 * JD-Core Version:    0.7.0.1
 */