package de.gaalop.ganja;

import de.gaalop.StringList;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Variable;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Christian Steinmetz
 */
public abstract class GanjaVisitor implements ControlFlowVisitor {

    protected Log log = LogFactory.getLog(GradedGanjaVisitor.class);

    protected AlgebraProperties algebraProperties;

    protected HashSet<String> mvs = new HashSet<>();

    protected ControlFlowGraph graph = null;

    protected HashMap<String, LinkedList<Integer>> mvComponents = new HashMap<>();

    protected HashMap<String, Color> expressionStatements = new HashMap<>();

    protected Color curColor = Color.black;

    protected int indentation = 0;

    protected BlockStringBuilder code = new BlockStringBuilder();

    public GanjaVisitor(AlgebraProperties algebraProperties) {
        this.algebraProperties = algebraProperties;
    }

    public String getCode(OutputMode outputMode) {
        return code.getCode(outputMode);
    }
    
    public void printDebugCode() {
        code.printDebugCode();
    }

    protected void appendIndentation() {
        for (int i = 0; i < indentation; ++i) {
            code.appendO('\t');
        }
    }
    
    public StringBuilder appendI(char c) {
        appendIndentation();
        return code.appendO(c);
    }
    
    public StringBuilder appendI(String str) {
        appendIndentation();
        return code.appendO(str);
    }

    @Override
    public void visit(StoreResultNode node) {
        code.storeResultNodes.add(node.getValue().toString());
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        expressionStatements.put(((Variable) (node.getExpression())).getName(), new Color(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), curColor.getAlpha()));
        node.getSuccessor().accept(this);
    }
    
    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();

        code.entryBlock("Begin Algebra");
        
        appendI(algebraProperties.getAlgebraHeader());
        indentation++;
        appendI(algebraProperties.getExtraBasisVectors());
        appendI(algebraProperties.getUp());
 
        code.entryBlock("Define FunctionToSliders");
        StringList inputParametersStrList = addFunctionToSliders();
        
        code.entryBlock("Define calculate");
        appendI("function calculate(" + inputParametersStrList.join() + ") {\n");

        indentation++;

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ColorNode node) {
        curColor = new Color(
                (float) ((FloatConstant) node.getR()).getValue(),
                (float) ((FloatConstant) node.getG()).getValue(),
                (float) ((FloatConstant) node.getB()).getValue(),
                (float) ((FloatConstant) node.getAlpha()).getValue()
        );

        node.getSuccessor().accept(this);
    }

    protected StringList addFunctionToSliders() {
        StringList inputParametersStrList = new StringList();
        StringList argsList = new StringList();
        StringList minList = new StringList();
        StringList maxList = new StringList();
        StringList stepList = new StringList();
        StringList cenList = new StringList();
        for (String var : graph.getInputs()) {
            inputParametersStrList.add(var);
            argsList.add("\"" + var + "= \"");

            String min = graph.getPragmaMinValue().getOrDefault(var, "0");
            String max = graph.getPragmaMaxValue().getOrDefault(var, "1");
            String def = Double.toString((Double.parseDouble(max) - Double.parseDouble(min)) / 100.0d);

            minList.add(min);
            maxList.add(max);
            cenList.add(Double.toString((Double.parseDouble(min) + Double.parseDouble(max)) / 2.0d));
            stepList.add(def);
        }
        
        code.setCenList(cenList);
        
        String sliders = 
                  "    var f_args = [" + argsList.join() + "];\n"
                + "    var min_list = [" + minList.join() + "];\n"
                + "    var max_list = [" + maxList.join() + "];\n"
                + "    var step_list = [" + stepList.join() + "];\n"
                + "    var cen_list = [" + cenList.join() + "];\n";
        code.setSliders(sliders);

        code.appendO(
                  "  var function_to_sliders = f=>{\n"
                + sliders
                + "    window.sliderValues = cen_list.slice(0);\n"
                + "    var sliderValueTextElements = cen_list.slice(0);\n"
                + "    for (let i=0; i<f.length; i++) {\n"
                + "      var slideritem = document.createElement('div');\n"
                + "      slideritem.setAttribute(\"class\", \"slideritem\");\n"
                + "      document.getElementById(\"sliderpanel\").appendChild(slideritem);\n"
                + "      var slidername = document.createElement('a');\n"
                + "      slidername.appendChild(document.createTextNode('['+min_list[i]+','+max_list[i]+']'));\n"
                + "      slideritem.appendChild(slidername);\n"
                + "      var slider = Object.assign(document.createElement('input'),{type:'range',min:min_list[i],max:max_list[i],step:step_list[i],value:cen_list[i]});\n"
                + "      slider.oninput = function(){sliderValueTextElements[i].innerHTML=this.value; window.sliderValues[i]=+this.value};\n"
                + "      slideritem.appendChild(slider);\n"
                + "      var slidername = document.createElement('a');\n"
                + "      slidername.appendChild(document.createTextNode(f_args[i]));\n"
                + "      slideritem.appendChild(slidername);\n"
                + "      sliderValueTextElements[i] = document.createElement('a');\n"
                + "      sliderValueTextElements[i].innerHTML = cen_list[i];\n"
                + "      slideritem.appendChild(sliderValueTextElements[i]);\n"
                + "    }\n"
                + "  }\n"
                + "  \n"
        );

        return inputParametersStrList;
    }
    
    protected void computeInitializeOutputNullVars() {
        StringBuilder sb = new StringBuilder();
        //Initialize to null all output vars, which were removed by optimization.
        for (String mv: code.storeResultNodes)
            if (!mvs.contains(mv))
            {
                sb.append("var "+mv+" = new Element();\n");
            }
        code.initializeOutputNullVars = sb.toString();
    }

    protected void computeRenderListWithoutColors() {
        StringBuilder sb = new StringBuilder();
        StringList renderList = new StringList();
        for (String mv: new StringList(code.storeResultNodes).sortIgnoringCase()) {
            renderList.add(mv);
            renderList.add("\""+mv+"\"");
        }
        sb.append("return ["+renderList.join()+"];\n");
        code.renderListWithoutColors = sb.toString();
    }
    
    protected void addBlocks() {
        code.entryBlock("Set RenderList");

        StringList renderList = new StringList();
        for (String mv : new StringList(this.expressionStatements.keySet()).sortIgnoringCase()) {
            Color c = this.expressionStatements.get(mv);
            renderList.add("0x" + String.format("%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
            renderList.add(mv);
            renderList.add("\"" + mv + "\"");
        }

        // add segments
        for (String[] segment: graph.drawSegments)
            if (segment.length == 2) {
                Color c = this.expressionStatements.get(segment[0]);
                renderList.add("0x" + String.format("%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                renderList.add("["+segment[0]+","+segment[1]+"]");
                renderList.add("\"\"");
            }
        
        // add triangles
        for (String[] triangle: graph.drawTriangles)
            if (triangle.length == 3) {
                Color c = this.expressionStatements.get(triangle[0]);
                renderList.add("0x" + String.format("%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                renderList.add("["+triangle[0]+","+triangle[1]+","+triangle[2]+"]");
                renderList.add("\"\"");
            }
        
        code.setRenderList(renderList);
        appendI("return [" + renderList.join() + "];\n");
        indentation--;
        appendI("}\n");

        code.entryBlock("Call Function to Sliders");
        appendI("function_to_sliders(calculate);\n\n");

        code.entryBlock("Call Graph");
        
        StringList options = new StringList();
        options.add("conformal:"+Boolean.toString(algebraProperties.isConformal()));
        options.add("gl:"+Boolean.toString(algebraProperties.isGL()));
        options.add("animate: true");
        options.add("ipns:true");
        options.add("noZ:true");
        options.add("grid:true");
        options.add("labels:true");
        options.add("thresh:0.05");
        options.add("lineWidth: 2");
        if (!algebraProperties.getUp().equals("")) {
            if (algebraProperties.getRenderingDimensions() == 2)
                options.add("up:up(Element.Scalar(\"x\"),Element.Scalar(\"y\")).Vector.map(x=>x.replace(new RegExp(\"-1\",\"g\"),\"-1.0\"))");
            else 
                options.add("up:up(Element.Scalar(\"x\"),Element.Scalar(\"y\"),Element.Scalar(\"z\")).Vector");
        }
        code.setGraphOptions("{ "+options.join()+" }");

        appendI(
		  "var canvas = this.graph(()=>[\n"
		+ "   ...calculate(...sliderValues)\n"
		+ "], "+code.getGraphOptions()+");\n");

        code.entryBlock("Set canvas options");
        
        appendI("canvas.style.width = '49vw';\n");
        appendI("canvas.style.height = '50vh';\n");
        
        code.entryBlock("Add canvas");
        appendI("document.getElementById(\"ganjacanvas\").appendChild(canvas);\n");
        
        code.entryBlock("End Algebra");
        indentation--;
        appendI("});");
    }

    @Override
    public void visit(IfThenElseNode node) {
        throw new IllegalStateException("IfThenElseNodes are not supported anymore.");
    }

    @Override
    public void visit(LoopNode node) {
        throw new IllegalStateException("LoopNode are not supported anymore.");
    }

    @Override
    public void visit(BreakNode breakNode) {
        throw new IllegalStateException("BreakNode are not supported anymore.");
    }

    @Override
    public void visit(BlockEndNode node) {
        throw new IllegalStateException("BlockEndNode are not supported anymore.");
    }

    @Override
    public void visit(Macro node) {
        throw new IllegalStateException("Macros should have been inlined and removed from the graph.");
    }
}
