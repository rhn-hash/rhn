package com.fh.category.service.impl;

import com.fh.category.mapper.CategoryMapper;
import com.fh.category.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;



    @Override
    public List<Map<String, Object>> queryList() {
        List<Map<String ,Object>>  allList = categoryMapper.queryList();
        List<Map<String ,Object>>  parentList = new ArrayList<Map<String, Object>>();
        for (Map  map : allList) {
            if(map.get("pid").equals(0)){
                parentList.add(map);
            }
        }
        selectChildren(parentList,allList);
        return parentList;
    }

    public void selectChildren(List<Map<String ,Object>>  parentList, List<Map<String ,Object>>  allList){
        for (Map<String, Object> pmap : parentList) {
            List<Map<String ,Object>>  childrenList = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> amap : allList) {
                if(pmap.get("id").equals(amap.get("pid"))){
                    childrenList.add(amap);
                }
            }
            if(childrenList!=null && childrenList.size()>0){
                pmap.put("children",childrenList);
                selectChildren(childrenList,allList);
            }
        }
    }

}
