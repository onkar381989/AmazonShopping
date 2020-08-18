package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;

public class JsonUtility {

    public static JSONObject createJsonObject4mFile(String filePath) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        String aLine = null;
        JSONObject jsonObject = null;

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            stringBuilder = new StringBuilder();
            aLine = bufferedReader.readLine();

            while (aLine != null) {
                stringBuilder.append(aLine);
                aLine = bufferedReader.readLine();
            }
            if (stringBuilder == null)
                stringBuilder = new StringBuilder("");

            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (Exception e) {
            //TODO: replace SOP with logging call
            System.out.println("JsonUtility Class, getJsonObject() method, Error=" + e);
        } finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    public static JSONObject createJsonObject4mString(String jsonText) {
        return new JSONObject(jsonText);
    }

}
