package com.blurryface.analyzer.controller;

import com.blurryface.analyzer.external.service.ShiprocketService;
import com.blurryface.analyzer.service.CsvParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class Analyzer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShiprocketService shiprocketService;

    @Autowired
    private CsvParser csvParser;

//    @RequestMapping(method = RequestMethod.GET, path = "/parse", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> parseCsv() throws Exception {
////        csvParser.readLineByLine(Path.of("/Users/pawankumar/Downloads/v2024.05.02.02.csv"));
//        return new ResponseEntity<>("Hi! I'm Parent Service", HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/temp", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> temp(HttpServletRequest servletRequest) throws Exception {
//        return new ResponseEntity<>(shiprocketService.getMarketingEnabledChannelDetails(servletRequest.getHeader("temp")), HttpStatus.OK);
//    }

    @RequestMapping(method = RequestMethod.GET, path = "/populate-org", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> populateOrg() throws Exception {
        csvParser.populateOrg(Path.of("/Users/pawankumar/Desktop/EngageMigrationData/RemainderActiveOrganization.csv"));
        return new ResponseEntity<>("Org populated successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/populate-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> populateUsers() throws Exception {
        csvParser.populateUser(Path.of("/Users/pawankumar/Desktop/EngageMigrationData/RemainderActiveOrgUsers.csv"));
        return new ResponseEntity<>("Users populated successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/map-main-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> mapMainUser() throws Exception {
        csvParser.mapMainUser(Path.of("/Users/pawankumar/Desktop/EngageMigrationData/KAM-ActiveOrgMainUsers-29-05-2024.csv"));
        return new ResponseEntity<>("Main Users mapped successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> email() throws Exception {

//        List<Integer>  activeOrgs = List.of(6654,6864,7178,7493,7633,7909,8168,8942,9220,9284,9341,9605,9804,9919,9979,10073,10110,10279,10376,10381,10410,10512,10610,10621,10630,10765,10819,10840,10901,10919,11035,11079,11187,11201,11210,11245,11247,11303,11382,11464,11518,11626,11635,11800,11810,11835,11862,11904,11914,11979,11998,12070,12160,12200,12202,12294,12315,12370,12560,12640,12657,12697,12732,12742,12767,12788,12799,12814,12820,12863,12868,12878,12909,12920,12940,13024,13044,13153,13333,13334,13361,13384,13395,13405,13458,13472,13493,13503,13558,13563,13590,13670,13706,13749,13753,13763,13776,13787,13788,13791,13792,13805,13864,13870,13872,14002,14051,14107,14134,14197,14231,14238,14269,14282,14304,14307,14318,14387,14393,14413,14427,14469,14479,14484,14492,14495,14500,14534,14541,14542,14551,14566,14571,14574,14583,14584,14592,14593,14594,14599,14601,14602,14605,14609,14611,14613,14614,14618,14625,14630,14632,14638,14639,14640,14641,14645,14657,14668,14669,14675,14694,14700,14702,14704,14732,14739,14743,14745,14749,14767,14771,14777,14793,14798,14806,14818,14821);
        List<Integer> mainUserOrgs = new ArrayList<>(List.of(6654,6864,7178,7493,7633,7909,8942,9220,9341,9605,9804,9919,9979,10073,10279,10376,10381,10410,10512,10610,10621,10630,10765,10901,10919,11035,11079,11187,11201,11210,11245,11303,11382,11626,11635,11800,11810,11835,11904,11914,11979,11998,12070,12160,12200,12202,12294,12315,12370,12560,12640,12657,12697,12732,12742,12788,12799,12820,12863,12868,12909,12920,12940,13024,13044,13333,13334,13361,13384,13395,13405,13458,13472,13493,13503,13558,13590,13670,13706,13749,13753,13763,13776,13787,13788,13791,13792,13805,13864,13872,14002,14107,14134,14197,14231,14238,14269,14282,14304,14307,14318,14393,14427,14469,14484,14495,14542,14551,14583,14584,14592,14594,14599,14601,14602,14611,14613,14618,14625,14630,14632,14638,14640,14645,14657,14668,14669,14675,14700,14702,14732,14739,14743,14745,14749,14767,14771,14777,14793,14798,14806,14821));
//        List<Integer> temp1 = new ArrayList<>(activeOrgs);
//        List<Integer> temp2 = new ArrayList<>(mainUserOrgs);
        List<Integer> gatheredOrgs = new ArrayList<>(List.of(6864,7178,7633,8942,9341,9605,9979,10073,10512,10610,10630,10765,10901,10919,11035,11079,11201,11210,11303,11626,11626,11810,11835,11904,11914,11979,12070,12160,12200,12202,12294,12315,12560,12640,12697,12742,12788,12863,12868,12909,12920,12940,13024,13044,13333,13334,13395,13405,13458,13493,13558,13590,13670,13706,13753,13763,13787,13791,13805,13864,13872,14002,14107,14134,14197,14231,14238,14269,14282,14304,14307,14318,14427,14469,14484,14542,14551,14583,14584,14592,14594,14599,14601,14602,14611,14613,14618,14625,14630,14632,14638,14640,14645,14657,14668,14669,14675,14700,14702,14732,14743,14745,14745,14749,14767,14771,14777,14793,14798,14806,14821));
        mainUserOrgs.removeAll(gatheredOrgs);
//        temp1.removeAll(mainUserOrgs);
//        temp2.removeAll(activeOrgs);

//        List<Integer> shopifyOrgs = List.of(6864,7178,7493,7633,8168,8942,9341,9605,9979,10073,10512,10610,10630,10765,10819,10840,10901,10919,11035,11079,11187,11201,11210,11303,11464,11518,11626,11835,11862,11904,11914,12070,12200,12202,12294,12315,12560,12640,12697,12742,12814,12863,12868,12909,12920,12940,13024,13044,13153,13333,13334,13395,13405,13458,13493,13558,13563,13590,13670,13706,13749,13753,13787,13791,13805,13864,13872,14002,14051,14107,14134,14238,14269,14282,14304,14307,14318,14387,14413,14427,14469,14479,14484,14492,14500,14541,14551,14566,14571,14574,14584,14592,14594,14599,14601,14602,14609,14611,14614,14618,14625,14630,14638,14639,14640,14641,14645,14668,14669,14675,14700,14702,14732,14743,14745,14749,14777,14821);
//        List<Integer> wooOrgs = List.of(7493,11187,11810,11979,12160,12788,13749,13763,14197,14231,14534,14542,14583,14593,14613,14632,14657,14694,14704,14767,14771,14793,14798,14806);
//
//        List<Integer> temp3 = new ArrayList<>(shopifyOrgs);
////        temp3.retainAll(wooOrgs);
//
//        List<Integer> temp4 = new ArrayList<>(wooOrgs);
////        temp4.retainAll(shopifyOrgs);

        List<String> emails = List.of("akshay.sethi@forestessentialsindia.com","aneesha.labroo@nykaa.com","karnav@fugazee.com","care@naturalvibes.in","jyoti.arya@meyerindia.in","gaurav@mbindia.net","Dm@travelogy.in","seohemincense@gmail.com","rahul.ecom@stori.in","harshjain@jkspices.in","ravi.r@powergummies.com","farmer@fraction9coffee.com","amit.tanwar@asics.com","rajesh.m@brandstudiolifestyle.com","parimagoel@dailyobjects.com","meeraplussizestore@gmail.com","dm@nappadori.com","kanak@vegnonveg.com","online@sangeetaboochra.com","contact.thehatke@gmail.com","care@ornatejewels.com","infoatmagique@gmail.com","dakshatashitap@gmail.com","suhas@campussutra.com","snaanaskincare1@gmail.com","info@4700bc.com","fashinfinity@yahoo.com","rohit.deshpande@lotusherbals.com","renu_sharma@njgroup.in","Sales@nuutjob.com","sales@nuutjob.com","jyoti.arya@meyerindia.in","moaz@alsannat.com","abdul.wajid@yaljgroup.com","rishabh@toffeecoffeeroasters.com","naman.arora@nykaa.com","khushboo.ahuja1@spykar.com","m.kaleem@upakarma.com","digitalmarketing@justintime.in","gowarthanan@sadhev.com","dm@nappadori.com","bhim@hidesign.com","gulabojaipur@gmail.com","suhas@campussutra.com","hr@ekostay.com","customercare@albertotorresi.com","dm@nappadori.com","shashank@superkicks.in","sonatextile16@gmail.com","inquiriesateterno@gmail.com","converse.online@camp.in","naveen@blanc9.com","hello@themedfa.com","shrinsam17@gmail.com","support@charmacy-india.com","uday.singh@zofffoods.com","rohit.pakhale@imara.co.in","naturemania.in@gmail.com","info@niana.co","crm@kimirica.shop","philomend@globusmail.com","ishita@meolaa.com","ecom.store@nutraj.com","munisha.badga@amorehealth.com","ecom.multiimport@gmail.com","monica@thelabellife.com","tushar.ahuja@kamaayurveda.com","ravi.r@powergummies.com","dolly@inc5shoes.com","veera.chilaka@ril.com","mukul.bhargav@khadinatural.com","mittimatetoys@gmail.com","operations@cristaspices.com","himansh@minibay.in","mukul.bhargav@khadinatural.com","nishu@diabexy.com","Aayushi@cossouq.com","sachinrolaniya@ellementry.com","hello@paaduks.com","sahil.kakde@emma-sleep.com","support@mulnivasiebooks.com","sarthak@eszett.in","brand@dusaan.com","shagun.maheshwari@papayain.com","sinha.sanat857@gmail.com","sarvash@dayalopticalsindia.com","mrityunjay@prdgy.in","misc@gadgetshieldz.com","lookforcomfort@gmail.com","studioojs7@gmail.com","aakash@liliorigin.com","nandiprasad@elecroom.in","vskcaringonline@gmail.com","ravi.rahul@tatainternational.com","aditya@bluebrew.in","abhi0mart.in@gmail.com","aumnisource@gmail.com","gautam@silverbourse.com","dhruv@firsteconomy.com","info@thedecorcentral.com","hello@lemonlords.com","drsoniayurvedpharmacy@gmail.com","Karan@cureayu.com","pimpompaatioil@gmail.com","wiselife.official@gmail.com","divyashriwellness@gmail.com","pdas@brandnourish.in","nimish@nytarra.in","help@denmonk.in","info.vogue93@gmail.com","rajeev@sgretail.in","support@mydesignation.com","ruchitdutt@gmail.com","flamingolounge00777@gmail.com","priyankasingh3.ps3@gmail.com","support@soulandpeace.com","support@geeklane.in","maaticare1@gmail.com","casebasket20@gmail.com","akanksha@barkersdozen.dog","partnership@aelegante.com","Contact@bachchaparty.in","contact@bachchaparty.in","developer@3003bc.com","business@zapvi.in","thechandistudio@gmail.com","Ceo@Plantomed.com","vinay.tiwari@brandeyes.in","support@fankarihaus.com","yogeshwar@magina.in","chirag@starfoxfashion.com");
        Map<String, Integer> counts = new HashMap<>();

        for(String email : emails) {
            if(counts.containsKey(email)) {
                counts.put(email, counts.get(email)+1);
            } else {
                counts.put(email, 1);
            }
        }

        List<String> duplicates = new ArrayList<>();

        for(String email: counts.keySet()) {
            if(counts.get(email) >1) {
                duplicates.add(email);
            }
        }

        System.out.println(objectMapper.writeValueAsString(duplicates));

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
