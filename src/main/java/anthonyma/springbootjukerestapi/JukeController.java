package anthonyma.springbootjukerestapi;

import java.lang.reflect.Array;
import java.util.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
class JukeController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello World! This is Anthony's SpringBoot REST API!";
    }

    /**
     * This method returns a list of LinkedHashMap, representing the json info from the api
     * Each LinkedHashmap represents a single jukebox information
     * @return List<LinkedHashMap> list
     */
    @GetMapping(value = "/jukes")
    public List<LinkedHashMap> getJukesAll(){
        String url = "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes";
        RestTemplate restTemplate = new RestTemplate(); // read json data from the url (api)
        LinkedHashMap[] jukes = restTemplate.getForObject(url,LinkedHashMap[].class);

        /**	From: System.out.println(list.get(0).getClass());
         *  We find that the list parsed by restTemplate is a LinkedHashMap
         */
        List<LinkedHashMap> list = Arrays.asList(jukes);
        return list;
    }

    /**
     * Query jukebox by model name
     * @param modelName
     * @return ArrayList<LinkedHashMap> listToReturn
     */
    @GetMapping("/jukes/{modelName}")
    public ArrayList<LinkedHashMap> getJukesByModel(@PathVariable String modelName) {

        List<LinkedHashMap> allJukesList = this.getJukesAll();
        ArrayList<LinkedHashMap> listToReturn = new ArrayList<>(); // save info we want

        for (LinkedHashMap map: allJukesList) {
            String model = map.get("model").toString();
            if (modelName.equals(model)){listToReturn.add(map);}
        }
        if(listToReturn.isEmpty()){throw new JukeNotFoundException(modelName);}

        return listToReturn;
    }

    /**
     * We firstly generate a 2-element list for each jukebox, containing its component and Map (from getJukesAll())
     * Then save all the 2-element into a superset list, representing all the Jukes
     * The superset list will be used for comparing the setting requirements in getSettingsOne()
     * then directly return the Map (all the Juke information)
     *
     * @return superSetList
     */
    public ArrayList<ArrayList> getJukeComponentsAndMap() {

        ArrayList<ArrayList> superSetList = new ArrayList<>();
        List<LinkedHashMap> allJukesList = this.getJukesAll();
        for (LinkedHashMap map: allJukesList)
        {
            /**
             * Example: hashMapList = [{"name": "led_panel"},{"name": "amplifier"}]
             * Use this hashMapList to retrieve all the components/requirements
             */
            ArrayList<LinkedHashMap> compHashMapList = new ArrayList<>();
            compHashMapList = (ArrayList<LinkedHashMap>) map.get("components");

            ArrayList<String> componentsList = new ArrayList<>();
            for (int i = 0; i < compHashMapList.size(); i++) {
                String component = compHashMapList.get(i).get("name").toString();
                componentsList.add(component);
            }
            ArrayList compAndMap = new ArrayList<>();
            compAndMap.add(componentsList); // add a list first
            compAndMap.add(map);
            //compAndMap.add(map.toString()); // add the LinkedHashMap

            superSetList.add(compAndMap);
        }
        return superSetList;
    }

    /**
     * This method puts all setting info from json format to a List of LinkedHashMap, for querying by setting_ID
     * @return List<LinkedHashMap<String,ArrayList>> setList
     */
    @GetMapping(value = "/settings")
    public List<LinkedHashMap<String,ArrayList>> getSettingsAll(){
        String url = "http://my-json-server.typicode.com/touchtunes/tech-assignment/settings";
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, ArrayList<LinkedHashMap<String,ArrayList>>> settings = restTemplate.getForObject(url, HashMap.class);

        List<LinkedHashMap<String,ArrayList>> setList = new ArrayList();
        setList = settings.get("settings");
        return setList;
    }

    /**
     * This method queries a given setting_ID, then retrieve its setting requirements (ex. camera)
     * then iterates through the jukebox info to see which jukebox satisfies the requirement
     * If the setting_ID is not found, JukeNotFoundException(settingID_Query) will be raised
     * If the setting_ID is found but no jukebox is qualified, RequirementNotMatchedException() will be raised
     * @param settingID_Query
     * @return ArrayList<LinkedHashMap> listToReturn or Exception()
     */
    @GetMapping("/settings/{settingID_Query}")
    public ArrayList<LinkedHashMap> getSettingsOne(@PathVariable String settingID_Query) {

        ArrayList<ArrayList> jukeComponentsAndMap = this.getJukeComponentsAndMap();
        //System.out.println(jukeComponentsAndMap);

        ArrayList<LinkedHashMap> listToReturn = new ArrayList<>();

        List<LinkedHashMap<String,ArrayList>> list = this.getSettingsAll();
        for (LinkedHashMap<String,ArrayList> map: list
        ) {
            String settingID = String.valueOf(map.get("id"));//.toString();
            ArrayList requirementsList = (ArrayList) map.get("requires");

            if (settingID_Query.equals(settingID)){
                // if find the query setting_id, we proceed
                for (ArrayList compAndMap:jukeComponentsAndMap) {
                    /**
                     * As what we saved above, pos 0 is the Component ArrayList; pos 1 is the map
                     */
                    ArrayList componentList = (ArrayList) compAndMap.get(0);
                    //String mapString = compAndMap.get(1).toString();
                    LinkedHashMap mapToAdd = (LinkedHashMap) compAndMap.get(1);

                    Boolean reqSatisfied = true;
                    for (int i = 0; i < requirementsList.size(); i++) {
                        if ( ! componentList.contains(requirementsList.get(i))){
                            reqSatisfied = false;
                            break;
                        }
                    }
                    if (reqSatisfied){ // we can add the map to the final ListToReturn
                        listToReturn.add(mapToAdd);
                    }
                }
                if (listToReturn.isEmpty()){
                    System.out.println("setting ID is found, but no Juke satisfies the requirement");
                    throw new RequirementNotMatchedException();
                }
                return listToReturn;
                // We assume the settingID is unique, once we found one, there is no need to continue
            }
        }
        throw new JukeNotFoundException(settingID_Query);
    }


}
