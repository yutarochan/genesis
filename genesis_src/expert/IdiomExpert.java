/*   1:    */ package expert;
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
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import links.words.BundleGenerator;
/*  15:    */ import links.words.BundleGenerator.Implementation;
/*  16:    */ import text.Punctuator;
/*  17:    */ import utils.Mark;
/*  18:    */ 
/*  19:    */ public class IdiomExpert
/*  20:    */   extends AbstractWiredBox
/*  21:    */ {
/*  22: 25 */   private boolean debug = false;
/*  23: 31 */   public static String STORY_PROCESSOR_PORT = "story";
/*  24: 33 */   public static String START = "start";
/*  25: 35 */   public static String DESCRIBE = "describe";
/*  26: 39 */   public static String STOP = "stop";
/*  27: 41 */   public static String START_PARSER_PART = "parser";
/*  28:    */   
/*  29:    */   public IdiomExpert()
/*  30:    */   {
/*  31: 44 */     setName("Idiom expert");
/*  32: 45 */     Connections.getPorts(this).addSignalProcessor("processIdioms");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void processIdioms(Object x)
/*  36:    */   {
/*  37: 51 */     if (!(x instanceof Sequence)) {
/*  38: 52 */       return;
/*  39:    */     }
/*  40: 54 */     Sequence s = (Sequence)x;
/*  41:    */     
/*  42: 56 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Idiom handler sees", s.asStringWithIndexes() });
/*  43: 57 */     for (Entity element : s.getElements()) {
/*  44: 58 */       if (element.relationP())
/*  45:    */       {
/*  46: 59 */         Relation relation = (Relation)element;
/*  47: 77 */         if (threadRestriction(relation))
/*  48:    */         {
/*  49: 78 */           Entity restriction = relation.getSubject();
/*  50: 79 */           restrictMeaning(restriction);
/*  51: 80 */           transmit(restriction);
/*  52:    */         }
/*  53:    */         else
/*  54:    */         {
/*  55: 84 */           transmit(element);
/*  56:    */         }
/*  57:    */       }
/*  58: 88 */       else if (element.functionP())
/*  59:    */       {
/*  60: 89 */         Function d = (Function)element;
/*  61: 90 */         if ((!d.isAPrimed("description")) || (!d.getSubject().isAPrimed("perspective"))) {
/*  62: 93 */           if (d.isAPrimed("description"))
/*  63:    */           {
/*  64: 94 */             Entity subject = d.getSubject();
/*  65: 95 */             Connections.getPorts(this).transmit(DESCRIBE, d.getSubject().getType());
/*  66:    */           }
/*  67: 98 */           else if (threadRestriction(d))
/*  68:    */           {
/*  69: 99 */             restrictMeaning(d);
/*  70:100 */             transmit(d);
/*  71:    */           }
/*  72:    */           else
/*  73:    */           {
/*  74:104 */             transmit(element);
/*  75:    */           }
/*  76:    */         }
/*  77:    */       }
/*  78:    */       else
/*  79:    */       {
/*  80:109 */         transmit(element);
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void restrictMeaning(Entity t)
/*  86:    */   {
/*  87:119 */     Function derivative = (Function)t;
/*  88:120 */     Relation relation = (Relation)derivative.getSubject();
/*  89:121 */     String type = relation.getSubject().getType();
/*  90:122 */     BundleGenerator.getInstance().getBundleMap().put(type, null);
/*  91:123 */     Bundle bundle = BundleGenerator.getInstance().getRawBundle(type);
/*  92:124 */     Mark.say(new Object[] {"Raw bundle is", bundle });
/*  93:125 */     String restriction = relation.getObject().getType();
/*  94:126 */     restriction = Punctuator.removeQuotes(restriction);
/*  95:127 */     relation.getObject().setName(restriction);
/*  96:128 */     ArrayList<Thread> winners = new ArrayList();
/*  97:129 */     for (Thread thread : bundle) {
/*  98:130 */       if (thread.contains(restriction)) {
/*  99:131 */         winners.add(thread);
/* 100:    */       }
/* 101:    */     }
/* 102:134 */     int size = winners.size();
/* 103:135 */     if (size == 0)
/* 104:    */     {
/* 105:136 */       Mark.say(new Object[] {"Ugh, you asked to restrict", relation.getSubject().getType(), "to", restriction, "but there is no thread with the restriction" });
/* 106:137 */       Mark.say(new Object[] {"Setting", type, "to", bundle });
/* 107:138 */       BundleGenerator.getInstance().getBundleMap().put(type, bundle);
/* 108:139 */       return;
/* 109:    */     }
/* 110:141 */     if (size > 1)
/* 111:    */     {
/* 112:142 */       Mark.say(new Object[] {"Ugh, you asked to restrict", relation.getSubject().getType(), "to", restriction, "but there is more than one thread with the restriction" });
/* 113:143 */       Mark.say(new Object[] {"I assume a", type, "is a", winners.get(0) });
/* 114:    */     }
/* 115:    */     else
/* 116:    */     {
/* 117:146 */       Mark.say(new Object[] {"Ok, the winning thread is", winners.get(0) });
/* 118:    */     }
/* 119:148 */     Bundle newBundle = new Bundle();
/* 120:149 */     newBundle.add((Thread)winners.get(0));
/* 121:150 */     Mark.say(new Object[] {"Setting", type, "to", newBundle });
/* 122:151 */     BundleGenerator.getInstance().getBundleMap().put(type, newBundle);
/* 123:    */   }
/* 124:    */   
/* 125:    */   private boolean threadRestriction(Entity relation)
/* 126:    */   {
/* 127:157 */     if ((relation.relationP("has_attitude")) && 
/* 128:158 */       (relation.getObject().entityP("imperative")) && (relation.getSubject().functionP("assume")) && 
/* 129:159 */       (relation.getSubject().getSubject().relationP("mean")))
/* 130:    */     {
/* 131:160 */       Mark.say(new Object[] {"Using new start now.  Fix me" });
/* 132:161 */       return true;
/* 133:    */     }
/* 134:165 */     if ((relation.functionP("assume")) && 
/* 135:166 */       (relation.getSubject().relationP("mean"))) {
/* 136:167 */       return true;
/* 137:    */     }
/* 138:170 */     return false;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void transmit(Entity element)
/* 142:    */   {
/* 143:174 */     Mark.say(
/* 144:    */     
/* 145:176 */       new Object[] { Boolean.valueOf(this.debug), "Passing through", element.asStringWithIndexes() });Connections.getPorts(this).transmit(element);
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.IdiomExpert
 * JD-Core Version:    0.7.0.1
 */