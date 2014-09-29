/*   1:    */ package matthewFay.VideoAnalysis;
/*   2:    */ 
/*   3:    */ import Co57.BerylVerbTranslator.EventToJSON;
/*   4:    */ import Co57.BerylVerbTranslator.VerbOutput;
/*   5:    */ import Signals.BetterSignal;
/*   6:    */ import bridge.reps.entities.Entity;
/*   7:    */ import bridge.reps.entities.RFactory;
/*   8:    */ import bridge.reps.entities.Relation;
/*   9:    */ import bridge.reps.entities.Sequence;
/*  10:    */ import com.google.gson.Gson;
/*  11:    */ import connections.AbstractWiredBox;
/*  12:    */ import connections.Connections;
/*  13:    */ import connections.Ports;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class VideoStoryMemory
/*  19:    */   extends AbstractWiredBox
/*  20:    */ {
/*  21:    */   public static final String VIDEO_STORY_MEMORY = "video story memory";
/*  22:    */   public static final String EVENT_PORT = "event port";
/*  23:    */   public static final String NEXT_EVENT_FLAG = "nextEvent";
/*  24:    */   private EventPredictor predictor;
/*  25:    */   
/*  26:    */   public VideoStoryMemory()
/*  27:    */   {
/*  28: 33 */     setName("video story memory");
/*  29:    */     
/*  30: 35 */     Connections.getPorts(this).addSignalProcessor("event port", "processEvent");
/*  31:    */     
/*  32: 37 */     this.predictor = new EventPredictor();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void finishCurrentSequence()
/*  36:    */   {
/*  37: 41 */     if (this.currentSequence == null) {
/*  38: 42 */       this.currentSequence = new Sequence();
/*  39:    */     }
/*  40: 44 */     if (this.currentSequence.getNumberOfChildren() > 0)
/*  41:    */     {
/*  42: 45 */       this.sequenceHistory.add(this.currentSequence);
/*  43: 46 */       this.currentSequence = new Sequence();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47: 50 */   Sequence currentSequence = new Sequence();
/*  48: 51 */   ArrayList<Sequence> sequenceHistory = new ArrayList();
/*  49:    */   
/*  50:    */   public void processEvent(Object o)
/*  51:    */   {
/*  52: 54 */     BetterSignal signal = BetterSignal.isSignal(o);
/*  53: 55 */     if (signal == null) {
/*  54: 56 */       return;
/*  55:    */     }
/*  56: 57 */     if (((String)signal.get(0, String.class)).equals("play by play events"))
/*  57:    */     {
/*  58: 58 */       Entity event = (Entity)signal.get(1, Entity.class);
/*  59:    */       
/*  60: 60 */       this.currentSequence.addElement(event);
/*  61: 61 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "nextEvent", predictNextEvent() }));
/*  62:    */     }
/*  63: 63 */     if (((String)signal.get(0, String.class)).equals("stopped")) {
/*  64: 64 */       finishCurrentSequence();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Entity predictNextEvent()
/*  69:    */   {
/*  70: 69 */     HashMap<Entity, Float> predictions = this.predictor.predictNextEvent(this.currentSequence, this.sequenceHistory);
/*  71: 70 */     Entity prediction = null;
/*  72: 71 */     Float max = Float.valueOf((1.0F / -1.0F));
/*  73: 72 */     for (Entity t : predictions.keySet()) {
/*  74: 73 */       if (prediction == null)
/*  75:    */       {
/*  76: 74 */         prediction = t;
/*  77: 75 */         max = (Float)predictions.get(t);
/*  78:    */       }
/*  79: 77 */       else if (max.floatValue() < ((Float)predictions.get(t)).floatValue())
/*  80:    */       {
/*  81: 78 */         prediction = t;
/*  82: 79 */         max = (Float)predictions.get(t);
/*  83:    */       }
/*  84:    */     }
/*  85: 83 */     return prediction;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void main(String[] args)
/*  89:    */   {
/*  90: 87 */     Entity subject = new Entity("Human_1");
/*  91: 88 */     Entity object = new Entity("Object_3");
/*  92: 89 */     Entity indirect = new Entity("Human_2");
/*  93:    */     
/*  94: 91 */     Relation t = RFactory.makeRoleFrameRelation(subject, "take", object);
/*  95: 92 */     RFactory.addRoleFrameFrom(indirect, t);
/*  96:    */     
/*  97: 94 */     Mark.say(new Object[] {t.asString() });
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:100 */     String json = "{\"direct\":0,\"direct_type\":\"object\",\"end\":1235,\"indirect\":3,\"indirect_type\":\"human\",\"name\":\"give\",\"start\":1234,\"subject\":1,\"subject_type\":\"human\"}";
/* 104:101 */     Gson gson = new Gson();
/* 105:102 */     BerylVerbTranslator.VerbOutput verbOutput = (BerylVerbTranslator.VerbOutput)gson.fromJson(json, BerylVerbTranslator.VerbOutput.class);
/* 106:103 */     Entity tt = verbOutput.toThing();
/* 107:104 */     Mark.say(new Object[] {tt.asString() });
/* 108:105 */     BerylVerbTranslator.EventToJSON etj = new BerylVerbTranslator.EventToJSON(tt);
/* 109:106 */     Mark.say(new Object[] {etj });
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.VideoAnalysis.VideoStoryMemory
 * JD-Core Version:    0.7.0.1
 */