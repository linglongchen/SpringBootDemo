package com.chunqiu.mrjuly.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created on 2018/8/23
 *
 * @author Yin
 */
public class XMLUtil {

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 *
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map parseXmlToMap(String strxml) {

		Map m = new HashMap();

		try {
			strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

			if (null == strxml || "".equals(strxml)) {
				return null;
			}

			InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if (children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = XMLUtil.getChildrenText(children);
				}

				m.put(k, v);
			}

			// 关闭流
			in.close();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (JDOMException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return m;
	}

	/**
	 * 获取子结点的xml
	 *
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	/**
	 * 微信支付将请求参数转换为xml格式的String
	 *
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestXml(SortedMap<String, Object> paramMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set set = paramMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = String.valueOf(entry.getKey());
			String value = String.valueOf(entry.getValue());
			sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 微信支付将请求参数转换为xml格式的String
	 *
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String parseMapToXml(SortedMap<Object, Object> paramMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set set = paramMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = String.valueOf(entry.getKey());
			String value = String.valueOf(entry.getValue());
			sb.append("<" + key + ">" + value + "</" + key + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 微信支付将请求参数转换为xml格式的String
	 *
	 * @param object
	 * @return
	 */
	public static String beanToXml(Object object) {
		XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStream.alias("xml", object.getClass());
		return xStream.toXML(object);
	}

	public static <T> T xmlToBean(String xmlStr, Class<T> clazz) {
		XStream xStream = new XStream() {

			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {

					@SuppressWarnings("rawtypes")
					@Override
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xStream.alias("xml", clazz);
		xStream.processAnnotations(clazz);
		@SuppressWarnings("unchecked")
		T obj = (T) xStream.fromXML(xmlStr);
		return obj;
	}

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。 微信
     *
     * @param strxml
     * @return
     * @throws Exception
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes" })
    public static Map doXMLParse(String strxml) {
        Map<String, Object> map = new HashMap<String, Object>();
        InputStream in = null;
        try {
            strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
            if (null == strxml || "".equals(strxml)) {
                return null;
            }
            in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(in);
            org.w3c.dom.Element root = doc.getDocumentElement();
            NodeList collegeNodes = root.getChildNodes();
            for (int i = 0; i < collegeNodes.getLength(); i++) {
                Node college = collegeNodes.item(i);
                if (college != null && college.getNodeType() == Node.ELEMENT_NODE) {
                    String k = college.getNodeName();
                    String v = college.getTextContent();
                    map.put(k, v);
                }
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        finally {
            // 关闭流
            try {
                in.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * @Description：将请求参数转换为xml格式的string
     * @param parameters
     *            请求参数
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(TreeMap<String, String> parameters) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
