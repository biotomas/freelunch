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
import java.util.LinkedList;

/**
 *
 * @author FD
 */
public class IOProblem {
    public enum EMetric{
        MINIMIZE_TOTAL_COST, MINIMIZE_TOTAL_TIME
    }
    public String problemName = null;
    public String domainName = null;
    public LinkedList<IOVariable> objects = new LinkedList<>();
    //public LinkedList<IOFunction> functionInit = new LinkedList<>();
    public LinkedList<IOPredicate> predicateInit = new LinkedList<>();
    public LinkedList<IOLiteral> goal = new LinkedList<>();   
    public EMetric mMetric = null;
    
    public void Explain(PrintStream s){
        s.println("--------------------------------");
        s.println("Describing problem: "+problemName);
        s.println("Metric: "+mMetric.name());
        s.println("Objects: ");
        for(IOVariable v:objects){
            s.println(v);
        }
        s.println();
        /*s.println("Functions: ");
        for(IOFunction f:functionInit){
            s.println(f);
        }*/
        s.println("Predicates: ");
        for(IOPredicate f:predicateInit){
            s.println(f);
        }
        s.println("Goal: ");
        for(IOLiteral f:goal){
            s.println(f);
        }
        s.println("-------------------------------");
        
    }
}
