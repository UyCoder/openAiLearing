package dev.ahmed.mytool;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Ahmed Bughra
 * @create 2023-04-04  3:50 PM
 */
public class GetWeeklyNews {
    ////////////////////////////very usefull
    /**
     * Retrieves and generates a weekly news list in HTML format.
     *
     * @param keyWord      the keyword to search for in the news
     * @param durationFrom the number of days from which to retrieve news
     * @return an HTML string containing the list of news articles
     */
    public String getWeeklyNews(String keyWord) {
        String apiKey = "d6bc5945b6e94b1590c998e8e381cf6e";
        int durationFrom = 7;

        LocalDate currentDate = LocalDate.now();
        LocalDate fromDate = currentDate.minusDays(durationFrom);
        LocalDate toDate = fromDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedFromDate = fromDate.format(formatter);
        String formattedToDate = toDate.format(formatter);

        String url = "https://newsapi.org/v2/everything?q=" + keyWord + "&from=" + formattedFromDate + "&to=" + formattedToDate + "&sortBy=publishedAt&apiKey=" + apiKey;
        System.out.println(url);
        try {
            URL newsUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) newsUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray articles = jsonResponse.getJSONArray("articles");

            StringBuilder html = new StringBuilder("<html><head><style>");
            html.append("body {font-family: Arial, sans-serif; background-color: #f1f1f1;}");
            html.append("h1 {text-align: center;}");
            html.append(".article {padding: 20px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 10px; background-color: rgb(255, 255, 255);}");
            html.append(".title {font-weight: bold; font-size: 20px;}");
            html.append(".description {margin-top: 10px;}");
            html.append(".source {margin-top: 10px; font-style: italic;}");
            html.append(".published-date {margin-top: 10px;}");
            html.append("</style></head><body><br><h1>Weekly News about " + keyWord + " from " + formattedFromDate + " to " + formattedToDate + "</h1>");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String description = article.getString("description");
                String urlToArticle = article.getString("url");
                String source = article.getJSONObject("source").getString("name");
                String publishedAt = article.getString("publishedAt");
                String formattedPublishedAt = LocalDate.parse(publishedAt.substring(0, 10)).format(formatter);

                html.append("<div class=\"article\">");
                html.append("<a href=\"").append(urlToArticle).append("\" class=\"title\">").append(title).append("</a>");
                html.append("<div class=\"description\">").append(description).append("</div>");
                html.append("<div class=\"source\">").append(source).append("</div>");
                html.append("<div class=\"published-date\">").append(formattedPublishedAt).append("</div>");
                html.append("</div>");
            }
            html.append("    <footer>\n" +
                    "        <a>This Weekly News collected by </a><a href=\"https://www.uysi.org/ug\"> Uyghur Reserach Institude</a><br>\n" +
                    "        <a>Author of This Web Page: </a> <a href=\"https://github.com/UyCoder\">Ahmed Bughra</a><br>\n" +
                    "      </footer>");
            html.append("</body></html>");

            String fileName = "WeeklyNewsAbout" + keyWord + "-" + formattedFromDate + ".html";
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            Path path = Paths.get(desktopPath + fileName);
            Files.write(path, html.toString().getBytes());

            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *
     * Daily news
     * */
    public String getTodaysNews(String keyWord) {
        String apiKey = "d6bc5945b6e94b1590c998e8e381cf6e";

        LocalDate currentDate = LocalDate.now();
        LocalDate fromDate = currentDate.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedFromDate = fromDate.format(formatter);
        String formattedToDate = currentDate.format(formatter);

        String url = "https://newsapi.org/v2/everything?q=" + keyWord + "&from=" + formattedFromDate + "&to=" + formattedToDate + "&sortBy=publishedAt&apiKey=" + apiKey;
        System.out.println(url);
        try {
            URL newsUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) newsUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray articles = jsonResponse.getJSONArray("articles");

            StringBuilder html = new StringBuilder("<html><head><style>");
            html.append("body {font-family: Arial, sans-serif; background-color: #f1f1f1;}");
            html.append("h1 {text-align: center;}");
            html.append(".article {padding: 20px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 10px; background-color: rgb(255, 255, 255);}");
            html.append(".title {font-weight: bold; font-size: 20px;}");
            html.append(".description {margin-top: 10px;}");
            html.append(".source {margin-top: 10px; font-style: italic;}");
            html.append(".published-date {margin-top: 10px;}");
            html.append("</style></head><body><br><h1>Daily News about " + keyWord + " from " + formattedFromDate + " to " + formattedToDate + "</h1>");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String description = article.getString("description");
                String urlToArticle = article.getString("url");
                String source = article.getJSONObject("source").getString("name");
                String publishedAt = article.getString("publishedAt");
                String formattedPublishedAt = LocalDate.parse(publishedAt.substring(0, 10)).format(formatter);

                html.append("<div class=\"article\">");
                html.append("<a href=\"").append(urlToArticle).append("\" class=\"title\">").append(title).append("</a>");
                html.append("<div class=\"description\">").append(description).append("</div>");
                html.append("<div class=\"source\">").append(source).append("</div>");
                html.append("<div class=\"published-date\">").append(formattedPublishedAt).append("</div>");
                html.append("</div>");
            }
            html.append("    <footer>\n" +
                    "        <a>This News collected by </a><a href=\"https://www.uysi.org/ug\"> Uyghur Reserach Institude</a><br>\n" +
                    "        <a>Author of This Web Page: </a> <a href=\"https://github.com/UyCoder\">Ahmed Bughra</a><br>\n" +
                    "      </footer>");
            html.append("</body></html>");

            String fileName = "DailyNewsAbout" + keyWord + "-" + formattedFromDate + ".html";
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            Path path = Paths.get(desktopPath + fileName);
            Files.write(path, html.toString().getBytes());

            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void test() {
        getWeeklyNews("Uyghur");
        getTodaysNews("cybersecurity");
    }
}
