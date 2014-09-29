/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.ReflectionLevelMemory;
/*   4:    */ import carynKrakauer.SimilarityProcessor;
/*   5:    */ import carynKrakauer.VectorMatchWrapper;
/*   6:    */ import carynKrakauer.VectorMatcher;
/*   7:    */ import java.awt.BorderLayout;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.swing.JLabel;
/*  12:    */ import javax.swing.JPanel;
/*  13:    */ import storyProcessor.ReflectionDescription;
/*  14:    */ 
/*  15:    */ public class FeatureMatchesVisualPanel
/*  16:    */   extends JPanel
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   SimilarityProcessor similarityProcessor;
/*  20:    */   String story1;
/*  21:    */   String story2;
/*  22:    */   ArrayList<String> s1;
/*  23:    */   ArrayList<String> s2;
/*  24:    */   VectorMatchWrapper matches;
/*  25: 31 */   boolean compared = false;
/*  26:    */   
/*  27:    */   public FeatureMatchesVisualPanel(SimilarityProcessor similarityProcessor)
/*  28:    */   {
/*  29: 34 */     this.similarityProcessor = similarityProcessor;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void visualizeMatches(String story1, String story2)
/*  33:    */   {
/*  34: 39 */     removeAll();
/*  35:    */     
/*  36: 41 */     this.story1 = story1;
/*  37: 42 */     this.story2 = story2;
/*  38:    */     
/*  39: 44 */     this.s1 = new ArrayList();
/*  40: 45 */     for (ReflectionDescription rd : this.similarityProcessor.getMemory().getStoryReflectionDescriptions(story1)) {
/*  41: 46 */       this.s1.add(rd.getName());
/*  42:    */     }
/*  43: 48 */     this.s2 = new ArrayList();
/*  44: 49 */     for (ReflectionDescription rd : this.similarityProcessor.getMemory().getStoryReflectionDescriptions(story2)) {
/*  45: 50 */       this.s2.add(rd.getName());
/*  46:    */     }
/*  47: 52 */     this.matches = VectorMatcher.match_story_vectors(story1, story2, this.similarityProcessor.getMemory());
/*  48:    */     
/*  49: 54 */     setLayout(new BorderLayout());
/*  50:    */     
/*  51: 56 */     add(new JLabel("Total value: " + this.matches.getValue()), "First");
/*  52:    */     
/*  53: 58 */     this.compared = true;
/*  54:    */     
/*  55: 60 */     repaint();
/*  56: 61 */     updateUI();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void paintComponent(Graphics g)
/*  60:    */   {
/*  61: 67 */     super.paintComponent(g);
/*  62:    */     
/*  63: 69 */     int top = getHeight() / 6;
/*  64: 70 */     int left = getWidth() / 9;
/*  65:    */     
/*  66: 72 */     int width = getWidth() * 4 / 6;
/*  67: 73 */     int height = getHeight() * 7 / 9;
/*  68:    */     
/*  69:    */ 
/*  70: 76 */     g.setColor(Color.white);
/*  71: 77 */     g.fillRect(left, top, width, height);
/*  72:    */     
/*  73:    */ 
/*  74: 80 */     g.setColor(Color.black);
/*  75: 81 */     g.drawLine(left, top, left, top + height);
/*  76: 82 */     g.drawLine(left, top + height, left + width, top + height);
/*  77: 84 */     if (!this.compared) {
/*  78: 85 */       return;
/*  79:    */     }
/*  80: 88 */     ArrayList<String> features = new ArrayList();
/*  81: 89 */     for (String s : this.s1) {
/*  82: 90 */       if (!features.contains(s)) {
/*  83: 91 */         features.add(s);
/*  84:    */       }
/*  85:    */     }
/*  86: 94 */     for (String s : this.s2) {
/*  87: 95 */       if (!features.contains(s)) {
/*  88: 96 */         features.add(s);
/*  89:    */       }
/*  90:    */     }
/*  91:101 */     int feature_width = width / features.size();
/*  92:102 */     int start_x = left;
/*  93:103 */     int feature_y = top + height + 14;
/*  94:104 */     for (int i = 0; i < features.size(); i++) {
/*  95:105 */       g.drawString((String)features.get(i), start_x + feature_width * i, feature_y);
/*  96:    */     }
/*  97:109 */     ArrayList<Integer> first_rect = new ArrayList();
/*  98:110 */     ArrayList<Integer> second_rect = new ArrayList();
/*  99:111 */     for (int i = 0; i < features.size(); i++)
/* 100:    */     {
/* 101:112 */       first_rect.add(Integer.valueOf(0));
/* 102:113 */       second_rect.add(Integer.valueOf(0));
/* 103:    */     }
/* 104:116 */     for (String s : this.s1)
/* 105:    */     {
/* 106:117 */       int i = ((Integer)first_rect.get(features.indexOf(s))).intValue();
/* 107:118 */       i++;
/* 108:119 */       first_rect.set(features.indexOf(s), Integer.valueOf(i));
/* 109:    */     }
/* 110:122 */     for (String s : this.s2)
/* 111:    */     {
/* 112:123 */       int i = ((Integer)second_rect.get(features.indexOf(s))).intValue();
/* 113:124 */       i++;
/* 114:125 */       second_rect.set(features.indexOf(s), Integer.valueOf(i));
/* 115:    */     }
/* 116:129 */     int max = 0;
/* 117:130 */     for (int i = 0; i < features.size(); i++)
/* 118:    */     {
/* 119:131 */       if (((Integer)first_rect.get(i)).intValue() > max) {
/* 120:132 */         max = ((Integer)first_rect.get(i)).intValue();
/* 121:    */       }
/* 122:134 */       if (((Integer)second_rect.get(i)).intValue() > max) {
/* 123:135 */         max = ((Integer)second_rect.get(i)).intValue();
/* 124:    */       }
/* 125:    */     }
/* 126:139 */     int part_height = height / max;
/* 127:    */     
/* 128:141 */     g.setColor(Color.green);
/* 129:143 */     for (int i = 0; i < features.size(); i++) {
/* 130:144 */       g.fillRect(start_x + i * feature_width, top + height - part_height * ((Integer)second_rect.get(i)).intValue(), 
/* 131:145 */         feature_width, part_height * ((Integer)second_rect.get(i)).intValue());
/* 132:    */     }
/* 133:148 */     g.setColor(Color.black);
/* 134:150 */     for (int i = 0; i < features.size(); i++) {
/* 135:151 */       g.drawRect(start_x + i * feature_width, top + height - part_height * ((Integer)first_rect.get(i)).intValue(), 
/* 136:152 */         feature_width, part_height * ((Integer)first_rect.get(i)).intValue());
/* 137:    */     }
/* 138:156 */     for (int i = 0; i < max; i++)
/* 139:    */     {
/* 140:157 */       g.drawString(i, start_x - 20, top + height - part_height * i);
/* 141:158 */       g.drawLine(start_x, top + height - part_height * i, start_x + 5, top + height - part_height * i);
/* 142:    */     }
/* 143:162 */     g.setColor(Color.black);
/* 144:163 */     int padding = 10;
/* 145:164 */     int key_dim = 50;
/* 146:165 */     g.drawRect(left + width + padding, top, key_dim, key_dim);
/* 147:166 */     g.drawString(" = " + this.story1, left + width + padding + key_dim + padding, top + 20);
/* 148:    */     
/* 149:168 */     g.setColor(Color.green);
/* 150:169 */     g.fillRect(left + width + padding, top + padding + key_dim, key_dim, key_dim);
/* 151:170 */     g.setColor(Color.black);
/* 152:171 */     g.drawString(" = " + this.story2, left + width + padding + key_dim + padding, top + padding + key_dim + 20);
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.FeatureMatchesVisualPanel
 * JD-Core Version:    0.7.0.1
 */