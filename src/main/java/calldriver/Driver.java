package calldriver;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Driver_table")
public class Driver {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long callId;
    private String status;

    @PrePersist
    public void onPrePersist(){
        DriverAssigned driverAssigned = new DriverAssigned();
        BeanUtils.copyProperties(this, driverAssigned);
        driverAssigned.publishAfterCommit();


        DriverAssignFailed driverAssignFailed = new DriverAssignFailed();
        BeanUtils.copyProperties(this, driverAssignFailed);
        driverAssignFailed.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
