package utility;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

public class TestData {

    private static HashMap<String,JSONObject> hashMap = new HashMap<>();

    @DataProvider(name = "testdata")
    public Object[][] getTestData(Method m){
        return new Object[][]{{getInstance(m)}};
    }

    private JSONObject getInstance(Method m){
        if (hashMap.get(m.getDeclaringClass().getSimpleName())==null)
            initialiseTestData(m);
        return hashMap.get(m.getDeclaringClass().getSimpleName()).getJSONObject(m.getName());
    }

    private void initialiseTestData(Method m){
        String testDataDir = getActualDir(m.getDeclaringClass().getCanonicalName());
        hashMap.put(m.getDeclaringClass().getSimpleName(), JsonUtility.createJsonObject4mFile(testDataDir+".json"));
    }

    private String getActualDir(String dir){
        String[] dirs =dir.split("\\.");
        return GlobalVariables.TESTDATAFILEPATH+File.separator+dirs[dirs.length-1];
    }

    public static void removeTestData(String className){
        hashMap.remove(className);
    }

}