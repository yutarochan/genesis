/*   1:    */ package tomLarson;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import connections.AbstractWiredBox;
/*  10:    */ import connections.Connections;
/*  11:    */ import connections.Ports;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.swing.JCheckBox;
/*  15:    */ import m2.InputTracker;
/*  16:    */ import m2.M2;
/*  17:    */ import m2.Mem;
/*  18:    */ import memory.utilities.Distances;
/*  19:    */ 
/*  20:    */ public class Disambiguator3
/*  21:    */   extends AbstractWiredBox
/*  22:    */ {
/*  23:    */   private Entity input;
/*  24:    */   private JCheckBox useDisambiguator;
/*  25:    */   private static final boolean debug = false;
/*  26:    */   
/*  27:    */   public Disambiguator3()
/*  28:    */   {
/*  29: 43 */     Connections.getPorts(this).addSignalProcessor("process");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Disambiguator3(JCheckBox useDisambiguator)
/*  33:    */   {
/*  34: 47 */     this();
/*  35: 48 */     this.useDisambiguator = useDisambiguator;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void process(Object o)
/*  39:    */   {
/*  40: 62 */     this.input = null;
/*  41: 63 */     if ((o instanceof Entity))
/*  42:    */     {
/*  43: 66 */       this.input = ((Entity)o);
/*  44: 67 */       InputTracker.addTopLevelFrame(this.input);
/*  45: 68 */       if ((this.useDisambiguator == null) || (this.useDisambiguator.isSelected()))
/*  46:    */       {
/*  47: 72 */         if ((this.input.sequenceP()) && (this.input.isAPrimed("semantic-interpretation"))) {
/*  48: 73 */           for (Entity e : this.input.getElements()) {
/*  49: 74 */             processElement(e);
/*  50:    */           }
/*  51:    */         }
/*  52: 77 */         transmit(this.input);
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56: 80 */         transmit(this.input);
/*  57:    */       }
/*  58:    */     }
/*  59:    */     else
/*  60:    */     {
/*  61: 84 */       System.err.println(o.getClass());
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void processElement(Entity e)
/*  66:    */   {
/*  67: 89 */     Entity pivot = findPivot(e);
/*  68: 91 */     if (pivot != null)
/*  69:    */     {
/*  70: 92 */       Bundle bundle = pivot.getBundle();
/*  71: 93 */       double closestDistance = -1.0D;
/*  72: 94 */       int closestIndex = 0;
/*  73: 95 */       for (int i = 0; i < bundle.size(); i++)
/*  74:    */       {
/*  75: 96 */         Thread primedThread = (Thread)bundle.get(i);
/*  76:    */         
/*  77:    */ 
/*  78: 99 */         Bundle primedBundle = new Bundle();
/*  79:100 */         primedBundle.add(primedThread);
/*  80:101 */         pivot.setBundle(primedBundle);
/*  81:    */         
/*  82:103 */         double distance = evaluation(e, primedThread);
/*  83:104 */         if (closestDistance < 0.0D)
/*  84:    */         {
/*  85:105 */           closestDistance = distance;
/*  86:106 */           System.out.println("Candidate: A" + i + ", " + distance);
/*  87:    */         }
/*  88:108 */         else if (distance < closestDistance)
/*  89:    */         {
/*  90:109 */           closestDistance = distance;
/*  91:110 */           System.out.println("Candidate B: " + i + ", " + distance);
/*  92:111 */           closestIndex = i;
/*  93:    */         }
/*  94:    */       }
/*  95:119 */       Thread primedThread = (Thread)bundle.get(closestIndex);
/*  96:120 */       Bundle primedBundle = new Bundle();
/*  97:121 */       primedBundle.add(primedThread);
/*  98:122 */       pivot.setBundle(primedBundle);
/*  99:124 */       if (closestIndex > 0) {
/* 100:128 */         Connections.getPorts(this).transmit("disambiguated", pivot);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public double evaluation(Entity e, Thread primed)
/* 106:    */   {
/* 107:137 */     List<Entity> nearbyList = M2.getMem().neighbors(e);
/* 108:139 */     if (nearbyList.size() > 0) {
/* 109:140 */       for (Entity t : nearbyList) {
/* 110:141 */         if (t != e)
/* 111:    */         {
/* 112:145 */           double d = Distances.distance(t, e);
/* 113:146 */           System.out.println("Distance of " + t.getName() + " is " + d);
/* 114:147 */           return d;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:154 */     return -1.0D;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private Entity findPivot(Entity e)
/* 122:    */   {
/* 123:161 */     if (e.relationP())
/* 124:    */     {
/* 125:162 */       Entity r = findPivot(((Relation)e).getSubject());
/* 126:163 */       if (r != null) {
/* 127:164 */         return r;
/* 128:    */       }
/* 129:167 */       return findPivot(((Relation)e).getObject());
/* 130:    */     }
/* 131:170 */     if (e.functionP()) {
/* 132:171 */       return findPivot(((Function)e).getSubject());
/* 133:    */     }
/* 134:173 */     if ((e.entityP()) && 
/* 135:174 */       (e.getBundle().size() > 1)) {
/* 136:175 */       return e;
/* 137:    */     }
/* 138:178 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void transmit(Entity t)
/* 142:    */   {
/* 143:182 */     Connections.getPorts(this).transmit(t);
/* 144:183 */     if (t.sequenceP())
/* 145:    */     {
/* 146:184 */       Sequence s = (Sequence)t;
/* 147:185 */       for (Entity element : s.getElements()) {
/* 148:186 */         Connections.getPorts(this).transmit("chain", element);
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static void main(String[] args)
/* 154:    */   {
/* 155:195 */     Thread t1 = new Thread("Thing");
/* 156:196 */     Thread t2 = new Thread("Thing");
/* 157:197 */     t1.add("Person");
/* 158:198 */     t1.add("Tom");
/* 159:199 */     t1.add("Thing");
/* 160:200 */     t2.add("Walrus");
/* 161:201 */     t2.add("Person");
/* 162:202 */     t2.add("Monkey");
/* 163:203 */     t2.add("Paul");
/* 164:204 */     t2.add("George");
/* 165:205 */     t2.add("Ringo");
/* 166:206 */     System.out.println(t1);
/* 167:207 */     System.out.println(t2);
/* 168:208 */     Entity thing = new Entity();
/* 169:209 */     Bundle bundle = new Bundle(t1);
/* 170:210 */     thing.setBundle(bundle);
/* 171:211 */     System.out.println(new Disambiguator3().evaluation(thing, t2));
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.Disambiguator3
 * JD-Core Version:    0.7.0.1
 */