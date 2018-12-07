package org.incode.eurocommercial.relatio.app.services.email;

import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import java.util.Arrays;

@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "999")

public class EmailCleaningService {

    public static final String IT_TLD = ".it";
    public static final String COM_TLD = ".com";

    @Setter
    public String input;

    public String process(final String input) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(input) ? input : clean(input);
    }

    public String clean(final String input) {
        //If email is smaller then 6 chars then we can't service it.
        String cleanMail = input.toLowerCase();
        if (cleanMail.length() < 6) {
            return cleanMail;
        }

        cleanMail = addAtSumbolBasedOnTLDs(cleanMail);
        //If email doens't contain @ symbol we can't service it.
        if (!cleanMail.contains("@")) {
            return input;
        }

        cleanMail = removeDotsBeforeAt(cleanMail);
        cleanMail = removeDoubleDotsAfterAt(cleanMail);
        cleanMail = removeUnwantedCharacters(cleanMail);
        cleanMail = removeXAndQuestionMark(cleanMail);
        cleanMail = removeNonAlphaAtTheEnd(cleanMail);
        cleanMail = cleanLastCharacterIfNotAlphabet(cleanMail);
        cleanMail = fixKnownTLDIfIncomplete(cleanMail);
        cleanMail = addMissingTldIfKnownDomain(cleanMail);
        cleanMail = fixKnownmissingDotTLDIfIncomplete(cleanMail);
        return cleanMail;
    }

    public String addAtSumbolBasedOnTLDs(final String input) {
        if (input.contains("@")) {
            return input;
        }

        String[] domains = new String[] {"vodafone", "alice", "fastnetweb", "fastwebnet", "libero", "live", "icloud", "gmail", "hotmail", "bayer", "yahoo"};
        for (String tld : domains) {
            if (input.contains(tld)){
                String substring = input.substring(input.lastIndexOf(tld));
                String head = input.substring(0, input.lastIndexOf(tld));
                return head.concat("@".concat(substring));
            }
        }
        return input;
    }

    public String removeDotsBeforeAt(final String input) {
        return input.replaceAll("\\.+@", "@");
    }

    public String removeDoubleDotsAfterAt(final String input) {
        String substring = input.substring(input.lastIndexOf('@') + 1);
        String head = input.substring(0, input.lastIndexOf('@') + 1);
        return head.concat(substring.replaceAll("\\.{2,}", "."));
    }


    public String removeUnwantedCharacters(final String input) {
        return input.replaceAll("[\",$\"(?)\" ]", "");
    }

    public String removeXAndQuestionMark(final String input) {
        return input.replaceAll("x\\?", "");
    }

    public String removeNonAlphaAtTheEnd(final String input) {
        String punctuation = input;
        return (input.endsWith(".") || input.endsWith("0")) ? punctuation.substring(0, punctuation.length() - 1) : punctuation;
    }

    public String cleanLastCharacterIfNotAlphabet(final String input) {
        String lastChar = input.substring(input.length() - 1).replaceAll("[^a-z]", "");
        String email = input.substring(0, input.length() - 1);
        return email.concat(lastChar);
    }

    public String fixKnownTLDIfIncomplete(final String input) {
        String[] splitOnDot = input.split("\\.");
        String email = String.join(".", Arrays.copyOf(splitOnDot, splitOnDot.length - 1));
        switch (splitOnDot[splitOnDot.length - 1]) {
            case "t":
                return email.concat(IT_TLD);
            case "i":
                return email.concat(IT_TLD);
            case "iy":
                return email.concat(IT_TLD);
            case "ot":
                return email.concat(IT_TLD);
            case "om":
                return email.concat(COM_TLD);
            case "commmi":
                return email.concat(COM_TLD);
            case "iit":
                return email.concat(IT_TLD);
            case "co":
                return email.concat(COM_TLD);
            case "c":
                return email.concat(COM_TLD);
            case "von":
                return email.concat(COM_TLD);
            case "ckm":
                return email.concat(COM_TLD);
            case "con":
                return email.concat(COM_TLD);
            case "come":
                return email.concat(COM_TLD);
            case "on":
                return email.concat(COM_TLD);
            case "cim":
                return email.concat(COM_TLD);
            case "cin":
                return email.concat(COM_TLD);
            case "xcon":
                return email.concat(COM_TLD);
            default:
                // If we want to keep incorrect email addresses:
                return input;
            // If we do not want to keep incorrect email addresses:
            // return null;
        }
    }

    /*
        Not the nicest function, will replace john.it.hi@hotmail.it.com
        instead of making it john.it.hi@hotmail.it, very edge-casey.
     */
    public String cleanTLDsWithRegex(final String input){
        String[] TLDs = {COM_TLD, IT_TLD};
        String[] splitOnDot = input.split("\\.com|\\.it");

        String firstTLD = null;
        if(splitOnDot.length > 1){
            Integer tldIndex = Integer.MAX_VALUE;

            for (String tld : TLDs) {
                if(input.contains(tld) && input.indexOf(tld) < (tldIndex)){
                    tldIndex = input.indexOf(tld);
                    firstTLD = tld;
                }
            }
            return splitOnDot[0].concat(firstTLD);
        }
        return input;
    }

    public String addMissingTldIfKnownDomain(final String input) {
        //check for not at symbol
        String substring = input.substring(input.lastIndexOf('@') + 1);
        String head = input.substring(0, input.lastIndexOf('@') + 1);
        switch (substring) {
            case "vodafone":
                return input.concat(IT_TLD);
            case "alice":
                return input.concat(IT_TLD);
            case "fastnetweb":
                return input.concat(IT_TLD);
            case "fastwebnet":
                return input.concat(IT_TLD);
            case "libero":
                return input.concat(IT_TLD);
            case "live":
                return input.concat(COM_TLD);
            case "icloud":
                return input.concat(COM_TLD);
            case "gmail":
                return input.concat(COM_TLD);
            case "hotmail":
                return input.concat(COM_TLD);
            case "bayer":
                return input.concat(COM_TLD);
            case "yahoo":
                return input.concat(COM_TLD);
            case "gmailcom":
                return head.concat("gmail".concat(COM_TLD));
            case "gmail.":
                return head.concat("gmail".concat(COM_TLD));
            case "hotmailcom":
                return head.concat("hotmail".concat(COM_TLD));
            case "hotmailit":
                return head.concat("hotmail".concat(IT_TLD));
            case "yahoocom":
                return head.concat("yahoo".concat(COM_TLD));
            case "yahooit":
                return head.concat("yahoo".concat(IT_TLD));
            case "hotmail-it":
                return head.concat("hotmail".concat(IT_TLD));
            case "tiscaliit":
                return head.concat("tiscali".concat(IT_TLD));
            default:
                // If we want to keep incorrect email addresses:
                return input;
        }
    }

    public String fixKnownmissingDotTLDIfIncomplete(final String input) {
        String[] splitOnDot = input.split("\\?");
        String email = String.join(".", Arrays.copyOf(splitOnDot, splitOnDot.length - 1));
        switch (splitOnDot[splitOnDot.length - 1]) {
            case "it":
                return email.concat(IT_TLD);
            case "com":
                return email.concat(COM_TLD);
            default:
                // If we want to keep incorrect email addresses:
                return input;
            // If we do not want to keep incorrect email addresses:
            // return null;
        }
    }
}