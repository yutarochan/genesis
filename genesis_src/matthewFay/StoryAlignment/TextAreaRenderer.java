/*   1:    */ package matthewFay.StoryAlignment;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import javax.swing.JTable;
/*  11:    */ import javax.swing.JTextArea;
/*  12:    */ import javax.swing.table.DefaultTableCellRenderer;
/*  13:    */ import javax.swing.table.TableCellRenderer;
/*  14:    */ import javax.swing.table.TableColumn;
/*  15:    */ import javax.swing.table.TableColumnModel;
/*  16:    */ 
/*  17:    */ public class TextAreaRenderer
/*  18:    */   extends JTextArea
/*  19:    */   implements TableCellRenderer
/*  20:    */ {
/*  21: 36 */   private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
/*  22: 39 */   private final Map<JTable, Map<Object, Map<Object, Integer>>> tablecellSizes = new HashMap();
/*  23:    */   public HashMap<Integer, HashMap<Integer, Color>> colorHash;
/*  24:    */   
/*  25:    */   public TextAreaRenderer()
/*  26:    */   {
/*  27: 45 */     setLineWrap(true);
/*  28: 46 */     setWrapStyleWord(true);
/*  29: 47 */     this.colorHash = new HashMap();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setColor(int row, int column, Color color)
/*  33:    */   {
/*  34: 52 */     if (this.colorHash.get(Integer.valueOf(row)) == null) {
/*  35: 53 */       this.colorHash.put(Integer.valueOf(row), new HashMap());
/*  36:    */     }
/*  37: 54 */     if (((HashMap)this.colorHash.get(Integer.valueOf(row))).get(Integer.valueOf(column)) != null) {
/*  38: 55 */       ((HashMap)this.colorHash.get(Integer.valueOf(row))).remove(Integer.valueOf(column));
/*  39:    */     }
/*  40: 56 */     ((HashMap)this.colorHash.get(Integer.valueOf(row))).put(Integer.valueOf(column), color);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Color getColor(int row, int column)
/*  44:    */   {
/*  45: 59 */     Color color = Color.WHITE;
/*  46: 60 */     if (this.colorHash.get(Integer.valueOf(row)) == null) {
/*  47: 61 */       return color;
/*  48:    */     }
/*  49: 62 */     color = (Color)((HashMap)this.colorHash.get(Integer.valueOf(row))).get(Integer.valueOf(column));
/*  50: 63 */     if (color == null) {
/*  51: 64 */       color = Color.WHITE;
/*  52:    */     }
/*  53: 65 */     return color;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/*  57:    */   {
/*  58: 84 */     this.renderer.getTableCellRendererComponent(table, value, 
/*  59: 85 */       isSelected, hasFocus, row, column);
/*  60: 86 */     setForeground(this.renderer.getForeground());
/*  61: 87 */     setBackground(getColor(row, column));
/*  62: 88 */     setBorder(this.renderer.getBorder());
/*  63: 89 */     setFont(this.renderer.getFont());
/*  64: 90 */     setText(this.renderer.getText());
/*  65:    */     
/*  66: 92 */     TableColumnModel columnModel = table.getColumnModel();
/*  67: 93 */     setSize(columnModel.getColumn(column).getWidth(), 0);
/*  68: 94 */     int height_wanted = (int)getPreferredSize().getHeight();
/*  69: 95 */     addSize(table, row, column, height_wanted);
/*  70: 96 */     height_wanted = findTotalMaximumRowSize(table, row);
/*  71: 97 */     if (height_wanted != table.getRowHeight(row)) {
/*  72: 98 */       table.setRowHeight(row, height_wanted);
/*  73:    */     }
/*  74:100 */     return this;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void addSize(JTable table, int row, int column, int height)
/*  78:    */   {
/*  79:111 */     Map<Object, Map<Object, Integer>> rowsMap = (Map)this.tablecellSizes.get(table);
/*  80:112 */     if (rowsMap == null) {
/*  81:113 */       this.tablecellSizes.put(table, rowsMap = new HashMap());
/*  82:    */     }
/*  83:115 */     Map<Object, Integer> rowheightsMap = (Map)rowsMap.get(Integer.valueOf(row));
/*  84:116 */     if (rowheightsMap == null) {
/*  85:117 */       rowsMap.put(Integer.valueOf(row), rowheightsMap = new HashMap());
/*  86:    */     }
/*  87:119 */     rowheightsMap.put(Integer.valueOf(column), Integer.valueOf(height));
/*  88:    */   }
/*  89:    */   
/*  90:    */   private int findTotalMaximumRowSize(JTable table, int row)
/*  91:    */   {
/*  92:132 */     int maximum_height = 0;
/*  93:133 */     Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
/*  94:134 */     while (columns.hasMoreElements())
/*  95:    */     {
/*  96:135 */       TableColumn tc = (TableColumn)columns.nextElement();
/*  97:136 */       TableCellRenderer cellRenderer = tc.getCellRenderer();
/*  98:137 */       if ((cellRenderer instanceof TextAreaRenderer))
/*  99:    */       {
/* 100:138 */         TextAreaRenderer tar = (TextAreaRenderer)cellRenderer;
/* 101:139 */         maximum_height = Math.max(maximum_height, 
/* 102:140 */           tar.findMaximumRowSize(table, row));
/* 103:    */       }
/* 104:    */     }
/* 105:143 */     return maximum_height;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private int findMaximumRowSize(JTable table, int row)
/* 109:    */   {
/* 110:154 */     Map<Object, Map<Object, Integer>> rows = (Map)this.tablecellSizes.get(table);
/* 111:155 */     if (rows == null) {
/* 112:155 */       return 0;
/* 113:    */     }
/* 114:156 */     Map<Object, Integer> rowheights = (Map)rows.get(Integer.valueOf(row));
/* 115:157 */     if (rowheights == null) {
/* 116:157 */       return 0;
/* 117:    */     }
/* 118:158 */     int maximum_height = 0;
/* 119:159 */     for (Map.Entry<Object, Integer> entry : rowheights.entrySet())
/* 120:    */     {
/* 121:160 */       int cellHeight = ((Integer)entry.getValue()).intValue();
/* 122:161 */       maximum_height = Math.max(maximum_height, cellHeight);
/* 123:    */     }
/* 124:163 */     return maximum_height;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.TextAreaRenderer
 * JD-Core Version:    0.7.0.1
 */