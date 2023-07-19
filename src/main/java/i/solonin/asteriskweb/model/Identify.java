package i.solonin.asteriskweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ps_endpoint_id_ips")
public class Identify {
    @Id
    @Column(length = 40)
    private String id;
    @ManyToOne
    @JoinColumn(name = "endpoint")
    private EndPoint endPoint;
    @Column(name = "match", length = 80)
    private String match;

    public Identify(EndPoint endPoint, String ipMatch) {
        this.endPoint = endPoint;
        this.id = endPoint.getId() + "_" + ipMatch;
        this.match = ipMatch;
    }

    @Override
    public String toString() {
        return match;
    }
}
