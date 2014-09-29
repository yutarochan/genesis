/*   1:    */ package m2.gui;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Ports;
/*   5:    */ import connections.WiredBox;
/*   6:    */ import java.awt.BorderLayout;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Point;
/*  11:    */ import java.awt.event.MouseEvent;
/*  12:    */ import java.awt.event.MouseListener;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.swing.JLabel;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import m2.M2;
/*  19:    */ import m2.datatypes.Chain;
/*  20:    */ 
/*  21:    */ public class RepViewer
/*  22:    */   extends JPanel
/*  23:    */   implements WiredBox
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 7951486464909702580L;
/*  26: 30 */   private ChainCircle circle = new ChainCircle();
/*  27:    */   
/*  28:    */   public RepViewer()
/*  29:    */   {
/*  30: 34 */     setLayout(new BorderLayout());
/*  31: 35 */     add(this.circle, "Center");
/*  32: 36 */     Connections.getPorts(this).addSignalProcessor("input");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void input(Object input)
/*  36:    */   {
/*  37: 42 */     if ((input instanceof List)) {
/*  38: 43 */       display((List)input);
/*  39:    */     } else {
/*  40: 46 */       System.err.println("Bad input to RepViewer");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void display(List<Chain> chains)
/*  45:    */   {
/*  46: 54 */     this.circle.setEntries(chains);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void output(Object obj)
/*  50:    */   {
/*  51: 61 */     if ((obj instanceof Chain)) {
/*  52: 62 */       Connections.getPorts(this).transmit((Chain)obj);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private class ChainCircle
/*  57:    */     extends JPanel
/*  58:    */   {
/*  59: 71 */     private List<Chain> chains = new ArrayList();
/*  60: 73 */     private List<Point> points = new ArrayList();
/*  61: 75 */     private List<DotLabel> labels = new ArrayList();
/*  62:    */     private int width;
/*  63:    */     private int height;
/*  64:    */     private int radius;
/*  65:    */     int dotSize;
/*  66: 83 */     private Point center = new Point(0, 0);
/*  67:    */     
/*  68:    */     public ChainCircle()
/*  69:    */     {
/*  70: 87 */       setBackground(Color.WHITE);
/*  71: 88 */       setLayout(null);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void setEntries(List<Chain> chains)
/*  75:    */     {
/*  76:    */       try
/*  77:    */       {
/*  78:100 */         this.chains = chains;
/*  79:    */         
/*  80:    */ 
/*  81:103 */         removeAll();
/*  82:104 */         this.labels.clear();
/*  83:105 */         for (int i = 0; i < chains.size(); i++)
/*  84:    */         {
/*  85:106 */           Chain e = (Chain)this.chains.get(i);
/*  86:107 */           DotLabel label = new DotLabel(e);
/*  87:108 */           label.addMouseListener(label);
/*  88:109 */           this.labels.add(label);
/*  89:110 */           add(label);
/*  90:    */         }
/*  91:    */       }
/*  92:    */       catch (Exception e)
/*  93:    */       {
/*  94:114 */         System.out.println("Blew out in setEntries in RepViewer");
/*  95:    */       }
/*  96:    */     }
/*  97:    */     
/*  98:    */     public void paintComponent(Graphics g)
/*  99:    */     {
/* 100:121 */       super.paintComponent(g);
/* 101:    */       
/* 102:123 */       this.width = getWidth();
/* 103:124 */       this.height = getHeight();
/* 104:125 */       this.radius = ((Math.min(this.width, this.height) - 20) / 2);
/* 105:126 */       this.center = new Point(this.width / 2, this.height / 2);
/* 106:127 */       this.dotSize = Math.min(Math.max(this.radius / 8, 6), 12);
/* 107:    */       
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:133 */       plotEntries();
/* 113:135 */       if ((this.points.size() != this.chains.size()) && 
/* 114:136 */         (M2.DEBUG)) {
/* 115:136 */         System.out.println("Broken rep invarient 1 in ChainCircle! Expect wonky behavor.");
/* 116:    */       }
/* 117:139 */       if ((this.labels.size() != this.chains.size()) && 
/* 118:140 */         (M2.DEBUG)) {
/* 119:140 */         System.out.println("Broken rep invarient 2 in ChainCircle! Expect wonky behavor.");
/* 120:    */       }
/* 121:145 */       g.setColor(Color.LIGHT_GRAY);
/* 122:146 */       g.fillOval((int)this.center.getX() - this.radius - 2, (int)this.center.getY() - this.radius - 2, this.radius * 2 + 4, this.radius * 2 + 4);
/* 123:147 */       g.setColor(Color.WHITE);
/* 124:148 */       g.fillOval((int)this.center.getX() - this.radius + 1, (int)this.center.getY() + 1 - this.radius, this.radius * 2 - 2, this.radius * 2 - 2);
/* 125:    */       try
/* 126:    */       {
/* 127:165 */         drawDots();
/* 128:    */       }
/* 129:    */       catch (Exception e)
/* 130:    */       {
/* 131:168 */         System.out.println("Blew out of step 4 in rep viewer");
/* 132:    */       }
/* 133:    */     }
/* 134:    */     
/* 135:    */     private void plotEntries()
/* 136:    */     {
/* 137:174 */       this.points.clear();
/* 138:175 */       double angle = 360.0D / this.chains.size();
/* 139:176 */       for (Chain entry : this.chains)
/* 140:    */       {
/* 141:177 */         double x = this.center.getX() + this.radius * Math.cos(Math.toRadians(angle * this.chains.indexOf(entry)));
/* 142:178 */         double y = this.center.getY() + this.radius * Math.sin(Math.toRadians(angle * this.chains.indexOf(entry)));
/* 143:179 */         int intX = (int)x;
/* 144:180 */         int intY = (int)y;
/* 145:181 */         this.points.add(new Point(intX, intY));
/* 146:    */       }
/* 147:    */     }
/* 148:    */     
/* 149:    */     private void drawDots()
/* 150:    */     {
/* 151:187 */       for (int i = 0; i < this.chains.size(); i++)
/* 152:    */       {
/* 153:188 */         Point p = (Point)this.points.get(i);
/* 154:189 */         DotLabel label = (DotLabel)this.labels.get(i);
/* 155:190 */         int weight = ((Chain)this.chains.get(i)).getWeight();
/* 156:191 */         int size = Math.min(this.dotSize + weight, this.radius);
/* 157:192 */         label.setBounds(p.x - size / 2, p.y - size / 2, size, size);
/* 158:    */       }
/* 159:    */     }
/* 160:    */     
/* 161:    */     private class DotLabel
/* 162:    */       extends JLabel
/* 163:    */       implements MouseListener
/* 164:    */     {
/* 165:    */       private Chain entry;
/* 166:    */       
/* 167:    */       public DotLabel(Chain e)
/* 168:    */       {
/* 169:203 */         this.entry = e;
/* 170:    */       }
/* 171:    */       
/* 172:    */       public void paintComponent(Graphics g)
/* 173:    */       {
/* 174:208 */         int size = getSize().width;
/* 175:    */         
/* 176:210 */         g.setColor(Color.BLUE);
/* 177:211 */         g.fillOval(0, 0, size, size);
/* 178:212 */         g.setColor(Color.BLACK);
/* 179:213 */         g.drawOval(0, 0, size - 1, size - 1);
/* 180:    */       }
/* 181:    */       
/* 182:    */       public void mouseClicked(MouseEvent e) {}
/* 183:    */       
/* 184:    */       public void mouseEntered(MouseEvent e)
/* 185:    */       {
/* 186:221 */         RepViewer.this.output(this.entry);
/* 187:    */       }
/* 188:    */       
/* 189:    */       public void mouseExited(MouseEvent e) {}
/* 190:    */       
/* 191:    */       public void mousePressed(MouseEvent e) {}
/* 192:    */       
/* 193:    */       public void mouseReleased(MouseEvent e) {}
/* 194:    */     }
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.gui.RepViewer
 * JD-Core Version:    0.7.0.1
 */