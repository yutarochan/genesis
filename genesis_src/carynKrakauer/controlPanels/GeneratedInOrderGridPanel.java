/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.InOrderVectorMatcher;
/*   4:    */ import carynKrakauer.KeywordAngles;
/*   5:    */ import carynKrakauer.ReflectionLevelMemory;
/*   6:    */ import carynKrakauer.StudyResults;
/*   7:    */ import carynKrakauer.VectorMatchWrapper;
/*   8:    */ import carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper;
/*   9:    */ import java.awt.Color;
/*  10:    */ import java.awt.Font;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Graphics2D;
/*  13:    */ import java.awt.event.MouseEvent;
/*  14:    */ import java.awt.event.MouseMotionListener;
/*  15:    */ import java.awt.geom.Rectangle2D;
/*  16:    */ import java.text.DecimalFormat;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.Iterator;
/*  20:    */ import java.util.Set;
/*  21:    */ import matthewFay.Utilities.Pair;
/*  22:    */ 
/*  23:    */ public class GeneratedInOrderGridPanel
/*  24:    */   extends MatrixJPanel
/*  25:    */   implements MouseMotionListener
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   private boolean grayscale;
/*  29:    */   private boolean triangle;
/*  30:    */   private int current_size;
/*  31:    */   KeywordAngles keywordAngles;
/*  32: 30 */   int mouse_x = -1;
/*  33: 31 */   int mouse_y = -1;
/*  34:    */   ReflectionLevelMemory memory;
/*  35:    */   
/*  36:    */   public GeneratedInOrderGridPanel(ReflectionLevelMemory memory, boolean grayscale, boolean triangle, int current_size)
/*  37:    */   {
/*  38: 37 */     this.memory = memory;
/*  39: 38 */     addMouseMotionListener(this);
/*  40:    */     
/*  41: 40 */     this.keywordAngles = new KeywordAngles();
/*  42:    */     
/*  43: 42 */     this.grayscale = grayscale;
/*  44: 43 */     this.triangle = triangle;
/*  45: 44 */     this.current_size = current_size;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void paintComponent(Graphics g)
/*  49:    */   {
/*  50: 49 */     super.paintComponent(g);
/*  51:    */     
/*  52: 51 */     setBackground(Color.white);
/*  53:    */     
/*  54: 53 */     g.setFont(new Font("Dialog", 0, 12));
/*  55:    */     
/*  56: 55 */     paintKeywords(g);
/*  57:    */   }
/*  58:    */   
/*  59:    */   private HashMap<String, HashMap<String, Integer>> getRanks()
/*  60:    */   {
/*  61: 59 */     HashMap<String, ArrayList<Pair<Double, String>>> ranks = new HashMap();
/*  62: 60 */     for (int i = 0; i < this.memory.getStoryCount(); i++)
/*  63:    */     {
/*  64: 61 */       String story1 = (String)this.memory.getStoryNames().get(i);
/*  65: 62 */       story1Ranks = new ArrayList();
/*  66: 63 */       ranks.put(story1, story1Ranks);
/*  67: 64 */       for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  68:    */       {
/*  69: 65 */         String story2 = (String)this.memory.getStoryNames().get(j);
/*  70:    */         
/*  71: 67 */         double val = InOrderVectorMatcher.matchGenVectorsInOrder(story1, story2, this.current_size, this.memory).getValue();
/*  72:    */         
/*  73: 69 */         boolean found = false;
/*  74: 70 */         for (int k = 0; k < story1Ranks.size(); k++) {
/*  75: 71 */           if (((Double)((Pair)story1Ranks.get(k)).a).doubleValue() < val)
/*  76:    */           {
/*  77: 72 */             story1Ranks.add(k, new Pair(Double.valueOf(val), story2));
/*  78: 73 */             found = true;
/*  79: 74 */             break;
/*  80:    */           }
/*  81:    */         }
/*  82: 77 */         if (!found) {
/*  83: 78 */           story1Ranks.add(new Pair(Double.valueOf(val), story2));
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87: 83 */     HashMap<String, HashMap<String, Integer>> outRanks = new HashMap();
/*  88:    */     ArrayList<Pair<Double, String>> ordering;
/*  89:    */     int i;
/*  90: 85 */     for (ArrayList<Pair<Double, String>> story1Ranks = ranks.keySet().iterator(); story1Ranks.hasNext(); i < ordering.size())
/*  91:    */     {
/*  92: 85 */       String story1 = (String)story1Ranks.next();
/*  93: 86 */       outRanks.put(story1, new HashMap());
/*  94: 87 */       ordering = (ArrayList)ranks.get(story1);
/*  95: 88 */       int current_rank = 0;
/*  96: 89 */       double current_val = -1.0D;
/*  97: 90 */       i = 0; continue;
/*  98: 91 */       if (((Double)((Pair)ordering.get(i)).a).doubleValue() != current_val)
/*  99:    */       {
/* 100: 92 */         current_rank = i;
/* 101: 93 */         current_val = ((Double)((Pair)ordering.get(i)).a).doubleValue();
/* 102:    */       }
/* 103: 95 */       ((HashMap)outRanks.get(story1)).put((String)((Pair)ordering.get(i)).b, Integer.valueOf(current_rank));i++;
/* 104:    */     }
/* 105: 99 */     return outRanks;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private double getRankSD()
/* 109:    */   {
/* 110:103 */     HashMap<String, HashMap<String, Integer>> ranks = getRanks();
/* 111:    */     
/* 112:105 */     double total = 0.0D;
/* 113:106 */     int num = 0;
/* 114:107 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 115:    */     {
/* 116:108 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 117:109 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 118:    */       {
/* 119:110 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 120:    */         
/* 121:112 */         int myRank = ((Integer)((HashMap)ranks.get(story1)).get(story2)).intValue();
/* 122:113 */         int studyRank = StudyResults.getRank(story1, story2);
/* 123:    */         
/* 124:115 */         double diff = Math.pow(myRank - studyRank, 2.0D);
/* 125:116 */         total += diff;
/* 126:117 */         num++;
/* 127:    */       }
/* 128:    */     }
/* 129:121 */     return Math.sqrt(total / num);
/* 130:    */   }
/* 131:    */   
/* 132:    */   private double calcStandardDev()
/* 133:    */   {
/* 134:126 */     double total = 0.0D;
/* 135:127 */     int num = 0;
/* 136:128 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 137:    */     {
/* 138:129 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 139:130 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 140:    */       {
/* 141:131 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 142:132 */         int studyResult = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/* 143:133 */         double studyVal = studyResult / 11.0F;
/* 144:134 */         double val = InOrderVectorMatcher.matchVectorsInOrder(story1, story2, this.memory).getValue();
/* 145:135 */         double diff = Math.pow(val - studyVal, 2.0D);
/* 146:136 */         total += diff;
/* 147:137 */         num++;
/* 148:    */       }
/* 149:    */     }
/* 150:140 */     return Math.sqrt(total / num);
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void paintKeywords(Graphics g)
/* 154:    */   {
/* 155:145 */     int width = getWidth();
/* 156:146 */     int height = getHeight();
/* 157:    */     
/* 158:148 */     Font font = new Font("Dialog", 0, 12);
/* 159:    */     
/* 160:150 */     DecimalFormat df = new DecimalFormat("#.##");
/* 161:151 */     g.setColor(Color.black);
/* 162:152 */     double stdDevError = getRankSD();
/* 163:153 */     writeStdDevError(g, df, stdDevError);
/* 164:    */     
/* 165:    */ 
/* 166:156 */     Graphics2D g2 = (Graphics2D)g;
/* 167:157 */     int x_start = 0;
/* 168:159 */     for (String name : this.memory.getStoryNames())
/* 169:    */     {
/* 170:160 */       Rectangle2D bounds = font.getStringBounds(name, g2.getFontRenderContext());
/* 171:161 */       x_start = (int)Math.max(x_start, bounds.getWidth());
/* 172:    */     }
/* 173:164 */     int y_start = 20;
/* 174:    */     
/* 175:166 */     int word_width = (int)((width - x_start) / this.memory.getStoryCount());
/* 176:167 */     int word_height = (int)((height - 40) / this.memory.getStoryCount());
/* 177:168 */     int x_pos = x_start;
/* 178:169 */     int y_pos = y_start;
/* 179:    */     
/* 180:171 */     double widestName = this.memory.getWidestNameWidth(font, g2.getFontRenderContext());
/* 181:172 */     if (widestName >= word_width * 2)
/* 182:    */     {
/* 183:173 */       g.setFont(new Font("Dialog", 1, 16));
/* 184:174 */       for (int i = 1; i <= this.memory.getStoryCount(); i++)
/* 185:    */       {
/* 186:175 */         g.drawString(i, (int)(x_pos + word_width * 0.5F), y_pos);
/* 187:176 */         x_pos += word_width;
/* 188:    */       }
/* 189:179 */       g.setFont(new Font("Dialog", 1, 12));
/* 190:180 */       x_pos = 0;
/* 191:181 */       y_pos = y_start + word_height;
/* 192:182 */       int index = 1;
/* 193:183 */       for (String storyName : this.memory.getStoryNames())
/* 194:    */       {
/* 195:184 */         g.drawString(index + ": " + storyName, x_pos, y_pos);
/* 196:185 */         y_pos += word_height;
/* 197:186 */         index++;
/* 198:    */       }
/* 199:    */     }
/* 200:    */     else
/* 201:    */     {
/* 202:190 */       g.setFont(new Font("Dialog", 0, 12));
/* 203:191 */       int index = 0;
/* 204:192 */       for (String storyName : this.memory.getStoryNames())
/* 205:    */       {
/* 206:193 */         g.drawString(storyName, x_pos, y_pos - y_pos / 2 * (index % 2));
/* 207:194 */         x_pos += word_width;
/* 208:195 */         index++;
/* 209:    */       }
/* 210:198 */       x_pos = 0;
/* 211:199 */       y_pos = y_start + word_height;
/* 212:200 */       for (String storyName : this.memory.getStoryNames())
/* 213:    */       {
/* 214:201 */         g.drawString(storyName, x_pos, y_pos);
/* 215:202 */         y_pos += word_height;
/* 216:    */       }
/* 217:    */     }
/* 218:207 */     for (int j = 0; j < this.memory.getStoryCount(); j++) {
/* 219:208 */       for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 220:209 */         if ((!this.triangle) || (i <= j))
/* 221:    */         {
/* 222:213 */           String story1 = (String)this.memory.getStoryNames().get(i);
/* 223:214 */           String story2 = (String)this.memory.getStoryNames().get(j);
/* 224:215 */           double match = InOrderVectorMatcher.matchGenVectorsInOrder(story1, story2, this.current_size, this.memory).getValue();
/* 225:216 */           if (this.grayscale) {
/* 226:217 */             g.setColor(new Color((int)(255.0D * match), (int)(255.0D * match), (int)(255.0D * match)));
/* 227:220 */           } else if (match == 0.0D) {
/* 228:221 */             g.setColor(Color.black);
/* 229:    */           } else {
/* 230:224 */             g.setColor(new Color(Color.HSBtoRGB(0.4F, 1.0F - (float)match, (float)match)));
/* 231:    */           }
/* 232:229 */           g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 233:    */           
/* 234:231 */           g.setColor(Color.black);
/* 235:232 */           g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 236:    */           
/* 237:234 */           g.setColor(Color.white);
/* 238:235 */           g.drawString(df.format(match), x_start + i * word_width, y_start + j * word_height + word_height);
/* 239:    */         }
/* 240:    */       }
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void mouseDragged(MouseEvent event) {}
/* 245:    */   
/* 246:    */   public void mouseMoved(MouseEvent event)
/* 247:    */   {
/* 248:272 */     this.mouse_x = event.getX();
/* 249:273 */     this.mouse_y = event.getY();
/* 250:    */     
/* 251:275 */     repaint();
/* 252:276 */     updateUI();
/* 253:    */   }
/* 254:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GeneratedInOrderGridPanel
 * JD-Core Version:    0.7.0.1
 */