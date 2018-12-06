package org.incode.eurocommercial.relatio.app.services.email;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailCleaningServiceTest {

    @Before
    public void setUp() {
        emailCleaningService = new EmailCleaningService();
    }

    private EmailCleaningService emailCleaningService;

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
        assertThat(withFixedTLD).isEqualTo("johndoe@hotmail.c");
    }

    @Test
    public void replaceDashForDot_happyCase(){
        //given
        String wrongDotInEmail = "johndoe@hotmail-it";

        //when
        String withFilterDashForDot = emailCleaningService.replaceDashForDot(wrongDotInEmail);

        //then
        assertThat(withFilterDashForDot).isEqualTo("johndoe@hotmail.it");
    }

    @Test
    public void replaceDashForDot_sadCase(){
        //given
        String wrongDotInEmail = "johndoe@hotmail--it";

        //when
        String withFilterDashForDot = emailCleaningService.replaceDashForDot(wrongDotInEmail);

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