package anthonyma.springbootjukerestapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringbootJukeRestApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Hello World! This is Anthony's SpringBoot REST API!");
	}

	private String settingID_1 = "aRandomSettingID";


	@Test
	public void settingID_NotFoundShouldReturn() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/settings/" + settingID_1,
				String.class)).contains("ERROR: Could not find ID");

	}

	private String settingID_2 = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
	@Test
	public void settingID_RequirementNotMeetShouldReturn() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/settings/" + settingID_2,
				String.class)).contains("ERROR: Setting Requirement Not Matched for any Juke");

	}

	private String settingID_3 = "9ac2d388-0f1b-4137-8415-02b953dd76f7";
	String textMeetRequirements = """
  [{"id":"5ca94a8acfdeb5e01e5bdbe8","model":"virtuo","components":[{"name":"money_storage"},{"name":"money_pcb"},{"name":"money_storage"},{"name":"camera"},{"name":"money_receiver"}]}]""";
	@Test
	public void settingID_RequirementMeetShouldReturn() throws Exception {

		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/settings/" + settingID_3,
				String.class)).contains(textMeetRequirements);

	}



}
