/*  1:   */ package matthewFay.Utilities;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public class DefaultHashMap<K, V>
/*  6:   */   extends HashMap<K, V>
/*  7:   */ {
/*  8:   */   protected V defaultValue;
/*  9:   */   
/* 10:   */   public DefaultHashMap(V defaultValue)
/* 11:   */   {
/* 12: 8 */     this.defaultValue = defaultValue;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public V get(Object k)
/* 16:   */   {
/* 17:13 */     V v = super.get(k);
/* 18:14 */     return (v == null) && (!containsKey(k)) ? this.defaultValue : v;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.DefaultHashMap
 * JD-Core Version:    0.7.0.1
 */