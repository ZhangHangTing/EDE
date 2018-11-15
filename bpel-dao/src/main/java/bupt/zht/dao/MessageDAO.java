package bupt.zht.dao;

import org.dom4j.Element;
import org.dom4j.QName;

public interface MessageDAO {
    void setType(QName type);
    QName getType();
    void setData(Element value);
    Element getData();
    void setHeader(Element value);
    Element getHeader();
    MessageExchangeDAO getMessageExchange();
}
