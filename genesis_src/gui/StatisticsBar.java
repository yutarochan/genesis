/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import javax.swing.Box;
/*   9:    */ import javax.swing.BoxLayout;
/*  10:    */ import javax.swing.JLabel;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ import utils.Timer;
/*  13:    */ 
/*  14:    */ public class StatisticsBar
/*  15:    */   extends JPanel
/*  16:    */   implements WiredBox
/*  17:    */ {
/*  18:    */   public static final String STORY_ELEMENTS = "story elements";
/*  19:    */   public static final String COMMONSENSE_LABEL = "commonsence label";
/*  20:    */   public static final String REFLECTION_LABEL = "reflection label";
/*  21:    */   public static final String COMMONSENSE_COUNT_LABEL = "commonsence count label";
/*  22:    */   public static final String REFLECTION_COUNT_LABEL = "reflection count label";
/*  23:    */   public static final String CLEAR_COUNTS = "clear counts";
/*  24:    */   public static final String FROM_COUNT_PRODUCER = "story processor input port";
/*  25: 37 */   public static final Object STORY_ELEMENT_COUNT = "story element count";
/*  26:    */   public static final String EXPLICIT_STATEMENT_COUNT = "explicit statement count";
/*  27: 41 */   public static final Object INFERENCE_RULE_COUNT = "inference rule count";
/*  28: 43 */   public static final Object INFERENCE_COUNT = "inference count";
/*  29: 45 */   public static final Object CONCEPT_COUNT = "concept count";
/*  30: 47 */   public static final Object CONCEPT_DISCOVERY_COUNT = "concept dsicovery count";
/*  31: 49 */   public static final Object STORY_TIMER = "story timer";
/*  32: 53 */   private JLabel totalElementsLabel = new JLabel();
/*  33: 55 */   private JLabel explicitElementsLabel = new JLabel();
/*  34: 57 */   private JLabel inferredElementsLabel = new JLabel();
/*  35: 59 */   private JLabel ruleLabel = new JLabel();
/*  36: 61 */   private JLabel conceptLabel = new JLabel();
/*  37: 63 */   private JLabel inferenceLabel = new JLabel();
/*  38: 65 */   private JLabel discoveryLabel = new JLabel();
/*  39: 67 */   private JLabel storyTimerLabel = new JLabel();
/*  40: 69 */   private JLabel totalTimerLabel = new JLabel();
/*  41:    */   private int explicitElementCount;
/*  42:    */   private int totalElementCount;
/*  43:    */   private long startTime;
/*  44:    */   
/*  45:    */   public StatisticsBar()
/*  46:    */   {
/*  47: 78 */     setLayout(new BoxLayout(this, 1));
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51: 82 */     setBackground(Color.WHITE);
/*  52: 83 */     setOpaque(true);
/*  53:    */     
/*  54: 85 */     add(Box.createVerticalStrut(20));
/*  55: 86 */     add(this.ruleLabel);
/*  56: 87 */     add(Box.createVerticalStrut(10));
/*  57: 88 */     add(this.inferenceLabel);
/*  58: 89 */     add(Box.createVerticalStrut(10));
/*  59: 90 */     add(this.conceptLabel);
/*  60: 91 */     add(Box.createVerticalStrut(10));
/*  61: 92 */     add(this.discoveryLabel);
/*  62: 93 */     add(Box.createVerticalStrut(10));
/*  63: 94 */     add(this.explicitElementsLabel);
/*  64: 95 */     add(Box.createVerticalStrut(10));
/*  65: 96 */     add(this.inferredElementsLabel);
/*  66: 97 */     add(Box.createVerticalStrut(10));
/*  67: 98 */     add(this.totalElementsLabel);
/*  68: 99 */     add(Box.createVerticalStrut(10));
/*  69:100 */     add(this.storyTimerLabel);
/*  70:101 */     add(Box.createVerticalStrut(10));
/*  71:102 */     add(this.totalTimerLabel);
/*  72:    */     
/*  73:104 */     setTotalElementCount(Integer.valueOf(0));
/*  74:105 */     setExplicitElementCount(Integer.valueOf(0));
/*  75:106 */     setInferredElementCount(Integer.valueOf(0));
/*  76:107 */     setCommonsenseRuleCount(Integer.valueOf(0));
/*  77:108 */     setConceptPatternCount(Integer.valueOf(0));
/*  78:109 */     setCommonsenseInferenceCount(Integer.valueOf(0));
/*  79:110 */     setConceptDiscoveryCount(Integer.valueOf(0));
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:117 */     Connections.getPorts(this).addSignalProcessor("reflection label", "setReflectionLabel");
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:122 */     Connections.getPorts(this).addSignalProcessor("clear counts", "clearCounts");
/*  92:    */     
/*  93:124 */     Connections.getPorts(this).addSignalProcessor("story processor input port", "distributeStoryProcessorData");
/*  94:    */     
/*  95:126 */     clearCounts("reset");
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void distributeStoryProcessorData(Object o)
/*  99:    */   {
/* 100:130 */     if ((o instanceof BetterSignal))
/* 101:    */     {
/* 102:131 */       BetterSignal signal = (BetterSignal)o;
/* 103:132 */       if (signal.get(0, String.class) == "explicit statement count") {
/* 104:133 */         setExplicitElementCount(signal.get(1, Integer.class));
/* 105:135 */       } else if (signal.get(0, String.class) == STORY_ELEMENT_COUNT) {
/* 106:136 */         setTotalElementCount(signal.get(1, Integer.class));
/* 107:138 */       } else if (signal.get(0, String.class) == INFERENCE_RULE_COUNT) {
/* 108:139 */         setCommonsenseRuleCount(signal.get(1, Integer.class));
/* 109:141 */       } else if (signal.get(0, String.class) == INFERENCE_COUNT) {
/* 110:142 */         setCommonsenseInferenceCount(signal.get(1, Integer.class));
/* 111:144 */       } else if (signal.get(0, String.class) == CONCEPT_COUNT) {
/* 112:145 */         setConceptPatternCount(signal.get(1, Integer.class));
/* 113:147 */       } else if (signal.get(0, String.class) == CONCEPT_DISCOVERY_COUNT) {
/* 114:148 */         setConceptDiscoveryCount(signal.get(1, Integer.class));
/* 115:150 */       } else if (signal.get(0, String.class) == STORY_TIMER) {
/* 116:151 */         setStoryTimer((String)signal.get(1, String.class));
/* 117:    */       }
/* 118:153 */       setTotalTimer(Timer.time("Total timer", this.startTime));
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTotalElementCount(Object o)
/* 123:    */   {
/* 124:158 */     this.totalElementCount = ((Integer)o).intValue();
/* 125:159 */     setInferredElementCount(Integer.valueOf(this.totalElementCount - this.explicitElementCount));
/* 126:160 */     this.totalElementsLabel.setText("Total elements: " + o.toString());
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void clearCounts(Object object)
/* 130:    */   {
/* 131:164 */     if (object == "reset")
/* 132:    */     {
/* 133:165 */       setCommonsenseRuleCount(Integer.valueOf(0));
/* 134:166 */       setConceptPatternCount(Integer.valueOf(0));
/* 135:167 */       setCommonsenseInferenceCount(Integer.valueOf(0));
/* 136:168 */       setConceptDiscoveryCount(Integer.valueOf(0));
/* 137:169 */       setExplicitElementCount(Integer.valueOf(0));
/* 138:170 */       setInferredElementCount(Integer.valueOf(0));
/* 139:171 */       setTotalElementCount(Integer.valueOf(0));
/* 140:172 */       setStoryTimer("0.0 sec");
/* 141:173 */       setTotalTimer("0.0 sec");
/* 142:174 */       this.startTime = System.currentTimeMillis();
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setExplicitElementCount(Object o)
/* 147:    */   {
/* 148:179 */     this.explicitElementCount = ((Integer)o).intValue();
/* 149:180 */     setInferredElementCount(Integer.valueOf(this.totalElementCount - this.explicitElementCount));
/* 150:181 */     this.explicitElementsLabel.setText("Explicit elements: " + o.toString());
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setInferredElementCount(Object o)
/* 154:    */   {
/* 155:185 */     this.inferredElementsLabel.setText("Inferred elements: " + o.toString());
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setCommonsenseRuleCount(Object o)
/* 159:    */   {
/* 160:189 */     this.ruleLabel.setText("Rules: " + o.toString());
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setConceptPatternCount(Object o)
/* 164:    */   {
/* 165:193 */     this.conceptLabel.setText("Concepts: " + o.toString());
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setCommonsenseInferenceCount(Object o)
/* 169:    */   {
/* 170:197 */     this.inferenceLabel.setText("Inferences: " + o.toString());
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setConceptDiscoveryCount(Object o)
/* 174:    */   {
/* 175:201 */     this.discoveryLabel.setText("Discoveries: " + o.toString());
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void setStoryTimer(String string)
/* 179:    */   {
/* 180:205 */     this.storyTimerLabel.setText("Story reading time: " + string);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void setTotalTimer(String string)
/* 184:    */   {
/* 185:209 */     this.totalTimerLabel.setText("Total time elapsed: " + string);
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.StatisticsBar
 * JD-Core Version:    0.7.0.1
 */