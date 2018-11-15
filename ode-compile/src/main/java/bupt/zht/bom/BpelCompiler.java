package bupt.zht.bom;

import bupt.zht.bom.generator.ActivityGenerator;
import bupt.zht.o.*;
import org.apache.ode.utils.GUID;

import javax.xml.transform.Source;
import java.net.URI;
import java.util.*;

public abstract class BpelCompiler implements CompilerContext{
    private Process _processDef;
    private OProcess _oprocess;
    private HashMap<Class,ActivityGenerator> _actGenerators = new HashMap<Class, ActivityGenerator>();
    private StructureStack _structureStack = new StructureStack();
    private List<OActivity> _compiledActivities = new ArrayList<OActivity>();
    private OExpressionLanguage _konstExprLang;
    private URI _processURI;
    protected BpelCompiler() {
    }
    public Process getProcessDef(){
        return _processDef;
    }
    public OProcess getOProcess(){
        return _oprocess;
    }
    public OLink resolveLink(String linkName){
        OLink ret = null;
        for(Iterator<OActivity> i = _structureStack.iterator(); i.hasNext();){
            OActivity oact = i.next();
            if(oact instanceof OFlow){
                ret = ((OFlow)oact).getLocalLink(linkName);
            }
            if(ret != null){
                return ret;
            }
        }
        return null;
    }
    public OScope.Variable resolveVariable(String varName){
        for(Iterator<OScope> i = _structureStack.oscopeIterator();i.hasNext();){
            OScope.Variable var = i.next().getLocalVariable(varName);
            if(var != null)
                return var;
        }
        return null;
    }

//  _structureStack  public PartnerLinkType resolvePartnerLinkType(String partnerLinkType) {
//        PartnerLinkType plinkType = _wsdlRegistry.getPartnerLinkType(partnerLinkType);
//        if (plinkType == null)
//            throw new CompilationException(__cmsgs.errUndeclaredPartnerLinkType(partnerLinkType));
//        return plinkType;
//    }

    public OPartnerLink resolvePartnerLink(String name) {
        for (Iterator<OScope> i = _structureStack.oscopeIterator(); i.hasNext();) {
            OPartnerLink oplink = i.next().getLocalPartnerLink(name);
            if (oplink != null)
                return oplink;
        }
        return null;
    }
    public OProcess compile(final Process process)  {
        if (process == null)
            throw new NullPointerException("Null process parameter");
        _processURI = process.getURI();
        _processDef = process;
        _structureStack.clear();
        _oprocess = new OProcess();
        _oprocess.guid = null;
//        _oprocess.constants = makeConstants();
//        _oprocess.debugInfo = createDebugInfo(process, "process");
//        if (process.getTargetNamespace() == null) {
//            _oprocess.targetNamespace = "--UNSPECIFIED--";
//            recoveredFromError(process, new CompilationException(__cmsgs.errProcessNamespaceNotSpecified()));
//        } else {
//            _oprocess.targetNamespace = _processDef.getTargetNamespace();
//        }

        if (process.getName() == null) {
            _oprocess.processName = "--UNSPECIFIED--";
        } else {
            _oprocess.processName = _processDef.getName();
        }
       // _oprocess.compileDate = _generatedDate;

        _konstExprLang = new OExpressionLanguage(_oprocess, null);
        //_konstExprLang.debugInfo = createDebugInfo(_processDef, "Constant Value Expression Language");
        _konstExprLang.expressionLanguageUri = "uri:www.fivesight.com/konstExpression";
        _konstExprLang.properties.put("runtime-class",
                "org.apache.ode.bpel.runtime.explang.konst.KonstExpressionLanguageRuntimeImpl");
        _oprocess.expressionLanguages.add(_konstExprLang);

        // Process the imports. Note, we expect all processes (Event BPEL 1.1)
        // to have an import declaration. This should be automatically generated
        // by the 1.1 parser.
//        for (Import imprt : _processDef.getImports()) {
//            try {
//                compile(_processURI, imprt);
//            } catch (CompilationException bce) {
//                // We try to recover from import problems by continuing
//                recoveredFromError(imprt, bce);
//            }
//        }

       // _expressionValidatorFactory.getValidator().bpelImportsLoaded(_processDef, this);

        // compile ALL wsdl properties; needed for property extraction
//        Definition4BPEL[] defs = _wsdlRegistry.getDefinitions();
//        for (Definition4BPEL def : defs) {
//            for (Property property : def.getProperties()) {
//                compile(property);
//            }
//        }
//        // compile ALL wsdl property aliases
//        for (Definition4BPEL def1 : defs) {
//            for (PropertyAlias propertyAlias : def1.getPropertyAliases()) {
//                compile(propertyAlias);
//            }
//        }
        //处理此流程的作用域
        OScope procesScope = new OScope(_oprocess, null);
        procesScope.name = "__PROCESS_SCOPE:" + process.getName();
       // procesScope.debugInfo = createDebugInfo(process, null);
//        _oprocess.procesScope = compileScope(procesScope, process, new Runnable() {
//            public void run() {
//                if (process.getRootActivity() == null) {
//                    System.out.println("process的RootActivity为null");
//                    return;
//                }
//                // Process custom properties are created as variables associated
//                // with the top scope
////                if (_customProcessProperties != null) {
////                    for (Map.Entry<QName, Node> customVar : _customProcessProperties.entrySet()) {
////                        final OScope oscope = _structureStack.topScope();
////                        OVarType varType = new OConstantVarType(_oprocess, customVar.getValue());
////                        OScope.Variable ovar = new OScope.Variable(_oprocess, varType);
////                        ovar.name = customVar.getKey().getLocalPart();
////                        ovar.declaringScope = oscope;
////                        ovar.debugInfo = createDebugInfo(null, "Process custom property variable");
////                        oscope.addLocalVariable(ovar);
////                        if (__log.isDebugEnabled())
////                            __log.debug("Compiled custom property variable " + ovar);
////                    }
////                }
//                //_structureStack.topScope().activity = compile(process.getRootActivity());
//            }
//        });
        assert _structureStack.size() == 0;

        boolean hasErrors = false;
        StringBuffer sb = new StringBuffer();

        //XslTransformHandler.getInstance().clearXSLSheets(_oprocess.getQName());

        //_expressionValidatorFactory.getValidator().bpelCompilationCompleted(_processDef);
         String digest = "version:BPEL2.0" + ";" + _oprocess.processName;
            //为每一个oprocess设置一个guid
        _oprocess.guid = GUID.makeGUID(digest);
        return _oprocess;
    }
//    private OScope compileScope(final OScope oscope, final Scope src, final Runnable init) {
//        if (oscope.name == null)
//            throw new IllegalArgumentException("Unnamed scope:" + src);
//
//        boolean previousAtomicScope = _atomicScope;
//        if (src.getAtomicScope() != null) {
//            boolean newValue = src.getAtomicScope().booleanValue();
//            if (_atomicScope)
//                throw new CompilationException(__cmsgs.errAtomicScopeNesting(newValue));
//
//            oscope.atomicScope = _atomicScope = newValue;
//        }
//        try {
//            compile(oscope, src, new Runnable() {
//                public void run() {
//                    for (Variable var : src.getVariables()) {
//                        try {
//                            compile(var);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(var, ce);
//                        }
//                    }
//                    for (CorrelationSet cset : src.getCorrelationSetDecls()) {
//                        try {
//                            compile(cset);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(cset, ce);
//                        }
//                    }
//                    for (PartnerLink plink : src.getPartnerLinks()) {
//                        try {
//                            compile(plink);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(plink, ce);
//                        }
//                    }
//
//                    if (!src.getEvents().isEmpty() || !src.getAlarms().isEmpty()) {
//                        oscope.eventHandler = new OEventHandler(_oprocess);
//                        oscope.eventHandler.debugInfo = createDebugInfo(src, "Event Handler for " + src);
//                    }
//
//                    for (OnEvent onEvent : src.getEvents()) {
//                        try {
//                            compile(onEvent);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(src, ce);
//                        }
//                    }
//
//                    for (OnAlarm onAlarm : src.getAlarms()) {
//                        try {
//                            compile(onAlarm);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(src, ce);
//                        }
//
//                    }
//
//                    if (init != null)
//                        try {
//                            init.run();
//                        } catch (CompilationException ce) {
//                            recoveredFromError(src, ce);
//                        }
//
//                    try {
//                        compile(src.getCompensationHandler());
//                    } catch (CompilationException bce) {
//                        recoveredFromError(src.getCompensationHandler(), bce);
//                    }
//
//                    try {
//                        compile(src.getTerminationHandler());
//                    } catch (CompilationException bce) {
//                        recoveredFromError(src.getTerminationHandler(), bce);
//                    }
//
//                    try {
//                        compile(src.getFaultHandler());
//                    } catch (CompilationException bce) {
//                        recoveredFromError(src.getFaultHandler(), bce);
//                    }
//                }
//            });
//        } finally {
//            _atomicScope = previousAtomicScope;
//        }
//        return oscope;
//    }
//    public OActivity compile(final Activity source) {
//        if (source == null)
//            throw new IllegalArgumentException("null-argument");
//
//        boolean previousSupressJoinFailure = _supressJoinFailure;
//        switch (source.getSuppressJoinFailure()) {
//            case NO:
//                _supressJoinFailure = false;
//                break;
//            case YES:
//                _supressJoinFailure = true;
//                break;
//        }
//
//        OActivity compiled;
//        try {
//            compiled = (source instanceof ScopeLikeActivity) ? compileSLC((ScopeLikeActivity) source,
//                    new OScope.Variable[0]) : compileActivity(true, source);
//            compiled.suppressJoinFailure = _supressJoinFailure;
//        } finally {
//            _supressJoinFailure = previousSupressJoinFailure;
//        }
//
//        if (__log.isDebugEnabled())
//            __log.debug("Compiled activity " + compiled);
//        return compiled;
//    }
//    private OActivity compileActivity(final boolean doLinks, final Activity source) {
//        final ActivityGenerator actgen = findActivityGen(source);
//        final OActivity oact = actgen.newInstance(source);
//        oact.name = createName(source, "activity");
//        oact.debugInfo = createDebugInfo(source, "Activity body for " + source);
//        _compiledActivities.add(oact);
//        compile(oact, source, new Runnable() {
//            public void run() {
//                if (doLinks)
//                    compileLinks(source);
//                actgen.compile(oact, source);
//            }
//        });
//
//        return oact;
//    }
//    private void compileLinks(Activity source) {
//        /* Source Links Fixup */
//        for (LinkSource ls : source.getLinkSources())
//            compileLinkSource(ls);
//
//        /* Target Links Fixup */
//        for (LinkTarget lt : source.getLinkTargets())
//            compileLinkTarget(lt);
//
//        _structureStack.topActivity().joinCondition = (source.getJoinCondition() == null || source.getLinkTargets()
//                .isEmpty()) ? null : compileJoinCondition(source.getJoinCondition());
//    }
//
//    private String createName(Activity source, String type) {
//        if (source.getName() != null)
//            return source.getName();
//
//        return source.getType().getLocalPart() + "-" + type + "-line-" + source.getLineNo();
//    }
//    private void compileLinkTarget(LinkTarget target) {
//        OLink ol = resolveLink(target.getLinkName());
//        assert ol != null;
//        ol.debugInfo = createDebugInfo(target, target.toString());
//        if (ol.target != null)
//            throw new CompilationException(__cmsgs.errDuplicateLinkTarget(target.getLinkName()).setSource(target));
//        ol.target = _structureStack.topActivity();
//
//        _structureStack.topActivity().targetLinks.add(ol);
//    }
//
//    private void compileLinkSource(LinkSource linksrc) {
//        OLink ol = resolveLink(linksrc.getLinkName());
//        assert ol != null;
//        ol.debugInfo = createDebugInfo(linksrc, linksrc.toString());
//        if (ol.source != null)
//            throw new CompilationException(__cmsgs.errDuplicateLinkSource(linksrc.getLinkName()).setSource(linksrc));
//        ol.source = _structureStack.topActivity();
//        ol.transitionCondition = linksrc.getTransitionCondition() == null ? constantExpr(true) : compileExpr(linksrc
//                .getTransitionCondition());
//
//        _structureStack.topActivity().sourceLinks.add(ol);
//        _structureStack.topActivity().outgoingLinks.add(ol);
//    }
//
//    private void compile(final PartnerLink plink) {
//        OPartnerLink oplink = new OPartnerLink(_oprocess);
//        oplink.debugInfo = createDebugInfo(plink, plink.toString());
//        try {
//            PartnerLinkType plinkType = resolvePartnerLinkType(plink.getPartnerLinkType());
//
//            oplink.partnerLinkType = plinkType.getName();
//            oplink.name = plink.getName();
//            oplink.initializePartnerRole = plink.isInitializePartnerRole();
//
//            if (plink.hasMyRole()) {
//                PartnerLinkType.Role myRole = plinkType.getRole(plink.getMyRole());
//                if (myRole == null)
//                    throw new CompilationException(__cmsgs.errUndeclaredRole(plink.getMyRole(), plinkType.getName()));
//                oplink.myRoleName = myRole.getName();
//                QName portType = myRole.getPortType();
//                if (portType == null)
//                    throw new CompilationException(__cmsgs.errMissingMyRolePortType(myRole.getPortType(), plink.getMyRole(), plinkType.getName()));
//                oplink.myRolePortType = resolvePortType(portType);
//            }
//
//            if (plink.isInitializePartnerRole() && !plink.hasPartnerRole()) {
//                throw new CompilationException(__cmsgs.errPartnerLinkNoPartnerRoleButInitialize(plink.getName()));
//            }
//            if (plink.hasPartnerRole()) {
//                PartnerLinkType.Role partnerRole = plinkType.getRole(plink.getPartnerRole());
//                if (partnerRole == null)
//                    throw new CompilationException(__cmsgs.errUndeclaredRole(plink.getPartnerRole(), plinkType
//                            .getName()));
//                oplink.partnerRoleName = partnerRole.getName();
//                QName portType = partnerRole.getPortType();
//                if (portType == null)
//                    throw new CompilationException(__cmsgs.errMissingPartnerRolePortType(partnerRole.getPortType(), plink.getPartnerRole(), plinkType.getName()));
//                oplink.partnerRolePortType = resolvePortType(portType);
//            }
//
//            oplink.declaringScope = _structureStack.topScope();
//            if (oplink.declaringScope.partnerLinks.containsKey(oplink.name))
//                throw new CompilationException(__cmsgs.errDuplicatePartnerLinkDecl(oplink.name));
//            oplink.declaringScope.partnerLinks.put(oplink.name, oplink);
//            _oprocess.allPartnerLinks.add(oplink);
//        } catch (CompilationException ce) {
//            ce.getCompilationMessage().setSource(plink);
//            throw ce;
//        }
//    }
//
//    private void compile(CorrelationSet cset) {
//        OScope oscope = _structureStack.topScope();
//        OScope.CorrelationSet ocset = new OScope.CorrelationSet(_oprocess);
//        ocset.name = cset.getName();
//        ocset.declaringScope = oscope;
//        ocset.debugInfo = createDebugInfo(cset, cset.toString());
//        QName[] setprops = cset.getProperties();
//        for (int j = 0; j < setprops.length; ++j)
//            ocset.properties.add(resolveProperty(setprops[j]));
//        oscope.addCorrelationSet(ocset);
//    }
//
//    public OActivity getCurrent() {
//        return _structureStack.topActivity();
//    }
//
//    public void compile(OActivity context, BpelObject source, Runnable run) {
//        DefaultActivityGenerator.defaultExtensibilityElements(context, source);
//        _structureStack.push(context,source);
//        try {
//            run.run();
//        } finally {
//            OActivity popped = _structureStack.pop();
//
//            OActivity newtop = _structureStack.topActivity();
//            OScope topScope = _structureStack.topScope();
//
//            if (newtop != null) {
//                newtop.nested.add(popped);
//                // Transfer outgoing and incoming links, excluding the locally defined links.
//                newtop.incomingLinks.addAll(popped.incomingLinks);
//                if (newtop instanceof OFlow) newtop.incomingLinks.removeAll(((OFlow) newtop).localLinks);
//                newtop.outgoingLinks.addAll(popped.outgoingLinks);
//
//                if (newtop instanceof OFlow) newtop.outgoingLinks.removeAll(((OFlow) newtop).localLinks);
//
//                // Transfer variables read/writen
//                newtop.variableRd.addAll(popped.variableRd);
//                newtop.variableWr.addAll(popped.variableWr);
//            }
//
//            if (topScope != null && popped instanceof OScope) topScope.compensatable.add((OScope) popped);
//        }
//    }
//
//    private OScope compileScope(final OScope oscope, final Scope src, final Runnable init) {
//        if (oscope.name == null)
//            throw new IllegalArgumentException("Unnamed scope:" + src);
//
//        oscope.debugInfo = createDebugInfo(src, src.toString());
//
//        boolean previousAtomicScope = _atomicScope;
//        if (src.getAtomicScope() != null) {
//            boolean newValue = src.getAtomicScope().booleanValue();
//            if (_atomicScope)
//                throw new CompilationException(__cmsgs.errAtomicScopeNesting(newValue));
//
//            oscope.atomicScope = _atomicScope = newValue;
//        }
//        try {
//            compile(oscope, src, new Runnable() {
//                public void run() {
//                    for (Variable var : src.getVariables()) {
//                        try {
//                            compile(var);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(var, ce);
//                        }
//                    }
//
//                    for (CorrelationSet cset : src.getCorrelationSetDecls()) {
//                        try {
//                            compile(cset);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(cset, ce);
//                        }
//                    }
//
//                    for (PartnerLink plink : src.getPartnerLinks()) {
//                        try {
//                            compile(plink);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(plink, ce);
//                        }
//                    }
//
//                    if (!src.getEvents().isEmpty() || !src.getAlarms().isEmpty()) {
//                        oscope.eventHandler = new OEventHandler(_oprocess);
//                        oscope.eventHandler.debugInfo = createDebugInfo(src, "Event Handler for " + src);
//                    }
//
//                    for (OnEvent onEvent : src.getEvents()) {
//                        try {
//                            compile(onEvent);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(src, ce);
//                        }
//                    }
//
//                    for (OnAlarm onAlarm : src.getAlarms()) {
//                        try {
//                            compile(onAlarm);
//                        } catch (CompilationException ce) {
//                            recoveredFromError(src, ce);
//                        }
//
//                    }
//
//                    if (init != null)
//                        try {
//                            init.run();
//                        } catch (CompilationException ce) {
//                            recoveredFromError(src, ce);
//                        }
//
//                    try {
//                        compile(src.getCompensationHandler());
//                    } catch (CompilationException bce) {
//                        recoveredFromError(src.getCompensationHandler(), bce);
//                    }
//
//                    try {
//                        compile(src.getTerminationHandler());
//                    } catch (CompilationException bce) {
//                        recoveredFromError(src.getTerminationHandler(), bce);
//                    }
//
//                    try {
//                        compile(src.getFaultHandler());
//                    } catch (CompilationException bce) {
//                        recoveredFromError(src.getFaultHandler(), bce);
//                    }
//                }
//            });
//        } finally {
//            _atomicScope = previousAtomicScope;
//        }
//
//        return oscope;
//    }
//
//    private void compile(final OnAlarm onAlarm) {
//        OScope oscope = _structureStack.topScope();
//        assert oscope.eventHandler != null;
//
//        final OEventHandler.OAlarm oalarm = new OEventHandler.OAlarm(_oprocess);
//        oalarm.debugInfo = createDebugInfo(onAlarm, "OnAlarm Event Handler: " + onAlarm);
//
//        if (onAlarm.getFor() != null && onAlarm.getUntil() == null) {
//            oalarm.forExpr = compileExpr(onAlarm.getFor());
//        } else if (onAlarm.getFor() == null && onAlarm.getUntil() != null) {
//            oalarm.untilExpr = compileExpr(onAlarm.getUntil());
//        } else if (onAlarm.getFor() != null && onAlarm.getUntil() != null) {
//            throw new CompilationException(__cmsgs.errInvalidAlarm().setSource(onAlarm));
//        } else if (onAlarm.getRepeatEvery() == null) {
//            throw new CompilationException(__cmsgs.errInvalidAlarm().setSource(onAlarm));
//        }
//
//        if (onAlarm.getRepeatEvery() != null)
//            oalarm.repeatExpr = compileExpr(onAlarm.getRepeatEvery());
//
//        if (onAlarm.getActivity() == null) throw new CompilationException(__cmsgs.errInvalidAlarm().setSource(onAlarm));
//        oalarm.activity = compile(onAlarm.getActivity());
//
//        // Check links crossing restrictions.
//        for (OLink link : oalarm.incomingLinks)
//            try {
//                throw new CompilationException(__cmsgs.errLinkCrossesEventHandlerBoundary(link.name).setSource(onAlarm));
//            } catch (CompilationException ce) {
//                recoveredFromError(onAlarm, ce);
//            }
//
//        for (OLink link : oalarm.outgoingLinks)
//            try {
//                throw new CompilationException(__cmsgs.errLinkCrossesEventHandlerBoundary(link.name).setSource(onAlarm));
//            } catch (CompilationException ce) {
//                recoveredFromError(onAlarm, ce);
//            }
//
//        oscope.eventHandler.onAlarms.add(oalarm);
//    }
//
//    private void compile(final OnEvent onEvent) {
//        final OScope oscope = _structureStack.topScope();
//        assert oscope.eventHandler != null;
//
//        final OEventHandler.OEvent oevent = new OEventHandler.OEvent(_oprocess, oscope);
//        oevent.name = "__eventHandler:";
//        oevent.debugInfo = createDebugInfo(onEvent, null);
//        compile(oevent, onEvent, new Runnable() {
//            public void run() {
//                switch (_processDef.getBpelVersion()) {
//                    case BPEL11:
//                        oevent.variable = resolveMessageVariable(onEvent.getVariable());
//                        break;
//                    case BPEL20_DRAFT:
//                    case BPEL20:
//                        if (onEvent.getMessageType() == null && onEvent.getElementType() == null)
//                            throw new CompilationException(__cmsgs.errVariableDeclMissingType(onEvent.getVariable())
//                                    .setSource(onEvent));
//                        if (onEvent.getMessageType() != null && onEvent.getElementType() != null)
//                            throw new CompilationException(__cmsgs.errVariableDeclInvalid(onEvent.getVariable()).setSource(
//                                    onEvent));
//
//                        OVarType varType;
//                        if (onEvent.getMessageType() != null)
//                            varType = resolveMessageType(onEvent.getMessageType());
//                        else if (onEvent.getElement() != null)
//                            varType = resolveElementType(onEvent.getElementType());
//                        else
//                            throw new CompilationException(__cmsgs
//                                    .errUnrecognizedVariableDeclaration(onEvent.getVariable()));
//
//                        oevent.variable = new OScope.Variable(_oprocess, varType);
//                        oevent.variable.name = onEvent.getVariable();
//                        oevent.variable.declaringScope = _structureStack.topScope();
//
//                        oevent.addLocalVariable(oevent.variable);
//                        break;
//                    default:
//                        throw new AssertionError("Unexpected BPEL VERSION constatnt: " + _processDef.getBpelVersion());
//                }
//
//                oevent.partnerLink = resolvePartnerLink(onEvent.getPartnerLink());
//                oevent.operation = resolveMyRoleOperation(oevent.partnerLink, onEvent.getOperation());
//                oevent.messageExchangeId = onEvent.getMessageExchangeId();
//                oevent.route = onEvent.getRoute();
//
//                if (onEvent.getPortType() != null
//                        && !onEvent.getPortType().equals(oevent.partnerLink.myRolePortType.getQName()))
//                    throw new CompilationException(__cmsgs.errPortTypeMismatch(onEvent.getPortType(),
//                            oevent.partnerLink.myRolePortType.getQName()));
//
//                Set<String> csetNames = new HashSet<String>(); // prevents duplicate cset in on one set of correlations
//                for (Correlation correlation : onEvent.getCorrelations()) {
//                    if( csetNames.contains(correlation.getCorrelationSet() ) ) {
//                        throw new CompilationException(__cmsgs.errDuplicateUseCorrelationSet(correlation
//                                .getCorrelationSet()));
//                    }
//
//                    OScope.CorrelationSet cset = resolveCorrelationSet(correlation.getCorrelationSet());
//
//                    switch (correlation.getInitiate()) {
//                        case UNSET:
//                        case NO:
//                            oevent.matchCorrelations.add(cset);
//                            oevent.partnerLink.addCorrelationSetForOperation(oevent.operation, cset, false);
//                            break;
//                        case YES:
//                            oevent.initCorrelations.add(cset);
//                            break;
//                        case JOIN:
//                            cset.hasJoinUseCases = true;
//                            oevent.joinCorrelations.add(cset);
//                            oevent.partnerLink.addCorrelationSetForOperation(oevent.operation, cset, true);
//                    }
//
//                    for (OProcess.OProperty property : cset.properties) {
//                        // Force resolution of alias, to make sure that we have
//                        // one for this variable-property pair.
//                        resolvePropertyAlias(oevent.variable, property.name);
//                    }
//
//                    csetNames.add(correlation.getCorrelationSet());
//                }
//
//                if (onEvent.getActivity() == null) throw new CompilationException(__cmsgs.errInvalidAlarm().setSource(onEvent));
//                oevent.activity = compile(onEvent.getActivity());
//            }
//        });
//
//        // Check links crossing restrictions.
//        for (OLink link : oevent.incomingLinks)
//            try {
//                throw new CompilationException(__cmsgs.errLinkCrossesEventHandlerBoundary(link.name));
//            } catch (CompilationException ce) {
//                recoveredFromError(onEvent, ce);
//            }
//
//        for (OLink link : oevent.outgoingLinks)
//            try {
//                throw new CompilationException(__cmsgs.errLinkCrossesEventHandlerBoundary(link.name));
//            } catch (CompilationException ce) {
//                recoveredFromError(onEvent, ce);
//            }
//
//        oscope.eventHandler.onMessages.add(oevent);
//    }
//    private void compile(final Variable src) {
//        final OScope oscope = _structureStack.topScope();
//
//        if (src.getKind() == null)
//            throw new CompilationException(__cmsgs.errVariableDeclMissingType(src.getName()).setSource(src));
//
//        if (oscope.getLocalVariable(src.getName()) != null)
//            throw new CompilationException(__cmsgs.errDuplicateVariableDecl(src.getName()).setSource(src));
//
//        if (src.getTypeName() == null) throw new CompilationException(__cmsgs.errUnrecognizedVariableDeclaration(src.getName()));
//        OVarType varType;
//        switch (src.getKind()) {
//            case ELEMENT:
//                varType = resolveElementType(src.getTypeName());
//                break;
//            case MESSAGE:
//                varType = resolveMessageType(src.getTypeName());
//                break;
//            case SCHEMA:
//                varType = resolveXsdType(src.getTypeName());
//                break;
//            default:
//                throw new CompilationException(__cmsgs.errUnrecognizedVariableDeclaration(src.getName()));
//        }
//
//        OScope.Variable ovar = new OScope.Variable(_oprocess, varType);
//        ovar.name = src.getName();
//        ovar.declaringScope = oscope;
//        ovar.debugInfo = createDebugInfo(src, null);
//
//        ovar.extVar = compileExtVar(src);
//
//        oscope.addLocalVariable(ovar);
//
//        if (__log.isDebugEnabled())
//            __log.debug("Compiled variable " + ovar);
//    }
//
//
//    private void compile(TerminationHandler terminationHandler) {
//        OScope oscope = _structureStack.topScope();
//        oscope.terminationHandler = new OTerminationHandler(_oprocess, oscope);
//        oscope.terminationHandler.name = "__terminationHandler:" + oscope.name;
//        oscope.terminationHandler.debugInfo = createDebugInfo(terminationHandler, null);
//        if (terminationHandler == null) {
//            oscope.terminationHandler.activity = createDefaultCompensateActivity(null,
//                    "Auto-generated 'compensate all' pseudo-activity for default termination handler on "
//                            + oscope.toString());
//        } else {
//            _recoveryContextStack.push(oscope);
//            try {
//                oscope.terminationHandler.activity = compile(terminationHandler.getActivity());
//            } finally {
//                _recoveryContextStack.pop();
//            }
//        }
//    }
//
//    private void compile(CompensationHandler compensationHandler) {
//        OScope oscope = _structureStack.topScope();
//        oscope.compensationHandler = new OCompensationHandler(_oprocess, oscope);
//        oscope.compensationHandler.name = "__compenationHandler_" + oscope.name;
//        oscope.compensationHandler.debugInfo = createDebugInfo(compensationHandler, null);
//        if (compensationHandler == null) {
//            oscope.compensationHandler.activity = createDefaultCompensateActivity(compensationHandler,
//                    "Auto-generated 'compensate all' pseudo-activity for default compensation handler on  "
//                            + oscope.toString());
//        } else {
//            _recoveryContextStack.push(oscope);
//            try {
//                oscope.compensationHandler.activity = compile(compensationHandler.getActivity());
//            } finally {
//                _recoveryContextStack.pop();
//            }
//        }
//    }
//
//    private void compile(FaultHandler fh) {
//        OScope oscope = _structureStack.topScope();
//        oscope.faultHandler = new OFaultHandler(_oprocess);
//        if (fh == null) {
//            // The default fault handler compensates all child activities
//            // AND then rethrows the fault!
//            final OCatch defaultCatch = new OCatch(_oprocess, oscope);
//            defaultCatch.name = "__defaultFaultHandler:" + oscope.name;
//            defaultCatch.faultName = null; // catch any fault
//            defaultCatch.faultVariable = null;
//            OSequence sequence = new OSequence(_oprocess, defaultCatch);
//            sequence.name = "__defaultFaultHandler_sequence:" + oscope.name;
//            sequence.debugInfo = createDebugInfo(fh, "Auto-generated sequence activity.");
//            ORethrow rethrow = new ORethrow(_oprocess, sequence);
//            rethrow.name = "__defaultFaultHandler_rethrow:" + oscope.name;
//            rethrow.debugInfo = createDebugInfo(fh, "Auto-generated re-throw activity.");
//            sequence.sequence.add(createDefaultCompensateActivity(fh, "Default compensation handler for " + oscope));
//            sequence.sequence.add(rethrow);
//
//            defaultCatch.activity = sequence;
//            oscope.faultHandler.catchBlocks.add(defaultCatch);
//            if (__log.isDebugEnabled())
//                __log.debug("Compiled default catch block " + defaultCatch + " for " + oscope);
//
//        } else {
//            _recoveryContextStack.push(oscope);
//            try {
//
//                int i = 0;
//                for (final Catch catchSrc : fh.getCatches()) {
//                    final OCatch ctch = new OCatch(_oprocess, oscope);
//                    ctch.debugInfo = createDebugInfo(catchSrc, catchSrc.toString());
//                    ctch.name = "__catch#" + i + ":" + _structureStack.topScope().name;
//                    ctch.faultName = catchSrc.getFaultName();
//                    compile(ctch, catchSrc, new Runnable() {
//                        public void run() {
//
//                            if (catchSrc.getFaultVariable() != null) {
//                                OScope.Variable faultVar;
//                                switch (_processDef.getBpelVersion()) {
//                                    case BPEL11:
//                                        faultVar = resolveVariable(catchSrc.getFaultVariable());
//                                        if (!(faultVar.type instanceof OMessageVarType))
//                                            throw new CompilationException(__cmsgs.errMessageVariableRequired(
//                                                    catchSrc.getFaultVariable()).setSource(catchSrc));
//                                        break;
//                                    case BPEL20_DRAFT:
//                                    case BPEL20:
//                                        if (catchSrc.getFaultVariableMessageType() == null
//                                                && catchSrc.getFaultVariableElementType() == null)
//                                            throw new CompilationException(__cmsgs.errVariableDeclMissingType(
//                                                    catchSrc.getFaultVariable()).setSource(catchSrc));
//                                        if (catchSrc.getFaultVariableMessageType() != null
//                                                && catchSrc.getFaultVariableElementType() != null)
//                                            throw new CompilationException(__cmsgs.errVariableDeclMissingType(
//                                                    catchSrc.getFaultVariable()).setSource(catchSrc));
//
//                                        OVarType faultVarType;
//                                        if (catchSrc.getFaultVariableMessageType() != null)
//                                            faultVarType = resolveMessageType(catchSrc.getFaultVariableMessageType());
//                                        else if (catchSrc.getFaultVariableElementType() != null)
//                                            faultVarType = resolveElementType(catchSrc.getFaultVariableElementType());
//                                        else
//                                            throw new CompilationException(__cmsgs
//                                                    .errUnrecognizedVariableDeclaration(catchSrc.getFaultVariable()));
//
//                                        faultVar = new OScope.Variable(_oprocess, faultVarType);
//                                        faultVar.name = catchSrc.getFaultVariable();
//                                        faultVar.declaringScope = _structureStack.topScope();
//
//                                        ctch.addLocalVariable(faultVar);
//                                        break;
//                                    default:
//                                        throw new AssertionError("Unexpected BPEL VERSION constatnt: "
//                                                + _processDef.getBpelVersion());
//                                }
//
//                                ctch.faultVariable = faultVar;
//                            }
//
//                            if (catchSrc.getActivity() == null)
//                                throw new CompilationException(__cmsgs.errEmptyCatch().setSource(catchSrc));
//                            _structureStack.topScope().activity = compile(catchSrc.getActivity());
//                        }
//                    });
//                    oscope.faultHandler.catchBlocks.add(ctch);
//                    ++i;
//                }
//            } finally {
//                _recoveryContextStack.pop();
//            }
//
//        }
//    }

    public List<OScope.Variable> getAccessibleVariables() {
        return null;
    }

    public OActivity compile(Activity child) {
        return null;
    }

    public String getSourceLocation() {
        return null;
    }

    public boolean isPartnerLinkAssigned(String plink) {
        return false;
    }

    public OActivity getCurrent() {
        return null;
    }

    public Map<URI, Source> getSchemaSources() {
        return null;
    }

    public URI getBaseResourceURI() {
        return null;
    }
    protected void registerActivityCompiler(Class defClass,ActivityGenerator generator){
        _actGenerators.put(defClass,generator);
    }
    public List<OActivity> getActivityStack() {
        ArrayList<OActivity> rval = new ArrayList<OActivity>(_structureStack._stack);
        Collections.reverse(rval);
        return rval;
    }
    private static class StructureStack {
        private Stack<OActivity> _stack = new Stack<OActivity>();
        private Map<OActivity,BpelObject> _srcMap = new HashMap<OActivity,BpelObject>();

        public void push(OActivity act, BpelObject src) {
            _stack.push(act);
            _srcMap.put(act,src);
        }

        public BpelObject topSource() {
            return _srcMap.get(topActivity());
        }

        public OScope topScope() {
            List<OScope> scopeStack = scopeStack();
            return scopeStack.isEmpty() ? null : scopeStack.get(scopeStack.size() - 1);
        }

        public OScope rootScope() {
            for (OActivity oActivity : _stack)
                if (oActivity instanceof OScope)
                    return (OScope) oActivity;
            return null;
        }

        public OActivity pop() {
            return _stack.pop();
        }

        public void clear() {
            _stack.clear();
        }

        public int size() {
            return _stack.size();
        }

        public Iterator<OScope> oscopeIterator() {
            List<OScope> scopeStack = scopeStack();
            Collections.reverse(scopeStack);
            return scopeStack.iterator();
        }

        private List<OScope> scopeStack() {
            ArrayList<OScope> newList = new ArrayList<OScope>();
           // CollectionsX.filter(newList, _stack.iterator(), OScope.class);
            return newList;
        }

        public OActivity topActivity() {
            return _stack.isEmpty() ? null : _stack.peek();
        }

        public Iterator<OActivity> iterator() {
            ArrayList<OActivity> rval = new ArrayList<OActivity>(_stack);
            Collections.reverse(rval);
            return rval.iterator();
        }
    }
}
