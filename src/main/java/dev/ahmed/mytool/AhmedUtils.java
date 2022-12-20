package dev.ahmed.mytool;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import okhttp3.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Bughra
 * @create 2022-12-16  1:48 PM
 */
public class AhmedUtils {
    // Use this methid to call openAi service
    public static String callOpenAi(String prompt,
                                    String token ,
                                    String fileType,
                                    String fromLanguage,
                                    String targetLanguage
                                    ) throws IOException, InterruptedException, NoSuchFieldException {
        OpenAiService service = new OpenAiService(token);
        Engine davinci = service.getEngine("davinci");
        ArrayList<CompletionChoice> storyArray = new ArrayList<CompletionChoice>();
        System.out.println("\nجاۋاپنى ئويلىنىۋاتىمەن...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .temperature(0.7)
                .maxTokens(200)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.3)
                .echo(true)
                .build();
        service.createCompletion("davinci", completionRequest).getChoices().forEach(line -> {
            storyArray.add(line);
        });

        // build the text for saved file
        String text = "\n" + "This is an OpenAI chat history on " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) + ".\n"
                + "====================================== \n"
                + storyArray.get(0).toString() + ".\n"
                + "====================================== \n"
//                + "Translation to " + targetLanguage + ": \n"
                + "-------------------------------------- \n "
//                + transalteFromLtVernCc(storyArray.get(0).getText(), fromLanguage, targetLanguage)
                + "Translation to Uyghur : \n"
                + "-------------------------------------- \n "
                + translateGoogle(removeBlankLines(storyArray.get(0).getText()), fromLanguage, "ug"); //

        if(fileType=="doc") {

            // save the record as .docx file on desktop
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(text);
            FileOutputStream out = new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\OpenAiHistory" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hh_mm_ss")) + ".docx");
            document.write(out);
            out.close();
        } if (fileType=="txt"){

            FileWriter fileWriter = new FileWriter(System.getProperty("user.home")+"\\Desktop\\OpenAiHistory.txt", true);
            fileWriter.write(text);
            fileWriter.close();
        }

        //  System.out.println(storyArray);
        return storyArray.get(0).getText();
    }


    public static String transalteFromLtVernCc(String text,
                                               String fromLanguage,
                                               String targetLangage) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("q", text)
                .add("source", fromLanguage)
                .add("target", targetLangage)
                .add("format", "text")
                .add("api_key","")
                .build();

        Request request = new Request.Builder()
                .url("https://lt.vern.cc/translate")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        result = String.format("%s", result);
        System.out.println(result);
        return result;
    }


    /////-===================================================
    public static String translateGoogle1(String text,
                                 String sourceLang,
                                 String targetLang) {
        try {
            // Build the URL for the translation service
            String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" +
                    sourceLang + "&tl=" + targetLang + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8")+ "&ie=UTF-8&oe=UTF-8";
            URL url = new URL(urlStr);
            // Make the HTTP request to the translation service
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();

            // Parse the response to extract the translated text
            String[] parts = result.split("\"");
            String translatedText = parts[1];

            // Use the translated text as needed in your application
            System.out.println("Translated text with google: " + translatedText);
            return translatedText;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



// this method is better for free google translation
    public static String translateGoogle(String text,
                                         String sourceLang,
                                         String targetLang) {
        try {
            // Build the URL for the translation service
            String urlStr = "https://clients5.google.com/translate_a/t?client=dict-chrome-ex&sl=" +
                    sourceLang + "&tl=" + targetLang + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
            URL url = new URL(urlStr);
            // Make the HTTP request to the translation service
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();

            // Parse the response to extract the translated text
            String[] parts = result.split("\"");
            String translatedText = parts[1];

            // Use the translated text as needed in your application
            System.out.println("Translated text with google: " + translatedText);
            return translatedText;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String removeBlankLines(String input) {
        // Split the input string into an array of lines
        String[] lines = input.split("\r?\n");
        // Add non-blank lines to a new list
        List<String> nonBlankLines = new ArrayList<>();
        for (String line : lines) {
            if (!line.isBlank()) {
                nonBlankLines.add(line);
            }
        }
        // Concatenate the non-blank lines into a single string
        return String.join("\n", nonBlankLines);
    }


//    // use this method to send gmail
//    public static void sendGmail(String recipientsEmail){
//
//        // Set up the email server properties
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        // Set up the email authentication
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication("ahmedg47262582@gmail.com", "EMAIL_PASS");
//                    }
//                });
//
//        try {
//            // Create a new email message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("ahmedg47262582@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientsEmail));
//            message.setSubject("سىناق ئىلخەت");
//            message.setText("ئەسسالام ئەلەيكۇم، قانداق ئەھۋالىڭ؟ بۇ ئېلخەتنى مەن يازغان جاۋا كودتىن يوللاۋاتىمەن.");
//
//            // Send the email
//            Transport.send(message);
//
//            System.out.println("Email sent successfully.");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }


}