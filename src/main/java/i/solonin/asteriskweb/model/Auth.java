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
@Entity(name = "ps_auths")
public class Auth {
    @Id
    @NotEmpty(message = "Не может быть пустым")
    @Column(length = 40)
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "auth", cascade = CascadeType.ALL)
    private List<EndPoint> endPoints = new ArrayList<>();

    public Auth(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return username;
    }
}
