/*   1:    */ package m2.datatypes;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Vector;
/*  11:    */ import m2.M2;
/*  12:    */ import magicLess.utils.EntityUtils;
/*  13:    */ 
/*  14:    */ public class Chain
/*  15:    */   extends ArrayList<DoubleBundle>
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 2853206012463972242L;
/*  18: 34 */   private List<Entity> inputs = new ArrayList();
/*  19:    */   
/*  20:    */   public Chain(Entity t)
/*  21:    */   {
/*  22: 40 */     for (Entity el : flattenThing(t))
/*  23:    */     {
/*  24: 42 */       DoubleBundle db2 = new DoubleBundle();
/*  25:    */       
/*  26: 44 */       Thread newThread = null;
/*  27: 45 */       if (el.getThread("thing") != null) {
/*  28: 46 */         newThread = new Thread(el.getThread("thing"));
/*  29:    */       } else {
/*  30: 49 */         newThread = new Thread(el.getPrimedThread());
/*  31:    */       }
/*  32: 52 */       Thread ft = el.getThread("feature");
/*  33: 53 */       if ((ft != null) && (ft.contains("not"))) {
/*  34: 54 */         db2.addNeg(newThread);
/*  35:    */       } else {
/*  36: 57 */         db2.addPos(newThread);
/*  37:    */       }
/*  38: 59 */       add(db2);
/*  39:    */     }
/*  40: 62 */     this.inputs.add(t);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void updateHistory(Entity t)
/*  44:    */   {
/*  45: 83 */     this.inputs.add(t);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<Entity> getInputList()
/*  49:    */   {
/*  50: 87 */     return new ArrayList(this.inputs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static List<Entity> flattenThing(Entity thing)
/*  54:    */   {
/*  55: 92 */     if (thing == null) {
/*  56: 92 */       return null;
/*  57:    */     }
/*  58: 93 */     List<Entity> list = new ArrayList();
/*  59: 94 */     list.add(thing);
/*  60: 95 */     if ((thing instanceof Sequence))
/*  61:    */     {
/*  62: 96 */       Sequence sequence = (Sequence)thing;
/*  63: 97 */       Vector<Entity> v = sequence.getElements();
/*  64: 98 */       for (int i = 0; i < v.size(); i++) {
/*  65: 99 */         list.addAll(flattenThing((Entity)v.elementAt(i)));
/*  66:    */       }
/*  67:    */     }
/*  68:102 */     else if ((thing instanceof Relation))
/*  69:    */     {
/*  70:103 */       Relation relation = (Relation)thing;
/*  71:104 */       list.addAll(flattenThing(relation.getSubject()));
/*  72:105 */       list.addAll(flattenThing(relation.getObject()));
/*  73:    */     }
/*  74:107 */     else if ((thing instanceof Function))
/*  75:    */     {
/*  76:108 */       Function derivative = (Function)thing;
/*  77:109 */       list.addAll(flattenThing(derivative.getSubject()));
/*  78:    */     }
/*  79:111 */     return list;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getWeight()
/*  83:    */   {
/*  84:116 */     return this.inputs.size();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Chain clone()
/*  88:    */   {
/*  89:133 */     Chain clone = new Chain((Entity)this.inputs.get(0));
/*  90:134 */     for (int i = 0; i < size(); i++) {
/*  91:135 */       clone.set(i, ((DoubleBundle)get(i)).clone());
/*  92:    */     }
/*  93:137 */     clone.inputs = new ArrayList(this.inputs);
/*  94:    */     
/*  95:139 */     return clone;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean overlaps(Chain c2)
/*  99:    */   {
/* 100:184 */     if (size() != c2.size()) {
/* 101:185 */       return false;
/* 102:    */     }
/* 103:187 */     for (int i = 0; i < size(); i++) {
/* 104:188 */       if (((DoubleBundle)get(i)).getDistance((DoubleBundle)c2.get(i)) != 0) {
/* 105:189 */         return false;
/* 106:    */       }
/* 107:    */     }
/* 108:192 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Chain mergeChain(Chain c)
/* 112:    */   {
/* 113:203 */     M2.m2assert(size() == c.size(), "LLMerger.mergeChains assertion failed: Chain are different sizes!");
/* 114:204 */     Chain result = clone();
/* 115:    */     DoubleBundle c2db;
/* 116:206 */     for (int i = 0; i < size(); i++)
/* 117:    */     {
/* 118:207 */       c2db = (DoubleBundle)c.get(i);
/* 119:208 */       DoubleBundle resultdb = (DoubleBundle)result.get(i);
/* 120:209 */       for (Thread t : c2db.getPosSet()) {
/* 121:210 */         resultdb.addPos(t);
/* 122:    */       }
/* 123:212 */       for (Thread t : c2db.getNegSet()) {
/* 124:213 */         resultdb.addNeg(t);
/* 125:    */       }
/* 126:    */     }
/* 127:223 */     for (Entity t : c.getInputList()) {
/* 128:224 */       result.updateHistory(t);
/* 129:    */     }
/* 130:230 */     return result;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getRepType()
/* 134:    */   {
/* 135:237 */     return (String)EntityUtils.getRepType((Entity)getInputList().get(0));
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.datatypes.Chain
 * JD-Core Version:    0.7.0.1
 */