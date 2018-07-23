package com.scxys.activiti.rest.service.api.engine.variable;

import org.activiti.engine.ActivitiIllegalArgumentException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: qiuxinlin
 * @Dercription: 集合类型的rest变量转换器
 * @Date: 11:36 2017/11/1
 */
public class CollectionRestVariableConverter implements RestVariableConverter {
    @Override
    public String getRestTypeName() {
        return "collection";
    }

    @Override
    public Class<?> getVariableType() {
        return Collection.class;
    }

    @Override
    public Object getVariableValue(RestVariable result) {
        if(result.getValue() != null) {
            if(!(result.getValue() instanceof Collection)) {
                throw new ActivitiIllegalArgumentException("Converter can only convert collection");
            }
            return (ArrayList) result.getValue();
        }
        return null;
    }

    @Override
    public void convertVariableValue(Object variableValue, RestVariable result) {
        if(variableValue != null) {
            if(!(variableValue instanceof Collection)) {
                throw new ActivitiIllegalArgumentException("Converter can only convert collection");
            }
            result.setValue(variableValue);
        } else {
            result.setValue(null);
        }
    }
}
