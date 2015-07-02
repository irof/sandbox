package sandbox.misc;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author irof
 */
public class LinkPage extends WebPage {

    public LinkPage() {
    }

    public LinkPage(PageParameters params) {
        super(params);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // AbstractLink
        // そのまま使っても面白みのない何も起こらないリンク
        add(new AbstractLink("abstract") {
        });
        // a, link, area タグの時に disableLink を呼ぶと span タグに変わる
        // buttonとかinputだと disabled になる
        add(new AbstractLink("abstract.disable") {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                disableLink(tag);
            }
        });

        // 通常のリンク
        add(new Link<Void>("link") {
            @Override
            public void onClick() {
                setResponsePage(LinkPage.class);
            }
        });

        // ブックマーク可能なURLへのリンク
        add(new BookmarkablePageLink<LinkPage>("bookmarkable", LinkPage.class));
        // PageParameterを渡すこともできる
        PageParameters params = new PageParameters();
        params.set("fizz", "buzz");
        add(new BookmarkablePageLink<LinkPage>("bookmarkable.withParams", LinkPage.class, params));

        // ステートレスなリンク
        // ステートレスなPageの場合はこっちを使うか、Link#setStatelessHint が true を返すようにする。
        // 他に通常のリンクとの違ってコンストラクタでモデルを受け取らないけど、 setModel は普通に呼べるぽい。
        // リンク時の
        add(new StatelessLink<Void>("stateless") {
            @Override
            public void onClick() {
                setResponsePage(LinkPage.class);
            }
        });

        // 外部リンクは特筆することはありません。
        add(new ExternalLink("external", "http://irof.hateblo.jp/"));

        // onload で javascript:self.close() やるHTMLに行く便利機能。
        add(new PopupCloseLink<Void>("popup"));

        // ファイルを直接ダウンロードさせるリンク
        // ファイルじゃなかったら ResourceLink を使う感じかな。
        add(new DownloadLink("download", getDownloadFile()));

        // 任意のリソースをへのリンク
        // ResourceLink 自体はたいしたことなくて、 ResourceReference が主役ぽい。
        // ResourceReference は実装クラスいっぱい。
        add(new ResourceLink<Void>("resource", new ResourceReference("fuga") {
            @Override
            public IResource getResource() {
                return new ByteArrayResource("text/plain", "piyo".getBytes(StandardCharsets.UTF_8));
            }
        }));

        // 以下ページ情報
        add(new Label("info.request", String.valueOf(getRequest())));
        add(new Label("info.path", String.valueOf(getClassRelativePath())));
        add(new Label("info.id", String.valueOf(getId())));
        add(new Label("info.parameters", String.valueOf(getPageParameters())));
    }

    public File getDownloadFile() {
        try {
            File file = File.createTempFile("LinkPage", ".txt");
            Files.write(file.toPath(), "hoge".getBytes(StandardCharsets.UTF_8));
            return file;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
