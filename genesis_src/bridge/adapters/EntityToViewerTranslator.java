/*   1:    */ package bridge.adapters;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import bridge.reps.entities.Tracer;
/*  10:    */ import bridge.views.frameviews.classic.FrameBundle;
/*  11:    */ import java.awt.Color;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Vector;
/*  15:    */ 
/*  16:    */ public class EntityToViewerTranslator
/*  17:    */ {
/*  18: 13 */   public static final Object[] SHOW_ALL_THREADS = { new Object() };
/*  19: 17 */   public static final Object[] SHOW_DEFAULT_THREADS = { "thing", "ad_word", "description", "feature", "action", 
/*  20: 18 */     "owners" };
/*  21: 20 */   public static final Object[] SHOW_NO_THREADS = new Object[0];
/*  22: 22 */   private static boolean showIDNumber = true;
/*  23:    */   
/*  24:    */   public static FrameBundle translate(Entity thing)
/*  25:    */   {
/*  26: 29 */     return translate(thing, SHOW_DEFAULT_THREADS);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static FrameBundle translate(Entity entity, Object[] types)
/*  30:    */   {
/*  31: 36 */     if (entity == null) {
/*  32: 37 */       return null;
/*  33:    */     }
/*  34: 39 */     String name = entity.getType();
/*  35: 40 */     Vector<String> threads = new Vector();
/*  36:    */     
/*  37: 42 */     String decorations = "";
/*  38: 43 */     ArrayList<Object> features = entity.getFeatures();
/*  39: 45 */     if (features.size() > 0) {
/*  40: 46 */       decorations = decorations + "Features:";
/*  41:    */     }
/*  42: 48 */     for (Object o : features)
/*  43:    */     {
/*  44: 49 */       decorations = decorations + " ";
/*  45: 50 */       if ((o instanceof Entity)) {
/*  46: 51 */         decorations = decorations + ((Entity)o).getType();
/*  47:    */       } else {
/*  48: 54 */         decorations = decorations + o.toString();
/*  49:    */       }
/*  50:    */     }
/*  51: 58 */     for (String key : entity.getKeys()) {
/*  52: 59 */       if (!key.equals("feature"))
/*  53:    */       {
/*  54: 62 */         decorations = decorations + " (" + key + ": ";
/*  55: 63 */         Object o = entity.getProperty(key);
/*  56: 64 */         if (o != null)
/*  57:    */         {
/*  58: 67 */           if ((o instanceof Entity)) {
/*  59: 68 */             decorations = decorations + ((Entity)o).getType();
/*  60:    */           } else {
/*  61: 71 */             decorations = decorations + o.toString();
/*  62:    */           }
/*  63: 73 */           decorations = decorations + ")";
/*  64:    */         }
/*  65:    */       }
/*  66:    */     }
/*  67: 78 */     if ((showIDNumber) || (entity.entityP())) {
/*  68: 79 */       threads.add(entity.getIdentifier());
/*  69:    */     }
/*  70: 82 */     threads.add(decorations);
/*  71: 84 */     if (types == SHOW_ALL_THREADS) {
/*  72: 85 */       for (int i = 0; i < entity.getBundle().size(); i++)
/*  73:    */       {
/*  74: 86 */         Thread thread = (Thread)entity.getBundle().elementAt(i);
/*  75: 87 */         threads.add(thread.getString().trim());
/*  76:    */       }
/*  77:    */     } else {
/*  78: 92 */       for (int i = 0; i < SHOW_DEFAULT_THREADS.length; i++) {
/*  79: 93 */         for (int j = 0; j < entity.getBundle().size(); j++)
/*  80:    */         {
/*  81: 94 */           Thread thread = (Thread)entity.getBundle().elementAt(j);
/*  82: 95 */           if (thread.contains((String)SHOW_DEFAULT_THREADS[i])) {
/*  83: 96 */             threads.add(thread.getString().trim());
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:106 */     FrameBundle bundle = new FrameBundle(name, threads, entity.hasFeature("not"));
/*  89:107 */     if (types == SHOW_NO_THREADS) {
/*  90:108 */       bundle.setShowNoThreads(true);
/*  91:    */     }
/*  92:110 */     if ((entity instanceof Sequence))
/*  93:    */     {
/*  94:111 */       bundle.setBarColor(Color.black);
/*  95:112 */       Sequence sequence = (Sequence)entity;
/*  96:113 */       Vector v = sequence.getElements();
/*  97:114 */       for (int i = 0; i < v.size(); i++) {
/*  98:115 */         bundle.addFrameBundle(translate((Entity)v.elementAt(i), types));
/*  99:    */       }
/* 100:    */     }
/* 101:118 */     else if ((entity instanceof Relation))
/* 102:    */     {
/* 103:119 */       bundle.setBarColor(Color.red);
/* 104:120 */       Relation relation = (Relation)entity;
/* 105:121 */       bundle.addFrameBundle(translate(relation.getSubject(), types));
/* 106:122 */       bundle.addFrameBundle(translate(relation.getObject(), types));
/* 107:    */     }
/* 108:124 */     else if ((entity instanceof Function))
/* 109:    */     {
/* 110:125 */       bundle.setBarColor(Color.blue);
/* 111:126 */       Function derivative = (Function)entity;
/* 112:127 */       bundle.addFrameBundle(translate(derivative.getSubject(), types));
/* 113:    */     }
/* 114:129 */     else if ((entity instanceof Entity))
/* 115:    */     {
/* 116:130 */       bundle.setBarColor(Color.gray);
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:133 */       System.err.println("Unrecognized object in Thing to FrameBundle translator");
/* 121:    */     }
/* 122:135 */     Color tracerColor = Tracer.getColor(entity);
/* 123:136 */     if (tracerColor != null) {
/* 124:137 */       bundle.setBarColor(tracerColor);
/* 125:    */     }
/* 126:139 */     return bundle;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static Vector translate(Vector variables)
/* 130:    */   {
/* 131:146 */     Vector<FrameBundle> bundles = new Vector();
/* 132:147 */     for (int i = 0; i < variables.size(); i++)
/* 133:    */     {
/* 134:148 */       Entity thing = (Entity)variables.elementAt(i);
/* 135:149 */       bundles.add(translate(thing));
/* 136:    */     }
/* 137:151 */     return bundles;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.adapters.EntityToViewerTranslator
 * JD-Core Version:    0.7.0.1
 */