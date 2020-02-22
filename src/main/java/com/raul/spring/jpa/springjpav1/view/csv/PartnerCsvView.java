package com.raul.spring.jpa.springjpav1.view.csv;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.util.Map;

@Component("index")// is mapping with partner list endpoint
public class PartnerCsvView extends AbstractView  {

    public PartnerCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map,
                                           HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse)
            throws Exception {

        httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"partners.csv\"");
        httpServletResponse.setContentType(getContentType());

        Page<Partner> partners = (Page<Partner>) map.get("partners");

        ICsvBeanWriter beanWriter = new CsvBeanWriter(httpServletResponse.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] header = {"id", "name", "surname","email","createAt"};
        beanWriter.writeHeader(header);

        for(Partner partner : partners){
            beanWriter.write(partner,header);
        }
        beanWriter.close();

    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
}
