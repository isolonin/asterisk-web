package i.solonin.asteriskweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ps_endpoints")
public class EndPoint {
    @Id
    @NotEmpty(message = "Не может быть пустым")
    @Column(length = 40)
    private String id;
    @Column(length = 40)
    private String transport = "udp-transport";
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aors")
    private AoR aors;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth")
    private Auth auth;
    @Column(length = 40)
    private String context;
    @Column(length = 200)
    private String disallow = "all";
    @Column(length = 200)
    private String allow = "alaw,ulaw";
    @Column(name = "direct_media")
    @Enumerated(EnumType.STRING)
    private YesNo directMedia = YesNo.no;
    @Column(length = 40, name = "callerid")
    private String callerId;
    @Column(length = 40, name = "tone_zone")
    private String toneZone = "ru";
    @Column(length = 40)
    private String language = "ru";
    @Column(length = 40, name = "from_domain")
    private String fromDomain;
    @Column(length = 40, name = "from_user")
    private String fromUser;
    @Column(length = 64)
    private String msisdn;

    @Transient
    private String contact;
    @Transient
    private String username;
    @Transient
    private String password;
    @Transient
    private String ids;
    @Transient
    private boolean isNew = true;

    public enum YesNo {
        yes, no
    }

    @OneToMany(mappedBy = "endPoint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Identify> identifies = new ArrayList<>();

    public String getIdentifiesAsString() {
        return identifies.stream().map(Identify::toString).collect(Collectors.joining(", "));
    }
}
