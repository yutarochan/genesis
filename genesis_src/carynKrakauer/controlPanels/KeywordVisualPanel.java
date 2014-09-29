/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.KeywordAngles;
/*   4:    */ import carynKrakauer.ReflectionLevelMemory;
/*   5:    */ import carynKrakauer.StudyResults;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Graphics2D;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.awt.event.MouseMotionListener;
/*  12:    */ import java.awt.geom.Rectangle2D;
/*  13:    */ import java.text.DecimalFormat;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Set;
/*  19:    */ import matthewFay.Utilities.Pair;
/*  20:    */ 
/*  21:    */ public class KeywordVisualPanel
/*  22:    */   extends MatrixJPanel
/*  23:    */   implements MouseMotionListener
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   private boolean grayscale;
/*  27:    */   private boolean triangle;
/*  28:    */   KeywordAngles keywordAngles;
/*  29: 30 */   int mouse_x = -1;
/*  30: 31 */   int mouse_y = -1;
/*  31:    */   ReflectionLevelMemory memory;
/*  32:    */   
/*  33:    */   public KeywordVisualPanel(ReflectionLevelMemory memory, boolean grayscale, boolean triangle)
/*  34:    */   {
/*  35: 37 */     this.memory = memory;
/*  36: 38 */     addMouseMotionListener(this);
/*  37:    */     
/*  38: 40 */     this.keywordAngles = new KeywordAngles();
/*  39:    */     
/*  40: 42 */     this.grayscale = grayscale;
/*  41: 43 */     this.triangle = triangle;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void paintComponent(Graphics g)
/*  45:    */   {
/*  46: 48 */     super.paintComponent(g);
/*  47:    */     
/*  48: 50 */     setBackground(Color.LIGHT_GRAY);
/*  49:    */     
/*  50: 52 */     g.setFont(new Font("Dialog", 0, 12));
/*  51:    */     
/*  52: 54 */     paintKeywords(g);
/*  53:    */   }
/*  54:    */   
/*  55:    */   private double calcStandardDev()
/*  56:    */   {
/*  57: 59 */     double total = 0.0D;
/*  58: 60 */     int num = 0;
/*  59: 61 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  60:    */     {
/*  61: 62 */       String story1 = (String)this.memory.getStoryNames().get(j);
/*  62: 63 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/*  63:    */       {
/*  64: 64 */         String story2 = (String)this.memory.getStoryNames().get(i);
/*  65: 65 */         int studyResult = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/*  66: 66 */         double studyVal = studyResult / 11.0F / StudyResults.getMaxStudyResult();
/*  67: 67 */         double keywordVal = ((Double)((HashMap)this.keywordAngles.keyword_angles.get(story1)).get(story2)).doubleValue() / this.keywordAngles.getMaxKeywordAngle();
/*  68: 68 */         double diff = Math.pow(keywordVal - studyVal, 2.0D);
/*  69: 69 */         total += diff;
/*  70: 70 */         num++;
/*  71:    */       }
/*  72:    */     }
/*  73: 73 */     return Math.sqrt(total / num);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private HashMap<String, HashMap<String, Integer>> getRanks()
/*  77:    */   {
/*  78: 77 */     HashMap<String, ArrayList<Pair<Double, String>>> ranks = new HashMap();
/*  79: 78 */     for (int i = 0; i < this.memory.getStoryCount(); i++)
/*  80:    */     {
/*  81: 79 */       String story1 = (String)this.memory.getStoryNames().get(i);
/*  82: 80 */       story1Ranks = new ArrayList();
/*  83: 81 */       ranks.put(story1, story1Ranks);
/*  84: 82 */       for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  85:    */       {
/*  86: 83 */         String story2 = (String)this.memory.getStoryNames().get(j);
/*  87:    */         
/*  88: 85 */         double val = ((Double)((HashMap)this.keywordAngles.keyword_angles.get(this.memory.getStoryNames().get(i))).get(this.memory.getStoryNames().get(j))).doubleValue();
/*  89:    */         
/*  90: 87 */         boolean found = false;
/*  91: 88 */         for (int k = 0; k < story1Ranks.size(); k++) {
/*  92: 89 */           if (((Double)((Pair)story1Ranks.get(k)).a).doubleValue() < val)
/*  93:    */           {
/*  94: 90 */             story1Ranks.add(k, new Pair(Double.valueOf(val), story2));
/*  95: 91 */             found = true;
/*  96: 92 */             break;
/*  97:    */           }
/*  98:    */         }
/*  99: 95 */         if (!found) {
/* 100: 96 */           story1Ranks.add(new Pair(Double.valueOf(val), story2));
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:101 */     HashMap<String, HashMap<String, Integer>> outRanks = new HashMap();
/* 105:    */     ArrayList<Pair<Double, String>> ordering;
/* 106:    */     int i;
/* 107:103 */     for (ArrayList<Pair<Double, String>> story1Ranks = ranks.keySet().iterator(); story1Ranks.hasNext(); i < ordering.size())
/* 108:    */     {
/* 109:103 */       String story1 = (String)story1Ranks.next();
/* 110:104 */       outRanks.put(story1, new HashMap());
/* 111:105 */       ordering = (ArrayList)ranks.get(story1);
/* 112:106 */       int current_rank = 0;
/* 113:107 */       double current_val = -1.0D;
/* 114:108 */       i = 0; continue;
/* 115:109 */       if (((Double)((Pair)ordering.get(i)).a).doubleValue() != current_val)
/* 116:    */       {
/* 117:110 */         current_rank = i;
/* 118:111 */         current_val = ((Double)((Pair)ordering.get(i)).a).doubleValue();
/* 119:    */       }
/* 120:113 */       ((HashMap)outRanks.get(story1)).put((String)((Pair)ordering.get(i)).b, Integer.valueOf(current_rank));i++;
/* 121:    */     }
/* 122:117 */     return outRanks;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private double getRankSD()
/* 126:    */   {
/* 127:121 */     HashMap<String, HashMap<String, Integer>> ranks = getRanks();
/* 128:    */     
/* 129:123 */     double total = 0.0D;
/* 130:124 */     int num = 0;
/* 131:125 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 132:    */     {
/* 133:126 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 134:127 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 135:    */       {
/* 136:128 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 137:    */         
/* 138:130 */         int myRank = ((Integer)((HashMap)ranks.get(story1)).get(story2)).intValue();
/* 139:131 */         int studyRank = StudyResults.getRank(story1, story2);
/* 140:    */         
/* 141:133 */         double diff = Math.pow(myRank - studyRank, 2.0D);
/* 142:134 */         total += diff;
/* 143:135 */         num++;
/* 144:    */       }
/* 145:    */     }
/* 146:139 */     if (num == 0) {
/* 147:140 */       return -1.0D;
/* 148:    */     }
/* 149:142 */     return Math.sqrt(total / num);
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void paintKeywords(Graphics g)
/* 153:    */   {
/* 154:147 */     int width = getWidth();
/* 155:148 */     int height = getHeight();
/* 156:    */     
/* 157:150 */     Font font = new Font("Dialog", 0, 12);
/* 158:    */     
/* 159:152 */     DecimalFormat df = new DecimalFormat("#.##");
/* 160:153 */     g.setColor(Color.black);
/* 161:    */     try
/* 162:    */     {
/* 163:157 */       double stdDevError = getRankSD();
/* 164:158 */       writeStdDevError(g, df, stdDevError);
/* 165:    */     }
/* 166:    */     catch (Exception e)
/* 167:    */     {
/* 168:161 */       e.printStackTrace();
/* 169:    */     }
/* 170:163 */     Graphics2D g2 = (Graphics2D)g;
/* 171:164 */     int x_start = 0;
/* 172:166 */     for (String name : this.memory.getStoryNames())
/* 173:    */     {
/* 174:167 */       Rectangle2D bounds = font.getStringBounds(name, g2.getFontRenderContext());
/* 175:168 */       x_start = (int)Math.max(x_start, bounds.getWidth());
/* 176:    */     }
/* 177:171 */     int y_start = 20;
/* 178:    */     
/* 179:173 */     int word_width = (int)((width - x_start) / this.memory.getStoryCount());
/* 180:174 */     int word_height = (int)((height - 40) / this.memory.getStoryCount());
/* 181:175 */     int x_pos = x_start;
/* 182:176 */     int y_pos = y_start;
/* 183:    */     
/* 184:178 */     double widestName = this.memory.getWidestNameWidth(font, g2.getFontRenderContext());
/* 185:179 */     if (widestName >= word_width * 2)
/* 186:    */     {
/* 187:180 */       g.setFont(new Font("Dialog", 1, 16));
/* 188:181 */       for (int i = 1; i <= this.memory.getStoryCount(); i++)
/* 189:    */       {
/* 190:182 */         g.drawString(i, (int)(x_pos + word_width * 0.5F), y_pos);
/* 191:183 */         x_pos += word_width;
/* 192:    */       }
/* 193:186 */       g.setFont(new Font("Dialog", 1, 12));
/* 194:187 */       x_pos = 0;
/* 195:188 */       y_pos = y_start + word_height;
/* 196:189 */       int index = 1;
/* 197:190 */       for (String storyName : this.memory.getStoryNames())
/* 198:    */       {
/* 199:191 */         g.drawString(index + ": " + storyName, x_pos, y_pos);
/* 200:192 */         y_pos += word_height;
/* 201:193 */         index++;
/* 202:    */       }
/* 203:    */     }
/* 204:    */     else
/* 205:    */     {
/* 206:197 */       g.setFont(new Font("Dialog", 0, 12));
/* 207:198 */       int index = 0;
/* 208:199 */       for (String storyName : this.memory.getStoryNames())
/* 209:    */       {
/* 210:200 */         g.drawString(storyName, x_pos, y_pos - y_pos / 2 * (index % 2));
/* 211:201 */         x_pos += word_width;
/* 212:202 */         index++;
/* 213:    */       }
/* 214:205 */       x_pos = 0;
/* 215:206 */       y_pos = y_start + word_height;
/* 216:207 */       for (String storyName : this.memory.getStoryNames())
/* 217:    */       {
/* 218:208 */         g.drawString(storyName, x_pos, y_pos);
/* 219:209 */         y_pos += word_height;
/* 220:    */       }
/* 221:    */     }
/* 222:214 */     for (int j = 0; j < this.memory.getStoryCount(); j++) {
/* 223:215 */       for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 224:216 */         if ((!this.triangle) || (i <= j))
/* 225:    */         {
/* 226:220 */           double match = ((Double)((HashMap)this.keywordAngles.keyword_angles.get(this.memory.getStoryNames().get(i))).get(this.memory.getStoryNames().get(j))).doubleValue();
/* 227:221 */           if (this.grayscale) {
/* 228:222 */             g.setColor(new Color((int)(255.0D * match), (int)(255.0D * match), (int)(255.0D * match)));
/* 229:225 */           } else if (match == 0.0D) {
/* 230:226 */             g.setColor(Color.black);
/* 231:    */           } else {
/* 232:229 */             g.setColor(new Color(Color.HSBtoRGB(0.4F, 1.0F - (float)match, (float)match)));
/* 233:    */           }
/* 234:234 */           g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 235:    */           
/* 236:236 */           g.setColor(Color.black);
/* 237:237 */           g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 238:    */         }
/* 239:    */       }
/* 240:    */     }
/* 241:241 */     double mean = 0.0D;
/* 242:242 */     Object matches = new ArrayList();
/* 243:243 */     for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 244:244 */       for (int j = i + 1; j < this.memory.getStoryCount(); j++)
/* 245:    */       {
/* 246:245 */         double match = ((Double)((HashMap)this.keywordAngles.keyword_angles.get(this.memory.getStoryNames().get(i))).get(this.memory.getStoryNames().get(j))).doubleValue();
/* 247:246 */         ((List)matches).add(Double.valueOf(match));
/* 248:247 */         mean += match;
/* 249:    */       }
/* 250:    */     }
/* 251:251 */     mean /= ((List)matches).size();
/* 252:252 */     double variance = 0.0D;
/* 253:253 */     for (Double match : (List)matches) {
/* 254:254 */       variance += Math.pow(match.doubleValue() - mean, 2.0D);
/* 255:    */     }
/* 256:256 */     variance /= ((List)matches).size();
/* 257:257 */     double stdDev = Math.sqrt(variance);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void mouseDragged(MouseEvent event) {}
/* 261:    */   
/* 262:    */   public void mouseMoved(MouseEvent event)
/* 263:    */   {
/* 264:274 */     this.mouse_x = event.getX();
/* 265:275 */     this.mouse_y = event.getY();
/* 266:    */     
/* 267:277 */     repaint();
/* 268:278 */     updateUI();
/* 269:    */   }
/* 270:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.KeywordVisualPanel
 * JD-Core Version:    0.7.0.1
 */