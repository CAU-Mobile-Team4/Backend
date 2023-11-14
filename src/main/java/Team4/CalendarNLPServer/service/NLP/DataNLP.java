package Team4.CalendarNLPServer.service.NLP;


import com.google.cloud.language.v1beta2.Document;
import com.google.cloud.language.v1beta2.Entity;
import com.google.cloud.language.v1beta2.EntityMention;
import com.google.cloud.language.v1beta2.LanguageServiceClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataNLP {

    public List<Entity> createJSON(String text) throws Exception {

        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            List<Entity> entitiesList = language.analyzeEntities(doc).getEntitiesList();

            return entitiesList.stream().toList();
        }
    }

//    public static void main(String... args) throws Exception {
//        String text = "11월 13일 오전 10시에 강남역에서 회의가 있어.";
//        try (LanguageServiceClient language = LanguageServiceClient.create()) {
//            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
//            List<Entity> entitiesList = language.analyzeEntities(doc).getEntitiesList();
//
//            for (Entity entity : entitiesList) {
//                for (EntityMention mention : entity.getMentionsList()) {
//                    System.out.println(entity.getType());
//                    System.out.println("Day: " + entity.getMetadataMap().get("day"));
//                    System.out.println("Month: " + entity.getMetadataMap().get("month"));
//                    System.out.println(mention.getText().getContent());
//                    System.out.println();
//                }
//            }
//        }
//    }

}
