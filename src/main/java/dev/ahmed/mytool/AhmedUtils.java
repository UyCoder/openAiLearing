package dev.ahmed.mytool;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;

import java.util.ArrayList;

/**
 * @author Ahmed Bughra
 * @create 2022-12-16  1:48 PM
 */
public class AhmedUtils {
    public static String callOpenAi(String prompt, String token) {
        OpenAiService service = new OpenAiService(token);
        Engine davinci = service.getEngine("davinci");
        ArrayList<CompletionChoice> storyArray = new ArrayList<CompletionChoice>();
        System.out.println("\nBrewing up a story...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .temperature(0.7)
                .maxTokens(99)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.3)
                .echo(true)
                .build();
        service.createCompletion("davinci", completionRequest).getChoices().forEach(line -> {
            storyArray.add(line);
        });
//        System.out.println(storyArray);

        return storyArray.get(0).getText();
    }
}
