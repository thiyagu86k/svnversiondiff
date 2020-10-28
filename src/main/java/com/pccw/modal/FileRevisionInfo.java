package com.pccw.modal;

public class FileRevisionInfo {
    long revisionNo1;
    long revisionNo2;
    String filePath;
    String file1Content;
    String file2Content;
    String branch;

    public String getFile1Content() {
        return file1Content;
    }

    public void setFile1Content(String file1Content) {
        this.file1Content = file1Content;
    }

    public String getFile2Content() {
        return file2Content;
    }

    public void setFile2Content(String file2Content) {
        this.file2Content = file2Content;
    }

    public long getRevisionNo1() {
        return revisionNo1;
    }

    public void setRevisionNo1(long revisionNo1) {
        this.revisionNo1 = revisionNo1;
    }

    public long getRevisionNo2() {
        return revisionNo2;
    }

    public void setRevisionNo2(long revisionNo2) {
        this.revisionNo2 = revisionNo2;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
