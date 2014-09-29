/*  1:   */ package michaelBehr;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import javax.xml.parsers.DocumentBuilder;
/*  7:   */ import javax.xml.parsers.DocumentBuilderFactory;
/*  8:   */ import javax.xml.parsers.ParserConfigurationException;
/*  9:   */ import org.w3c.dom.Document;
/* 10:   */ import org.w3c.dom.Element;
/* 11:   */ import org.w3c.dom.Node;
/* 12:   */ import org.w3c.dom.NodeList;
/* 13:   */ import org.xml.sax.SAXException;
/* 14:   */ 
/* 15:   */ public class AnnotationUtils
/* 16:   */ {
/* 17:   */   public static Document parse(File file)
/* 18:   */     throws IOException, SAXException
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:14 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 23:15 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 24:16 */       return builder.parse(file);
/* 25:   */     }
/* 26:   */     catch (ParserConfigurationException e)
/* 27:   */     {
/* 28:19 */       throw new RuntimeException(e);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static Element[] getElementChildren(Node parent)
/* 33:   */   {
/* 34:26 */     ArrayList<Element> outList = new ArrayList();
/* 35:27 */     NodeList children = parent.getChildNodes();
/* 36:28 */     for (int i = 0; i < children.getLength(); i++)
/* 37:   */     {
/* 38:29 */       Node child = children.item(i);
/* 39:30 */       if (child.getNodeType() == 1) {
/* 40:31 */         outList.add((Element)child);
/* 41:   */       }
/* 42:   */     }
/* 43:34 */     Element[] out = new Element[outList.size()];
/* 44:35 */     return (Element[])outList.toArray(out);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static Element[] getTypedChildren(Node parent, String tagname)
/* 48:   */   {
/* 49:39 */     ArrayList<Element> outList = new ArrayList();
/* 50:40 */     NodeList children = parent.getChildNodes();
/* 51:41 */     for (int i = 0; i < children.getLength(); i++)
/* 52:   */     {
/* 53:42 */       Node child = children.item(i);
/* 54:43 */       if (child.getNodeType() == 1)
/* 55:   */       {
/* 56:44 */         Element eChild = (Element)child;
/* 57:45 */         if (eChild.getTagName() == tagname) {
/* 58:46 */           outList.add(eChild);
/* 59:   */         }
/* 60:   */       }
/* 61:   */     }
/* 62:50 */     Element[] out = new Element[outList.size()];
/* 63:51 */     return (Element[])outList.toArray(out);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static Element makeListElement(String name, AnnotationElement[] elements, Document doc)
/* 67:   */   {
/* 68:57 */     Element out = doc.createElement(name);
/* 69:58 */     for (AnnotationElement n : elements) {
/* 70:59 */       out.appendChild(n.toElement(doc));
/* 71:   */     }
/* 72:61 */     return out;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.AnnotationUtils
 * JD-Core Version:    0.7.0.1
 */