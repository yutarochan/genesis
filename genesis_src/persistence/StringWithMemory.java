/*  1:   */ package persistence;
/*  2:   */ 
/*  3:   */ import java.util.prefs.Preferences;
/*  4:   */ 
/*  5:   */ public class StringWithMemory
/*  6:   */ {
/*  7:   */   String name;
/*  8:   */   String string;
/*  9:   */   
/* 10:   */   public String getString()
/* 11:   */   {
/* 12:17 */     return this.string;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void getString(String theString)
/* 16:   */   {
/* 17:21 */     Preferences.userRoot().put(this.name, this.string);
/* 18:22 */     this.string = theString;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public StringWithMemory(String name, String defaultValue)
/* 22:   */   {
/* 23:26 */     this.name = name;
/* 24:27 */     this.string = Preferences.userRoot().get(name, defaultValue);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     persistence.StringWithMemory
 * JD-Core Version:    0.7.0.1
 */