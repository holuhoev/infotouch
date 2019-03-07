package ru.hse.infotouch.feed.site;

import ru.hse.infotouch.domain.enums.CityType;

public class PersonUrl {
    private String hseUrl;
    private String personsEndpoint;
    private String letterParam;
    private String cityParam;
    private String letter;
    private CityType city;

    PersonUrl(String hseUrl, String personsEndpoint, String letterParam, String cityParam, String letter, CityType city) {
        this.hseUrl = hseUrl;
        this.personsEndpoint = personsEndpoint;
        this.letterParam = letterParam;
        this.cityParam = cityParam;
        this.letter = letter;
        this.city = city;
    }

    @Override
    public String toString() {
        return hseUrl
                + personsEndpoint
                + "?" + letterParam + "=" + letter
                + "&" + cityParam + "=" + city.getSite(); // TODO:
    }

    CityType getCity() {
        return city;
    }
}
