package com.reports.helpers;

import com.reports.dto.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReportHelpers {

    @Value("${report.template}")
    private String reportTemplate;

    public String readTemplate(Company company){
        System.out.println(this.reportTemplate);
        return this.reportTemplate
                .replace("{company}",company.getName())
                .replace("{foundation_date}",company.getFoundation_date().toString())
                .replace("{founder}",company.getFounder())
                .replace("{website_sites}",company.getWebSite().toString());
    }

    public List<String> getPlaceholdersFromTemplate(String template){
        var split = template.split("\\{");
        return Arrays.stream(split).filter(line-> !line.isEmpty())
                .map(line-> {
                    var index = line.indexOf("}");
                    return line.substring(0,index);
                }).collect(Collectors.toList());
    }
}
