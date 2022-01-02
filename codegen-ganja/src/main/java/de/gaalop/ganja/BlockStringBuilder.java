package de.gaalop.ganja;

import de.gaalop.StringList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Christian Steinmetz
 */
public class BlockStringBuilder {

    public StringBuilder current = null;

    private HashMap<String, StringBuilder> blocks = new HashMap<>();

    public StringList blockorder = new StringList();

    private StringList renderList = new StringList();
    private StringList cenList = new StringList();
    private String graphOptions = "{}";

    private String sliders;
    public HashSet<String> storeResultNodes = new HashSet<String>();
    public String initializeOutputNullVars;
    public String renderListWithoutColors;

    public void entryBlock(String caption) {
        current = new StringBuilder();
        blocks.put(caption, current);
        blockorder.add(caption);
    }

    public StringBuilder appendO(char c) {
        return current.append(c);
    }

    public StringBuilder appendO(String str) {
        return current.append(str);
    }

    public StringBuilder getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String block : blockorder) {
            result.append(blocks.get(block));
        }
        return result.toString();
    }

    public String getCode(OutputMode outputMode) {
        switch (outputMode) {
            case COFFEESHOP: 
                return createCodeForCoffeeshop();
            case GAALOPWEB:
                return createCodeForGAALOPWeb();
            case JUPYTER_NOTEBOOK:
                return createCodeForJupyterNotebook();
            case STANDALONE_PAGE:
            default:
                return createCodeForStandalonePage();
        }
    }

    public void printDebugCode() {
        for (String block : blockorder) {
            System.out.println("// ============== BLOCK " + block + " =================");
            System.out.println(blocks.get(block).toString());
        }
    }

    public void setRenderList(StringList renderList) {
        this.renderList = renderList;
    }

    public void setCenList(StringList cenList) {
        this.cenList = cenList;
    }

    private String createCodeForCoffeeshop() {
        StringBuilder result = new StringBuilder();
        result.append(blocks.get("Begin Algebra"));
        result.append(blocks.get("Define calculate"));
        result.append(blocks.get("Set RenderList"));

        //Use centered input_values as input variable values
        result.append("var input_values = [" + cenList.join() + "];\n");

        //Modified canvas
        result.append("var canvas = this.graph(()=>{\n");
        result.append("   return calculate(...input_values);\n");
        result.append("}," + graphOptions + ");\n");

        result.append("document.body.appendChild(canvas);\n");
        result.append(blocks.get("End Algebra"));
        return result.toString();
    }

    private String createCodeForGAALOPWeb() {
        StringBuilder result = new StringBuilder();
        // GAALOPWeb output format is identical to Ganja Visitors output, so only use all blocks in right order (= blockorder)
        for (String block : blockorder) {
            result.append(blocks.get(block));
        }
        return result.toString();
    }

    private String createCodeForJupyterNotebook() {
        StringBuilder result = new StringBuilder();
        result.append(blocks.get("Begin Algebra"));
        result.append(blocks.get("Define calculate"));
        result.append(blocks.get("Set RenderList"));

        //Use centered input_values as input variable values
        result.append("var input_values = [" + cenList.join() + "];\n");

        //Modified canvas
        result.append("return this.graph(()=>{\n");
        result.append("   return calculate(...input_values);\n");
        result.append("}," + graphOptions + ");\n");

        result.append(blocks.get("End Algebra"));
        return result.toString();
    }

    private String createCodeForStandalonePage() {
        StringBuilder result = new StringBuilder();
        result.append(
                "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <meta charset=\"utf-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <script src=\"https://gaalopweb.esa.informatik.tu-darmstadt.de/gaalopweb/lib/ganja/ganja.js\"></script>\n"
                + "        <style>\n"
                + "            body {\n"
                + "                display: flex;\n"
                + "                flex: 1;\n"
                + "                flex-direction: column;\n"
                + "                justify-content: flex-start;\n"
                + "                align-items: stretch;\n"
                + "                min-height: 98vh;\n"
                + "                background-color: rgb(176,232,255);\n"
                + "                color: rgb(17,20,173);\n"
                + "            }\n"
                + "            .vispanel {\n"
                + "                display: flex;\n"
                + "                flex-direction: row;\n"
                + "                justify-content: flex-start;\n"
                + "                align-items: flex-start;\n"
                + "            }\n"
                + "            #sliderpanel {\n"
                + "                display: flex;\n"
                + "                flex-direction: column;\n"
                + "                justify-content: flex-start;\n"
                + "                align-items: flex-start;\n"
                + "            }\n"
                + "            .slideritem {\n"
                + "                display: flex;\n"
                + "                flex-direction: row;\n"
                + "                justify-content: center;\n"
                + "                align-items: center;\n"
                + "            }\n"
                + "        </style>\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <a>Visualization (Based on ganja.js):</a>\n"
                + "        <div class=\"vispanel\">\n"
                + "            <div id=\"ganjacanvas\"></div>\n"
                + "            <div id=\"sliderpanel\"></div>\n"
                + "        </div>\n"
                + "        <script>\n");
        result.append(createCodeForGAALOPWeb());
        result.append(
                "\n"
                + "        </script>\n"
                + "    </body>\n"
                + "</html>\n");
        return result.toString();
    }

    public String getGraphOptions() {
        return graphOptions;
    }

    public void setGraphOptions(String graphOptions) {
        this.graphOptions = graphOptions;
    }

    public void setSliders(String sliders) {
        this.sliders = sliders;
    }

}
