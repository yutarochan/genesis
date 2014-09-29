/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import text.Punctuator;
/*  7:   */ import utils.JHtmlLabel;
/*  8:   */ 
/*  9:   */ public class NameLabel
/* 10:   */   extends JHtmlLabel
/* 11:   */   implements WiredBox
/* 12:   */ {
/* 13:   */   public NameLabel(String... x)
/* 14:   */   {
/* 15:16 */     super(x);
/* 16:17 */     super.setText("");
/* 17:   */     
/* 18:   */ 
/* 19:20 */     Connections.getPorts(this).addSignalProcessor("process");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void process(Object input)
/* 23:   */   {
/* 24:24 */     super.setText(Punctuator.conditionName(input.toString()));
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.NameLabel
 * JD-Core Version:    0.7.0.1
 */