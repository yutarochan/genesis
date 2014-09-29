/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import connections.Connections.NetWireError;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import links.words.BundleGenerator.Implementation;
/*  8:   */ 
/*  9:   */ public class FallbackBundleGenerator
/* 10:   */   extends RemoteBundleGenerator
/* 11:   */ {
/* 12:11 */   private BundleGenerator.Implementation fallback = null;
/* 13:   */   
/* 14:   */   public FallbackBundleGenerator()
/* 15:   */   {
/* 16:14 */     super(false);
/* 17:   */     
/* 18:   */ 
/* 19:   */ 
/* 20:   */ 
/* 21:   */ 
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:26 */     this.bundleGeneratorProxy = null;
/* 29:27 */     initFallback();
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void initFallback()
/* 33:   */   {
/* 34:31 */     this.fallback = new BundleGenerator.Implementation();
/* 35:   */     try
/* 36:   */     {
/* 37:33 */       this.fallback.readWordnetCache();
/* 38:   */     }
/* 39:   */     catch (Exception e)
/* 40:   */     {
/* 41:36 */       this.fallback.purgeWordnetCache();
/* 42:37 */       this.fallback.writeWordnetCache();
/* 43:   */     }
/* 44:40 */     for (String word : super.getBundleMap().keySet()) {
/* 45:41 */       this.fallback.getBundleMap().put(word, (Bundle)super.getBundleMap().get(word));
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Bundle getRawBundle(String word)
/* 50:   */   {
/* 51:48 */     if ((this.bundleGeneratorProxy != null) && (this.fallback == null)) {
/* 52:   */       try
/* 53:   */       {
/* 54:52 */         return super.getRawBundle(word);
/* 55:   */       }
/* 56:   */       catch (Connections.NetWireError e)
/* 57:   */       {
/* 58:56 */         System.out.println(e.getStackTrace());
/* 59:57 */         e.printStackTrace();
/* 60:58 */         if (this.fallback == null)
/* 61:   */         {
/* 62:59 */           System.err.println(e.toString());
/* 63:60 */           System.err.println("Remote error while looking up bundle of " + word + ". Falling back");
/* 64:   */           
/* 65:   */ 
/* 66:63 */           System.err.flush();
/* 67:64 */           initFallback();
/* 68:   */         }
/* 69:   */         else
/* 70:   */         {
/* 71:67 */           System.err.println("RPC failed. falling back on local");
/* 72:68 */           System.err.flush();
/* 73:   */         }
/* 74:70 */         return this.fallback.getRawBundle(word);
/* 75:   */       }
/* 76:   */     }
/* 77:74 */     return this.fallback.getRawBundle(word);
/* 78:   */   }
/* 79:   */   
/* 80:   */   public HashMap<String, Bundle> getBundleMap()
/* 81:   */   {
/* 82:80 */     if (this.fallback != null) {
/* 83:81 */       return this.fallback.getBundleMap();
/* 84:   */     }
/* 85:84 */     return super.getBundleMap();
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.FallbackBundleGenerator
 * JD-Core Version:    0.7.0.1
 */