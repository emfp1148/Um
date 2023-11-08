package com.CCA.web.notice;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeForm {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 200)
    private String title;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    private byte[] fileData; // 파일 데이터를 저장하기 위한 필드
    private String fileName; // 파일 이름을 저장하기 위한 필드


    // 생성자, 다른 필드, 메서드

    // Setter 및 Getter 메서드
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
