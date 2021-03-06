# Springboot REST API for Jukebox
This is a **Spring Boot REST API** built for retrieving qualified Jukebox objects by a unique ```setting_id``` and ```model_name```, which represents several Jukebox setting requirements.
## Main contents 
- SpringBoot [GET function](src/main/java/anthonyma/springbootjukerestapi/JukeController.java) that supports query with:  ```settingId``` and ```model name```
- [Docker Hub Repository](https://hub.docker.com/r/anthonyma0706/springboot-juke-rest-api/tags). Please pull the repo using ```docker pull anthonyma0706/springboot-juke-rest-api:latest```
- [Unit Test Class](src/test/java/anthonyma/springbootjukerestapi/SpringbootJukeRestApiApplicationTests.java). Please run ``` ./mvnw test ``` to see the test results

## Demo
### 1. Query by setting_id 
#### 1.1. setting_id is NOT found
<p align="center" width="70%">
<img width="400" alt="Screen Shot 2022-04-16 at 10 45 57 PM" src="https://user-images.githubusercontent.com/57332047/163698054-9254eb22-2fed-4d0c-b8dc-d74b73b295b0.png">
</p>

#### 1.2. setting_id is found, but NO qualified juke 

When searching for setting_id: 4efdf86e-68a1-4256-a154-5069510d78fc
```js
{
      "id" : "4efdf86e-68a1-4256-a154-5069510d78fc",
      "requires" : [
         "speaker",
         "camera",
         "led_panel"
      ]
 }
```
We can see the requirements are _**speaker, camera and led panel**_. However, there is no jukebox that satisfies these three requirements.
Therefore, when sending the query request to our server, you will see this _Error message_ pop up:

<p align="center" width="70%">
    <img width="600" alt="settings:4efdf86e-68a1-4256-a154-5069510d78fc" src="https://user-images.githubusercontent.com/57332047/163697084-899db5f1-2abc-4783-8222-822325b108c3.png">
</p>

#### 1.3. Qualified Juke info is retrieved by the given setting_id  

In contrast, for setting
```js
{
      "id": "9ac2d388-0f1b-4137-8415-02b953dd76f7",
      "requires": [
        "camera",
        "money_receiver"
      ]
}
```
when querying this setting_id with requirements being satisfied by any Jukebox, our app will show: 
<p align="center" width="70%">
      <img width="800" alt="Screen Shot 2022-04-16 at 10 00 23 PM" src="https://user-images.githubusercontent.com/57332047/163697061-7e224f31-c1fb-4d1a-8bb4-7d916ec9a12a.png">
</p>

which indeed satisfies **camera and money receiver**. By manually looking up all the jukebox info, we validate that this jukebox is the **only qualified one**.


### 2. Query by model_name 
<p align="center" width="70%">
<img width="887" alt="Screen Shot 2022-04-16 at 10 18 10 PM" src="https://user-images.githubusercontent.com/57332047/163697427-9a866a18-a168-40fc-b663-1999c4f95d03.png">
 </p>
 
 Here, by querying http://localhost:8080/jukes/fusion, we can find all the jukeboxes namely _**fusion**_ .
 
 ### 3. Unit Test
 <p align="center" width="70%">
 <img width="634" alt="Screen Shot unit test" src="https://user-images.githubusercontent.com/57332047/164083605-ed767691-f870-4883-9902-bd6e71757588.png">
 </p>
 
 The Unit Test results after running  ``` ./mvnw test ```.
 
 

