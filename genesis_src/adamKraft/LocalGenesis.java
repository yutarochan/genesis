/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import genesis.Genesis;
/*  4:   */ import links.words.BundleGenerator;
/*  5:   */ 
/*  6:   */ public class LocalGenesis
/*  7:   */   extends Genesis
/*  8:   */ {
/*  9:   */   static
/* 10:   */   {
/* 11: 8 */     BundleGenerator.setSingletonClass(FallbackBundleGenerator.class);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static void main(String[] args)
/* 15:   */   {
/* 16:13 */     LocalGenesis myGenesis = new LocalGenesis();
/* 17:14 */     myGenesis.startInFrame();
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */