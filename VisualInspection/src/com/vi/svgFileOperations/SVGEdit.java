package com.vi.svgFileOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


public class SVGEdit  {	
	  
	  public void writeEnhancedSVG_useStream(PrintWriter out, InputStream svgStream, String itemNr, String version,
				BasicDataSource dataSource, String absoluteURL) {

			try {

				InputStream is = svgStream;

				DocumentBuilderFactory domfac = DocumentBuilderFactory
						.newInstance();

				DocumentBuilder dombuilder = null;

				dombuilder = domfac.newDocumentBuilder();

				dombuilder.setEntityResolver(new EntityResolver() {
					public InputSource resolveEntity(String publicId,
							String systemId) throws SAXException, IOException {
						return new InputSource(ServletActionContext
								.getServletContext().getRealPath("/")
								+ "svg10.dtd");
					}
				});

				Document doc = null;
				
				//build DOM tree from SVG
				doc = dombuilder.parse(is);

				if (null != is) {
					is.close();
				}

				Element documentElement = doc.getDocumentElement();

				//add onload='Init(evt)' 
				documentElement.setAttribute("onload", "Init(evt,'" + absoluteURL + "')");
				//add javascript functionality part1
				documentElement.setAttribute("contentScriptType", "text/ecmascript");
				//add link functionality
				documentElement.setAttribute("xmlns:xlink","http://www.w3.org/1999/xlink");

				//add javascript functionality part2
				Element script = doc.createElement("script");
				script.setAttribute("xlink:href", "../js/displayPartInfo.js");

				//will use Xpaths to select elements from the tree
				XPathFactory factory = XPathFactory.newInstance();

				//get first child of svg, insert script node before it
				XPath findFirstChild = factory.newXPath();
				Node firstChild = (Node) findFirstChild.evaluate("/svg/*[1]", doc,
						XPathConstants.NODE);

				documentElement.insertBefore(script, firstChild);

				documentElement = null;
				script = null;
				firstChild = null;
				findFirstChild = null;

				//find draft element, it contains all relevant nodes (ignore defs)
				XPath findDraft = factory.newXPath();
				Element draft = (Element) findDraft.evaluate("/svg/g[@id='draft']",
						doc, XPathConstants.NODE);
				findDraft = null;

				//get text size that matches size of the other stuff, get the size from the A5E...-label			

				XPath xpath = factory.newXPath();
				XPathExpression expr = xpath
					//.compile("/svg/g/g/text//@font-size");
				.compile("/svg/g/g/text/tspan/@font-size[ contains(..,'A5E')]");
				;
				Double textSize = (Double) expr
						.evaluate(doc, XPathConstants.NUMBER);

				//this will be needed many times:
				Element text = doc.createElement("text");
				text.setAttribute("color", "rgb(0,255,255)");
				text.setAttribute("display", "none");
				text.setAttribute("font-size",textSize.toString());
				text.setAttribute("text-anchor", "middle");
				text.setAttribute("writing-mode", "lr-tb");
				text.setAttribute("fill", "currentColor");
				//let it ignore events
				text.setAttribute("pointer-events", "none");

				Element tspan = doc.createElement("tspan");
				tspan.setAttribute("font-size", textSize.toString());

				//prepare node for label
				Node textNode = doc.createTextNode("");

				//put all together
				tspan.appendChild(textNode);
				text.appendChild(tspan);

				//find all children of draft that have an id
				XPath findDraftChildren = factory.newXPath();
				NodeList draftChildren = (NodeList) findDraftChildren.evaluate(
						"g[@id='ID_0']/g", draft, XPathConstants.NODESET);
				findDraftChildren = null;

				//get the part information from the database
				Connection conn = null;
				CallableStatement coll = null;

				conn = dataSource.getConnection();

				//query DB for each element
				for (int i = 0; i < draftChildren.getLength(); i++) {
					Element element = (Element) draftChildren.item(i);

					//add events
					element.setAttribute("onmousemove","ShowMyTooltip(evt, true)");
					element.setAttribute("onclick","aMouseClick(evt)");
					element.setAttribute("onmouseout", "ShowMyTooltip(evt, false)");					
					String refdes = element.getAttribute("id");
					//System.out.print(refdes + '\n');
					
					//calling the stored procedure which gets the label
					//PROCEDURE GET_LABEL(in_itemNr IN VARCHAR2,in_versionAS IN VARCHAR2,
					//in_refdes IN VARCHAR2,out_component_nr OUT VARCHAR2,out_label OUT VARCHAR2);
					coll = conn.prepareCall("{call PCBVI.PKG_GET_LABEL.GET_LABEL(?,?,?,?,?)}");
					//prepare input and output arguments 
					coll.setString(1, itemNr);
					coll.setString(2, version);
					coll.setString(3, refdes);
					coll.registerOutParameter(4, OracleTypes.VARCHAR);
					coll.registerOutParameter(5, OracleTypes.VARCHAR);

					coll.execute();

					//retrieve label and componentNr from query result
					String componentNr = coll.getString(4);
					String label = coll.getString(5);
					coll.close();
					if (null != componentNr) {
						//modify file/tree:
						
						// firstChild2 is <use tag
						Element firstChild2 = (Element) element.getFirstChild()
								.getNextSibling();
						
						//add this attributes to get events also inside the element, not just when touching the border:
						firstChild2.setAttribute("fill","white");
						firstChild2.setAttribute("fill-opacity","0");
						//String abcString=firstChild2.getAttribute("stroke-width");
						//double stoke=Float.parseFloat(abcString);
						//firstChild2.setAttribute("stroke-width",String.valueOf(10* Float.parseFloat(firstChild2.getAttribute("stroke-width"))));
						
						//get position
						String transformVal = firstChild2.getAttribute("transform");
						//only need "translate-part"
						transformVal = transformVal.substring(0, transformVal
								.indexOf(')') + 1);
						//mirror text											
						transformVal = transformVal + " scale(1 -1)";

						//change text to label-text
						if (null != label) {
							textNode.setNodeValue(refdes + " | Comp.Nr.: "
									+ componentNr + " | Label: " + label);
						} else {
							textNode.setNodeValue(refdes + " | Comp.Nr.: "
									+ componentNr + " | Label:");
						}

						//get copy of the prepared node
						Element individualized = (Element) text.cloneNode(true);
						//set transform attribute
						individualized.setAttribute("transform", transformVal);

						//add it to the element
						element.appendChild(individualized);

					}
				}

				if (conn != null) {
					conn.close();
				}

				if (coll != null) {
					coll.close();
				}

				// now that the changes are done output the SVG to the servlet response
				XMLSerializer serializer = new XMLSerializer((Writer) out,
						new OutputFormat(doc, "UTF-8", true));
				serializer.serialize(doc);
				
			} catch (IOException e) {
				out.println("IO Error: " + e.getMessage());
				e.printStackTrace(out);
			} catch (ParserConfigurationException e) {
				out.println("Parser Configuration Error: " + e.getMessage());
				e.printStackTrace(out);
			} catch (SAXException e) {
				out.println("SAX Excepion: " + e.getMessage());
				e.printStackTrace(out);
			} catch (XPathExpressionException e) {
				out.println("XPath Exception: " + e.getMessage());
				e.printStackTrace(out);
			} catch (SQLException e) {
				out.println("SQL Exception: " + e.getMessage());
				e.printStackTrace(out);
			}

		}



}



