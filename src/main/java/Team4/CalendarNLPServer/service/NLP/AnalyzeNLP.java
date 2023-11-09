package Team4.CalendarNLPServer.service.NLP;

import com.google.cloud.language.v2.AnalyzeEntitiesRequest;
import com.google.cloud.language.v2.AnalyzeEntitiesResponse;
import com.google.cloud.language.v2.Document;
import com.google.cloud.language.v2.Document.Type;
import com.google.cloud.language.v2.EncodingType;
import com.google.cloud.language.v2.Entity;
import com.google.cloud.language.v2.EntityMention;
import com.google.cloud.language.v2.LanguageServiceClient;
import java.util.Map;

public class AnalyzeNLP {

    public static void analyzeEntitiesText(String text) throws Exception {

        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            AnalyzeEntitiesRequest request =
                    AnalyzeEntitiesRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();

            AnalyzeEntitiesResponse response = language.analyzeEntities(request);

            for (Entity entity : response.getEntitiesList()) {
                System.out.printf("Entity: %s", entity.getName());
                System.out.println("Metadata: ");
                for (Map.Entry<String, String> entry : entity.getMetadataMap().entrySet()) {
                    System.out.printf("%s : %s", entry.getKey(), entry.getValue());
                }
                for (EntityMention mention : entity.getMentionsList()) {
                    System.out.printf("Begin offset: %d\n", mention.getText().getBeginOffset());
                    System.out.printf("Content: %s\n", mention.getText().getContent());
                    System.out.printf("Type: %s\n\n", mention.getType());
                    System.out.printf("Probability: %s\n\n", mention.getProbability());
                }
            }
        }
    }
}