/*   1:    */ package utils;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.net.JarURLConnection;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.net.URLDecoder;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.Enumeration;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.jar.JarEntry;
/*  16:    */ import java.util.jar.JarFile;
/*  17:    */ import org.apache.commons.io.FileUtils;
/*  18:    */ import org.apache.commons.io.FilenameUtils;
/*  19:    */ import org.apache.commons.io.IOCase;
/*  20:    */ import org.apache.commons.io.IOUtils;
/*  21:    */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*  22:    */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*  23:    */ import stories.StoryAnchor;
/*  24:    */ 
/*  25:    */ public class PathFinder
/*  26:    */ {
/*  27:    */   public static final String STORY_ROOT = "story-root";
/*  28: 43 */   public static ClassLoader myClassLoader = StoryAnchor.class.getClassLoader();
/*  29:    */   
/*  30:    */   public static URL lookupURL(String resource)
/*  31:    */   {
/*  32: 64 */     return myClassLoader.getResource(resource);
/*  33:    */   }
/*  34:    */   
/*  35: 67 */   private static HashMap<String, URL> storyCache = new HashMap();
/*  36:    */   
/*  37:    */   public static URL storyURL(String storyReference)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:109 */     if (storyCache.containsKey(storyReference)) {
/*  41:110 */       return (URL)storyCache.get(storyReference);
/*  42:    */     }
/*  43:113 */     URL reference = lookupURL(storyReference);
/*  44:114 */     if (reference != null)
/*  45:    */     {
/*  46:115 */       storyCache.put(storyReference, reference);
/*  47:116 */       return reference;
/*  48:    */     }
/*  49:121 */     ArrayList<URL> initialResults = listStoryMatches(storyReference);
/*  50:122 */     ArrayList<URL> results = initialResults;
/*  51:124 */     if (results.isEmpty()) {
/*  52:125 */       throw new IOException("Story " + storyReference + " not Found!");
/*  53:    */     }
/*  54:127 */     URL storyURL = (URL)results.get(0);
/*  55:128 */     if (results.size() > 1)
/*  56:    */     {
/*  57:129 */       Mark.err(new Object[] {"Multiple Stories Found for " + storyReference });
/*  58:130 */       for (URL u : results) {
/*  59:131 */         Mark.err(new Object[] {"\t" + u });
/*  60:    */       }
/*  61:133 */       throw new IOException("Multiple Stories Found!");
/*  62:    */     }
/*  63:136 */     storyCache.put(storyReference, storyURL);
/*  64:137 */     return storyURL;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static URL storyRootURL()
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:148 */     URL storyAnchor = myClassLoader.getResource("story-root");
/*  71:149 */     return new URL(storyAnchor.toString()
/*  72:150 */       .replaceFirst("story-root$", ""));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static ArrayList<URL> listStoryMatches(String storyTitle)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:164 */     ArrayList<URL> results = new ArrayList();
/*  79:165 */     results.addAll(listFiles(storyRootURL(), storyTitle + ".txt"));
/*  80:166 */     results.addAll(listFiles(storyRootURL(), storyTitle));
/*  81:    */     
/*  82:    */ 
/*  83:    */ 
/*  84:170 */     ArrayList<URL> matches = new ArrayList();
/*  85:171 */     for (URL u : results) {
/*  86:174 */       if (FilenameUtils.getName(URLDecoder.decode(u.toString(), "utf-8")).toLowerCase().startsWith(storyTitle.toLowerCase())) {
/*  87:175 */         matches.add(u);
/*  88:    */       }
/*  89:    */     }
/*  90:178 */     return matches;
/*  91:    */   }
/*  92:    */   
/*  93:182 */   private static HashMap<String, HashMap<String, ArrayList<URL>>> fileSearchCache = new HashMap();
/*  94:    */   
/*  95:    */   public static ArrayList<URL> listFiles(String root, String endFilter)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:199 */     URL rootURL = lookupURL(root);
/*  99:200 */     if ((fileSearchCache.containsKey(rootURL.getPath())) && 
/* 100:201 */       (((HashMap)fileSearchCache.get(rootURL.getPath())).containsKey(endFilter))) {
/* 101:202 */       return (ArrayList)((HashMap)fileSearchCache.get(rootURL.getPath())).get(endFilter);
/* 102:    */     }
/* 103:204 */     ArrayList<URL> files = listFiles(lookupURL(root), endFilter);
/* 104:205 */     if (!fileSearchCache.containsKey(rootURL.getPath())) {
/* 105:206 */       fileSearchCache.put(rootURL.getPath(), new HashMap());
/* 106:    */     }
/* 107:207 */     ((HashMap)fileSearchCache.get(rootURL.getPath())).put(endFilter, files);
/* 108:208 */     return files;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static ArrayList<URL> listFiles(URL root, String endFilter)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:220 */     if (root.getProtocol().equals("file")) {
/* 115:221 */       return listFiles(new File(root.getPath()), endFilter);
/* 116:    */     }
/* 117:223 */     if (root.getProtocol().equals("jar"))
/* 118:    */     {
/* 119:225 */       JarURLConnection connection = 
/* 120:226 */         (JarURLConnection)root.openConnection();
/* 121:    */       
/* 122:228 */       JarFile jarFile = connection.getJarFile();
/* 123:    */       
/* 124:230 */       URL jarFileURL = connection.getJarFileURL();
/* 125:231 */       return listFiles(root, endFilter, jarFile, jarFileURL);
/* 126:    */     }
/* 127:234 */     Mark.err(new Object[] {"Genesis appears to not be in either the filesystem or a jar." });
/* 128:    */     
/* 129:236 */     return null;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static ArrayList<URL> listFiles(File root, String endFilter)
/* 133:    */   {
/* 134:249 */     ArrayList<URL> results = new ArrayList();
/* 135:251 */     if (root.isFile())
/* 136:    */     {
/* 137:    */       try
/* 138:    */       {
/* 139:252 */         results.add(root.toURI().toURL());
/* 140:    */       }
/* 141:    */       catch (MalformedURLException e)
/* 142:    */       {
/* 143:253 */         e.printStackTrace();
/* 144:    */       }
/* 145:254 */       return results;
/* 146:    */     }
/* 147:257 */     Collection<File> matches = 
/* 148:    */     
/* 149:259 */       FileUtils.listFiles(root, 
/* 150:    */       
/* 151:261 */       FileFilterUtils.suffixFileFilter(endFilter, IOCase.INSENSITIVE), 
/* 152:262 */       TrueFileFilter.INSTANCE);
/* 153:263 */     for (File f : matches) {
/* 154:    */       try
/* 155:    */       {
/* 156:264 */         results.add(f.toURI().toURL());
/* 157:    */       }
/* 158:    */       catch (MalformedURLException e)
/* 159:    */       {
/* 160:265 */         e.printStackTrace();
/* 161:    */       }
/* 162:    */     }
/* 163:267 */     return results;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static ArrayList<URL> listFiles(URL root, String endFilter, JarFile jarFile, URL jarFileURL)
/* 167:    */   {
/* 168:282 */     ArrayList<URL> matches = new ArrayList();
/* 169:283 */     Enumeration<JarEntry> entries = jarFile.entries();
/* 170:284 */     String jarRoot = "jar:" + jarFileURL.toString() + "!/";
/* 171:285 */     while (entries.hasMoreElements())
/* 172:    */     {
/* 173:286 */       JarEntry e = (JarEntry)entries.nextElement();
/* 174:287 */       String entryName = e.getName();
/* 175:288 */       if (((jarRoot + entryName).startsWith(root.toString())) && 
/* 176:289 */         (entryName.toLowerCase().endsWith(endFilter.toLowerCase()))) {
/* 177:    */         try
/* 178:    */         {
/* 179:292 */           URL match = new URL(jarRoot + entryName);
/* 180:293 */           matches.add(match);
/* 181:    */         }
/* 182:    */         catch (MalformedURLException e1)
/* 183:    */         {
/* 184:295 */           e1.printStackTrace();
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:298 */     return matches;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static void main(String[] args)
/* 192:    */     throws IOException
/* 193:    */   {
/* 194:302 */     System.out.println(storyRootURL());
/* 195:303 */     System.out.println(storyURL("Start experiment"));
/* 196:304 */     System.out.println(
/* 197:305 */       IOUtils.toString(
/* 198:306 */       storyURL("Start experiment").openStream()));
/* 199:307 */     System.out.println(storyURL("Macbeth plot"));
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.PathFinder
 * JD-Core Version:    0.7.0.1
 */