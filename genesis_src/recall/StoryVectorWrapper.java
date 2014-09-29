/*  1:   */ package recall;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import storyProcessor.ReflectionAnalysis;
/*  6:   */ import storyProcessor.ReflectionDescription;
/*  7:   */ 
/*  8:   */ public class StoryVectorWrapper
/*  9:   */ {
/* 10:   */   private String title;
/* 11:   */   private Sequence story;
/* 12:21 */   private int length2 = 0;
/* 13:23 */   double length = 0.0D;
/* 14:25 */   HashMap<String, Integer> vector = new HashMap();
/* 15:   */   
/* 16:   */   public StoryVectorWrapper(ReflectionAnalysis analysis)
/* 17:   */   {
/* 18:28 */     setTitle(analysis.getStoryName());
/* 19:29 */     this.story = analysis.getStory();
/* 20:30 */     for (ReflectionDescription completion : analysis.getReflectionDescriptions()) {
/* 21:31 */       incrementValue(completion.getName());
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setTitle(String title)
/* 26:   */   {
/* 27:39 */     this.title = title;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean equals(Object o)
/* 31:   */   {
/* 32:43 */     if ((o instanceof StoryVectorWrapper))
/* 33:   */     {
/* 34:44 */       StoryVectorWrapper s = (StoryVectorWrapper)o;
/* 35:45 */       if (getTitle().equalsIgnoreCase(s.getTitle())) {
/* 36:46 */         return true;
/* 37:   */       }
/* 38:   */     }
/* 39:49 */     return false;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getTitle()
/* 43:   */   {
/* 44:53 */     return this.title;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public HashMap<String, Integer> getMap()
/* 48:   */   {
/* 49:57 */     return this.vector;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void incrementValue(String word)
/* 53:   */   {
/* 54:61 */     Integer i = (Integer)this.vector.get(word);
/* 55:62 */     if (i == null)
/* 56:   */     {
/* 57:63 */       this.vector.put(word, Integer.valueOf(1));
/* 58:64 */       this.length2 += 1;
/* 59:   */     }
/* 60:   */     else
/* 61:   */     {
/* 62:67 */       this.length2 = ((int)(this.length2 - Math.pow(i.intValue(), 2.0D)));
/* 63:68 */       this.vector.put(word, Integer.valueOf(i.intValue() + 1));
/* 64:69 */       this.length2 = ((int)(this.length2 + Math.pow(i.intValue() + 1, 2.0D)));
/* 65:   */     }
/* 66:71 */     this.length = Math.sqrt(this.length2);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public int getValue(String word)
/* 70:   */   {
/* 71:75 */     Integer i = (Integer)this.vector.get(word);
/* 72:76 */     if (i == null) {
/* 73:77 */       return 0;
/* 74:   */     }
/* 75:80 */     return i.intValue();
/* 76:   */   }
/* 77:   */   
/* 78:   */   public double getLength()
/* 79:   */   {
/* 80:85 */     return this.length;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public Sequence getStory()
/* 84:   */   {
/* 85:89 */     return this.story;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public String toString()
/* 89:   */   {
/* 90:93 */     String result = "<" + getTitle().toString();
/* 91:94 */     for (String s : this.vector.keySet()) {
/* 92:95 */       result = result + " (" + s + ", " + this.vector.get(s) + ")";
/* 93:   */     }
/* 94:97 */     result = result + ">";
/* 95:98 */     return result;
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     recall.StoryVectorWrapper
 * JD-Core Version:    0.7.0.1
 */