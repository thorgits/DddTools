package code2modle.scanpackage.script;


import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author liwenjun
 * @ClassName Script
 * @Date 2019-12-17 17:25
 */
public class Script {
    Style style;
    Footer footer;
    Header header;
    List<String> relations = new ArrayList<>();
    List<String> items = new ArrayList<>();
    List<String> sequences=new ArrayList<>();

    public List<String> getSequences() {
        return sequences;
    }

    public void setSequences(List<String> sequences) {
        this.sequences = sequences;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
