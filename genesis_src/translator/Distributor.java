/*   1:    */ package translator;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Vector;
/*  12:    */ 
/*  13:    */ public class Distributor
/*  14:    */   extends AbstractWiredBox
/*  15:    */ {
/*  16:    */   public static final String START_STORY = "startStory";
/*  17:    */   public static final String TELL_STORY = "tellStory";
/*  18:    */   public static final String STOP_STORY = "stopStory";
/*  19:    */   public static final String THREAD = "thread";
/*  20:    */   public static final String PLACE = "place";
/*  21:    */   public static final String PATH_ELEMENT = "pathElement";
/*  22:    */   public static final String TRAJECTORY = "trajectory";
/*  23:    */   public static final String TRANSITION = "transition";
/*  24:    */   public static final String TRANSFER = "transfer";
/*  25:    */   public static final String ROLES = "roles";
/*  26:    */   public static final String CAUSE = "cause";
/*  27:    */   public static final String COERCE = "coerce";
/*  28:    */   public static final String TIME = "time";
/*  29:    */   public static final String QUESTION = "question";
/*  30:    */   public static final String IMAGINE = "imagine";
/*  31:    */   public static final String DESCRIBE = "describe";
/*  32:    */   Entity input;
/*  33:    */   
/*  34:    */   public Distributor()
/*  35:    */   {
/*  36: 54 */     Connections.getPorts(this).addSignalProcessor("process");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void process(Object o)
/*  40:    */   {
/*  41: 58 */     this.input = null;
/*  42: 60 */     if ((o instanceof Entity))
/*  43:    */     {
/*  44: 61 */       this.input = ((Entity)o);
/*  45: 62 */       transmitMem(this.input);
/*  46: 63 */       sort(this.input);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void transmitMem(Entity t)
/*  51:    */   {
/*  52: 70 */     if ((t.sequenceP()) && (t.getElements().size() > 0)) {
/*  53: 71 */       t = (Entity)t.getElements().get(0);
/*  54:    */     }
/*  55: 73 */     transmit("port for english sentence input", t);
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void transmit(String port, Entity t)
/*  59:    */   {
/*  60: 78 */     Connections.getPorts(this).transmit(port, t);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void sort(Object o)
/*  64:    */   {
/*  65: 83 */     if (!(o instanceof Entity)) {
/*  66: 84 */       return;
/*  67:    */     }
/*  68: 86 */     Entity thing = (Entity)o;
/*  69: 87 */     if (thing.relationP())
/*  70:    */     {
/*  71: 88 */       sort(((Relation)thing).getSubject());
/*  72: 89 */       sort(((Relation)thing).getObject());
/*  73: 91 */       if (thing.isA("question")) {
/*  74: 92 */         transmit("question", wrap(thing));
/*  75: 94 */       } else if (thing.isA("trajectory")) {
/*  76: 95 */         transmit("trajectory", wrap(thing));
/*  77: 97 */       } else if (thing.isA("roles")) {
/*  78: 98 */         transmit("roles", thing);
/*  79:100 */       } else if (thing.isA("transfer")) {
/*  80:101 */         transmit("transfer", thing);
/*  81:103 */       } else if ((thing.isA("because")) || (thing.isA("cause"))) {
/*  82:104 */         transmit("cause", thing);
/*  83:106 */       } else if (thing.isA("coerce")) {
/*  84:107 */         transmit("coerce", thing);
/*  85:109 */       } else if (thing.isA("ask")) {
/*  86:110 */         transmit("coerce", thing);
/*  87:112 */       } else if ((thing.isA("before")) || (thing.isA("after")) || (thing.isA("while"))) {
/*  88:113 */         transmit("time", thing);
/*  89:115 */       } else if (thing.isA("classification")) {
/*  90:116 */         transmit("thread", thing);
/*  91:    */       }
/*  92:    */     }
/*  93:119 */     if (thing.functionP())
/*  94:    */     {
/*  95:120 */       sort(((Function)thing).getSubject());
/*  96:121 */       if (thing.isA("question")) {
/*  97:122 */         transmit("question", thing);
/*  98:124 */       } else if (thing.isA("imagine")) {
/*  99:125 */         transmit("imagine", thing);
/* 100:127 */       } else if (thing.isA("description")) {
/* 101:128 */         transmit("describe", thing);
/* 102:130 */       } else if (thing.isA("transition")) {
/* 103:131 */         transmit("transition", wrap(thing));
/* 104:    */       }
/* 105:    */     }
/* 106:134 */     if (thing.sequenceP())
/* 107:    */     {
/* 108:135 */       Sequence s = (Sequence)thing;
/* 109:136 */       for (Entity t : s.getElements()) {
/* 110:137 */         sort(t);
/* 111:    */       }
/* 112:139 */       if ((s.isA("path")) || (s.isA("location")))
/* 113:    */       {
/* 114:140 */         Vector v = s.getElements();
/* 115:141 */         for (Object i = v.iterator(); ((Iterator)i).hasNext();)
/* 116:    */         {
/* 117:142 */           Function element = (Function)((Iterator)i).next();
/* 118:143 */           if (s.isA("path"))
/* 119:    */           {
/* 120:144 */             transmit("pathElement", element);
/* 121:145 */             transmit("place", element.getSubject());
/* 122:    */           }
/* 123:147 */           transmit("place", element);
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   private Entity wrap(Entity thing)
/* 130:    */   {
/* 131:174 */     return thing;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Entity getInput()
/* 135:    */   {
/* 136:179 */     return this.input;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.Distributor
 * JD-Core Version:    0.7.0.1
 */