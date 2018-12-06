package org.incode.eurocommercial.relatio.app.services.email;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;

@DomainService(nature = NatureOfService.DOMAIN)
public class EmailCleaningService {

    public static final String IT_TLD = ".it";
    public static final String COM_TLD = ".com";

    public String EmailCleaningService(final String input){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(input) ? input : clean(input);
    }

    public String clean(final String input){
        String cleanMail = input.toLowerCase();
        cleanMail = removeUnwantedCharacters(cleanMail);
        cleanMail = removeNonAlphaAtTheEnd(cleanMail);
        cleanMail = cleanLastCharacterIfNotAlphabet(cleanMail);
        cleanMail = addMissingTldIfKnownDomain(cleanMail);
        return cleanMail;
    }

    public String removeUnwantedCharacters(final String input){
        return input.replaceAll("[\",$\"(?)\" ]","");
    }

    // TODO perhaps replace instead this for checking duplicated unwanted chars.
    public String removeNonAlphaAtTheEnd(final String input){
        String punctuation = input;
        return (input.endsWith("\\.") || input.endsWith("0")) ? punctuation.substring(0, punctuation.length() - 1) : punctuation;
    }

    public String cleanLastCharacterIfNotAlphabet(final String input){
        String lastChar = input.substring(input.length()-1).replaceAll("[^a-z]", "");
        String email = input.substring(0, input.length()-1);
        return email.concat(lastChar);
    }

    // TODO add more cases to fix known TLDs
    public String fixKnownTLDIfIncomplete(final String input){
        String[] splitOnDot = input.split("\\.");
        String email = String.join(".", Arrays.copyOf(splitOnDot, splitOnDot.length - 1));
        switch (splitOnDot[splitOnDot.length-1]) {
            case "t":
                return email.concat(IT_TLD);
            case "i":
                return email.concat(IT_TLD);
            case "om":
                return email.concat(COM_TLD);
            case "iit":
                return email.concat(IT_TLD);
            case "co":
                return email.concat(COM_TLD);
            default:
                // If we want to keep incorrect email addresses:
                return input;
                // If we do not want to keep incorrect email addresses:
                // return null;
        }
    }

    public String addMissingTldIfKnownDomain(final String input){
        //check for not at symbol
        String substring = input.substring(input.lastIndexOf('@') + 1);
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
            default:
                // If we want to keep incorrect email addresses:
                return input;
                // If we do not want to keep incorrect email addresses:
                // return null;
        }
    }

    // TODO perhaps adding more mails.
    public String replaceDashForDot(final String input){
        String substring = input.substring(input.lastIndexOf('@') + 1);
        String head = input.substring(0, input.lastIndexOf('@') + 1);
        switch (substring) {
            case "hotmail-it":
                return head.concat("hotmail.it");
            default:
                // If we want to keep incorrect email addresses:
                return input;
                // If we do not want to keep incorrect email addresses:
                // return null;
        }
    }


    public String removeXAndQuestionMark(final String input){
        return input.replaceAll("x\\?","");
    }

    public String fixKnownmissingDotTLDIfIncomplete(final String input){
        String[] splitOnDot = input.split("\\?");
        String email = String.join(".", Arrays.copyOf(splitOnDot, splitOnDot.length - 1));
        switch (splitOnDot[splitOnDot.length-1]) {
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
