package com.eCommerce.eCommerceApp.Campaign;

import com.eCommerce.eCommerceApp.entity.Campaign;
import com.eCommerce.eCommerceApp.repository.CampaignRepostiroy;
import com.eCommerce.eCommerceApp.service.CampaignService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
public class CampaignTest {
    @Autowired
    CampaignService campaignService;

    @Mock
    private CampaignRepostiroy campaignRepostiroy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testUpdateCampaignAtSpecificTime() {
        List<Campaign> campaigns = new ArrayList<>();

        when(campaignRepostiroy.findByNewCostIdIsNotNull()).thenReturn(campaigns);

        List<Campaign> oldListCampaings = campaigns;
        // Call the method to be tested
        campaignService.updatecampaignAtSpecificTime();
        List<Campaign> newListCampaings = campaigns;

        // Add assertions to verify the behavior if needed
    }
}
