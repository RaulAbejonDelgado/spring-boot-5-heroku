package com.raul.spring.jpa.springjpav1.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T>  {

    private String url;
    private Page<T> page;

    private int totalPages;

    private int nunElemperPage;

    private int currentPage;

    private List<PageItem> pages;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNunElemperPage() {
        return nunElemperPage;
    }

    public void setNunElemperPage(int nunElemperPage) {
        this.nunElemperPage = nunElemperPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public void setPages(List<PageItem> pages) {
        this.pages = pages;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){return page.isLast();}
    public boolean isHasNext(){return page.hasNext();}
    public boolean isHasPrevius(){return page.hasPrevious();}

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<>();

        nunElemperPage = 6;
        totalPages = page.getTotalPages();
        currentPage = page.getNumber() + 1;

        int from, to;
        if(totalPages <= nunElemperPage) {
            from = 1;
            to = totalPages;
        } else {
            if(currentPage <= nunElemperPage / 2){
                from = 1;
                to = nunElemperPage;
            }else if (currentPage >= totalPages - nunElemperPage / 2){
                from = totalPages - nunElemperPage + 1;
                to = nunElemperPage;
            }else{
                from = currentPage - nunElemperPage / 2;
                to = nunElemperPage;
            }
        }

        for(int i=0; i < to; i++){
            pages.add(new PageItem(from + i ,currentPage == from + i));
        }



    }


}
