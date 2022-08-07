package com.ssafy.domain.imgae;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originFileName;

    @Column
    private String serverFileName;

    @Column
    private String filePath;

    @Column
    private String contentType;

    @Column
    private long fileSize;

    public Image(String originFileName, String contentType, String serverFileName, String filePath, long fileSize) {
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.contentType = contentType;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
