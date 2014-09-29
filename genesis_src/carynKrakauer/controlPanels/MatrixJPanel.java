/*  1:   */ package carynKrakauer.controlPanels;
/*  2:   */ 
/*  3:   */ import java.awt.Graphics;
/*  4:   */ import java.text.DecimalFormat;
/*  5:   */ import javax.swing.JPanel;
/*  6:   */ 
/*  7:   */ public class MatrixJPanel
/*  8:   */   extends JPanel
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   
/* 12:   */   protected void writeStdDevError(Graphics g, DecimalFormat df, double stdDevError)
/* 13:   */   {
/* 14:23 */     if ((stdDevError < 0.0D) || (Double.isNaN(stdDevError))) {
/* 15:24 */       g.drawString("StdDev error: ---", 0, 20);
/* 16:   */     } else {
/* 17:28 */       g.drawString("StdDev error: " + df.format(stdDevError), 0, 20);
/* 18:   */     }
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.MatrixJPanel
 * JD-Core Version:    0.7.0.1
 */