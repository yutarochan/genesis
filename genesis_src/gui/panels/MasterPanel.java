/*   1:    */ package gui.panels;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.LayoutManager;
/*   8:    */ import java.beans.PropertyChangeEvent;
/*   9:    */ import java.beans.PropertyChangeListener;
/*  10:    */ import javax.swing.JFrame;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class MasterPanel
/*  15:    */   extends JPanel
/*  16:    */   implements PropertyChangeListener
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   JPanel tickledComponent;
/*  20:    */   int tickledSize;
/*  21:    */   
/*  22:    */   public MasterPanel()
/*  23:    */   {
/*  24: 26 */     setLayout(new MyLayoutManager());
/*  25: 27 */     setBackground(Color.CYAN);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void addStandardPanel(StandardPanel p)
/*  29:    */   {
/*  30: 31 */     super.add(p);
/*  31: 32 */     p.addPropertyChangeListener(this);
/*  32: 33 */     setSizesEqual();
/*  33: 34 */     adjustToHundredPercent();
/*  34:    */   }
/*  35:    */   
/*  36:    */   class MyLayoutManager
/*  37:    */     implements LayoutManager
/*  38:    */   {
/*  39:    */     MyLayoutManager() {}
/*  40:    */     
/*  41:    */     public void addLayoutComponent(String arg0, Component arg1) {}
/*  42:    */     
/*  43:    */     public void layoutContainer(Container arg0)
/*  44:    */     {
/*  45: 46 */       int count = MasterPanel.this.getComponents().length;
/*  46: 47 */       int accumulated = 0;
/*  47: 48 */       int reservedForButtonBars = StandardPanel.buttonPanelHeight * count;
/*  48: 49 */       int adjustedHeight = MasterPanel.this.getHeight() - reservedForButtonBars;
/*  49: 50 */       for (int i = 0; i < count; i++)
/*  50:    */       {
/*  51: 51 */         StandardPanel p = (StandardPanel)MasterPanel.this.getComponents()[i];
/*  52: 52 */         int thisHeight = StandardPanel.buttonPanelHeight + p.getHeightPercent() * adjustedHeight / 100;
/*  53: 53 */         if (i == count - 1) {
/*  54: 54 */           p.setBounds(0, accumulated, MasterPanel.this.getWidth(), MasterPanel.this.getHeight() - accumulated);
/*  55:    */         } else {
/*  56: 57 */           p.setBounds(0, accumulated, MasterPanel.this.getWidth(), thisHeight);
/*  57:    */         }
/*  58: 59 */         accumulated += thisHeight;
/*  59:    */       }
/*  60:    */     }
/*  61:    */     
/*  62:    */     public Dimension minimumLayoutSize(Container arg0)
/*  63:    */     {
/*  64: 65 */       return null;
/*  65:    */     }
/*  66:    */     
/*  67:    */     public Dimension preferredLayoutSize(Container arg0)
/*  68:    */     {
/*  69: 70 */       return null;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public void removeLayoutComponent(Component arg0) {}
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void propertyChange(PropertyChangeEvent e)
/*  76:    */   {
/*  77: 81 */     if ("add".equals(e.getPropertyName()))
/*  78:    */     {
/*  79: 82 */       Mark.say(new Object[] {"Hello world" });
/*  80: 83 */       addStandardPanel(new StandardPanel());
/*  81:    */       
/*  82: 85 */       setSizesEqual();
/*  83: 86 */       adjustToHundredPercent();
/*  84: 87 */       return;
/*  85:    */     }
/*  86: 89 */     if ("remove".equals(e.getPropertyName()))
/*  87:    */     {
/*  88: 90 */       remove((Component)e.getSource());
/*  89: 91 */       setSizesEqual();
/*  90: 92 */       adjustToHundredPercent();
/*  91: 93 */       return;
/*  92:    */     }
/*  93: 95 */     if ("equal".equals(e.getPropertyName()))
/*  94:    */     {
/*  95: 96 */       setSizesEqual();
/*  96: 97 */       adjustToHundredPercent();
/*  97: 98 */       return;
/*  98:    */     }
/*  99:100 */     if (!"height".equals(e.getPropertyName())) {
/* 100:101 */       return;
/* 101:    */     }
/* 102:103 */     this.tickledComponent = ((StandardPanel)e.getSource());
/* 103:104 */     this.tickledSize = ((Integer)e.getNewValue()).intValue();
/* 104:105 */     int unadjustedSum = 0;
/* 105:106 */     for (Component c : getComponents())
/* 106:    */     {
/* 107:107 */       StandardPanel p = (StandardPanel)c;
/* 108:108 */       if (p != this.tickledComponent) {
/* 109:111 */         unadjustedSum += p.getHeightPercent();
/* 110:    */       }
/* 111:    */     }
/* 112:    */     StandardPanel p;
/* 113:114 */     if (unadjustedSum == 0) {
/* 114:115 */       for (Component c : getComponents())
/* 115:    */       {
/* 116:116 */         p = (StandardPanel)c;
/* 117:117 */         if (p != this.tickledComponent)
/* 118:    */         {
/* 119:120 */           p.setHeightPercent(1);
/* 120:121 */           unadjustedSum++;
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:125 */     int remainder = 100 - this.tickledSize;
/* 125:126 */     for (Component c : getComponents())
/* 126:    */     {
/* 127:127 */       StandardPanel p = (StandardPanel)c;
/* 128:128 */       if (p != this.tickledComponent)
/* 129:    */       {
/* 130:131 */         int newPercent = p.getHeightPercent() * remainder / unadjustedSum;
/* 131:132 */         p.setHeightPercent(newPercent);
/* 132:    */       }
/* 133:    */     }
/* 134:135 */     adjustToHundredPercent();
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void adjustToHundredPercent()
/* 138:    */   {
/* 139:139 */     int total = 0;
/* 140:    */     StandardPanel p;
/* 141:140 */     for (Component c : getComponents())
/* 142:    */     {
/* 143:141 */       p = (StandardPanel)c;
/* 144:142 */       total += p.getHeightPercent();
/* 145:    */     }
/* 146:144 */     int delta = 100 - total;
/* 147:145 */     if (delta != 0)
/* 148:    */     {
/* 149:146 */       StandardPanel tallestPanel = null;
/* 150:147 */       int tallestHeight = -1;
/* 151:    */       Component[] arrayOfComponent2;
/* 152:148 */       StandardPanel localStandardPanel1 = (arrayOfComponent2 = getComponents()).length;
/* 153:148 */       for (p = 0; p < localStandardPanel1; p++)
/* 154:    */       {
/* 155:148 */         Component c = arrayOfComponent2[p];
/* 156:149 */         StandardPanel p = (StandardPanel)c;
/* 157:150 */         if (p.getHeightPercent() > tallestHeight)
/* 158:    */         {
/* 159:151 */           tallestPanel = p;
/* 160:152 */           tallestHeight = p.getHeightPercent();
/* 161:    */         }
/* 162:    */       }
/* 163:155 */       tallestPanel.setHeightPercent(tallestPanel.getHeightPercent() + delta);
/* 164:    */     }
/* 165:157 */     revalidate();
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void setSizesEqual()
/* 169:    */   {
/* 170:161 */     int equalPercent = 100 / getComponents().length;
/* 171:162 */     for (int i = 0; i < getComponents().length; i++)
/* 172:    */     {
/* 173:163 */       StandardPanel p = (StandardPanel)getComponents()[i];
/* 174:164 */       p.setHeightPercent(equalPercent);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   private Dimension transformHeight(int height, Dimension input)
/* 179:    */   {
/* 180:169 */     return new Dimension(input.width, height);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static void main(String[] args)
/* 184:    */   {
/* 185:173 */     MasterPanel panel = new MasterPanel();
/* 186:    */     
/* 187:175 */     JFrame frame = new JFrame();
/* 188:176 */     frame.setContentPane(panel);
/* 189:177 */     frame.setBounds(0, 0, 500, 400);
/* 190:178 */     frame.setVisible(true);
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.panels.MasterPanel
 * JD-Core Version:    0.7.0.1
 */