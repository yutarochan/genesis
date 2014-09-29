/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import connections.Ports;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Font;
/*   9:    */ import java.awt.FontMetrics;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.util.Vector;
/*  13:    */ import javax.swing.JFrame;
/*  14:    */ 
/*  15:    */ public class ThreadViewer
/*  16:    */   extends NegatableJPanel
/*  17:    */ {
/*  18:    */   Entity theThing;
/*  19:    */   private Ports ports;
/*  20:    */   
/*  21:    */   public ThreadViewer()
/*  22:    */   {
/*  23: 21 */     setBackground(Color.WHITE);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void view(Object signal)
/*  27:    */   {
/*  28: 26 */     if ((signal instanceof Entity))
/*  29:    */     {
/*  30: 27 */       Entity input = (Entity)signal;
/*  31: 28 */       if (input.relationP())
/*  32:    */       {
/*  33: 31 */         Entity newThing = ((Relation)input).getObject();
/*  34: 32 */         TaughtWords.getTaughtWords().add(newThing.getType(), newThing.getPrimedThread());
/*  35: 33 */         setInput(newThing);
/*  36:    */       }
/*  37:    */     }
/*  38: 36 */     setTruthValue(signal);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void main(String[] args)
/*  42:    */   {
/*  43: 40 */     ThreadViewer reader = new ThreadViewer();
/*  44: 41 */     JFrame frame = new JFrame("Testing");
/*  45: 42 */     frame.getContentPane().add(reader, "Center");
/*  46: 43 */     frame.setBounds(100, 100, 200, 600);
/*  47: 44 */     frame.setDefaultCloseOperation(3);
/*  48: 45 */     frame.setVisible(true);
/*  49: 46 */     Entity thing = new Entity("rabbit");
/*  50: 47 */     thing.addType("mammal");
/*  51: 48 */     System.out.println(thing);
/*  52: 49 */     reader.setInput(thing);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void paintComponent(Graphics g)
/*  56:    */   {
/*  57: 53 */     super.paintComponent(g);
/*  58: 54 */     int h = getHeight();
/*  59: 55 */     int w = getWidth();
/*  60: 56 */     if ((h == 0) || (w == 0)) {
/*  61: 57 */       return;
/*  62:    */     }
/*  63: 59 */     g.drawRect(0, 0, w - 1, h - 1);
/*  64: 60 */     if (this.theThing == null) {
/*  65: 61 */       return;
/*  66:    */     }
/*  67: 63 */     int keepers = 4;
/*  68: 64 */     Vector types = this.theThing.getTypes();
/*  69: 65 */     Vector truncatedTypes = new Vector();
/*  70: 66 */     if (types.size() > keepers)
/*  71:    */     {
/*  72: 67 */       truncatedTypes.addAll(types.subList(types.size() - keepers, types.size()));
/*  73: 68 */       truncatedTypes.add(0, "...");
/*  74: 69 */       truncatedTypes.add(0, types.get(0));
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78: 72 */       truncatedTypes = types;
/*  79:    */     }
/*  80: 74 */     int toBeShown = truncatedTypes.size();
/*  81: 75 */     int spaces = toBeShown + 1;
/*  82: 76 */     g.setFont(new Font("Georgia", 1, Math.max(10, Math.min(14, h / 8))));
/*  83: 77 */     int fontHeight = g.getFontMetrics().getHeight();
/*  84: 78 */     for (int i = 0; i < toBeShown; i++)
/*  85:    */     {
/*  86: 79 */       g.drawString(truncatedTypes.get(i).toString(), 10, h / spaces + i * h / spaces);
/*  87: 80 */       if (i < toBeShown - 1) {
/*  88: 81 */         drawArrow(g, 15, i, spaces, h);
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   private void drawArrow(Graphics g, int xOffset, int i, int spaces, int h)
/*  94:    */   {
/*  95: 87 */     g.setColor(Color.BLUE);
/*  96: 88 */     int fontHeight = g.getFontMetrics().getHeight();
/*  97: 89 */     int top = h / spaces + i * h / spaces + 5;
/*  98: 90 */     int bottom = h / spaces + (i + 1) * h / spaces - fontHeight;
/*  99: 91 */     if (bottom > top) {
/* 100: 92 */       g.drawLine(xOffset, top, xOffset, bottom);
/* 101:    */     }
/* 102: 94 */     int barbPosition = Math.max(0, Math.min(10, bottom - top));
/* 103: 95 */     g.drawLine(xOffset, top, xOffset - barbPosition / 2, top + barbPosition);
/* 104: 96 */     g.drawLine(xOffset, top, xOffset + barbPosition / 2, top + barbPosition);
/* 105: 97 */     g.setColor(Color.BLACK);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setInput(Object input, Object port)
/* 109:    */   {
/* 110:101 */     if ((input instanceof Entity)) {
/* 111:102 */       setInput((Entity)input);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected void setInput(Entity thing)
/* 116:    */   {
/* 117:107 */     this.theThing = thing;
/* 118:108 */     repaint();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Ports getPorts()
/* 122:    */   {
/* 123:112 */     if (this.ports == null) {
/* 124:113 */       this.ports = new Ports();
/* 125:    */     }
/* 126:115 */     return this.ports;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ThreadViewer
 * JD-Core Version:    0.7.0.1
 */