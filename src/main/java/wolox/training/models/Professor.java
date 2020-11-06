package wolox.training.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Data
@DiscriminatorValue("professor")
public class Professor extends User{

    @Column
    private String subject;
}
