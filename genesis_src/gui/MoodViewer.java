/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.Ports;
/*  6:   */ import gifs.PictureAnchor;
/*  7:   */ import java.awt.Color;
/*  8:   */ import java.awt.Container;
/*  9:   */ import javax.swing.BorderFactory;
/* 10:   */ import javax.swing.ImageIcon;
/* 11:   */ import javax.swing.JFrame;
/* 12:   */ 
/* 13:   */ public class MoodViewer
/* 14:   */   extends PictureViewer
/* 15:   */ {
/* 16:   */   Ports ports;
/* 17:   */   
/* 18:   */   public MoodViewer()
/* 19:   */   {
/* 20:24 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setState(String state)
/* 24:   */   {
/* 25:28 */     if ((state != null) && (state.equals("happy"))) {
/* 26:29 */       setImage(new ImageIcon(PictureAnchor.class.getResource("mood/happy.png")));
/* 27:31 */     } else if ((state != null) && (state.equals("unhappy"))) {
/* 28:32 */       setImage(new ImageIcon(PictureAnchor.class.getResource("mood/sad.png")));
/* 29:34 */     } else if ((state != null) && (state.equals("angry"))) {
/* 30:35 */       setImage(new ImageIcon(PictureAnchor.class.getResource("mood/angry.png")));
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void view(Object input)
/* 35:   */   {
/* 36:40 */     if (!(input instanceof Entity)) {
/* 37:41 */       return;
/* 38:   */     }
/* 39:43 */     Entity t = (Entity)input;
/* 40:44 */     if ((t.isAPrimed("has-mental-state")) && (t.relationP()))
/* 41:   */     {
/* 42:46 */       Relation r = (Relation)t;
/* 43:48 */       if (r.getObject().isAPrimed("mental-state")) {
/* 44:49 */         setState(t.getObject().getType());
/* 45:   */       }
/* 46:   */     }
/* 47:52 */     setTruthValue(input);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static void main(String[] args)
/* 51:   */   {
/* 52:56 */     MoodViewer viewer = new MoodViewer();
/* 53:57 */     JFrame frame = new JFrame("Testing");
/* 54:58 */     frame.getContentPane().add(viewer);
/* 55:59 */     frame.setBounds(100, 100, 400, 400);
/* 56:60 */     frame.setDefaultCloseOperation(3);
/* 57:61 */     frame.setVisible(true);
/* 58:62 */     Entity t = new Entity("mental-state");
/* 59:63 */     t.addType("negative");
/* 60:64 */     Relation r = new Relation("has-mental-state", new Entity(), t);
/* 61:65 */     viewer.view(r);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.MoodViewer
 * JD-Core Version:    0.7.0.1
 */