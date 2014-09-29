/*   1:    */ package openmind.generate;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileWriter;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.sql.Connection;
/*   9:    */ import java.sql.DriverManager;
/*  10:    */ import java.sql.PreparedStatement;
/*  11:    */ import java.sql.ResultSet;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.LinkedList;
/*  16:    */ import java.util.List;
/*  17:    */ import start.PhraseFactory;
/*  18:    */ import start.RoleFrameGrandParent;
/*  19:    */ import start.Start;
/*  20:    */ 
/*  21:    */ public class OpenMindGenerator
/*  22:    */ {
/*  23: 21 */   private static final Boolean VERBOSE = Boolean.valueOf(true);
/*  24:    */   private static final int port = 3306;
/*  25:    */   private static final String db_host = "mysql.csail.mit.edu";
/*  26:    */   private static final String db_name = "openmind_rlm";
/*  27:    */   private static final String user = "genesis_rlm";
/*  28:    */   private static final String password = "g3nesis";
/*  29:    */   private static final String url = "jdbc:mysql://mysql.csail.mit.edu:3306/openmind_rlm";
/*  30:    */   private static Connection conn;
/*  31:    */   
/*  32:    */   public static void intializeSQLConnection()
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 40 */       Class.forName("com.mysql.jdbc.Driver");
/*  37: 41 */       conn = DriverManager.getConnection("jdbc:mysql://mysql.csail.mit.edu:3306/openmind_rlm", "genesis_rlm", "g3nesis");
/*  38:    */     }
/*  39:    */     catch (Exception e)
/*  40:    */     {
/*  41: 42 */       e.printStackTrace();
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   static
/*  46:    */   {
/*  47: 45 */     intializeSQLConnection();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void closeSQLConnection()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 49 */       conn.close();
/*  55:    */     }
/*  56:    */     catch (SQLException e)
/*  57:    */     {
/*  58: 50 */       e.printStackTrace();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static List<HashMap<String, String>> getOpenMindData(OpenMindTable table, int start, int end)
/*  63:    */   {
/*  64: 57 */     String query = "SELECT * FROM " + table.getTableName();
/*  65:    */     
/*  66:    */ 
/*  67: 60 */     int rowNum = 0;
/*  68: 61 */     List<HashMap<String, String>> results = 
/*  69: 62 */       new LinkedList();
/*  70:    */     try
/*  71:    */     {
/*  72: 64 */       PreparedStatement ps = conn.prepareStatement(query);
/*  73: 65 */       ResultSet rs = ps.executeQuery();
/*  74: 66 */       rs.relative(start);
/*  75:    */       do
/*  76:    */       {
/*  77: 68 */         rowNum++;
/*  78: 69 */         HashMap<String, String> data = new HashMap();
/*  79: 70 */         for (String key : table.getKeys()) {
/*  80: 71 */           data.put(key, rs.getString(key));
/*  81:    */         }
/*  82: 74 */         if (table.verifyMap(data)) {
/*  83: 74 */           results.add(data);
/*  84:    */         }
/*  85: 67 */         if (!rs.next()) {
/*  86:    */           break;
/*  87:    */         }
/*  88: 67 */       } while (rowNum < end);
/*  89:    */     }
/*  90:    */     catch (SQLException e)
/*  91:    */     {
/*  92: 75 */       e.printStackTrace();
/*  93:    */     }
/*  94: 76 */     return results;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static List<HashMap<String, String>> getOpenMindData(OpenMindTable table)
/*  98:    */   {
/*  99: 80 */     return getOpenMindData(table, 0, 2147483647);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static List<String> getRules(OpenMindTable table, int start, int end)
/* 103:    */   {
/* 104: 85 */     List<HashMap<String, String>> results = 
/* 105: 86 */       getOpenMindData(table, start, end);
/* 106: 87 */     int progress = 1;
/* 107: 88 */     List<String> rules = new LinkedList();
/* 108: 89 */     for (HashMap<String, String> entry : results)
/* 109:    */     {
/* 110: 90 */       if ((progress++ % 50 == 0) && (VERBOSE.booleanValue())) {
/* 111: 90 */         System.out.println(progress - 1);
/* 112:    */       }
/* 113: 91 */       String triples = table.getTriples(entry);
/* 114: 92 */       if (triples != null)
/* 115:    */       {
/* 116: 93 */         String rule = GenerateEnglish(triples);
/* 117: 95 */         if (rule != null) {
/* 118: 95 */           rules.add(rule);
/* 119: 96 */         } else if (VERBOSE.booleanValue()) {
/* 120: 96 */           System.err.println("rule is null.");
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124: 97 */     return rules;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static List<String> getRules(OpenMindTable table)
/* 128:    */   {
/* 129:100 */     return getRules(table, 0, 2147483647);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static void outputAllRulesToFile(File file, OpenMindTable table)
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:105 */       BufferedWriter outputFile = new BufferedWriter(new FileWriter(file));
/* 137:106 */       List<String> rules = getRules(table);
/* 138:    */       String rule;
/* 139:107 */       for (Iterator localIterator = rules.iterator(); localIterator.hasNext(); outputFile.write(rule + "\n")) {
/* 140:107 */         rule = (String)localIterator.next();
/* 141:    */       }
/* 142:108 */       outputFile.close();
/* 143:    */     }
/* 144:    */     catch (IOException e)
/* 145:    */     {
/* 146:109 */       e.printStackTrace();
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static List<String> getTriples(String sentence)
/* 151:    */   {
/* 152:113 */     LinkedList<String> triples = new LinkedList();
/* 153:114 */     String[] rawTrips = Start.getStart().processSentence(sentence).split("\n");
/* 154:115 */     for (String s : rawTrips) {
/* 155:116 */       if (!s.matches(".*<.*")) {
/* 156:117 */         triples.add(s);
/* 157:    */       }
/* 158:    */     }
/* 159:118 */     return triples;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static String GenerateEnglish(String triples)
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:125 */       String result = PhraseFactory.getPhraseFactory().generate(triples, new RoleFrameGrandParent[0]);
/* 167:128 */       if ((result == null) || (result.contains("Please try again later.")))
/* 168:    */       {
/* 169:129 */         System.err.println("nothing generated.");
/* 170:130 */         return null;
/* 171:    */       }
/* 172:131 */       return result;
/* 173:    */     }
/* 174:    */     catch (NullPointerException npe) {}
/* 175:132 */     return null;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.generate.OpenMindGenerator
 * JD-Core Version:    0.7.0.1
 */