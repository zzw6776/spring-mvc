package com.demo.hibernate.dao;

import com.demo.hibernate.entity.KeyValue;
import org.springframework.stereotype.Repository;

@Repository
public class KeyValueDao  extends HGenericDao<KeyValue,String>{

    public void updateValueByKey(KeyValue keyValue) {
        String updateSql = "UPDATE `key_value` SET `value` = ? WHERE `key_value`.`key` = ?";
        int i = this.bulkUpdate(updateSql, keyValue.getValue(), keyValue.getKey());
        System.out.println(i);
    }

}
