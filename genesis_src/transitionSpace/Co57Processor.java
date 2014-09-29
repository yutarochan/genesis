/*   1:    */ package transitionSpace;
/*   2:    */ 
/*   3:    */ import connections.AbstractWiredBox;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import start.StartPreprocessor;
/*   9:    */ import utils.Mark;
/*  10:    */ 
/*  11:    */ public class Co57Processor
/*  12:    */   extends AbstractWiredBox
/*  13:    */ {
/*  14:    */   private static Co57Processor co57Processor;
/*  15: 18 */   private ArrayList<HashMap<String, Object>> attributeTraces = new ArrayList();
/*  16: 20 */   private Ladders ladders = new Ladders();
/*  17:    */   
/*  18:    */   public static Co57Processor getCo57Processor()
/*  19:    */   {
/*  20: 23 */     if (co57Processor == null) {
/*  21: 24 */       co57Processor = new Co57Processor();
/*  22:    */     }
/*  23: 26 */     return co57Processor;
/*  24:    */   }
/*  25:    */   
/*  26:    */   private Co57Processor()
/*  27:    */   {
/*  28: 30 */     setName("Co57Processor");
/*  29: 31 */     Connections.wire("traces port", StartPreprocessor.getStartPreprocessor(), this);
/*  30: 32 */     Connections.wire(this, StartPreprocessor.getStartPreprocessor());
/*  31: 33 */     Connections.getPorts(this).addSignalProcessor("process");
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void augmentHashMap(HashMap<String, Object> map)
/*  35:    */   {
/*  36: 37 */     if (this.attributeTraces.size() >= 5) {
/*  37: 38 */       this.attributeTraces.remove(0);
/*  38:    */     }
/*  39: 40 */     this.attributeTraces.add(map);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void process(Object o)
/*  43:    */   {
/*  44: 44 */     if ((o instanceof HashMap)) {
/*  45: 45 */       processAttentionTrace((HashMap)o);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void processAttentionTrace(HashMap<String, HashMap> trace)
/*  50:    */   {
/*  51: 50 */     Mark.betterSay(
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96: 95 */       new Object[] { "Hashmap", trace });HashMap<String, Object> attributes = (HashMap)trace.get("FOAAttrs");
/*  97: 52 */     if (this.attributeTraces.size() != 0)
/*  98:    */     {
/*  99: 53 */       HashMap<String, Object> previousAttributes = (HashMap)this.attributeTraces.get(this.attributeTraces.size() - 1);
/* 100: 54 */       Integer focus = (Integer)attributes.get("FocusedObject");
/* 101: 55 */       Integer previousFocus = (Integer)previousAttributes.get("FocusedObject");
/* 102: 56 */       Ladder ladder = new Ladder();
/* 103: 57 */       String f = "Alpha";
/* 104: 58 */       if (focus.intValue() == 1) {
/* 105: 59 */         f = "Bravo";
/* 106: 61 */       } else if (focus.intValue() == 2) {
/* 107: 62 */         f = "Charlie";
/* 108:    */       }
/* 109: 64 */       if ((focus != null) && (previousFocus != null))
/* 110:    */       {
/* 111: 65 */         Double dx = (Double)attributes.get("dxFocusedObject");
/* 112: 66 */         Double previousDx = (Double)previousAttributes.get("dxFocusedObject");
/* 113: 68 */         if (focus != previousFocus)
/* 114:    */         {
/* 115: 70 */           if (dx.doubleValue() > 0.0D) {
/* 116: 71 */             ladder.addTransition(f, "moving right", "appear");
/* 117: 73 */           } else if (dx.doubleValue() < 0.0D) {
/* 118: 74 */             ladder.addTransition(f, "moving left", "appear");
/* 119:    */           }
/* 120:    */         }
/* 121: 79 */         else if (((dx.doubleValue() < 0.0D) && (previousDx.doubleValue() > 0.0D)) || ((dx.doubleValue() > 0.0D) && (previousDx.doubleValue() < 0.0D))) {
/* 122: 80 */           if (dx.doubleValue() > 0.0D)
/* 123:    */           {
/* 124: 81 */             ladder.addTransition(f, "moving right", "appear");
/* 125: 82 */             ladder.addTransition(f, "moving left", "disappear");
/* 126:    */           }
/* 127:    */           else
/* 128:    */           {
/* 129: 85 */             ladder.addTransition(f, "moving left", "appear");
/* 130: 86 */             ladder.addTransition(f, "moving right", "disappear");
/* 131:    */           }
/* 132:    */         }
/* 133:    */       }
/* 134: 91 */       this.ladders.addLadder(ladder);
/* 135:    */     }
/* 136: 93 */     augmentHashMap(attributes);
/* 137: 94 */     processLadders(this.ladders);
/* 138:    */   }
/* 139:    */   
/* 140: 97 */   int delay = 0;
/* 141:    */   
/* 142:    */   private void processLadders(Ladders event)
/* 143:    */   {
/* 144:100 */     String result = Patterns.getPatterns().match(event);
/* 145:102 */     if (result != null) {
/* 146:103 */       if (this.delay > 30)
/* 147:    */       {
/* 148:104 */         this.delay = 0;
/* 149:105 */         Mark.betterSay(new Object[] {"Produced result", result });
/* 150:106 */         Connections.getPorts(this).transmit(result);
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:109 */         this.delay += 1;
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static void main(String[] ignore)
/* 160:    */   {
/* 161:115 */     Ladders event = new Ladders();
/* 162:116 */     Ladder ladder = new Ladder();
/* 163:117 */     ladder.addTransition("Bravo", "moving right", "appear");
/* 164:118 */     event.addLadder(ladder);
/* 165:119 */     new Co57Processor().process(event);
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     transitionSpace.Co57Processor
 * JD-Core Version:    0.7.0.1
 */