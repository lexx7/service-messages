package com.lexx7.servicemessages.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "address_book")
public class AddressBook {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id", name = "to_user_id")
    private User toUser;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id", name = "from_user_id")
    private User fromUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
