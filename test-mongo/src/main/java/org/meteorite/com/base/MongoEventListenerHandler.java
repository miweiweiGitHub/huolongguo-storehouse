package org.meteorite.com.base;

import org.meteorite.com.annotation.AutoIncKey;
import org.meteorite.com.domian.entity.SeqInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author EX_052100260
 * @title: MongoEventListenerHandler
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-8 8:49
 */
public class MongoEventListenerHandler extends AbstractMongoEventListener<Object> {

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MongoTemplate mongo;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        super.onBeforeConvert(event);
        if (event.getSource()!=null) {

            ReflectionUtils.doWithFields(event.getSource().getClass(), new ReflectionUtils.FieldCallback() {
                @Override
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    if (field.isAnnotationPresent(AutoIncKey.class)) {

                        field.set(event.getSource(),getNextId(event.getSource().getClass().getSimpleName()));
                    }
                    if ("creatDate".equals(field.getName())) {
                        field.set(event.getSource(),getNow());
                    }
                }

            });


        }
    }

    private String getNow() {
        LocalDateTime now = LocalDateTime.now();
        return df.format(now);
    }

    private Long getNextId(String simpleName) {
        Query collName = new Query(Criteria.where("collName").is(simpleName));
        Update update = new Update();
        update.inc("seqId");
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
        findAndModifyOptions.upsert(true);
        findAndModifyOptions.returnNew(true);
        SeqInfo andModify = mongo.findAndModify(collName, update, findAndModifyOptions, SeqInfo.class);
        return andModify.getSeqId();
    }
}
