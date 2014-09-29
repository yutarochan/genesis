/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.InOrderVectorMatcher;
/*   4:    */ import carynKrakauer.KeywordAngles;
/*   5:    */ import carynKrakauer.ReflectionLevelMemory;
/*   6:    */ import carynKrakauer.StudyResults;
/*   7:    */ import carynKrakauer.VectorMatchWrapper;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Font;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.awt.Graphics2D;
/*  12:    */ import java.awt.event.MouseEvent;
/*  13:    */ import java.awt.event.MouseMotionListener;
/*  14:    */ import java.awt.geom.Rectangle2D;
/*  15:    */ import java.text.DecimalFormat;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.Iterator;
/*  19:    */ import java.util.Set;
/*  20:    */ import matthewFay.Utilities.Pair;
/*  21:    */ 
/*  22:    */ public class InOrderGridPanel
/*  23:    */   extends MatrixJPanel
/*  24:    */   implements MouseMotionListener
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   private boolean grayscale;
/*  28:    */   private boolean triangle;
/*  29:    */   KeywordAngles keywordAngles;
/*  30: 30 */   int mouse_x = -1;
/*  31: 31 */   int mouse_y = -1;
/*  32:    */   ReflectionLevelMemory memory;
/*  33:    */   
/*  34:    */   public InOrderGridPanel(ReflectionLevelMemory memory, boolean grayscale, boolean triangle)
/*  35:    */   {
/*  36: 37 */     this.memory = memory;
/*  37: 38 */     addMouseMotionListener(this);
/*  38:    */     
/*  39: 40 */     this.keywordAngles = new KeywordAngles();
/*  40:    */     
/*  41: 42 */     this.grayscale = grayscale;
/*  42: 43 */     this.triangle = triangle;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void paintComponent(Graphics g)
/*  46:    */   {
/*  47: 48 */     super.paintComponent(g);
/*  48:    */     
/*  49: 50 */     setBackground(Color.white);
/*  50:    */     
/*  51: 52 */     g.setFont(new Font("Dialog", 0, 12));
/*  52:    */     
/*  53: 54 */     paintKeywords(g);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private HashMap<String, HashMap<String, Integer>> getRanks()
/*  57:    */   {
/*  58: 58 */     HashMap<String, ArrayList<Pair<Double, String>>> ranks = new HashMap();
/*  59: 59 */     for (int i = 0; i < this.memory.getStoryCount(); i++)
/*  60:    */     {
/*  61: 60 */       String story1 = (String)this.memory.getStoryNames().get(i);
/*  62: 61 */       story1Ranks = new ArrayList();
/*  63: 62 */       ranks.put(story1, story1Ranks);
/*  64: 63 */       for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  65:    */       {
/*  66: 64 */         String story2 = (String)this.memory.getStoryNames().get(j);
/*  67:    */         
/*  68: 66 */         double val = InOrderVectorMatcher.matchVectorsInOrder(story1, story2, this.memory).getValue();
/*  69:    */         
/*  70: 68 */         boolean found = false;
/*  71: 69 */         for (int k = 0; k < story1Ranks.size(); k++) {
/*  72: 70 */           if (((Double)((Pair)story1Ranks.get(k)).a).doubleValue() < val)
/*  73:    */           {
/*  74: 71 */             story1Ranks.add(k, new Pair(Double.valueOf(val), story2));
/*  75: 72 */             found = true;
/*  76: 73 */             break;
/*  77:    */           }
/*  78:    */         }
/*  79: 76 */         if (!found) {
/*  80: 77 */           story1Ranks.add(new Pair(Double.valueOf(val), story2));
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84: 82 */     HashMap<String, HashMap<String, Integer>> outRanks = new HashMap();
/*  85:    */     ArrayList<Pair<Double, String>> ordering;
/*  86:    */     int i;
/*  87: 84 */     for (ArrayList<Pair<Double, String>> story1Ranks = ranks.keySet().iterator(); story1Ranks.hasNext(); i < ordering.size())
/*  88:    */     {
/*  89: 84 */       String story1 = (String)story1Ranks.next();
/*  90: 85 */       outRanks.put(story1, new HashMap());
/*  91: 86 */       ordering = (ArrayList)ranks.get(story1);
/*  92: 87 */       int current_rank = 0;
/*  93: 88 */       double current_val = -1.0D;
/*  94: 89 */       i = 0; continue;
/*  95: 90 */       if (((Double)((Pair)ordering.get(i)).a).doubleValue() != current_val)
/*  96:    */       {
/*  97: 91 */         current_rank = i;
/*  98: 92 */         current_val = ((Double)((Pair)ordering.get(i)).a).doubleValue();
/*  99:    */       }
/* 100: 94 */       ((HashMap)outRanks.get(story1)).put((String)((Pair)ordering.get(i)).b, Integer.valueOf(current_rank));i++;
/* 101:    */     }
/* 102: 98 */     return outRanks;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private double getRankSD()
/* 106:    */   {
/* 107:102 */     HashMap<String, HashMap<String, Integer>> ranks = getRanks();
/* 108:    */     
/* 109:104 */     double total = 0.0D;
/* 110:105 */     int num = 0;
/* 111:106 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 112:    */     {
/* 113:107 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 114:108 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 115:    */       {
/* 116:109 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 117:    */         
/* 118:111 */         int myRank = ((Integer)((HashMap)ranks.get(story1)).get(story2)).intValue();
/* 119:112 */         int studyRank = StudyResults.getRank(story1, story2);
/* 120:    */         
/* 121:114 */         double diff = Math.pow(myRank - studyRank, 2.0D);
/* 122:115 */         total += diff;
/* 123:116 */         num++;
/* 124:    */       }
/* 125:    */     }
/* 126:120 */     return Math.sqrt(total / num);
/* 127:    */   }
/* 128:    */   
/* 129:    */   private double calcStandardDev()
/* 130:    */   {
/* 131:125 */     double total = 0.0D;
/* 132:126 */     int num = 0;
/* 133:127 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 134:    */     {
/* 135:128 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 136:129 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 137:    */       {
/* 138:130 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 139:131 */         int studyResult = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/* 140:132 */         double studyVal = studyResult / 11.0F;
/* 141:133 */         double val = InOrderVectorMatcher.matchVectorsInOrder(story1, story2, this.memory).getValue();
/* 142:134 */         double diff = Math.pow(val - studyVal, 2.0D);
/* 143:135 */         total += diff;
/* 144:136 */         num++;
/* 145:    */       }
/* 146:    */     }
/* 147:139 */     return Math.sqrt(total / num);
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void paintKeywords(Graphics g)
/* 151:    */   {
/* 152:144 */     int width = getWidth();
/* 153:145 */     int height = getHeight();
/* 154:    */     
/* 155:147 */     Font font = new Font("Dialog", 0, 12);
/* 156:    */     
/* 157:149 */     DecimalFormat df = new DecimalFormat("#.##");
/* 158:150 */     g.setColor(Color.black);
/* 159:151 */     double stdDevError = getRankSD();
/* 160:152 */     writeStdDevError(g, df, stdDevError);
/* 161:    */     
/* 162:154 */     Graphics2D g2 = (Graphics2D)g;
/* 163:155 */     int x_start = 0;
/* 164:157 */     for (String name : this.memory.getStoryNames())
/* 165:    */     {
/* 166:158 */       Rectangle2D bounds = font.getStringBounds(name, g2.getFontRenderContext());
/* 167:159 */       x_start = (int)Math.max(x_start, bounds.getWidth());
/* 168:    */     }
/* 169:162 */     int y_start = 20;
/* 170:    */     
/* 171:164 */     int word_width = (int)((width - x_start) / this.memory.getStoryCount());
/* 172:165 */     int word_height = (int)((height - 40) / this.memory.getStoryCount());
/* 173:166 */     int x_pos = x_start;
/* 174:167 */     int y_pos = y_start;
/* 175:    */     
/* 176:169 */     double widestName = this.memory.getWidestNameWidth(font, g2.getFontRenderContext());
/* 177:170 */     if (widestName >= word_width * 2)
/* 178:    */     {
/* 179:171 */       g.setFont(new Font("Dialog", 1, 16));
/* 180:172 */       for (int i = 1; i <= this.memory.getStoryCount(); i++)
/* 181:    */       {
/* 182:173 */         g.drawString(i, (int)(x_pos + word_width * 0.5F), y_pos);
/* 183:174 */         x_pos += word_width;
/* 184:    */       }
/* 185:177 */       g.setFont(new Font("Dialog", 1, 12));
/* 186:178 */       x_pos = 0;
/* 187:179 */       y_pos = y_start + word_height;
/* 188:180 */       int index = 1;
/* 189:181 */       for (String storyName : this.memory.getStoryNames())
/* 190:    */       {
/* 191:182 */         g.drawString(index + ": " + storyName, x_pos, y_pos);
/* 192:183 */         y_pos += word_height;
/* 193:184 */         index++;
/* 194:    */       }
/* 195:    */     }
/* 196:    */     else
/* 197:    */     {
/* 198:188 */       g.setFont(new Font("Dialog", 0, 12));
/* 199:189 */       int index = 0;
/* 200:190 */       for (String storyName : this.memory.getStoryNames())
/* 201:    */       {
/* 202:191 */         g.drawString(storyName, x_pos, y_pos - y_pos / 2 * (index % 2));
/* 203:192 */         x_pos += word_width;
/* 204:193 */         index++;
/* 205:    */       }
/* 206:196 */       x_pos = 0;
/* 207:197 */       y_pos = y_start + word_height;
/* 208:198 */       for (String storyName : this.memory.getStoryNames())
/* 209:    */       {
/* 210:199 */         g.drawString(storyName, x_pos, y_pos);
/* 211:200 */         y_pos += word_height;
/* 212:    */       }
/* 213:    */     }
/* 214:205 */     for (int j = 0; j < this.memory.getStoryCount(); j++) {
/* 215:206 */       for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 216:207 */         if ((!this.triangle) || (i <= j))
/* 217:    */         {
/* 218:211 */           String story1 = (String)this.memory.getStoryNames().get(i);
/* 219:212 */           String story2 = (String)this.memory.getStoryNames().get(j);
/* 220:213 */           double match = InOrderVectorMatcher.matchVectorsInOrder(story1, story2, this.memory).getValue();
/* 221:214 */           if (this.grayscale) {
/* 222:215 */             g.setColor(new Color((int)(255.0D * match), (int)(255.0D * match), (int)(255.0D * match)));
/* 223:218 */           } else if (match == 0.0D) {
/* 224:219 */             g.setColor(Color.black);
/* 225:    */           } else {
/* 226:222 */             g.setColor(new Color(Color.HSBtoRGB(0.4F, 1.0F - (float)match, (float)match)));
/* 227:    */           }
/* 228:227 */           g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 229:    */           
/* 230:229 */           g.setColor(Color.black);
/* 231:230 */           g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 232:    */           
/* 233:232 */           g.setColor(Color.black);
/* 234:233 */           g.drawString(df.format(match), x_start + i * word_width, y_start + j * word_height + word_height);
/* 235:    */         }
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void mouseDragged(MouseEvent event) {}
/* 241:    */   
/* 242:    */   public void mouseMoved(MouseEvent event)
/* 243:    */   {
/* 244:270 */     this.mouse_x = event.getX();
/* 245:271 */     this.mouse_y = event.getY();
/* 246:    */     
/* 247:273 */     repaint();
/* 248:274 */     updateUI();
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.InOrderGridPanel
 * JD-Core Version:    0.7.0.1
 */