package bupt.zht.iapi;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;

import java.util.List;
import java.util.Map;

public interface Message {
    QName getType();

    List<String> getParts();

    /**
     * Get a message part.
     * @param partName name of the part
     * @return named {@l
     */
    Element getPart(String partName);

    /**
     * Set the message part.
     * @param partName name of part
     * @param content part content
     */
    void setPart(String partName, Element content);

    /**
     * Get a header part.
     * @param partName name of the header part
     * @return named
     */
    Element getHeaderPart(String partName);

    /**
     * Set a header part element.
     * @param name header part name
     * @param content header part element
     */
    void setHeaderPart(String name, Element content);

    /**
     * Set a header part value
     * @param name header part name
     * @param content header part text content
     */
    void setHeaderPart(String name, String content);

    /**
     * Gets all header parts in the message.
     * @return
     */
    Map<String, Node> getHeaderParts();

    /**
     * Set the message as an element. The name of the element is irrelevant,
     * but it should have one child element for each message part.
     * TODO: remove this, temporary hack.
     */
    void setMessage(Element msg);

    /**
     * Get the message as an element. The returned element will have one
     * child element corresponding (and named after) each part in the message.
     * TODO: remove this, temporary hack.
     */
    Element getMessage();
}
