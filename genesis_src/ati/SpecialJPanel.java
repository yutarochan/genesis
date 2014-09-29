/*   1:    */ package ati;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.LayoutManager;
/*   6:    */ import javax.swing.JPanel;
/*   7:    */ 
/*   8:    */ public class SpecialJPanel
/*   9:    */   extends JPanel
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 4442656082338946934L;
/*  12: 22 */   private static boolean showAdvice = false;
/*  13: 24 */   private boolean hasAdvice = false;
/*  14: 26 */   public static boolean showNames = false;
/*  15:    */   protected TitledJPanelAdvice advicePanel;
/*  16:    */   private String advice;
/*  17:    */   
/*  18:    */   public SpecialJPanel() {}
/*  19:    */   
/*  20:    */   public SpecialJPanel(boolean isDoubleBuffered)
/*  21:    */   {
/*  22: 37 */     super(isDoubleBuffered);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public SpecialJPanel(LayoutManager layout, boolean isDoubleBuffered)
/*  26:    */   {
/*  27: 41 */     super(layout, isDoubleBuffered);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public SpecialJPanel(LayoutManager layout)
/*  31:    */   {
/*  32: 45 */     super(layout);
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected Dimension getMySize(Component component)
/*  36:    */   {
/*  37: 49 */     Dimension p = component.getPreferredSize();
/*  38: 50 */     if (!(component instanceof SpecialJPanel))
/*  39:    */     {
/*  40: 51 */       Dimension m = component.getMinimumSize();
/*  41: 52 */       if (m != null) {
/*  42: 53 */         return m;
/*  43:    */       }
/*  44:    */     }
/*  45: 56 */     if (p != null) {
/*  46: 57 */       return p;
/*  47:    */     }
/*  48: 59 */     return new Dimension(100, 100);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setAdvice(String advice)
/*  52:    */   {
/*  53: 63 */     if ((advice == null) || ("".equals(advice.trim()))) {
/*  54: 64 */       this.hasAdvice = false;
/*  55:    */     } else {
/*  56: 67 */       this.hasAdvice = true;
/*  57:    */     }
/*  58: 69 */     this.advice = advice;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public TitledJPanelAdvice getAdvicePanel()
/*  62:    */   {
/*  63: 73 */     if (this.advicePanel == null) {
/*  64: 74 */       this.advicePanel = new TitledJPanelAdvice();
/*  65:    */     }
/*  66: 76 */     String s = "";
/*  67: 77 */     if (showNames)
/*  68:    */     {
/*  69: 78 */       s = s + "<b><font color = \"red\">";
/*  70: 79 */       if (getName() != null) {
/*  71: 80 */         s = s + "I am ";
/*  72:    */       } else {
/*  73: 83 */         s = s + "My class is ";
/*  74:    */       }
/*  75: 85 */       s = s + getAName(this);
/*  76: 86 */       s = s + ".</font><b>";
/*  77: 87 */       String subNames = getComponentNames(this, 1);
/*  78: 88 */       if (!subNames.equals(""))
/*  79:    */       {
/*  80: 89 */         s = s + "  I contain " + subNames;
/*  81: 90 */         s = s + ". ";
/*  82:    */       }
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86: 95 */       s = s + getAdvice();
/*  87:    */     }
/*  88: 97 */     this.advicePanel.setText(s);
/*  89: 98 */     return this.advicePanel;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private String getAdvice()
/*  93:    */   {
/*  94:102 */     return this.advice;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private String getAName(JPanel component)
/*  98:    */   {
/*  99:106 */     String name = component.getName();
/* 100:107 */     String result = "";
/* 101:108 */     if (name != null) {
/* 102:109 */       result = result + name;
/* 103:    */     } else {
/* 104:112 */       result = result + component.getClass().getName();
/* 105:    */     }
/* 106:114 */     return result;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private String getComponentNames(JPanel component, int level)
/* 110:    */   {
/* 111:118 */     String result = "";
/* 112:119 */     Component[] components = component.getComponents();
/* 113:120 */     for (int i = 0; i < components.length; i++)
/* 114:    */     {
/* 115:122 */       String name = components[i].getName();
/* 116:123 */       if (name != null) {
/* 117:124 */         result = result + " (" + name + " at level " + level + ")";
/* 118:    */       }
/* 119:129 */       if ((components[i] instanceof JPanel))
/* 120:    */       {
/* 121:130 */         JPanel panel = (JPanel)components[i];
/* 122:131 */         result = result + getComponentNames(panel, level + 1);
/* 123:    */       }
/* 124:    */     }
/* 125:134 */     return result;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static boolean isShowAdvice()
/* 129:    */   {
/* 130:138 */     return showAdvice;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static boolean isShowNames()
/* 134:    */   {
/* 135:142 */     return showNames;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean hasAdvice()
/* 139:    */   {
/* 140:146 */     return this.hasAdvice;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static void toggleShowNames()
/* 144:    */   {
/* 145:150 */     showNames = !showNames;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void setShowNames(boolean showName)
/* 149:    */   {
/* 150:154 */     showNames = showName;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static void setShowAdvice(boolean b)
/* 154:    */   {
/* 155:158 */     showAdvice = b;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.SpecialJPanel
 * JD-Core Version:    0.7.0.1
 */