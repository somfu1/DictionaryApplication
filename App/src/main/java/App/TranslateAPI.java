package App;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

// Class to interact with AWS Translate API
public class TranslateAPI {
    private final TranslateClient awsTranslateClient; // AWS Translate client

    // Constructor to initialize the AWS Translate client
    public TranslateAPI() {
        this.awsTranslateClient = TranslateClient.builder()
                .region(Region.AP_SOUTHEAST_2) // Set your desired AWS region
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    // Method to translate text from source language to target language
    public String translateText(String inputText, String sourceLanguage, String targetLanguage) {
        try {
            // Build the translation request
            TranslateTextRequest translationRequest = TranslateTextRequest.builder()
                    .text(inputText)
                    .sourceLanguageCode(sourceLanguage)
                    .targetLanguageCode(targetLanguage)
                    .build();

            // Get the translation response
            TranslateTextResponse translationResponse = awsTranslateClient.translateText(translationRequest);
            return translationResponse.translatedText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Translation Error";
        }
    }
}
