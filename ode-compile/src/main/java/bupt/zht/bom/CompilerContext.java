package bupt.zht.bom;

import bupt.zht.o.*;

import javax.xml.transform.Source;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface CompilerContext {
    //OExpression
   // OExpression constantExpr(boolean value);

//    OExpression compileJoinCondition(Expression expr)
//            throws CompilationException;
//
//    OExpression compileExpr(Expression expr)
//            throws CompilationException;
//
//    OExpression compileExpr(Expression expr, OVarType rootNodeType, Object requestedResultType, Object[] resultType)
//            throws CompilationException;
//
//    OLValueExpression compileLValueExpr(Expression expr)
//            throws CompilationException;
//
//    OLValueExpression compileLValueExpr(Expression expr, OVarType rootNodeType, Object requestedResultType, Object[] resultType)
//            throws CompilationException;

//    OExpression compileExpr(String locationstr, NSContext nsContext)
//            throws CompilationException;
//
//    OXslSheet compileXslt(String docStrUri)
//            throws CompilationException;
//
//    OXsdTypeVarType resolveXsdType(QName typeName)
//            throws CompilationException;
//
//    OProcess.OProperty resolveProperty(QName name)
//            throws CompilationException;
//
//    OScope.Variable resolveVariable(String name)
//            throws CompilationException;

    List<OScope.Variable> getAccessibleVariables();

//    OScope.Variable resolveMessageVariable(String inputVar)
//            throws CompilationException;
//
//    OScope.Variable resolveMessageVariable(String inputVar, QName messageType)
//            throws CompilationException;
//
//    OMessageVarType.Part resolvePart(OScope.Variable variable, String partname)
//            throws CompilationException;
//
//    OMessageVarType.Part resolveHeaderPart(OScope.Variable variable, String partname)
//            throws CompilationException;

    OActivity compile(Activity child);
//
//    OScope compileSLC(ScopeLikeActivity child, Variable[] variables);
//
//    OPartnerLink resolvePartnerLink(String name)
//            throws CompilationException;
//
//    Operation resolvePartnerRoleOperation(OPartnerLink partnerLink, String operationName)
//            throws CompilationException;
//
//    Operation resolveMyRoleOperation(OPartnerLink partnerLink, String operationName)
//            throws CompilationException;
//
//    OProcess.OPropertyAlias resolvePropertyAlias(OScope.Variable variable, QName property)
//            throws CompilationException;
//
//    void recoveredFromError(SourceLocation where, CompilationException bce)
//            throws CompilationException;

    OLink resolveLink(String linkName);

  // CompilationException OScope resolveCompensatableScope(String scopeToCompensate)
    //        throws CompilationException;


    OProcess getOProcess();


    //OScope.CorrelationSet resolveCorrelationSet(String csetName)

    String getSourceLocation();

    boolean isPartnerLinkAssigned(String plink);

    List<OActivity> getActivityStack();

    OActivity getCurrent();

    Map<URI, Source> getSchemaSources();

    /**
     * Retrieves the base URI that the BPEL Process execution contextis running relative to.
     *
     * @return URI - the URI representing the absolute physical file path location that this process is defined within.
     */
    URI getBaseResourceURI();
}
