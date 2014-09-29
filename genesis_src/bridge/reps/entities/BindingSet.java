/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Vector;
/*  5:   */ 
/*  6:   */ public class BindingSet
/*  7:   */   extends Vector<Binding>
/*  8:   */ {
/*  9: 6 */   int sameTypeCount = 0;
/* 10: 8 */   int matchedVariableCount = 0;
/* 11:10 */   int forcedTypeCount = 0;
/* 12:   */   
/* 13:   */   public boolean add(Binding b)
/* 14:   */   {
/* 15:13 */     boolean result = super.add(b);
/* 16:14 */     incrementMatchedVariableCount();
/* 17:15 */     Vector<String> variableVector = ((Entity)b.getVariable()).getTypes();
/* 18:16 */     Vector<String> valueVector = ((Entity)b.getValue()).getTypes();
/* 19:17 */     this.sameTypeCount += vectorIntersection(variableVector, valueVector).size();
/* 20:18 */     return result;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object getValue(Object variable)
/* 24:   */   {
/* 25:22 */     for (int i = 0; i < size(); i++)
/* 26:   */     {
/* 27:23 */       Binding binding = (Binding)elementAt(i);
/* 28:24 */       if (variable.equals(binding.getVariable())) {
/* 29:25 */         return binding.getValue();
/* 30:   */       }
/* 31:   */     }
/* 32:28 */     return null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public BindingSet() {}
/* 36:   */   
/* 37:   */   public BindingSet(Collection<Binding> c)
/* 38:   */   {
/* 39:37 */     super(c);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public BindingSet(int ic)
/* 43:   */   {
/* 44:41 */     super(ic);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public BindingSet(int ic, int ci)
/* 48:   */   {
/* 49:45 */     super(ic, ci);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object clone()
/* 53:   */   {
/* 54:49 */     BindingSet bs = new BindingSet(this);
/* 55:50 */     bs.sameTypeCount = this.sameTypeCount;
/* 56:51 */     bs.matchedVariableCount = this.matchedVariableCount;
/* 57:52 */     bs.forcedTypeCount = this.forcedTypeCount;
/* 58:53 */     return bs;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getScore()
/* 62:   */   {
/* 63:57 */     return this.sameTypeCount;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String toString()
/* 67:   */   {
/* 68:61 */     String result = "\nMatch succeeded\n";
/* 69:62 */     if (this.forcedTypeCount > 0) {
/* 70:63 */       result = result + "Forced types: " + this.forcedTypeCount + "\n";
/* 71:   */     }
/* 72:65 */     if (this.matchedVariableCount > 0) {
/* 73:66 */       result = result + "Matched variables: " + this.matchedVariableCount + "\n";
/* 74:   */     }
/* 75:68 */     if (this.sameTypeCount > 0) {
/* 76:69 */       result = result + "Matched types in structures: " + this.sameTypeCount + "\n";
/* 77:   */     }
/* 78:72 */     for (int i = 0; i < size(); i++)
/* 79:   */     {
/* 80:73 */       Binding binding = (Binding)elementAt(i);
/* 81:74 */       result = result + binding.toString() + '\n';
/* 82:   */     }
/* 83:76 */     return result;
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void incrementForcedTypeCount(int i)
/* 87:   */   {
/* 88:80 */     this.forcedTypeCount += i;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public void incrementMatchedVariableCount()
/* 92:   */   {
/* 93:84 */     this.matchedVariableCount += 1;
/* 94:   */   }
/* 95:   */   
/* 96:   */   private static <T> Vector<T> vectorIntersection(Vector<T> v1, Vector<T> v2)
/* 97:   */   {
/* 98:88 */     Vector<T> result = new Vector();
/* 99:89 */     for (int i = 0; i < v1.size(); i++)
/* :0:   */     {
/* :1:90 */       T object = v1.elementAt(i);
/* :2:91 */       if (v2.contains(object)) {
/* :3:92 */         result.add(object);
/* :4:   */       }
/* :5:   */     }
/* :6:95 */     return result;
/* :7:   */   }
/* :8:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.BindingSet
 * JD-Core Version:    0.7.0.1
 */