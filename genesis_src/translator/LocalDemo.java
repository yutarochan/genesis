/*  1:   */ package translator;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import javax.swing.JFrame;
/*  5:   */ 
/*  6:   */ public class LocalDemo
/*  7:   */   extends Demo
/*  8:   */ {
/*  9:   */   public static void main(String[] ignore)
/* 10:   */   {
/* 11:13 */     Demo d = new Demo();
/* 12:14 */     JFrame frame = new JFrame();
/* 13:15 */     frame.setDefaultCloseOperation(3);
/* 14:16 */     frame.getContentPane().add(d);
/* 15:17 */     frame.setSize(800, 600);
/* 16:18 */     frame.setVisible(true);
/* 17:19 */     RuleSet.reportSuccess = true;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.LocalDemo
 * JD-Core Version:    0.7.0.1
 */