/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import javax.swing.JLabel;
/*  4:   */ 
/*  5:   */ public class JHtmlLabel
/*  6:   */   extends JLabel
/*  7:   */ {
/*  8:14 */   private String front = "<html>";
/*  9:16 */   private String back = "</html>";
/* 10:   */   
/* 11:   */   public JHtmlLabel(String... wrappers)
/* 12:   */   {
/* 13:19 */     super("", 0);
/* 14:20 */     for (String x : wrappers)
/* 15:   */     {
/* 16:21 */       this.front = (this.front + "<" + x + ">");
/* 17:22 */       this.back = ("</" + x + ">" + this.back);
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setText(String text)
/* 22:   */   {
/* 23:27 */     super.setText(this.front + text + this.back);
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.JHtmlLabel
 * JD-Core Version:    0.7.0.1
 */