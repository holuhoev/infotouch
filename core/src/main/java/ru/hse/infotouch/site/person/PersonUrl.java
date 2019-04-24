package ru.hse.infotouch.site.person;

import ru.hse.infotouch.domain.models.enums.CityType;

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
                + "&" + cityParam + "=" + city.getSiteParam();
    }

    CityType getCity() {
        return city;
    }
}
