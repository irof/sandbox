package client.apicall;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.ws.rs.client.ClientBuilder;

/**
 * OpenWatherMapのAPIを適当に叩きます。
 *
 * @author irof
 * @see <a href="http://openweathermap.org/api">API</a>
 */
public class OpenWeatherMap {

    public static void main(String[] args) {
        // 大阪のをとってきます
        Res res = ClientBuilder.newClient()
                .target("http://api.openweathermap.org/data/2.5")
                .path("weather")
                .queryParam("q", "Osaka")
                .queryParam("APPID", System.getProperty("APPID"))
                .request()
                .get(Res.class);

        System.out.println(res);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Res {
        public long id;
        public String name;
        public Weather[] weather;

        @Override
        public String toString() {
            return String.format("%s: %s", name, weather[0]);
        }
    }

    public static class Weather {
        public long id;
        public String main;
        public String description;
        public String icon;

        @Override
        public String toString() {
            return description;
        }
    }
}
