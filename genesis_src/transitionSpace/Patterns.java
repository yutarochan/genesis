/*   1:    */ package transitionSpace;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import utils.Mark;
/*   8:    */ 
/*   9:    */ public class Patterns
/*  10:    */   extends ArrayList<Pattern>
/*  11:    */ {
/*  12:    */   private static Patterns patterns;
/*  13: 16 */   private HashMap<String, String> t1 = new HashMap();
/*  14: 18 */   private HashMap<String, String> t2 = new HashMap();
/*  15: 20 */   private HashMap<String, String> t3 = new HashMap();
/*  16: 22 */   private HashMap<String, String> t4 = new HashMap();
/*  17: 24 */   private HashMap<String, String> t5 = new HashMap();
/*  18: 26 */   private HashMap<String, String> t6 = new HashMap();
/*  19: 28 */   private ArrayList<HashMap> transforms = new ArrayList();
/*  20:    */   
/*  21:    */   public static Patterns getPatterns()
/*  22:    */   {
/*  23: 31 */     if (patterns == null) {
/*  24: 32 */       patterns = new Patterns();
/*  25:    */     }
/*  26: 34 */     return patterns;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Patterns()
/*  30:    */   {
/*  31: 39 */     this.t1.put("pattern object 1", "Alpha");
/*  32: 40 */     this.t1.put("pattern object 2", "Bravo");
/*  33: 41 */     this.t1.put("pattern object 3", "Charlie");
/*  34:    */     
/*  35: 43 */     this.t2.put("pattern object 1", "Alpha");
/*  36: 44 */     this.t2.put("pattern object 2", "Charlie");
/*  37: 45 */     this.t2.put("pattern object 3", "Bravo");
/*  38:    */     
/*  39: 47 */     this.t3.put("pattern object 1", "Bravo");
/*  40: 48 */     this.t3.put("pattern object 2", "Alpha");
/*  41: 49 */     this.t3.put("pattern object 3", "Charlie");
/*  42:    */     
/*  43: 51 */     this.t4.put("pattern object 1", "Bravo");
/*  44: 52 */     this.t4.put("pattern object 2", "Charlie");
/*  45: 53 */     this.t4.put("pattern object 3", "Alpha");
/*  46:    */     
/*  47: 55 */     this.t5.put("Alpha", "pattern object 3");
/*  48: 56 */     this.t5.put("Bravo", "pattern object 1");
/*  49: 57 */     this.t5.put("Charlie", "pattern object 2");
/*  50:    */     
/*  51: 59 */     this.t6.put("Alpha", "pattern object 3");
/*  52: 60 */     this.t6.put("Bravo", "pattern object 2");
/*  53: 61 */     this.t6.put("Charlie", "pattern object 1");
/*  54:    */     
/*  55: 63 */     this.transforms.add(this.t1);
/*  56: 64 */     this.transforms.add(this.t2);
/*  57: 65 */     this.transforms.add(this.t3);
/*  58: 66 */     this.transforms.add(this.t4);
/*  59: 67 */     this.transforms.add(this.t5);
/*  60: 68 */     this.transforms.add(this.t6);
/*  61:    */     
/*  62: 70 */     Pattern pattern = new Pattern("moves left");
/*  63: 71 */     Ladder ladder = new Ladder();
/*  64: 72 */     ladder.addTransition("pattern object 1", "moving left", "appear");
/*  65: 73 */     pattern.addLadder(ladder);
/*  66: 74 */     addPattern(pattern);
/*  67:    */     
/*  68: 76 */     pattern = new Pattern("moves right");
/*  69: 77 */     ladder = new Ladder();
/*  70: 78 */     ladder.addTransition("pattern object 1", "moving right", "appear");
/*  71: 79 */     pattern.addLadder(ladder);
/*  72: 80 */     addPattern(pattern);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void addPattern(Pattern p)
/*  76:    */   {
/*  77: 85 */     add(p);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String match(Ladders event)
/*  81:    */   {
/*  82:    */     Iterator localIterator2;
/*  83: 89 */     for (Iterator localIterator1 = iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  84:    */     {
/*  85: 89 */       Pattern pattern = (Pattern)localIterator1.next();
/*  86: 90 */       localIterator2 = this.transforms.iterator(); continue;HashMap<String, String> t = (HashMap)localIterator2.next();
/*  87: 91 */       if (match(t, event, pattern))
/*  88:    */       {
/*  89: 92 */         Mark.betterSay(new Object[] {"Found pattern:", t.get("pattern object 1"), pattern.getName() });
/*  90: 93 */         return (String)t.get("pattern object 1") + " " + pattern.getName();
/*  91:    */       }
/*  92:    */     }
/*  93: 97 */     Mark.betterSay(new Object[] {"No match" });
/*  94: 98 */     return null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private boolean match(HashMap<String, String> transform, Ladders event, Pattern pattern)
/*  98:    */   {
/*  99:102 */     int eventLadderCount = event.size();
/* 100:103 */     for (int i = 0; i < pattern.size(); i++)
/* 101:    */     {
/* 102:104 */       Ladder pLadder = (Ladder)pattern.get(i);
/* 103:105 */       int eventIndex = eventLadderCount - i - 1;
/* 104:106 */       if (eventIndex < 0) {
/* 105:107 */         return false;
/* 106:    */       }
/* 107:109 */       Ladder eLadder = (Ladder)event.get(eventIndex);
/* 108:    */       Iterator localIterator2;
/* 109:110 */       for (Iterator localIterator1 = pLadder.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 110:    */       {
/* 111:110 */         String object = (String)localIterator1.next();
/* 112:111 */         HashMap<String, String> map = (HashMap)pLadder.get(object);
/* 113:112 */         localIterator2 = map.keySet().iterator(); continue;String label = (String)localIterator2.next();
/* 114:113 */         String patternValue = pLadder.get(object, label);
/* 115:114 */         String eventValue = eLadder.get((String)transform.get(object), label);
/* 116:115 */         if (patternValue != eventValue) {
/* 117:116 */           return false;
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:121 */     return true;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void main(String[] ignore)
/* 125:    */   {
/* 126:125 */     Ladders event = new Ladders();
/* 127:126 */     Ladder ladder = new Ladder();
/* 128:127 */     ladder.addTransition("Bravo", "moving right", "appear");
/* 129:128 */     event.addLadder(ladder);
/* 130:    */     
/* 131:130 */     getPatterns().match(event);
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     transitionSpace.Patterns
 * JD-Core Version:    0.7.0.1
 */