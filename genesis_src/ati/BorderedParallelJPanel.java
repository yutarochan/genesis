/*  1:   */ package ati;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import javax.swing.BorderFactory;
/*  5:   */ import javax.swing.border.Border;
/*  6:   */ import javax.swing.border.TitledBorder;
/*  7:   */ 
/*  8:   */ public class BorderedParallelJPanel
/*  9:   */   extends ParallelJPanel
/* 10:   */ {
/* 11:   */   public BorderedParallelJPanel(String name)
/* 12:   */   {
/* 13:16 */     Border lb = BorderFactory.createLineBorder(Color.black);
/* 14:17 */     TitledBorder border = BorderFactory.createTitledBorder(lb, name);
/* 15:18 */     border.setTitleColor(Color.BLACK);
/* 16:   */     
/* 17:20 */     setBorder(border);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.BorderedParallelJPanel
 * JD-Core Version:    0.7.0.1
 */