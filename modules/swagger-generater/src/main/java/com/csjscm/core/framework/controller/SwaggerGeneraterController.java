package com.csjscm.core.framework.controller;

import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.google.common.collect.Maps;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/apiDoc/apiDoc")
@Api("API文档")
@Slf4j
public class SwaggerGeneraterController {
    @RequestMapping(value = "/apiDoc/_generater",method = RequestMethod.POST)
    public APIResponse<String> apiDocGenerater(@RequestParam(value = "url",required = false,defaultValue = "127.0.0.1:1010")String url,
                                               @RequestParam(value = "group",required = false,defaultValue = "所有接口")String group,
                                               @ApiParam(value = "类型,pdf/html")@RequestParam(value = "type",required = false,defaultValue = "pdf")String type){

        Path path=Paths.get("./files/api/"+group+"/");
        File dir=path.toFile();
        dir.mkdirs();

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withPathsGroupedBy(GroupBy.TAGS)
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .build();
        try {
            Swagger2MarkupConverter
                    .from(new URL("http://"+url+"/v2/api-docs?group="+ URLEncoder.encode(group, "utf-8")))
                    .withConfig(config)
                    .build()
                    .toFolder(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        Attributes attributes = new Attributes();
        attributes.setCopyCss(true);
        attributes.setLinkCss(false);
        attributes.setSectNumLevels(3);
        attributes.setAnchors(true);
        attributes.setSectionNumbers(true);
        attributes.setHardbreaks(true);
        attributes.setTableOfContents(Placement.LEFT);
        Map<String,Object> map= Maps.newHashMap();
        map.put("pdf-style","cn");
        attributes.setAttributes(map);
        OptionsBuilder optionsBuilder = OptionsBuilder.options()
                .backend(type)
                .docType("book")
                .eruby("")
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .attributes(attributes);
        Path asciiInputFilePath=path.resolve("index.adoc");
        File file=asciiInputFilePath.toFile();
        try(FileWriter writer=new FileWriter(file.getPath())){
            writer.write("include::./overview.adoc[]\n" +
                    "include::./paths.adoc[]\n" +
                    "include::./security.adoc[]\n" +
                    "include::./definitions.adoc[]");
        }catch (Exception e){
            e.printStackTrace();
        }
        asciidoctor.convertFile(
                file,
                optionsBuilder.get());
        return APIResponse.success("生成成功");
    }

    @RequestMapping(value = "/api/_download",method = RequestMethod.GET)
    public void downloadApiFile(@RequestParam(value = "group",defaultValue = "所有接口",required = false)String group,
                                @ApiParam("类型：pdf/html")@RequestParam(value = "type",defaultValue = "pdf",required = false)String type,
                                HttpServletResponse response){
        try(FileInputStream fis=new FileInputStream("./files/api/"+group+"/index."+type);OutputStream out=response.getOutputStream()){
            byte[] buffer = new byte[8192];
            int bytesRead = 0;

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName="apiDoc."+type;
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);
            while((bytesRead = fis.read(buffer, 0, 8192)) != -1){
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }catch (IOException e){
            log.error("导出api文档失败",e);
        }
    }
}
