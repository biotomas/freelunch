/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2012 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */
package freelunch.core.pddlSupport.io.pddl.in;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import freelunch.core.pddlSupport.fLib.utils.io.FileHandling;
import freelunch.core.pddlSupport.io.pddl.in.automata.PddlLexer;
import freelunch.core.pddlSupport.io.pddl.in.automata.PddlParser;
import freelunch.core.pddlSupport.io.pddl.in.objects.*;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOProblem.EMetric;

/**
 *
 * @author FD
 */
public class PDDLFactory {

    private static void error(CommonTree context) {
        printTree(context);
        throw new UnsupportedOperationException();
    }

    private static void printTree(Tree t) {
        StringBuffer out = new StringBuffer();
        recTree(t, 0, out);
        System.out.println(out);
    }

    private static void recTree(Tree t, int indent, StringBuffer out) {
        for (int i = 0; i < indent; i++) {
            out.append(" ");
        }
        out.append(t.getText());
        out.append('\n');
        for (int i = 0; i < t.getChildCount(); i++) {
            recTree(t.getChild(i), indent + 1, out);
        }
    }

    public static IOProblem parseProblem(String path) {
        String data = FileHandling.readFileContents(path);
        CommonTree t = null;
        try {
            t = getTree(data);
        } catch (RecognitionException ex) {
            Logger.getLogger(PDDLFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnsupportedOperationException("Parsing failed for path: " + path);
        }
        IOProblem p = parseProblem(t);
        return p;
    }

    public static IODomain parseDomain(String path) {
        String data = FileHandling.readFileContents(path);
        CommonTree t = null;
        try {
            t = getTree(data);
        } catch (RecognitionException ex) {
            Logger.getLogger(PDDLFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnsupportedOperationException("Parsing failed for path: " + path);
        }
        IODomain d = parseDomain(t);
        return d;
    }

    public static CommonTree getTree(String in) throws RecognitionException {
        PddlLexer lexik = new PddlLexer(new ANTLRStringStream(in));
        CommonTokenStream toks = new CommonTokenStream(lexik);
        PddlParser par = new PddlParser(toks);
        CommonTree tree = (CommonTree) par.pddlDoc().getTree();
        return tree;
    }

    @SuppressWarnings("unchecked")
    private static IODomain parseDomain(CommonTree t) {
        IODomain d = new IODomain();
        for (CommonTree c : (List<CommonTree>) t.getChildren()) {
            switch (c.toString()) {
                case "DOMAIN_NAME":
                    d.domainName = c.getChild(0).getText();
                    break;
                case "REQUIREMENTS":
                    d.requirements = parseRequirements(c);
                    break;
                case "TYPES":
                    d.types = parseTypes(c);
                    break;
                case "CONSTANTS":
                    d.constants = parseConstants(c);
                    break;
                case "PREDICATES":
                    d.predicates = parsePredicates(c);
                    break;
                case "FUNCTIONS":
                    //we are completly skipping this one for now
                    //d.functions = parseFunctions(c);
                    break;
                case "ACTION":
                    IOAction a = parseAction(c);
                    if (d.actions == null) {
                        d.actions = new LinkedList<>();
                    }
                    d.actions.add(a);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown domain keyword.");
            }
        }
        return d;
    }

    @SuppressWarnings("unchecked")
    private static IOProblem parseProblem(CommonTree t) {
        //printTree(t);
        IOProblem p = new IOProblem();
        for (CommonTree c : (List<CommonTree>) t.getChildren()) {
            switch (c.toString()) {
                case "PROBLEM_NAME":
                    p.problemName = c.getChild(0).getText();
                    break;
                case "PROBLEM_DOMAIN":
                    p.domainName = c.getChild(0).getText();
                    break;
                case "OBJECTS":
                    p.objects = parseObjects(c);
                    break;
                case "INIT":
                    for (CommonTree tr : (List<CommonTree>) c.getChildren()) {
                        switch (tr.getText()) {
                            case "INIT_EQ":
                                //we are skipping this for now
                                /*IOFunction ff = new IOFunction();
                                ff.mName = tr.getChild(0).getChild(0).getText();
                                ff.mStartingValue = tr.getChild(1).getText();
                                p.functionInit.add(ff);*/
                                break;
                            case "PRED_INST":
                                IOPredicate pr = new IOPredicate();
                                pr.mName = tr.getChild(0).getText();
                                for (int i = 1; i < tr.getChildCount(); i++) {
                                    pr.mVars.add(new IOVariable(tr.getChild(i).getText(), null));
                                }
                                p.predicateInit.add(pr);
                                break;
                            default:
                                throw new UnsupportedOperationException();
                        }
                    }
                    break;
                case "GOAL":

                    p.goal = parseGoal(c);
                    break;
                case "PROBLEM_METRIC":
                    p.mMetric = parseMetric(c);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown domain keyword.");
            }
        }
        return p;
    }

    @SuppressWarnings("unchecked")
    private static LinkedHashSet<String> parseRequirements(CommonTree c) {
        LinkedHashSet<String> ret = new LinkedHashSet<>();
        for (CommonTree t : (List<CommonTree>) c.getChildren()) {
            ret.add(t.getText());
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOType> parseTypes(CommonTree c) {
        LinkedList<IOType> ret = new LinkedList<>();
        for (CommonTree t : (List<CommonTree>) c.getChildren()) {
            IOType a = new IOType();
            a.mName = t.getText();
            try {
                a.mType = t.getChild(0).getText();
            } catch (Exception e) {
                a.mType = "object"; //object is the default type
            }
            ret.add(a);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOPredicate> parsePredicates(CommonTree c) {
        LinkedList<IOPredicate> ret = new LinkedList<>();
        for (CommonTree t : (List<CommonTree>) c.getChildren()) {
            IOPredicate p = new IOPredicate();
            p.mName = t.getText();
            p.mVars = new LinkedList<>();
            if (t.getChildCount() > 0) {
                for (CommonTree var : (List<CommonTree>) t.getChildren()) {
                    IOVariable varik = new IOVariable();
                    if (var.getChildCount() > 0) {//this is standard parameter
                        varik.mName = null;
                        varik.mType = var.getChild(0).getText();
                    } else { //this is constant
                        varik.mName = var.getText();
                        varik.mType = null;
                    }
                    p.mVars.add(varik);
                }
            }
            ret.add(p);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static IOFunction parseFunction(CommonTree c) {
        c = (CommonTree) c.getChild(0);
        //System.out.println("Parsing...");
        //printTree(c);
        IOFunction f = new IOFunction();
        f.mName = c.getText();
        f.mVars = new LinkedList<>();
        if (c.getChildCount() == 3) {
            //the type is defined
            f.mOutputType = new IOVariable(null, c.getChild(2).getText());
        } else if (c.getChildCount() > 3) {
            //todo - work with multiple types
            throw new UnsupportedOperationException("Type intersections not yet supported!");
        } else {
            //numeric is the default ...or not ?
            f.mOutputType = new IOVariable(null, "number");
        }
        f.isNumeric = f.mOutputType.mType.equals("number");

        if (c.getChildCount() > 0) {
            for (CommonTree tr : (List<CommonTree>) c.getChildren()) {
                f.mVars.add(new IOVariable(tr.getText(), tr.getChild(0).getText()));
            }
        }
        return f;
    }

    @SuppressWarnings({ "unused", "unchecked" })
    private static LinkedList<IOFunction> parseFunctions(CommonTree c) {
        LinkedList<IOFunction> ret = new LinkedList<>();
        for (CommonTree t : (List<CommonTree>) c.getChildren()) {
            IOFunction f = parseFunction(t);
            if (!f.isNumeric) {
                ret.add(f); //TODO: we are throwing away numeric functions ...
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOVariable> parseConstants(CommonTree c) {
        //printTree(c);
        LinkedList<IOVariable> ret = new LinkedList<>();
        for (CommonTree t : (List<CommonTree>) c.getChildren()) {
            IOVariable p = new IOVariable();
            p.mName = t.getText();
            p.mType = t.getChild(0).getText();
            ret.add(p);
        }
        return ret;
    }

    private static IOAction parseAction(CommonTree c) {
        IOAction a = new IOAction();
        a.mName = c.getChild(0).getText();
        a.parameters = parseParams((CommonTree) c.getChild(1));
        a.conditions = parseConditions((CommonTree) c.getChild(2));
        a.effects = parseEffects((CommonTree) c.getChild(3), a);
        return a;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOVariable> parseParams(CommonTree c) {

        LinkedList<IOVariable> ret = new LinkedList<>();
        if (!c.getChild(0).getText().equals("()")) {
            for (CommonTree t : (List<CommonTree>) c.getChildren()) {
                IOVariable varik = new IOVariable();
                varik.mName = t.getText();
                varik.mType = t.getChild(0).getText();
                ret.add(varik);
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOLiteral> parseConditions(CommonTree c) {
        //printTree(c);
        if (c.getText().equals("PRECONDITION")) {
            c = (CommonTree) c.getChild(0);
        }
        LinkedList<IOLiteral> ret = new LinkedList<>();
        switch (c.getText()) {
            case "AND_GD":
                for (CommonTree t : (List<CommonTree>) c.getChildren()) {
                    ret.add(parseFact(t, null));
                }
                break;
            default:
                ret.add(parseFact(c, null));
        }
        while (ret.remove(null));
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOLiteral> parseEffects(CommonTree c, IOAction a) {
        //printTree(c);
        if (c.getText().equals("EFFECT")) {
            c = (CommonTree) c.getChild(0);
        }
        LinkedList<IOLiteral> ret = new LinkedList<>();
        switch (c.getText()) {
            case "AND_EFFECT":
                for (CommonTree t : (List<CommonTree>) c.getChildren()) {
                    ret.add(parseFact(t, a));
                }
                break;
            default:
                ret.add(parseFact(c, a));
        }
        while (ret.remove(null));
        return ret;
    }

    private static IOLiteral parseFact(CommonTree t, IOAction a) {
        IOLiteral f = new IOLiteral();
        if (t.getText().equals("NOT_EFFECT") || t.getText().equals("NOT_GD")) {
            f.positive = false;
            t = (CommonTree) t.getChild(0);
        } else {
            f.positive = true;
        }

        switch (t.getText()) {
            case "ASSIGN_EFFECT":
                //we do not parse those facts at all ...
                //f.numeric = true;
                try {
                    int costIncrease = Integer.parseInt(t.getChild(2).getText());
                    a.cost = costIncrease;
                } catch (NumberFormatException numberFormatException) {
                    a.cost = 1; //HACK!!
                }
                return null;
            case "PRED_HEAD":
                f.mName = t.getChild(0).getText();
                f.vars = new LinkedList<>();
                for (int i = 1; i < t.getChildCount(); i++) {
                    String val = t.getChild(i).getText();
                    IOVariable v = new IOVariable();
                    v.mName = val;
                    f.vars.add(v);
                }
                break;
            default:
                error(t);
        }
        return f;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOVariable> parseObjects(CommonTree c) {
        LinkedList<IOVariable> ret = new LinkedList<>();
        for (CommonTree t : (List<CommonTree>) c.getChildren()) {
            IOVariable v = new IOVariable();
            v.mName = t.getText();
            v.mType = t.getChild(0).getText();
            ret.add(v);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IOLiteral> parseGoal(CommonTree c) {
        //printTree(c);
        LinkedList<IOLiteral> ret = new LinkedList<>();
        c = (CommonTree) c.getChild(0);
        switch (c.getText()) {
            case "AND_GD":
                for (CommonTree t : (List<CommonTree>) c.getChildren()) {
                    ret.add(parseFact(t, null));
                }
                break;
            default:
                ret.add(parseFact(c, null));
        }
        while (ret.remove(null));
        return ret;
    }

    private static EMetric parseMetric(CommonTree c) {
        //printTree(c);
        switch (c.getChild(2).getText()) {
            case "total-cost":
                return EMetric.MINIMIZE_TOTAL_COST;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
