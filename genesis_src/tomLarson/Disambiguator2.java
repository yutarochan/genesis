/*   1:    */ package tomLarson;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import connections.AbstractWiredBox;
/*   9:    */ import connections.Connections;
/*  10:    */ import connections.Ports;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Vector;
/*  16:    */ import javax.swing.JCheckBox;
/*  17:    */ 
/*  18:    */ public class Disambiguator2
/*  19:    */   extends AbstractWiredBox
/*  20:    */ {
/*  21:    */   private Entity input;
/*  22:    */   private JCheckBox useDisambiguator;
/*  23:    */   private DisambiguatorMemory disambiguatorMemory;
/*  24:    */   public static final String VIEW = "VIEW";
/*  25:    */   public static final String VIEWBUNDLE = "VIEWBUNDLE";
/*  26:    */   private static final boolean debug = false;
/*  27:    */   
/*  28:    */   public Disambiguator2()
/*  29:    */   {
/*  30: 47 */     Connections.getPorts(this).addSignalProcessor("process");
/*  31: 48 */     this.disambiguatorMemory = new DisambiguatorMemory();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Disambiguator2(JCheckBox useDisambiguator)
/*  35:    */   {
/*  36: 53 */     this.useDisambiguator = useDisambiguator;
/*  37: 54 */     Connections.getPorts(this).addSignalProcessor("process");
/*  38: 55 */     this.disambiguatorMemory = new DisambiguatorMemory();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void process(Object o)
/*  42:    */   {
/*  43: 67 */     this.input = null;
/*  44: 68 */     if ((o instanceof Entity))
/*  45:    */     {
/*  46: 71 */       this.input = ((Entity)o);
/*  47: 72 */       if (this.useDisambiguator.isSelected())
/*  48:    */       {
/*  49: 76 */         recursivelyDisambiguate(this.input);
/*  50:    */         
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 83 */         transmit(this.input);
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 87 */         transmit(this.input);
/*  61:    */       }
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 91 */       System.err.println(o.getClass());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void recursivelyDisambiguate(Entity input)
/*  70:    */   {
/*  71: 97 */     if ((input.isA("trajectory")) && (input.relationP())) {
/*  72: 98 */       disambiguate(input);
/*  73:    */     }
/*  74:100 */     if (input.functionP())
/*  75:    */     {
/*  76:101 */       recursivelyDisambiguate(((Function)input).getSubject());
/*  77:    */     }
/*  78:103 */     else if (input.relationP())
/*  79:    */     {
/*  80:104 */       recursivelyDisambiguate(((Function)input).getSubject());
/*  81:105 */       recursivelyDisambiguate(((Function)input).getObject());
/*  82:    */     }
/*  83:107 */     else if (input.sequenceP())
/*  84:    */     {
/*  85:108 */       Vector v = ((Sequence)input).getElements();
/*  86:109 */       for (Object o : v) {
/*  87:110 */         recursivelyDisambiguate((Entity)o);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void transmit(Entity t)
/*  93:    */   {
/*  94:123 */     Connections.getPorts(this).transmit(t);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void transmit(String s, ThreadTree t)
/*  98:    */   {
/*  99:127 */     Connections.getPorts(this).transmit(s, t);
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void disambiguate(Entity t)
/* 103:    */   {
/* 104:135 */     if (t.isA("trajectory"))
/* 105:    */     {
/* 106:136 */       boolean report = false;
/* 107:137 */       System.out.println("Found a trajectory to disambiguate");
/* 108:138 */       Bundle bundle = t.getSubject().getBundle();
/* 109:139 */       transmit("VIEWBUNDLE", ThreadTree.makeThreadTree(bundle));
/* 110:    */       
/* 111:141 */       Thread thread = t.getSubject().getPrimedThread();
/* 112:142 */       ThreadTree tree = this.disambiguatorMemory.getThreadTree(t.getType());
/* 113:143 */       Type type = tree.getImpactofThread(thread);
/* 114:144 */       double score = type.getWeight();
/* 115:145 */       double max = score;
/* 116:146 */       for (int i = 0; i < bundle.size(); i++) {
/* 117:147 */         if (tree != null)
/* 118:    */         {
/* 119:150 */           type = tree.getImpactofThread((Thread)bundle.get(i));
/* 120:151 */           score = type.getWeight();
/* 121:152 */           if (score > max)
/* 122:    */           {
/* 123:153 */             thread = (Thread)bundle.get(i);
/* 124:154 */             max = score;
/* 125:155 */             report = true;
/* 126:    */           }
/* 127:    */         }
/* 128:    */       }
/* 129:159 */       this.disambiguatorMemory.addThread(t.getType(), thread);
/* 130:160 */       Entity newThing = new Entity();
/* 131:161 */       Bundle newBundle = new Bundle(thread);
/* 132:162 */       newThing.setBundle(newBundle);
/* 133:163 */       t.setSubject(newThing);
/* 134:164 */       if (report)
/* 135:    */       {
/* 136:165 */         for (Vector x : bundle) {
/* 137:166 */           System.out.println(x);
/* 138:    */         }
/* 139:168 */         Connections.getPorts(this).transmit("disambiguated", newThing);
/* 140:    */       }
/* 141:    */     }
/* 142:181 */     transmit("VIEW", this.disambiguatorMemory.getThreadTree(t.getType()));
/* 143:    */   }
/* 144:    */   
/* 145:    */   private int indexOfBestThread(Map<Thread, Integer> threadMap, Map<Thread, Integer> posMap)
/* 146:    */   {
/* 147:189 */     int min = 2147483647;
/* 148:190 */     int best = 0;
/* 149:191 */     boolean difference = false;
/* 150:192 */     assert (threadMap.keySet() != null);
/* 151:193 */     Iterator<Integer> vals = threadMap.values().iterator();
/* 152:194 */     int val = ((Integer)vals.next()).intValue();
/* 153:195 */     while (vals.hasNext()) {
/* 154:196 */       if (((Integer)vals.next()).intValue() != val) {
/* 155:197 */         difference = true;
/* 156:    */       }
/* 157:    */     }
/* 158:201 */     for (Thread thread : threadMap.keySet()) {
/* 159:202 */       if ((((Integer)threadMap.get(thread)).intValue() < min) && (!thread.contains("unknownWord")))
/* 160:    */       {
/* 161:203 */         min = ((Integer)threadMap.get(thread)).intValue();
/* 162:204 */         best = ((Integer)posMap.get(thread)).intValue();
/* 163:    */       }
/* 164:    */     }
/* 165:207 */     if (!difference) {
/* 166:208 */       return -1;
/* 167:    */     }
/* 168:211 */     return best;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static void main(String[] args)
/* 172:    */   {
/* 173:247 */     Thread t1 = new Thread();
/* 174:248 */     Thread t2 = new Thread();
/* 175:249 */     t1.add("Thing");
/* 176:250 */     t1.add("Person");
/* 177:251 */     t1.add("Tom");
/* 178:252 */     t1.add("Thing");
/* 179:253 */     t2.add("Walrus");
/* 180:254 */     t2.add("Person");
/* 181:255 */     t2.add("Monkey");
/* 182:256 */     t2.add("Paul");
/* 183:257 */     t2.add("George");
/* 184:258 */     t2.add("Ringo");
/* 185:259 */     System.out.println(t1);
/* 186:260 */     System.out.println(t2);
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.Disambiguator2
 * JD-Core Version:    0.7.0.1
 */