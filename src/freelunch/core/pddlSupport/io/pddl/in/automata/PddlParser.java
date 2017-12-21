// $ANTLR 3.4 C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g 2012-11-15 16:32:00
package freelunch.core.pddlSupport.io.pddl.in.automata;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


/**
 * PDDL grammar for ANTLR
 * Filip Dvorak, filip.dvorak@runbox.com
 * LAAS-CNRS / Charles University in Prague
 * 
 *
 */
@SuppressWarnings({"all", "warnings", "unchecked"})
public class PddlParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "AND_EFFECT", "AND_GD", "ANY_CHAR", "ASSIGN_EFFECT", "BINARY_OP", "COMPARISON_GD", "CONSTANTS", "DIGIT", "DOMAIN", "DOMAIN_NAME", "DURATIVE_ACTION", "EFFECT", "EITHER_TYPE", "EXISTS_GD", "FORALL_EFFECT", "FORALL_GD", "FUNCTIONS", "FUNCTION_BODY", "FUNC_HEAD", "GOAL", "IMPLY_GD", "INIT", "INIT_AT", "INIT_EQ", "LETTER", "LINE_COMMENT", "NAME", "NOT_EFFECT", "NOT_GD", "NOT_PRED_INIT", "NUMBER", "OBJECTS", "OR_GD", "OTHERBODY", "PRECONDITION", "PREDICATES", "PRED_HEAD", "PRED_INST", "PROBLEM", "PROBLEM_CONSTRAINT", "PROBLEM_DOMAIN", "PROBLEM_METRIC", "PROBLEM_NAME", "REQUIREMENTS", "REQUIRE_KEY", "TYPES", "UNARY_MINUS", "VARIABLE", "WHEN_EFFECT", "WHITESPACE", "'('", "'()'", "')'", "'*'", "'+'", "'-'", "'/'", "':action'", "':condition'", "':constants'", "':constraints'", "':derived'", "':domain'", "':duration'", "':durative-action'", "':effect'", "':functions'", "':goal'", "':init'", "':metric'", "':objects'", "':parameters'", "':precondition'", "':predicates'", "':requirements'", "':types'", "'<'", "'<='", "'<remove_this_if_you_know_what_you_are_doing>at'", "'<remove_this_if_you_know_what_you_are_doing>at-most-once'", "'='", "'>'", "'>='", "'?duration'", "'all'", "'always'", "'always-within'", "'and'", "'assign'", "'decrease'", "'define'", "'domain'", "'either'", "'end'", "'exists'", "'forall'", "'hold-after'", "'hold-during'", "'imply'", "'increase'", "'is-violated'", "'maximize'", "'minimize'", "'not'", "'number'", "'or'", "'over'", "'preference'", "'problem'", "'scale-down'", "'scale-up'", "'sometime'", "'sometime-after'", "'sometime-before'", "'start'", "'total-time'", "'when'", "'within'"
    };

    public static final int EOF=-1;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int T__102=102;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__110=110;
    public static final int T__111=111;
    public static final int T__112=112;
    public static final int T__113=113;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__120=120;
    public static final int T__121=121;
    public static final int T__122=122;
    public static final int ACTION=4;
    public static final int AND_EFFECT=5;
    public static final int AND_GD=6;
    public static final int ANY_CHAR=7;
    public static final int ASSIGN_EFFECT=8;
    public static final int BINARY_OP=9;
    public static final int COMPARISON_GD=10;
    public static final int CONSTANTS=11;
    public static final int DIGIT=12;
    public static final int DOMAIN=13;
    public static final int DOMAIN_NAME=14;
    public static final int DURATIVE_ACTION=15;
    public static final int EFFECT=16;
    public static final int EITHER_TYPE=17;
    public static final int EXISTS_GD=18;
    public static final int FORALL_EFFECT=19;
    public static final int FORALL_GD=20;
    public static final int FUNCTIONS=21;
    public static final int FUNCTION_BODY=22;
    public static final int FUNC_HEAD=23;
    public static final int GOAL=24;
    public static final int IMPLY_GD=25;
    public static final int INIT=26;
    public static final int INIT_AT=27;
    public static final int INIT_EQ=28;
    public static final int LETTER=29;
    public static final int LINE_COMMENT=30;
    public static final int NAME=31;
    public static final int NOT_EFFECT=32;
    public static final int NOT_GD=33;
    public static final int NOT_PRED_INIT=34;
    public static final int NUMBER=35;
    public static final int OBJECTS=36;
    public static final int OR_GD=37;
    public static final int OTHERBODY=38;
    public static final int PRECONDITION=39;
    public static final int PREDICATES=40;
    public static final int PRED_HEAD=41;
    public static final int PRED_INST=42;
    public static final int PROBLEM=43;
    public static final int PROBLEM_CONSTRAINT=44;
    public static final int PROBLEM_DOMAIN=45;
    public static final int PROBLEM_METRIC=46;
    public static final int PROBLEM_NAME=47;
    public static final int REQUIREMENTS=48;
    public static final int REQUIRE_KEY=49;
    public static final int TYPES=50;
    public static final int UNARY_MINUS=51;
    public static final int VARIABLE=52;
    public static final int WHEN_EFFECT=53;
    public static final int WHITESPACE=54;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public PddlParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public PddlParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
        this.state.ruleMemo = new HashMap[207+1];
         

    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return PddlParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g"; }


    	public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
            String hdr = getErrorHeader(e);
            String msg = getErrorMessage(e, tokenNames);
            
            // Now do something with hdr and msg...
            System.out.println(hdr);
            System.out.println(msg);
            throw new UnsupportedOperationException("Parsing error");
        }


    public static class pddlDoc_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "pddlDoc"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:78:1: pddlDoc : ( domain | problem );
    public final PddlParser.pddlDoc_return pddlDoc() throws RecognitionException {
        PddlParser.pddlDoc_return retval = new PddlParser.pddlDoc_return();
        retval.start = input.LT(1);

        int pddlDoc_StartIndex = input.index();

        Object root_0 = null;

        PddlParser.domain_return domain1 =null;

        PddlParser.problem_return problem2 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:78:9: ( domain | problem )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==55) ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1==95) ) {
                    int LA1_2 = input.LA(3);

                    if ( (LA1_2==55) ) {
                        int LA1_3 = input.LA(4);

                        if ( (LA1_3==96) ) {
                            alt1=1;
                        }
                        else if ( (LA1_3==113) ) {
                            alt1=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 3, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 2, input);

                        throw nvae;

                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:78:11: domain
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_domain_in_pddlDoc246);
                    domain1=domain();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, domain1.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:78:20: problem
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_problem_in_pddlDoc250);
                    problem2=problem();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, problem2.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 1, pddlDoc_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "pddlDoc"


    public static class domain_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "domain"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:82:1: domain : '(' 'define' domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* ')' -> ^( DOMAIN domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* ) ;
    public final PddlParser.domain_return domain() throws RecognitionException {
        PddlParser.domain_return retval = new PddlParser.domain_return();
        retval.start = input.LT(1);

        int domain_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal3=null;
        Token string_literal4=null;
        Token char_literal13=null;
        PddlParser.domainName_return domainName5 =null;

        PddlParser.requireDef_return requireDef6 =null;

        PddlParser.typesDef_return typesDef7 =null;

        PddlParser.constantsDef_return constantsDef8 =null;

        PddlParser.predicatesDef_return predicatesDef9 =null;

        PddlParser.functionsDef_return functionsDef10 =null;

        PddlParser.constraints_return constraints11 =null;

        PddlParser.structureDef_return structureDef12 =null;


        Object char_literal3_tree=null;
        Object string_literal4_tree=null;
        Object char_literal13_tree=null;
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_constantsDef=new RewriteRuleSubtreeStream(adaptor,"rule constantsDef");
        RewriteRuleSubtreeStream stream_requireDef=new RewriteRuleSubtreeStream(adaptor,"rule requireDef");
        RewriteRuleSubtreeStream stream_predicatesDef=new RewriteRuleSubtreeStream(adaptor,"rule predicatesDef");
        RewriteRuleSubtreeStream stream_constraints=new RewriteRuleSubtreeStream(adaptor,"rule constraints");
        RewriteRuleSubtreeStream stream_structureDef=new RewriteRuleSubtreeStream(adaptor,"rule structureDef");
        RewriteRuleSubtreeStream stream_domainName=new RewriteRuleSubtreeStream(adaptor,"rule domainName");
        RewriteRuleSubtreeStream stream_functionsDef=new RewriteRuleSubtreeStream(adaptor,"rule functionsDef");
        RewriteRuleSubtreeStream stream_typesDef=new RewriteRuleSubtreeStream(adaptor,"rule typesDef");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:83:5: ( '(' 'define' domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* ')' -> ^( DOMAIN domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:83:7: '(' 'define' domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* ')'
            {
            char_literal3=(Token)match(input,55,FOLLOW_55_in_domain265); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal3);


            string_literal4=(Token)match(input,95,FOLLOW_95_in_domain267); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_95.add(string_literal4);


            pushFollow(FOLLOW_domainName_in_domain269);
            domainName5=domainName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_domainName.add(domainName5.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:84:7: ( requireDef )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==55) ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==79) ) {
                    alt2=1;
                }
            }
            switch (alt2) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:84:7: requireDef
                    {
                    pushFollow(FOLLOW_requireDef_in_domain277);
                    requireDef6=requireDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_requireDef.add(requireDef6.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:85:7: ( typesDef )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==55) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==80) ) {
                    alt3=1;
                }
            }
            switch (alt3) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:85:7: typesDef
                    {
                    pushFollow(FOLLOW_typesDef_in_domain286);
                    typesDef7=typesDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_typesDef.add(typesDef7.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:86:7: ( constantsDef )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==55) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==64) ) {
                    alt4=1;
                }
            }
            switch (alt4) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:86:7: constantsDef
                    {
                    pushFollow(FOLLOW_constantsDef_in_domain296);
                    constantsDef8=constantsDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_constantsDef.add(constantsDef8.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:87:7: ( predicatesDef )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==55) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==78) ) {
                    alt5=1;
                }
            }
            switch (alt5) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:87:7: predicatesDef
                    {
                    pushFollow(FOLLOW_predicatesDef_in_domain305);
                    predicatesDef9=predicatesDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_predicatesDef.add(predicatesDef9.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:88:7: ( functionsDef )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==55) ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==71) ) {
                    alt6=1;
                }
            }
            switch (alt6) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:88:7: functionsDef
                    {
                    pushFollow(FOLLOW_functionsDef_in_domain314);
                    functionsDef10=functionsDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_functionsDef.add(functionsDef10.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:89:7: ( constraints )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==55) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==65) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:89:7: constraints
                    {
                    pushFollow(FOLLOW_constraints_in_domain323);
                    constraints11=constraints();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_constraints.add(constraints11.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:90:7: ( structureDef )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==55) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:90:7: structureDef
            	    {
            	    pushFollow(FOLLOW_structureDef_in_domain332);
            	    structureDef12=structureDef();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_structureDef.add(structureDef12.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            char_literal13=(Token)match(input,57,FOLLOW_57_in_domain341); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal13);


            // AST REWRITE
            // elements: structureDef, functionsDef, domainName, constantsDef, typesDef, requireDef, constraints, predicatesDef
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 92:7: -> ^( DOMAIN domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:92:10: ^( DOMAIN domainName ( requireDef )? ( typesDef )? ( constantsDef )? ( predicatesDef )? ( functionsDef )? ( constraints )? ( structureDef )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(DOMAIN, "DOMAIN")
                , root_1);

                adaptor.addChild(root_1, stream_domainName.nextTree());

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:92:30: ( requireDef )?
                if ( stream_requireDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_requireDef.nextTree());

                }
                stream_requireDef.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:92:42: ( typesDef )?
                if ( stream_typesDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_typesDef.nextTree());

                }
                stream_typesDef.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:93:17: ( constantsDef )?
                if ( stream_constantsDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_constantsDef.nextTree());

                }
                stream_constantsDef.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:93:31: ( predicatesDef )?
                if ( stream_predicatesDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_predicatesDef.nextTree());

                }
                stream_predicatesDef.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:93:46: ( functionsDef )?
                if ( stream_functionsDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_functionsDef.nextTree());

                }
                stream_functionsDef.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:94:17: ( constraints )?
                if ( stream_constraints.hasNext() ) {
                    adaptor.addChild(root_1, stream_constraints.nextTree());

                }
                stream_constraints.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:94:30: ( structureDef )*
                while ( stream_structureDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_structureDef.nextTree());

                }
                stream_structureDef.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 2, domain_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "domain"


    public static class domainName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "domainName"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:97:1: domainName : '(' 'domain' NAME ')' -> ^( DOMAIN_NAME NAME ) ;
    public final PddlParser.domainName_return domainName() throws RecognitionException {
        PddlParser.domainName_return retval = new PddlParser.domainName_return();
        retval.start = input.LT(1);

        int domainName_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal14=null;
        Token string_literal15=null;
        Token NAME16=null;
        Token char_literal17=null;

        Object char_literal14_tree=null;
        Object string_literal15_tree=null;
        Object NAME16_tree=null;
        Object char_literal17_tree=null;
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:98:5: ( '(' 'domain' NAME ')' -> ^( DOMAIN_NAME NAME ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:98:7: '(' 'domain' NAME ')'
            {
            char_literal14=(Token)match(input,55,FOLLOW_55_in_domainName425); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal14);


            string_literal15=(Token)match(input,96,FOLLOW_96_in_domainName427); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_96.add(string_literal15);


            NAME16=(Token)match(input,NAME,FOLLOW_NAME_in_domainName429); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME16);


            char_literal17=(Token)match(input,57,FOLLOW_57_in_domainName431); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal17);


            // AST REWRITE
            // elements: NAME
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 99:6: -> ^( DOMAIN_NAME NAME )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:99:9: ^( DOMAIN_NAME NAME )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(DOMAIN_NAME, "DOMAIN_NAME")
                , root_1);

                adaptor.addChild(root_1, 
                stream_NAME.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 3, domainName_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "domainName"


    public static class requireDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "requireDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:102:1: requireDef : '(' ':requirements' ( REQUIRE_KEY )+ ')' -> ^( REQUIREMENTS ( REQUIRE_KEY )+ ) ;
    public final PddlParser.requireDef_return requireDef() throws RecognitionException {
        PddlParser.requireDef_return retval = new PddlParser.requireDef_return();
        retval.start = input.LT(1);

        int requireDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal18=null;
        Token string_literal19=null;
        Token REQUIRE_KEY20=null;
        Token char_literal21=null;

        Object char_literal18_tree=null;
        Object string_literal19_tree=null;
        Object REQUIRE_KEY20_tree=null;
        Object char_literal21_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_REQUIRE_KEY=new RewriteRuleTokenStream(adaptor,"token REQUIRE_KEY");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:103:2: ( '(' ':requirements' ( REQUIRE_KEY )+ ')' -> ^( REQUIREMENTS ( REQUIRE_KEY )+ ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:103:4: '(' ':requirements' ( REQUIRE_KEY )+ ')'
            {
            char_literal18=(Token)match(input,55,FOLLOW_55_in_requireDef458); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal18);


            string_literal19=(Token)match(input,79,FOLLOW_79_in_requireDef460); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_79.add(string_literal19);


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:103:24: ( REQUIRE_KEY )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==REQUIRE_KEY) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:103:24: REQUIRE_KEY
            	    {
            	    REQUIRE_KEY20=(Token)match(input,REQUIRE_KEY,FOLLOW_REQUIRE_KEY_in_requireDef462); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_REQUIRE_KEY.add(REQUIRE_KEY20);


            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            char_literal21=(Token)match(input,57,FOLLOW_57_in_requireDef465); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal21);


            // AST REWRITE
            // elements: REQUIRE_KEY
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 104:2: -> ^( REQUIREMENTS ( REQUIRE_KEY )+ )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:104:5: ^( REQUIREMENTS ( REQUIRE_KEY )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(REQUIREMENTS, "REQUIREMENTS")
                , root_1);

                if ( !(stream_REQUIRE_KEY.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_REQUIRE_KEY.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_REQUIRE_KEY.nextNode()
                    );

                }
                stream_REQUIRE_KEY.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 4, requireDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "requireDef"


    public static class typesDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typesDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:107:1: typesDef : '(' ':types' typedNameList ')' -> ^( TYPES typedNameList ) ;
    public final PddlParser.typesDef_return typesDef() throws RecognitionException {
        PddlParser.typesDef_return retval = new PddlParser.typesDef_return();
        retval.start = input.LT(1);

        int typesDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal22=null;
        Token string_literal23=null;
        Token char_literal25=null;
        PddlParser.typedNameList_return typedNameList24 =null;


        Object char_literal22_tree=null;
        Object string_literal23_tree=null;
        Object char_literal25_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleSubtreeStream stream_typedNameList=new RewriteRuleSubtreeStream(adaptor,"rule typedNameList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:108:2: ( '(' ':types' typedNameList ')' -> ^( TYPES typedNameList ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:108:4: '(' ':types' typedNameList ')'
            {
            char_literal22=(Token)match(input,55,FOLLOW_55_in_typesDef486); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal22);


            string_literal23=(Token)match(input,80,FOLLOW_80_in_typesDef488); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_80.add(string_literal23);


            pushFollow(FOLLOW_typedNameList_in_typesDef490);
            typedNameList24=typedNameList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_typedNameList.add(typedNameList24.getTree());

            char_literal25=(Token)match(input,57,FOLLOW_57_in_typesDef492); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal25);


            // AST REWRITE
            // elements: typedNameList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 109:4: -> ^( TYPES typedNameList )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:109:7: ^( TYPES typedNameList )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(TYPES, "TYPES")
                , root_1);

                adaptor.addChild(root_1, stream_typedNameList.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 5, typesDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typesDef"


    public static class typedNameList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typedNameList"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:113:1: typedNameList : ( ( NAME )* | ( singleTypeNameList )+ ( NAME )* ) ;
    public final PddlParser.typedNameList_return typedNameList() throws RecognitionException {
        PddlParser.typedNameList_return retval = new PddlParser.typedNameList_return();
        retval.start = input.LT(1);

        int typedNameList_StartIndex = input.index();

        Object root_0 = null;

        Token NAME26=null;
        Token NAME28=null;
        PddlParser.singleTypeNameList_return singleTypeNameList27 =null;


        Object NAME26_tree=null;
        Object NAME28_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:5: ( ( ( NAME )* | ( singleTypeNameList )+ ( NAME )* ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:7: ( ( NAME )* | ( singleTypeNameList )+ ( NAME )* )
            {
            root_0 = (Object)adaptor.nil();


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:7: ( ( NAME )* | ( singleTypeNameList )+ ( NAME )* )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:8: ( NAME )*
                    {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:8: ( NAME )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==NAME) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:8: NAME
                    	    {
                    	    NAME26=(Token)match(input,NAME,FOLLOW_NAME_in_typedNameList519); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NAME26_tree = 
                    	    (Object)adaptor.create(NAME26)
                    	    ;
                    	    adaptor.addChild(root_0, NAME26_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:16: ( singleTypeNameList )+ ( NAME )*
                    {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:16: ( singleTypeNameList )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        alt11 = dfa11.predict(input);
                        switch (alt11) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:16: singleTypeNameList
                    	    {
                    	    pushFollow(FOLLOW_singleTypeNameList_in_typedNameList524);
                    	    singleTypeNameList27=singleTypeNameList();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, singleTypeNameList27.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:36: ( NAME )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==NAME) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:114:36: NAME
                    	    {
                    	    NAME28=(Token)match(input,NAME,FOLLOW_NAME_in_typedNameList527); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NAME28_tree = 
                    	    (Object)adaptor.create(NAME28)
                    	    ;
                    	    adaptor.addChild(root_0, NAME28_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 6, typedNameList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typedNameList"


    public static class singleTypeNameList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "singleTypeNameList"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:117:1: singleTypeNameList : ( ( NAME )+ '-' t= type ) -> ( ^( NAME $t) )+ ;
    public final PddlParser.singleTypeNameList_return singleTypeNameList() throws RecognitionException {
        PddlParser.singleTypeNameList_return retval = new PddlParser.singleTypeNameList_return();
        retval.start = input.LT(1);

        int singleTypeNameList_StartIndex = input.index();

        Object root_0 = null;

        Token NAME29=null;
        Token char_literal30=null;
        PddlParser.type_return t =null;


        Object NAME29_tree=null;
        Object char_literal30_tree=null;
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:118:5: ( ( ( NAME )+ '-' t= type ) -> ( ^( NAME $t) )+ )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:118:7: ( ( NAME )+ '-' t= type )
            {
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:118:7: ( ( NAME )+ '-' t= type )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:118:8: ( NAME )+ '-' t= type
            {
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:118:8: ( NAME )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==NAME) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:118:8: NAME
            	    {
            	    NAME29=(Token)match(input,NAME,FOLLOW_NAME_in_singleTypeNameList547); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_NAME.add(NAME29);


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            char_literal30=(Token)match(input,60,FOLLOW_60_in_singleTypeNameList550); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_60.add(char_literal30);


            pushFollow(FOLLOW_type_in_singleTypeNameList554);
            t=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(t.getTree());

            }


            // AST REWRITE
            // elements: NAME, t
            // token labels: 
            // rule labels: retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (Object)adaptor.nil();
            // 119:4: -> ( ^( NAME $t) )+
            {
                if ( !(stream_NAME.hasNext()||stream_t.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_NAME.hasNext()||stream_t.hasNext() ) {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:119:7: ^( NAME $t)
                    {
                    Object root_1 = (Object)adaptor.nil();
                    root_1 = (Object)adaptor.becomeRoot(
                    stream_NAME.nextNode()
                    , root_1);

                    adaptor.addChild(root_1, stream_t.nextTree());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_NAME.reset();
                stream_t.reset();

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 7, singleTypeNameList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "singleTypeNameList"


    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:122:1: type : ( ( '(' 'either' ( primType )+ ')' ) -> ^( EITHER_TYPE ( primType )+ ) | primType );
    public final PddlParser.type_return type() throws RecognitionException {
        PddlParser.type_return retval = new PddlParser.type_return();
        retval.start = input.LT(1);

        int type_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal31=null;
        Token string_literal32=null;
        Token char_literal34=null;
        PddlParser.primType_return primType33 =null;

        PddlParser.primType_return primType35 =null;


        Object char_literal31_tree=null;
        Object string_literal32_tree=null;
        Object char_literal34_tree=null;
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_primType=new RewriteRuleSubtreeStream(adaptor,"rule primType");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:123:2: ( ( '(' 'either' ( primType )+ ')' ) -> ^( EITHER_TYPE ( primType )+ ) | primType )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==55) ) {
                alt16=1;
            }
            else if ( (LA16_0==NAME) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:123:4: ( '(' 'either' ( primType )+ ')' )
                    {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:123:4: ( '(' 'either' ( primType )+ ')' )
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:123:6: '(' 'either' ( primType )+ ')'
                    {
                    char_literal31=(Token)match(input,55,FOLLOW_55_in_type581); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal31);


                    string_literal32=(Token)match(input,97,FOLLOW_97_in_type583); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_97.add(string_literal32);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:123:19: ( primType )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==NAME) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:123:19: primType
                    	    {
                    	    pushFollow(FOLLOW_primType_in_type585);
                    	    primType33=primType();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_primType.add(primType33.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);


                    char_literal34=(Token)match(input,57,FOLLOW_57_in_type588); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal34);


                    }


                    // AST REWRITE
                    // elements: primType
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 124:4: -> ^( EITHER_TYPE ( primType )+ )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:124:7: ^( EITHER_TYPE ( primType )+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(EITHER_TYPE, "EITHER_TYPE")
                        , root_1);

                        if ( !(stream_primType.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_primType.hasNext() ) {
                            adaptor.addChild(root_1, stream_primType.nextTree());

                        }
                        stream_primType.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:125:4: primType
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_primType_in_type607);
                    primType35=primType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primType35.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 8, type_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "type"


    public static class primType_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "primType"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:128:1: primType : NAME ;
    public final PddlParser.primType_return primType() throws RecognitionException {
        PddlParser.primType_return retval = new PddlParser.primType_return();
        retval.start = input.LT(1);

        int primType_StartIndex = input.index();

        Object root_0 = null;

        Token NAME36=null;

        Object NAME36_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:128:10: ( NAME )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:128:12: NAME
            {
            root_0 = (Object)adaptor.nil();


            NAME36=(Token)match(input,NAME,FOLLOW_NAME_in_primType617); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME36_tree = 
            (Object)adaptor.create(NAME36)
            ;
            adaptor.addChild(root_0, NAME36_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 9, primType_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "primType"


    public static class functionsDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionsDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:130:1: functionsDef : '(' ':functions' functionList ')' -> ^( FUNCTIONS functionList ) ;
    public final PddlParser.functionsDef_return functionsDef() throws RecognitionException {
        PddlParser.functionsDef_return retval = new PddlParser.functionsDef_return();
        retval.start = input.LT(1);

        int functionsDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal37=null;
        Token string_literal38=null;
        Token char_literal40=null;
        PddlParser.functionList_return functionList39 =null;


        Object char_literal37_tree=null;
        Object string_literal38_tree=null;
        Object char_literal40_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleSubtreeStream stream_functionList=new RewriteRuleSubtreeStream(adaptor,"rule functionList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:131:2: ( '(' ':functions' functionList ')' -> ^( FUNCTIONS functionList ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:131:4: '(' ':functions' functionList ')'
            {
            char_literal37=(Token)match(input,55,FOLLOW_55_in_functionsDef627); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal37);


            string_literal38=(Token)match(input,71,FOLLOW_71_in_functionsDef629); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_71.add(string_literal38);


            pushFollow(FOLLOW_functionList_in_functionsDef631);
            functionList39=functionList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_functionList.add(functionList39.getTree());

            char_literal40=(Token)match(input,57,FOLLOW_57_in_functionsDef633); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal40);


            // AST REWRITE
            // elements: functionList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 132:2: -> ^( FUNCTIONS functionList )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:132:5: ^( FUNCTIONS functionList )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(FUNCTIONS, "FUNCTIONS")
                , root_1);

                adaptor.addChild(root_1, stream_functionList.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 10, functionsDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "functionsDef"


    public static class functionList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionList"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:136:1: functionList : ( fOuterBody ^)* ;
    public final PddlParser.functionList_return functionList() throws RecognitionException {
        PddlParser.functionList_return retval = new PddlParser.functionList_return();
        retval.start = input.LT(1);

        int functionList_StartIndex = input.index();

        Object root_0 = null;

        PddlParser.fOuterBody_return fOuterBody41 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:137:2: ( ( fOuterBody ^)* )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:137:4: ( fOuterBody ^)*
            {
            root_0 = (Object)adaptor.nil();


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:137:4: ( fOuterBody ^)*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==55) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:137:5: fOuterBody ^
            	    {
            	    pushFollow(FOLLOW_fOuterBody_in_functionList655);
            	    fOuterBody41=fOuterBody();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(fOuterBody41.getTree(), root_0);

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 11, functionList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "functionList"


    public static class fOuterBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fOuterBody"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:139:1: fOuterBody : functionBody -> ^( FUNCTION_BODY functionBody ) ;
    public final PddlParser.fOuterBody_return fOuterBody() throws RecognitionException {
        PddlParser.fOuterBody_return retval = new PddlParser.fOuterBody_return();
        retval.start = input.LT(1);

        int fOuterBody_StartIndex = input.index();

        Object root_0 = null;

        PddlParser.functionBody_return functionBody42 =null;


        RewriteRuleSubtreeStream stream_functionBody=new RewriteRuleSubtreeStream(adaptor,"rule functionBody");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:140:2: ( functionBody -> ^( FUNCTION_BODY functionBody ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:140:4: functionBody
            {
            pushFollow(FOLLOW_functionBody_in_fOuterBody671);
            functionBody42=functionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_functionBody.add(functionBody42.getTree());

            // AST REWRITE
            // elements: functionBody
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 140:17: -> ^( FUNCTION_BODY functionBody )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:140:20: ^( FUNCTION_BODY functionBody )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(FUNCTION_BODY, "FUNCTION_BODY")
                , root_1);

                adaptor.addChild(root_1, stream_functionBody.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 12, fOuterBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fOuterBody"


    public static class functionBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionBody"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:142:1: functionBody : atomicFunctionSkeleton ( '-' functionType )? ;
    public final PddlParser.functionBody_return functionBody() throws RecognitionException {
        PddlParser.functionBody_return retval = new PddlParser.functionBody_return();
        retval.start = input.LT(1);

        int functionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal44=null;
        PddlParser.atomicFunctionSkeleton_return atomicFunctionSkeleton43 =null;

        PddlParser.functionType_return functionType45 =null;


        Object char_literal44_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:143:2: ( atomicFunctionSkeleton ( '-' functionType )? )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:144:3: atomicFunctionSkeleton ( '-' functionType )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_atomicFunctionSkeleton_in_functionBody692);
            atomicFunctionSkeleton43=atomicFunctionSkeleton();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicFunctionSkeleton43.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:144:26: ( '-' functionType )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==60) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:144:27: '-' functionType
                    {
                    char_literal44=(Token)match(input,60,FOLLOW_60_in_functionBody695); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal44_tree = 
                    (Object)adaptor.create(char_literal44)
                    ;
                    adaptor.addChild(root_0, char_literal44_tree);
                    }

                    pushFollow(FOLLOW_functionType_in_functionBody697);
                    functionType45=functionType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionType45.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 13, functionBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "functionBody"


    public static class atomicFunctionSkeleton_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "atomicFunctionSkeleton"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:149:1: atomicFunctionSkeleton : '(' ! functionSymbol ^ typedVariableList ')' !;
    public final PddlParser.atomicFunctionSkeleton_return atomicFunctionSkeleton() throws RecognitionException {
        PddlParser.atomicFunctionSkeleton_return retval = new PddlParser.atomicFunctionSkeleton_return();
        retval.start = input.LT(1);

        int atomicFunctionSkeleton_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal46=null;
        Token char_literal49=null;
        PddlParser.functionSymbol_return functionSymbol47 =null;

        PddlParser.typedVariableList_return typedVariableList48 =null;


        Object char_literal46_tree=null;
        Object char_literal49_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:150:2: ( '(' ! functionSymbol ^ typedVariableList ')' !)
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:150:4: '(' ! functionSymbol ^ typedVariableList ')' !
            {
            root_0 = (Object)adaptor.nil();


            char_literal46=(Token)match(input,55,FOLLOW_55_in_atomicFunctionSkeleton715); if (state.failed) return retval;

            pushFollow(FOLLOW_functionSymbol_in_atomicFunctionSkeleton718);
            functionSymbol47=functionSymbol();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(functionSymbol47.getTree(), root_0);

            pushFollow(FOLLOW_typedVariableList_in_atomicFunctionSkeleton721);
            typedVariableList48=typedVariableList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList48.getTree());

            char_literal49=(Token)match(input,57,FOLLOW_57_in_atomicFunctionSkeleton723); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 14, atomicFunctionSkeleton_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "atomicFunctionSkeleton"


    public static class functionSymbol_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionSymbol"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:156:1: functionSymbol : NAME ;
    public final PddlParser.functionSymbol_return functionSymbol() throws RecognitionException {
        PddlParser.functionSymbol_return retval = new PddlParser.functionSymbol_return();
        retval.start = input.LT(1);

        int functionSymbol_StartIndex = input.index();

        Object root_0 = null;

        Token NAME50=null;

        Object NAME50_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:156:16: ( NAME )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:156:18: NAME
            {
            root_0 = (Object)adaptor.nil();


            NAME50=(Token)match(input,NAME,FOLLOW_NAME_in_functionSymbol740); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME50_tree = 
            (Object)adaptor.create(NAME50)
            ;
            adaptor.addChild(root_0, NAME50_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 15, functionSymbol_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "functionSymbol"


    public static class functionType_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionType"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:158:1: functionType : 'number' ;
    public final PddlParser.functionType_return functionType() throws RecognitionException {
        PddlParser.functionType_return retval = new PddlParser.functionType_return();
        retval.start = input.LT(1);

        int functionType_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal51=null;

        Object string_literal51_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:158:14: ( 'number' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:158:16: 'number'
            {
            root_0 = (Object)adaptor.nil();


            string_literal51=(Token)match(input,109,FOLLOW_109_in_functionType749); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal51_tree = 
            (Object)adaptor.create(string_literal51)
            ;
            adaptor.addChild(root_0, string_literal51_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 16, functionType_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "functionType"


    public static class constantsDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constantsDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:160:1: constantsDef : '(' ':constants' typedNameList ')' -> ^( CONSTANTS typedNameList ) ;
    public final PddlParser.constantsDef_return constantsDef() throws RecognitionException {
        PddlParser.constantsDef_return retval = new PddlParser.constantsDef_return();
        retval.start = input.LT(1);

        int constantsDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal52=null;
        Token string_literal53=null;
        Token char_literal55=null;
        PddlParser.typedNameList_return typedNameList54 =null;


        Object char_literal52_tree=null;
        Object string_literal53_tree=null;
        Object char_literal55_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_typedNameList=new RewriteRuleSubtreeStream(adaptor,"rule typedNameList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:161:2: ( '(' ':constants' typedNameList ')' -> ^( CONSTANTS typedNameList ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:161:4: '(' ':constants' typedNameList ')'
            {
            char_literal52=(Token)match(input,55,FOLLOW_55_in_constantsDef760); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal52);


            string_literal53=(Token)match(input,64,FOLLOW_64_in_constantsDef762); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(string_literal53);


            pushFollow(FOLLOW_typedNameList_in_constantsDef764);
            typedNameList54=typedNameList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_typedNameList.add(typedNameList54.getTree());

            char_literal55=(Token)match(input,57,FOLLOW_57_in_constantsDef766); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal55);


            // AST REWRITE
            // elements: typedNameList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 162:2: -> ^( CONSTANTS typedNameList )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:162:5: ^( CONSTANTS typedNameList )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CONSTANTS, "CONSTANTS")
                , root_1);

                adaptor.addChild(root_1, stream_typedNameList.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 17, constantsDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "constantsDef"


    public static class predicatesDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "predicatesDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:165:1: predicatesDef : '(' ':predicates' ( atomicFormulaSkeleton )+ ')' -> ^( PREDICATES ( atomicFormulaSkeleton )+ ) ;
    public final PddlParser.predicatesDef_return predicatesDef() throws RecognitionException {
        PddlParser.predicatesDef_return retval = new PddlParser.predicatesDef_return();
        retval.start = input.LT(1);

        int predicatesDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal56=null;
        Token string_literal57=null;
        Token char_literal59=null;
        PddlParser.atomicFormulaSkeleton_return atomicFormulaSkeleton58 =null;


        Object char_literal56_tree=null;
        Object string_literal57_tree=null;
        Object char_literal59_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_atomicFormulaSkeleton=new RewriteRuleSubtreeStream(adaptor,"rule atomicFormulaSkeleton");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:166:2: ( '(' ':predicates' ( atomicFormulaSkeleton )+ ')' -> ^( PREDICATES ( atomicFormulaSkeleton )+ ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:166:4: '(' ':predicates' ( atomicFormulaSkeleton )+ ')'
            {
            char_literal56=(Token)match(input,55,FOLLOW_55_in_predicatesDef786); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal56);


            string_literal57=(Token)match(input,78,FOLLOW_78_in_predicatesDef788); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal57);


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:166:22: ( atomicFormulaSkeleton )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==55) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:166:22: atomicFormulaSkeleton
            	    {
            	    pushFollow(FOLLOW_atomicFormulaSkeleton_in_predicatesDef790);
            	    atomicFormulaSkeleton58=atomicFormulaSkeleton();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_atomicFormulaSkeleton.add(atomicFormulaSkeleton58.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt19 >= 1 ) break loop19;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
            } while (true);


            char_literal59=(Token)match(input,57,FOLLOW_57_in_predicatesDef793); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal59);


            // AST REWRITE
            // elements: atomicFormulaSkeleton
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 167:2: -> ^( PREDICATES ( atomicFormulaSkeleton )+ )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:167:5: ^( PREDICATES ( atomicFormulaSkeleton )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PREDICATES, "PREDICATES")
                , root_1);

                if ( !(stream_atomicFormulaSkeleton.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_atomicFormulaSkeleton.hasNext() ) {
                    adaptor.addChild(root_1, stream_atomicFormulaSkeleton.nextTree());

                }
                stream_atomicFormulaSkeleton.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 18, predicatesDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "predicatesDef"


    public static class atomicFormulaSkeleton_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "atomicFormulaSkeleton"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:170:1: atomicFormulaSkeleton : '(' predicate ( typedVariableList )? ')' -> ^( predicate ( typedVariableList )? ) ;
    public final PddlParser.atomicFormulaSkeleton_return atomicFormulaSkeleton() throws RecognitionException {
        PddlParser.atomicFormulaSkeleton_return retval = new PddlParser.atomicFormulaSkeleton_return();
        retval.start = input.LT(1);

        int atomicFormulaSkeleton_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal60=null;
        Token char_literal63=null;
        PddlParser.predicate_return predicate61 =null;

        PddlParser.typedVariableList_return typedVariableList62 =null;


        Object char_literal60_tree=null;
        Object char_literal63_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_typedVariableList=new RewriteRuleSubtreeStream(adaptor,"rule typedVariableList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:171:2: ( '(' predicate ( typedVariableList )? ')' -> ^( predicate ( typedVariableList )? ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:171:4: '(' predicate ( typedVariableList )? ')'
            {
            char_literal60=(Token)match(input,55,FOLLOW_55_in_atomicFormulaSkeleton814); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal60);


            pushFollow(FOLLOW_predicate_in_atomicFormulaSkeleton816);
            predicate61=predicate();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_predicate.add(predicate61.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:171:18: ( typedVariableList )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==VARIABLE) ) {
                alt20=1;
            }
            else if ( (LA20_0==57) ) {
                int LA20_2 = input.LA(2);

                if ( (synpred20_Pddl()) ) {
                    alt20=1;
                }
            }
            switch (alt20) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:171:18: typedVariableList
                    {
                    pushFollow(FOLLOW_typedVariableList_in_atomicFormulaSkeleton818);
                    typedVariableList62=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_typedVariableList.add(typedVariableList62.getTree());

                    }
                    break;

            }


            char_literal63=(Token)match(input,57,FOLLOW_57_in_atomicFormulaSkeleton821); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal63);


            // AST REWRITE
            // elements: typedVariableList, predicate
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 172:2: -> ^( predicate ( typedVariableList )? )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:172:5: ^( predicate ( typedVariableList )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_predicate.nextNode(), root_1);

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:172:17: ( typedVariableList )?
                if ( stream_typedVariableList.hasNext() ) {
                    adaptor.addChild(root_1, stream_typedVariableList.nextTree());

                }
                stream_typedVariableList.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 19, atomicFormulaSkeleton_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "atomicFormulaSkeleton"


    public static class predicate_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "predicate"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:175:1: predicate : NAME ;
    public final PddlParser.predicate_return predicate() throws RecognitionException {
        PddlParser.predicate_return retval = new PddlParser.predicate_return();
        retval.start = input.LT(1);

        int predicate_StartIndex = input.index();

        Object root_0 = null;

        Token NAME64=null;

        Object NAME64_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:175:11: ( NAME )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:175:13: NAME
            {
            root_0 = (Object)adaptor.nil();


            NAME64=(Token)match(input,NAME,FOLLOW_NAME_in_predicate840); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME64_tree = 
            (Object)adaptor.create(NAME64)
            ;
            adaptor.addChild(root_0, NAME64_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 20, predicate_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "predicate"


    public static class typedVariableList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typedVariableList"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:178:1: typedVariableList : ( ( VARIABLE )* | ( singleTypeVarList )+ ( VARIABLE )* ) ;
    public final PddlParser.typedVariableList_return typedVariableList() throws RecognitionException {
        PddlParser.typedVariableList_return retval = new PddlParser.typedVariableList_return();
        retval.start = input.LT(1);

        int typedVariableList_StartIndex = input.index();

        Object root_0 = null;

        Token VARIABLE65=null;
        Token VARIABLE67=null;
        PddlParser.singleTypeVarList_return singleTypeVarList66 =null;


        Object VARIABLE65_tree=null;
        Object VARIABLE67_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:5: ( ( ( VARIABLE )* | ( singleTypeVarList )+ ( VARIABLE )* ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:7: ( ( VARIABLE )* | ( singleTypeVarList )+ ( VARIABLE )* )
            {
            root_0 = (Object)adaptor.nil();


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:7: ( ( VARIABLE )* | ( singleTypeVarList )+ ( VARIABLE )* )
            int alt24=2;
            alt24 = dfa24.predict(input);
            switch (alt24) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:10: ( VARIABLE )*
                    {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:10: ( VARIABLE )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==VARIABLE) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:10: VARIABLE
                    	    {
                    	    VARIABLE65=(Token)match(input,VARIABLE,FOLLOW_VARIABLE_in_typedVariableList857); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    VARIABLE65_tree = 
                    	    (Object)adaptor.create(VARIABLE65)
                    	    ;
                    	    adaptor.addChild(root_0, VARIABLE65_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:22: ( singleTypeVarList )+ ( VARIABLE )*
                    {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:22: ( singleTypeVarList )+
                    int cnt22=0;
                    loop22:
                    do {
                        int alt22=2;
                        alt22 = dfa22.predict(input);
                        switch (alt22) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:22: singleTypeVarList
                    	    {
                    	    pushFollow(FOLLOW_singleTypeVarList_in_typedVariableList862);
                    	    singleTypeVarList66=singleTypeVarList();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, singleTypeVarList66.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt22 >= 1 ) break loop22;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(22, input);
                                throw eee;
                        }
                        cnt22++;
                    } while (true);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:41: ( VARIABLE )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==VARIABLE) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:179:41: VARIABLE
                    	    {
                    	    VARIABLE67=(Token)match(input,VARIABLE,FOLLOW_VARIABLE_in_typedVariableList865); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    VARIABLE67_tree = 
                    	    (Object)adaptor.create(VARIABLE67)
                    	    ;
                    	    adaptor.addChild(root_0, VARIABLE67_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 21, typedVariableList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typedVariableList"


    public static class singleTypeVarList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "singleTypeVarList"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:182:1: singleTypeVarList : ( ( VARIABLE )+ '-' t= type ) -> ( ^( VARIABLE $t) )+ ;
    public final PddlParser.singleTypeVarList_return singleTypeVarList() throws RecognitionException {
        PddlParser.singleTypeVarList_return retval = new PddlParser.singleTypeVarList_return();
        retval.start = input.LT(1);

        int singleTypeVarList_StartIndex = input.index();

        Object root_0 = null;

        Token VARIABLE68=null;
        Token char_literal69=null;
        PddlParser.type_return t =null;


        Object VARIABLE68_tree=null;
        Object char_literal69_tree=null;
        RewriteRuleTokenStream stream_VARIABLE=new RewriteRuleTokenStream(adaptor,"token VARIABLE");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:183:5: ( ( ( VARIABLE )+ '-' t= type ) -> ( ^( VARIABLE $t) )+ )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:183:7: ( ( VARIABLE )+ '-' t= type )
            {
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:183:7: ( ( VARIABLE )+ '-' t= type )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:183:8: ( VARIABLE )+ '-' t= type
            {
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:183:8: ( VARIABLE )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==VARIABLE) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:183:8: VARIABLE
            	    {
            	    VARIABLE68=(Token)match(input,VARIABLE,FOLLOW_VARIABLE_in_singleTypeVarList886); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_VARIABLE.add(VARIABLE68);


            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);


            char_literal69=(Token)match(input,60,FOLLOW_60_in_singleTypeVarList889); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_60.add(char_literal69);


            pushFollow(FOLLOW_type_in_singleTypeVarList893);
            t=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(t.getTree());

            }


            // AST REWRITE
            // elements: t, VARIABLE
            // token labels: 
            // rule labels: retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (Object)adaptor.nil();
            // 184:7: -> ( ^( VARIABLE $t) )+
            {
                if ( !(stream_t.hasNext()||stream_VARIABLE.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_t.hasNext()||stream_VARIABLE.hasNext() ) {
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:184:10: ^( VARIABLE $t)
                    {
                    Object root_1 = (Object)adaptor.nil();
                    root_1 = (Object)adaptor.becomeRoot(
                    stream_VARIABLE.nextNode()
                    , root_1);

                    adaptor.addChild(root_1, stream_t.nextTree());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_t.reset();
                stream_VARIABLE.reset();

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 22, singleTypeVarList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "singleTypeVarList"


    public static class constraints_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constraints"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:187:1: constraints : '(' ! ':constraints' ^ conGD ')' !;
    public final PddlParser.constraints_return constraints() throws RecognitionException {
        PddlParser.constraints_return retval = new PddlParser.constraints_return();
        retval.start = input.LT(1);

        int constraints_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal70=null;
        Token string_literal71=null;
        Token char_literal73=null;
        PddlParser.conGD_return conGD72 =null;


        Object char_literal70_tree=null;
        Object string_literal71_tree=null;
        Object char_literal73_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:188:2: ( '(' ! ':constraints' ^ conGD ')' !)
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:188:4: '(' ! ':constraints' ^ conGD ')' !
            {
            root_0 = (Object)adaptor.nil();


            char_literal70=(Token)match(input,55,FOLLOW_55_in_constraints924); if (state.failed) return retval;

            string_literal71=(Token)match(input,65,FOLLOW_65_in_constraints927); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal71_tree = 
            (Object)adaptor.create(string_literal71)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal71_tree, root_0);
            }

            pushFollow(FOLLOW_conGD_in_constraints930);
            conGD72=conGD();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conGD72.getTree());

            char_literal73=(Token)match(input,57,FOLLOW_57_in_constraints932); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 23, constraints_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "constraints"


    public static class structureDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "structureDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:191:1: structureDef : ( actionDef | durativeActionDef | derivedDef );
    public final PddlParser.structureDef_return structureDef() throws RecognitionException {
        PddlParser.structureDef_return retval = new PddlParser.structureDef_return();
        retval.start = input.LT(1);

        int structureDef_StartIndex = input.index();

        Object root_0 = null;

        PddlParser.actionDef_return actionDef74 =null;

        PddlParser.durativeActionDef_return durativeActionDef75 =null;

        PddlParser.derivedDef_return derivedDef76 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:192:2: ( actionDef | durativeActionDef | derivedDef )
            int alt26=3;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==55) ) {
                switch ( input.LA(2) ) {
                case 62:
                    {
                    alt26=1;
                    }
                    break;
                case 69:
                    {
                    alt26=2;
                    }
                    break;
                case 66:
                    {
                    alt26=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;

            }
            switch (alt26) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:192:4: actionDef
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_actionDef_in_structureDef944);
                    actionDef74=actionDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionDef74.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:193:4: durativeActionDef
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_durativeActionDef_in_structureDef949);
                    durativeActionDef75=durativeActionDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, durativeActionDef75.getTree());

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:194:4: derivedDef
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_derivedDef_in_structureDef954);
                    derivedDef76=derivedDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, derivedDef76.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 24, structureDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "structureDef"


    public static class actionDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "actionDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:200:1: actionDef : '(' ':action' actionSymbol params actionDefBody ')' -> ^( ACTION actionSymbol params actionDefBody ) ;
    public final PddlParser.actionDef_return actionDef() throws RecognitionException {
        PddlParser.actionDef_return retval = new PddlParser.actionDef_return();
        retval.start = input.LT(1);

        int actionDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal77=null;
        Token string_literal78=null;
        Token char_literal82=null;
        PddlParser.actionSymbol_return actionSymbol79 =null;

        PddlParser.params_return params80 =null;

        PddlParser.actionDefBody_return actionDefBody81 =null;


        Object char_literal77_tree=null;
        Object string_literal78_tree=null;
        Object char_literal82_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleSubtreeStream stream_actionSymbol=new RewriteRuleSubtreeStream(adaptor,"rule actionSymbol");
        RewriteRuleSubtreeStream stream_params=new RewriteRuleSubtreeStream(adaptor,"rule params");
        RewriteRuleSubtreeStream stream_actionDefBody=new RewriteRuleSubtreeStream(adaptor,"rule actionDefBody");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:201:2: ( '(' ':action' actionSymbol params actionDefBody ')' -> ^( ACTION actionSymbol params actionDefBody ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:201:4: '(' ':action' actionSymbol params actionDefBody ')'
            {
            char_literal77=(Token)match(input,55,FOLLOW_55_in_actionDef969); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal77);


            string_literal78=(Token)match(input,62,FOLLOW_62_in_actionDef971); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_62.add(string_literal78);


            pushFollow(FOLLOW_actionSymbol_in_actionDef973);
            actionSymbol79=actionSymbol();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_actionSymbol.add(actionSymbol79.getTree());

            pushFollow(FOLLOW_params_in_actionDef977);
            params80=params();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_params.add(params80.getTree());

            pushFollow(FOLLOW_actionDefBody_in_actionDef990);
            actionDefBody81=actionDefBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_actionDefBody.add(actionDefBody81.getTree());

            char_literal82=(Token)match(input,57,FOLLOW_57_in_actionDef992); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal82);


            // AST REWRITE
            // elements: actionSymbol, params, actionDefBody
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 204:8: -> ^( ACTION actionSymbol params actionDefBody )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:204:11: ^( ACTION actionSymbol params actionDefBody )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(ACTION, "ACTION")
                , root_1);

                adaptor.addChild(root_1, stream_actionSymbol.nextTree());

                adaptor.addChild(root_1, stream_params.nextTree());

                adaptor.addChild(root_1, stream_actionDefBody.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 25, actionDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "actionDef"


    public static class actionSymbol_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "actionSymbol"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:207:1: actionSymbol : NAME ;
    public final PddlParser.actionSymbol_return actionSymbol() throws RecognitionException {
        PddlParser.actionSymbol_return retval = new PddlParser.actionSymbol_return();
        retval.start = input.LT(1);

        int actionSymbol_StartIndex = input.index();

        Object root_0 = null;

        Token NAME83=null;

        Object NAME83_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:207:14: ( NAME )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:207:16: NAME
            {
            root_0 = (Object)adaptor.nil();


            NAME83=(Token)match(input,NAME,FOLLOW_NAME_in_actionSymbol1024); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME83_tree = 
            (Object)adaptor.create(NAME83)
            ;
            adaptor.addChild(root_0, NAME83_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 26, actionSymbol_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "actionSymbol"


    public static class params_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "params"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:209:1: params : paramsBody -> ^( OTHERBODY paramsBody ) ;
    public final PddlParser.params_return params() throws RecognitionException {
        PddlParser.params_return retval = new PddlParser.params_return();
        retval.start = input.LT(1);

        int params_StartIndex = input.index();

        Object root_0 = null;

        PddlParser.paramsBody_return paramsBody84 =null;


        RewriteRuleSubtreeStream stream_paramsBody=new RewriteRuleSubtreeStream(adaptor,"rule paramsBody");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:209:8: ( paramsBody -> ^( OTHERBODY paramsBody ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:209:10: paramsBody
            {
            pushFollow(FOLLOW_paramsBody_in_params1033);
            paramsBody84=paramsBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_paramsBody.add(paramsBody84.getTree());

            // AST REWRITE
            // elements: paramsBody
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 209:21: -> ^( OTHERBODY paramsBody )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:209:24: ^( OTHERBODY paramsBody )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OTHERBODY, "OTHERBODY")
                , root_1);

                adaptor.addChild(root_1, stream_paramsBody.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 27, params_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "params"


    public static class paramsBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "paramsBody"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:211:1: paramsBody : ':parameters' ! ( '(' ! ( typedVariableList )? ')' !| '()' ) ;
    public final PddlParser.paramsBody_return paramsBody() throws RecognitionException {
        PddlParser.paramsBody_return retval = new PddlParser.paramsBody_return();
        retval.start = input.LT(1);

        int paramsBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal85=null;
        Token char_literal86=null;
        Token char_literal88=null;
        Token string_literal89=null;
        PddlParser.typedVariableList_return typedVariableList87 =null;


        Object string_literal85_tree=null;
        Object char_literal86_tree=null;
        Object char_literal88_tree=null;
        Object string_literal89_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:212:2: ( ':parameters' ! ( '(' ! ( typedVariableList )? ')' !| '()' ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:3: ':parameters' ! ( '(' ! ( typedVariableList )? ')' !| '()' )
            {
            root_0 = (Object)adaptor.nil();


            string_literal85=(Token)match(input,76,FOLLOW_76_in_paramsBody1055); if (state.failed) return retval;

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:18: ( '(' ! ( typedVariableList )? ')' !| '()' )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==55) ) {
                alt28=1;
            }
            else if ( (LA28_0==56) ) {
                alt28=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;

            }
            switch (alt28) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:19: '(' ! ( typedVariableList )? ')' !
                    {
                    char_literal86=(Token)match(input,55,FOLLOW_55_in_paramsBody1059); if (state.failed) return retval;

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:24: ( typedVariableList )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==VARIABLE) ) {
                        alt27=1;
                    }
                    else if ( (LA27_0==57) ) {
                        int LA27_2 = input.LA(2);

                        if ( (synpred28_Pddl()) ) {
                            alt27=1;
                        }
                    }
                    switch (alt27) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:24: typedVariableList
                            {
                            pushFollow(FOLLOW_typedVariableList_in_paramsBody1062);
                            typedVariableList87=typedVariableList();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList87.getTree());

                            }
                            break;

                    }


                    char_literal88=(Token)match(input,57,FOLLOW_57_in_paramsBody1065); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:48: '()'
                    {
                    string_literal89=(Token)match(input,56,FOLLOW_56_in_paramsBody1068); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal89_tree = 
                    (Object)adaptor.create(string_literal89)
                    ;
                    adaptor.addChild(root_0, string_literal89_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 28, paramsBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "paramsBody"


    public static class actionDefBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "actionDefBody"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:219:1: actionDefBody : ( ':precondition' ( ( '(' ')' ) | goalDesc ) )? ( ':effect' ( ( '(' ')' ) | effect ) )? -> ^( PRECONDITION ( goalDesc )? ) ^( EFFECT ( effect )? ) ;
    public final PddlParser.actionDefBody_return actionDefBody() throws RecognitionException {
        PddlParser.actionDefBody_return retval = new PddlParser.actionDefBody_return();
        retval.start = input.LT(1);

        int actionDefBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal90=null;
        Token char_literal91=null;
        Token char_literal92=null;
        Token string_literal94=null;
        Token char_literal95=null;
        Token char_literal96=null;
        PddlParser.goalDesc_return goalDesc93 =null;

        PddlParser.effect_return effect97 =null;


        Object string_literal90_tree=null;
        Object char_literal91_tree=null;
        Object char_literal92_tree=null;
        Object string_literal94_tree=null;
        Object char_literal95_tree=null;
        Object char_literal96_tree=null;
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_effect=new RewriteRuleSubtreeStream(adaptor,"rule effect");
        RewriteRuleSubtreeStream stream_goalDesc=new RewriteRuleSubtreeStream(adaptor,"rule goalDesc");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:2: ( ( ':precondition' ( ( '(' ')' ) | goalDesc ) )? ( ':effect' ( ( '(' ')' ) | effect ) )? -> ^( PRECONDITION ( goalDesc )? ) ^( EFFECT ( effect )? ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:4: ( ':precondition' ( ( '(' ')' ) | goalDesc ) )? ( ':effect' ( ( '(' ')' ) | effect ) )?
            {
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:4: ( ':precondition' ( ( '(' ')' ) | goalDesc ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==77) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:6: ':precondition' ( ( '(' ')' ) | goalDesc )
                    {
                    string_literal90=(Token)match(input,77,FOLLOW_77_in_actionDefBody1085); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_77.add(string_literal90);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:22: ( ( '(' ')' ) | goalDesc )
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==55) ) {
                        int LA29_1 = input.LA(2);

                        if ( (LA29_1==57) ) {
                            alt29=1;
                        }
                        else if ( (LA29_1==NAME||(LA29_1 >= 81 && LA29_1 <= 82)||(LA29_1 >= 85 && LA29_1 <= 87)||LA29_1==92||(LA29_1 >= 99 && LA29_1 <= 100)||LA29_1==103||LA29_1==108||LA29_1==110) ) {
                            alt29=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 29, 1, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 0, input);

                        throw nvae;

                    }
                    switch (alt29) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:23: ( '(' ')' )
                            {
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:23: ( '(' ')' )
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:24: '(' ')'
                            {
                            char_literal91=(Token)match(input,55,FOLLOW_55_in_actionDefBody1089); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_55.add(char_literal91);


                            char_literal92=(Token)match(input,57,FOLLOW_57_in_actionDefBody1091); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_57.add(char_literal92);


                            }


                            }
                            break;
                        case 2 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:220:35: goalDesc
                            {
                            pushFollow(FOLLOW_goalDesc_in_actionDefBody1096);
                            goalDesc93=goalDesc();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc93.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:4: ( ':effect' ( ( '(' ')' ) | effect ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==70) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:6: ':effect' ( ( '(' ')' ) | effect )
                    {
                    string_literal94=(Token)match(input,70,FOLLOW_70_in_actionDefBody1106); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_70.add(string_literal94);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:16: ( ( '(' ')' ) | effect )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==55) ) {
                        int LA31_1 = input.LA(2);

                        if ( (LA31_1==57) ) {
                            alt31=1;
                        }
                        else if ( (LA31_1==NAME||(LA31_1 >= 92 && LA31_1 <= 94)||LA31_1==100||LA31_1==104||LA31_1==108||(LA31_1 >= 114 && LA31_1 <= 115)||LA31_1==121) ) {
                            alt31=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 31, 1, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 0, input);

                        throw nvae;

                    }
                    switch (alt31) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:17: ( '(' ')' )
                            {
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:17: ( '(' ')' )
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:18: '(' ')'
                            {
                            char_literal95=(Token)match(input,55,FOLLOW_55_in_actionDefBody1110); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_55.add(char_literal95);


                            char_literal96=(Token)match(input,57,FOLLOW_57_in_actionDefBody1112); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_57.add(char_literal96);


                            }


                            }
                            break;
                        case 2 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:221:29: effect
                            {
                            pushFollow(FOLLOW_effect_in_actionDefBody1117);
                            effect97=effect();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_effect.add(effect97.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }


            // AST REWRITE
            // elements: goalDesc, effect
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 222:4: -> ^( PRECONDITION ( goalDesc )? ) ^( EFFECT ( effect )? )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:222:7: ^( PRECONDITION ( goalDesc )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PRECONDITION, "PRECONDITION")
                , root_1);

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:222:22: ( goalDesc )?
                if ( stream_goalDesc.hasNext() ) {
                    adaptor.addChild(root_1, stream_goalDesc.nextTree());

                }
                stream_goalDesc.reset();

                adaptor.addChild(root_0, root_1);
                }

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:222:33: ^( EFFECT ( effect )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EFFECT, "EFFECT")
                , root_1);

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:222:42: ( effect )?
                if ( stream_effect.hasNext() ) {
                    adaptor.addChild(root_1, stream_effect.nextTree());

                }
                stream_effect.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 29, actionDefBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "actionDefBody"


    public static class goalDesc_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "goalDesc"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:226:1: goalDesc : ( atomicTermFormula | '(' 'and' ( goalDesc )* ')' -> ^( AND_GD ( goalDesc )* ) | '(' 'or' ( goalDesc )* ')' -> ^( OR_GD ( goalDesc )* ) | '(' 'not' goalDesc ')' -> ^( NOT_GD goalDesc ) | '(' 'imply' goalDesc goalDesc ')' -> ^( IMPLY_GD goalDesc goalDesc ) | '(' 'exists' '(' typedVariableList ')' goalDesc ')' -> ^( EXISTS_GD typedVariableList goalDesc ) | '(' 'forall' '(' typedVariableList ')' goalDesc ')' -> ^( FORALL_GD typedVariableList goalDesc ) | fComp -> ^( COMPARISON_GD fComp ) );
    public final PddlParser.goalDesc_return goalDesc() throws RecognitionException {
        PddlParser.goalDesc_return retval = new PddlParser.goalDesc_return();
        retval.start = input.LT(1);

        int goalDesc_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal99=null;
        Token string_literal100=null;
        Token char_literal102=null;
        Token char_literal103=null;
        Token string_literal104=null;
        Token char_literal106=null;
        Token char_literal107=null;
        Token string_literal108=null;
        Token char_literal110=null;
        Token char_literal111=null;
        Token string_literal112=null;
        Token char_literal115=null;
        Token char_literal116=null;
        Token string_literal117=null;
        Token char_literal118=null;
        Token char_literal120=null;
        Token char_literal122=null;
        Token char_literal123=null;
        Token string_literal124=null;
        Token char_literal125=null;
        Token char_literal127=null;
        Token char_literal129=null;
        PddlParser.atomicTermFormula_return atomicTermFormula98 =null;

        PddlParser.goalDesc_return goalDesc101 =null;

        PddlParser.goalDesc_return goalDesc105 =null;

        PddlParser.goalDesc_return goalDesc109 =null;

        PddlParser.goalDesc_return goalDesc113 =null;

        PddlParser.goalDesc_return goalDesc114 =null;

        PddlParser.typedVariableList_return typedVariableList119 =null;

        PddlParser.goalDesc_return goalDesc121 =null;

        PddlParser.typedVariableList_return typedVariableList126 =null;

        PddlParser.goalDesc_return goalDesc128 =null;

        PddlParser.fComp_return fComp130 =null;


        Object char_literal99_tree=null;
        Object string_literal100_tree=null;
        Object char_literal102_tree=null;
        Object char_literal103_tree=null;
        Object string_literal104_tree=null;
        Object char_literal106_tree=null;
        Object char_literal107_tree=null;
        Object string_literal108_tree=null;
        Object char_literal110_tree=null;
        Object char_literal111_tree=null;
        Object string_literal112_tree=null;
        Object char_literal115_tree=null;
        Object char_literal116_tree=null;
        Object string_literal117_tree=null;
        Object char_literal118_tree=null;
        Object char_literal120_tree=null;
        Object char_literal122_tree=null;
        Object char_literal123_tree=null;
        Object string_literal124_tree=null;
        Object char_literal125_tree=null;
        Object char_literal127_tree=null;
        Object char_literal129_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_fComp=new RewriteRuleSubtreeStream(adaptor,"rule fComp");
        RewriteRuleSubtreeStream stream_goalDesc=new RewriteRuleSubtreeStream(adaptor,"rule goalDesc");
        RewriteRuleSubtreeStream stream_typedVariableList=new RewriteRuleSubtreeStream(adaptor,"rule typedVariableList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:227:2: ( atomicTermFormula | '(' 'and' ( goalDesc )* ')' -> ^( AND_GD ( goalDesc )* ) | '(' 'or' ( goalDesc )* ')' -> ^( OR_GD ( goalDesc )* ) | '(' 'not' goalDesc ')' -> ^( NOT_GD goalDesc ) | '(' 'imply' goalDesc goalDesc ')' -> ^( IMPLY_GD goalDesc goalDesc ) | '(' 'exists' '(' typedVariableList ')' goalDesc ')' -> ^( EXISTS_GD typedVariableList goalDesc ) | '(' 'forall' '(' typedVariableList ')' goalDesc ')' -> ^( FORALL_GD typedVariableList goalDesc ) | fComp -> ^( COMPARISON_GD fComp ) )
            int alt35=8;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==55) ) {
                switch ( input.LA(2) ) {
                case 92:
                    {
                    alt35=2;
                    }
                    break;
                case 110:
                    {
                    alt35=3;
                    }
                    break;
                case 108:
                    {
                    alt35=4;
                    }
                    break;
                case 103:
                    {
                    alt35=5;
                    }
                    break;
                case 99:
                    {
                    alt35=6;
                    }
                    break;
                case 100:
                    {
                    alt35=7;
                    }
                    break;
                case NAME:
                    {
                    alt35=1;
                    }
                    break;
                case 81:
                case 82:
                case 85:
                case 86:
                case 87:
                    {
                    alt35=8;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;

            }
            switch (alt35) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:227:4: atomicTermFormula
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicTermFormula_in_goalDesc1152);
                    atomicTermFormula98=atomicTermFormula();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicTermFormula98.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:228:5: '(' 'and' ( goalDesc )* ')'
                    {
                    char_literal99=(Token)match(input,55,FOLLOW_55_in_goalDesc1158); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal99);


                    string_literal100=(Token)match(input,92,FOLLOW_92_in_goalDesc1160); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_92.add(string_literal100);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:228:15: ( goalDesc )*
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==55) ) {
                            alt33=1;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:228:15: goalDesc
                    	    {
                    	    pushFollow(FOLLOW_goalDesc_in_goalDesc1162);
                    	    goalDesc101=goalDesc();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc101.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop33;
                        }
                    } while (true);


                    char_literal102=(Token)match(input,57,FOLLOW_57_in_goalDesc1165); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal102);


                    // AST REWRITE
                    // elements: goalDesc
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 229:12: -> ^( AND_GD ( goalDesc )* )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:229:15: ^( AND_GD ( goalDesc )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(AND_GD, "AND_GD")
                        , root_1);

                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:229:24: ( goalDesc )*
                        while ( stream_goalDesc.hasNext() ) {
                            adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        }
                        stream_goalDesc.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:230:4: '(' 'or' ( goalDesc )* ')'
                    {
                    char_literal103=(Token)match(input,55,FOLLOW_55_in_goalDesc1190); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal103);


                    string_literal104=(Token)match(input,110,FOLLOW_110_in_goalDesc1192); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_110.add(string_literal104);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:230:13: ( goalDesc )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==55) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:230:13: goalDesc
                    	    {
                    	    pushFollow(FOLLOW_goalDesc_in_goalDesc1194);
                    	    goalDesc105=goalDesc();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc105.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


                    char_literal106=(Token)match(input,57,FOLLOW_57_in_goalDesc1197); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal106);


                    // AST REWRITE
                    // elements: goalDesc
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 231:12: -> ^( OR_GD ( goalDesc )* )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:231:15: ^( OR_GD ( goalDesc )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(OR_GD, "OR_GD")
                        , root_1);

                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:231:23: ( goalDesc )*
                        while ( stream_goalDesc.hasNext() ) {
                            adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        }
                        stream_goalDesc.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 4 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:232:4: '(' 'not' goalDesc ')'
                    {
                    char_literal107=(Token)match(input,55,FOLLOW_55_in_goalDesc1223); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal107);


                    string_literal108=(Token)match(input,108,FOLLOW_108_in_goalDesc1225); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_108.add(string_literal108);


                    pushFollow(FOLLOW_goalDesc_in_goalDesc1227);
                    goalDesc109=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc109.getTree());

                    char_literal110=(Token)match(input,57,FOLLOW_57_in_goalDesc1229); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal110);


                    // AST REWRITE
                    // elements: goalDesc
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 233:12: -> ^( NOT_GD goalDesc )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:233:15: ^( NOT_GD goalDesc )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NOT_GD, "NOT_GD")
                        , root_1);

                        adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 5 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:234:4: '(' 'imply' goalDesc goalDesc ')'
                    {
                    char_literal111=(Token)match(input,55,FOLLOW_55_in_goalDesc1253); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal111);


                    string_literal112=(Token)match(input,103,FOLLOW_103_in_goalDesc1255); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_103.add(string_literal112);


                    pushFollow(FOLLOW_goalDesc_in_goalDesc1257);
                    goalDesc113=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc113.getTree());

                    pushFollow(FOLLOW_goalDesc_in_goalDesc1259);
                    goalDesc114=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc114.getTree());

                    char_literal115=(Token)match(input,57,FOLLOW_57_in_goalDesc1261); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal115);


                    // AST REWRITE
                    // elements: goalDesc, goalDesc
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 235:12: -> ^( IMPLY_GD goalDesc goalDesc )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:235:15: ^( IMPLY_GD goalDesc goalDesc )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(IMPLY_GD, "IMPLY_GD")
                        , root_1);

                        adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 6 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:236:4: '(' 'exists' '(' typedVariableList ')' goalDesc ')'
                    {
                    char_literal116=(Token)match(input,55,FOLLOW_55_in_goalDesc1287); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal116);


                    string_literal117=(Token)match(input,99,FOLLOW_99_in_goalDesc1289); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_99.add(string_literal117);


                    char_literal118=(Token)match(input,55,FOLLOW_55_in_goalDesc1291); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal118);


                    pushFollow(FOLLOW_typedVariableList_in_goalDesc1293);
                    typedVariableList119=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_typedVariableList.add(typedVariableList119.getTree());

                    char_literal120=(Token)match(input,57,FOLLOW_57_in_goalDesc1295); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal120);


                    pushFollow(FOLLOW_goalDesc_in_goalDesc1297);
                    goalDesc121=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc121.getTree());

                    char_literal122=(Token)match(input,57,FOLLOW_57_in_goalDesc1299); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal122);


                    // AST REWRITE
                    // elements: typedVariableList, goalDesc
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 237:12: -> ^( EXISTS_GD typedVariableList goalDesc )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:237:15: ^( EXISTS_GD typedVariableList goalDesc )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(EXISTS_GD, "EXISTS_GD")
                        , root_1);

                        adaptor.addChild(root_1, stream_typedVariableList.nextTree());

                        adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 7 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:238:4: '(' 'forall' '(' typedVariableList ')' goalDesc ')'
                    {
                    char_literal123=(Token)match(input,55,FOLLOW_55_in_goalDesc1325); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal123);


                    string_literal124=(Token)match(input,100,FOLLOW_100_in_goalDesc1327); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_100.add(string_literal124);


                    char_literal125=(Token)match(input,55,FOLLOW_55_in_goalDesc1329); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal125);


                    pushFollow(FOLLOW_typedVariableList_in_goalDesc1331);
                    typedVariableList126=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_typedVariableList.add(typedVariableList126.getTree());

                    char_literal127=(Token)match(input,57,FOLLOW_57_in_goalDesc1333); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal127);


                    pushFollow(FOLLOW_goalDesc_in_goalDesc1335);
                    goalDesc128=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc128.getTree());

                    char_literal129=(Token)match(input,57,FOLLOW_57_in_goalDesc1337); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal129);


                    // AST REWRITE
                    // elements: typedVariableList, goalDesc
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 239:12: -> ^( FORALL_GD typedVariableList goalDesc )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:239:15: ^( FORALL_GD typedVariableList goalDesc )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(FORALL_GD, "FORALL_GD")
                        , root_1);

                        adaptor.addChild(root_1, stream_typedVariableList.nextTree());

                        adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 8 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:240:7: fComp
                    {
                    pushFollow(FOLLOW_fComp_in_goalDesc1366);
                    fComp130=fComp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fComp.add(fComp130.getTree());

                    // AST REWRITE
                    // elements: fComp
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 241:15: -> ^( COMPARISON_GD fComp )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:241:18: ^( COMPARISON_GD fComp )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(COMPARISON_GD, "COMPARISON_GD")
                        , root_1);

                        adaptor.addChild(root_1, stream_fComp.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 30, goalDesc_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "goalDesc"


    public static class fComp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fComp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:244:1: fComp : '(' ! binaryComp fExp fExp ')' !;
    public final PddlParser.fComp_return fComp() throws RecognitionException {
        PddlParser.fComp_return retval = new PddlParser.fComp_return();
        retval.start = input.LT(1);

        int fComp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal131=null;
        Token char_literal135=null;
        PddlParser.binaryComp_return binaryComp132 =null;

        PddlParser.fExp_return fExp133 =null;

        PddlParser.fExp_return fExp134 =null;


        Object char_literal131_tree=null;
        Object char_literal135_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:245:2: ( '(' ! binaryComp fExp fExp ')' !)
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:245:4: '(' ! binaryComp fExp fExp ')' !
            {
            root_0 = (Object)adaptor.nil();


            char_literal131=(Token)match(input,55,FOLLOW_55_in_fComp1402); if (state.failed) return retval;

            pushFollow(FOLLOW_binaryComp_in_fComp1405);
            binaryComp132=binaryComp();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, binaryComp132.getTree());

            pushFollow(FOLLOW_fExp_in_fComp1407);
            fExp133=fExp();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExp133.getTree());

            pushFollow(FOLLOW_fExp_in_fComp1409);
            fExp134=fExp();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExp134.getTree());

            char_literal135=(Token)match(input,57,FOLLOW_57_in_fComp1411); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 31, fComp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fComp"


    public static class atomicTermFormula_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "atomicTermFormula"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:248:1: atomicTermFormula : '(' predicate ( term )* ')' -> ^( PRED_HEAD predicate ( term )* ) ;
    public final PddlParser.atomicTermFormula_return atomicTermFormula() throws RecognitionException {
        PddlParser.atomicTermFormula_return retval = new PddlParser.atomicTermFormula_return();
        retval.start = input.LT(1);

        int atomicTermFormula_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal136=null;
        Token char_literal139=null;
        PddlParser.predicate_return predicate137 =null;

        PddlParser.term_return term138 =null;


        Object char_literal136_tree=null;
        Object char_literal139_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:249:2: ( '(' predicate ( term )* ')' -> ^( PRED_HEAD predicate ( term )* ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:249:4: '(' predicate ( term )* ')'
            {
            char_literal136=(Token)match(input,55,FOLLOW_55_in_atomicTermFormula1423); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal136);


            pushFollow(FOLLOW_predicate_in_atomicTermFormula1425);
            predicate137=predicate();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_predicate.add(predicate137.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:249:18: ( term )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==NAME||LA36_0==VARIABLE) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:249:18: term
            	    {
            	    pushFollow(FOLLOW_term_in_atomicTermFormula1427);
            	    term138=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_term.add(term138.getTree());

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);


            char_literal139=(Token)match(input,57,FOLLOW_57_in_atomicTermFormula1430); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal139);


            // AST REWRITE
            // elements: term, predicate
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 249:28: -> ^( PRED_HEAD predicate ( term )* )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:249:31: ^( PRED_HEAD predicate ( term )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PRED_HEAD, "PRED_HEAD")
                , root_1);

                adaptor.addChild(root_1, stream_predicate.nextTree());

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:249:53: ( term )*
                while ( stream_term.hasNext() ) {
                    adaptor.addChild(root_1, stream_term.nextTree());

                }
                stream_term.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 32, atomicTermFormula_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "atomicTermFormula"


    public static class term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:253:1: term : ( NAME | VARIABLE );
    public final PddlParser.term_return term() throws RecognitionException {
        PddlParser.term_return retval = new PddlParser.term_return();
        retval.start = input.LT(1);

        int term_StartIndex = input.index();

        Object root_0 = null;

        Token set140=null;

        Object set140_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:253:6: ( NAME | VARIABLE )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set140=(Token)input.LT(1);

            if ( input.LA(1)==NAME||input.LA(1)==VARIABLE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set140)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 33, term_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "term"


    public static class durativeActionDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "durativeActionDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:257:1: durativeActionDef : '(' ':durative-action' actionSymbol ':parameters' '(' typedVariableList ')' daDefBody ')' -> ^( DURATIVE_ACTION actionSymbol typedVariableList daDefBody ) ;
    public final PddlParser.durativeActionDef_return durativeActionDef() throws RecognitionException {
        PddlParser.durativeActionDef_return retval = new PddlParser.durativeActionDef_return();
        retval.start = input.LT(1);

        int durativeActionDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal141=null;
        Token string_literal142=null;
        Token string_literal144=null;
        Token char_literal145=null;
        Token char_literal147=null;
        Token char_literal149=null;
        PddlParser.actionSymbol_return actionSymbol143 =null;

        PddlParser.typedVariableList_return typedVariableList146 =null;

        PddlParser.daDefBody_return daDefBody148 =null;


        Object char_literal141_tree=null;
        Object string_literal142_tree=null;
        Object string_literal144_tree=null;
        Object char_literal145_tree=null;
        Object char_literal147_tree=null;
        Object char_literal149_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_actionSymbol=new RewriteRuleSubtreeStream(adaptor,"rule actionSymbol");
        RewriteRuleSubtreeStream stream_daDefBody=new RewriteRuleSubtreeStream(adaptor,"rule daDefBody");
        RewriteRuleSubtreeStream stream_typedVariableList=new RewriteRuleSubtreeStream(adaptor,"rule typedVariableList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:258:2: ( '(' ':durative-action' actionSymbol ':parameters' '(' typedVariableList ')' daDefBody ')' -> ^( DURATIVE_ACTION actionSymbol typedVariableList daDefBody ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:258:4: '(' ':durative-action' actionSymbol ':parameters' '(' typedVariableList ')' daDefBody ')'
            {
            char_literal141=(Token)match(input,55,FOLLOW_55_in_durativeActionDef1471); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal141);


            string_literal142=(Token)match(input,69,FOLLOW_69_in_durativeActionDef1473); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_69.add(string_literal142);


            pushFollow(FOLLOW_actionSymbol_in_durativeActionDef1475);
            actionSymbol143=actionSymbol();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_actionSymbol.add(actionSymbol143.getTree());

            string_literal144=(Token)match(input,76,FOLLOW_76_in_durativeActionDef1484); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_76.add(string_literal144);


            char_literal145=(Token)match(input,55,FOLLOW_55_in_durativeActionDef1486); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal145);


            pushFollow(FOLLOW_typedVariableList_in_durativeActionDef1488);
            typedVariableList146=typedVariableList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_typedVariableList.add(typedVariableList146.getTree());

            char_literal147=(Token)match(input,57,FOLLOW_57_in_durativeActionDef1490); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal147);


            pushFollow(FOLLOW_daDefBody_in_durativeActionDef1503);
            daDefBody148=daDefBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_daDefBody.add(daDefBody148.getTree());

            char_literal149=(Token)match(input,57,FOLLOW_57_in_durativeActionDef1505); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal149);


            // AST REWRITE
            // elements: typedVariableList, actionSymbol, daDefBody
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 261:8: -> ^( DURATIVE_ACTION actionSymbol typedVariableList daDefBody )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:261:11: ^( DURATIVE_ACTION actionSymbol typedVariableList daDefBody )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(DURATIVE_ACTION, "DURATIVE_ACTION")
                , root_1);

                adaptor.addChild(root_1, stream_actionSymbol.nextTree());

                adaptor.addChild(root_1, stream_typedVariableList.nextTree());

                adaptor.addChild(root_1, stream_daDefBody.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 34, durativeActionDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "durativeActionDef"


    public static class daDefBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "daDefBody"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:264:1: daDefBody : ( ':duration' durationConstraint | ':condition' ( ( '(' ')' ) | daGD ) | ':effect' ( ( '(' ')' ) | daEffect ) );
    public final PddlParser.daDefBody_return daDefBody() throws RecognitionException {
        PddlParser.daDefBody_return retval = new PddlParser.daDefBody_return();
        retval.start = input.LT(1);

        int daDefBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal150=null;
        Token string_literal152=null;
        Token char_literal153=null;
        Token char_literal154=null;
        Token string_literal156=null;
        Token char_literal157=null;
        Token char_literal158=null;
        PddlParser.durationConstraint_return durationConstraint151 =null;

        PddlParser.daGD_return daGD155 =null;

        PddlParser.daEffect_return daEffect159 =null;


        Object string_literal150_tree=null;
        Object string_literal152_tree=null;
        Object char_literal153_tree=null;
        Object char_literal154_tree=null;
        Object string_literal156_tree=null;
        Object char_literal157_tree=null;
        Object char_literal158_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:265:2: ( ':duration' durationConstraint | ':condition' ( ( '(' ')' ) | daGD ) | ':effect' ( ( '(' ')' ) | daEffect ) )
            int alt39=3;
            switch ( input.LA(1) ) {
            case 68:
                {
                alt39=1;
                }
                break;
            case 63:
                {
                alt39=2;
                }
                break;
            case 70:
                {
                alt39=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;

            }

            switch (alt39) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:265:4: ':duration' durationConstraint
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal150=(Token)match(input,68,FOLLOW_68_in_daDefBody1538); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal150_tree = 
                    (Object)adaptor.create(string_literal150)
                    ;
                    adaptor.addChild(root_0, string_literal150_tree);
                    }

                    pushFollow(FOLLOW_durationConstraint_in_daDefBody1540);
                    durationConstraint151=durationConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, durationConstraint151.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:266:4: ':condition' ( ( '(' ')' ) | daGD )
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal152=(Token)match(input,63,FOLLOW_63_in_daDefBody1546); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal152_tree = 
                    (Object)adaptor.create(string_literal152)
                    ;
                    adaptor.addChild(root_0, string_literal152_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:266:17: ( ( '(' ')' ) | daGD )
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==55) ) {
                        int LA37_1 = input.LA(2);

                        if ( (LA37_1==57) ) {
                            alt37=1;
                        }
                        else if ( (LA37_1==83||LA37_1==92||LA37_1==100||(LA37_1 >= 111 && LA37_1 <= 112)) ) {
                            alt37=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 37, 1, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 0, input);

                        throw nvae;

                    }
                    switch (alt37) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:266:18: ( '(' ')' )
                            {
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:266:18: ( '(' ')' )
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:266:19: '(' ')'
                            {
                            char_literal153=(Token)match(input,55,FOLLOW_55_in_daDefBody1550); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal153_tree = 
                            (Object)adaptor.create(char_literal153)
                            ;
                            adaptor.addChild(root_0, char_literal153_tree);
                            }

                            char_literal154=(Token)match(input,57,FOLLOW_57_in_daDefBody1552); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal154_tree = 
                            (Object)adaptor.create(char_literal154)
                            ;
                            adaptor.addChild(root_0, char_literal154_tree);
                            }

                            }


                            }
                            break;
                        case 2 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:266:30: daGD
                            {
                            pushFollow(FOLLOW_daGD_in_daDefBody1557);
                            daGD155=daGD();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, daGD155.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:267:7: ':effect' ( ( '(' ')' ) | daEffect )
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal156=(Token)match(input,70,FOLLOW_70_in_daDefBody1566); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal156_tree = 
                    (Object)adaptor.create(string_literal156)
                    ;
                    adaptor.addChild(root_0, string_literal156_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:267:17: ( ( '(' ')' ) | daEffect )
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==55) ) {
                        int LA38_1 = input.LA(2);

                        if ( (LA38_1==57) ) {
                            alt38=1;
                        }
                        else if ( (LA38_1==83||(LA38_1 >= 92 && LA38_1 <= 94)||LA38_1==100||LA38_1==104||(LA38_1 >= 114 && LA38_1 <= 115)||LA38_1==121) ) {
                            alt38=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 38, 1, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 38, 0, input);

                        throw nvae;

                    }
                    switch (alt38) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:267:18: ( '(' ')' )
                            {
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:267:18: ( '(' ')' )
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:267:19: '(' ')'
                            {
                            char_literal157=(Token)match(input,55,FOLLOW_55_in_daDefBody1570); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal157_tree = 
                            (Object)adaptor.create(char_literal157)
                            ;
                            adaptor.addChild(root_0, char_literal157_tree);
                            }

                            char_literal158=(Token)match(input,57,FOLLOW_57_in_daDefBody1572); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal158_tree = 
                            (Object)adaptor.create(char_literal158)
                            ;
                            adaptor.addChild(root_0, char_literal158_tree);
                            }

                            }


                            }
                            break;
                        case 2 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:267:30: daEffect
                            {
                            pushFollow(FOLLOW_daEffect_in_daDefBody1577);
                            daEffect159=daEffect();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, daEffect159.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 35, daDefBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "daDefBody"


    public static class daGD_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "daGD"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:270:1: daGD : ( prefTimedGD | '(' 'and' ( daGD )* ')' | '(' 'forall' '(' typedVariableList ')' daGD ')' );
    public final PddlParser.daGD_return daGD() throws RecognitionException {
        PddlParser.daGD_return retval = new PddlParser.daGD_return();
        retval.start = input.LT(1);

        int daGD_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal161=null;
        Token string_literal162=null;
        Token char_literal164=null;
        Token char_literal165=null;
        Token string_literal166=null;
        Token char_literal167=null;
        Token char_literal169=null;
        Token char_literal171=null;
        PddlParser.prefTimedGD_return prefTimedGD160 =null;

        PddlParser.daGD_return daGD163 =null;

        PddlParser.typedVariableList_return typedVariableList168 =null;

        PddlParser.daGD_return daGD170 =null;


        Object char_literal161_tree=null;
        Object string_literal162_tree=null;
        Object char_literal164_tree=null;
        Object char_literal165_tree=null;
        Object string_literal166_tree=null;
        Object char_literal167_tree=null;
        Object char_literal169_tree=null;
        Object char_literal171_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:271:2: ( prefTimedGD | '(' 'and' ( daGD )* ')' | '(' 'forall' '(' typedVariableList ')' daGD ')' )
            int alt41=3;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==55) ) {
                switch ( input.LA(2) ) {
                case 83:
                case 111:
                case 112:
                    {
                    alt41=1;
                    }
                    break;
                case 92:
                    {
                    alt41=2;
                    }
                    break;
                case 100:
                    {
                    alt41=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 41, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;

            }
            switch (alt41) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:271:4: prefTimedGD
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_prefTimedGD_in_daGD1592);
                    prefTimedGD160=prefTimedGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefTimedGD160.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:272:4: '(' 'and' ( daGD )* ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal161=(Token)match(input,55,FOLLOW_55_in_daGD1598); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal161_tree = 
                    (Object)adaptor.create(char_literal161)
                    ;
                    adaptor.addChild(root_0, char_literal161_tree);
                    }

                    string_literal162=(Token)match(input,92,FOLLOW_92_in_daGD1600); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal162_tree = 
                    (Object)adaptor.create(string_literal162)
                    ;
                    adaptor.addChild(root_0, string_literal162_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:272:14: ( daGD )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==55) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:272:14: daGD
                    	    {
                    	    pushFollow(FOLLOW_daGD_in_daGD1602);
                    	    daGD163=daGD();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, daGD163.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);


                    char_literal164=(Token)match(input,57,FOLLOW_57_in_daGD1605); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal164_tree = 
                    (Object)adaptor.create(char_literal164)
                    ;
                    adaptor.addChild(root_0, char_literal164_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:273:4: '(' 'forall' '(' typedVariableList ')' daGD ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal165=(Token)match(input,55,FOLLOW_55_in_daGD1610); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal165_tree = 
                    (Object)adaptor.create(char_literal165)
                    ;
                    adaptor.addChild(root_0, char_literal165_tree);
                    }

                    string_literal166=(Token)match(input,100,FOLLOW_100_in_daGD1612); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal166_tree = 
                    (Object)adaptor.create(string_literal166)
                    ;
                    adaptor.addChild(root_0, string_literal166_tree);
                    }

                    char_literal167=(Token)match(input,55,FOLLOW_55_in_daGD1614); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal167_tree = 
                    (Object)adaptor.create(char_literal167)
                    ;
                    adaptor.addChild(root_0, char_literal167_tree);
                    }

                    pushFollow(FOLLOW_typedVariableList_in_daGD1616);
                    typedVariableList168=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList168.getTree());

                    char_literal169=(Token)match(input,57,FOLLOW_57_in_daGD1618); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal169_tree = 
                    (Object)adaptor.create(char_literal169)
                    ;
                    adaptor.addChild(root_0, char_literal169_tree);
                    }

                    pushFollow(FOLLOW_daGD_in_daGD1620);
                    daGD170=daGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, daGD170.getTree());

                    char_literal171=(Token)match(input,57,FOLLOW_57_in_daGD1622); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal171_tree = 
                    (Object)adaptor.create(char_literal171)
                    ;
                    adaptor.addChild(root_0, char_literal171_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 36, daGD_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "daGD"


    public static class prefTimedGD_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prefTimedGD"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:276:1: prefTimedGD : ( timedGD | '(' 'preference' ( NAME )? timedGD ')' );
    public final PddlParser.prefTimedGD_return prefTimedGD() throws RecognitionException {
        PddlParser.prefTimedGD_return retval = new PddlParser.prefTimedGD_return();
        retval.start = input.LT(1);

        int prefTimedGD_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal173=null;
        Token string_literal174=null;
        Token NAME175=null;
        Token char_literal177=null;
        PddlParser.timedGD_return timedGD172 =null;

        PddlParser.timedGD_return timedGD176 =null;


        Object char_literal173_tree=null;
        Object string_literal174_tree=null;
        Object NAME175_tree=null;
        Object char_literal177_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:277:2: ( timedGD | '(' 'preference' ( NAME )? timedGD ')' )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==55) ) {
                int LA43_1 = input.LA(2);

                if ( (LA43_1==83||LA43_1==111) ) {
                    alt43=1;
                }
                else if ( (LA43_1==112) ) {
                    alt43=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;

            }
            switch (alt43) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:277:4: timedGD
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timedGD_in_prefTimedGD1633);
                    timedGD172=timedGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timedGD172.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:278:4: '(' 'preference' ( NAME )? timedGD ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal173=(Token)match(input,55,FOLLOW_55_in_prefTimedGD1638); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal173_tree = 
                    (Object)adaptor.create(char_literal173)
                    ;
                    adaptor.addChild(root_0, char_literal173_tree);
                    }

                    string_literal174=(Token)match(input,112,FOLLOW_112_in_prefTimedGD1640); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal174_tree = 
                    (Object)adaptor.create(string_literal174)
                    ;
                    adaptor.addChild(root_0, string_literal174_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:278:21: ( NAME )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==NAME) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:278:21: NAME
                            {
                            NAME175=(Token)match(input,NAME,FOLLOW_NAME_in_prefTimedGD1642); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NAME175_tree = 
                            (Object)adaptor.create(NAME175)
                            ;
                            adaptor.addChild(root_0, NAME175_tree);
                            }

                            }
                            break;

                    }


                    pushFollow(FOLLOW_timedGD_in_prefTimedGD1645);
                    timedGD176=timedGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timedGD176.getTree());

                    char_literal177=(Token)match(input,57,FOLLOW_57_in_prefTimedGD1647); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal177_tree = 
                    (Object)adaptor.create(char_literal177)
                    ;
                    adaptor.addChild(root_0, char_literal177_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 37, prefTimedGD_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "prefTimedGD"


    public static class timedGD_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timedGD"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:281:1: timedGD : ( '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier goalDesc ')' | '(' 'over' interval goalDesc ')' );
    public final PddlParser.timedGD_return timedGD() throws RecognitionException {
        PddlParser.timedGD_return retval = new PddlParser.timedGD_return();
        retval.start = input.LT(1);

        int timedGD_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal178=null;
        Token string_literal179=null;
        Token char_literal182=null;
        Token char_literal183=null;
        Token string_literal184=null;
        Token char_literal187=null;
        PddlParser.timeSpecifier_return timeSpecifier180 =null;

        PddlParser.goalDesc_return goalDesc181 =null;

        PddlParser.interval_return interval185 =null;

        PddlParser.goalDesc_return goalDesc186 =null;


        Object char_literal178_tree=null;
        Object string_literal179_tree=null;
        Object char_literal182_tree=null;
        Object char_literal183_tree=null;
        Object string_literal184_tree=null;
        Object char_literal187_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:282:2: ( '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier goalDesc ')' | '(' 'over' interval goalDesc ')' )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==55) ) {
                int LA44_1 = input.LA(2);

                if ( (LA44_1==83) ) {
                    alt44=1;
                }
                else if ( (LA44_1==111) ) {
                    alt44=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 44, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;

            }
            switch (alt44) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:282:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal178=(Token)match(input,55,FOLLOW_55_in_timedGD1658); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal178_tree = 
                    (Object)adaptor.create(char_literal178)
                    ;
                    adaptor.addChild(root_0, char_literal178_tree);
                    }

                    string_literal179=(Token)match(input,83,FOLLOW_83_in_timedGD1660); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal179_tree = 
                    (Object)adaptor.create(string_literal179)
                    ;
                    adaptor.addChild(root_0, string_literal179_tree);
                    }

                    pushFollow(FOLLOW_timeSpecifier_in_timedGD1662);
                    timeSpecifier180=timeSpecifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeSpecifier180.getTree());

                    pushFollow(FOLLOW_goalDesc_in_timedGD1664);
                    goalDesc181=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc181.getTree());

                    char_literal182=(Token)match(input,57,FOLLOW_57_in_timedGD1666); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal182_tree = 
                    (Object)adaptor.create(char_literal182)
                    ;
                    adaptor.addChild(root_0, char_literal182_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:283:4: '(' 'over' interval goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal183=(Token)match(input,55,FOLLOW_55_in_timedGD1671); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal183_tree = 
                    (Object)adaptor.create(char_literal183)
                    ;
                    adaptor.addChild(root_0, char_literal183_tree);
                    }

                    string_literal184=(Token)match(input,111,FOLLOW_111_in_timedGD1673); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal184_tree = 
                    (Object)adaptor.create(string_literal184)
                    ;
                    adaptor.addChild(root_0, string_literal184_tree);
                    }

                    pushFollow(FOLLOW_interval_in_timedGD1675);
                    interval185=interval();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, interval185.getTree());

                    pushFollow(FOLLOW_goalDesc_in_timedGD1677);
                    goalDesc186=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc186.getTree());

                    char_literal187=(Token)match(input,57,FOLLOW_57_in_timedGD1679); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal187_tree = 
                    (Object)adaptor.create(char_literal187)
                    ;
                    adaptor.addChild(root_0, char_literal187_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 38, timedGD_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timedGD"


    public static class timeSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeSpecifier"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:286:1: timeSpecifier : ( 'start' | 'end' );
    public final PddlParser.timeSpecifier_return timeSpecifier() throws RecognitionException {
        PddlParser.timeSpecifier_return retval = new PddlParser.timeSpecifier_return();
        retval.start = input.LT(1);

        int timeSpecifier_StartIndex = input.index();

        Object root_0 = null;

        Token set188=null;

        Object set188_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:286:15: ( 'start' | 'end' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set188=(Token)input.LT(1);

            if ( input.LA(1)==98||input.LA(1)==119 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set188)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 39, timeSpecifier_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeSpecifier"


    public static class interval_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "interval"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:287:1: interval : 'all' ;
    public final PddlParser.interval_return interval() throws RecognitionException {
        PddlParser.interval_return retval = new PddlParser.interval_return();
        retval.start = input.LT(1);

        int interval_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal189=null;

        Object string_literal189_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:287:10: ( 'all' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:287:12: 'all'
            {
            root_0 = (Object)adaptor.nil();


            string_literal189=(Token)match(input,89,FOLLOW_89_in_interval1701); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal189_tree = 
            (Object)adaptor.create(string_literal189)
            ;
            adaptor.addChild(root_0, string_literal189_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 40, interval_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "interval"


    public static class derivedDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "derivedDef"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:291:1: derivedDef : '(' ! ':derived' ^ typedVariableList goalDesc ')' !;
    public final PddlParser.derivedDef_return derivedDef() throws RecognitionException {
        PddlParser.derivedDef_return retval = new PddlParser.derivedDef_return();
        retval.start = input.LT(1);

        int derivedDef_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal190=null;
        Token string_literal191=null;
        Token char_literal194=null;
        PddlParser.typedVariableList_return typedVariableList192 =null;

        PddlParser.goalDesc_return goalDesc193 =null;


        Object char_literal190_tree=null;
        Object string_literal191_tree=null;
        Object char_literal194_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:292:2: ( '(' ! ':derived' ^ typedVariableList goalDesc ')' !)
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:292:4: '(' ! ':derived' ^ typedVariableList goalDesc ')' !
            {
            root_0 = (Object)adaptor.nil();


            char_literal190=(Token)match(input,55,FOLLOW_55_in_derivedDef1714); if (state.failed) return retval;

            string_literal191=(Token)match(input,66,FOLLOW_66_in_derivedDef1717); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal191_tree = 
            (Object)adaptor.create(string_literal191)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal191_tree, root_0);
            }

            pushFollow(FOLLOW_typedVariableList_in_derivedDef1720);
            typedVariableList192=typedVariableList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList192.getTree());

            pushFollow(FOLLOW_goalDesc_in_derivedDef1722);
            goalDesc193=goalDesc();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc193.getTree());

            char_literal194=(Token)match(input,57,FOLLOW_57_in_derivedDef1724); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 41, derivedDef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "derivedDef"


    public static class fExp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fExp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:297:1: fExp : ( NUMBER | '(' binaryOp fExp fExp2 ')' -> ^( BINARY_OP binaryOp fExp fExp2 ) | '(' '-' fExp ')' -> ^( UNARY_MINUS fExp ) | fHead );
    public final PddlParser.fExp_return fExp() throws RecognitionException {
        PddlParser.fExp_return retval = new PddlParser.fExp_return();
        retval.start = input.LT(1);

        int fExp_StartIndex = input.index();

        Object root_0 = null;

        Token NUMBER195=null;
        Token char_literal196=null;
        Token char_literal200=null;
        Token char_literal201=null;
        Token char_literal202=null;
        Token char_literal204=null;
        PddlParser.binaryOp_return binaryOp197 =null;

        PddlParser.fExp_return fExp198 =null;

        PddlParser.fExp2_return fExp2199 =null;

        PddlParser.fExp_return fExp203 =null;

        PddlParser.fHead_return fHead205 =null;


        Object NUMBER195_tree=null;
        Object char_literal196_tree=null;
        Object char_literal200_tree=null;
        Object char_literal201_tree=null;
        Object char_literal202_tree=null;
        Object char_literal204_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_fExp2=new RewriteRuleSubtreeStream(adaptor,"rule fExp2");
        RewriteRuleSubtreeStream stream_fExp=new RewriteRuleSubtreeStream(adaptor,"rule fExp");
        RewriteRuleSubtreeStream stream_binaryOp=new RewriteRuleSubtreeStream(adaptor,"rule binaryOp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:298:2: ( NUMBER | '(' binaryOp fExp fExp2 ')' -> ^( BINARY_OP binaryOp fExp fExp2 ) | '(' '-' fExp ')' -> ^( UNARY_MINUS fExp ) | fHead )
            int alt45=4;
            switch ( input.LA(1) ) {
            case NUMBER:
                {
                alt45=1;
                }
                break;
            case 55:
                {
                int LA45_2 = input.LA(2);

                if ( (synpred57_Pddl()) ) {
                    alt45=2;
                }
                else if ( (synpred58_Pddl()) ) {
                    alt45=3;
                }
                else if ( (true) ) {
                    alt45=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 45, 2, input);

                    throw nvae;

                }
                }
                break;
            case NAME:
                {
                alt45=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;

            }

            switch (alt45) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:298:4: NUMBER
                    {
                    root_0 = (Object)adaptor.nil();


                    NUMBER195=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_fExp1739); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER195_tree = 
                    (Object)adaptor.create(NUMBER195)
                    ;
                    adaptor.addChild(root_0, NUMBER195_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:299:4: '(' binaryOp fExp fExp2 ')'
                    {
                    char_literal196=(Token)match(input,55,FOLLOW_55_in_fExp1744); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal196);


                    pushFollow(FOLLOW_binaryOp_in_fExp1746);
                    binaryOp197=binaryOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_binaryOp.add(binaryOp197.getTree());

                    pushFollow(FOLLOW_fExp_in_fExp1748);
                    fExp198=fExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fExp.add(fExp198.getTree());

                    pushFollow(FOLLOW_fExp2_in_fExp1750);
                    fExp2199=fExp2();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fExp2.add(fExp2199.getTree());

                    char_literal200=(Token)match(input,57,FOLLOW_57_in_fExp1752); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal200);


                    // AST REWRITE
                    // elements: fExp, fExp2, binaryOp
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 299:32: -> ^( BINARY_OP binaryOp fExp fExp2 )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:299:35: ^( BINARY_OP binaryOp fExp fExp2 )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(BINARY_OP, "BINARY_OP")
                        , root_1);

                        adaptor.addChild(root_1, stream_binaryOp.nextTree());

                        adaptor.addChild(root_1, stream_fExp.nextTree());

                        adaptor.addChild(root_1, stream_fExp2.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:300:4: '(' '-' fExp ')'
                    {
                    char_literal201=(Token)match(input,55,FOLLOW_55_in_fExp1769); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal201);


                    char_literal202=(Token)match(input,60,FOLLOW_60_in_fExp1771); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_60.add(char_literal202);


                    pushFollow(FOLLOW_fExp_in_fExp1773);
                    fExp203=fExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fExp.add(fExp203.getTree());

                    char_literal204=(Token)match(input,57,FOLLOW_57_in_fExp1775); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal204);


                    // AST REWRITE
                    // elements: fExp
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 300:21: -> ^( UNARY_MINUS fExp )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:300:24: ^( UNARY_MINUS fExp )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(UNARY_MINUS, "UNARY_MINUS")
                        , root_1);

                        adaptor.addChild(root_1, stream_fExp.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 4 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:301:4: fHead
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_fHead_in_fExp1788);
                    fHead205=fHead();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fHead205.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 42, fExp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fExp"


    public static class fExp2_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fExp2"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:306:1: fExp2 : fExp ;
    public final PddlParser.fExp2_return fExp2() throws RecognitionException {
        PddlParser.fExp2_return retval = new PddlParser.fExp2_return();
        retval.start = input.LT(1);

        int fExp2_StartIndex = input.index();

        Object root_0 = null;

        PddlParser.fExp_return fExp206 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:306:7: ( fExp )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:306:9: fExp
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_fExp_in_fExp21800);
            fExp206=fExp();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExp206.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 43, fExp2_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fExp2"


    public static class fHead_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fHead"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:308:1: fHead : ( '(' functionSymbol ( term )* ')' -> ^( FUNC_HEAD functionSymbol ( term )* ) | functionSymbol -> ^( FUNC_HEAD functionSymbol ) );
    public final PddlParser.fHead_return fHead() throws RecognitionException {
        PddlParser.fHead_return retval = new PddlParser.fHead_return();
        retval.start = input.LT(1);

        int fHead_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal207=null;
        Token char_literal210=null;
        PddlParser.functionSymbol_return functionSymbol208 =null;

        PddlParser.term_return term209 =null;

        PddlParser.functionSymbol_return functionSymbol211 =null;


        Object char_literal207_tree=null;
        Object char_literal210_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_functionSymbol=new RewriteRuleSubtreeStream(adaptor,"rule functionSymbol");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:309:2: ( '(' functionSymbol ( term )* ')' -> ^( FUNC_HEAD functionSymbol ( term )* ) | functionSymbol -> ^( FUNC_HEAD functionSymbol ) )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==55) ) {
                alt47=1;
            }
            else if ( (LA47_0==NAME) ) {
                alt47=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;

            }
            switch (alt47) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:309:4: '(' functionSymbol ( term )* ')'
                    {
                    char_literal207=(Token)match(input,55,FOLLOW_55_in_fHead1810); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal207);


                    pushFollow(FOLLOW_functionSymbol_in_fHead1812);
                    functionSymbol208=functionSymbol();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_functionSymbol.add(functionSymbol208.getTree());

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:309:23: ( term )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==NAME||LA46_0==VARIABLE) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:309:23: term
                    	    {
                    	    pushFollow(FOLLOW_term_in_fHead1814);
                    	    term209=term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_term.add(term209.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    char_literal210=(Token)match(input,57,FOLLOW_57_in_fHead1817); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal210);


                    // AST REWRITE
                    // elements: term, functionSymbol
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 309:33: -> ^( FUNC_HEAD functionSymbol ( term )* )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:309:36: ^( FUNC_HEAD functionSymbol ( term )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(FUNC_HEAD, "FUNC_HEAD")
                        , root_1);

                        adaptor.addChild(root_1, stream_functionSymbol.nextTree());

                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:309:63: ( term )*
                        while ( stream_term.hasNext() ) {
                            adaptor.addChild(root_1, stream_term.nextTree());

                        }
                        stream_term.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:310:4: functionSymbol
                    {
                    pushFollow(FOLLOW_functionSymbol_in_fHead1833);
                    functionSymbol211=functionSymbol();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_functionSymbol.add(functionSymbol211.getTree());

                    // AST REWRITE
                    // elements: functionSymbol
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 310:19: -> ^( FUNC_HEAD functionSymbol )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:310:22: ^( FUNC_HEAD functionSymbol )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(FUNC_HEAD, "FUNC_HEAD")
                        , root_1);

                        adaptor.addChild(root_1, stream_functionSymbol.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 44, fHead_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fHead"


    public static class effect_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "effect"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:313:1: effect : ( '(' 'and' ( cEffect )* ')' -> ^( AND_EFFECT ( cEffect )* ) | cEffect );
    public final PddlParser.effect_return effect() throws RecognitionException {
        PddlParser.effect_return retval = new PddlParser.effect_return();
        retval.start = input.LT(1);

        int effect_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal212=null;
        Token string_literal213=null;
        Token char_literal215=null;
        PddlParser.cEffect_return cEffect214 =null;

        PddlParser.cEffect_return cEffect216 =null;


        Object char_literal212_tree=null;
        Object string_literal213_tree=null;
        Object char_literal215_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_cEffect=new RewriteRuleSubtreeStream(adaptor,"rule cEffect");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:314:2: ( '(' 'and' ( cEffect )* ')' -> ^( AND_EFFECT ( cEffect )* ) | cEffect )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==55) ) {
                int LA49_1 = input.LA(2);

                if ( (LA49_1==92) ) {
                    alt49=1;
                }
                else if ( (LA49_1==NAME||(LA49_1 >= 93 && LA49_1 <= 94)||LA49_1==100||LA49_1==104||LA49_1==108||(LA49_1 >= 114 && LA49_1 <= 115)||LA49_1==121) ) {
                    alt49=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 49, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;

            }
            switch (alt49) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:314:4: '(' 'and' ( cEffect )* ')'
                    {
                    char_literal212=(Token)match(input,55,FOLLOW_55_in_effect1852); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal212);


                    string_literal213=(Token)match(input,92,FOLLOW_92_in_effect1854); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_92.add(string_literal213);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:314:14: ( cEffect )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==55) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:314:14: cEffect
                    	    {
                    	    pushFollow(FOLLOW_cEffect_in_effect1856);
                    	    cEffect214=cEffect();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_cEffect.add(cEffect214.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop48;
                        }
                    } while (true);


                    char_literal215=(Token)match(input,57,FOLLOW_57_in_effect1859); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal215);


                    // AST REWRITE
                    // elements: cEffect
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 314:27: -> ^( AND_EFFECT ( cEffect )* )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:314:30: ^( AND_EFFECT ( cEffect )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(AND_EFFECT, "AND_EFFECT")
                        , root_1);

                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:314:43: ( cEffect )*
                        while ( stream_cEffect.hasNext() ) {
                            adaptor.addChild(root_1, stream_cEffect.nextTree());

                        }
                        stream_cEffect.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:315:4: cEffect
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cEffect_in_effect1873);
                    cEffect216=cEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cEffect216.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 45, effect_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "effect"


    public static class cEffect_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cEffect"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:318:1: cEffect : ( '(' 'forall' '(' typedVariableList ')' effect ')' -> ^( FORALL_EFFECT typedVariableList effect ) | '(' 'when' goalDesc condEffect ')' -> ^( WHEN_EFFECT goalDesc condEffect ) | pEffect );
    public final PddlParser.cEffect_return cEffect() throws RecognitionException {
        PddlParser.cEffect_return retval = new PddlParser.cEffect_return();
        retval.start = input.LT(1);

        int cEffect_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal217=null;
        Token string_literal218=null;
        Token char_literal219=null;
        Token char_literal221=null;
        Token char_literal223=null;
        Token char_literal224=null;
        Token string_literal225=null;
        Token char_literal228=null;
        PddlParser.typedVariableList_return typedVariableList220 =null;

        PddlParser.effect_return effect222 =null;

        PddlParser.goalDesc_return goalDesc226 =null;

        PddlParser.condEffect_return condEffect227 =null;

        PddlParser.pEffect_return pEffect229 =null;


        Object char_literal217_tree=null;
        Object string_literal218_tree=null;
        Object char_literal219_tree=null;
        Object char_literal221_tree=null;
        Object char_literal223_tree=null;
        Object char_literal224_tree=null;
        Object string_literal225_tree=null;
        Object char_literal228_tree=null;
        RewriteRuleTokenStream stream_121=new RewriteRuleTokenStream(adaptor,"token 121");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_effect=new RewriteRuleSubtreeStream(adaptor,"rule effect");
        RewriteRuleSubtreeStream stream_condEffect=new RewriteRuleSubtreeStream(adaptor,"rule condEffect");
        RewriteRuleSubtreeStream stream_goalDesc=new RewriteRuleSubtreeStream(adaptor,"rule goalDesc");
        RewriteRuleSubtreeStream stream_typedVariableList=new RewriteRuleSubtreeStream(adaptor,"rule typedVariableList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:319:2: ( '(' 'forall' '(' typedVariableList ')' effect ')' -> ^( FORALL_EFFECT typedVariableList effect ) | '(' 'when' goalDesc condEffect ')' -> ^( WHEN_EFFECT goalDesc condEffect ) | pEffect )
            int alt50=3;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==55) ) {
                switch ( input.LA(2) ) {
                case 100:
                    {
                    alt50=1;
                    }
                    break;
                case 121:
                    {
                    alt50=2;
                    }
                    break;
                case NAME:
                case 93:
                case 94:
                case 104:
                case 108:
                case 114:
                case 115:
                    {
                    alt50=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 50, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;

            }
            switch (alt50) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:319:4: '(' 'forall' '(' typedVariableList ')' effect ')'
                    {
                    char_literal217=(Token)match(input,55,FOLLOW_55_in_cEffect1884); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal217);


                    string_literal218=(Token)match(input,100,FOLLOW_100_in_cEffect1886); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_100.add(string_literal218);


                    char_literal219=(Token)match(input,55,FOLLOW_55_in_cEffect1888); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal219);


                    pushFollow(FOLLOW_typedVariableList_in_cEffect1890);
                    typedVariableList220=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_typedVariableList.add(typedVariableList220.getTree());

                    char_literal221=(Token)match(input,57,FOLLOW_57_in_cEffect1892); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal221);


                    pushFollow(FOLLOW_effect_in_cEffect1894);
                    effect222=effect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_effect.add(effect222.getTree());

                    char_literal223=(Token)match(input,57,FOLLOW_57_in_cEffect1896); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal223);


                    // AST REWRITE
                    // elements: effect, typedVariableList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 320:4: -> ^( FORALL_EFFECT typedVariableList effect )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:320:7: ^( FORALL_EFFECT typedVariableList effect )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(FORALL_EFFECT, "FORALL_EFFECT")
                        , root_1);

                        adaptor.addChild(root_1, stream_typedVariableList.nextTree());

                        adaptor.addChild(root_1, stream_effect.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:321:4: '(' 'when' goalDesc condEffect ')'
                    {
                    char_literal224=(Token)match(input,55,FOLLOW_55_in_cEffect1914); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal224);


                    string_literal225=(Token)match(input,121,FOLLOW_121_in_cEffect1916); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_121.add(string_literal225);


                    pushFollow(FOLLOW_goalDesc_in_cEffect1918);
                    goalDesc226=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc226.getTree());

                    pushFollow(FOLLOW_condEffect_in_cEffect1920);
                    condEffect227=condEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_condEffect.add(condEffect227.getTree());

                    char_literal228=(Token)match(input,57,FOLLOW_57_in_cEffect1922); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal228);


                    // AST REWRITE
                    // elements: goalDesc, condEffect
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 322:4: -> ^( WHEN_EFFECT goalDesc condEffect )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:322:7: ^( WHEN_EFFECT goalDesc condEffect )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(WHEN_EFFECT, "WHEN_EFFECT")
                        , root_1);

                        adaptor.addChild(root_1, stream_goalDesc.nextTree());

                        adaptor.addChild(root_1, stream_condEffect.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:323:4: pEffect
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_pEffect_in_cEffect1940);
                    pEffect229=pEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pEffect229.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 46, cEffect_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "cEffect"


    public static class pEffect_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "pEffect"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:326:1: pEffect : ( '(' assignOp fHead fExp ')' -> ^( ASSIGN_EFFECT assignOp fHead fExp ) | '(' 'not' atomicTermFormula ')' -> ^( NOT_EFFECT atomicTermFormula ) | atomicTermFormula );
    public final PddlParser.pEffect_return pEffect() throws RecognitionException {
        PddlParser.pEffect_return retval = new PddlParser.pEffect_return();
        retval.start = input.LT(1);

        int pEffect_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal230=null;
        Token char_literal234=null;
        Token char_literal235=null;
        Token string_literal236=null;
        Token char_literal238=null;
        PddlParser.assignOp_return assignOp231 =null;

        PddlParser.fHead_return fHead232 =null;

        PddlParser.fExp_return fExp233 =null;

        PddlParser.atomicTermFormula_return atomicTermFormula237 =null;

        PddlParser.atomicTermFormula_return atomicTermFormula239 =null;


        Object char_literal230_tree=null;
        Object char_literal234_tree=null;
        Object char_literal235_tree=null;
        Object string_literal236_tree=null;
        Object char_literal238_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_fHead=new RewriteRuleSubtreeStream(adaptor,"rule fHead");
        RewriteRuleSubtreeStream stream_assignOp=new RewriteRuleSubtreeStream(adaptor,"rule assignOp");
        RewriteRuleSubtreeStream stream_atomicTermFormula=new RewriteRuleSubtreeStream(adaptor,"rule atomicTermFormula");
        RewriteRuleSubtreeStream stream_fExp=new RewriteRuleSubtreeStream(adaptor,"rule fExp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:327:2: ( '(' assignOp fHead fExp ')' -> ^( ASSIGN_EFFECT assignOp fHead fExp ) | '(' 'not' atomicTermFormula ')' -> ^( NOT_EFFECT atomicTermFormula ) | atomicTermFormula )
            int alt51=3;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==55) ) {
                switch ( input.LA(2) ) {
                case 108:
                    {
                    alt51=2;
                    }
                    break;
                case 93:
                case 94:
                case 104:
                case 114:
                case 115:
                    {
                    alt51=1;
                    }
                    break;
                case NAME:
                    {
                    alt51=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;

            }
            switch (alt51) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:327:4: '(' assignOp fHead fExp ')'
                    {
                    char_literal230=(Token)match(input,55,FOLLOW_55_in_pEffect1951); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal230);


                    pushFollow(FOLLOW_assignOp_in_pEffect1953);
                    assignOp231=assignOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_assignOp.add(assignOp231.getTree());

                    pushFollow(FOLLOW_fHead_in_pEffect1955);
                    fHead232=fHead();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fHead.add(fHead232.getTree());

                    pushFollow(FOLLOW_fExp_in_pEffect1957);
                    fExp233=fExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fExp.add(fExp233.getTree());

                    char_literal234=(Token)match(input,57,FOLLOW_57_in_pEffect1959); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal234);


                    // AST REWRITE
                    // elements: assignOp, fExp, fHead
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 328:4: -> ^( ASSIGN_EFFECT assignOp fHead fExp )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:328:7: ^( ASSIGN_EFFECT assignOp fHead fExp )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(ASSIGN_EFFECT, "ASSIGN_EFFECT")
                        , root_1);

                        adaptor.addChild(root_1, stream_assignOp.nextTree());

                        adaptor.addChild(root_1, stream_fHead.nextTree());

                        adaptor.addChild(root_1, stream_fExp.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:329:4: '(' 'not' atomicTermFormula ')'
                    {
                    char_literal235=(Token)match(input,55,FOLLOW_55_in_pEffect1979); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal235);


                    string_literal236=(Token)match(input,108,FOLLOW_108_in_pEffect1981); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_108.add(string_literal236);


                    pushFollow(FOLLOW_atomicTermFormula_in_pEffect1983);
                    atomicTermFormula237=atomicTermFormula();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_atomicTermFormula.add(atomicTermFormula237.getTree());

                    char_literal238=(Token)match(input,57,FOLLOW_57_in_pEffect1985); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal238);


                    // AST REWRITE
                    // elements: atomicTermFormula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 330:4: -> ^( NOT_EFFECT atomicTermFormula )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:330:7: ^( NOT_EFFECT atomicTermFormula )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NOT_EFFECT, "NOT_EFFECT")
                        , root_1);

                        adaptor.addChild(root_1, stream_atomicTermFormula.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:331:4: atomicTermFormula
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicTermFormula_in_pEffect2001);
                    atomicTermFormula239=atomicTermFormula();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicTermFormula239.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 47, pEffect_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "pEffect"


    public static class condEffect_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "condEffect"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:336:1: condEffect : ( '(' 'and' ( pEffect )* ')' -> ^( AND_EFFECT ( pEffect )* ) | pEffect );
    public final PddlParser.condEffect_return condEffect() throws RecognitionException {
        PddlParser.condEffect_return retval = new PddlParser.condEffect_return();
        retval.start = input.LT(1);

        int condEffect_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal240=null;
        Token string_literal241=null;
        Token char_literal243=null;
        PddlParser.pEffect_return pEffect242 =null;

        PddlParser.pEffect_return pEffect244 =null;


        Object char_literal240_tree=null;
        Object string_literal241_tree=null;
        Object char_literal243_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_pEffect=new RewriteRuleSubtreeStream(adaptor,"rule pEffect");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:337:2: ( '(' 'and' ( pEffect )* ')' -> ^( AND_EFFECT ( pEffect )* ) | pEffect )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==55) ) {
                int LA53_1 = input.LA(2);

                if ( (LA53_1==92) ) {
                    alt53=1;
                }
                else if ( (LA53_1==NAME||(LA53_1 >= 93 && LA53_1 <= 94)||LA53_1==104||LA53_1==108||(LA53_1 >= 114 && LA53_1 <= 115)) ) {
                    alt53=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;

            }
            switch (alt53) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:337:4: '(' 'and' ( pEffect )* ')'
                    {
                    char_literal240=(Token)match(input,55,FOLLOW_55_in_condEffect2014); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal240);


                    string_literal241=(Token)match(input,92,FOLLOW_92_in_condEffect2016); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_92.add(string_literal241);


                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:337:14: ( pEffect )*
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( (LA52_0==55) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:337:14: pEffect
                    	    {
                    	    pushFollow(FOLLOW_pEffect_in_condEffect2018);
                    	    pEffect242=pEffect();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_pEffect.add(pEffect242.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);


                    char_literal243=(Token)match(input,57,FOLLOW_57_in_condEffect2021); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal243);


                    // AST REWRITE
                    // elements: pEffect
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 337:27: -> ^( AND_EFFECT ( pEffect )* )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:337:30: ^( AND_EFFECT ( pEffect )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(AND_EFFECT, "AND_EFFECT")
                        , root_1);

                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:337:43: ( pEffect )*
                        while ( stream_pEffect.hasNext() ) {
                            adaptor.addChild(root_1, stream_pEffect.nextTree());

                        }
                        stream_pEffect.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:338:4: pEffect
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_pEffect_in_condEffect2035);
                    pEffect244=pEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pEffect244.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 48, condEffect_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "condEffect"


    public static class binaryOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "binaryOp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:342:1: binaryOp : ( '*' | '+' | '-' | '/' );
    public final PddlParser.binaryOp_return binaryOp() throws RecognitionException {
        PddlParser.binaryOp_return retval = new PddlParser.binaryOp_return();
        retval.start = input.LT(1);

        int binaryOp_StartIndex = input.index();

        Object root_0 = null;

        Token set245=null;

        Object set245_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:342:10: ( '*' | '+' | '-' | '/' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set245=(Token)input.LT(1);

            if ( (input.LA(1) >= 58 && input.LA(1) <= 61) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set245)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 49, binaryOp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "binaryOp"


    public static class binaryComp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "binaryComp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:344:1: binaryComp : ( '>' | '<' | '=' | '>=' | '<=' );
    public final PddlParser.binaryComp_return binaryComp() throws RecognitionException {
        PddlParser.binaryComp_return retval = new PddlParser.binaryComp_return();
        retval.start = input.LT(1);

        int binaryComp_StartIndex = input.index();

        Object root_0 = null;

        Token set246=null;

        Object set246_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:344:12: ( '>' | '<' | '=' | '>=' | '<=' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set246=(Token)input.LT(1);

            if ( (input.LA(1) >= 81 && input.LA(1) <= 82)||(input.LA(1) >= 85 && input.LA(1) <= 87) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set246)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 50, binaryComp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "binaryComp"


    public static class assignOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assignOp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:346:1: assignOp : ( 'assign' | 'scale-up' | 'scale-down' | 'increase' | 'decrease' );
    public final PddlParser.assignOp_return assignOp() throws RecognitionException {
        PddlParser.assignOp_return retval = new PddlParser.assignOp_return();
        retval.start = input.LT(1);

        int assignOp_StartIndex = input.index();

        Object root_0 = null;

        Token set247=null;

        Object set247_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:346:10: ( 'assign' | 'scale-up' | 'scale-down' | 'increase' | 'decrease' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set247=(Token)input.LT(1);

            if ( (input.LA(1) >= 93 && input.LA(1) <= 94)||input.LA(1)==104||(input.LA(1) >= 114 && input.LA(1) <= 115) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set247)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 51, assignOp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "assignOp"


    public static class durationConstraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "durationConstraint"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:351:1: durationConstraint : ( '(' 'and' ( simpleDurationConstraint )+ ')' | '(' ')' | simpleDurationConstraint );
    public final PddlParser.durationConstraint_return durationConstraint() throws RecognitionException {
        PddlParser.durationConstraint_return retval = new PddlParser.durationConstraint_return();
        retval.start = input.LT(1);

        int durationConstraint_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal248=null;
        Token string_literal249=null;
        Token char_literal251=null;
        Token char_literal252=null;
        Token char_literal253=null;
        PddlParser.simpleDurationConstraint_return simpleDurationConstraint250 =null;

        PddlParser.simpleDurationConstraint_return simpleDurationConstraint254 =null;


        Object char_literal248_tree=null;
        Object string_literal249_tree=null;
        Object char_literal251_tree=null;
        Object char_literal252_tree=null;
        Object char_literal253_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:352:2: ( '(' 'and' ( simpleDurationConstraint )+ ')' | '(' ')' | simpleDurationConstraint )
            int alt55=3;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==55) ) {
                switch ( input.LA(2) ) {
                case 92:
                    {
                    alt55=1;
                    }
                    break;
                case 57:
                    {
                    alt55=2;
                    }
                    break;
                case 82:
                case 83:
                case 85:
                case 87:
                    {
                    alt55=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 55, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;

            }
            switch (alt55) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:352:4: '(' 'and' ( simpleDurationConstraint )+ ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal248=(Token)match(input,55,FOLLOW_55_in_durationConstraint2122); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal248_tree = 
                    (Object)adaptor.create(char_literal248)
                    ;
                    adaptor.addChild(root_0, char_literal248_tree);
                    }

                    string_literal249=(Token)match(input,92,FOLLOW_92_in_durationConstraint2124); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal249_tree = 
                    (Object)adaptor.create(string_literal249)
                    ;
                    adaptor.addChild(root_0, string_literal249_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:352:14: ( simpleDurationConstraint )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==55) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:352:14: simpleDurationConstraint
                    	    {
                    	    pushFollow(FOLLOW_simpleDurationConstraint_in_durationConstraint2126);
                    	    simpleDurationConstraint250=simpleDurationConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleDurationConstraint250.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt54 >= 1 ) break loop54;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(54, input);
                                throw eee;
                        }
                        cnt54++;
                    } while (true);


                    char_literal251=(Token)match(input,57,FOLLOW_57_in_durationConstraint2129); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal251_tree = 
                    (Object)adaptor.create(char_literal251)
                    ;
                    adaptor.addChild(root_0, char_literal251_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:353:4: '(' ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal252=(Token)match(input,55,FOLLOW_55_in_durationConstraint2134); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal252_tree = 
                    (Object)adaptor.create(char_literal252)
                    ;
                    adaptor.addChild(root_0, char_literal252_tree);
                    }

                    char_literal253=(Token)match(input,57,FOLLOW_57_in_durationConstraint2136); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal253_tree = 
                    (Object)adaptor.create(char_literal253)
                    ;
                    adaptor.addChild(root_0, char_literal253_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:354:4: simpleDurationConstraint
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_simpleDurationConstraint_in_durationConstraint2141);
                    simpleDurationConstraint254=simpleDurationConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleDurationConstraint254.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 52, durationConstraint_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "durationConstraint"


    public static class simpleDurationConstraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "simpleDurationConstraint"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:357:1: simpleDurationConstraint : ( '(' durOp '?duration' durValue ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier simpleDurationConstraint ')' );
    public final PddlParser.simpleDurationConstraint_return simpleDurationConstraint() throws RecognitionException {
        PddlParser.simpleDurationConstraint_return retval = new PddlParser.simpleDurationConstraint_return();
        retval.start = input.LT(1);

        int simpleDurationConstraint_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal255=null;
        Token string_literal257=null;
        Token char_literal259=null;
        Token char_literal260=null;
        Token string_literal261=null;
        Token char_literal264=null;
        PddlParser.durOp_return durOp256 =null;

        PddlParser.durValue_return durValue258 =null;

        PddlParser.timeSpecifier_return timeSpecifier262 =null;

        PddlParser.simpleDurationConstraint_return simpleDurationConstraint263 =null;


        Object char_literal255_tree=null;
        Object string_literal257_tree=null;
        Object char_literal259_tree=null;
        Object char_literal260_tree=null;
        Object string_literal261_tree=null;
        Object char_literal264_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:358:2: ( '(' durOp '?duration' durValue ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier simpleDurationConstraint ')' )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==55) ) {
                int LA56_1 = input.LA(2);

                if ( (LA56_1==83) ) {
                    alt56=2;
                }
                else if ( (LA56_1==82||LA56_1==85||LA56_1==87) ) {
                    alt56=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 56, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;

            }
            switch (alt56) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:358:4: '(' durOp '?duration' durValue ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal255=(Token)match(input,55,FOLLOW_55_in_simpleDurationConstraint2152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal255_tree = 
                    (Object)adaptor.create(char_literal255)
                    ;
                    adaptor.addChild(root_0, char_literal255_tree);
                    }

                    pushFollow(FOLLOW_durOp_in_simpleDurationConstraint2154);
                    durOp256=durOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, durOp256.getTree());

                    string_literal257=(Token)match(input,88,FOLLOW_88_in_simpleDurationConstraint2156); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal257_tree = 
                    (Object)adaptor.create(string_literal257)
                    ;
                    adaptor.addChild(root_0, string_literal257_tree);
                    }

                    pushFollow(FOLLOW_durValue_in_simpleDurationConstraint2158);
                    durValue258=durValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, durValue258.getTree());

                    char_literal259=(Token)match(input,57,FOLLOW_57_in_simpleDurationConstraint2160); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal259_tree = 
                    (Object)adaptor.create(char_literal259)
                    ;
                    adaptor.addChild(root_0, char_literal259_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:359:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier simpleDurationConstraint ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal260=(Token)match(input,55,FOLLOW_55_in_simpleDurationConstraint2165); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal260_tree = 
                    (Object)adaptor.create(char_literal260)
                    ;
                    adaptor.addChild(root_0, char_literal260_tree);
                    }

                    string_literal261=(Token)match(input,83,FOLLOW_83_in_simpleDurationConstraint2167); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal261_tree = 
                    (Object)adaptor.create(string_literal261)
                    ;
                    adaptor.addChild(root_0, string_literal261_tree);
                    }

                    pushFollow(FOLLOW_timeSpecifier_in_simpleDurationConstraint2169);
                    timeSpecifier262=timeSpecifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeSpecifier262.getTree());

                    pushFollow(FOLLOW_simpleDurationConstraint_in_simpleDurationConstraint2171);
                    simpleDurationConstraint263=simpleDurationConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleDurationConstraint263.getTree());

                    char_literal264=(Token)match(input,57,FOLLOW_57_in_simpleDurationConstraint2173); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal264_tree = 
                    (Object)adaptor.create(char_literal264)
                    ;
                    adaptor.addChild(root_0, char_literal264_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 53, simpleDurationConstraint_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "simpleDurationConstraint"


    public static class durOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "durOp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:362:1: durOp : ( '<=' | '>=' | '=' );
    public final PddlParser.durOp_return durOp() throws RecognitionException {
        PddlParser.durOp_return retval = new PddlParser.durOp_return();
        retval.start = input.LT(1);

        int durOp_StartIndex = input.index();

        Object root_0 = null;

        Token set265=null;

        Object set265_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:362:7: ( '<=' | '>=' | '=' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set265=(Token)input.LT(1);

            if ( input.LA(1)==82||input.LA(1)==85||input.LA(1)==87 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set265)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 54, durOp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "durOp"


    public static class durValue_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "durValue"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:364:1: durValue : ( NUMBER | fExp );
    public final PddlParser.durValue_return durValue() throws RecognitionException {
        PddlParser.durValue_return retval = new PddlParser.durValue_return();
        retval.start = input.LT(1);

        int durValue_StartIndex = input.index();

        Object root_0 = null;

        Token NUMBER266=null;
        PddlParser.fExp_return fExp267 =null;


        Object NUMBER266_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:364:10: ( NUMBER | fExp )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==NUMBER) ) {
                int LA57_1 = input.LA(2);

                if ( (synpred86_Pddl()) ) {
                    alt57=1;
                }
                else if ( (true) ) {
                    alt57=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 57, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA57_0==NAME||LA57_0==55) ) {
                alt57=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;

            }
            switch (alt57) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:364:12: NUMBER
                    {
                    root_0 = (Object)adaptor.nil();


                    NUMBER266=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_durValue2200); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER266_tree = 
                    (Object)adaptor.create(NUMBER266)
                    ;
                    adaptor.addChild(root_0, NUMBER266_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:364:21: fExp
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_fExp_in_durValue2204);
                    fExp267=fExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fExp267.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 55, durValue_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "durValue"


    public static class daEffect_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "daEffect"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:366:1: daEffect : ( '(' 'and' ( daEffect )* ')' | timedEffect | '(' 'forall' '(' typedVariableList ')' daEffect ')' | '(' 'when' daGD timedEffect ')' | '(' assignOp fHead fExpDA ')' );
    public final PddlParser.daEffect_return daEffect() throws RecognitionException {
        PddlParser.daEffect_return retval = new PddlParser.daEffect_return();
        retval.start = input.LT(1);

        int daEffect_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal268=null;
        Token string_literal269=null;
        Token char_literal271=null;
        Token char_literal273=null;
        Token string_literal274=null;
        Token char_literal275=null;
        Token char_literal277=null;
        Token char_literal279=null;
        Token char_literal280=null;
        Token string_literal281=null;
        Token char_literal284=null;
        Token char_literal285=null;
        Token char_literal289=null;
        PddlParser.daEffect_return daEffect270 =null;

        PddlParser.timedEffect_return timedEffect272 =null;

        PddlParser.typedVariableList_return typedVariableList276 =null;

        PddlParser.daEffect_return daEffect278 =null;

        PddlParser.daGD_return daGD282 =null;

        PddlParser.timedEffect_return timedEffect283 =null;

        PddlParser.assignOp_return assignOp286 =null;

        PddlParser.fHead_return fHead287 =null;

        PddlParser.fExpDA_return fExpDA288 =null;


        Object char_literal268_tree=null;
        Object string_literal269_tree=null;
        Object char_literal271_tree=null;
        Object char_literal273_tree=null;
        Object string_literal274_tree=null;
        Object char_literal275_tree=null;
        Object char_literal277_tree=null;
        Object char_literal279_tree=null;
        Object char_literal280_tree=null;
        Object string_literal281_tree=null;
        Object char_literal284_tree=null;
        Object char_literal285_tree=null;
        Object char_literal289_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:2: ( '(' 'and' ( daEffect )* ')' | timedEffect | '(' 'forall' '(' typedVariableList ')' daEffect ')' | '(' 'when' daGD timedEffect ')' | '(' assignOp fHead fExpDA ')' )
            int alt59=5;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==55) ) {
                int LA59_1 = input.LA(2);

                if ( (synpred88_Pddl()) ) {
                    alt59=1;
                }
                else if ( (synpred89_Pddl()) ) {
                    alt59=2;
                }
                else if ( (synpred90_Pddl()) ) {
                    alt59=3;
                }
                else if ( (synpred91_Pddl()) ) {
                    alt59=4;
                }
                else if ( (true) ) {
                    alt59=5;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 59, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;

            }
            switch (alt59) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:4: '(' 'and' ( daEffect )* ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal268=(Token)match(input,55,FOLLOW_55_in_daEffect2214); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal268_tree = 
                    (Object)adaptor.create(char_literal268)
                    ;
                    adaptor.addChild(root_0, char_literal268_tree);
                    }

                    string_literal269=(Token)match(input,92,FOLLOW_92_in_daEffect2216); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal269_tree = 
                    (Object)adaptor.create(string_literal269)
                    ;
                    adaptor.addChild(root_0, string_literal269_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:14: ( daEffect )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==55) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:14: daEffect
                    	    {
                    	    pushFollow(FOLLOW_daEffect_in_daEffect2218);
                    	    daEffect270=daEffect();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, daEffect270.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);


                    char_literal271=(Token)match(input,57,FOLLOW_57_in_daEffect2221); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal271_tree = 
                    (Object)adaptor.create(char_literal271)
                    ;
                    adaptor.addChild(root_0, char_literal271_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:368:4: timedEffect
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timedEffect_in_daEffect2226);
                    timedEffect272=timedEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timedEffect272.getTree());

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:369:4: '(' 'forall' '(' typedVariableList ')' daEffect ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal273=(Token)match(input,55,FOLLOW_55_in_daEffect2231); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal273_tree = 
                    (Object)adaptor.create(char_literal273)
                    ;
                    adaptor.addChild(root_0, char_literal273_tree);
                    }

                    string_literal274=(Token)match(input,100,FOLLOW_100_in_daEffect2233); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal274_tree = 
                    (Object)adaptor.create(string_literal274)
                    ;
                    adaptor.addChild(root_0, string_literal274_tree);
                    }

                    char_literal275=(Token)match(input,55,FOLLOW_55_in_daEffect2235); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal275_tree = 
                    (Object)adaptor.create(char_literal275)
                    ;
                    adaptor.addChild(root_0, char_literal275_tree);
                    }

                    pushFollow(FOLLOW_typedVariableList_in_daEffect2237);
                    typedVariableList276=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList276.getTree());

                    char_literal277=(Token)match(input,57,FOLLOW_57_in_daEffect2239); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal277_tree = 
                    (Object)adaptor.create(char_literal277)
                    ;
                    adaptor.addChild(root_0, char_literal277_tree);
                    }

                    pushFollow(FOLLOW_daEffect_in_daEffect2241);
                    daEffect278=daEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, daEffect278.getTree());

                    char_literal279=(Token)match(input,57,FOLLOW_57_in_daEffect2243); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal279_tree = 
                    (Object)adaptor.create(char_literal279)
                    ;
                    adaptor.addChild(root_0, char_literal279_tree);
                    }

                    }
                    break;
                case 4 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:370:4: '(' 'when' daGD timedEffect ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal280=(Token)match(input,55,FOLLOW_55_in_daEffect2248); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal280_tree = 
                    (Object)adaptor.create(char_literal280)
                    ;
                    adaptor.addChild(root_0, char_literal280_tree);
                    }

                    string_literal281=(Token)match(input,121,FOLLOW_121_in_daEffect2250); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal281_tree = 
                    (Object)adaptor.create(string_literal281)
                    ;
                    adaptor.addChild(root_0, string_literal281_tree);
                    }

                    pushFollow(FOLLOW_daGD_in_daEffect2252);
                    daGD282=daGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, daGD282.getTree());

                    pushFollow(FOLLOW_timedEffect_in_daEffect2254);
                    timedEffect283=timedEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timedEffect283.getTree());

                    char_literal284=(Token)match(input,57,FOLLOW_57_in_daEffect2256); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal284_tree = 
                    (Object)adaptor.create(char_literal284)
                    ;
                    adaptor.addChild(root_0, char_literal284_tree);
                    }

                    }
                    break;
                case 5 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:371:4: '(' assignOp fHead fExpDA ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal285=(Token)match(input,55,FOLLOW_55_in_daEffect2261); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal285_tree = 
                    (Object)adaptor.create(char_literal285)
                    ;
                    adaptor.addChild(root_0, char_literal285_tree);
                    }

                    pushFollow(FOLLOW_assignOp_in_daEffect2263);
                    assignOp286=assignOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignOp286.getTree());

                    pushFollow(FOLLOW_fHead_in_daEffect2265);
                    fHead287=fHead();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fHead287.getTree());

                    pushFollow(FOLLOW_fExpDA_in_daEffect2267);
                    fExpDA288=fExpDA();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fExpDA288.getTree());

                    char_literal289=(Token)match(input,57,FOLLOW_57_in_daEffect2269); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal289_tree = 
                    (Object)adaptor.create(char_literal289)
                    ;
                    adaptor.addChild(root_0, char_literal289_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 56, daEffect_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "daEffect"


    public static class timedEffect_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timedEffect"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:374:1: timedEffect : ( '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier daEffect ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier fAssignDA ')' | '(' assignOp fHead fExp ')' );
    public final PddlParser.timedEffect_return timedEffect() throws RecognitionException {
        PddlParser.timedEffect_return retval = new PddlParser.timedEffect_return();
        retval.start = input.LT(1);

        int timedEffect_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal290=null;
        Token string_literal291=null;
        Token char_literal294=null;
        Token char_literal295=null;
        Token string_literal296=null;
        Token char_literal299=null;
        Token char_literal300=null;
        Token char_literal304=null;
        PddlParser.timeSpecifier_return timeSpecifier292 =null;

        PddlParser.daEffect_return daEffect293 =null;

        PddlParser.timeSpecifier_return timeSpecifier297 =null;

        PddlParser.fAssignDA_return fAssignDA298 =null;

        PddlParser.assignOp_return assignOp301 =null;

        PddlParser.fHead_return fHead302 =null;

        PddlParser.fExp_return fExp303 =null;


        Object char_literal290_tree=null;
        Object string_literal291_tree=null;
        Object char_literal294_tree=null;
        Object char_literal295_tree=null;
        Object string_literal296_tree=null;
        Object char_literal299_tree=null;
        Object char_literal300_tree=null;
        Object char_literal304_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:375:2: ( '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier daEffect ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier fAssignDA ')' | '(' assignOp fHead fExp ')' )
            int alt60=3;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==55) ) {
                int LA60_1 = input.LA(2);

                if ( (synpred92_Pddl()) ) {
                    alt60=1;
                }
                else if ( (synpred93_Pddl()) ) {
                    alt60=2;
                }
                else if ( (true) ) {
                    alt60=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 60, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;

            }
            switch (alt60) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:375:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier daEffect ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal290=(Token)match(input,55,FOLLOW_55_in_timedEffect2280); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal290_tree = 
                    (Object)adaptor.create(char_literal290)
                    ;
                    adaptor.addChild(root_0, char_literal290_tree);
                    }

                    string_literal291=(Token)match(input,83,FOLLOW_83_in_timedEffect2282); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal291_tree = 
                    (Object)adaptor.create(string_literal291)
                    ;
                    adaptor.addChild(root_0, string_literal291_tree);
                    }

                    pushFollow(FOLLOW_timeSpecifier_in_timedEffect2284);
                    timeSpecifier292=timeSpecifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeSpecifier292.getTree());

                    pushFollow(FOLLOW_daEffect_in_timedEffect2286);
                    daEffect293=daEffect();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, daEffect293.getTree());

                    char_literal294=(Token)match(input,57,FOLLOW_57_in_timedEffect2288); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal294_tree = 
                    (Object)adaptor.create(char_literal294)
                    ;
                    adaptor.addChild(root_0, char_literal294_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:376:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier fAssignDA ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal295=(Token)match(input,55,FOLLOW_55_in_timedEffect2298); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal295_tree = 
                    (Object)adaptor.create(char_literal295)
                    ;
                    adaptor.addChild(root_0, char_literal295_tree);
                    }

                    string_literal296=(Token)match(input,83,FOLLOW_83_in_timedEffect2300); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal296_tree = 
                    (Object)adaptor.create(string_literal296)
                    ;
                    adaptor.addChild(root_0, string_literal296_tree);
                    }

                    pushFollow(FOLLOW_timeSpecifier_in_timedEffect2302);
                    timeSpecifier297=timeSpecifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeSpecifier297.getTree());

                    pushFollow(FOLLOW_fAssignDA_in_timedEffect2304);
                    fAssignDA298=fAssignDA();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fAssignDA298.getTree());

                    char_literal299=(Token)match(input,57,FOLLOW_57_in_timedEffect2306); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal299_tree = 
                    (Object)adaptor.create(char_literal299)
                    ;
                    adaptor.addChild(root_0, char_literal299_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:377:4: '(' assignOp fHead fExp ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal300=(Token)match(input,55,FOLLOW_55_in_timedEffect2311); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal300_tree = 
                    (Object)adaptor.create(char_literal300)
                    ;
                    adaptor.addChild(root_0, char_literal300_tree);
                    }

                    pushFollow(FOLLOW_assignOp_in_timedEffect2313);
                    assignOp301=assignOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignOp301.getTree());

                    pushFollow(FOLLOW_fHead_in_timedEffect2315);
                    fHead302=fHead();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fHead302.getTree());

                    pushFollow(FOLLOW_fExp_in_timedEffect2317);
                    fExp303=fExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fExp303.getTree());

                    char_literal304=(Token)match(input,57,FOLLOW_57_in_timedEffect2319); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal304_tree = 
                    (Object)adaptor.create(char_literal304)
                    ;
                    adaptor.addChild(root_0, char_literal304_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 57, timedEffect_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timedEffect"


    public static class fAssignDA_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fAssignDA"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:380:1: fAssignDA : '(' assignOp fHead fExpDA ')' ;
    public final PddlParser.fAssignDA_return fAssignDA() throws RecognitionException {
        PddlParser.fAssignDA_return retval = new PddlParser.fAssignDA_return();
        retval.start = input.LT(1);

        int fAssignDA_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal305=null;
        Token char_literal309=null;
        PddlParser.assignOp_return assignOp306 =null;

        PddlParser.fHead_return fHead307 =null;

        PddlParser.fExpDA_return fExpDA308 =null;


        Object char_literal305_tree=null;
        Object char_literal309_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:381:2: ( '(' assignOp fHead fExpDA ')' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:381:4: '(' assignOp fHead fExpDA ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal305=(Token)match(input,55,FOLLOW_55_in_fAssignDA2339); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal305_tree = 
            (Object)adaptor.create(char_literal305)
            ;
            adaptor.addChild(root_0, char_literal305_tree);
            }

            pushFollow(FOLLOW_assignOp_in_fAssignDA2341);
            assignOp306=assignOp();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, assignOp306.getTree());

            pushFollow(FOLLOW_fHead_in_fAssignDA2343);
            fHead307=fHead();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, fHead307.getTree());

            pushFollow(FOLLOW_fExpDA_in_fAssignDA2345);
            fExpDA308=fExpDA();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExpDA308.getTree());

            char_literal309=(Token)match(input,57,FOLLOW_57_in_fAssignDA2347); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal309_tree = 
            (Object)adaptor.create(char_literal309)
            ;
            adaptor.addChild(root_0, char_literal309_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 58, fAssignDA_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fAssignDA"


    public static class fExpDA_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fExpDA"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:384:1: fExpDA : ( '(' ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) ) ')' | '?duration' | fExp );
    public final PddlParser.fExpDA_return fExpDA() throws RecognitionException {
        PddlParser.fExpDA_return retval = new PddlParser.fExpDA_return();
        retval.start = input.LT(1);

        int fExpDA_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal310=null;
        Token char_literal314=null;
        Token char_literal316=null;
        Token string_literal317=null;
        PddlParser.binaryOp_return binaryOp311 =null;

        PddlParser.fExpDA_return fExpDA312 =null;

        PddlParser.fExpDA_return fExpDA313 =null;

        PddlParser.fExpDA_return fExpDA315 =null;

        PddlParser.fExp_return fExp318 =null;


        Object char_literal310_tree=null;
        Object char_literal314_tree=null;
        Object char_literal316_tree=null;
        Object string_literal317_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:2: ( '(' ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) ) ')' | '?duration' | fExp )
            int alt62=3;
            switch ( input.LA(1) ) {
            case 55:
                {
                int LA62_1 = input.LA(2);

                if ( (synpred95_Pddl()) ) {
                    alt62=1;
                }
                else if ( (true) ) {
                    alt62=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 62, 1, input);

                    throw nvae;

                }
                }
                break;
            case 88:
                {
                alt62=2;
                }
                break;
            case NAME:
            case NUMBER:
                {
                alt62=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;

            }

            switch (alt62) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:4: '(' ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) ) ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal310=(Token)match(input,55,FOLLOW_55_in_fExpDA2358); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal310_tree = 
                    (Object)adaptor.create(char_literal310)
                    ;
                    adaptor.addChild(root_0, char_literal310_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:8: ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) )
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==60) ) {
                        int LA61_1 = input.LA(2);

                        if ( (synpred94_Pddl()) ) {
                            alt61=1;
                        }
                        else if ( (true) ) {
                            alt61=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 61, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA61_0 >= 58 && LA61_0 <= 59)||LA61_0==61) ) {
                        alt61=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 61, 0, input);

                        throw nvae;

                    }
                    switch (alt61) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( binaryOp fExpDA fExpDA )
                            {
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( binaryOp fExpDA fExpDA )
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:10: binaryOp fExpDA fExpDA
                            {
                            pushFollow(FOLLOW_binaryOp_in_fExpDA2362);
                            binaryOp311=binaryOp();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, binaryOp311.getTree());

                            pushFollow(FOLLOW_fExpDA_in_fExpDA2364);
                            fExpDA312=fExpDA();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExpDA312.getTree());

                            pushFollow(FOLLOW_fExpDA_in_fExpDA2366);
                            fExpDA313=fExpDA();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExpDA313.getTree());

                            }


                            }
                            break;
                        case 2 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:36: ( '-' fExpDA )
                            {
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:36: ( '-' fExpDA )
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:37: '-' fExpDA
                            {
                            char_literal314=(Token)match(input,60,FOLLOW_60_in_fExpDA2372); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal314_tree = 
                            (Object)adaptor.create(char_literal314)
                            ;
                            adaptor.addChild(root_0, char_literal314_tree);
                            }

                            pushFollow(FOLLOW_fExpDA_in_fExpDA2374);
                            fExpDA315=fExpDA();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, fExpDA315.getTree());

                            }


                            }
                            break;

                    }


                    char_literal316=(Token)match(input,57,FOLLOW_57_in_fExpDA2378); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal316_tree = 
                    (Object)adaptor.create(char_literal316)
                    ;
                    adaptor.addChild(root_0, char_literal316_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:386:4: '?duration'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal317=(Token)match(input,88,FOLLOW_88_in_fExpDA2383); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal317_tree = 
                    (Object)adaptor.create(string_literal317)
                    ;
                    adaptor.addChild(root_0, string_literal317_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:387:4: fExp
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_fExp_in_fExpDA2388);
                    fExp318=fExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fExp318.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 59, fExpDA_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "fExpDA"


    public static class problem_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "problem"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:392:1: problem : '(' 'define' problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? ')' -> ^( PROBLEM problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? ) ;
    public final PddlParser.problem_return problem() throws RecognitionException {
        PddlParser.problem_return retval = new PddlParser.problem_return();
        retval.start = input.LT(1);

        int problem_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal319=null;
        Token string_literal320=null;
        Token char_literal329=null;
        PddlParser.problemDecl_return problemDecl321 =null;

        PddlParser.problemDomain_return problemDomain322 =null;

        PddlParser.requireDef_return requireDef323 =null;

        PddlParser.objectDecl_return objectDecl324 =null;

        PddlParser.init_return init325 =null;

        PddlParser.goal_return goal326 =null;

        PddlParser.probConstraints_return probConstraints327 =null;

        PddlParser.metricSpec_return metricSpec328 =null;


        Object char_literal319_tree=null;
        Object string_literal320_tree=null;
        Object char_literal329_tree=null;
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_metricSpec=new RewriteRuleSubtreeStream(adaptor,"rule metricSpec");
        RewriteRuleSubtreeStream stream_objectDecl=new RewriteRuleSubtreeStream(adaptor,"rule objectDecl");
        RewriteRuleSubtreeStream stream_problemDecl=new RewriteRuleSubtreeStream(adaptor,"rule problemDecl");
        RewriteRuleSubtreeStream stream_probConstraints=new RewriteRuleSubtreeStream(adaptor,"rule probConstraints");
        RewriteRuleSubtreeStream stream_requireDef=new RewriteRuleSubtreeStream(adaptor,"rule requireDef");
        RewriteRuleSubtreeStream stream_init=new RewriteRuleSubtreeStream(adaptor,"rule init");
        RewriteRuleSubtreeStream stream_problemDomain=new RewriteRuleSubtreeStream(adaptor,"rule problemDomain");
        RewriteRuleSubtreeStream stream_goal=new RewriteRuleSubtreeStream(adaptor,"rule goal");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:393:2: ( '(' 'define' problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? ')' -> ^( PROBLEM problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:393:4: '(' 'define' problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? ')'
            {
            char_literal319=(Token)match(input,55,FOLLOW_55_in_problem2402); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal319);


            string_literal320=(Token)match(input,95,FOLLOW_95_in_problem2404); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_95.add(string_literal320);


            pushFollow(FOLLOW_problemDecl_in_problem2406);
            problemDecl321=problemDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_problemDecl.add(problemDecl321.getTree());

            pushFollow(FOLLOW_problemDomain_in_problem2411);
            problemDomain322=problemDomain();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_problemDomain.add(problemDomain322.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:395:7: ( requireDef )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==55) ) {
                int LA63_1 = input.LA(2);

                if ( (LA63_1==79) ) {
                    alt63=1;
                }
            }
            switch (alt63) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:395:7: requireDef
                    {
                    pushFollow(FOLLOW_requireDef_in_problem2419);
                    requireDef323=requireDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_requireDef.add(requireDef323.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:396:7: ( objectDecl )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==55) ) {
                int LA64_1 = input.LA(2);

                if ( (LA64_1==75) ) {
                    alt64=1;
                }
            }
            switch (alt64) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:396:7: objectDecl
                    {
                    pushFollow(FOLLOW_objectDecl_in_problem2428);
                    objectDecl324=objectDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_objectDecl.add(objectDecl324.getTree());

                    }
                    break;

            }


            pushFollow(FOLLOW_init_in_problem2437);
            init325=init();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_init.add(init325.getTree());

            pushFollow(FOLLOW_goal_in_problem2445);
            goal326=goal();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_goal.add(goal326.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:399:7: ( probConstraints )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==55) ) {
                int LA65_1 = input.LA(2);

                if ( (LA65_1==65) ) {
                    alt65=1;
                }
            }
            switch (alt65) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:399:7: probConstraints
                    {
                    pushFollow(FOLLOW_probConstraints_in_problem2453);
                    probConstraints327=probConstraints();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_probConstraints.add(probConstraints327.getTree());

                    }
                    break;

            }


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:400:7: ( metricSpec )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==55) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:400:7: metricSpec
                    {
                    pushFollow(FOLLOW_metricSpec_in_problem2462);
                    metricSpec328=metricSpec();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_metricSpec.add(metricSpec328.getTree());

                    }
                    break;

            }


            char_literal329=(Token)match(input,57,FOLLOW_57_in_problem2478); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal329);


            // AST REWRITE
            // elements: problemDomain, init, probConstraints, problemDecl, requireDef, metricSpec, objectDecl, goal
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 403:7: -> ^( PROBLEM problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:403:10: ^( PROBLEM problemDecl problemDomain ( requireDef )? ( objectDecl )? init goal ( probConstraints )? ( metricSpec )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROBLEM, "PROBLEM")
                , root_1);

                adaptor.addChild(root_1, stream_problemDecl.nextTree());

                adaptor.addChild(root_1, stream_problemDomain.nextTree());

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:403:46: ( requireDef )?
                if ( stream_requireDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_requireDef.nextTree());

                }
                stream_requireDef.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:403:58: ( objectDecl )?
                if ( stream_objectDecl.hasNext() ) {
                    adaptor.addChild(root_1, stream_objectDecl.nextTree());

                }
                stream_objectDecl.reset();

                adaptor.addChild(root_1, stream_init.nextTree());

                adaptor.addChild(root_1, stream_goal.nextTree());

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:404:19: ( probConstraints )?
                if ( stream_probConstraints.hasNext() ) {
                    adaptor.addChild(root_1, stream_probConstraints.nextTree());

                }
                stream_probConstraints.reset();

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:404:36: ( metricSpec )?
                if ( stream_metricSpec.hasNext() ) {
                    adaptor.addChild(root_1, stream_metricSpec.nextTree());

                }
                stream_metricSpec.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 60, problem_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "problem"


    public static class problemDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "problemDecl"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:407:1: problemDecl : '(' 'problem' NAME ')' -> ^( PROBLEM_NAME NAME ) ;
    public final PddlParser.problemDecl_return problemDecl() throws RecognitionException {
        PddlParser.problemDecl_return retval = new PddlParser.problemDecl_return();
        retval.start = input.LT(1);

        int problemDecl_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal330=null;
        Token string_literal331=null;
        Token NAME332=null;
        Token char_literal333=null;

        Object char_literal330_tree=null;
        Object string_literal331_tree=null;
        Object NAME332_tree=null;
        Object char_literal333_tree=null;
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:408:5: ( '(' 'problem' NAME ')' -> ^( PROBLEM_NAME NAME ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:408:7: '(' 'problem' NAME ')'
            {
            char_literal330=(Token)match(input,55,FOLLOW_55_in_problemDecl2535); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal330);


            string_literal331=(Token)match(input,113,FOLLOW_113_in_problemDecl2537); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_113.add(string_literal331);


            NAME332=(Token)match(input,NAME,FOLLOW_NAME_in_problemDecl2539); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME332);


            char_literal333=(Token)match(input,57,FOLLOW_57_in_problemDecl2541); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal333);


            // AST REWRITE
            // elements: NAME
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 409:5: -> ^( PROBLEM_NAME NAME )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:409:8: ^( PROBLEM_NAME NAME )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROBLEM_NAME, "PROBLEM_NAME")
                , root_1);

                adaptor.addChild(root_1, 
                stream_NAME.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 61, problemDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "problemDecl"


    public static class problemDomain_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "problemDomain"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:412:1: problemDomain : '(' ':domain' NAME ')' -> ^( PROBLEM_DOMAIN NAME ) ;
    public final PddlParser.problemDomain_return problemDomain() throws RecognitionException {
        PddlParser.problemDomain_return retval = new PddlParser.problemDomain_return();
        retval.start = input.LT(1);

        int problemDomain_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal334=null;
        Token string_literal335=null;
        Token NAME336=null;
        Token char_literal337=null;

        Object char_literal334_tree=null;
        Object string_literal335_tree=null;
        Object NAME336_tree=null;
        Object char_literal337_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:413:2: ( '(' ':domain' NAME ')' -> ^( PROBLEM_DOMAIN NAME ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:413:4: '(' ':domain' NAME ')'
            {
            char_literal334=(Token)match(input,55,FOLLOW_55_in_problemDomain2567); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal334);


            string_literal335=(Token)match(input,67,FOLLOW_67_in_problemDomain2569); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(string_literal335);


            NAME336=(Token)match(input,NAME,FOLLOW_NAME_in_problemDomain2571); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME336);


            char_literal337=(Token)match(input,57,FOLLOW_57_in_problemDomain2573); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal337);


            // AST REWRITE
            // elements: NAME
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 414:2: -> ^( PROBLEM_DOMAIN NAME )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:414:5: ^( PROBLEM_DOMAIN NAME )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROBLEM_DOMAIN, "PROBLEM_DOMAIN")
                , root_1);

                adaptor.addChild(root_1, 
                stream_NAME.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 62, problemDomain_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "problemDomain"


    public static class objectDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "objectDecl"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:417:1: objectDecl : '(' ':objects' typedNameList ')' -> ^( OBJECTS typedNameList ) ;
    public final PddlParser.objectDecl_return objectDecl() throws RecognitionException {
        PddlParser.objectDecl_return retval = new PddlParser.objectDecl_return();
        retval.start = input.LT(1);

        int objectDecl_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal338=null;
        Token string_literal339=null;
        Token char_literal341=null;
        PddlParser.typedNameList_return typedNameList340 =null;


        Object char_literal338_tree=null;
        Object string_literal339_tree=null;
        Object char_literal341_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_typedNameList=new RewriteRuleSubtreeStream(adaptor,"rule typedNameList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:418:2: ( '(' ':objects' typedNameList ')' -> ^( OBJECTS typedNameList ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:418:4: '(' ':objects' typedNameList ')'
            {
            char_literal338=(Token)match(input,55,FOLLOW_55_in_objectDecl2593); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal338);


            string_literal339=(Token)match(input,75,FOLLOW_75_in_objectDecl2595); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_75.add(string_literal339);


            pushFollow(FOLLOW_typedNameList_in_objectDecl2597);
            typedNameList340=typedNameList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_typedNameList.add(typedNameList340.getTree());

            char_literal341=(Token)match(input,57,FOLLOW_57_in_objectDecl2599); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal341);


            // AST REWRITE
            // elements: typedNameList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 419:2: -> ^( OBJECTS typedNameList )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:419:5: ^( OBJECTS typedNameList )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OBJECTS, "OBJECTS")
                , root_1);

                adaptor.addChild(root_1, stream_typedNameList.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 63, objectDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "objectDecl"


    public static class init_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "init"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:422:1: init : '(' ':init' ( initEl )* ')' -> ^( INIT ( initEl )* ) ;
    public final PddlParser.init_return init() throws RecognitionException {
        PddlParser.init_return retval = new PddlParser.init_return();
        retval.start = input.LT(1);

        int init_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal342=null;
        Token string_literal343=null;
        Token char_literal345=null;
        PddlParser.initEl_return initEl344 =null;


        Object char_literal342_tree=null;
        Object string_literal343_tree=null;
        Object char_literal345_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleSubtreeStream stream_initEl=new RewriteRuleSubtreeStream(adaptor,"rule initEl");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:423:2: ( '(' ':init' ( initEl )* ')' -> ^( INIT ( initEl )* ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:423:4: '(' ':init' ( initEl )* ')'
            {
            char_literal342=(Token)match(input,55,FOLLOW_55_in_init2619); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal342);


            string_literal343=(Token)match(input,73,FOLLOW_73_in_init2621); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_73.add(string_literal343);


            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:423:16: ( initEl )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==55) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:423:16: initEl
            	    {
            	    pushFollow(FOLLOW_initEl_in_init2623);
            	    initEl344=initEl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_initEl.add(initEl344.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            char_literal345=(Token)match(input,57,FOLLOW_57_in_init2626); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal345);


            // AST REWRITE
            // elements: initEl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 424:2: -> ^( INIT ( initEl )* )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:424:5: ^( INIT ( initEl )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(INIT, "INIT")
                , root_1);

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:424:12: ( initEl )*
                while ( stream_initEl.hasNext() ) {
                    adaptor.addChild(root_1, stream_initEl.nextTree());

                }
                stream_initEl.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 64, init_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "init"


    public static class initEl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "initEl"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:427:1: initEl : ( nameLiteral | '(' '=' fHead NUMBER ')' -> ^( INIT_EQ fHead NUMBER ) | '(' '<remove_this_if_you_know_what_you_are_doing>at' NUMBER nameLiteral ')' -> ^( INIT_AT NUMBER nameLiteral ) );
    public final PddlParser.initEl_return initEl() throws RecognitionException {
        PddlParser.initEl_return retval = new PddlParser.initEl_return();
        retval.start = input.LT(1);

        int initEl_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal347=null;
        Token char_literal348=null;
        Token NUMBER350=null;
        Token char_literal351=null;
        Token char_literal352=null;
        Token string_literal353=null;
        Token NUMBER354=null;
        Token char_literal356=null;
        PddlParser.nameLiteral_return nameLiteral346 =null;

        PddlParser.fHead_return fHead349 =null;

        PddlParser.nameLiteral_return nameLiteral355 =null;


        Object char_literal347_tree=null;
        Object char_literal348_tree=null;
        Object NUMBER350_tree=null;
        Object char_literal351_tree=null;
        Object char_literal352_tree=null;
        Object string_literal353_tree=null;
        Object NUMBER354_tree=null;
        Object char_literal356_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_NUMBER=new RewriteRuleTokenStream(adaptor,"token NUMBER");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_fHead=new RewriteRuleSubtreeStream(adaptor,"rule fHead");
        RewriteRuleSubtreeStream stream_nameLiteral=new RewriteRuleSubtreeStream(adaptor,"rule nameLiteral");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:428:2: ( nameLiteral | '(' '=' fHead NUMBER ')' -> ^( INIT_EQ fHead NUMBER ) | '(' '<remove_this_if_you_know_what_you_are_doing>at' NUMBER nameLiteral ')' -> ^( INIT_AT NUMBER nameLiteral ) )
            int alt68=3;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==55) ) {
                switch ( input.LA(2) ) {
                case NAME:
                case 108:
                    {
                    alt68=1;
                    }
                    break;
                case 85:
                    {
                    alt68=2;
                    }
                    break;
                case 83:
                    {
                    alt68=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 68, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;

            }
            switch (alt68) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:428:4: nameLiteral
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_nameLiteral_in_initEl2647);
                    nameLiteral346=nameLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, nameLiteral346.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:429:4: '(' '=' fHead NUMBER ')'
                    {
                    char_literal347=(Token)match(input,55,FOLLOW_55_in_initEl2652); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal347);


                    char_literal348=(Token)match(input,85,FOLLOW_85_in_initEl2654); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_85.add(char_literal348);


                    pushFollow(FOLLOW_fHead_in_initEl2656);
                    fHead349=fHead();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fHead.add(fHead349.getTree());

                    NUMBER350=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_initEl2658); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_NUMBER.add(NUMBER350);


                    char_literal351=(Token)match(input,57,FOLLOW_57_in_initEl2660); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal351);


                    // AST REWRITE
                    // elements: NUMBER, fHead
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 429:37: -> ^( INIT_EQ fHead NUMBER )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:429:40: ^( INIT_EQ fHead NUMBER )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(INIT_EQ, "INIT_EQ")
                        , root_1);

                        adaptor.addChild(root_1, stream_fHead.nextTree());

                        adaptor.addChild(root_1, 
                        stream_NUMBER.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:430:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' NUMBER nameLiteral ')'
                    {
                    char_literal352=(Token)match(input,55,FOLLOW_55_in_initEl2683); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal352);


                    string_literal353=(Token)match(input,83,FOLLOW_83_in_initEl2685); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_83.add(string_literal353);


                    NUMBER354=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_initEl2687); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_NUMBER.add(NUMBER354);


                    pushFollow(FOLLOW_nameLiteral_in_initEl2689);
                    nameLiteral355=nameLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_nameLiteral.add(nameLiteral355.getTree());

                    char_literal356=(Token)match(input,57,FOLLOW_57_in_initEl2691); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal356);


                    // AST REWRITE
                    // elements: NUMBER, nameLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 430:81: -> ^( INIT_AT NUMBER nameLiteral )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:430:84: ^( INIT_AT NUMBER nameLiteral )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(INIT_AT, "INIT_AT")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_NUMBER.nextNode()
                        );

                        adaptor.addChild(root_1, stream_nameLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 65, initEl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "initEl"


    public static class nameLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "nameLiteral"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:433:1: nameLiteral : ( atomicNameFormula | '(' 'not' atomicNameFormula ')' -> ^( NOT_PRED_INIT atomicNameFormula ) );
    public final PddlParser.nameLiteral_return nameLiteral() throws RecognitionException {
        PddlParser.nameLiteral_return retval = new PddlParser.nameLiteral_return();
        retval.start = input.LT(1);

        int nameLiteral_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal358=null;
        Token string_literal359=null;
        Token char_literal361=null;
        PddlParser.atomicNameFormula_return atomicNameFormula357 =null;

        PddlParser.atomicNameFormula_return atomicNameFormula360 =null;


        Object char_literal358_tree=null;
        Object string_literal359_tree=null;
        Object char_literal361_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_atomicNameFormula=new RewriteRuleSubtreeStream(adaptor,"rule atomicNameFormula");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:434:2: ( atomicNameFormula | '(' 'not' atomicNameFormula ')' -> ^( NOT_PRED_INIT atomicNameFormula ) )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==55) ) {
                int LA69_1 = input.LA(2);

                if ( (LA69_1==108) ) {
                    alt69=2;
                }
                else if ( (LA69_1==NAME) ) {
                    alt69=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 69, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;

            }
            switch (alt69) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:434:4: atomicNameFormula
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicNameFormula_in_nameLiteral2713);
                    atomicNameFormula357=atomicNameFormula();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicNameFormula357.getTree());

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:435:4: '(' 'not' atomicNameFormula ')'
                    {
                    char_literal358=(Token)match(input,55,FOLLOW_55_in_nameLiteral2718); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal358);


                    string_literal359=(Token)match(input,108,FOLLOW_108_in_nameLiteral2720); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_108.add(string_literal359);


                    pushFollow(FOLLOW_atomicNameFormula_in_nameLiteral2722);
                    atomicNameFormula360=atomicNameFormula();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_atomicNameFormula.add(atomicNameFormula360.getTree());

                    char_literal361=(Token)match(input,57,FOLLOW_57_in_nameLiteral2724); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal361);


                    // AST REWRITE
                    // elements: atomicNameFormula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 435:36: -> ^( NOT_PRED_INIT atomicNameFormula )
                    {
                        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:435:39: ^( NOT_PRED_INIT atomicNameFormula )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NOT_PRED_INIT, "NOT_PRED_INIT")
                        , root_1);

                        adaptor.addChild(root_1, stream_atomicNameFormula.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 66, nameLiteral_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "nameLiteral"


    public static class atomicNameFormula_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "atomicNameFormula"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:438:1: atomicNameFormula : '(' predicate ( NAME )* ')' -> ^( PRED_INST predicate ( NAME )* ) ;
    public final PddlParser.atomicNameFormula_return atomicNameFormula() throws RecognitionException {
        PddlParser.atomicNameFormula_return retval = new PddlParser.atomicNameFormula_return();
        retval.start = input.LT(1);

        int atomicNameFormula_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal362=null;
        Token NAME364=null;
        Token char_literal365=null;
        PddlParser.predicate_return predicate363 =null;


        Object char_literal362_tree=null;
        Object NAME364_tree=null;
        Object char_literal365_tree=null;
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:439:2: ( '(' predicate ( NAME )* ')' -> ^( PRED_INST predicate ( NAME )* ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:439:4: '(' predicate ( NAME )* ')'
            {
            char_literal362=(Token)match(input,55,FOLLOW_55_in_atomicNameFormula2743); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal362);


            pushFollow(FOLLOW_predicate_in_atomicNameFormula2745);
            predicate363=predicate();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_predicate.add(predicate363.getTree());

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:439:18: ( NAME )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==NAME) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:439:18: NAME
            	    {
            	    NAME364=(Token)match(input,NAME,FOLLOW_NAME_in_atomicNameFormula2747); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_NAME.add(NAME364);


            	    }
            	    break;

            	default :
            	    break loop70;
                }
            } while (true);


            char_literal365=(Token)match(input,57,FOLLOW_57_in_atomicNameFormula2750); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal365);


            // AST REWRITE
            // elements: NAME, predicate
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 439:28: -> ^( PRED_INST predicate ( NAME )* )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:439:31: ^( PRED_INST predicate ( NAME )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PRED_INST, "PRED_INST")
                , root_1);

                adaptor.addChild(root_1, stream_predicate.nextTree());

                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:439:53: ( NAME )*
                while ( stream_NAME.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_NAME.nextNode()
                    );

                }
                stream_NAME.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 67, atomicNameFormula_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "atomicNameFormula"


    public static class goal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "goal"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:446:1: goal : '(' ':goal' goalDesc ')' -> ^( GOAL goalDesc ) ;
    public final PddlParser.goal_return goal() throws RecognitionException {
        PddlParser.goal_return retval = new PddlParser.goal_return();
        retval.start = input.LT(1);

        int goal_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal366=null;
        Token string_literal367=null;
        Token char_literal369=null;
        PddlParser.goalDesc_return goalDesc368 =null;


        Object char_literal366_tree=null;
        Object string_literal367_tree=null;
        Object char_literal369_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleSubtreeStream stream_goalDesc=new RewriteRuleSubtreeStream(adaptor,"rule goalDesc");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:446:6: ( '(' ':goal' goalDesc ')' -> ^( GOAL goalDesc ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:446:8: '(' ':goal' goalDesc ')'
            {
            char_literal366=(Token)match(input,55,FOLLOW_55_in_goal2775); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal366);


            string_literal367=(Token)match(input,72,FOLLOW_72_in_goal2777); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_72.add(string_literal367);


            pushFollow(FOLLOW_goalDesc_in_goal2779);
            goalDesc368=goalDesc();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_goalDesc.add(goalDesc368.getTree());

            char_literal369=(Token)match(input,57,FOLLOW_57_in_goal2781); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal369);


            // AST REWRITE
            // elements: goalDesc
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 446:33: -> ^( GOAL goalDesc )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:446:36: ^( GOAL goalDesc )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(GOAL, "GOAL")
                , root_1);

                adaptor.addChild(root_1, stream_goalDesc.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 68, goal_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "goal"


    public static class probConstraints_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "probConstraints"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:448:1: probConstraints : '(' ':constraints' prefConGD ')' -> ^( PROBLEM_CONSTRAINT prefConGD ) ;
    public final PddlParser.probConstraints_return probConstraints() throws RecognitionException {
        PddlParser.probConstraints_return retval = new PddlParser.probConstraints_return();
        retval.start = input.LT(1);

        int probConstraints_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal370=null;
        Token string_literal371=null;
        Token char_literal373=null;
        PddlParser.prefConGD_return prefConGD372 =null;


        Object char_literal370_tree=null;
        Object string_literal371_tree=null;
        Object char_literal373_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_prefConGD=new RewriteRuleSubtreeStream(adaptor,"rule prefConGD");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:449:2: ( '(' ':constraints' prefConGD ')' -> ^( PROBLEM_CONSTRAINT prefConGD ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:449:4: '(' ':constraints' prefConGD ')'
            {
            char_literal370=(Token)match(input,55,FOLLOW_55_in_probConstraints2799); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal370);


            string_literal371=(Token)match(input,65,FOLLOW_65_in_probConstraints2801); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_65.add(string_literal371);


            pushFollow(FOLLOW_prefConGD_in_probConstraints2804);
            prefConGD372=prefConGD();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_prefConGD.add(prefConGD372.getTree());

            char_literal373=(Token)match(input,57,FOLLOW_57_in_probConstraints2806); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal373);


            // AST REWRITE
            // elements: prefConGD
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 450:4: -> ^( PROBLEM_CONSTRAINT prefConGD )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:450:7: ^( PROBLEM_CONSTRAINT prefConGD )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROBLEM_CONSTRAINT, "PROBLEM_CONSTRAINT")
                , root_1);

                adaptor.addChild(root_1, stream_prefConGD.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 69, probConstraints_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "probConstraints"


    public static class prefConGD_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prefConGD"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:453:1: prefConGD : ( '(' 'and' ( prefConGD )* ')' | '(' 'forall' '(' typedVariableList ')' prefConGD ')' | '(' 'preference' ( NAME )? conGD ')' | conGD );
    public final PddlParser.prefConGD_return prefConGD() throws RecognitionException {
        PddlParser.prefConGD_return retval = new PddlParser.prefConGD_return();
        retval.start = input.LT(1);

        int prefConGD_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal374=null;
        Token string_literal375=null;
        Token char_literal377=null;
        Token char_literal378=null;
        Token string_literal379=null;
        Token char_literal380=null;
        Token char_literal382=null;
        Token char_literal384=null;
        Token char_literal385=null;
        Token string_literal386=null;
        Token NAME387=null;
        Token char_literal389=null;
        PddlParser.prefConGD_return prefConGD376 =null;

        PddlParser.typedVariableList_return typedVariableList381 =null;

        PddlParser.prefConGD_return prefConGD383 =null;

        PddlParser.conGD_return conGD388 =null;

        PddlParser.conGD_return conGD390 =null;


        Object char_literal374_tree=null;
        Object string_literal375_tree=null;
        Object char_literal377_tree=null;
        Object char_literal378_tree=null;
        Object string_literal379_tree=null;
        Object char_literal380_tree=null;
        Object char_literal382_tree=null;
        Object char_literal384_tree=null;
        Object char_literal385_tree=null;
        Object string_literal386_tree=null;
        Object NAME387_tree=null;
        Object char_literal389_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:2: ( '(' 'and' ( prefConGD )* ')' | '(' 'forall' '(' typedVariableList ')' prefConGD ')' | '(' 'preference' ( NAME )? conGD ')' | conGD )
            int alt73=4;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==55) ) {
                int LA73_1 = input.LA(2);

                if ( (synpred107_Pddl()) ) {
                    alt73=1;
                }
                else if ( (synpred108_Pddl()) ) {
                    alt73=2;
                }
                else if ( (synpred110_Pddl()) ) {
                    alt73=3;
                }
                else if ( (true) ) {
                    alt73=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 73, 1, input);

                    throw nvae;

                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;

            }
            switch (alt73) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:4: '(' 'and' ( prefConGD )* ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal374=(Token)match(input,55,FOLLOW_55_in_prefConGD2828); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal374_tree = 
                    (Object)adaptor.create(char_literal374)
                    ;
                    adaptor.addChild(root_0, char_literal374_tree);
                    }

                    string_literal375=(Token)match(input,92,FOLLOW_92_in_prefConGD2830); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal375_tree = 
                    (Object)adaptor.create(string_literal375)
                    ;
                    adaptor.addChild(root_0, string_literal375_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:14: ( prefConGD )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==55) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:14: prefConGD
                    	    {
                    	    pushFollow(FOLLOW_prefConGD_in_prefConGD2832);
                    	    prefConGD376=prefConGD();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefConGD376.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);


                    char_literal377=(Token)match(input,57,FOLLOW_57_in_prefConGD2835); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal377_tree = 
                    (Object)adaptor.create(char_literal377)
                    ;
                    adaptor.addChild(root_0, char_literal377_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:455:4: '(' 'forall' '(' typedVariableList ')' prefConGD ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal378=(Token)match(input,55,FOLLOW_55_in_prefConGD2840); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal378_tree = 
                    (Object)adaptor.create(char_literal378)
                    ;
                    adaptor.addChild(root_0, char_literal378_tree);
                    }

                    string_literal379=(Token)match(input,100,FOLLOW_100_in_prefConGD2842); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal379_tree = 
                    (Object)adaptor.create(string_literal379)
                    ;
                    adaptor.addChild(root_0, string_literal379_tree);
                    }

                    char_literal380=(Token)match(input,55,FOLLOW_55_in_prefConGD2844); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal380_tree = 
                    (Object)adaptor.create(char_literal380)
                    ;
                    adaptor.addChild(root_0, char_literal380_tree);
                    }

                    pushFollow(FOLLOW_typedVariableList_in_prefConGD2846);
                    typedVariableList381=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList381.getTree());

                    char_literal382=(Token)match(input,57,FOLLOW_57_in_prefConGD2848); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal382_tree = 
                    (Object)adaptor.create(char_literal382)
                    ;
                    adaptor.addChild(root_0, char_literal382_tree);
                    }

                    pushFollow(FOLLOW_prefConGD_in_prefConGD2850);
                    prefConGD383=prefConGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefConGD383.getTree());

                    char_literal384=(Token)match(input,57,FOLLOW_57_in_prefConGD2852); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal384_tree = 
                    (Object)adaptor.create(char_literal384)
                    ;
                    adaptor.addChild(root_0, char_literal384_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:4: '(' 'preference' ( NAME )? conGD ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal385=(Token)match(input,55,FOLLOW_55_in_prefConGD2857); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal385_tree = 
                    (Object)adaptor.create(char_literal385)
                    ;
                    adaptor.addChild(root_0, char_literal385_tree);
                    }

                    string_literal386=(Token)match(input,112,FOLLOW_112_in_prefConGD2859); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal386_tree = 
                    (Object)adaptor.create(string_literal386)
                    ;
                    adaptor.addChild(root_0, string_literal386_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:21: ( NAME )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==NAME) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:21: NAME
                            {
                            NAME387=(Token)match(input,NAME,FOLLOW_NAME_in_prefConGD2861); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NAME387_tree = 
                            (Object)adaptor.create(NAME387)
                            ;
                            adaptor.addChild(root_0, NAME387_tree);
                            }

                            }
                            break;

                    }


                    pushFollow(FOLLOW_conGD_in_prefConGD2864);
                    conGD388=conGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, conGD388.getTree());

                    char_literal389=(Token)match(input,57,FOLLOW_57_in_prefConGD2866); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal389_tree = 
                    (Object)adaptor.create(char_literal389)
                    ;
                    adaptor.addChild(root_0, char_literal389_tree);
                    }

                    }
                    break;
                case 4 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:457:4: conGD
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_conGD_in_prefConGD2871);
                    conGD390=conGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, conGD390.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 70, prefConGD_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "prefConGD"


    public static class metricSpec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "metricSpec"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:460:1: metricSpec : '(' ':metric' optimization metricFExp ')' -> ^( PROBLEM_METRIC optimization metricFExp ) ;
    public final PddlParser.metricSpec_return metricSpec() throws RecognitionException {
        PddlParser.metricSpec_return retval = new PddlParser.metricSpec_return();
        retval.start = input.LT(1);

        int metricSpec_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal391=null;
        Token string_literal392=null;
        Token char_literal395=null;
        PddlParser.optimization_return optimization393 =null;

        PddlParser.metricFExp_return metricFExp394 =null;


        Object char_literal391_tree=null;
        Object string_literal392_tree=null;
        Object char_literal395_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleSubtreeStream stream_optimization=new RewriteRuleSubtreeStream(adaptor,"rule optimization");
        RewriteRuleSubtreeStream stream_metricFExp=new RewriteRuleSubtreeStream(adaptor,"rule metricFExp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:461:2: ( '(' ':metric' optimization metricFExp ')' -> ^( PROBLEM_METRIC optimization metricFExp ) )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:461:4: '(' ':metric' optimization metricFExp ')'
            {
            char_literal391=(Token)match(input,55,FOLLOW_55_in_metricSpec2882); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(char_literal391);


            string_literal392=(Token)match(input,74,FOLLOW_74_in_metricSpec2884); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_74.add(string_literal392);


            pushFollow(FOLLOW_optimization_in_metricSpec2886);
            optimization393=optimization();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_optimization.add(optimization393.getTree());

            pushFollow(FOLLOW_metricFExp_in_metricSpec2888);
            metricFExp394=metricFExp();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_metricFExp.add(metricFExp394.getTree());

            char_literal395=(Token)match(input,57,FOLLOW_57_in_metricSpec2890); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal395);


            // AST REWRITE
            // elements: optimization, metricFExp
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 462:4: -> ^( PROBLEM_METRIC optimization metricFExp )
            {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:462:7: ^( PROBLEM_METRIC optimization metricFExp )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROBLEM_METRIC, "PROBLEM_METRIC")
                , root_1);

                adaptor.addChild(root_1, stream_optimization.nextTree());

                adaptor.addChild(root_1, stream_metricFExp.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 71, metricSpec_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "metricSpec"


    public static class optimization_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optimization"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:465:1: optimization : ( 'minimize' | 'maximize' );
    public final PddlParser.optimization_return optimization() throws RecognitionException {
        PddlParser.optimization_return retval = new PddlParser.optimization_return();
        retval.start = input.LT(1);

        int optimization_StartIndex = input.index();

        Object root_0 = null;

        Token set396=null;

        Object set396_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:465:14: ( 'minimize' | 'maximize' )
            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:
            {
            root_0 = (Object)adaptor.nil();


            set396=(Token)input.LT(1);

            if ( (input.LA(1) >= 106 && input.LA(1) <= 107) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set396)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 72, optimization_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optimization"


    public static class metricFExp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "metricFExp"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:467:1: metricFExp : ( '(' binaryOp metricFExp metricFExp ')' | '(' ( '*' | '/' ) metricFExp ( metricFExp )+ ')' | '(' '-' metricFExp ')' | NUMBER | '(' functionSymbol ( NAME )* ')' | functionSymbol | 'total-time' | '(' 'is-violated' NAME ')' );
    public final PddlParser.metricFExp_return metricFExp() throws RecognitionException {
        PddlParser.metricFExp_return retval = new PddlParser.metricFExp_return();
        retval.start = input.LT(1);

        int metricFExp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal397=null;
        Token char_literal401=null;
        Token char_literal402=null;
        Token set403=null;
        Token char_literal406=null;
        Token char_literal407=null;
        Token char_literal408=null;
        Token char_literal410=null;
        Token NUMBER411=null;
        Token char_literal412=null;
        Token NAME414=null;
        Token char_literal415=null;
        Token string_literal417=null;
        Token char_literal418=null;
        Token string_literal419=null;
        Token NAME420=null;
        Token char_literal421=null;
        PddlParser.binaryOp_return binaryOp398 =null;

        PddlParser.metricFExp_return metricFExp399 =null;

        PddlParser.metricFExp_return metricFExp400 =null;

        PddlParser.metricFExp_return metricFExp404 =null;

        PddlParser.metricFExp_return metricFExp405 =null;

        PddlParser.metricFExp_return metricFExp409 =null;

        PddlParser.functionSymbol_return functionSymbol413 =null;

        PddlParser.functionSymbol_return functionSymbol416 =null;


        Object char_literal397_tree=null;
        Object char_literal401_tree=null;
        Object char_literal402_tree=null;
        Object set403_tree=null;
        Object char_literal406_tree=null;
        Object char_literal407_tree=null;
        Object char_literal408_tree=null;
        Object char_literal410_tree=null;
        Object NUMBER411_tree=null;
        Object char_literal412_tree=null;
        Object NAME414_tree=null;
        Object char_literal415_tree=null;
        Object string_literal417_tree=null;
        Object char_literal418_tree=null;
        Object string_literal419_tree=null;
        Object NAME420_tree=null;
        Object char_literal421_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:468:2: ( '(' binaryOp metricFExp metricFExp ')' | '(' ( '*' | '/' ) metricFExp ( metricFExp )+ ')' | '(' '-' metricFExp ')' | NUMBER | '(' functionSymbol ( NAME )* ')' | functionSymbol | 'total-time' | '(' 'is-violated' NAME ')' )
            int alt76=8;
            switch ( input.LA(1) ) {
            case 55:
                {
                int LA76_1 = input.LA(2);

                if ( (synpred112_Pddl()) ) {
                    alt76=1;
                }
                else if ( (synpred115_Pddl()) ) {
                    alt76=2;
                }
                else if ( (synpred116_Pddl()) ) {
                    alt76=3;
                }
                else if ( (synpred119_Pddl()) ) {
                    alt76=5;
                }
                else if ( (true) ) {
                    alt76=8;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 76, 1, input);

                    throw nvae;

                }
                }
                break;
            case NUMBER:
                {
                alt76=4;
                }
                break;
            case NAME:
                {
                alt76=6;
                }
                break;
            case 120:
                {
                alt76=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;

            }

            switch (alt76) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:468:4: '(' binaryOp metricFExp metricFExp ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal397=(Token)match(input,55,FOLLOW_55_in_metricFExp2927); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal397_tree = 
                    (Object)adaptor.create(char_literal397)
                    ;
                    adaptor.addChild(root_0, char_literal397_tree);
                    }

                    pushFollow(FOLLOW_binaryOp_in_metricFExp2929);
                    binaryOp398=binaryOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, binaryOp398.getTree());

                    pushFollow(FOLLOW_metricFExp_in_metricFExp2931);
                    metricFExp399=metricFExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, metricFExp399.getTree());

                    pushFollow(FOLLOW_metricFExp_in_metricFExp2933);
                    metricFExp400=metricFExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, metricFExp400.getTree());

                    char_literal401=(Token)match(input,57,FOLLOW_57_in_metricFExp2935); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal401_tree = 
                    (Object)adaptor.create(char_literal401)
                    ;
                    adaptor.addChild(root_0, char_literal401_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:4: '(' ( '*' | '/' ) metricFExp ( metricFExp )+ ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal402=(Token)match(input,55,FOLLOW_55_in_metricFExp2940); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal402_tree = 
                    (Object)adaptor.create(char_literal402)
                    ;
                    adaptor.addChild(root_0, char_literal402_tree);
                    }

                    set403=(Token)input.LT(1);

                    if ( input.LA(1)==58||input.LA(1)==61 ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (Object)adaptor.create(set403)
                        );
                        state.errorRecovery=false;
                        state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    pushFollow(FOLLOW_metricFExp_in_metricFExp2948);
                    metricFExp404=metricFExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, metricFExp404.getTree());

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:29: ( metricFExp )+
                    int cnt74=0;
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==NAME||LA74_0==NUMBER||LA74_0==55||LA74_0==120) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:29: metricFExp
                    	    {
                    	    pushFollow(FOLLOW_metricFExp_in_metricFExp2950);
                    	    metricFExp405=metricFExp();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, metricFExp405.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt74 >= 1 ) break loop74;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(74, input);
                                throw eee;
                        }
                        cnt74++;
                    } while (true);


                    char_literal406=(Token)match(input,57,FOLLOW_57_in_metricFExp2953); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal406_tree = 
                    (Object)adaptor.create(char_literal406)
                    ;
                    adaptor.addChild(root_0, char_literal406_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:470:4: '(' '-' metricFExp ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal407=(Token)match(input,55,FOLLOW_55_in_metricFExp2958); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal407_tree = 
                    (Object)adaptor.create(char_literal407)
                    ;
                    adaptor.addChild(root_0, char_literal407_tree);
                    }

                    char_literal408=(Token)match(input,60,FOLLOW_60_in_metricFExp2960); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal408_tree = 
                    (Object)adaptor.create(char_literal408)
                    ;
                    adaptor.addChild(root_0, char_literal408_tree);
                    }

                    pushFollow(FOLLOW_metricFExp_in_metricFExp2962);
                    metricFExp409=metricFExp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, metricFExp409.getTree());

                    char_literal410=(Token)match(input,57,FOLLOW_57_in_metricFExp2964); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal410_tree = 
                    (Object)adaptor.create(char_literal410)
                    ;
                    adaptor.addChild(root_0, char_literal410_tree);
                    }

                    }
                    break;
                case 4 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:471:4: NUMBER
                    {
                    root_0 = (Object)adaptor.nil();


                    NUMBER411=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_metricFExp2969); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER411_tree = 
                    (Object)adaptor.create(NUMBER411)
                    ;
                    adaptor.addChild(root_0, NUMBER411_tree);
                    }

                    }
                    break;
                case 5 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:4: '(' functionSymbol ( NAME )* ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal412=(Token)match(input,55,FOLLOW_55_in_metricFExp2974); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal412_tree = 
                    (Object)adaptor.create(char_literal412)
                    ;
                    adaptor.addChild(root_0, char_literal412_tree);
                    }

                    pushFollow(FOLLOW_functionSymbol_in_metricFExp2976);
                    functionSymbol413=functionSymbol();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionSymbol413.getTree());

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:23: ( NAME )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==NAME) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:23: NAME
                    	    {
                    	    NAME414=(Token)match(input,NAME,FOLLOW_NAME_in_metricFExp2978); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NAME414_tree = 
                    	    (Object)adaptor.create(NAME414)
                    	    ;
                    	    adaptor.addChild(root_0, NAME414_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);


                    char_literal415=(Token)match(input,57,FOLLOW_57_in_metricFExp2981); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal415_tree = 
                    (Object)adaptor.create(char_literal415)
                    ;
                    adaptor.addChild(root_0, char_literal415_tree);
                    }

                    }
                    break;
                case 6 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:473:4: functionSymbol
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_functionSymbol_in_metricFExp2986);
                    functionSymbol416=functionSymbol();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionSymbol416.getTree());

                    }
                    break;
                case 7 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:474:7: 'total-time'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal417=(Token)match(input,120,FOLLOW_120_in_metricFExp2994); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal417_tree = 
                    (Object)adaptor.create(string_literal417)
                    ;
                    adaptor.addChild(root_0, string_literal417_tree);
                    }

                    }
                    break;
                case 8 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:475:4: '(' 'is-violated' NAME ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal418=(Token)match(input,55,FOLLOW_55_in_metricFExp2999); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal418_tree = 
                    (Object)adaptor.create(char_literal418)
                    ;
                    adaptor.addChild(root_0, char_literal418_tree);
                    }

                    string_literal419=(Token)match(input,105,FOLLOW_105_in_metricFExp3001); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal419_tree = 
                    (Object)adaptor.create(string_literal419)
                    ;
                    adaptor.addChild(root_0, string_literal419_tree);
                    }

                    NAME420=(Token)match(input,NAME,FOLLOW_NAME_in_metricFExp3003); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME420_tree = 
                    (Object)adaptor.create(NAME420)
                    ;
                    adaptor.addChild(root_0, NAME420_tree);
                    }

                    char_literal421=(Token)match(input,57,FOLLOW_57_in_metricFExp3005); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal421_tree = 
                    (Object)adaptor.create(char_literal421)
                    ;
                    adaptor.addChild(root_0, char_literal421_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 73, metricFExp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "metricFExp"


    public static class conGD_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "conGD"
    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:480:1: conGD : ( '(' 'and' ( conGD )* ')' | '(' 'forall' '(' typedVariableList ')' conGD ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at' 'end' goalDesc ')' | '(' 'always' goalDesc ')' | '(' 'sometime' goalDesc ')' | '(' 'within' NUMBER goalDesc ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at-most-once' goalDesc ')' | '(' 'sometime-after' goalDesc goalDesc ')' | '(' 'sometime-before' goalDesc goalDesc ')' | '(' 'always-within' NUMBER goalDesc goalDesc ')' | '(' 'hold-during' NUMBER NUMBER goalDesc ')' | '(' 'hold-after' NUMBER goalDesc ')' );
    public final PddlParser.conGD_return conGD() throws RecognitionException {
        PddlParser.conGD_return retval = new PddlParser.conGD_return();
        retval.start = input.LT(1);

        int conGD_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal422=null;
        Token string_literal423=null;
        Token char_literal425=null;
        Token char_literal426=null;
        Token string_literal427=null;
        Token char_literal428=null;
        Token char_literal430=null;
        Token char_literal432=null;
        Token char_literal433=null;
        Token string_literal434=null;
        Token string_literal435=null;
        Token char_literal437=null;
        Token char_literal438=null;
        Token string_literal439=null;
        Token char_literal441=null;
        Token char_literal442=null;
        Token string_literal443=null;
        Token char_literal445=null;
        Token char_literal446=null;
        Token string_literal447=null;
        Token NUMBER448=null;
        Token char_literal450=null;
        Token char_literal451=null;
        Token string_literal452=null;
        Token char_literal454=null;
        Token char_literal455=null;
        Token string_literal456=null;
        Token char_literal459=null;
        Token char_literal460=null;
        Token string_literal461=null;
        Token char_literal464=null;
        Token char_literal465=null;
        Token string_literal466=null;
        Token NUMBER467=null;
        Token char_literal470=null;
        Token char_literal471=null;
        Token string_literal472=null;
        Token NUMBER473=null;
        Token NUMBER474=null;
        Token char_literal476=null;
        Token char_literal477=null;
        Token string_literal478=null;
        Token NUMBER479=null;
        Token char_literal481=null;
        PddlParser.conGD_return conGD424 =null;

        PddlParser.typedVariableList_return typedVariableList429 =null;

        PddlParser.conGD_return conGD431 =null;

        PddlParser.goalDesc_return goalDesc436 =null;

        PddlParser.goalDesc_return goalDesc440 =null;

        PddlParser.goalDesc_return goalDesc444 =null;

        PddlParser.goalDesc_return goalDesc449 =null;

        PddlParser.goalDesc_return goalDesc453 =null;

        PddlParser.goalDesc_return goalDesc457 =null;

        PddlParser.goalDesc_return goalDesc458 =null;

        PddlParser.goalDesc_return goalDesc462 =null;

        PddlParser.goalDesc_return goalDesc463 =null;

        PddlParser.goalDesc_return goalDesc468 =null;

        PddlParser.goalDesc_return goalDesc469 =null;

        PddlParser.goalDesc_return goalDesc475 =null;

        PddlParser.goalDesc_return goalDesc480 =null;


        Object char_literal422_tree=null;
        Object string_literal423_tree=null;
        Object char_literal425_tree=null;
        Object char_literal426_tree=null;
        Object string_literal427_tree=null;
        Object char_literal428_tree=null;
        Object char_literal430_tree=null;
        Object char_literal432_tree=null;
        Object char_literal433_tree=null;
        Object string_literal434_tree=null;
        Object string_literal435_tree=null;
        Object char_literal437_tree=null;
        Object char_literal438_tree=null;
        Object string_literal439_tree=null;
        Object char_literal441_tree=null;
        Object char_literal442_tree=null;
        Object string_literal443_tree=null;
        Object char_literal445_tree=null;
        Object char_literal446_tree=null;
        Object string_literal447_tree=null;
        Object NUMBER448_tree=null;
        Object char_literal450_tree=null;
        Object char_literal451_tree=null;
        Object string_literal452_tree=null;
        Object char_literal454_tree=null;
        Object char_literal455_tree=null;
        Object string_literal456_tree=null;
        Object char_literal459_tree=null;
        Object char_literal460_tree=null;
        Object string_literal461_tree=null;
        Object char_literal464_tree=null;
        Object char_literal465_tree=null;
        Object string_literal466_tree=null;
        Object NUMBER467_tree=null;
        Object char_literal470_tree=null;
        Object char_literal471_tree=null;
        Object string_literal472_tree=null;
        Object NUMBER473_tree=null;
        Object NUMBER474_tree=null;
        Object char_literal476_tree=null;
        Object char_literal477_tree=null;
        Object string_literal478_tree=null;
        Object NUMBER479_tree=null;
        Object char_literal481_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }

            // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:481:2: ( '(' 'and' ( conGD )* ')' | '(' 'forall' '(' typedVariableList ')' conGD ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at' 'end' goalDesc ')' | '(' 'always' goalDesc ')' | '(' 'sometime' goalDesc ')' | '(' 'within' NUMBER goalDesc ')' | '(' '<remove_this_if_you_know_what_you_are_doing>at-most-once' goalDesc ')' | '(' 'sometime-after' goalDesc goalDesc ')' | '(' 'sometime-before' goalDesc goalDesc ')' | '(' 'always-within' NUMBER goalDesc goalDesc ')' | '(' 'hold-during' NUMBER NUMBER goalDesc ')' | '(' 'hold-after' NUMBER goalDesc ')' )
            int alt78=12;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==55) ) {
                switch ( input.LA(2) ) {
                case 92:
                    {
                    alt78=1;
                    }
                    break;
                case 100:
                    {
                    alt78=2;
                    }
                    break;
                case 83:
                    {
                    alt78=3;
                    }
                    break;
                case 90:
                    {
                    alt78=4;
                    }
                    break;
                case 116:
                    {
                    alt78=5;
                    }
                    break;
                case 122:
                    {
                    alt78=6;
                    }
                    break;
                case 84:
                    {
                    alt78=7;
                    }
                    break;
                case 117:
                    {
                    alt78=8;
                    }
                    break;
                case 118:
                    {
                    alt78=9;
                    }
                    break;
                case 91:
                    {
                    alt78=10;
                    }
                    break;
                case 102:
                    {
                    alt78=11;
                    }
                    break;
                case 101:
                    {
                    alt78=12;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 78, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;

            }
            switch (alt78) {
                case 1 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:481:4: '(' 'and' ( conGD )* ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal422=(Token)match(input,55,FOLLOW_55_in_conGD3019); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal422_tree = 
                    (Object)adaptor.create(char_literal422)
                    ;
                    adaptor.addChild(root_0, char_literal422_tree);
                    }

                    string_literal423=(Token)match(input,92,FOLLOW_92_in_conGD3021); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal423_tree = 
                    (Object)adaptor.create(string_literal423)
                    ;
                    adaptor.addChild(root_0, string_literal423_tree);
                    }

                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:481:14: ( conGD )*
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==55) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:481:14: conGD
                    	    {
                    	    pushFollow(FOLLOW_conGD_in_conGD3023);
                    	    conGD424=conGD();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conGD424.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop77;
                        }
                    } while (true);


                    char_literal425=(Token)match(input,57,FOLLOW_57_in_conGD3026); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal425_tree = 
                    (Object)adaptor.create(char_literal425)
                    ;
                    adaptor.addChild(root_0, char_literal425_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:482:4: '(' 'forall' '(' typedVariableList ')' conGD ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal426=(Token)match(input,55,FOLLOW_55_in_conGD3031); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal426_tree = 
                    (Object)adaptor.create(char_literal426)
                    ;
                    adaptor.addChild(root_0, char_literal426_tree);
                    }

                    string_literal427=(Token)match(input,100,FOLLOW_100_in_conGD3033); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal427_tree = 
                    (Object)adaptor.create(string_literal427)
                    ;
                    adaptor.addChild(root_0, string_literal427_tree);
                    }

                    char_literal428=(Token)match(input,55,FOLLOW_55_in_conGD3035); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal428_tree = 
                    (Object)adaptor.create(char_literal428)
                    ;
                    adaptor.addChild(root_0, char_literal428_tree);
                    }

                    pushFollow(FOLLOW_typedVariableList_in_conGD3037);
                    typedVariableList429=typedVariableList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, typedVariableList429.getTree());

                    char_literal430=(Token)match(input,57,FOLLOW_57_in_conGD3039); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal430_tree = 
                    (Object)adaptor.create(char_literal430)
                    ;
                    adaptor.addChild(root_0, char_literal430_tree);
                    }

                    pushFollow(FOLLOW_conGD_in_conGD3041);
                    conGD431=conGD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, conGD431.getTree());

                    char_literal432=(Token)match(input,57,FOLLOW_57_in_conGD3043); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal432_tree = 
                    (Object)adaptor.create(char_literal432)
                    ;
                    adaptor.addChild(root_0, char_literal432_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:483:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' 'end' goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal433=(Token)match(input,55,FOLLOW_55_in_conGD3048); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal433_tree = 
                    (Object)adaptor.create(char_literal433)
                    ;
                    adaptor.addChild(root_0, char_literal433_tree);
                    }

                    string_literal434=(Token)match(input,83,FOLLOW_83_in_conGD3050); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal434_tree = 
                    (Object)adaptor.create(string_literal434)
                    ;
                    adaptor.addChild(root_0, string_literal434_tree);
                    }

                    string_literal435=(Token)match(input,98,FOLLOW_98_in_conGD3052); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal435_tree = 
                    (Object)adaptor.create(string_literal435)
                    ;
                    adaptor.addChild(root_0, string_literal435_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3054);
                    goalDesc436=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc436.getTree());

                    char_literal437=(Token)match(input,57,FOLLOW_57_in_conGD3056); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal437_tree = 
                    (Object)adaptor.create(char_literal437)
                    ;
                    adaptor.addChild(root_0, char_literal437_tree);
                    }

                    }
                    break;
                case 4 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:484:7: '(' 'always' goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal438=(Token)match(input,55,FOLLOW_55_in_conGD3064); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal438_tree = 
                    (Object)adaptor.create(char_literal438)
                    ;
                    adaptor.addChild(root_0, char_literal438_tree);
                    }

                    string_literal439=(Token)match(input,90,FOLLOW_90_in_conGD3066); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal439_tree = 
                    (Object)adaptor.create(string_literal439)
                    ;
                    adaptor.addChild(root_0, string_literal439_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3068);
                    goalDesc440=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc440.getTree());

                    char_literal441=(Token)match(input,57,FOLLOW_57_in_conGD3070); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal441_tree = 
                    (Object)adaptor.create(char_literal441)
                    ;
                    adaptor.addChild(root_0, char_literal441_tree);
                    }

                    }
                    break;
                case 5 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:485:4: '(' 'sometime' goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal442=(Token)match(input,55,FOLLOW_55_in_conGD3075); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal442_tree = 
                    (Object)adaptor.create(char_literal442)
                    ;
                    adaptor.addChild(root_0, char_literal442_tree);
                    }

                    string_literal443=(Token)match(input,116,FOLLOW_116_in_conGD3077); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal443_tree = 
                    (Object)adaptor.create(string_literal443)
                    ;
                    adaptor.addChild(root_0, string_literal443_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3079);
                    goalDesc444=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc444.getTree());

                    char_literal445=(Token)match(input,57,FOLLOW_57_in_conGD3081); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal445_tree = 
                    (Object)adaptor.create(char_literal445)
                    ;
                    adaptor.addChild(root_0, char_literal445_tree);
                    }

                    }
                    break;
                case 6 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:486:5: '(' 'within' NUMBER goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal446=(Token)match(input,55,FOLLOW_55_in_conGD3087); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal446_tree = 
                    (Object)adaptor.create(char_literal446)
                    ;
                    adaptor.addChild(root_0, char_literal446_tree);
                    }

                    string_literal447=(Token)match(input,122,FOLLOW_122_in_conGD3089); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal447_tree = 
                    (Object)adaptor.create(string_literal447)
                    ;
                    adaptor.addChild(root_0, string_literal447_tree);
                    }

                    NUMBER448=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_conGD3091); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER448_tree = 
                    (Object)adaptor.create(NUMBER448)
                    ;
                    adaptor.addChild(root_0, NUMBER448_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3093);
                    goalDesc449=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc449.getTree());

                    char_literal450=(Token)match(input,57,FOLLOW_57_in_conGD3095); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal450_tree = 
                    (Object)adaptor.create(char_literal450)
                    ;
                    adaptor.addChild(root_0, char_literal450_tree);
                    }

                    }
                    break;
                case 7 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:487:4: '(' '<remove_this_if_you_know_what_you_are_doing>at-most-once' goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal451=(Token)match(input,55,FOLLOW_55_in_conGD3100); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal451_tree = 
                    (Object)adaptor.create(char_literal451)
                    ;
                    adaptor.addChild(root_0, char_literal451_tree);
                    }

                    string_literal452=(Token)match(input,84,FOLLOW_84_in_conGD3102); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal452_tree = 
                    (Object)adaptor.create(string_literal452)
                    ;
                    adaptor.addChild(root_0, string_literal452_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3104);
                    goalDesc453=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc453.getTree());

                    char_literal454=(Token)match(input,57,FOLLOW_57_in_conGD3106); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal454_tree = 
                    (Object)adaptor.create(char_literal454)
                    ;
                    adaptor.addChild(root_0, char_literal454_tree);
                    }

                    }
                    break;
                case 8 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:488:4: '(' 'sometime-after' goalDesc goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal455=(Token)match(input,55,FOLLOW_55_in_conGD3111); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal455_tree = 
                    (Object)adaptor.create(char_literal455)
                    ;
                    adaptor.addChild(root_0, char_literal455_tree);
                    }

                    string_literal456=(Token)match(input,117,FOLLOW_117_in_conGD3113); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal456_tree = 
                    (Object)adaptor.create(string_literal456)
                    ;
                    adaptor.addChild(root_0, string_literal456_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3115);
                    goalDesc457=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc457.getTree());

                    pushFollow(FOLLOW_goalDesc_in_conGD3117);
                    goalDesc458=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc458.getTree());

                    char_literal459=(Token)match(input,57,FOLLOW_57_in_conGD3119); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal459_tree = 
                    (Object)adaptor.create(char_literal459)
                    ;
                    adaptor.addChild(root_0, char_literal459_tree);
                    }

                    }
                    break;
                case 9 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:489:4: '(' 'sometime-before' goalDesc goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal460=(Token)match(input,55,FOLLOW_55_in_conGD3124); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal460_tree = 
                    (Object)adaptor.create(char_literal460)
                    ;
                    adaptor.addChild(root_0, char_literal460_tree);
                    }

                    string_literal461=(Token)match(input,118,FOLLOW_118_in_conGD3126); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal461_tree = 
                    (Object)adaptor.create(string_literal461)
                    ;
                    adaptor.addChild(root_0, string_literal461_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3128);
                    goalDesc462=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc462.getTree());

                    pushFollow(FOLLOW_goalDesc_in_conGD3130);
                    goalDesc463=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc463.getTree());

                    char_literal464=(Token)match(input,57,FOLLOW_57_in_conGD3132); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal464_tree = 
                    (Object)adaptor.create(char_literal464)
                    ;
                    adaptor.addChild(root_0, char_literal464_tree);
                    }

                    }
                    break;
                case 10 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:490:4: '(' 'always-within' NUMBER goalDesc goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal465=(Token)match(input,55,FOLLOW_55_in_conGD3137); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal465_tree = 
                    (Object)adaptor.create(char_literal465)
                    ;
                    adaptor.addChild(root_0, char_literal465_tree);
                    }

                    string_literal466=(Token)match(input,91,FOLLOW_91_in_conGD3139); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal466_tree = 
                    (Object)adaptor.create(string_literal466)
                    ;
                    adaptor.addChild(root_0, string_literal466_tree);
                    }

                    NUMBER467=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_conGD3141); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER467_tree = 
                    (Object)adaptor.create(NUMBER467)
                    ;
                    adaptor.addChild(root_0, NUMBER467_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3143);
                    goalDesc468=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc468.getTree());

                    pushFollow(FOLLOW_goalDesc_in_conGD3145);
                    goalDesc469=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc469.getTree());

                    char_literal470=(Token)match(input,57,FOLLOW_57_in_conGD3147); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal470_tree = 
                    (Object)adaptor.create(char_literal470)
                    ;
                    adaptor.addChild(root_0, char_literal470_tree);
                    }

                    }
                    break;
                case 11 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:491:4: '(' 'hold-during' NUMBER NUMBER goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal471=(Token)match(input,55,FOLLOW_55_in_conGD3152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal471_tree = 
                    (Object)adaptor.create(char_literal471)
                    ;
                    adaptor.addChild(root_0, char_literal471_tree);
                    }

                    string_literal472=(Token)match(input,102,FOLLOW_102_in_conGD3154); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal472_tree = 
                    (Object)adaptor.create(string_literal472)
                    ;
                    adaptor.addChild(root_0, string_literal472_tree);
                    }

                    NUMBER473=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_conGD3156); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER473_tree = 
                    (Object)adaptor.create(NUMBER473)
                    ;
                    adaptor.addChild(root_0, NUMBER473_tree);
                    }

                    NUMBER474=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_conGD3158); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER474_tree = 
                    (Object)adaptor.create(NUMBER474)
                    ;
                    adaptor.addChild(root_0, NUMBER474_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3160);
                    goalDesc475=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc475.getTree());

                    char_literal476=(Token)match(input,57,FOLLOW_57_in_conGD3162); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal476_tree = 
                    (Object)adaptor.create(char_literal476)
                    ;
                    adaptor.addChild(root_0, char_literal476_tree);
                    }

                    }
                    break;
                case 12 :
                    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:492:4: '(' 'hold-after' NUMBER goalDesc ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal477=(Token)match(input,55,FOLLOW_55_in_conGD3167); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal477_tree = 
                    (Object)adaptor.create(char_literal477)
                    ;
                    adaptor.addChild(root_0, char_literal477_tree);
                    }

                    string_literal478=(Token)match(input,101,FOLLOW_101_in_conGD3169); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal478_tree = 
                    (Object)adaptor.create(string_literal478)
                    ;
                    adaptor.addChild(root_0, string_literal478_tree);
                    }

                    NUMBER479=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_conGD3171); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMBER479_tree = 
                    (Object)adaptor.create(NUMBER479)
                    ;
                    adaptor.addChild(root_0, NUMBER479_tree);
                    }

                    pushFollow(FOLLOW_goalDesc_in_conGD3173);
                    goalDesc480=goalDesc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, goalDesc480.getTree());

                    char_literal481=(Token)match(input,57,FOLLOW_57_in_conGD3175); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal481_tree = 
                    (Object)adaptor.create(char_literal481)
                    ;
                    adaptor.addChild(root_0, char_literal481_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 74, conGD_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "conGD"

    // $ANTLR start synpred20_Pddl
    public final void synpred20_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:171:18: ( typedVariableList )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:171:18: typedVariableList
        {
        pushFollow(FOLLOW_typedVariableList_in_synpred20_Pddl818);
        typedVariableList();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred20_Pddl

    // $ANTLR start synpred28_Pddl
    public final void synpred28_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:24: ( typedVariableList )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:213:24: typedVariableList
        {
        pushFollow(FOLLOW_typedVariableList_in_synpred28_Pddl1062);
        typedVariableList();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred28_Pddl

    // $ANTLR start synpred57_Pddl
    public final void synpred57_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:299:4: ( '(' binaryOp fExp fExp2 ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:299:4: '(' binaryOp fExp fExp2 ')'
        {
        match(input,55,FOLLOW_55_in_synpred57_Pddl1744); if (state.failed) return ;

        pushFollow(FOLLOW_binaryOp_in_synpred57_Pddl1746);
        binaryOp();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_fExp_in_synpred57_Pddl1748);
        fExp();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_fExp2_in_synpred57_Pddl1750);
        fExp2();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred57_Pddl1752); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred57_Pddl

    // $ANTLR start synpred58_Pddl
    public final void synpred58_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:300:4: ( '(' '-' fExp ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:300:4: '(' '-' fExp ')'
        {
        match(input,55,FOLLOW_55_in_synpred58_Pddl1769); if (state.failed) return ;

        match(input,60,FOLLOW_60_in_synpred58_Pddl1771); if (state.failed) return ;

        pushFollow(FOLLOW_fExp_in_synpred58_Pddl1773);
        fExp();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred58_Pddl1775); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred58_Pddl

    // $ANTLR start synpred86_Pddl
    public final void synpred86_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:364:12: ( NUMBER )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:364:12: NUMBER
        {
        match(input,NUMBER,FOLLOW_NUMBER_in_synpred86_Pddl2200); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred86_Pddl

    // $ANTLR start synpred88_Pddl
    public final void synpred88_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:4: ( '(' 'and' ( daEffect )* ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:4: '(' 'and' ( daEffect )* ')'
        {
        match(input,55,FOLLOW_55_in_synpred88_Pddl2214); if (state.failed) return ;

        match(input,92,FOLLOW_92_in_synpred88_Pddl2216); if (state.failed) return ;

        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:14: ( daEffect )*
        loop93:
        do {
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==55) ) {
                alt93=1;
            }


            switch (alt93) {
        	case 1 :
        	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:367:14: daEffect
        	    {
        	    pushFollow(FOLLOW_daEffect_in_synpred88_Pddl2218);
        	    daEffect();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop93;
            }
        } while (true);


        match(input,57,FOLLOW_57_in_synpred88_Pddl2221); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred88_Pddl

    // $ANTLR start synpred89_Pddl
    public final void synpred89_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:368:4: ( timedEffect )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:368:4: timedEffect
        {
        pushFollow(FOLLOW_timedEffect_in_synpred89_Pddl2226);
        timedEffect();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred89_Pddl

    // $ANTLR start synpred90_Pddl
    public final void synpred90_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:369:4: ( '(' 'forall' '(' typedVariableList ')' daEffect ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:369:4: '(' 'forall' '(' typedVariableList ')' daEffect ')'
        {
        match(input,55,FOLLOW_55_in_synpred90_Pddl2231); if (state.failed) return ;

        match(input,100,FOLLOW_100_in_synpred90_Pddl2233); if (state.failed) return ;

        match(input,55,FOLLOW_55_in_synpred90_Pddl2235); if (state.failed) return ;

        pushFollow(FOLLOW_typedVariableList_in_synpred90_Pddl2237);
        typedVariableList();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred90_Pddl2239); if (state.failed) return ;

        pushFollow(FOLLOW_daEffect_in_synpred90_Pddl2241);
        daEffect();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred90_Pddl2243); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred90_Pddl

    // $ANTLR start synpred91_Pddl
    public final void synpred91_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:370:4: ( '(' 'when' daGD timedEffect ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:370:4: '(' 'when' daGD timedEffect ')'
        {
        match(input,55,FOLLOW_55_in_synpred91_Pddl2248); if (state.failed) return ;

        match(input,121,FOLLOW_121_in_synpred91_Pddl2250); if (state.failed) return ;

        pushFollow(FOLLOW_daGD_in_synpred91_Pddl2252);
        daGD();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_timedEffect_in_synpred91_Pddl2254);
        timedEffect();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred91_Pddl2256); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred91_Pddl

    // $ANTLR start synpred92_Pddl
    public final void synpred92_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:375:4: ( '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier daEffect ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:375:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier daEffect ')'
        {
        match(input,55,FOLLOW_55_in_synpred92_Pddl2280); if (state.failed) return ;

        match(input,83,FOLLOW_83_in_synpred92_Pddl2282); if (state.failed) return ;

        pushFollow(FOLLOW_timeSpecifier_in_synpred92_Pddl2284);
        timeSpecifier();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_daEffect_in_synpred92_Pddl2286);
        daEffect();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred92_Pddl2288); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred92_Pddl

    // $ANTLR start synpred93_Pddl
    public final void synpred93_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:376:4: ( '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier fAssignDA ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:376:4: '(' '<remove_this_if_you_know_what_you_are_doing>at' timeSpecifier fAssignDA ')'
        {
        match(input,55,FOLLOW_55_in_synpred93_Pddl2298); if (state.failed) return ;

        match(input,83,FOLLOW_83_in_synpred93_Pddl2300); if (state.failed) return ;

        pushFollow(FOLLOW_timeSpecifier_in_synpred93_Pddl2302);
        timeSpecifier();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_fAssignDA_in_synpred93_Pddl2304);
        fAssignDA();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred93_Pddl2306); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred93_Pddl

    // $ANTLR start synpred94_Pddl
    public final void synpred94_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( ( binaryOp fExpDA fExpDA ) )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( binaryOp fExpDA fExpDA )
        {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( binaryOp fExpDA fExpDA )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:10: binaryOp fExpDA fExpDA
        {
        pushFollow(FOLLOW_binaryOp_in_synpred94_Pddl2362);
        binaryOp();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_fExpDA_in_synpred94_Pddl2364);
        fExpDA();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_fExpDA_in_synpred94_Pddl2366);
        fExpDA();

        state._fsp--;
        if (state.failed) return ;

        }


        }

    }
    // $ANTLR end synpred94_Pddl

    // $ANTLR start synpred95_Pddl
    public final void synpred95_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:4: ( '(' ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) ) ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:4: '(' ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) ) ')'
        {
        match(input,55,FOLLOW_55_in_synpred95_Pddl2358); if (state.failed) return ;

        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:8: ( ( binaryOp fExpDA fExpDA ) | ( '-' fExpDA ) )
        int alt94=2;
        int LA94_0 = input.LA(1);

        if ( (LA94_0==60) ) {
            int LA94_1 = input.LA(2);

            if ( (synpred94_Pddl()) ) {
                alt94=1;
            }
            else if ( (true) ) {
                alt94=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 1, input);

                throw nvae;

            }
        }
        else if ( ((LA94_0 >= 58 && LA94_0 <= 59)||LA94_0==61) ) {
            alt94=1;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 94, 0, input);

            throw nvae;

        }
        switch (alt94) {
            case 1 :
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( binaryOp fExpDA fExpDA )
                {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:9: ( binaryOp fExpDA fExpDA )
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:10: binaryOp fExpDA fExpDA
                {
                pushFollow(FOLLOW_binaryOp_in_synpred95_Pddl2362);
                binaryOp();

                state._fsp--;
                if (state.failed) return ;

                pushFollow(FOLLOW_fExpDA_in_synpred95_Pddl2364);
                fExpDA();

                state._fsp--;
                if (state.failed) return ;

                pushFollow(FOLLOW_fExpDA_in_synpred95_Pddl2366);
                fExpDA();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;
            case 2 :
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:36: ( '-' fExpDA )
                {
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:36: ( '-' fExpDA )
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:385:37: '-' fExpDA
                {
                match(input,60,FOLLOW_60_in_synpred95_Pddl2372); if (state.failed) return ;

                pushFollow(FOLLOW_fExpDA_in_synpred95_Pddl2374);
                fExpDA();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }


        match(input,57,FOLLOW_57_in_synpred95_Pddl2378); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred95_Pddl

    // $ANTLR start synpred107_Pddl
    public final void synpred107_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:4: ( '(' 'and' ( prefConGD )* ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:4: '(' 'and' ( prefConGD )* ')'
        {
        match(input,55,FOLLOW_55_in_synpred107_Pddl2828); if (state.failed) return ;

        match(input,92,FOLLOW_92_in_synpred107_Pddl2830); if (state.failed) return ;

        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:14: ( prefConGD )*
        loop95:
        do {
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==55) ) {
                alt95=1;
            }


            switch (alt95) {
        	case 1 :
        	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:454:14: prefConGD
        	    {
        	    pushFollow(FOLLOW_prefConGD_in_synpred107_Pddl2832);
        	    prefConGD();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop95;
            }
        } while (true);


        match(input,57,FOLLOW_57_in_synpred107_Pddl2835); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred107_Pddl

    // $ANTLR start synpred108_Pddl
    public final void synpred108_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:455:4: ( '(' 'forall' '(' typedVariableList ')' prefConGD ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:455:4: '(' 'forall' '(' typedVariableList ')' prefConGD ')'
        {
        match(input,55,FOLLOW_55_in_synpred108_Pddl2840); if (state.failed) return ;

        match(input,100,FOLLOW_100_in_synpred108_Pddl2842); if (state.failed) return ;

        match(input,55,FOLLOW_55_in_synpred108_Pddl2844); if (state.failed) return ;

        pushFollow(FOLLOW_typedVariableList_in_synpred108_Pddl2846);
        typedVariableList();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred108_Pddl2848); if (state.failed) return ;

        pushFollow(FOLLOW_prefConGD_in_synpred108_Pddl2850);
        prefConGD();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred108_Pddl2852); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred108_Pddl

    // $ANTLR start synpred110_Pddl
    public final void synpred110_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:4: ( '(' 'preference' ( NAME )? conGD ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:4: '(' 'preference' ( NAME )? conGD ')'
        {
        match(input,55,FOLLOW_55_in_synpred110_Pddl2857); if (state.failed) return ;

        match(input,112,FOLLOW_112_in_synpred110_Pddl2859); if (state.failed) return ;

        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:21: ( NAME )?
        int alt96=2;
        int LA96_0 = input.LA(1);

        if ( (LA96_0==NAME) ) {
            alt96=1;
        }
        switch (alt96) {
            case 1 :
                // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:456:21: NAME
                {
                match(input,NAME,FOLLOW_NAME_in_synpred110_Pddl2861); if (state.failed) return ;

                }
                break;

        }


        pushFollow(FOLLOW_conGD_in_synpred110_Pddl2864);
        conGD();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred110_Pddl2866); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred110_Pddl

    // $ANTLR start synpred112_Pddl
    public final void synpred112_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:468:4: ( '(' binaryOp metricFExp metricFExp ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:468:4: '(' binaryOp metricFExp metricFExp ')'
        {
        match(input,55,FOLLOW_55_in_synpred112_Pddl2927); if (state.failed) return ;

        pushFollow(FOLLOW_binaryOp_in_synpred112_Pddl2929);
        binaryOp();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_metricFExp_in_synpred112_Pddl2931);
        metricFExp();

        state._fsp--;
        if (state.failed) return ;

        pushFollow(FOLLOW_metricFExp_in_synpred112_Pddl2933);
        metricFExp();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred112_Pddl2935); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred112_Pddl

    // $ANTLR start synpred115_Pddl
    public final void synpred115_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:4: ( '(' ( '*' | '/' ) metricFExp ( metricFExp )+ ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:4: '(' ( '*' | '/' ) metricFExp ( metricFExp )+ ')'
        {
        match(input,55,FOLLOW_55_in_synpred115_Pddl2940); if (state.failed) return ;

        if ( input.LA(1)==58||input.LA(1)==61 ) {
            input.consume();
            state.errorRecovery=false;
            state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        pushFollow(FOLLOW_metricFExp_in_synpred115_Pddl2948);
        metricFExp();

        state._fsp--;
        if (state.failed) return ;

        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:29: ( metricFExp )+
        int cnt97=0;
        loop97:
        do {
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==NAME||LA97_0==NUMBER||LA97_0==55||LA97_0==120) ) {
                alt97=1;
            }


            switch (alt97) {
        	case 1 :
        	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:469:29: metricFExp
        	    {
        	    pushFollow(FOLLOW_metricFExp_in_synpred115_Pddl2950);
        	    metricFExp();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt97 >= 1 ) break loop97;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(97, input);
                    throw eee;
            }
            cnt97++;
        } while (true);


        match(input,57,FOLLOW_57_in_synpred115_Pddl2953); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred115_Pddl

    // $ANTLR start synpred116_Pddl
    public final void synpred116_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:470:4: ( '(' '-' metricFExp ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:470:4: '(' '-' metricFExp ')'
        {
        match(input,55,FOLLOW_55_in_synpred116_Pddl2958); if (state.failed) return ;

        match(input,60,FOLLOW_60_in_synpred116_Pddl2960); if (state.failed) return ;

        pushFollow(FOLLOW_metricFExp_in_synpred116_Pddl2962);
        metricFExp();

        state._fsp--;
        if (state.failed) return ;

        match(input,57,FOLLOW_57_in_synpred116_Pddl2964); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred116_Pddl

    // $ANTLR start synpred119_Pddl
    public final void synpred119_Pddl_fragment() throws RecognitionException {
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:4: ( '(' functionSymbol ( NAME )* ')' )
        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:4: '(' functionSymbol ( NAME )* ')'
        {
        match(input,55,FOLLOW_55_in_synpred119_Pddl2974); if (state.failed) return ;

        pushFollow(FOLLOW_functionSymbol_in_synpred119_Pddl2976);
        functionSymbol();

        state._fsp--;
        if (state.failed) return ;

        // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:23: ( NAME )*
        loop98:
        do {
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==NAME) ) {
                alt98=1;
            }


            switch (alt98) {
        	case 1 :
        	    // C:\\ROOT\\PROJECTS\\pddlParser\\ext\\Pddl.g:472:23: NAME
        	    {
        	    match(input,NAME,FOLLOW_NAME_in_synpred119_Pddl2978); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop98;
            }
        } while (true);


        match(input,57,FOLLOW_57_in_synpred119_Pddl2981); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred119_Pddl

    // Delegated rules

    public final boolean synpred94_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred94_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred88_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred88_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred95_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred95_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred115_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred115_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred89_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred89_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred86_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred86_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred93_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred93_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred107_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred107_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred119_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred119_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred112_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred112_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred28_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred28_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred108_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred108_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred91_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred91_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred92_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred92_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred110_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred110_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred90_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred90_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred116_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred116_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred57_Pddl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred57_Pddl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA13 dfa13 = new DFA13(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA24 dfa24 = new DFA24(this);
    protected DFA22 dfa22 = new DFA22(this);
    static final String DFA13_eotS =
        "\4\uffff";
    static final String DFA13_eofS =
        "\4\uffff";
    static final String DFA13_minS =
        "\2\37\2\uffff";
    static final String DFA13_maxS =
        "\1\71\1\74\2\uffff";
    static final String DFA13_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA13_specialS =
        "\4\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1\31\uffff\1\2",
            "\1\1\31\uffff\1\2\2\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "114:7: ( ( NAME )* | ( singleTypeNameList )+ ( NAME )* )";
        }
    }
    static final String DFA11_eotS =
        "\4\uffff";
    static final String DFA11_eofS =
        "\4\uffff";
    static final String DFA11_minS =
        "\2\37\2\uffff";
    static final String DFA11_maxS =
        "\1\71\1\74\2\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA11_specialS =
        "\4\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\1\31\uffff\1\2",
            "\1\1\31\uffff\1\2\2\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "()+ loopback of 114:16: ( singleTypeNameList )+";
        }
    }
    static final String DFA24_eotS =
        "\4\uffff";
    static final String DFA24_eofS =
        "\2\2\2\uffff";
    static final String DFA24_minS =
        "\2\64\2\uffff";
    static final String DFA24_maxS =
        "\1\71\1\74\2\uffff";
    static final String DFA24_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA24_specialS =
        "\4\uffff}>";
    static final String[] DFA24_transitionS = {
            "\1\1\2\uffff\1\2\1\uffff\1\2",
            "\1\1\2\uffff\1\2\1\uffff\1\2\2\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA24_eot = DFA.unpackEncodedString(DFA24_eotS);
    static final short[] DFA24_eof = DFA.unpackEncodedString(DFA24_eofS);
    static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars(DFA24_minS);
    static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars(DFA24_maxS);
    static final short[] DFA24_accept = DFA.unpackEncodedString(DFA24_acceptS);
    static final short[] DFA24_special = DFA.unpackEncodedString(DFA24_specialS);
    static final short[][] DFA24_transition;

    static {
        int numStates = DFA24_transitionS.length;
        DFA24_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = DFA24_eot;
            this.eof = DFA24_eof;
            this.min = DFA24_min;
            this.max = DFA24_max;
            this.accept = DFA24_accept;
            this.special = DFA24_special;
            this.transition = DFA24_transition;
        }
        public String getDescription() {
            return "179:7: ( ( VARIABLE )* | ( singleTypeVarList )+ ( VARIABLE )* )";
        }
    }
    static final String DFA22_eotS =
        "\4\uffff";
    static final String DFA22_eofS =
        "\2\2\2\uffff";
    static final String DFA22_minS =
        "\2\64\2\uffff";
    static final String DFA22_maxS =
        "\1\71\1\74\2\uffff";
    static final String DFA22_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA22_specialS =
        "\4\uffff}>";
    static final String[] DFA22_transitionS = {
            "\1\1\2\uffff\1\2\1\uffff\1\2",
            "\1\1\2\uffff\1\2\1\uffff\1\2\2\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "()+ loopback of 179:22: ( singleTypeVarList )+";
        }
    }
 

    public static final BitSet FOLLOW_domain_in_pddlDoc246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_problem_in_pddlDoc250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_domain265 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_domain267 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_domainName_in_domain269 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_requireDef_in_domain277 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_typesDef_in_domain286 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_constantsDef_in_domain296 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_predicatesDef_in_domain305 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_functionsDef_in_domain314 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_constraints_in_domain323 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_structureDef_in_domain332 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_domain341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_domainName425 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_domainName427 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_NAME_in_domainName429 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_domainName431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_requireDef458 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_requireDef460 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_REQUIRE_KEY_in_requireDef462 = new BitSet(new long[]{0x0202000000000000L});
    public static final BitSet FOLLOW_57_in_requireDef465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_typesDef486 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_typesDef488 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_typedNameList_in_typesDef490 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_typesDef492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_typedNameList519 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_singleTypeNameList_in_typedNameList524 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_NAME_in_typedNameList527 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_NAME_in_singleTypeNameList547 = new BitSet(new long[]{0x1000000080000000L});
    public static final BitSet FOLLOW_60_in_singleTypeNameList550 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_type_in_singleTypeNameList554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_type581 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_type583 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_primType_in_type585 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_57_in_type588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primType_in_type607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_primType617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_functionsDef627 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_functionsDef629 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_functionList_in_functionsDef631 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_functionsDef633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fOuterBody_in_functionList655 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_functionBody_in_fOuterBody671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicFunctionSkeleton_in_functionBody692 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_functionBody695 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_functionType_in_functionBody697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_atomicFunctionSkeleton715 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_functionSymbol_in_atomicFunctionSkeleton718 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_atomicFunctionSkeleton721 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_atomicFunctionSkeleton723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_functionSymbol740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_functionType749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_constantsDef760 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_constantsDef762 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_typedNameList_in_constantsDef764 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_constantsDef766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_predicatesDef786 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_predicatesDef788 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_atomicFormulaSkeleton_in_predicatesDef790 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_predicatesDef793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_atomicFormulaSkeleton814 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_predicate_in_atomicFormulaSkeleton816 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_atomicFormulaSkeleton818 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_atomicFormulaSkeleton821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_predicate840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VARIABLE_in_typedVariableList857 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_singleTypeVarList_in_typedVariableList862 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_VARIABLE_in_typedVariableList865 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_VARIABLE_in_singleTypeVarList886 = new BitSet(new long[]{0x1010000000000000L});
    public static final BitSet FOLLOW_60_in_singleTypeVarList889 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_type_in_singleTypeVarList893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_constraints924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_constraints927 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_conGD_in_constraints930 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_constraints932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionDef_in_structureDef944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_durativeActionDef_in_structureDef949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_derivedDef_in_structureDef954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_actionDef969 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_actionDef971 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_actionSymbol_in_actionDef973 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_params_in_actionDef977 = new BitSet(new long[]{0x0200000000000000L,0x0000000000002040L});
    public static final BitSet FOLLOW_actionDefBody_in_actionDef990 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_actionDef992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_actionSymbol1024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_paramsBody_in_params1033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_paramsBody1055 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_55_in_paramsBody1059 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_paramsBody1062 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_paramsBody1065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_paramsBody1068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_actionDefBody1085 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_actionDefBody1089 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_actionDefBody1091 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_goalDesc_in_actionDefBody1096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_actionDefBody1106 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_actionDefBody1110 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_actionDefBody1112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_effect_in_actionDefBody1117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicTermFormula_in_goalDesc1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goalDesc1158 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_goalDesc1160 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1162 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goalDesc1190 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_goalDesc1192 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1194 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goalDesc1223 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_goalDesc1225 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1227 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goalDesc1253 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_goalDesc1255 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1257 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1259 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goalDesc1287 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_goalDesc1289 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_goalDesc1291 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_goalDesc1293 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1295 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1297 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goalDesc1325 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_goalDesc1327 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_goalDesc1329 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_goalDesc1331 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1333 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goalDesc1335 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goalDesc1337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fComp_in_goalDesc1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_fComp1402 = new BitSet(new long[]{0x0000000000000000L,0x0000000000E60000L});
    public static final BitSet FOLLOW_binaryComp_in_fComp1405 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_fComp1407 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_fComp1409 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_fComp1411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_atomicTermFormula1423 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_predicate_in_atomicTermFormula1425 = new BitSet(new long[]{0x0210000080000000L});
    public static final BitSet FOLLOW_term_in_atomicTermFormula1427 = new BitSet(new long[]{0x0210000080000000L});
    public static final BitSet FOLLOW_57_in_atomicTermFormula1430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_durativeActionDef1471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_durativeActionDef1473 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_actionSymbol_in_durativeActionDef1475 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_durativeActionDef1484 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_durativeActionDef1486 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_durativeActionDef1488 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_durativeActionDef1490 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000050L});
    public static final BitSet FOLLOW_daDefBody_in_durativeActionDef1503 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_durativeActionDef1505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_daDefBody1538 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_durationConstraint_in_daDefBody1540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_daDefBody1546 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_daDefBody1550 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daDefBody1552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_daGD_in_daDefBody1557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_daDefBody1566 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_daDefBody1570 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daDefBody1572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_daEffect_in_daDefBody1577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prefTimedGD_in_daGD1592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_daGD1598 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_daGD1600 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_daGD_in_daGD1602 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_daGD1605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_daGD1610 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_daGD1612 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_daGD1614 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_daGD1616 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daGD1618 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daGD_in_daGD1620 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daGD1622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timedGD_in_prefTimedGD1633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_prefTimedGD1638 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_prefTimedGD1640 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_NAME_in_prefTimedGD1642 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_timedGD_in_prefTimedGD1645 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_prefTimedGD1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_timedGD1658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_timedGD1660 = new BitSet(new long[]{0x0000000000000000L,0x0080000400000000L});
    public static final BitSet FOLLOW_timeSpecifier_in_timedGD1662 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_timedGD1664 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_timedGD1666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_timedGD1671 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_111_in_timedGD1673 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_interval_in_timedGD1675 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_timedGD1677 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_timedGD1679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_interval1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_derivedDef1714 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_derivedDef1717 = new BitSet(new long[]{0x0090000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_derivedDef1720 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_derivedDef1722 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_derivedDef1724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_fExp1739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_fExp1744 = new BitSet(new long[]{0x3C00000000000000L});
    public static final BitSet FOLLOW_binaryOp_in_fExp1746 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_fExp1748 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp2_in_fExp1750 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_fExp1752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_fExp1769 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_fExp1771 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_fExp1773 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_fExp1775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fHead_in_fExp1788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fExp_in_fExp21800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_fHead1810 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_functionSymbol_in_fHead1812 = new BitSet(new long[]{0x0210000080000000L});
    public static final BitSet FOLLOW_term_in_fHead1814 = new BitSet(new long[]{0x0210000080000000L});
    public static final BitSet FOLLOW_57_in_fHead1817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionSymbol_in_fHead1833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_effect1852 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_effect1854 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_cEffect_in_effect1856 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_effect1859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cEffect_in_effect1873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_cEffect1884 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_cEffect1886 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_cEffect1888 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_cEffect1890 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_cEffect1892 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_effect_in_cEffect1894 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_cEffect1896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_cEffect1914 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_cEffect1916 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_cEffect1918 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_condEffect_in_cEffect1920 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_cEffect1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pEffect_in_cEffect1940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_pEffect1951 = new BitSet(new long[]{0x0000000000000000L,0x000C010060000000L});
    public static final BitSet FOLLOW_assignOp_in_pEffect1953 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_fHead_in_pEffect1955 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_pEffect1957 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_pEffect1959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_pEffect1979 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_pEffect1981 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_atomicTermFormula_in_pEffect1983 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_pEffect1985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicTermFormula_in_pEffect2001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_condEffect2014 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_condEffect2016 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_pEffect_in_condEffect2018 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_condEffect2021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pEffect_in_condEffect2035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_durationConstraint2122 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_durationConstraint2124 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_simpleDurationConstraint_in_durationConstraint2126 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_durationConstraint2129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_durationConstraint2134 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_durationConstraint2136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleDurationConstraint_in_durationConstraint2141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_simpleDurationConstraint2152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A40000L});
    public static final BitSet FOLLOW_durOp_in_simpleDurationConstraint2154 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_simpleDurationConstraint2156 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_durValue_in_simpleDurationConstraint2158 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_simpleDurationConstraint2160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_simpleDurationConstraint2165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_simpleDurationConstraint2167 = new BitSet(new long[]{0x0000000000000000L,0x0080000400000000L});
    public static final BitSet FOLLOW_timeSpecifier_in_simpleDurationConstraint2169 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_simpleDurationConstraint_in_simpleDurationConstraint2171 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_simpleDurationConstraint2173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_durValue2200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fExp_in_durValue2204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_daEffect2214 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_daEffect2216 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_daEffect_in_daEffect2218 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_daEffect2221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timedEffect_in_daEffect2226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_daEffect2231 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_daEffect2233 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_daEffect2235 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_daEffect2237 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daEffect2239 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daEffect_in_daEffect2241 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daEffect2243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_daEffect2248 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_daEffect2250 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daGD_in_daEffect2252 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_timedEffect_in_daEffect2254 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daEffect2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_daEffect2261 = new BitSet(new long[]{0x0000000000000000L,0x000C010060000000L});
    public static final BitSet FOLLOW_assignOp_in_daEffect2263 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_fHead_in_daEffect2265 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_daEffect2267 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_daEffect2269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_timedEffect2280 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_timedEffect2282 = new BitSet(new long[]{0x0000000000000000L,0x0080000400000000L});
    public static final BitSet FOLLOW_timeSpecifier_in_timedEffect2284 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daEffect_in_timedEffect2286 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_timedEffect2288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_timedEffect2298 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_timedEffect2300 = new BitSet(new long[]{0x0000000000000000L,0x0080000400000000L});
    public static final BitSet FOLLOW_timeSpecifier_in_timedEffect2302 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_fAssignDA_in_timedEffect2304 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_timedEffect2306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_timedEffect2311 = new BitSet(new long[]{0x0000000000000000L,0x000C010060000000L});
    public static final BitSet FOLLOW_assignOp_in_timedEffect2313 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_fHead_in_timedEffect2315 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_timedEffect2317 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_timedEffect2319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_fAssignDA2339 = new BitSet(new long[]{0x0000000000000000L,0x000C010060000000L});
    public static final BitSet FOLLOW_assignOp_in_fAssignDA2341 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_fHead_in_fAssignDA2343 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_fAssignDA2345 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_fAssignDA2347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_fExpDA2358 = new BitSet(new long[]{0x3C00000000000000L});
    public static final BitSet FOLLOW_binaryOp_in_fExpDA2362 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_fExpDA2364 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_fExpDA2366 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_60_in_fExpDA2372 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_fExpDA2374 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_fExpDA2378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_fExpDA2383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fExp_in_fExpDA2388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_problem2402 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_problem2404 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_problemDecl_in_problem2406 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_problemDomain_in_problem2411 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_requireDef_in_problem2419 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_objectDecl_in_problem2428 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_init_in_problem2437 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goal_in_problem2445 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_probConstraints_in_problem2453 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_metricSpec_in_problem2462 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_problem2478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_problemDecl2535 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_problemDecl2537 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_NAME_in_problemDecl2539 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_problemDecl2541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_problemDomain2567 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_problemDomain2569 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_NAME_in_problemDomain2571 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_problemDomain2573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_objectDecl2593 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_objectDecl2595 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_typedNameList_in_objectDecl2597 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_objectDecl2599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_init2619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_init2621 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_initEl_in_init2623 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_init2626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameLiteral_in_initEl2647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_initEl2652 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_initEl2654 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_fHead_in_initEl2656 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_initEl2658 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_initEl2660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_initEl2683 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_initEl2685 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_initEl2687 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_nameLiteral_in_initEl2689 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_initEl2691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicNameFormula_in_nameLiteral2713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_nameLiteral2718 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_nameLiteral2720 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_atomicNameFormula_in_nameLiteral2722 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_nameLiteral2724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_atomicNameFormula2743 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_predicate_in_atomicNameFormula2745 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_NAME_in_atomicNameFormula2747 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_57_in_atomicNameFormula2750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_goal2775 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_goal2777 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_goal2779 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_goal2781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_probConstraints2799 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_probConstraints2801 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_prefConGD_in_probConstraints2804 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_probConstraints2806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_prefConGD2828 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_prefConGD2830 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_prefConGD_in_prefConGD2832 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_prefConGD2835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_prefConGD2840 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_prefConGD2842 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_prefConGD2844 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_prefConGD2846 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_prefConGD2848 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_prefConGD_in_prefConGD2850 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_prefConGD2852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_prefConGD2857 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_prefConGD2859 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_NAME_in_prefConGD2861 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_conGD_in_prefConGD2864 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_prefConGD2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conGD_in_prefConGD2871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_metricSpec2882 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_metricSpec2884 = new BitSet(new long[]{0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_optimization_in_metricSpec2886 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_metricSpec2888 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_metricSpec2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_metricFExp2927 = new BitSet(new long[]{0x3C00000000000000L});
    public static final BitSet FOLLOW_binaryOp_in_metricFExp2929 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_metricFExp2931 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_metricFExp2933 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_metricFExp2935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_metricFExp2940 = new BitSet(new long[]{0x2400000000000000L});
    public static final BitSet FOLLOW_set_in_metricFExp2942 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_metricFExp2948 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_metricFExp2950 = new BitSet(new long[]{0x0280000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_57_in_metricFExp2953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_metricFExp2958 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_metricFExp2960 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_metricFExp2962 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_metricFExp2964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_metricFExp2969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_metricFExp2974 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_functionSymbol_in_metricFExp2976 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_NAME_in_metricFExp2978 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_57_in_metricFExp2981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionSymbol_in_metricFExp2986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_metricFExp2994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_metricFExp2999 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_105_in_metricFExp3001 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_NAME_in_metricFExp3003 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_metricFExp3005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3019 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_conGD3021 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_conGD_in_conGD3023 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3031 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_conGD3033 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_conGD3035 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_conGD3037 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3039 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_conGD_in_conGD3041 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3048 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_conGD3050 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_conGD3052 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3054 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3064 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_conGD3066 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3068 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3075 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_116_in_conGD3077 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3079 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3087 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_122_in_conGD3089 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_conGD3091 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3093 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3100 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_conGD3102 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3104 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3111 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_117_in_conGD3113 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3115 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3117 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3124 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_118_in_conGD3126 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3128 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3130 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3137 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_conGD3139 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_conGD3141 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3143 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3145 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3152 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_conGD3154 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_conGD3156 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_conGD3158 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3160 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_conGD3167 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_conGD3169 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_NUMBER_in_conGD3171 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_goalDesc_in_conGD3173 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_conGD3175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typedVariableList_in_synpred20_Pddl818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typedVariableList_in_synpred28_Pddl1062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred57_Pddl1744 = new BitSet(new long[]{0x3C00000000000000L});
    public static final BitSet FOLLOW_binaryOp_in_synpred57_Pddl1746 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_synpred57_Pddl1748 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp2_in_synpred57_Pddl1750 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred57_Pddl1752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred58_Pddl1769 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_synpred58_Pddl1771 = new BitSet(new long[]{0x0080000880000000L});
    public static final BitSet FOLLOW_fExp_in_synpred58_Pddl1773 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred58_Pddl1775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_synpred86_Pddl2200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred88_Pddl2214 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_synpred88_Pddl2216 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_daEffect_in_synpred88_Pddl2218 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_synpred88_Pddl2221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timedEffect_in_synpred89_Pddl2226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred90_Pddl2231 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_synpred90_Pddl2233 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_synpred90_Pddl2235 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_synpred90_Pddl2237 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred90_Pddl2239 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daEffect_in_synpred90_Pddl2241 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred90_Pddl2243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred91_Pddl2248 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_synpred91_Pddl2250 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daGD_in_synpred91_Pddl2252 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_timedEffect_in_synpred91_Pddl2254 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred91_Pddl2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred92_Pddl2280 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_synpred92_Pddl2282 = new BitSet(new long[]{0x0000000000000000L,0x0080000400000000L});
    public static final BitSet FOLLOW_timeSpecifier_in_synpred92_Pddl2284 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_daEffect_in_synpred92_Pddl2286 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred92_Pddl2288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred93_Pddl2298 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_synpred93_Pddl2300 = new BitSet(new long[]{0x0000000000000000L,0x0080000400000000L});
    public static final BitSet FOLLOW_timeSpecifier_in_synpred93_Pddl2302 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_fAssignDA_in_synpred93_Pddl2304 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred93_Pddl2306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_binaryOp_in_synpred94_Pddl2362 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_synpred94_Pddl2364 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_synpred94_Pddl2366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred95_Pddl2358 = new BitSet(new long[]{0x3C00000000000000L});
    public static final BitSet FOLLOW_binaryOp_in_synpred95_Pddl2362 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_synpred95_Pddl2364 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_synpred95_Pddl2366 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_60_in_synpred95_Pddl2372 = new BitSet(new long[]{0x0080000880000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fExpDA_in_synpred95_Pddl2374 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred95_Pddl2378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred107_Pddl2828 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_synpred107_Pddl2830 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_prefConGD_in_synpred107_Pddl2832 = new BitSet(new long[]{0x0280000000000000L});
    public static final BitSet FOLLOW_57_in_synpred107_Pddl2835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred108_Pddl2840 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_synpred108_Pddl2842 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_synpred108_Pddl2844 = new BitSet(new long[]{0x0210000000000000L});
    public static final BitSet FOLLOW_typedVariableList_in_synpred108_Pddl2846 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred108_Pddl2848 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_prefConGD_in_synpred108_Pddl2850 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred108_Pddl2852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred110_Pddl2857 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_synpred110_Pddl2859 = new BitSet(new long[]{0x0080000080000000L});
    public static final BitSet FOLLOW_NAME_in_synpred110_Pddl2861 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_conGD_in_synpred110_Pddl2864 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred110_Pddl2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred112_Pddl2927 = new BitSet(new long[]{0x3C00000000000000L});
    public static final BitSet FOLLOW_binaryOp_in_synpred112_Pddl2929 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_synpred112_Pddl2931 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_synpred112_Pddl2933 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred112_Pddl2935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred115_Pddl2940 = new BitSet(new long[]{0x2400000000000000L});
    public static final BitSet FOLLOW_set_in_synpred115_Pddl2942 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_synpred115_Pddl2948 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_synpred115_Pddl2950 = new BitSet(new long[]{0x0280000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_57_in_synpred115_Pddl2953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred116_Pddl2958 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_synpred116_Pddl2960 = new BitSet(new long[]{0x0080000880000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_metricFExp_in_synpred116_Pddl2962 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_synpred116_Pddl2964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred119_Pddl2974 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_functionSymbol_in_synpred119_Pddl2976 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_NAME_in_synpred119_Pddl2978 = new BitSet(new long[]{0x0200000080000000L});
    public static final BitSet FOLLOW_57_in_synpred119_Pddl2981 = new BitSet(new long[]{0x0000000000000002L});

}