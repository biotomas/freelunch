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
package freelunch.core.pddlSupport.io.pddl.in.objects;

import java.io.PrintStream;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 *
 * @author FD
 */
public class IODomain {

    public String domainName = null;
    public LinkedHashSet<String> requirements = new LinkedHashSet<>(); //without ":"
    public LinkedList<IOType> types = new LinkedList<>();
    public LinkedList<IOVariable> constants = new LinkedList<>();
    //public LinkedList<IOFunction> functions = new LinkedList<>();
    public LinkedList<IOPredicate> predicates = new LinkedList<>();
    public LinkedList<IOAction> actions = new LinkedList<>();
    
    
    public void Explain(PrintStream s){
        s.println("--------------------------------");
        s.println("Describing domain: "+domainName);
        //s.println("Metric: "+mMetric.name());
        s.println("Requirements: ");
        for(String v:requirements){
            s.println(v);
        }
        s.println();
        s.println("Types: ");
        for(IOType f:types){
            s.println(f);
        }
        s.println("Constants: ");
        for(IOVariable f:constants){
            s.println(f);
        }
        /*s.println("Functions: ");
        for(IOFunction f:functions){
            s.println(f);
        }*/
        s.println("Predicates: ");
        for(IOPredicate f:predicates){
            s.println(f);
        }
        s.println("Actions: ");
        for(IOAction f:actions){
            s.println(f);
        }
        s.println("-------------------------------");
    }
}
