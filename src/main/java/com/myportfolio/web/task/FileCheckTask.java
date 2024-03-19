package com.myportfolio.web.task;

import com.myportfolio.web.domain.BoardAttachVO;
import com.myportfolio.web.mapper.BoardAttachMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Component
@AllArgsConstructor
public class FileCheckTask {
    private BoardAttachMapper attachMapper;

    @Scheduled(cron="0 0 2 * * *")
    public void checkFiles() throws Exception {
        log.warn("File Check Task run ...........");

        log.warn(new Date());
        List<BoardAttachVO> fileList = attachMapper.getOldFiles();

        List<Path> filesListPaths = fileList.stream().map(vo-> Paths.get("C:\\upload", vo.getUploadPath(),
                        vo.getUuid()+"_"+vo.getFileName())).collect(Collectors.toList());

        fileList.stream().filter(vo->vo.isFileType() == true)
                .map(vo->Paths.get("C:\\upload",vo.getUploadPath(),
                        "s_"+vo.getUuid()+"_"+vo.getFileName())).forEach(p->filesListPaths.add(p));
        log.warn("==============================");

        filesListPaths.forEach(p->log.warn(p));

        File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();

        File[] removeFiles = targetDir.listFiles(file -> filesListPaths.contains(file.toPath())==false);

        log.warn("------------------------------");

        for(File file : removeFiles) {
            log.warn(file.getAbsolutePath());
            file.delete();
        }

    }

    private String getFolderYesterDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);

        String str = sdf.format(cal.getTime());

        return str.replace("-", File.separator);
    }
}
