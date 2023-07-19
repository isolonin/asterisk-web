package i.solonin.asteriskweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ps_aors")
public class AoR {
    @Id
    @Column(length = 40)
    @NotEmpty(message = "Не может быть пустым")
    private String id;
    @Column
    private String contact;
    @Column(name = "max_contacts")
    private Integer maxContacts = 1;
    @Column(name = "remove_existing")
    private String removeExisting = "yes";

    @OneToMany(mappedBy = "aors", cascade = CascadeType.ALL)
    private List<EndPoint> endPoints = new ArrayList<>();

    public AoR(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
