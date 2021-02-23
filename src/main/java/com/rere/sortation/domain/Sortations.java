package com.rere.sortation.domain;

import com.rere.sortation.dto.SortationResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Sortations {
    @OneToMany(mappedBy = "user")
    private List<Sortation> sortations = new ArrayList<>();

    protected Sortations() {
    }

    private Sortations(List<Sortation> sortations) {
        this.sortations = sortations;
    }

    public static Sortations of(List<Sortation> sortations) {
        return new Sortations(sortations);
    }

    public static Sortations of() {
        return new Sortations();
    }

    public List<SortationResponse> getSortationResponses() {
        return sortations.stream()
                .map(SortationResponse::of)
                .collect(Collectors.toList());
    }

    public int size() {
        return sortations.size();
    }

}
