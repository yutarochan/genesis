/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import java.awt.Component;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.awt.Graphics;
/*  6:   */ import java.awt.Graphics2D;
/*  7:   */ import java.awt.print.Book;
/*  8:   */ import java.awt.print.PageFormat;
/*  9:   */ import java.awt.print.Printable;
/* 10:   */ import java.awt.print.PrinterJob;
/* 11:   */ import java.io.PrintStream;
/* 12:   */ 
/* 13:   */ public class EasyPrint
/* 14:   */ {
/* 15:   */   public static int easyPrint(Component screen, Graphics graphics, PageFormat format, int pageIndex)
/* 16:   */   {
/* 17:17 */     double offsetX = 0.0D;double offsetY = 0.0D;
/* 18:18 */     Graphics2D g = (Graphics2D)graphics;
/* 19:19 */     Dimension d = screen.getSize();
/* 20:20 */     double scaleW = format.getImageableWidth() / d.width;
/* 21:21 */     double scaleH = format.getImageableHeight() / d.height;
/* 22:22 */     double scale = 1.0D;
/* 23:23 */     if (scaleW > scaleH) {
/* 24:24 */       scale = scaleH;
/* 25:   */     } else {
/* 26:27 */       scale = scaleW;
/* 27:   */     }
/* 28:29 */     offsetX = format.getImageableX();
/* 29:30 */     offsetY = format.getImageableY();
/* 30:31 */     g.translate(offsetX, offsetY);
/* 31:32 */     g.scale(scale, scale);
/* 32:33 */     screen.print(g);
/* 33:34 */     System.out.println("Hello world, I'm runing easy print with four arguments!");
/* 34:35 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void easyPrint(Printable screen)
/* 38:   */   {
/* 39:41 */     System.out.println("Printing...");
/* 40:42 */     PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 41:43 */     boolean doPrint = printerJob.printDialog();
/* 42:44 */     if (!doPrint)
/* 43:   */     {
/* 44:44 */       System.out.println("Printing cancelled");return;
/* 45:   */     }
/* 46:45 */     Book book = new Book();
/* 47:46 */     PageFormat oldFormat = new PageFormat();
/* 48:47 */     PageFormat newFormat = printerJob.pageDialog(oldFormat);
/* 49:48 */     if (oldFormat == newFormat)
/* 50:   */     {
/* 51:48 */       System.out.println("Printing cancelled");return;
/* 52:   */     }
/* 53:49 */     book.append(screen, newFormat);
/* 54:50 */     printerJob.setPageable(book);
/* 55:51 */     System.out.println("Hello world, I'm runing easy print with one argument!");
/* 56:   */     try
/* 57:   */     {
/* 58:53 */       printerJob.print();
/* 59:   */     }
/* 60:   */     catch (Exception e)
/* 61:   */     {
/* 62:56 */       e.printStackTrace();System.out.println("Printing failed");
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.EasyPrint
 * JD-Core Version:    0.7.0.1
 */