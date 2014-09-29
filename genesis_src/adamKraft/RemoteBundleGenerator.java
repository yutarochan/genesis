/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import adam.RPCBox;
/*   5:    */ import bridge.reps.entities.Bundle;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Connections.NetWireError;
/*   8:    */ import connections.Connections.NetWireException;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import gui.ActivityMonitor;
/*  12:    */ import gui.OnOffLabel;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import links.words.BundleGenerator;
/*  17:    */ import links.words.BundleGenerator.Implementation;
/*  18:    */ import utils.Mark;
/*  19:    */ import utils.Timer;
/*  20:    */ 
/*  21:    */ public class RemoteBundleGenerator
/*  22:    */   extends BundleGenerator.Implementation
/*  23:    */   implements WiredBox
/*  24:    */ {
/*  25: 18 */   public static String WORDNET_SERVICE_GUID = "Wordnet Bundle Generator";
/*  26:    */   RPCBox bundleGeneratorProxy;
/*  27:    */   
/*  28:    */   public RemoteBundleGenerator()
/*  29:    */     throws Connections.NetWireException
/*  30:    */   {
/*  31: 23 */     this.bundleGeneratorProxy = ((RPCBox)Connections.subscribe(WORDNET_SERVICE_GUID));
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected RemoteBundleGenerator(boolean ignoreMe)
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 28 */       this.bundleGeneratorProxy = ((RPCBox)Connections.subscribe(WORDNET_SERVICE_GUID));
/*  39:    */     }
/*  40:    */     catch (Connections.NetWireException e)
/*  41:    */     {
/*  42: 31 */       Mark.err(new Object[] {"Encountered NetWire exception" });
/*  43:    */     }
/*  44:    */     catch (Connections.NetWireError localNetWireError) {}
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Bundle getRawBundle(String word)
/*  48:    */   {
/*  49: 40 */     Bundle bundle = (Bundle)getBundleMap().get(word);
/*  50: 41 */     if (bundle == null) {
/*  51: 42 */       if (word.indexOf('-') >= 0)
/*  52:    */       {
/*  53: 43 */         bundle = new Bundle();
/*  54: 44 */         Mark.say(new Object[] {"Making empty bundle for", word });
/*  55:    */       }
/*  56:    */       else
/*  57:    */       {
/*  58:    */         try
/*  59:    */         {
/*  60: 53 */           ActivityMonitor.serverWordNetConnection.turnOn();
/*  61:    */           
/*  62: 55 */           Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.WORDNET_SERVER_WORKING, 
/*  63: 56 */             Boolean.valueOf(true) }));
/*  64:    */           
/*  65: 58 */           long start = System.currentTimeMillis();
/*  66: 59 */           Mark.say(new Object[] {"Doing RPC to look up " + word });
/*  67: 60 */           bundle = (Bundle)this.bundleGeneratorProxy.rpc("getBundle", new Object[] { word });
/*  68: 61 */           Timer.laptime(false, "Word timer", "Time for " + word + ":", start);
/*  69:    */         }
/*  70:    */         catch (Exception e)
/*  71:    */         {
/*  72: 64 */           Mark.err(new Object[] {"Harmless exception thrown in RemoteBundleGenerator.getRawBundle" });
/*  73:    */         }
/*  74:    */         finally
/*  75:    */         {
/*  76: 68 */           ActivityMonitor.serverWordNetConnection.turnOff();
/*  77: 69 */           Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.WORDNET_SERVER_WORKING, 
/*  78: 70 */             Boolean.valueOf(false) }));
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82: 75 */     getBundleMap().put(word, bundle);
/*  83: 76 */     return bundle;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static class BundleServer
/*  87:    */     extends BundleGenerator.Implementation
/*  88:    */     implements WiredBox
/*  89:    */   {
/*  90:    */     public String getName()
/*  91:    */     {
/*  92: 82 */       return "Bundle Generator Server Box";
/*  93:    */     }
/*  94:    */     
/*  95:    */     public Bundle getRawBundle(String word)
/*  96:    */     {
/*  97: 86 */       System.out.println("On " + new Date() + ", got request for bundle of word " + word + "!");
/*  98: 87 */       return super.getRawBundle(word);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static void main(String[] args)
/* 103:    */   {
/* 104: 92 */     System.out.println("Starting Bundle Generator Service!");
/* 105: 93 */     BundleGenerator.setSingletonClass(BundleServer.class);
/* 106:    */     try
/* 107:    */     {
/* 108: 95 */       Connections.useWireServer("http://glue.csail.mit.edu/WireServer");
/* 109: 96 */       Connections.publish((WiredBox)BundleGenerator.getInstance(), WORDNET_SERVICE_GUID);
/* 110:    */       try
/* 111:    */       {
/* 112: 98 */         BundleGenerator.readWordnetCache();
/* 113:    */       }
/* 114:    */       catch (Exception e)
/* 115:    */       {
/* 116:101 */         BundleGenerator.purgeWordnetCache();
/* 117:102 */         BundleGenerator.writeWordnetCache();
/* 118:    */       }
/* 119:104 */       System.out.println("Serving forever!");
/* 120:    */       try
/* 121:    */       {
/* 122:    */         for (;;)
/* 123:    */         {
/* 124:107 */           Thread.sleep(10000L);
/* 125:    */         }
/* 126:    */       }
/* 127:    */       catch (InterruptedException e)
/* 128:    */       {
/* 129:110 */         e.printStackTrace();
/* 130:    */       }
/* 131:    */     }
/* 132:    */     catch (Connections.NetWireException e)
/* 133:    */     {
/* 134:115 */       e.printStackTrace();
/* 135:    */     }
/* 136:    */     catch (Connections.NetWireError e)
/* 137:    */     {
/* 138:118 */       e.printStackTrace();
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getName()
/* 143:    */   {
/* 144:124 */     return "Remote bundle generator";
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.RemoteBundleGenerator
 * JD-Core Version:    0.7.0.1
 */