package messageListener;

import com.gym.crm.microservice.MicroserviceApplication;
import config.JmsTestConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@CucumberContextConfiguration
@SpringBootTest(classes = {JmsTestConfig.class, MicroserviceApplication.class})
public class MessageListenerSteps {
    @Autowired
    private JmsTemplate jmsTemplate;
    private List<String> receivedMessages = new ArrayList<>();

    @Given("an ActiveMQ broker is running")
    public void an_activemq_broker_is_running() {
        assertNotNull(jmsTemplate);
    }

    @When("multiple messages are sent to the queue")
    public void multiple_messages_are_sent_to_the_queue() {
        jmsTemplate.convertAndSend("trainerWorkloadQueue", "Message 1");
        jmsTemplate.convertAndSend("trainerWorkloadQueue", "Message 2");
        jmsTemplate.convertAndSend("trainerWorkloadQueue", "Message 3");
    }

    @Then("the message listener should receive all messages")
    public void the_message_listener_should_receive_all_messages() throws InterruptedException {
        assertEquals(3, receivedMessages.size());
        assertEquals("\"Message 1\"", receivedMessages.get(0));
        assertEquals("\"Message 2\"", receivedMessages.get(1));
        assertEquals("\"Message 3\"", receivedMessages.get(2));
    }

    @JmsListener(destination = "trainerWorkloadQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        receivedMessages.add(message);
    }
}
