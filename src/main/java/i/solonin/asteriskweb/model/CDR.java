package i.solonin.asteriskweb.model;

import i.solonin.asteriskweb.converter.DispositionConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CDR {
    @Id
    @Column(name = "ctid")
    private String id;
    @Column(length = 20)
    private String accountcode;
    @Column(length = 80)
    private String src;
    @Column(length = 80, name = "realsrc")
    private String realSrc;
    @Column(length = 80)
    private String dst;
    @Column(length = 80)
    private String dcontext;
    @Column(length = 80)
    private String clid;
    @Column(length = 80)
    private String channel;
    @Column(length = 80)
    private String dstchannel;
    @Column(length = 80)
    private String lastapp;
    @Column(length = 80)
    private String lastdata;
    private LocalDateTime start;
    private LocalDateTime answer;
    private LocalDateTime end;
    private Integer duration;
    private Integer billsec;
    @Column(name = "real_status")
    @Convert(converter = DispositionConverter.class)
    private Disposition disposition;
    @Column(length = 45)
    private String amaflags;
    @Column(length = 256)
    private String userfield;
    @Column(length = 150)
    private String uniqueid;
    @Column(length = 150)
    private String linkedid;
    @Column(length = 20)
    private String peeraccount;
    @Column(length = 100, name = "record")
    private String record;
    private Integer sequence;

    public boolean haveRecord() {
        return disposition.equals(Disposition.ANSWER) && !StringUtils.isEmpty(record);
    }

    public enum Disposition {
        CHANUNAVAIL("Канал недоступен"),
        CONGESTION("Проблема с каналом связи"),
        NOANSWER("Не отвечен"),
        BUSY("Занято"),
        ANSWER("Отвечен"),
        CANCEL("Вызов был отменён"),
        DONTCALL("Вызов был отклонён"),
        TORTURE("Вызов был отклонён"),
        INVALIDARGS("Внутренняя ошибка");

        @Getter
        private final String title;

        Disposition(String value) {
            this.title = value;
        }
    }
}
