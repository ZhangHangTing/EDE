package bupt.zht.iapi;

import org.dom4j.Node;
import org.dom4j.QName;

import javax.wsdl.Definition;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface ProcessConf {
    QName getProcessId();
    QName getType();
    InputStream getCBPFileSize();
    String getBpelDocument();
    URI getBaseURI();
    List<File> getFiles();
    Map<QName,Node> getProcessProperties();
    ProcessState getState();
    Definition getDefinitionForService(QName serviceName);
}
