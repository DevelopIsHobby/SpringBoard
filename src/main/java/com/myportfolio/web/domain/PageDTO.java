package com.myportfolio.web.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private Criteria cri;

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil((double)cri.getPageNum() / 10)) * 10 ;
        this.startPage = this.endPage - 9;

        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

        if(realEnd < this.endPage) {
            this.endPage = realEnd;
        }
        this.prev = this.startPage>1;
        this.next = this.endPage<realEnd;
    }
    public void print() {
        System.out.print(prev? "PREV":"");
        for(int i=startPage; i<=endPage; i++) {
            System.out.print(i+ " ");
        }
        System.out.println(next? "NEXT" : "");
    }
}
