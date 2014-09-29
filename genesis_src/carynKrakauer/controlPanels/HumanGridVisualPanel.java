/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.ReflectionLevelMemory;
/*   4:    */ import carynKrakauer.StudyResults;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Font;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Graphics2D;
/*   9:    */ import java.awt.geom.Rectangle2D;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import javax.swing.JPanel;
/*  13:    */ 
/*  14:    */ public class HumanGridVisualPanel
/*  15:    */   extends JPanel
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   private boolean grayscale;
/*  19:    */   private boolean triangle;
/*  20:    */   private ReflectionLevelMemory memory;
/*  21:    */   
/*  22:    */   public HumanGridVisualPanel(ReflectionLevelMemory memory, boolean grayscale, boolean triangle)
/*  23:    */   {
/*  24: 28 */     this.memory = memory;
/*  25:    */     
/*  26: 30 */     this.grayscale = grayscale;
/*  27: 31 */     this.triangle = triangle;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void drawPlotComparisons(int size)
/*  31:    */   {
/*  32: 35 */     removeAll();
/*  33:    */     
/*  34: 37 */     repaint();
/*  35: 38 */     updateUI();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void paint(Graphics g)
/*  39:    */   {
/*  40: 43 */     super.paintComponent(g);
/*  41:    */     
/*  42: 45 */     setBackground(Color.white);
/*  43:    */     
/*  44:    */ 
/*  45: 48 */     paintComparisons(g);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void paintComparisons(Graphics g)
/*  49:    */   {
/*  50: 52 */     int width = getWidth();
/*  51: 53 */     int height = getHeight();
/*  52:    */     
/*  53: 55 */     Font font = new Font("Dialog", 0, 12);
/*  54: 56 */     Graphics2D g2 = (Graphics2D)g;
/*  55: 57 */     int x_start = 0;
/*  56: 59 */     for (String name : this.memory.getStoryNames())
/*  57:    */     {
/*  58: 60 */       Rectangle2D bounds = font.getStringBounds(name, g2.getFontRenderContext());
/*  59: 61 */       x_start = (int)Math.max(x_start, bounds.getWidth());
/*  60:    */     }
/*  61: 64 */     int y_start = 20;
/*  62: 65 */     int word_width = (int)((width - x_start) / this.memory.getStoryCount());
/*  63: 66 */     int word_height = (int)((height - 40) / this.memory.getStoryCount());
/*  64: 67 */     int x_pos = x_start;
/*  65: 68 */     int y_pos = y_start;
/*  66:    */     
/*  67: 70 */     double widestName = this.memory.getWidestNameWidth(font, g2.getFontRenderContext());
/*  68: 71 */     if (widestName >= word_width * 2)
/*  69:    */     {
/*  70: 72 */       g.setFont(new Font("Dialog", 1, 16));
/*  71: 73 */       for (int i = 1; i <= this.memory.getStoryCount(); i++)
/*  72:    */       {
/*  73: 74 */         g.drawString(i, (int)(x_pos + word_width * 0.5F), y_pos);
/*  74: 75 */         x_pos += word_width;
/*  75:    */       }
/*  76: 78 */       g.setFont(new Font("Dialog", 1, 12));
/*  77: 79 */       x_pos = 0;
/*  78: 80 */       y_pos = y_start + word_height;
/*  79: 81 */       int index = 1;
/*  80: 82 */       for (String storyName : this.memory.getStoryNames())
/*  81:    */       {
/*  82: 83 */         g.drawString(index + ": " + storyName, x_pos, y_pos);
/*  83: 84 */         y_pos += word_height;
/*  84: 85 */         index++;
/*  85:    */       }
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89: 89 */       g.setFont(new Font("Dialog", 0, 12));
/*  90: 90 */       int index = 0;
/*  91: 91 */       for (String storyName : this.memory.getStoryNames())
/*  92:    */       {
/*  93: 92 */         g.drawString(storyName, x_pos, y_pos - y_pos / 2 * (index % 2));
/*  94: 93 */         x_pos += word_width;
/*  95: 94 */         index++;
/*  96:    */       }
/*  97: 97 */       x_pos = 0;
/*  98: 98 */       y_pos = y_start + word_height;
/*  99: 99 */       for (String storyName : this.memory.getStoryNames())
/* 100:    */       {
/* 101:100 */         g.drawString(storyName, x_pos, y_pos);
/* 102:101 */         y_pos += word_height;
/* 103:    */       }
/* 104:    */     }
/* 105:105 */     int maxMatch = -1;
/* 106:106 */     for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 107:107 */       for (int j = i + 1; j < this.memory.getStoryCount(); j++)
/* 108:    */       {
/* 109:108 */         String story1 = (String)this.memory.getStoryNames().get(i);
/* 110:109 */         String story2 = (String)this.memory.getStoryNames().get(j);
/* 111:    */         
/* 112:111 */         int matchSize = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/* 113:112 */         if (matchSize > maxMatch) {
/* 114:113 */           maxMatch = matchSize;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:118 */     if (maxMatch == -1)
/* 119:    */     {
/* 120:119 */       for (int i = 0; i < this.memory.getStoryCount(); i++)
/* 121:    */       {
/* 122:120 */         if (this.grayscale) {
/* 123:121 */           g.setColor(Color.white);
/* 124:    */         } else {
/* 125:124 */           g.setColor(Color.white);
/* 126:    */         }
/* 127:126 */         g.fillRect(x_start + i * word_width, y_start + i * word_height, word_width, word_height);
/* 128:    */         
/* 129:128 */         g.setColor(Color.black);
/* 130:129 */         g.drawRect(x_start + i * word_width, y_start + i * word_height, word_width, word_height);
/* 131:    */       }
/* 132:131 */       return;
/* 133:    */     }
/* 134:135 */     for (int j = 0; j < this.memory.getStoryCount(); j++) {
/* 135:136 */       for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 136:137 */         if ((!this.triangle) || (i <= j)) {
/* 137:141 */           if (i == j)
/* 138:    */           {
/* 139:142 */             if (this.grayscale) {
/* 140:143 */               g.setColor(Color.white);
/* 141:    */             } else {
/* 142:146 */               g.setColor(Color.white);
/* 143:    */             }
/* 144:148 */             g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 145:    */             
/* 146:150 */             g.setColor(Color.black);
/* 147:151 */             g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 148:    */           }
/* 149:    */           else
/* 150:    */           {
/* 151:155 */             String story1 = (String)this.memory.getStoryNames().get(i);
/* 152:156 */             String story2 = (String)this.memory.getStoryNames().get(j);
/* 153:157 */             int matchSize = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/* 154:    */             
/* 155:159 */             double match = matchSize / maxMatch;
/* 156:161 */             if (this.grayscale) {
/* 157:162 */               g.setColor(new Color((int)(255.0D * match), (int)(255.0D * match), (int)(255.0D * match)));
/* 158:165 */             } else if (match == 0.0D) {
/* 159:166 */               g.setColor(Color.black);
/* 160:    */             } else {
/* 161:169 */               g.setColor(new Color(Color.HSBtoRGB(0.4F, 1.0F - (float)match, (float)match)));
/* 162:    */             }
/* 163:173 */             g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 164:    */             
/* 165:175 */             g.setColor(Color.black);
/* 166:176 */             g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 167:178 */             if (this.grayscale) {
/* 168:179 */               g.setColor(Color.GREEN);
/* 169:    */             } else {
/* 170:182 */               g.setColor(Color.white);
/* 171:    */             }
/* 172:186 */             g.setFont(new Font("Dialog", 0, 12));
/* 173:187 */             if (matchSize != 0) {
/* 174:188 */               g.drawString(matchSize, x_start + i * word_width, y_start + j * word_height + word_height);
/* 175:    */             }
/* 176:    */           }
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void update(Integer size)
/* 183:    */   {
/* 184:195 */     drawPlotComparisons(size.intValue());
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.HumanGridVisualPanel
 * JD-Core Version:    0.7.0.1
 */