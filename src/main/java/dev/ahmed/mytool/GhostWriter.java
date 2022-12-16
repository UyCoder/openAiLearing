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
        String prompt;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the sentence tha you want to complete: \n");
        prompt = scanner.nextLine();
        do {
            System.out.println(AhmedUtils.callOpenAi(prompt, token,
                    "txt",
                    "en",
                    "tr"));
            System.out.println("\n You can insert another sentence. \n " +
                    "But if you want to logout, Just enter EXIT.");
            prompt = scanner.nextLine();
        } while (!prompt.equals("EXIT"));
        System.out.println("Thanks for using.");
        scanner.close();

        // sen email.
//        AhmedUtils.sendGmail("ahmedbughra@gmail.com");
    }
}
