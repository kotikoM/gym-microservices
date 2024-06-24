package cucumberTests;

import com.gym.crm.module.GymCrmModuleApplication;
import com.gym.crm.module.dto.ActionType;
import com.gym.crm.module.dto.TrainerWorkloadRequestDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = GymCrmModuleApplication.class)
@CucumberContextConfiguration
@ContextConfiguration
public class TrainerWorkloadActiveMQSteps {
    @Autowired
    private JmsTemplate jmsTemplate;

    private static final String QUEUE_NAME = "trainer-workload-queue";

    @Given("the gym-rest-api is running")
    public void gym_rest_api_is_running() {
    }

    @When("a TrainerWorkloadRequest is sent to the ActiveMQ queue")
    public void a_TrainerWorkloadRequest_is_sent_to_ActiveMQ() {
        TrainerWorkloadRequestDto request = new TrainerWorkloadRequestDto();
        request.setUsername("testuser");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setIsActive(true);
        request.setActionType(ActionType.ADD);
        request.setDurationMin(30);

        jmsTemplate.convertAndSend(new ActiveMQQueue(QUEUE_NAME), request);
    }

    @Then("the TrainerWorkloadRequest message should be present in the queue")
    public void the_message_should_be_in_the_queue() throws JMSException {
        jmsTemplate.setReceiveTimeout(1000);
        Message message = jmsTemplate.receive(new ActiveMQQueue(QUEUE_NAME));

        assertNotNull(message, "Message should not be null");
        assertEquals(ActiveMQTextMessage.class, message.getClass());

        ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
        assertNotNull(textMessage.getText(), "Message content should not be null");
    }
}
