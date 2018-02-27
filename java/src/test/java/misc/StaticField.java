package misc;

/**
 * static finalだとコンパイル時にインライン化されるけれど、finalではないのでインライン化されない。
 * でもクラスロード時にフィールド初期化が動くのでHOGE_FUGAはinitメソッドが呼ばれる前にHOGEを読む。
 */
public class StaticField {

    static String HOGE;

    static String HOGE_FUGA = HOGE + "_FUGA";

    static void init() {
        HOGE = "HOGE";
    }

    public static void main(String[] args) {
        init();
        System.out.println(HOGE_FUGA);
    }
}
