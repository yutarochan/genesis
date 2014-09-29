/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import frames.PathElementFrame;
/*  8:   */ import java.awt.Container;
/*  9:   */ import java.awt.Graphics;
/* 10:   */ import java.awt.Graphics2D;
/* 11:   */ import java.io.PrintStream;
/* 12:   */ import javax.swing.JFrame;
/* 13:   */ import javax.swing.JPanel;
/* 14:   */ 
/* 15:   */ public class CauseViewer
/* 16:   */   extends JPanel
/* 17:   */   implements WiredBox
/* 18:   */ {
/* 19:   */   public CauseViewer()
/* 20:   */   {
/* 21:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void process(Object signal)
/* 25:   */   {
/* 26:23 */     System.out.println("Cause observed!");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void paint(Graphics graphics)
/* 30:   */   {
/* 31:27 */     Graphics2D g = (Graphics2D)graphics;
/* 32:28 */     int height = getHeight();
/* 33:29 */     int width = getWidth();
/* 34:   */   }
/* 35:   */   
/* 36:   */   private void setParameters(String preposition)
/* 37:   */   {
/* 38:33 */     System.err.println("Cause word is " + preposition);
/* 39:34 */     repaint();
/* 40:   */   }
/* 41:   */   
/* 42:   */   private void clearData() {}
/* 43:   */   
/* 44:   */   public static void main(String[] args)
/* 45:   */   {
/* 46:44 */     CauseViewer view = new CauseViewer();
/* 47:45 */     Entity thing = new Entity();
/* 48:46 */     thing.addType("dog");
/* 49:47 */     PathElementFrame pathElement = new PathElementFrame("toward", thing);
/* 50:48 */     JFrame frame = new JFrame();
/* 51:49 */     frame.getContentPane().add(view);
/* 52:50 */     frame.setBounds(0, 0, 200, 200);
/* 53:51 */     frame.setVisible(true);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.CauseViewer
 * JD-Core Version:    0.7.0.1
 */