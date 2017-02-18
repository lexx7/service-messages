package com.lexx7.servicemessages.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date createTime;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id", name = "to_user_id")
    private User toUser;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id", name = "from_user_id")
    private User fromUser;

    @Column
    private String theme;

    @Column
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
    	// FindBug: EI_EXPOSE_REP2
    	// java.util.Date is mutable object
        return new Date(createTime.getTime());
    }

    public void setCreateTime(Date createTime) {
    	// FindBug: EI_EXPOSE_REP2
    	// java.util.Date is mutable object
        this.createTime =  new Date(createTime.getTime());
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
