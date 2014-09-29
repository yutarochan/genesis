/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Ports;
/*   7:    */ import gifs.PictureAnchor;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.Font;
/*  11:    */ import java.awt.FontMetrics;
/*  12:    */ import java.awt.Graphics;
/*  13:    */ import javax.swing.BorderFactory;
/*  14:    */ import javax.swing.ImageIcon;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ 
/*  17:    */ public class GoalViewer
/*  18:    */   extends PictureViewer
/*  19:    */ {
/*  20:    */   Ports ports;
/*  21: 22 */   private String action = "";
/*  22: 24 */   private String actor = "";
/*  23:    */   
/*  24:    */   public GoalViewer()
/*  25:    */   {
/*  26: 28 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setState(String state, String actor, String action)
/*  30:    */   {
/*  31: 32 */     setActor(actor);
/*  32: 33 */     setAction(action);
/*  33: 34 */     if ((state != null) && (state.equals("want"))) {
/*  34: 35 */       setImage(new ImageIcon(PictureAnchor.class.getResource("thumbUp.jpg")));
/*  35: 37 */     } else if ((state != null) && (state.equals("notWant"))) {
/*  36: 38 */       setImage(new ImageIcon(PictureAnchor.class.getResource("thumbDown.jpg")));
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void setActor(String actor)
/*  41:    */   {
/*  42: 44 */     this.actor = actor;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void setAction(String action)
/*  46:    */   {
/*  47: 48 */     this.action = action;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void view(Object input)
/*  51:    */   {
/*  52: 52 */     if (!(input instanceof Entity)) {
/*  53: 53 */       return;
/*  54:    */     }
/*  55: 55 */     Entity t = (Entity)input;
/*  56: 56 */     if ((t.isAPrimed("goal")) && (t.relationP()))
/*  57:    */     {
/*  58: 57 */       Relation r = (Relation)t;
/*  59: 58 */       String action = "?";
/*  60: 59 */       if (r.getObject().isAPrimed("roles"))
/*  61:    */       {
/*  62: 60 */         Sequence s = (Sequence)r.getObject();
/*  63: 61 */         for (Entity x : s.getElements()) {
/*  64: 62 */           if ((x.functionP()) && (x.isAPrimed("object"))) {
/*  65: 63 */             action = x.getSubject().getType();
/*  66:    */           }
/*  67:    */         }
/*  68:    */       }
/*  69: 67 */       if (r.isA("not")) {
/*  70: 68 */         setState("notWant", r.getSubject().getType(), action);
/*  71:    */       } else {
/*  72: 71 */         setState("want", r.getSubject().getType(), r.getObject().getType());
/*  73:    */       }
/*  74:    */     }
/*  75: 74 */     setTruthValue(input);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void paint(Graphics g)
/*  79:    */   {
/*  80: 78 */     super.paint(g);
/*  81: 79 */     int w = getWidth();
/*  82: 80 */     int h = getHeight();
/*  83: 81 */     Font f = g.getFont();
/*  84: 82 */     int fontSize = w / 10;
/*  85: 83 */     g.setFont(new Font(f.getName(), f.getStyle(), fontSize));
/*  86: 84 */     FontMetrics fontMetrics = g.getFontMetrics();
/*  87: 85 */     int fOffset = fontMetrics.getDescent();
/*  88: 86 */     int wActor = fontMetrics.stringWidth(this.actor);
/*  89: 87 */     int wAction = fontMetrics.stringWidth(this.action);
/*  90: 88 */     g.drawString(this.actor, 5, h - fOffset);
/*  91: 89 */     g.drawString(this.action, w - wAction - 5, h - fOffset);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void main(String[] args)
/*  95:    */   {
/*  96: 93 */     GoalViewer viewer = new GoalViewer();
/*  97: 94 */     JFrame frame = new JFrame("Testing");
/*  98: 95 */     frame.getContentPane().add(viewer);
/*  99: 96 */     frame.setBounds(100, 100, 400, 400);
/* 100: 97 */     frame.setDefaultCloseOperation(3);
/* 101: 98 */     frame.setVisible(true);
/* 102: 99 */     Relation t = new Relation("goal", new Entity("may"), new Entity("rug"));
/* 103:100 */     t.addType("want");
/* 104:101 */     t.addType("not", "feature");
/* 105:102 */     viewer.view(t);
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.GoalViewer
 * JD-Core Version:    0.7.0.1
 */