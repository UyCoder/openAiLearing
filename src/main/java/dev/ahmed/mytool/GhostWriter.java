package dev.ahmed.mytool;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ahmed Bughra
 * @create 2022-12-16  12:06 AM
 *
 * lzkrfZwubDsmj8pP4F3NT3BlbkFJm4N1zN9SYlefIdXvIgIi
 */
public class GhostWriter {
    public static void main(String... args) throws RuntimeException, IOException, InterruptedException, NoSuchFieldException {
        String token = System.getenv("OPENAI_TOKEN");
        Scanner scanner = new Scanner(System.in);
        System.out.println("مەندىن خالىغان نەرسە سورىسىڭىز بولىدۇ:: \n");
        String promptUg = scanner.nextLine();

        do {
            String prompt = AhmedUtils.translateGoogle(promptUg, "ug", "en");
            System.out.println(AhmedUtils.callOpenAi(prompt, token,
                    "txt",
                    "en",
                    "tr"));
            System.out.println("\n  مەندىن يەنە داۋاملىق سۇئال سورىسىڭىز بولىدۇ \n " +
                    "ئەگەر چېكىنمەكچى بولسىڭىز EXIT دەپ يېزىڭ");
            promptUg = scanner.nextLine();
        } while (!promptUg.equals("EXIT"));


        System.out.println("ئىشلەتكىنىڭىزگە كۆپ رەھمەت!");
        scanner.close();

        // sen email.
//        AhmedUtils.sendGmail("ahmedbughra@gmail.com");
    }
}
