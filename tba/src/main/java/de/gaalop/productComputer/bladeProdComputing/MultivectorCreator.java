package de.gaalop.productComputer.bladeProdComputing;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.api.dfg.IllegalExpressionVisitor;
import de.gaalop.tba.Algebra;
import de.gaalop.tba.Blade;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;

/**
 * Creates a multivector of an expression
 * @author Christian Steinmetz
 */
public class MultivectorCreator extends IllegalExpressionVisitor {

    private Multivector result;
    private Algebra algebra;
    private BladeIndexer indexer;

    private Stack<Float> prefactors = new Stack<Float>();

    private MultivectorCreator(Algebra algebra, BladeIndexer indexer) {
        this.algebra = algebra;
        this.indexer = indexer;
        this.result = new Multivector();
    }

    /**
     * Creates a multivector of an expression
     * @param expression The expression
     * @param algebra The algebra to use
     * @param indexer The blade indexer
     * @return The created multivector
     */
    public static Multivector getMultivectorFromExpression(Expression expression, Algebra algebra, BladeIndexer indexer) {
        MultivectorCreator creator = new MultivectorCreator(algebra, indexer);
        expression.accept(creator);
        return creator.result;
    }

    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        prefactors.push(new Float(-1));
        node.getRight().accept(this);
        prefactors.pop();
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(Multiplication node) {
        if (node.getLeft() instanceof FloatConstant) {
            prefactors.push(((FloatConstant) node.getLeft()).getValue());
            node.getRight().accept(this);
            prefactors.pop();
        } else {
            prefactors.push(((FloatConstant) node.getRight()).getValue());
            node.getLeft().accept(this);
            prefactors.pop();
        }
    }

    @Override
    public void visit(Variable node) {

        float prefactor = 1;
        for (Float f: prefactors) 
            prefactor *= f;
        
        if (prefactor-Math.round(prefactor) > 10E-5)  {
            System.err.println("Var: "+prefactor+" must be a byte!");
            
        }

        int p = Math.round(prefactor);
        if (p<-1 ||p>1)
            System.err.println("FC2:" + prefactor+" must be -1 or 0 or 1, but is "+p+"!");


        int index = Integer.parseInt(node.getName().substring(1)); 
        index = algebra.getIndex(new Blade(new Vector<String>(Arrays.asList(indexer.getBlade(index).getBase()))));

        result.addBlade(new BladeRef((byte) p, index));
    }

    @Override
    public void visit(FloatConstant node) {
        float prefactor = node.getValue();
        for (Float f: prefactors)
            prefactor *= f;

        if (Math.abs(prefactor) > 10E-5) {
            if (prefactor-Math.round(prefactor) > 10E-5)
                System.err.println("FC:" + prefactor+" must be a byte!");

            int p = Math.round(prefactor);
            if (p<-1 ||p>1) System.err.println("FC:" + prefactor+" must be -1 or 0 or 1, but is "+p+"!");
            
            result.addBlade(new BladeRef((byte) p, 0));
        }
        
    }

    @Override
    public void visit(Negation node) {
        prefactors.push(new Float(-1));
        node.getOperand().accept(this);
        prefactors.pop();
    }

}
