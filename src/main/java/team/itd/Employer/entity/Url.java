package team.itd.Employer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Url {
    private int period;
    private String text;
    private int per_page;
    private int page;

    public void nextPage() {
        page++;
    }
    @Override
    public String toString() {
        return String.format("https://api.hh.ru/vacancies?period=%s&text=%s&per_page=%s&page=%s",period,text,per_page,page);
    }
}
