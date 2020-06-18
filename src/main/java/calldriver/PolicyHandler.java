package calldriver;

import calldriver.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{

    @Autowired
    DriverRepository dr;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDriverRequested_DriverRequest(@Payload DriverRequested driverRequested){


        if(driverRequested.isMe()){

            List<Driver> list = dr.findByStatus("NON");





            if(list == null || list.size() < 1) {
                DriverAssignFailed driverAssignFailed = new DriverAssignFailed();
                driverAssignFailed.setCallId(driverRequested.getId());
                driverAssignFailed.setStatus("Failed");
                driverAssignFailed.publish();

            } else {

                Driver driver = list.get(0);
                driver.setStatus("Matched");
                driver.setCallId(driverRequested.getId());
                dr.save(driver);
            }

    }
}
    

}
