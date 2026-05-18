package com.learn.shein.mongo.listener;


import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.learn.shein.mongo.model.SheinItem;
import com.learn.shein.mongo.service.SheinItemSeqService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class SheinItemListener
    extends AbstractMongoEventListener<SheinItem> {

    private SheinItemSeqService sheinItemSeqService;

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok

    @Override
    public void onBeforeConvert(BeforeConvertEvent<SheinItem> event) {
        if (event.getSource().getId() == null) {
            if (event.getSource().getId() == null || event.getSource().getId() < 1) {
                event.getSource().setId(this.sheinItemSeqService.generateSequence(SheinItem.SEQ_NAME));
            }
        }
    }
}
