/*   1:    */ package core;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Connections;
/*   7:    */ import genesis.FileSourceReader;
/*   8:    */ import genesis.GenesisPlugBoardUpper;
/*   9:    */ import java.io.File;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.concurrent.ExecutionException;
/*  12:    */ import java.util.concurrent.ExecutorService;
/*  13:    */ import java.util.concurrent.Executors;
/*  14:    */ import java.util.concurrent.Future;
/*  15:    */ import testBoxes.QueueBox;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class HeadlessGenesis
/*  19:    */   extends GenesisPlugBoardUpper
/*  20:    */ {
/*  21:    */   private static HeadlessGenesis headlessGenesis;
/*  22: 37 */   private QueueBox collectCompleteStory = new QueueBox();
/*  23:    */   
/*  24:    */   public static HeadlessGenesis getHeadlessGenesis()
/*  25:    */   {
/*  26: 45 */     if (headlessGenesis == null)
/*  27:    */     {
/*  28: 46 */       headlessGenesis = new HeadlessGenesis();
/*  29: 47 */       headlessGenesis.initializeWiring();
/*  30:    */     }
/*  31: 49 */     return headlessGenesis;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private HeadlessGenesis()
/*  35:    */   {
/*  36: 59 */     setName("Headless Genesis");
/*  37:    */     
/*  38: 61 */     Connections.wire("complete story analysis port", 
/*  39: 62 */       getMentalModel1(), this.collectCompleteStory);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void submitStory(final String storyPathOrName)
/*  43:    */   {
/*  44: 81 */     new Thread()
/*  45:    */     {
/*  46:    */       public void run()
/*  47:    */       {
/*  48: 79 */         HeadlessGenesis.this.getFileSourceReader().readStory(storyPathOrName);
/*  49:    */       }
/*  50:    */     }.start();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void submitStory(final File story)
/*  54:    */   {
/*  55: 89 */     new Thread()
/*  56:    */     {
/*  57:    */       public void run()
/*  58:    */       {
/*  59: 87 */         HeadlessGenesis.this.getFileSourceReader().readStory(story);
/*  60:    */       }
/*  61:    */     }.start();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public BetterSignal getProcessedStory()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:102 */       Object signal = Executors.newFixedThreadPool(1)
/*  69:103 */         .submit(this.collectCompleteStory).get();
/*  70:104 */       if ((signal instanceof BetterSignal)) {
/*  71:105 */         return (BetterSignal)signal;
/*  72:    */       }
/*  73:108 */       throw new ClassCastException();
/*  74:    */     }
/*  75:    */     catch (InterruptedException|ExecutionException e)
/*  76:    */     {
/*  77:111 */       e.printStackTrace();
/*  78:    */     }
/*  79:112 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public BetterSignal processStoryFile(String storyPathOrName)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:140 */     submitStory(storyPathOrName);
/*  86:141 */     return getProcessedStory();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public BetterSignal processStoryFile(File storyFile)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:145 */     submitStory(storyFile);
/*  93:146 */     return getProcessedStory();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void demoProcessSignal(BetterSignal s)
/*  97:    */   {
/*  98:157 */     Mark.say(
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:183 */       new Object[] { "Entering processSignal" });Sequence story = (Sequence)s.get(0, Sequence.class);Sequence explicitElements = (Sequence)s.get(1, Sequence.class);Sequence inferences = (Sequence)s.get(2, Sequence.class);Sequence concepts = (Sequence)s.get(3, Sequence.class);Mark.say(new Object[] { "\n\n\nStory elements" });Mark.say(new Object[] { "\n\n\nStory: get(0, Sequence.class)" });
/* 125:166 */     for (Entity e : story.getElements()) {
/* 126:167 */       Mark.say(new Object[] {e.asString() });
/* 127:    */     }
/* 128:169 */     Mark.say(new Object[] {"\n\n\nExplicit story elements: get(1, Sequence.class)" });
/* 129:170 */     for (Entity e : explicitElements.getElements()) {
/* 130:171 */       Mark.say(new Object[] {e.asString() });
/* 131:    */     }
/* 132:173 */     Mark.say(new Object[] {"\n\n\nInstantiated commonsense rules: get(2, Sequence.class)" });
/* 133:175 */     for (Entity e : inferences.getElements()) {
/* 134:176 */       Mark.say(new Object[] {e.asString() });
/* 135:    */     }
/* 136:178 */     Mark.say(new Object[] {"\n\n\nInstantiated concept patterns: get(3, Sequence.class)" });
/* 137:180 */     for (Entity e : concepts.getElements()) {
/* 138:181 */       Mark.say(new Object[] {e.asString() });
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static void main(String[] ignore)
/* 143:    */   {
/* 144:186 */     HeadlessGenesis hg = getHeadlessGenesis();
/* 145:    */     try
/* 146:    */     {
/* 147:189 */       BetterSignal signal = hg.processStoryFile("/stories/Shakespeare/Macbeth1.txt");
/* 148:190 */       demoProcessSignal(signal);
/* 149:    */     }
/* 150:    */     catch (IOException e)
/* 151:    */     {
/* 152:192 */       e.printStackTrace();
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     core.HeadlessGenesis
 * JD-Core Version:    0.7.0.1
 */