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

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
        System.out.println("\nBrewing up a story...");
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


        String text = "\n" + "This is an OpenAI chat history on " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) + ".\n"
                + "====================================== \n"
                + storyArray.get(0).toString() + ".\n"
                + "====================================== \n"
                + "Translation to " + targetLanguage + ": \n"
                + "-------------------------------------- \n " +
                transalteFromLtVernCc(storyArray.get(0).getText(), fromLanguage, targetLanguage); //

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
            fileWriter.write(storyArray.get(0).toString()+text);
            fileWriter.close();
        }

        //  System.out.println(storyArray);
        return storyArray.get(0).getText();
    }


    public static String transalteFromLtVernCc(String text,
                                               String fromLanguage,
                                               String targetLangage) throws IOException, InterruptedException, NoSuchFieldException {
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


    @Test
    public void test() throws IOException, InterruptedException, NoSuchFieldException {
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