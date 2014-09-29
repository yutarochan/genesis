/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.awt.TextArea;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ 
/*  9:   */ public class WiredTextArea
/* 10:   */   extends TextArea
/* 11:   */   implements WiredBox
/* 12:   */ {
/* 13:   */   public static final String TRANSMIT = "transmit";
/* 14:   */   
/* 15:   */   public WiredTextArea()
/* 16:   */   {
/* 17:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 18:19 */     Connections.getPorts(this).addSignalProcessor("transmit", "transmit");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void transmit(Object o)
/* 22:   */   {
/* 23:23 */     Connections.getPorts(this).transmit("transmit", getText());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void process(Object o)
/* 27:   */   {
/* 28:27 */     if ((o instanceof ArrayList))
/* 29:   */     {
/* 30:28 */       String text = "";
/* 31:29 */       for (Object s : (ArrayList)o) {
/* 32:30 */         text = text + s.toString() + "\n";
/* 33:   */       }
/* 34:32 */       setText(text);
/* 35:   */     }
/* 36:34 */     else if ((o instanceof String))
/* 37:   */     {
/* 38:35 */       setText((String)o);
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.WiredTextArea
 * JD-Core Version:    0.7.0.1
 */