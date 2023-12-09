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
//        String text = "I have a party on the 19th.";
//        List<Entity> data = createJSON(text);
//
//        String event = null;
//        String location = null;
//        String year = "2023";
//        String month = null;
//        String day = null;
//        String time = null;
//        for (Entity entity : data) {
//            for (EntityMention mention : entity.getMentionsList()) {
//                if (entity.getType().toString().equals("EVENT") || entity.getType().toString().equals("ORGANIZATION") || entity.getType().toString().equals("OTHER")) {
//                    event = mention.getText().getContent();
//                }
//                if (entity.getType().toString().equals("LOCATION")) {
//                    location = mention.getText().getContent();
//                }
//                if (entity.getType().toString().equals("DATE")) {
//                    time = mention.getText().getContent();
//                    if (entity.getMetadataMap().containsKey("year")) {
//                        year = entity.getMetadataMap().get("year");
//                        time = time.replaceFirst(year, "");
//                        time = time.replaceFirst("년", "");
//                    }
//                    if (entity.getMetadataMap().containsKey("month")) {
//                        month = entity.getMetadataMap().get("month");
//                        time = time.replaceFirst(month, "");
//                        time = time.replaceFirst("월", "");
//                        time = time.replaceFirst("January", "");
//                        time = time.replaceFirst("Jan", "");
//                        time = time.replaceFirst("February", "");
//                        time = time.replaceFirst("Feb", "");
//                        time = time.replaceFirst("March", "");
//                        time = time.replaceFirst("Mar", "");
//                        time = time.replaceFirst("April", "");
//                        time = time.replaceFirst("Apr", "");
//                        time = time.replaceFirst("May", "");
//                        time = time.replaceFirst("May", "");
//                        time = time.replaceFirst("June", "");
//                        time = time.replaceFirst("Jun", "");
//                        time = time.replaceFirst("July", "");
//                        time = time.replaceFirst("Jul", "");
//                        time = time.replaceFirst("August", "");
//                        time = time.replaceFirst("Aug", "");
//                        time = time.replaceFirst("September", "");
//                        time = time.replaceFirst("Sep", "");
//                        time = time.replaceFirst("October", "");
//                        time = time.replaceFirst("Oct", "");
//                        time = time.replaceFirst("November", "");
//                        time = time.replaceFirst("Nov", "");
//                        time = time.replaceFirst("December", "");
//                        time = time.replaceFirst("Dec", "");
//                    }
//                    if (entity.getMetadataMap().containsKey("day")) {
//                        day = entity.getMetadataMap().get("day");
//                        time = time.replaceFirst(day, "");
//                        time = time.replaceFirst("일", "");
//                    }
//                    time = time.replaceFirst("at", "");
//                    time = time.replaceFirst("am", "");
//                    time = time.replaceFirst("pm", "");
//                    time = time.replaceFirst("'o Clock", "");
//                    time = time.replaceAll("\\.", "");
//                    time = time.replaceAll(",", "");
//                    time = time.trim();
//                    if (time.equals(" ") || time.equals("")) {
//                        time = null;
//                    }
//                }
//            }
//        }
//        System.out.println("EVENT: " + event);
//        System.out.println("LOCATION: " + location);
//        System.out.println("YEAR: " + year);
//        System.out.println("MONTH: " + month);
//        System.out.println("DAY: " + day);
//        System.out.println("TIME: " + time);
//    }

}
