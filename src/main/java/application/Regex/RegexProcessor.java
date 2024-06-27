package application.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexProcessor {

    public String search(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();

        if (matcher.find()) {
            result.append("Found: ").append(matcher.group()).append("\n");
        }
        else{
            result.append("No Much found");
        }

        return result.toString();
    }

    public boolean match(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public String replace(String text, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(replacement);
    }
}
