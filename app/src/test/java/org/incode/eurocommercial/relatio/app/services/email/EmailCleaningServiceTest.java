package org.incode.eurocommercial.relatio.app.services.email;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailCleaningServiceTest {

    private EmailCleaningService emailCleaningService;

    @Before
    public void setUp() {
        emailCleaningService = new EmailCleaningService();
    }


    @Test
    public void addAtSymbolBasedOnTLDs_happyCase(){
        //given
        String withoutAtSymbol = "johndoeyahoo.com";

        //when
        String withAtSymbol = emailCleaningService.addAtSymbolBasedOnTLDs(withoutAtSymbol);

        //then
        assertThat(withAtSymbol).isEqualTo("johndoe@yahoo.com");

    }

    @Test
    public void addAtSymbolBasedOnTLDsNoPeriod_happyCase(){
        //given
        String withoutAtSymbol = "johndoegmailcom";

        //when
        String withAtSymbol = emailCleaningService.addAtSymbolBasedOnTLDs(withoutAtSymbol);

        //then
        assertThat(withAtSymbol).isEqualTo("johndoe@gmailcom");

    }

    @Test
    public void removeDotsBeforeAt_happyCase(){
        //given
        String emailWithSymbolsBeforeAt = "johndoe989!....@hotmail.com";

        //when
        String emailWithoutSymbolsBeforeAt = emailCleaningService.removeDotsBeforeAt(emailWithSymbolsBeforeAt);

        //then
        assertThat(emailWithoutSymbolsBeforeAt).isEqualTo("johndoe989!@hotmail.com");
    }

    @Test
    public void cleanTLDsWithRegex_happyCase(){
        //given
        String emailWithDoubleDomains = "johndoe@hotmail.com@ormai.com";

        //when
        String emailWithOneTLD = emailCleaningService.cleanTLDsWithRegex(emailWithDoubleDomains);

        //then
        assertThat(emailWithOneTLD).isEqualTo("johndoe@hotmail.com");
    }

    @Test
    public void cleanTLDsWithRegex_sadCase(){
        //given
        String emailWithDoubleDomains = "johndoeitaly@hotmail.it@ormai.com";

        //when
        String emailWithOneTLD = emailCleaningService.cleanTLDsWithRegex(emailWithDoubleDomains);

        //then
        assertThat(emailWithOneTLD).isEqualTo("johndoeitaly@hotmail.it");
    }

    @Test
    public void removeUnwantedCharacters_happyCase(){
        //given
        String unwantedCharsEmail = "johndoe? @hotmail.com";

        //when
        String withFilteredChars = emailCleaningService.removeUnwantedCharacters(unwantedCharsEmail);

        //then
        assertThat(withFilteredChars).isEqualTo("johndoe@hotmail.com");
    }

    @Test
    public void removeNonAlphaAtTheEnd_happyCase(){
        //given
        String unwantedEndEmail = "johndoe@hotmail.com.";

        //when
        String withFilteredChars = emailCleaningService.removeNonAlphaAtTheEnd(unwantedEndEmail);

        //then
        assertThat(withFilteredChars).isEqualTo("johndoe@hotmail.com");
    }

    @Test
    public void removeNonAlphaAtTheEnd_sadCase(){
        //given
        String unwantedEndEmail = "johndoe@hotmail.com..";

        //when
        String withFilteredChars = emailCleaningService.removeNonAlphaAtTheEnd(unwantedEndEmail);

        //then
        assertThat(withFilteredChars).isEqualTo("johndoe@hotmail.com.");
    }


    @Test
    public void cleanLastCharacters_happyCase(){
        //given
        String unwantedEndEmail = "johndoe@hotmail.com.";

        //when
        String withFilteredChars = emailCleaningService.cleanLastCharacterIfNotAlphabet(unwantedEndEmail);

        //then
        assertThat(withFilteredChars).isEqualTo("johndoe@hotmail.com");
    }

    @Test
    public void cleanLastCharacters_sadCase(){
        //given
        String unwantedEndEmail = "johndoe@hotmail.com..";

        //when
        String withFilteredChars = emailCleaningService.cleanLastCharacterIfNotAlphabet(unwantedEndEmail);

        //then
        assertThat(withFilteredChars).isEqualTo("johndoe@hotmail.com.");
    }

    @Test
    public void removeDoubleDotsAfterAt_happyCase(){
        //given
        String unwantedDoubleDots = "josetroya1020@hotmail..com";

        //when
        String fixedDoubleDots = emailCleaningService.removeDoubleDotsAfterAt(unwantedDoubleDots);

        assertThat(fixedDoubleDots).isEqualTo("josetroya1020@hotmail.com");

    }

    @Test
    public void fixKnownTLDIfIncomplete_happyCase(){
        //given
        String incompleteTLDEmail = "johndoe@hotmail.iit";

        //when
        String withFixedTLD = emailCleaningService.fixKnownTLDIfIncomplete(incompleteTLDEmail);

        //then
        assertThat(withFixedTLD).isEqualTo("johndoe@hotmail.it");
    }

    @Test
    public void fixKnownTLDIfIncomplete_sadCase(){
        //given
        String incompleteTLDEmail = "johndoe@hotmail.c";

        //when
        String withFixedTLD = emailCleaningService.fixKnownTLDIfIncomplete(incompleteTLDEmail);

        //then
        assertThat(withFixedTLD).isEqualTo("johndoe@hotmail.com");
    }

    @Test
    public void addMissingTLDIfKnownDomain_happyCase_dash(){
        //given
        String wrongDotInEmail = "johndoe@hotmail-it";

        //when
        String withFilterDashForDot = emailCleaningService.addMissingTLDIfKnownDomain(wrongDotInEmail);

        //then
        assertThat(withFilterDashForDot).isEqualTo("johndoe@hotmail.it");
    }

    @Test
    public void addMissingTLDIfKnownDomain_sadCase(){
        //given
        String wrongDotInEmail = "johndoe@hotmail--it";

        //when
        String withFilterDashForDot = emailCleaningService.addMissingTLDIfKnownDomain(wrongDotInEmail);

        //then
        assertThat(withFilterDashForDot).isEqualTo("johndoe@hotmail--it");
    }

    @Test
    public void addMissingSubDomainIfKnownDomain_happyCase() {
        // given
        String withoutTLD = "johndoe@bayer";

        // when
        String withTLD = emailCleaningService.addMissingTLDIfKnownDomain(withoutTLD);

        // then
        assertThat(withTLD).isEqualTo("johndoe@bayer.com");
    }

    @Test
    public void  addMissingSubDomainIfKnownDomain_sadCase_unknownDomain() {
        // given
        String unknownDomain = "johndoe@fakedomain";

        // when
        String withTLD = emailCleaningService.addMissingTLDIfKnownDomain(unknownDomain);

        // then
        assertThat(withTLD).isEqualTo(unknownDomain);
    }


    @Test
    public void addMissingSubDomainIfKnownDomain_happyCase_alreadyContainsTLD() {
        // given
        String withTLD = "johndoe@bayer.com";

        // when
        String withTLDAdded = emailCleaningService.addMissingTLDIfKnownDomain(withTLD);

        // then
        assertThat(withTLDAdded).isEqualTo(withTLD);
    }

    @Test
    public void removeXAndQuestionMark_happyCase(){
        //given
        String unwantedXInEmail = "xjohndoex?@hotmail.com";

        //when
        String withFilteredChars = emailCleaningService.removeXAndQuestionMark(unwantedXInEmail);

        //then
        assertThat(withFilteredChars).isEqualTo("xjohndoe@hotmail.com");
    }

    @Test
    public void fixKnownMissingDotTLDIfIncomplete_happyCase(){
        //given
        String incompleteTLDEmail = "johndoe@gmail?com";

        //when
        String withFixedTLD = emailCleaningService.fixKnownMissingDotTLDIfIncomplete(incompleteTLDEmail);

        //then
        assertThat(withFixedTLD).isEqualTo("johndoe@gmail.com");
    }


}