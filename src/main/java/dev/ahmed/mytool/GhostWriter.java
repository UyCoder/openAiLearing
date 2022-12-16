package dev.ahmed.mytool;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ahmed Bughra
 * @create 2022-12-16  12:06 AM
 *
 * lzkrfZwubDsmj8pP4F3NT3BlbkFJm4N1zN9SYlefIdXvIgIi
 */
public class GhostWriter {
    public static void main(String... args) {
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token);
        Engine davinci = service.getEngine("davinci");
        ArrayList<CompletionChoice> storyArray = new ArrayList<CompletionChoice>();

        // My code begins here:
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the sentence tha you want to complete: \n");
        String prompt = scanner.nextLine();

        // dont touch the code below !!!
        System.out.println("\nBrewing up a story...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .temperature(0.7)
                .maxTokens(300)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.3)
                .echo(true)
                .build();
        service.createCompletion("davinci", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
//        System.out.println(storyArray);
        System.out.println(storyArray.get(0).getText());

        scanner.close();
    }
}
