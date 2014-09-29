/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import javax.swing.filechooser.FileFilter;
/*   7:    */ 
/*   8:    */ public class ExampleFileFilter
/*   9:    */   extends FileFilter
/*  10:    */ {
/*  11: 64 */   private static String TYPE_UNKNOWN = "Type Unknown";
/*  12: 65 */   private static String HIDDEN_FILE = "Hidden File";
/*  13: 66 */   private Hashtable filters = null;
/*  14: 67 */   private String description = null;
/*  15: 68 */   private String fullDescription = null;
/*  16: 69 */   private boolean useExtensionsInDescription = true;
/*  17:    */   
/*  18:    */   public ExampleFileFilter()
/*  19:    */   {
/*  20: 77 */     this.filters = new Hashtable();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ExampleFileFilter(String extension)
/*  24:    */   {
/*  25: 86 */     this(extension, null);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ExampleFileFilter(String extension, String description)
/*  29:    */   {
/*  30: 98 */     this();
/*  31: 99 */     if (extension != null) {
/*  32: 99 */       addExtension(extension);
/*  33:    */     }
/*  34:100 */     if (description != null) {
/*  35:100 */       setDescription(description);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ExampleFileFilter(String[] filters)
/*  40:    */   {
/*  41:112 */     this(filters, null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ExampleFileFilter(String[] filters, String description)
/*  45:    */   {
/*  46:123 */     this();
/*  47:124 */     for (int i = 0; i < filters.length; i++) {
/*  48:126 */       addExtension(filters[i]);
/*  49:    */     }
/*  50:128 */     if (description != null) {
/*  51:128 */       setDescription(description);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean accept(File f)
/*  56:    */   {
/*  57:140 */     if (f != null)
/*  58:    */     {
/*  59:141 */       if (f.isDirectory()) {
/*  60:142 */         return true;
/*  61:    */       }
/*  62:144 */       String extension = getExtension(f);
/*  63:145 */       if ((extension != null) && (this.filters.get(getExtension(f)) != null)) {
/*  64:146 */         return true;
/*  65:    */       }
/*  66:    */     }
/*  67:149 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getExtension(File f)
/*  71:    */   {
/*  72:158 */     if (f != null)
/*  73:    */     {
/*  74:159 */       String filename = f.getName();
/*  75:160 */       int i = filename.lastIndexOf('.');
/*  76:161 */       if ((i > 0) && (i < filename.length() - 1)) {
/*  77:162 */         return filename.substring(i + 1).toLowerCase();
/*  78:    */       }
/*  79:    */     }
/*  80:165 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void addExtension(String extension)
/*  84:    */   {
/*  85:180 */     if (this.filters == null) {
/*  86:181 */       this.filters = new Hashtable(5);
/*  87:    */     }
/*  88:183 */     this.filters.put(extension.toLowerCase(), this);
/*  89:184 */     this.fullDescription = null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getDescription()
/*  93:    */   {
/*  94:196 */     if (this.fullDescription == null) {
/*  95:197 */       if ((this.description == null) || (isExtensionListInDescription()))
/*  96:    */       {
/*  97:198 */         this.fullDescription = (this.description + " (");
/*  98:    */         
/*  99:200 */         Enumeration extensions = this.filters.keys();
/* 100:201 */         if (extensions != null)
/* 101:    */         {
/* 102:202 */           this.fullDescription = (this.fullDescription + "." + (String)extensions.nextElement());
/* 103:203 */           while (extensions.hasMoreElements()) {
/* 104:204 */             this.fullDescription = (this.fullDescription + ", ." + (String)extensions.nextElement());
/* 105:    */           }
/* 106:    */         }
/* 107:207 */         this.fullDescription += ")";
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:209 */         this.fullDescription = this.description;
/* 112:    */       }
/* 113:    */     }
/* 114:212 */     return this.fullDescription;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDescription(String description)
/* 118:    */   {
/* 119:223 */     this.description = description;
/* 120:224 */     this.fullDescription = null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setExtensionListInDescription(boolean b)
/* 124:    */   {
/* 125:238 */     this.useExtensionsInDescription = b;
/* 126:239 */     this.fullDescription = null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isExtensionListInDescription()
/* 130:    */   {
/* 131:253 */     return this.useExtensionsInDescription;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ExampleFileFilter
 * JD-Core Version:    0.7.0.1
 */