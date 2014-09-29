/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.io.File;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.lang.reflect.Constructor;
/*  7:   */ import java.lang.reflect.Field;
/*  8:   */ import java.lang.reflect.InvocationTargetException;
/*  9:   */ import java.net.URL;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import java.util.HashSet;
/* 12:   */ import java.util.List;
/* 13:   */ import java.util.Set;
/* 14:   */ 
/* 15:   */ public class SmartFrameFactory
/* 16:   */ {
/* 17:14 */   private static Set<String> unframedTypes = new HashSet();
/* 18:15 */   private static List<Class> cachedFrames = null;
/* 19:   */   
/* 20:   */   private static List<Class> getFrames()
/* 21:   */   {
/* 22:18 */     if (cachedFrames == null) {
/* 23:19 */       cachedFrames = findSubclasses("frames", Frame.class);
/* 24:   */     }
/* 25:21 */     return cachedFrames;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static Frame translate(Entity thing)
/* 29:   */   {
/* 30:   */     try
/* 31:   */     {
/* 32:26 */       for (Class<? extends Frame> frame : )
/* 33:   */       {
/* 34:27 */         String type = (String)frame.getField("FRAMETYPE").get(null);
/* 35:28 */         if (thing.isA(type)) {
/* 36:29 */           return (Frame)frame.getConstructor(new Class[] { Entity.class }).newInstance(new Object[] { thing });
/* 37:   */         }
/* 38:   */       }
/* 39:   */     }
/* 40:   */     catch (InstantiationException e)
/* 41:   */     {
/* 42:33 */       e.printStackTrace();
/* 43:   */     }
/* 44:   */     catch (InvocationTargetException e)
/* 45:   */     {
/* 46:35 */       e.printStackTrace();
/* 47:   */     }
/* 48:   */     catch (IllegalAccessException e)
/* 49:   */     {
/* 50:37 */       e.printStackTrace();
/* 51:   */     }
/* 52:   */     catch (NoSuchMethodException e)
/* 53:   */     {
/* 54:39 */       e.printStackTrace();
/* 55:   */     }
/* 56:   */     catch (NoSuchFieldException e)
/* 57:   */     {
/* 58:41 */       e.printStackTrace();
/* 59:   */     }
/* 60:43 */     unframedTypes.add(thing.getType());
/* 61:   */     
/* 62:   */ 
/* 63:46 */     return null;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static List<Class> findSubclasses(String packagename, Class tosubclass)
/* 67:   */   {
/* 68:51 */     List<Class> result = new ArrayList();
/* 69:52 */     String name = new String(packagename);
/* 70:53 */     if (!name.startsWith("/")) {
/* 71:54 */       name = "/" + name;
/* 72:   */     }
/* 73:56 */     name = name.replace('.', '/');
/* 74:57 */     URL url = SmartFrameFactory.class.getResource(name);
/* 75:58 */     assert (url != null);
/* 76:59 */     File directory = new File(url.getFile());
/* 77:60 */     assert (directory.exists());
/* 78:61 */     for (String element : directory.list()) {
/* 79:62 */       if (element.endsWith(".class"))
/* 80:   */       {
/* 81:63 */         String classname = element.substring(0, element.length() - 6);
/* 82:   */         try
/* 83:   */         {
/* 84:65 */           Class c = Class.forName(packagename + "." + classname);
/* 85:66 */           if (tosubclass.isAssignableFrom(c)) {
/* 86:67 */             result.add(c);
/* 87:   */           }
/* 88:   */         }
/* 89:   */         catch (ClassNotFoundException cnfex)
/* 90:   */         {
/* 91:70 */           System.err.println(cnfex);
/* 92:   */         }
/* 93:   */       }
/* 94:   */     }
/* 95:74 */     return result;
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.SmartFrameFactory
 * JD-Core Version:    0.7.0.1
 */