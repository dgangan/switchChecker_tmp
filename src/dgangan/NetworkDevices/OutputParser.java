package dgangan.NetworkDevices;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputParser {

    public static LinkedHashMap<String, HashMap<String, String>> parse(String rawOutput, String patternString, String[] namedGroups){
        Pattern pattern = Pattern.compile(patternString);
        LinkedHashMap<String, HashMap<String, String>> parsedOut = new LinkedHashMap<>();

        for (String line : rawOutput.split("\n")){
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            try{
                LinkedHashMap<String, String> portData = new LinkedHashMap<>();
                for (int i = 1; i < namedGroups.length; i++){
                    portData.put(namedGroups[i], matcher.group(namedGroups[i] +""));
                }
                parsedOut.put(matcher.group(namedGroups[0]), portData);
                }
            catch(Exception e) {
                //e.printStackTrace();
            }
        }
        return parsedOut;
    }
}

