/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.InOrderVectorMatcher;
/*   4:    */ import carynKrakauer.ReflectionLevelMemory;
/*   5:    */ import carynKrakauer.SimilarityProcessor;
/*   6:    */ import carynKrakauer.VectorMatchWrapper;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Font;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.GridLayout;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import javax.swing.JLabel;
/*  13:    */ import javax.swing.JPanel;
/*  14:    */ import storyProcessor.ReflectionDescription;
/*  15:    */ import utils.Mark;
/*  16:    */ 
/*  17:    */ public class InOrderMatchesVisualPanel
/*  18:    */   extends JPanel
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21: 24 */   int numRows = 5;
/*  22: 25 */   int highlight_height = 16;
/*  23:    */   SimilarityProcessor similarityProcessor;
/*  24: 29 */   boolean highlight_needed = false;
/*  25:    */   ArrayList<ReflectionDescription> s1;
/*  26:    */   ArrayList<ReflectionDescription> s2;
/*  27:    */   VectorMatchWrapper matches;
/*  28:    */   
/*  29:    */   public InOrderMatchesVisualPanel(SimilarityProcessor similarityProcessor)
/*  30:    */   {
/*  31: 36 */     this.similarityProcessor = similarityProcessor;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void visualizeMatches(String story1, String story2)
/*  35:    */   {
/*  36: 42 */     removeAll();
/*  37:    */     
/*  38: 44 */     this.s1 = this.similarityProcessor.getMemory().getStoryReflectionDescriptions(story1);
/*  39: 45 */     this.s2 = this.similarityProcessor.getMemory().getStoryReflectionDescriptions(story2);
/*  40: 46 */     this.matches = InOrderVectorMatcher.matchVectorsInOrder(story1, story2, this.similarityProcessor.getMemory());
/*  41:    */     
/*  42: 48 */     GridLayout layout = new GridLayout(this.numRows, Math.max(this.s1.size(), this.s2.size()));
/*  43: 49 */     setLayout(layout);
/*  44:    */     
/*  45:    */ 
/*  46: 52 */     JLabel label0 = new JLabel("In Order Matches:");
/*  47: 53 */     label0.setFont(new Font("SansSerif", 1, 16));
/*  48: 54 */     add(label0);
/*  49: 55 */     add(new JLabel("Value: " + this.matches.getValue()));
/*  50: 57 */     for (int i = 2; i < Math.max(this.s1.size(), this.s2.size()); i++) {
/*  51: 58 */       add(new JLabel());
/*  52:    */     }
/*  53: 63 */     JLabel label1 = new JLabel(story1);
/*  54: 64 */     label1.setFont(new Font("SansSerif", 0, 14));
/*  55: 65 */     add(label1);
/*  56: 67 */     for (int i = 1; i < Math.max(this.s1.size(), this.s2.size()); i++) {
/*  57: 68 */       add(new JLabel());
/*  58:    */     }
/*  59: 71 */     for (int i = 0; i < this.s1.size(); i++)
/*  60:    */     {
/*  61: 73 */       ReflectionDescription desc = (ReflectionDescription)this.s1.get(i);
/*  62: 74 */       add(new JLabel(desc.getName(), 0));
/*  63:    */     }
/*  64: 77 */     for (; i < Math.max(this.s1.size(), this.s2.size()); i++) {
/*  65: 78 */       add(new JLabel());
/*  66:    */     }
/*  67: 83 */     JLabel label2 = new JLabel(story2);
/*  68: 84 */     label2.setFont(new Font("SansSerif", 0, 14));
/*  69: 85 */     add(label2);
/*  70: 87 */     for (i = 1; i < Math.max(this.s1.size(), this.s2.size()); i++) {
/*  71: 88 */       add(new JLabel());
/*  72:    */     }
/*  73: 91 */     for (i = 0; i < this.s2.size(); i++)
/*  74:    */     {
/*  75: 93 */       ReflectionDescription desc = (ReflectionDescription)this.s2.get(i);
/*  76: 94 */       add(new JLabel(desc.getName(), 0));
/*  77:    */     }
/*  78: 97 */     for (; i < Math.max(this.s1.size(), this.s2.size()); i++) {
/*  79: 98 */       add(new JLabel());
/*  80:    */     }
/*  81:101 */     int start_x = 0;
/*  82:102 */     int start_y = getHeight() / this.numRows;
/*  83:    */     
/*  84:104 */     this.highlight_needed = true;
/*  85:    */     
/*  86:106 */     repaint();
/*  87:107 */     updateUI();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void paintComponent(Graphics g)
/*  91:    */   {
/*  92:113 */     super.paintComponent(g);
/*  93:115 */     if (this.highlight_needed) {
/*  94:116 */       paintHighlights(g);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void paintHighlights(Graphics g)
/*  99:    */   {
/* 100:120 */     g.setColor(Color.GREEN);
/* 101:    */     
/* 102:    */ 
/* 103:123 */     int match_index = 0;
/* 104:124 */     boolean match_started = false;
/* 105:125 */     int start_x = 0;
/* 106:126 */     int start_y = 0;
/* 107:127 */     int end_x = 0;
/* 108:128 */     Mark.say(new Object[] {"MATCHES: " + this.matches.getMatches() });
/* 109:129 */     for (int i = 0; i < this.s1.size(); i++) {
/* 110:130 */       if ((this.matches.getMatches().size() > match_index) && (((ReflectionDescription)this.s1.get(i)).getName().equals(this.matches.getMatches().get(match_index))))
/* 111:    */       {
/* 112:131 */         if (!match_started)
/* 113:    */         {
/* 114:132 */           start_x = getWidth() / Math.max(this.s1.size(), this.s2.size()) * i;
/* 115:133 */           start_y = getHeight() / this.numRows * 2 + (getHeight() / this.numRows / 2 - this.highlight_height / 2);
/* 116:134 */           end_x = start_x + getWidth() / Math.max(this.s1.size(), this.s2.size());
/* 117:135 */           match_started = true;
/* 118:    */         }
/* 119:    */         else
/* 120:    */         {
/* 121:138 */           end_x += getWidth() / Math.max(this.s1.size(), this.s2.size());
/* 122:    */         }
/* 123:141 */         match_index++;
/* 124:    */       }
/* 125:144 */       else if (match_started)
/* 126:    */       {
/* 127:145 */         g.fillRoundRect(start_x, start_y, end_x - start_x, this.highlight_height, this.highlight_height / 3, this.highlight_height / 3);
/* 128:146 */         match_started = false;
/* 129:    */       }
/* 130:    */     }
/* 131:150 */     if (match_started)
/* 132:    */     {
/* 133:151 */       g.fillRoundRect(start_x, start_y, end_x - start_x, this.highlight_height, this.highlight_height / 3, this.highlight_height / 3);
/* 134:152 */       match_started = false;
/* 135:    */     }
/* 136:156 */     match_index = 0;
/* 137:157 */     match_started = false;
/* 138:158 */     start_x = 0;
/* 139:159 */     start_y = 0;
/* 140:160 */     end_x = 0;
/* 141:161 */     for (int i = 0; i < this.s2.size(); i++) {
/* 142:162 */       if ((this.matches.getMatches().size() > match_index) && (((ReflectionDescription)this.s2.get(i)).getName().equals(this.matches.getMatches().get(match_index))))
/* 143:    */       {
/* 144:163 */         if (!match_started)
/* 145:    */         {
/* 146:164 */           start_x = getWidth() / Math.max(this.s1.size(), this.s2.size()) * i;
/* 147:165 */           start_y = getHeight() / this.numRows * 4 + (getHeight() / this.numRows / 2 - this.highlight_height / 2);
/* 148:166 */           end_x = start_x + getWidth() / Math.max(this.s1.size(), this.s2.size());
/* 149:167 */           match_started = true;
/* 150:    */         }
/* 151:    */         else
/* 152:    */         {
/* 153:170 */           end_x += getWidth() / Math.max(this.s1.size(), this.s2.size());
/* 154:    */         }
/* 155:173 */         match_index++;
/* 156:    */       }
/* 157:176 */       else if (match_started)
/* 158:    */       {
/* 159:177 */         g.fillRoundRect(start_x, start_y, end_x - start_x, this.highlight_height, this.highlight_height / 3, this.highlight_height / 3);
/* 160:178 */         match_started = false;
/* 161:    */       }
/* 162:    */     }
/* 163:182 */     if (match_started)
/* 164:    */     {
/* 165:183 */       g.fillRoundRect(start_x, start_y, end_x - start_x, this.highlight_height, this.highlight_height / 3, this.highlight_height / 3);
/* 166:184 */       match_started = false;
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.InOrderMatchesVisualPanel
 * JD-Core Version:    0.7.0.1
 */