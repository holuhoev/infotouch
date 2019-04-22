package ru.hse.infotouch.feed.site;

import io.vertx.core.json.JsonArray;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.dto.EmployeeHseDTO;
import ru.hse.infotouch.domain.dto.PersonHseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Component
public class PersonExtractor {

    @Value("${hse.site.url}")
    private String hseUrl;

    private Pattern avatarUrlPattern = Pattern.compile("background-image: url\\((.*)\\);");

    List<PersonHseDTO> extractPersonDTOs(Document doc, PersonUrl url) {
        return doc.select(".post.person .post__content.post__content_person").stream()
                .map(personElement -> {
                    PersonHseDTO person = new PersonHseDTO();

                    person.setFio(extractFio(personElement));
                    person.setUrl(extractUrl(personElement));
                    person.setAvatarUrl(extractAvatarUrl(personElement));
                    person.setCity(url.getCity());

                    person.setEmails(extractEmails(personElement));
                    person.setEmployees(extractEmployees(personElement));

                    return person;
                }).collect(Collectors.toList());
    }

    private String extractAvatarUrl(Element personElement) {
        String style = personElement.select(".link.link_dark.large.b")
                .select(".g-pic.person-avatar-small2")
                .attr("style");

        Matcher matcher = avatarUrlPattern.matcher(style);

        if (!matcher.find()) {
            return Strings.EMPTY;
        }

        return hseUrl + matcher.group(1);
    }

    private String extractUrl(Element personElement) {
        return hseUrl + personElement.select(".link.link_dark.large.b").attr("href");
    }

    private String extractFio(Element personElement) {
        return personElement.select(".link.link_dark.large.b").text();
    }

    private List<EmployeeHseDTO> extractEmployees(Element personElement) {
        return personElement.select(".with-indent7").select("span").stream()
                .map(this::extractEmployeeDTO)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<EmployeeHseDTO> extractEmployeeDTO(Element employeeElement) {
        String text = employeeElement.text();

        if (isNotEmpty(text)) {
            EmployeeHseDTO dto = new EmployeeHseDTO();

            String[] data = text.split(":");

            dto.setPosition(data[0]);

            if (data.length == 2) {
                String[] places = Arrays.stream(data[1].split("/")).map(String::trim).toArray(String[]::new);

                if (places.length == 1) {
                    dto.setFacultyName(places[0]);
                }

                if (places.length == 2) {
                    dto.setFacultyName(places[0]);
                    dto.setChairName(places[1]);
                }

                if (places.length == 3) {
                    dto.setFacultyName(places[1]);
                    dto.setChairName(places[2]);
                }

                return Optional.of(dto);
            }
        }

        return Optional.empty();
    }

    private String[] extractEmails(Element person) {
        return person.select(".l-extra.small").select(".link").stream()
                .map(e -> e.attr("data-at"))
                .map(this::mapEmailAttribute)
                .toArray(String[]::new);
    }

    private String mapEmailAttribute(String emailAttr) {
        StringJoiner joiner = new StringJoiner("");
        for (Object s : new JsonArray(emailAttr).getList()) {
            String str = s.toString();

            if (str.equals("-at-")) {
                joiner.add("@");
                continue;
            }

            joiner.add(str);
        }

        return joiner.toString();
    }
}
