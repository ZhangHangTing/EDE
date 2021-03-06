package bupt.zht.o;

import org.dom4j.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;


import javax.xml.namespace.QName;

public class OAssign extends OActivity {
    static final long serivalVersionUID = -1L;
    public final List<Copy> copy = new ArrayList<Copy>();
    public OAssign(OProcess owner,OActivity parent){
        super(owner,parent);
    }
    public static class Copy extends OBase{
        private static final long serialVersionUID = 1L;
        public LValue to;
        public RValue from;
        public Copy(OProcess owner){
            super(owner);
        }
    }
    public interface LValue{
        OScope.Variable getVariable();
    }
    public interface RValue{}
    public static class Literal extends OBase implements RValue {
        private static final long serialVersionUID = 1L;
        public transient String xmlLiteral;

        public Literal(OProcess owner, Document xmlLiteral) {
            super(owner);
            if (xmlLiteral == null)
                throw new IllegalArgumentException("null xmlLiteral!");
            //this.xmlLiteral = DOMUtils.domToString(xmlLiteral);
        }

        public String toString() {
            return "{Literal " + xmlLiteral + "}";
        }

        private void writeObject(java.io.ObjectOutputStream out)
                throws IOException
        {
            out.writeObject(xmlLiteral);
        }

        private void readObject(java.io.ObjectInputStream in)
                throws IOException
        {
            String domStr = null;
            try {
                domStr = (String) in.readObject();
            } catch (ClassNotFoundException e) {
                throw (IOException)(new IOException("XML de-serialization error.")).initCause(e);
            }
            xmlLiteral = domStr;
        }

        public String getXmlLiteral() {
            return xmlLiteral;
        }
    }

    public static class LValueExpression extends OBase implements LValue {
        private static final long serialVersionUID = 1L;
        public OLValueExpression expression;

        public LValueExpression(OProcess owner, OLValueExpression compiledExpression) {
            super(owner);
            this.expression = compiledExpression;
        }

        public String toString() {
            return expression.toString();
        }
        public OScope.Variable getVariable() {
            return expression.getVariable();
        }
    }
    public static class Expression extends OBase implements RValue {
        private static final long serialVersionUID = 1L;
        public OExpression expression;

        public Expression(OProcess owner, OExpression compiledExpression) {
            super(owner);
            this.expression = compiledExpression;
        }

        public String toString() {
            return expression.toString();
        }
    }


    /**
     * Direct reference: selects named child of the message document element.
     * This is used for access to extensions (SOAP headers for example).
     * @author mszefler
     */
    public static class DirectRef extends OBase implements RValue, LValue {
        private static final long serialVersionUID = 1L;
        public DirectRef(OProcess owner) {
            super(owner);
        }

        /** Referenced Variable */
        public OScope.Variable variable;

        /** Name of the element referenced. */
        public QName elName;

        public OScope.Variable getVariable() {
            return variable;
        }
    }

    public static class VariableRef extends OBase implements RValue, LValue {
        private static final long serialVersionUID = 1L;
        public OScope.Variable variable;
        public OExpression location;

        public VariableRef(OProcess owner) {
            super(owner);
        }

        public OScope.Variable getVariable() {
            return variable;
        }

        /**
         * Report whether this is a reference to a whole "message"
         * @return <code>true</code> if whole-message reference
         */
//        public boolean isMessageRef() {
//            return variable.type instanceof OMessageVarType && part == null && headerPart == null && location == null;
//        }

        /**
         * Report whether this is a reference to a message part.
         * @return <code>true</code> if reference to a message part
         */
//        public boolean isPartRef() {
//            return variable.type instanceof OMessageVarType && part != null && location == null;
//        }

//        public boolean isHeaderRef() {
//            return variable.type instanceof OMessageVarType && headerPart != null && location == null;
//        }

//        public String toString() {
//            return "{VarRef " + variable  +
//                    (part==null ? "" : "." + part.name) +
//                    (location == null ? "" : location.toString())+ "}";
//        }
    }

    public static class PropertyRef extends OBase implements RValue, LValue {
        private static final long serialVersionUID = 1L;
        public OScope.Variable variable;
       // public OProcess.OPropertyAlias propertyAlias;

        public PropertyRef(OProcess owner) { super(owner); }

        public OScope.Variable getVariable() {
            return variable;
        }
//        public String toString() {
//            return "{PropRef " + variable + "!" + propertyAlias+ "}";
//        }
    }

    public static class PartnerLinkRef extends OBase implements RValue, LValue {
        private static final long serialVersionUID = 1L;
        public OPartnerLink partnerLink;
        public boolean isMyEndpointReference;

        public PartnerLinkRef(OProcess owner) { super(owner); }

        // Must fit in a LValue even if it's not variable based
        public OScope.Variable getVariable() {
            return null;
        }

        public String toString() {
            return "{PLinkRef " + partnerLink + "!" + isMyEndpointReference + "}";
        }
    }

}
