/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import java.awt.BorderLayout;
/*  6:   */ import java.awt.Color;
/*  7:   */ import java.awt.Container;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ import javax.swing.BorderFactory;
/* 10:   */ import javax.swing.JFrame;
/* 11:   */ import javax.swing.JLabel;
/* 12:   */ import javax.swing.border.TitledBorder;
/* 13:   */ 
/* 14:   */ public class RelationViewer
/* 15:   */   extends NegatableJPanel
/* 16:   */ {
/* 17:20 */   String part = "";
/* 18:22 */   String type = "";
/* 19:24 */   JLabel innerPanel = new JLabel("");
/* 20:26 */   TitledBorder border = BorderFactory.createTitledBorder("");
/* 21:   */   
/* 22:   */   public RelationViewer()
/* 23:   */   {
/* 24:29 */     setOpaque(false);
/* 25:30 */     setLayout(new BorderLayout());
/* 26:31 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/* 27:32 */     this.innerPanel.setBorder(this.border);
/* 28:33 */     add(this.innerPanel);
/* 29:34 */     this.innerPanel.setHorizontalAlignment(0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void view(Object signal)
/* 33:   */   {
/* 34:38 */     if ((signal instanceof Entity))
/* 35:   */     {
/* 36:39 */       Entity input = (Entity)signal;
/* 37:40 */       if (input.relationP())
/* 38:   */       {
/* 39:41 */         this.type = ((Relation)input).getSubject().getType();
/* 40:42 */         this.part = ((Relation)input).getObject().getType();
/* 41:43 */         this.innerPanel.setText(this.part);
/* 42:44 */         this.border.setTitle(this.type);
/* 43:   */       }
/* 44:   */     }
/* 45:47 */     setTruthValue(signal);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static void main(String[] args)
/* 49:   */   {
/* 50:51 */     RelationViewer reader = new RelationViewer();
/* 51:52 */     JFrame frame = new JFrame("Testing");
/* 52:53 */     frame.getContentPane().add(reader, "Center");
/* 53:54 */     frame.setBounds(100, 100, 200, 600);
/* 54:55 */     frame.setDefaultCloseOperation(3);
/* 55:56 */     frame.setVisible(true);
/* 56:57 */     Relation thing = new Relation("has-body-part", new Entity("bird"), new Entity("wings"));
/* 57:58 */     System.out.println(thing);
/* 58:59 */     reader.view(thing);
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.RelationViewer
 * JD-Core Version:    0.7.0.1
 */