package org.incode.eurocommercial.relatio.app.services.email;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailCleaningServiceTest {

    private EmailCleaningService emailCleaningService;

    @Before
    public void setUp() {
        emailCleaningService = new EmailCleaningService(null);
    }


    @Test
    public void addAtSumbolBasedOnTLDs_happyCase(){
        //given
        String withoutAtSymbol = "johndoeyahoo.com";

        //when
        String withAtSymbol = emailCleaningService.addAtSumbolBasedOnTLDs(withoutAtSymbol);

        //then
        assertThat(withAtSymbol).isEqualTo("johndoe@yahoo.com");

    }

    @Test
    public void addAtSumbolBasedOnTLDsNoPeriod_happyCase(){
        //given
        String withoutAtSymbol = "johndoegmailcom";

        //when
        String withAtSymbol = emailCleaningService.addAtSumbolBasedOnTLDs(withoutAtSymbol);

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
    public void removeNonAlphaAtTheEnd_SadCase(){
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
    public void cleanLastCharacters_SadCase(){
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
    public void fixKnownTLDIfIncomplete_SadCase(){
        //given
        String incompleteTLDEmail = "johndoe@hotmail.c";

        //when
        String withFixedTLD = emailCleaningService.fixKnownTLDIfIncomplete(incompleteTLDEmail);

        //then
        assertThat(withFixedTLD).isEqualTo("johndoe@hotmail.com");
    }

    @Test
    public void addMissingTldIfKnownDomain_happyCaseDash(){
        //given
        String wrongDotInEmail = "johndoe@hotmail-it";

        //when
        String withFilterDashForDot = emailCleaningService.addMissingTldIfKnownDomain(wrongDotInEmail);

        //then
        assertThat(withFilterDashForDot).isEqualTo("johndoe@hotmail.it");
    }

    @Test
    public void addMissingTldIfKnownDomain_sadCase(){
        //given
        String wrongDotInEmail = "johndoe@hotmail--it";

        //when
        String withFilterDashForDot = emailCleaningService.addMissingTldIfKnownDomain(wrongDotInEmail);

        //then
        assertThat(withFilterDashForDot).isEqualTo("johndoe@hotmail--it");
    }

    @Test
    public void addMissingSubDomainIfKnownDomain_happyCase() {
        // given
        String withoutTld = "johndoe@bayer";

        // when
        String withTld = emailCleaningService.addMissingTldIfKnownDomain(withoutTld);

        // then
        assertThat(withTld).isEqualTo("johndoe@bayer.com");
    }

    @Test
    public void  addMissingSubDomainIfKnownDomain_sadCase_unknownDomain() {
        // given
        String unknownDomain = "johndoe@fakedomain";

        // when
        String withTld = emailCleaningService.addMissingTldIfKnownDomain(unknownDomain);

        // then
        assertThat(withTld).isEqualTo(unknownDomain);
    }


    @Test
    public void  addMissingSubDomainIfKnownDomain_happyCase_alreadyContainsTld() {
        // given
        String withTld = "johndoe@bayer.com";

        // when
        String withTldAdded = emailCleaningService.addMissingTldIfKnownDomain(withTld);

        // then
        assertThat(withTldAdded).isEqualTo(withTld);
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
    public void fixKnownmissingDotTLDIfIncomplete_happyCase(){
        //given
        String incompleteTLDEmail = "johndoe@gmail?com";

        //when
        String withFixedTLD = emailCleaningService.fixKnownmissingDotTLDIfIncomplete(incompleteTLDEmail);

        //then
        assertThat(withFixedTLD).isEqualTo("johndoe@gmail.com");
    }


}