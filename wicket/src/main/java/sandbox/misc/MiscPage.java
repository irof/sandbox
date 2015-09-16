package sandbox.misc;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import sandbox.HomePage;
import sandbox.misc.switching.LabelOrTextField;

/**
 * いろいろごった煮。
 *
 * @author irof
 */
public class MiscPage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ButtonPanel("panel.button"));
        add(new ListPanel("panel.list"));
        add(new ChoicePanel("panel.choice"));

        Model<String> hoge1 = Model.of("hogehoge");
        add(new LabelOrTextField("panel.switching.input", hoge1, Model.of(true)));
        add(new LabelOrTextField("panel.switching.label", Model.of("fugafuga"), Model.of(false)));

        // "http://..../wicket"
        add(new Label("fullUrl",
                RequestCycle.get().getUrlRenderer().renderFullUrl(
                        Url.parse(urlFor(HomePage.class, null).toString()))));
        // "/wicket"
        add(new Label("contextPath",
                WebApplication.get().getServletContext().getContextPath()));
        // "../../" 相対位置になる
        add(new Label("urlForPage", urlFor(HomePage.class, null).toString()));
    }
}
