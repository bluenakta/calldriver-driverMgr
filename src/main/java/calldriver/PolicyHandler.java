package calldriver;

import calldriver.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    DriverRepository dr;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDriverRequested_DriverRequest(@Payload DriverRequested driverRequested){


        if(driverRequested.isMe()){

            Driver driver = dr.findByStatus("NON").get(0);

            if(driver == null) {
                DriverAssignFailed driverAssignFailed = new DriverAssignFailed();
                driverAssignFailed.setCallId(driverRequested.getId());
                driverAssignFailed.setStatus("Failed");
            } else {
                driver.setStatus("Matched");
                dr.save(driver);
            }
    }
}
    

}
