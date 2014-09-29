/*  1:   */ package matthewFay.Depricated;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Relation;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ 
/*  8:   */ @Deprecated
/*  9:   */ public class Persona
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 3L;
/* 13:16 */   private ArrayList<Relation> rules = null;
/* 14:17 */   private ArrayList<Sequence> reflections = null;
/* 15:18 */   private int version = 0;
/* 16:20 */   private String name = "default";
/* 17:   */   
/* 18:   */   public void setName(String newName)
/* 19:   */   {
/* 20:22 */     this.name = newName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:25 */     return this.name;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Persona(Sequence rules, Sequence reflections)
/* 29:   */   {
/* 30:29 */     this.rules = new ArrayList();
/* 31:30 */     this.reflections = new ArrayList();
/* 32:31 */     addRules(rules);
/* 33:32 */     addReflections(reflections);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Persona()
/* 37:   */   {
/* 38:36 */     this.rules = new ArrayList();
/* 39:37 */     this.reflections = new ArrayList();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String toString()
/* 43:   */   {
/* 44:41 */     String s = this.rules.toString() + "\n" + this.reflections.toString();
/* 45:42 */     return s;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Sequence getRules()
/* 49:   */   {
/* 50:46 */     Sequence gotRules = new Sequence();
/* 51:47 */     gotRules.addType(this.name);
/* 52:48 */     for (Relation r : this.rules) {
/* 53:49 */       gotRules.addElement(r.cloneForResolver());
/* 54:   */     }
/* 55:51 */     return gotRules;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void addRule(Relation rule)
/* 59:   */   {
/* 60:55 */     this.rules.add((Relation)rule.cloneForResolver());
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void addRules(Sequence rules)
/* 64:   */   {
/* 65:59 */     for (int i = 0; i < rules.getNumberOfChildren(); i++) {
/* 66:60 */       addRule((Relation)rules.getElement(i));
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public Sequence getReflections()
/* 71:   */   {
/* 72:65 */     Sequence gotReflections = new Sequence();
/* 73:66 */     gotReflections.addType(this.name);
/* 74:67 */     for (Sequence s : this.reflections) {
/* 75:68 */       gotReflections.addElement(s.cloneForResolver());
/* 76:   */     }
/* 77:70 */     return gotReflections;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void addReflection(Sequence reflection)
/* 81:   */   {
/* 82:74 */     this.reflections.add((Sequence)reflection.cloneForResolver());
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void addReflections(Sequence reflections)
/* 86:   */   {
/* 87:78 */     for (int i = 0; i < reflections.getNumberOfChildren(); i++) {
/* 88:79 */       addReflection((Sequence)reflections.getElement(i));
/* 89:   */     }
/* 90:81 */     this.version += 1;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public int getVersion()
/* 94:   */   {
/* 95:85 */     return this.version;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public void markVersion()
/* 99:   */   {
/* :0:89 */     this.version += 1;
/* :1:   */   }
/* :2:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.Persona
 * JD-Core Version:    0.7.0.1
 */