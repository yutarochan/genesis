/*  1:   */ package matthewFay.Utilities;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Comparator;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class OrderedHashMatrix<ROW, COL, DATA extends Comparable<? super DATA>>
/*  9:   */   extends HashMatrix<ROW, COL, DATA>
/* 10:   */ {
/* 11:   */   public List<COL> getOrderedColKeySet(ROW r)
/* 12:   */   {
/* 13:19 */     List<COL> sorted = new ArrayList(keySetCols(r));
/* 14:   */     
/* 15:21 */     OrderedHashMatrix<ROW, COL, DATA>.COL_Comparator<ROW, COL, DATA> comp = new COL_Comparator(this, r);
/* 16:   */     
/* 17:23 */     Collections.sort(sorted, comp);
/* 18:   */     
/* 19:25 */     return sorted;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public List<ROW> getOrderedRowKeySet(COL c)
/* 23:   */   {
/* 24:29 */     List<ROW> sorted = new ArrayList(keySetRows(c));
/* 25:   */     
/* 26:31 */     OrderedHashMatrix<ROW, COL, DATA>.ROW_Comparator<ROW, COL, DATA> comp = new ROW_Comparator(this, c);
/* 27:   */     
/* 28:33 */     Collections.sort(sorted, comp);
/* 29:   */     
/* 30:35 */     return sorted;
/* 31:   */   }
/* 32:   */   
/* 33:   */   private class COL_Comparator<r, c, d extends Comparable<? super d>>
/* 34:   */     implements Comparator<c>
/* 35:   */   {
/* 36:39 */     private OrderedHashMatrix<r, c, d> _matrix = null;
/* 37:40 */     private r _row = null;
/* 38:   */     
/* 39:   */     public COL_Comparator(r matrix)
/* 40:   */     {
/* 41:43 */       this._matrix = matrix;
/* 42:44 */       this._row = row;
/* 43:   */     }
/* 44:   */     
/* 45:   */     public int compare(c col1, c col2)
/* 46:   */     {
/* 47:50 */       d d1 = (Comparable)this._matrix.get(this._row, col1);
/* 48:51 */       d d2 = (Comparable)this._matrix.get(this._row, col2);
/* 49:   */       
/* 50:53 */       return d2.compareTo(d1);
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   private class ROW_Comparator<r, c, d extends Comparable<? super d>>
/* 55:   */     implements Comparator<r>
/* 56:   */   {
/* 57:58 */     private OrderedHashMatrix<r, c, d> _matrix = null;
/* 58:59 */     private c _col = null;
/* 59:   */     
/* 60:   */     public ROW_Comparator(c matrix)
/* 61:   */     {
/* 62:62 */       this._matrix = matrix;
/* 63:63 */       this._col = col;
/* 64:   */     }
/* 65:   */     
/* 66:   */     public int compare(r row1, r row2)
/* 67:   */     {
/* 68:69 */       d d1 = (Comparable)this._matrix.get(row1, this._col);
/* 69:70 */       d d2 = (Comparable)this._matrix.get(row2, this._col);
/* 70:   */       
/* 71:72 */       return d2.compareTo(d1);
/* 72:   */     }
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.OrderedHashMatrix
 * JD-Core Version:    0.7.0.1
 */