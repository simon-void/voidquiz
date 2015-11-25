package net.simonvoid.data.model.orm;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by stephan on 25.11.2015.
 */

@Entity
@Table(name="highscore")
public class HighscoreDto {
    final public static int MAX_NAME_LENGTH = 16;

    @Id
    @GeneratedValue
    // the id of this entity is automatically generated
    private Long id;

    @CreationTimestamp
    // save the creation date (which is automatically generated
    private Date createdAt;

    @Column(length = MAX_NAME_LENGTH)
    // the optional @Column makes sure that the name is limited to a suitable size
    private String name;

    @Column
    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        assert name!=null
                : "name is null";
        assert name.length()<=MAX_NAME_LENGTH
                : "name is to long! max length: "+MAX_NAME_LENGTH+" ,current length: "+name.length()+ ", name: "+name;

        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        assert points>=0
                : "can't have negative points";

        this.points = points;
    }
}
