package sandbox.i18n;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

import java.util.HashMap;
import java.util.Map;

/**
 * リソースからメッセージ取ってきて表示するやつ。
 * wicket:message 使ったり ResourceModel 使ったりバインドしたり。
 *
 * @author irof
 */
public class ResourcePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("resource.static", new ResourceModel("simple")));

        Object[] args = {"Dynamic", "Args"};
        add(new Label("resource.dynamic1", new StringResourceModel("dynamic1", Model.of(), args)));

        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("arg1", "Argument1");
        valueMap.put("arg3", "Argument3");
        add(new Label("resource.dynamic2", new StringResourceModel("dynamic2", Model.ofMap(valueMap))));

        add(new Label("markup.label.pattern1", new ResourceModel("markup.pattern1")));
        add(new Label("markup.label.pattern2", new ResourceModel("markup.pattern2")));
        add(new Label("markup.label.pattern3", new ResourceModel("markup.pattern3")));

        add(new Label("markup.label.pattern1.unescape", new ResourceModel("markup.pattern1")).setEscapeModelStrings(false));
        add(new Label("markup.label.pattern2.unescape", new ResourceModel("markup.pattern2")).setEscapeModelStrings(false));
        add(new Label("markup.label.pattern3.unescape", new ResourceModel("markup.pattern3")).setEscapeModelStrings(false));
    }
}
