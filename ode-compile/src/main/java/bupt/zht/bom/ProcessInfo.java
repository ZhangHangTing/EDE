package bupt.zht.bom;

import bupt.zht.o.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProcessInfo {
    public String processName;
    public OScope processScope;
    public final Set<OPartnerLink> allPartnerLinks = new HashSet<OPartnerLink>();
//    public final HashSet<OExpressionLanguage> expressionLanguages = new HashSet<OExpressionLanguage>();
    public final List<OProcess.OProperty> properties = new ArrayList<OProcess.OProperty>();
    public final List<Import> allImports = new ArrayList<Import>();
    List<OBase> _children = new ArrayList<OBase>();
}
