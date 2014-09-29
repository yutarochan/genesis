/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import javax.swing.JFrame;
/*  6:   */ import javax.swing.JLabel;
/*  7:   */ 
/*  8:   */ public class HelloWorld
/*  9:   */   extends JFrame
/* 10:   */ {
/* 11:   */   public static void main(String[] args)
/* 12:   */   {
/* 13:13 */     HelloWorld hw = new HelloWorld();
/* 14:14 */     hw.getContentPane().add(new JLabel("Hello World"));
/* 15:15 */     hw.setBounds(0, 0, 500, 500);
/* 16:16 */     hw.setVisible(true);
/* 17:17 */     System.gc();
/* 18:18 */     System.out.println("Memory A1: " + Runtime.getRuntime().freeMemory());
/* 19:19 */     System.out.println("Memory A2: " + Runtime.getRuntime().totalMemory());
/* 20:20 */     Genesis genesis = new Genesis();
/* 21:21 */     System.out.println("Memory B1: " + Runtime.getRuntime().freeMemory());
/* 22:22 */     System.out.println("Memory B2: " + Runtime.getRuntime().totalMemory());
/* 23:23 */     genesis.startInFrame();
/* 24:24 */     System.gc();
/* 25:25 */     System.out.println("Memory C1: " + Runtime.getRuntime().freeMemory());
/* 26:26 */     System.out.println("Memory C2: " + Runtime.getRuntime().totalMemory());
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.HelloWorld
 * JD-Core Version:    0.7.0.1
 */