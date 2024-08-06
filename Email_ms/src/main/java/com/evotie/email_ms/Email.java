package com.evotie.email_ms;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_email")
    private String to;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "is_html")
    private boolean isHtml;

    @Column(name = "sent_date")
    private Date sentDate;

    public Email() {
    }

    public Long getId() {
        return this.id;
    }

    public String getTo() {
        return this.to;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }

    public boolean isHtml() {
        return this.isHtml;
    }

    public Date getSentDate() {
        return this.sentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Email)) return false;
        final Email other = (Email) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$to = this.getTo();
        final Object other$to = other.getTo();
        if (this$to == null ? other$to != null : !this$to.equals(other$to)) return false;
        final Object this$subject = this.getSubject();
        final Object other$subject = other.getSubject();
        if (this$subject == null ? other$subject != null : !this$subject.equals(other$subject)) return false;
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        if (this.isHtml() != other.isHtml()) return false;
        final Object this$sentDate = this.getSentDate();
        final Object other$sentDate = other.getSentDate();
        if (this$sentDate == null ? other$sentDate != null : !this$sentDate.equals(other$sentDate)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Email;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $to = this.getTo();
        result = result * PRIME + ($to == null ? 43 : $to.hashCode());
        final Object $subject = this.getSubject();
        result = result * PRIME + ($subject == null ? 43 : $subject.hashCode());
        final Object $body = this.getBody();
        result = result * PRIME + ($body == null ? 43 : $body.hashCode());
        result = result * PRIME + (this.isHtml() ? 79 : 97);
        final Object $sentDate = this.getSentDate();
        result = result * PRIME + ($sentDate == null ? 43 : $sentDate.hashCode());
        return result;
    }

    public String toString() {
        return "Email(id=" + this.getId() + ", to=" + this.getTo() + ", subject=" + this.getSubject() + ", body=" + this.getBody() + ", isHtml=" + this.isHtml() + ", sentDate=" + this.getSentDate() + ")";
    }

    // Getters and setters
}