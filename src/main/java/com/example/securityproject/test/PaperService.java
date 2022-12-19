package com.example.securityproject.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService implements InitializingBean {
    private HashMap<Long, Paper> paperDB = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void setPaper(Paper paper) {
        paperDB.put(paper.getPaperId(), paper);
    }

    public List<Paper> getMyPapers(String username) {
        return paperDB.values().stream().filter(
                a->a.getStudentIds().contains(username)
        ).collect(Collectors.toList());
    }

    public Paper getPaper(Long paperId) {
        return paperDB.get(paperId);
    }
}